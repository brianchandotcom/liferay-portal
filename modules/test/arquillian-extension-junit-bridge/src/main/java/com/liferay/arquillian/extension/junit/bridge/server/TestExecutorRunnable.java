/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.arquillian.extension.junit.bridge.server;

import com.liferay.arquillian.extension.junit.bridge.command.RunNotifierCommand;

import java.io.EOFException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.Socket;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

/**
 * @author Shuyang Zhou
 */
public class TestExecutorRunnable implements Runnable {

	public TestExecutorRunnable(
		Bundle bundle, Map<String, List<String>> filteredMethodNamesMap,
		String reportServerHostName, int reportServerPort, long passCode) {

		_bundle = bundle;
		_filteredMethodNamesMap = filteredMethodNamesMap;
		_reportServerHostName = reportServerHostName;
		_reportServerPort = reportServerPort;
		_passCode = passCode;
	}

	@Override
	public void run() {
		try (Socket socket = new Socket(
				_reportServerHostName, _reportServerPort);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				socket.getOutputStream());
			ObjectInputStream objectInputStream = new ObjectInputStream(
				socket.getInputStream())) {

			objectOutputStream.writeLong(_passCode);

			objectOutputStream.flush();

			while (true) {
				String testClass = objectInputStream.readUTF();

				_execute(_createTestClass(testClass), objectOutputStream);

				objectOutputStream.writeObject(null);

				objectOutputStream.flush();
			}
		}
		catch (EOFException eofException) {
		}
		catch (Exception exception) {
			try {
				_bundle.uninstall();
			}
			catch (BundleException bundleException) {
				exception.addSuppressed(bundleException);
			}

			_logger.log(
				Level.SEVERE,
				"Unable to report back to client. Uninstalled test bundle " +
					"and abort test.",
				exception);
		}
	}

	private static Throwable _findAssumptionViolatedException(
		Throwable throwable) {

		if (throwable instanceof AssumptionViolatedException) {
			return throwable;
		}

		Throwable[] suppressionThrowables = throwable.getSuppressed();

		for (Throwable suppressionThrowable : suppressionThrowables) {
			return _findAssumptionViolatedException(suppressionThrowable);
		}

		return null;
	}

	private static void _processThrowable(
			boolean classLevel, Throwable throwable,
			ObjectOutputStream objectOutputStream, Description description)
		throws IOException {

		Throwable assumptionViolatedThrowable =
			_findAssumptionViolatedException(throwable);

		if (assumptionViolatedThrowable != null) {
			if (classLevel) {
				objectOutputStream.writeObject(
					RunNotifierCommand.testStarted(description));
			}

			// To neutralize the nonserializable Matcher field inside
			// AssumptionViolatedException

			AssumptionViolatedException assumptionViolatedException =
				new AssumptionViolatedException(
					assumptionViolatedThrowable.getMessage());

			assumptionViolatedException.setStackTrace(
				assumptionViolatedThrowable.getStackTrace());

			objectOutputStream.writeObject(
				RunNotifierCommand.assumptionFailed(
					description, assumptionViolatedException));

			if (classLevel) {
				objectOutputStream.writeObject(
					RunNotifierCommand.testFinished(description));
			}
		}
		else if (throwable instanceof MultipleFailureException) {
			MultipleFailureException multipleFailureException =
				(MultipleFailureException)throwable;

			for (Throwable curThrowable :
					multipleFailureException.getFailures()) {

				_processThrowable(
					objectOutputStream, description, curThrowable);
			}
		}
		else {
			_processThrowable(objectOutputStream, description, throwable);
		}

		objectOutputStream.flush();
	}

	private static void _processThrowable(
			ObjectOutputStream objectOutputStream, Description description,
			Throwable throwable)
		throws IOException {

		try {
			objectOutputStream.writeObject(
				RunNotifierCommand.testFailure(description, throwable));
		}
		catch (NotSerializableException notSerializableException) {
			objectOutputStream.reset();

			Class<? extends Throwable> clazz = throwable.getClass();

			Exception exception = new Exception(
				clazz.getName() + ": " + throwable.getMessage());

			exception.setStackTrace(throwable.getStackTrace());

			notSerializableException.initCause(exception);

			objectOutputStream.writeObject(
				RunNotifierCommand.testFailure(
					description, notSerializableException));
		}
	}

	private Statement _classBlock(
		TestClass testClass, Description description,
		ObjectOutputStream objectOutputStream) {

		Statement statement = new Statement() {

			@Override
			public void evaluate() throws Throwable {
				for (FrameworkMethod frameworkMethod :
						testClass.getAnnotatedMethods(Test.class)) {

					Description description = Description.createTestDescription(
						testClass.getName(), frameworkMethod.getName());

					try {
						objectOutputStream.writeObject(
							RunNotifierCommand.testStarted(description));

						objectOutputStream.flush();

						Statement statement = _methodBlock(
							testClass, frameworkMethod.getMethod());

						statement.evaluate();
					}
					catch (Throwable throwable) {
						_processThrowable(
							false, throwable, objectOutputStream, description);
					}
					finally {
						objectOutputStream.writeObject(
							RunNotifierCommand.testFinished(description));

						objectOutputStream.flush();
					}
				}
			}

		};

		statement = _withBefores(
			statement, BeforeClass.class, testClass, statement);
		statement = _withAfters(
			statement, AfterClass.class, testClass, statement);
		statement = _withRules(
			statement, ClassRule.class, testClass, null, description);

		return statement;
	}

	private TestClass _createTestClass(String testClassName)
		throws ClassNotFoundException {

		return new TestClass(_bundle.loadClass(testClassName)) {

			@Override
			protected void scanAnnotatedMembers(
				Map<Class<? extends Annotation>, List<FrameworkMethod>>
					frameworkMethodsMap,
				Map<Class<? extends Annotation>, List<FrameworkField>>
					frameworkFieldsMap) {

				super.scanAnnotatedMembers(
					frameworkMethodsMap, frameworkFieldsMap);

				List<FrameworkMethod> testFrameworkMethods =
					frameworkMethodsMap.get(Test.class);

				List<FrameworkMethod> ignoreFrameworkMethods =
					frameworkMethodsMap.get(Ignore.class);

				if (ignoreFrameworkMethods != null) {
					testFrameworkMethods.removeAll(ignoreFrameworkMethods);
				}

				List<String> filteredMethodNames = _filteredMethodNamesMap.get(
					getName());

				if (filteredMethodNames != null) {
					testFrameworkMethods.removeIf(
						frameworkMethod -> filteredMethodNames.contains(
							frameworkMethod.getName()));
				}

				testFrameworkMethods.sort(
					Comparator.comparing(FrameworkMethod::getName));
			}

		};
	}

	private void _execute(
			TestClass testClass, ObjectOutputStream objectOutputStream)
		throws IOException {

		Description description = Description.createSuiteDescription(
			testClass.getJavaClass());

		try {
			Statement statement = _classBlock(
				testClass, description, objectOutputStream);

			statement.evaluate();
		}
		catch (Throwable throwable) {
			_processThrowable(true, throwable, objectOutputStream, description);
		}
	}

	private Class<?> _getExpectedExceptionClass(Method method) {
		Test test = method.getAnnotation(Test.class);

		if (test == null) {
			return Test.None.class;
		}

		return test.expected();
	}

	private Statement _methodBlock(TestClass testClass, Method method)
		throws Throwable {

		Class<?> clazz = testClass.getJavaClass();

		Object target = clazz.newInstance();

		Statement statement = new InvokeMethod(null, target) {

			@Override
			public void evaluate() throws Throwable {
				Thread currentThread = Thread.currentThread();

				ClassLoader classLoader = currentThread.getContextClassLoader();

				currentThread.setContextClassLoader(clazz.getClassLoader());

				Throwable throwable1 = null;

				try {
					method.invoke(target);
				}
				catch (Throwable throwable2) {
					throwable1 = throwable2;
				}
				finally {
					currentThread.setContextClassLoader(classLoader);
				}

				if (throwable1 == null) {
					Class<?> throwableClass = _getExpectedExceptionClass(
						method);

					if (Test.None.class != throwableClass) {
						throw new AssertionError(
							"Expected test to throw " + throwableClass);
					}
				}
				else {
					if (throwable1 instanceof InvocationTargetException) {
						throwable1 = throwable1.getCause();
					}

					if (throwable1 instanceof AssumptionViolatedException) {
						throw throwable1;
					}

					_processExpectedException(throwable1, method);
				}
			}

		};

		statement = _withTimeout(method, statement);

		statement = _withBefores(statement, Before.class, testClass, target);

		statement = _withAfters(statement, After.class, testClass, target);

		statement = _withRules(
			statement, Rule.class, testClass, target,
			Description.createTestDescription(
				clazz, method.getName(), method.getAnnotations()));

		return statement;
	}

	private void _processExpectedException(Throwable throwable, Method method)
		throws Throwable {

		Class<?> expectedThrown = _getExpectedExceptionClass(method);

		if (expectedThrown == Test.None.class) {
			throw throwable;
		}

		Class<?> clazz = throwable.getClass();

		if (!expectedThrown.isAssignableFrom(clazz)) {
			String message =
				"Unexpected exception, expected<" + expectedThrown.getName() +
					"> but was<" + clazz.getName() + ">";

			throw new Exception(message, throwable);
		}
	}

	private Statement _withAfters(
		Statement statement, Class<? extends Annotation> afterClass,
		TestClass junitTestClass, Object target) {

		List<FrameworkMethod> frameworkMethods =
			junitTestClass.getAnnotatedMethods(afterClass);

		if (!frameworkMethods.isEmpty()) {
			statement = new RunAfters(statement, frameworkMethods, target);
		}

		return statement;
	}

	private Statement _withBefores(
		Statement statement, Class<? extends Annotation> beforeClass,
		TestClass junitTestClass, Object target) {

		List<FrameworkMethod> frameworkMethods =
			junitTestClass.getAnnotatedMethods(beforeClass);

		if (!frameworkMethods.isEmpty()) {
			statement = new RunBefores(statement, frameworkMethods, target);
		}

		return statement;
	}

	private Statement _withRules(
		Statement statement, Class<? extends Annotation> ruleClass,
		TestClass junitTestClass, Object target, Description description) {

		List<TestRule> testRules = junitTestClass.getAnnotatedMethodValues(
			target, ruleClass, TestRule.class);

		testRules.addAll(
			junitTestClass.getAnnotatedFieldValues(
				target, ruleClass, TestRule.class));

		if (!testRules.isEmpty()) {
			statement = new RunRules(statement, testRules, description);
		}

		return statement;
	}

	private Statement _withTimeout(Method method, Statement statement) {
		Test test = method.getAnnotation(Test.class);

		if ((test == null) || (test.timeout() <= 0)) {
			return statement;
		}

		FailOnTimeout.Builder builder = FailOnTimeout.builder();

		builder.withTimeout(test.timeout(), TimeUnit.MILLISECONDS);

		return builder.build(statement);
	}

	private static final Logger _logger = Logger.getLogger(
		TestExecutorRunnable.class.getName());

	private final Bundle _bundle;
	private final Map<String, List<String>> _filteredMethodNamesMap;
	private final long _passCode;
	private final String _reportServerHostName;
	private final int _reportServerPort;

}