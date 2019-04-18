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

package com.liferay.arquillian.extension.junit.bridge.server;

import com.liferay.arquillian.extension.junit.bridge.command.KillCommand;
import com.liferay.arquillian.extension.junit.bridge.command.RunNotifierCommand;

import java.io.EOFException;
import java.io.IOException;
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
		Bundle bundle, List<String> filterMethodNames,
		String reportServerHostName, int reportServerPort, long passCode) {

		_bundle = bundle;
		_filterMethodNames = filterMethodNames;
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

				objectOutputStream.writeObject(KillCommand.INSTANCE);

				objectOutputStream.flush();
			}
		}
		catch (EOFException eofe) {
		}
		catch (Exception e) {
			try {
				_bundle.uninstall();
			}
			catch (BundleException be) {
				e.addSuppressed(be);
			}

			_logger.log(
				Level.SEVERE,
				"Unable to report back to client. Uninstalled test bundle " +
					"and abort test.",
				e);
		}
	}

	private static Statement _classBlock(
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
					catch (Throwable t) {
						_processThrowable(
							false, t, objectOutputStream, description);
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

	private static void _execute(
			TestClass testClass, ObjectOutputStream objectOutputStream)
		throws IOException {

		Description description = Description.createSuiteDescription(
			testClass.getJavaClass());

		try {
			Statement statement = _classBlock(
				testClass, description, objectOutputStream);

			statement.evaluate();
		}
		catch (Throwable t) {
			_processThrowable(true, t, objectOutputStream, description);
		}
	}

	private static Statement _methodBlock(TestClass testClass, Method method)
		throws Throwable {

		Class<?> clazz = testClass.getJavaClass();

		Object target = clazz.newInstance();

		Statement statement = new InvokeMethod(null, target) {

			@Override
			public void evaluate() throws Throwable {
				Thread currentThread = Thread.currentThread();

				ClassLoader classLoader = currentThread.getContextClassLoader();

				currentThread.setContextClassLoader(clazz.getClassLoader());

				try {
					method.invoke(target);
				}
				catch (Throwable t) {
					if (t instanceof InvocationTargetException) {
						t = t.getCause();
					}

					if (t instanceof AssumptionViolatedException) {
						throw t;
					}

					_processExpectedException(t, method);
				}
				finally {
					currentThread.setContextClassLoader(classLoader);
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

	private static void _processExpectedException(
			Throwable throwable, Method method)
		throws Throwable {

		Test test = method.getAnnotation(Test.class);

		if (test == null) {
			throw throwable;
		}

		Class<?> expected = test.expected();

		if (test.expected() == Test.None.class) {
			throw throwable;
		}

		Class<?> clazz = throwable.getClass();

		if (!expected.isAssignableFrom(clazz)) {
			String message =
				"Unexpected exception, expected<" + expected.getName() +
					"> but was<" + clazz.getName() + ">";

			throw new Exception(message, throwable);
		}
	}

	private static void _processThrowable(
			boolean classLevel, Throwable throwable,
			ObjectOutputStream objectOutputStream, Description description)
		throws IOException {

		if (throwable instanceof AssumptionViolatedException) {
			if (classLevel) {
				objectOutputStream.writeObject(
					RunNotifierCommand.testStarted(description));
			}

			// To neutralize the nonserializable Matcher field inside
			// AssumptionViolatedException

			AssumptionViolatedException ave = new AssumptionViolatedException(
				throwable.getMessage());

			ave.setStackTrace(throwable.getStackTrace());

			objectOutputStream.writeObject(
				RunNotifierCommand.assumptionFailed(description, ave));

			if (classLevel) {
				objectOutputStream.writeObject(
					RunNotifierCommand.testFinished(description));
			}
		}
		else if (throwable instanceof MultipleFailureException) {
			MultipleFailureException mfe = (MultipleFailureException)throwable;

			for (Throwable t : mfe.getFailures()) {
				objectOutputStream.writeObject(
					RunNotifierCommand.testFailure(description, t));
			}
		}
		else {
			objectOutputStream.writeObject(
				RunNotifierCommand.testFailure(description, throwable));
		}

		objectOutputStream.flush();
	}

	private static Statement _withAfters(
		Statement statement, Class<? extends Annotation> afterClass,
		TestClass junitTestClass, Object target) {

		List<FrameworkMethod> frameworkMethods =
			junitTestClass.getAnnotatedMethods(afterClass);

		if (!frameworkMethods.isEmpty()) {
			statement = new RunAfters(statement, frameworkMethods, target);
		}

		return statement;
	}

	private static Statement _withBefores(
		Statement statement, Class<? extends Annotation> beforeClass,
		TestClass junitTestClass, Object target) {

		List<FrameworkMethod> frameworkMethods =
			junitTestClass.getAnnotatedMethods(beforeClass);

		if (!frameworkMethods.isEmpty()) {
			statement = new RunBefores(statement, frameworkMethods, target);
		}

		return statement;
	}

	private static Statement _withRules(
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

	private static Statement _withTimeout(Method method, Statement statement) {
		Test test = method.getAnnotation(Test.class);

		if ((test == null) || (test.timeout() <= 0)) {
			return statement;
		}

		FailOnTimeout.Builder builder = FailOnTimeout.builder();

		builder.withTimeout(test.timeout(), TimeUnit.MILLISECONDS);

		return builder.build(statement);
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

				testFrameworkMethods.removeIf(
					frameworkMethod -> _filterMethodNames.contains(
						frameworkMethod.getName()));

				testFrameworkMethods.sort(
					Comparator.comparing(FrameworkMethod::getName));
			}

		};
	}

	private static final Logger _logger = Logger.getLogger(
		TestExecutorRunnable.class.getName());

	private final Bundle _bundle;
	private final List<String> _filterMethodNames;
	private final long _passCode;
	private final String _reportServerHostName;
	private final int _reportServerPort;

}