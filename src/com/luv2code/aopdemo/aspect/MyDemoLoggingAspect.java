package com.luv2code.aopdemo.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.luv2code.aopdemo.Account;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {
	
	
	@AfterThrowing(pointcut="execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",throwing="theExc" )
	public void afterThrowingFindAccountAdvice(
			
		JoinPoint theJoinPoint, Throwable theExc) {
		
		// print out which method we are advising on
		
           String method = theJoinPoint.getSignature().toShortString();
		   System.out.println("\n=====> Executing @AfterThrowing on method: " + method);
		
		// log the exception
		  
		   System.out.println("\n=====> The exception is: " + theExc);
		  
	}
	
	
	
	// add a new advice for @AfterReturning on the findAccounts method
	
	@AfterReturning(pointcut="execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",returning="result")
	public void afterReturningFindAccountsAdvice(JoinPoint theJoinPoint, List<Account> result) {
		// print out which method we are advising on 
		String method = theJoinPoint.getSignature().toShortString();
		
		System.out.println("\n=====> Executing @AfterReturning on method: " + method);
		// print out the results of the method call
		
		System.out.println("\n=====> Result is: " + result);
		
		// lets post-process the data .... lets modify it 
		
		// convert the account names to upercase
		convertAccountNameToUperCase(result);
		
		System.out.println("\n=====> Result is: " + result);
		
	}

	

    private void convertAccountNameToUperCase(List<Account> result) {
		
    	// loop through accounts
    	
    	for (Account tempAccount : result) {
    		
    		// get upercase of the version
    		String theUperName= tempAccount.getName().toUpperCase();
    		// update the name on account
    		
    		tempAccount.setName(theUperName);
    		
    	}
		
	}



	@Before("com.luv2code.aopdemo.aspect.LuvAOPExpressions.forDaoPackageNoGetterSetter()")
	public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {
		System.out.println("\n=====>>> Executing @Before advice on addAccount()");
		
		// display the method signature
		MethodSignature methodSign = (MethodSignature) theJoinPoint.getSignature();
		
		System.out.println("Method: "+methodSign);
		
		// display the method arguments
		
		// get args 
		Object[] args= theJoinPoint.getArgs();
		
		// loop through args
		for(Object tempArgs : args) {
			System.out.println(tempArgs);
			
			if(tempArgs instanceof Account) {
				
				// downcast and print Account specific stuff
				Account theAccount = (Account) tempArgs;
				
				System.out.println("account name:"+ theAccount.getName());
				System.out.println("account level:"+ theAccount.getLevel());
			}
		}
	}
	

    
}
