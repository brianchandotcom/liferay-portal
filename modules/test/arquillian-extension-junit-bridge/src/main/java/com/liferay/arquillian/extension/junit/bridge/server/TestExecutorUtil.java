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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * @author Shuyang Zhou
 */
public class TestExecutorUtil {

	public static void execute(TestClass testClass, Method method)
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

					_processThrowable(t, method);
				}
				finally {
					currentThread.setContextClassLoader(classLoader);
				}
			}

		};

		statement = _withBefores(statement, Before.class, testClass, target);

		statement = _withAfters(statement, After.class, testClass, target);

		statement = _withRules(
			statement, testClass, target,
			Description.createTestDescription(
				clazz, method.getName(), method.getAnnotations()));

		List<FrameworkMethod> frameworkMethods = new ArrayList<>(
			testClass.getAnnotatedMethods(Test.class));

		FrameworkMethod firstFrameworkMethod = frameworkMethods.get(0);

		boolean firstMethod = false;

		if (method.equals(firstFrameworkMethod.getMethod())) {
			firstMethod = true;

			statement = _withBefores(
				statement, BeforeClass.class, testClass, null);
		}

		FrameworkMethod lastFrameworkMethod = frameworkMethods.get(
			frameworkMethods.size() - 1);

		boolean lastMethod = false;

		if (method.equals(lastFrameworkMethod.getMethod())) {
			lastMethod = true;

			statement = _withAfters(
				statement, AfterClass.class, testClass, null);
		}

		statement = _withClassRule(
			statement, testClass, target,
			Description.createSuiteDescription(clazz), firstMethod, lastMethod);

		statement = _withTimeout(method, statement);

		statement.evaluate();
	}

	private static void _handleClassRules(
		List<TestRule> testRules, boolean firstMethod, boolean lastMethod,
		boolean enable) {

		for (TestRule testRule : testRules) {
			Class<?> testRuleClass = testRule.getClass();

			if (firstMethod) {
				try {
					Method handleBeforeClassMethod = testRuleClass.getMethod(
						"handleBeforeClass", boolean.class);

					handleBeforeClassMethod.invoke(testRule, enable);
				}
				catch (ReflectiveOperationException roe) {
					continue;
				}
			}

			if (lastMethod) {
				try {
					Method handleAfterClassMethod = testRuleClass.getMethod(
						"handleAfterClass", boolean.class);

					handleAfterClassMethod.invoke(testRule, enable);
				}
				catch (ReflectiveOperationException roe) {
					continue;
				}
			}
		}
	}

	private static void _processThrowable(Throwable throwable, Method method)
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

	private static Statement _withClassRule(
			Statement statement, TestClass junitTestClass, Object target,
			Description description, boolean firstMethod, boolean lastMethod)
		throws Throwable {

		if (!firstMethod && !lastMethod) {
			return statement;
		}

		List<TestRule> testRules = junitTestClass.getAnnotatedMethodValues(
			target, ClassRule.class, TestRule.class);

		testRules.addAll(
			junitTestClass.getAnnotatedFieldValues(
				target, ClassRule.class, TestRule.class));

		if (testRules.isEmpty()) {
			return statement;
		}

		return new InvokeMethod(null, target) {

			@Override
			public void evaluate() throws Throwable {
				_handleClassRules(testRules, firstMethod, lastMethod, true);

				Statement ruleStatement = new RunRules(
					statement, testRules, description);

				try {
					ruleStatement.evaluate();
				}
				finally {
					_handleClassRules(
						testRules, firstMethod, lastMethod, false);
				}
			}

		};
	}

	private static Statement _withRules(
		Statement statement, TestClass junitTestClass, Object target,
		Description description) {

		List<TestRule> testRules = junitTestClass.getAnnotatedMethodValues(
			target, Rule.class, TestRule.class);

		testRules.addAll(
			junitTestClass.getAnnotatedFieldValues(
				target, Rule.class, TestRule.class));

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

}