/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.arquillian.extension.junit.bridge.junit;

import com.liferay.arquillian.extension.junit.bridge.util.FrameworkMethodComparator;

import java.lang.annotation.Annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.concurrent.atomic.AtomicInteger;
import org.jboss.arquillian.junit.State;
import org.jboss.arquillian.junit.event.AfterRules;
import org.jboss.arquillian.junit.event.BeforeRules;
import org.jboss.arquillian.test.spi.LifecycleMethodExecutor;
import org.jboss.arquillian.test.spi.TestMethodExecutor;
import org.jboss.arquillian.test.spi.TestResult;
import org.jboss.arquillian.test.spi.TestRunnerAdaptor;
import org.jboss.arquillian.test.spi.TestRunnerAdaptorBuilder;
import org.jboss.arquillian.test.spi.execution.SkippedTestExecutionException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.MultipleFailureException;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.Fail;
import org.junit.rules.TestRule;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * @author Shuyang Zhou
 */
public class Arquillian extends BlockJUnit4ClassRunner {

	public Arquillian(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	@Override
	protected List<TestRule> classRules() {
		return Collections.emptyList();
	}

	@Override
	protected TestClass createTestClass(Class<?> testClass) {
		return new TestClass(testClass) {

			@Override
			public List<FrameworkMethod> getAnnotatedMethods(
				Class<? extends Annotation> annotationClass) {

				List<FrameworkMethod> frameworkMethods = new ArrayList<>(
					super.getAnnotatedMethods(annotationClass));

				Collections.sort(
					frameworkMethods, FrameworkMethodComparator.INSTANCE);

				return frameworkMethods;
			}

		};
	}

	@Override
	protected List<TestRule> getTestRules(Object target) {
		return Collections.emptyList();
	}

	@Override
	protected Statement withAfters(
		FrameworkMethod method, Object target, Statement statement) {

		return statement;
	}

	@Override
	protected Statement withBefores(
		FrameworkMethod method, Object target, Statement statement) {

		return statement;
	}

   @Override
   public void run(final RunNotifier notifier)
   {
		StateUtil.runnerStarted();

      // first time we're being initialized
      if(!StateUtil.hasTestAdaptor())
      {
         // no, initialization has been attempted before and failed, refuse to do anything else

		Throwable throwable = StateUtil.getInitializationException();

         if(throwable != null)
         {
            // failed on suite level, ignore children
            //notifier.fireTestIgnored(getDescription());
            notifier.fireTestFailure(
                  new Failure(getDescription(),
                        new RuntimeException(
                              "Arquillian has previously been attempted initialized, but failed. See cause for previous exception",
                              throwable)));
         }
         else
         {
            try
            {
               // ARQ-1742 If exceptions happen during boot
               TestRunnerAdaptor adaptor = TestRunnerAdaptorBuilder.build();
               // don't set it if beforeSuite fails
               adaptor.beforeSuite();
               StateUtil.testAdaptor(adaptor);
            }
            catch (Exception e)
            {
               // caught exception during BeforeSuite, mark this as failed
               StateUtil.caughtInitializationException(e);
               notifier.fireTestFailure(new Failure(getDescription(), e));
            }
         }
      }
      notifier.addListener(new RunListener()
      {
         @Override
         public void testRunFinished(Result result) throws Exception
         {
            StateUtil.runnerFinished();
            shutdown();
         }

         private void shutdown()
         {
            try
            {
               if(StateUtil.isLastRunner())
               {
                  try
                  {
                     if(adaptor != null)
                     {
                        adaptor.afterSuite();
                        adaptor.shutdown();
                     }
                  }
                  finally
                  {
                     StateUtil.clean();
                  }
               }
               adaptor = null;
            }
            catch (Exception e)
            {
               throw new RuntimeException("Could not run @AfterSuite", e);
            }
         }
      });
      // initialization ok, run children
      if(StateUtil.hasTestAdaptor())
      {
         adaptor = StateUtil.getTestAdaptor();
         super.run(notifier);
      }
   }

   /**
    * Override to allow test methods with arguments
    */
   @Override
   protected void validatePublicVoidNoArgMethods(Class<? extends Annotation> annotation, boolean isStatic, List<Throwable> errors)
   {
      List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(annotation);
      for (FrameworkMethod eachTestMethod : methods)
      {
         eachTestMethod.validatePublicVoid(isStatic, errors);
      }
   }

	@Override
	protected Statement withBeforeClasses(Statement statement) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				TestClass testClass = getTestClass();

				adaptor.beforeClass(testClass.getJavaClass(), () -> {});

				statement.evaluate();
			}
		};
	}

	@Override
	protected Statement withAfterClasses(Statement statement) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				multiExecute(
					statement,
					new Statement() {

						@Override
						public void evaluate() throws Throwable {
							TestClass testClass = getTestClass();

							adaptor.afterClass(
								testClass.getJavaClass(), () -> {});
						}

					}
				);
			}
		};
	}

   @Override
   @SuppressWarnings("deprecation")
   protected Statement methodBlock(final FrameworkMethod method) {
       Object temp;
       try {
           temp = new ReflectiveCallable() {
               @Override
               protected Object runReflectiveCall() throws Throwable {
                   return createTest();
               }
           }.run();
       } catch (Throwable e) {
           return new Fail(e);
       }
       final Object test = temp;
       try
       {
           Method withRules = BlockJUnit4ClassRunner.class.getDeclaredMethod("withRules",
                   new Class[] {FrameworkMethod.class, Object.class, Statement.class});
           withRules.setAccessible(true);

           Statement statement = methodInvoker(method, test);
           statement = possiblyExpectingExceptions(method, test, statement);
           statement = withPotentialTimeout(method, test, statement);

           Statement arounds = withBefores(method, test, statement);
           arounds = withAfters(method, test, arounds);
           final Statement stmtwithLifecycle = arounds;
           final Statement stmtWithRules = (Statement)withRules.invoke(this, new Object[] {method, test, arounds});
           return new Statement() {

               @Override
               public void evaluate() throws Throwable {
                   State.caughtExceptionAfterJunit(null);
                   final AtomicInteger integer = new AtomicInteger();
                   List<Throwable> exceptions = new ArrayList<Throwable>();

                   try {
                       adaptor.fireCustomLifecycle(new BeforeRules(test, method.getMethod(), new LifecycleMethodExecutor() {
                           @Override
                           public void invoke() throws Throwable {
                               integer.incrementAndGet();
                               stmtWithRules.evaluate();
                           }
                       }));
                       // If AroundRules (includes lifecycles) were not executed, invoke only lifecycles+test
                       if(integer.get() == 0) {
                           try {
                               stmtwithLifecycle.evaluate();
                           } catch(Throwable t) {
                               State.caughtExceptionAfterJunit(t);
                               throw t;
                           }
                       }
                   } catch(Throwable t) {
                       State.caughtExceptionAfterJunit(t);
                       exceptions.add(t);
                   }
                   finally {
                       try {
                           adaptor.fireCustomLifecycle(new AfterRules(test, method.getMethod(), LifecycleMethodExecutor.NO_OP));
                       } catch(Throwable t) {
                           State.caughtExceptionAfterJunit(t);
                           exceptions.add(t);
                       }
                   }
                   if(exceptions.isEmpty())
                   {
                      return;
                   }
                   if(exceptions.size() == 1)
                   {
                      throw exceptions.get(0);
                   }
                   throw new MultipleFailureException(exceptions);
               }
           };
       } catch(Exception e) {
           throw new RuntimeException("Could not create statement", e);
       }
    }

   @Override
   protected Statement methodInvoker(final FrameworkMethod method, final Object test)
   {
      return new Statement()
      {
         @Override
         public void evaluate() throws Throwable
         {
            TestResult result = adaptor.test(new TestMethodExecutor()
            {
               @Override
               public void invoke(Object... parameters) throws Throwable
               {
                  try
                  {
                     method.invokeExplosively(test, parameters);
                  }
                  catch (Throwable e)
                  {
                     // Force a way to return the thrown Exception from the Container to the client.
                     State.caughtTestException(e);
                     throw e;
                  }
               }

               public Method getMethod()
               {
                  return method.getMethod();
               }

               public Object getInstance()
               {
                  return test;
               }
            });
            Throwable throwable = result.getThrowable();
            if(throwable != null)
            {
               if (result.getStatus() == TestResult.Status.SKIPPED)
               {
                   if (throwable instanceof SkippedTestExecutionException)
                   {
                       result.setThrowable(new AssumptionViolatedException(throwable.getMessage()));
                   }
               }
               throw result.getThrowable();
            }
         }
      };
   }

   /**
    * A helper to safely execute multiple statements in one.<br/>
    *
    * Will execute all statements even if they fail, all exceptions will be kept. If multiple {@link Statement}s
    * fail, a {@link MultipleFailureException} will be thrown.
    *
    * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
    * @version $Revision: $
    */
   private void multiExecute(Statement... statements) throws Throwable
   {
      List<Throwable> exceptions = new ArrayList<Throwable>();
      for(Statement command : statements)
      {
         try
         {
            command.evaluate();
         }
         catch (Throwable e)
         {
            exceptions.add(e);
         }
      }
      if(exceptions.isEmpty())
      {
         return;
      }
      if(exceptions.size() == 1)
      {
         throw exceptions.get(0);
      }
      throw new MultipleFailureException(exceptions);
   }

	private static class StateUtil {

		public static void caughtInitializationException(Throwable throwable) {
			try {
				_caughtInitializationExceptionMethod.invoke(null, throwable);
			}
			catch (ReflectiveOperationException roe) {
				throw new RuntimeException(roe);
			}
		}

		public static void clean() {
			try {
				_cleanMethod.invoke(null);
			}
			catch (ReflectiveOperationException roe) {
				throw new RuntimeException(roe);
			}
		}

		public static Throwable getInitializationException() {
			try {
				return (Throwable)_getInitializationExceptionMethod.invoke(null);
			}
			catch (ReflectiveOperationException roe) {
				throw new RuntimeException(roe);
			}
		}

		public static TestRunnerAdaptor getTestAdaptor() {
			try {
				return (TestRunnerAdaptor)_getTestAdaptorMethod.invoke(null);
			}
			catch (ReflectiveOperationException roe) {
				throw new RuntimeException(roe);
			}
		}

		public static boolean hasTestAdaptor() {
			try {
				return (boolean)_hasTestAdaptorMethod.invoke(null);
			}
			catch (ReflectiveOperationException roe) {
				throw new RuntimeException(roe);
			}
		}

		public static boolean isLastRunner() {
			try {
				return (boolean)_isLastRunnerMethod.invoke(null);
			}
			catch (ReflectiveOperationException roe) {
				throw new RuntimeException(roe);
			}
		}

		public static void runnerFinished() {
			try {
				_runnerFinishedMethod.invoke(null);
			}
			catch (ReflectiveOperationException roe) {
				throw new RuntimeException(roe);
			}
		}

		public static void runnerStarted() {
			try {
				_runnerStartedMethod.invoke(null);
			}
			catch (ReflectiveOperationException roe) {
				throw new RuntimeException(roe);
			}
		}

		public static void testAdaptor(TestRunnerAdaptor testRunnerAdaptor) {
			try {
				_testAdaptorMethod.invoke(null, testRunnerAdaptor);
			}
			catch (ReflectiveOperationException roe) {
				throw new RuntimeException(roe);
			}
		}

		private static final Method _caughtInitializationExceptionMethod;
		private static final Method _cleanMethod;
		private static final Method _getInitializationExceptionMethod;
		private static final Method _getTestAdaptorMethod;
		private static final Method _hasTestAdaptorMethod;
		private static final Method _isLastRunnerMethod;
		private static final Method _runnerFinishedMethod;
		private static final Method _runnerStartedMethod;
		private static final Method _testAdaptorMethod;

		static {
			try {
				_runnerStartedMethod = State.class.getDeclaredMethod(
					"runnerStarted");

				_runnerStartedMethod.setAccessible(true);

				_hasTestAdaptorMethod = State.class.getDeclaredMethod(
					"hasTestAdaptor");

				_hasTestAdaptorMethod.setAccessible(true);

				_getInitializationExceptionMethod = State.class.getDeclaredMethod(
					"getInitializationException");

				_getInitializationExceptionMethod.setAccessible(true);

				_testAdaptorMethod = State.class.getDeclaredMethod(
					"testAdaptor", TestRunnerAdaptor.class);

				_testAdaptorMethod.setAccessible(true);

				_caughtInitializationExceptionMethod =
					State.class.getDeclaredMethod(
						"caughtInitializationException", Throwable.class);

				_caughtInitializationExceptionMethod.setAccessible(true);

				_runnerFinishedMethod = State.class.getDeclaredMethod(
					"runnerFinished");

				_runnerFinishedMethod.setAccessible(true);

				_isLastRunnerMethod = State.class.getDeclaredMethod("isLastRunner");

				_isLastRunnerMethod.setAccessible(true);

				_cleanMethod = State.class.getDeclaredMethod("clean");

				_cleanMethod.setAccessible(true);

				_getTestAdaptorMethod = State.class.getDeclaredMethod(
					"getTestAdaptor");

				_getTestAdaptorMethod.setAccessible(true);
			}
			catch (ReflectiveOperationException roe) {
				throw new ExceptionInInitializerError(roe);
			}
		}
	}

   private TestRunnerAdaptor adaptor;

}