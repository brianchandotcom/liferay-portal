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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jboss.arquillian.test.spi.LifecycleMethodExecutor;
import org.jboss.arquillian.test.spi.TestMethodExecutor;
import org.jboss.arquillian.test.spi.TestResult;
import org.jboss.arquillian.test.spi.TestRunnerAdaptor;
import org.jboss.arquillian.test.spi.TestRunnerAdaptorBuilder;
import org.jboss.arquillian.test.spi.event.suite.AfterTestLifecycleEvent;
import org.jboss.arquillian.test.spi.event.suite.BeforeTestLifecycleEvent;
import org.jboss.arquillian.test.spi.execution.SkippedTestExecutionException;

import org.junit.AssumptionViolatedException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.internal.runners.statements.Fail;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.rules.MethodRule;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * @author Shuyang Zhou
 */
public class Arquillian extends Runner implements Filterable {

	public Arquillian(Class<?> clazz) {
		_clazz = clazz;
	}

	@Override
	public void filter(Filter filter) throws NoTestsRemainException {
		_filter = filter;

		_testClass = new FilteredSortedTestClass(_clazz);

		List<FrameworkMethod> frameworkMethods = _testClass.getAnnotatedMethods(
			Test.class);

		if (frameworkMethods.isEmpty()) {
			throw new NoTestsRemainException();
		}
	}

	@Override
	public Description getDescription() {
		Description description = Description.createSuiteDescription(
			_clazz.getName(), _clazz.getAnnotations());

		for (FrameworkMethod frameworkMethod : _getChildren()) {
			description.addChild(_describeChild(frameworkMethod));
		}

		return description;
	}

	@Override
	public void run(RunNotifier runNotifier) {
		TestRunnerAdaptor testRunnerAdaptor =
			_testRunnerAdaptorThreadLocal.get();

		if (testRunnerAdaptor == null) {
			try {
				testRunnerAdaptor = TestRunnerAdaptorBuilder.build();

				testRunnerAdaptor.beforeSuite();

				_testRunnerAdaptorThreadLocal.set(testRunnerAdaptor);
			}
			catch (Exception e) {
				runNotifier.fireTestFailure(new Failure(getDescription(), e));

				return;
			}
		}

		final TestRunnerAdaptor finalTestRunnerAdaptor = testRunnerAdaptor;

		runNotifier.addListener(
			new RunListener() {

				@Override
				public void testRunFinished(Result result) throws Exception {
					try {
						finalTestRunnerAdaptor.afterSuite();

						finalTestRunnerAdaptor.shutdown();
					}
					finally {
						_testRunnerAdaptorThreadLocal.remove();
					}
				}

			});

		Description description = getDescription();

		try {
			Statement statement = _classBlock(runNotifier, testRunnerAdaptor);

			statement.evaluate();
		}
		catch (org.junit.internal.AssumptionViolatedException ave) {
			runNotifier.fireTestAssumptionFailed(new Failure(description, ave));
		}
		catch (StoppedByUserException sbue) {
			throw sbue;
		}
		catch (MultipleFailureException mfe) {
			for (Throwable t : mfe.getFailures()) {
				runNotifier.fireTestFailure(new Failure(description, t));
			}
		}
		catch (Throwable t) {
			runNotifier.fireTestFailure(new Failure(description, t));
		}
	}

