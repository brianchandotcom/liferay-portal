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

import java.lang.reflect.InvocationTargetException;
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
import org.junit.AssumptionViolatedException;
import org.junit.internal.runners.statements.Fail;
import org.junit.rules.MethodRule;
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
	public void run(RunNotifier runNotifier) {
		_testRunnerAdaptor = _testRunnerAdaptorThreadLocal.get();

		if (_testRunnerAdaptor == null) {
			try {
				_testRunnerAdaptor = TestRunnerAdaptorBuilder.build();

				_testRunnerAdaptor.beforeSuite();

				_testRunnerAdaptorThreadLocal.set(_testRunnerAdaptor);
			}
			catch (Exception e) {
				runNotifier.fireTestFailure(new Failure(getDescription(), e));
			}
		}

		if (_testRunnerAdaptor == null) {
			return;
		}

		runNotifier.addListener(
			new RunListener() {

				@Override
				public void testRunFinished(Result result) throws Exception {
					try {
						_testRunnerAdaptor.afterSuite();
						_testRunnerAdaptor.shutdown();

						_testRunnerAdaptor = null;
					}
					finally {
						_testRunnerAdaptorThreadLocal.remove();
					}
				}

			});

		super.run(runNotifier);
   }

	@Override
	protected Statement withBeforeClasses(Statement statement) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				TestClass testClass = getTestClass();

				_testRunnerAdaptor.beforeClass(
					testClass.getJavaClass(), () -> {});

				statement.evaluate();
			}
		};
	}

	@Override
	protected Statement withAfterClasses(Statement statement) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				Throwable throwable = null;

				try {
					statement.evaluate();
				}
				catch (Throwable t) {
					throwable = t;
				}

				TestClass testClass = getTestClass();

				try {
					_testRunnerAdaptor.afterClass(
						testClass.getJavaClass(), () -> {});
				}
				catch (Throwable t) {
					if (throwable != null) {
						t.addSuppressed(throwable);
					}

					throwable = t;
				}

				if (throwable != null) {
					throw throwable;
				}
			}
		};
	}

	@Override
	@SuppressWarnings("deprecation")
	protected Statement methodBlock(final FrameworkMethod method) {
		Object testObject = null;

		try {
			testObject = createTest();
		}
		catch (Throwable t) {
			if (t instanceof InvocationTargetException) {
				t = t.getCause();
			}

			return new Fail(t);
		}

		final Object test = testObject;

		try {
			Statement statement = methodInvoker(method, test);

			statement = possiblyExpectingExceptions(method, test, statement);

			statement = withPotentialTimeout(method, test, statement);

			final Statement originalStatement = statement;

			for (MethodRule methodRule : rules(test)) {
				statement = methodRule.apply(statement, method, test);
			}

			final Statement statementWithRules = statement;

			return new Statement() {

				@Override
				public void evaluate() throws Throwable {
					final AtomicInteger counter = new AtomicInteger();

					Throwable throwable = null;

					try {
						_testRunnerAdaptor.fireCustomLifecycle(
							new BeforeRules(
								test, method.getMethod(),
								() -> {
									counter.incrementAndGet();

									statementWithRules.evaluate();
								}
							));

						if(counter.get() == 0) {
							originalStatement.evaluate();
						}
					}
					catch(Throwable t) {
						throwable = t;
					}
					finally {
						try {
							_testRunnerAdaptor.fireCustomLifecycle(
								new AfterRules(
									test, method.getMethod(),
									LifecycleMethodExecutor.NO_OP));
						}
						catch(Throwable t) {
							if (throwable != null) {
								t.addSuppressed(throwable);
							}

							throwable = t;
						}
					}

					if (throwable != null) {
						throw throwable;
					}
				}
			};
		}
		catch(Exception e) {
			throw new RuntimeException("Could not create statement", e);
		}
    }

	@Override
	protected Statement methodInvoker(
		FrameworkMethod frameworkMethod, Object testObject) {

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				TestResult testResult = _testRunnerAdaptor.test(
					new TestMethodExecutor() {

						@Override
						public void invoke(Object... parameters)
							throws Throwable {

							try {
								frameworkMethod.invokeExplosively(
									testObject, parameters);
							}
							catch (Throwable t) {
								State.caughtTestException(t);

								throw t;
							}
						}

						@Override
						public Method getMethod() {
							return frameworkMethod.getMethod();
						}

						@Override
						public Object getInstance() {
							return testObject;
						}

					});

				Throwable throwable = testResult.getThrowable();

				if (throwable == null) {
					return;
				}

				if ((testResult.getStatus() == TestResult.Status.SKIPPED) &&
						(throwable instanceof SkippedTestExecutionException)) {

					testResult.setThrowable(
						new AssumptionViolatedException(
							throwable.getMessage()));
				}

				throw throwable;
			}
		};
	}

	private static final ThreadLocal<TestRunnerAdaptor>
		_testRunnerAdaptorThreadLocal = new ThreadLocal<>();

	private TestRunnerAdaptor _testRunnerAdaptor;

}