	private Statement _classBlock(
		RunNotifier runNotifier, TestRunnerAdaptor testRunnerAdaptor) {

		Statement statement = new Statement() {

			@Override
			public void evaluate() {
				for (FrameworkMethod frameworkMethod : _getChildren()) {
					_runChild(frameworkMethod, runNotifier, testRunnerAdaptor);
				}
			}

		};

		boolean hasTestMethod = false;

		for (FrameworkMethod frameworkMethod : _getChildren()) {
			if (!_isIgnored(frameworkMethod)) {
				hasTestMethod = true;

				break;
			}
		}

		if (hasTestMethod) {
			return new Statement() {

				@Override
				public void evaluate() throws Throwable {
					Throwable throwable = null;

					try {
						testRunnerAdaptor.beforeClass(
							_clazz, LifecycleMethodExecutor.NO_OP);

						statement.evaluate();
					}
					catch (Throwable t) {
						throwable = t;
					}

					try {
						testRunnerAdaptor.afterClass(
							_clazz, LifecycleMethodExecutor.NO_OP);
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

		return statement;
	}

	private Description _describeChild(FrameworkMethod frameworkMethod) {
		return _methodDescriptions.computeIfAbsent(
			frameworkMethod,
			keyFrameworkMethod -> {
				return Description.createTestDescription(
					_clazz, keyFrameworkMethod.getName(),
					keyFrameworkMethod.getAnnotations());
			});
	}

	private List<FrameworkMethod> _getChildren() {
		TestClass testClass = _getTestClass();

		return testClass.getAnnotatedMethods(Test.class);
	}

	private List<MethodRule> _getMethodRules(Object testObject) {
		TestClass testClass = _getTestClass();

		List<MethodRule> methodRules = testClass.getAnnotatedMethodValues(
			testObject, Rule.class, MethodRule.class);

		methodRules.addAll(
			testClass.getAnnotatedFieldValues(
				testObject, Rule.class, MethodRule.class));

		return methodRules;
	}

	private TestClass _getTestClass() {
		if (_testClass == null) {
			_testClass = new FilteredSortedTestClass(_clazz);
		}

		return _testClass;
	}

	private boolean _isIgnored(FrameworkMethod frameworkMethod) {
		if (frameworkMethod.getAnnotation(Ignore.class) != null) {
			return true;
		}

		return false;
	}

	private Statement _methodBlock(
		FrameworkMethod frameworkMethod, TestRunnerAdaptor testRunnerAdaptor) {

		Object testObject = null;

		try {
			TestClass testClass = _getTestClass();

			Constructor<?> constructor = testClass.getOnlyConstructor();

			testObject = constructor.newInstance();
		}
		catch (ReflectiveOperationException roe) {
			if (roe instanceof InvocationTargetException) {
				return new Fail(roe.getCause());
			}

			return new Fail(roe);
		}

		final Object test = testObject;

		Statement statement = _methodInvoker(
			frameworkMethod, test, testRunnerAdaptor);

		statement = _possiblyExpectingExceptions(frameworkMethod, statement);

		statement = _withPotentialTimeout(frameworkMethod, statement);

		final Statement oldStatement = statement;

		for (MethodRule methodRule : _getMethodRules(test)) {
			statement = methodRule.apply(statement, frameworkMethod, test);
		}

		final Statement newStatement = statement;

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				final AtomicBoolean flag = new AtomicBoolean();

				Throwable throwable = null;

				try {
					testRunnerAdaptor.fireCustomLifecycle(
						new BeforeTestLifecycleEvent(
							test, frameworkMethod.getMethod(),
							() -> {
								flag.set(true);

								newStatement.evaluate();
							}));

					if (flag.get() == false) {
						oldStatement.evaluate();
					}
				}
				catch (Throwable t) {
					throwable = t;
				}
				finally {
					try {
						testRunnerAdaptor.fireCustomLifecycle(
							new AfterTestLifecycleEvent(
								test, frameworkMethod.getMethod(),
								LifecycleMethodExecutor.NO_OP));
					}
					catch (Throwable t) {
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

	private Statement _methodInvoker(
		FrameworkMethod frameworkMethod, Object testObject,
		TestRunnerAdaptor testRunnerAdaptor) {

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				TestResult testResult = testRunnerAdaptor.test(
					new TestMethodExecutor() {

						@Override
						public Object getInstance() {
							return testObject;
						}

						@Override
						public Method getMethod() {
							return frameworkMethod.getMethod();
						}

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

	private Statement _possiblyExpectingExceptions(
		FrameworkMethod frameworkMethod, Statement statement) {

		Test test = frameworkMethod.getAnnotation(Test.class);

		if ((test == null) || (test.expected() == Test.None.class)) {
			return statement;
		}

		return new ExpectException(statement, test.expected());
	}

	private void _runChild(
		FrameworkMethod frameworkMethod, RunNotifier runNotifier,
		TestRunnerAdaptor testRunnerAdaptor) {

		Description description = _describeChild(frameworkMethod);

		if (_isIgnored(frameworkMethod)) {
			runNotifier.fireTestIgnored(description);
		}
		else {
			Statement statement = _methodBlock(
				frameworkMethod, testRunnerAdaptor);

			runNotifier.fireTestStarted(description);

			try {
				statement.evaluate();
			}
			catch (org.junit.internal.AssumptionViolatedException ave) {
				runNotifier.fireTestAssumptionFailed(
					new Failure(description, ave));
			}
			catch (MultipleFailureException mfe) {
				for (Throwable t : mfe.getFailures()) {
					runNotifier.fireTestFailure(new Failure(description, t));
				}
			}
			catch (Throwable t) {
				runNotifier.fireTestFailure(new Failure(description, t));
			}
			finally {
				runNotifier.fireTestFinished(description);
			}
		}
	}

	private Statement _withPotentialTimeout(
		FrameworkMethod frameworkMethod, Statement statement) {

		Test test = frameworkMethod.getAnnotation(Test.class);

		if ((test == null) || (test.timeout() <= 0)) {
			return statement;
		}

		FailOnTimeout.Builder builder = FailOnTimeout.builder();

		builder.withTimeout(test.timeout(), TimeUnit.MILLISECONDS);

		return builder.build(statement);
	}

	private static final ThreadLocal<TestRunnerAdaptor>
		_testRunnerAdaptorThreadLocal = new ThreadLocal<>();

	private final Class<?> _clazz;
	private Filter _filter;
	private final Map<FrameworkMethod, Description> _methodDescriptions =
		new ConcurrentHashMap<>();
	private TestClass _testClass;

	private class FilteredSortedTestClass extends TestClass {

		@Override
		protected void scanAnnotatedMembers(
			Map<Class<? extends Annotation>, List<FrameworkMethod>> methods,
			Map<Class<? extends Annotation>, List<FrameworkField>> fields) {

			super.scanAnnotatedMembers(methods, fields);

			List<FrameworkMethod> frameworkMethods = methods.get(Test.class);

			if (_filter != null) {
				Iterator<FrameworkMethod> iterator =
					frameworkMethods.iterator();

				while (iterator.hasNext()) {
					FrameworkMethod frameworkMethod = iterator.next();

					if (_filter.shouldRun(_describeChild(frameworkMethod))) {
						try {
							_filter.apply(frameworkMethod);
						}
						catch (NoTestsRemainException ntre) {
							iterator.remove();
						}
					}
					else {
						iterator.remove();
					}
				}
			}

			frameworkMethods.sort(
				Comparator.comparing(FrameworkMethod::getName));
		}

		private FilteredSortedTestClass(Class<?> clazz) {
			super(clazz);
		}

	}

}