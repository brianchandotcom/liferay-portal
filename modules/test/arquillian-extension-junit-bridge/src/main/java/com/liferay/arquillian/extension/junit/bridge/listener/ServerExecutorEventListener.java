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

package com.liferay.arquillian.extension.junit.bridge.listener;

import com.liferay.arquillian.extension.junit.bridge.event.Event;
import com.liferay.arquillian.extension.junit.bridge.event.TestEvent;
import com.liferay.arquillian.extension.junit.bridge.junit.State;
import com.liferay.arquillian.extension.junit.bridge.remote.manager.Registry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jboss.arquillian.test.spi.TestResult;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
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
public class ServerExecutorEventListener implements EventListener {

	public ServerExecutorEventListener(Registry registry) {
		_registry = registry;
	}

	@Override
	public void handleEvent(Event event) throws Throwable {
		if (event instanceof TestEvent) {
			_handleTestEvent((TestEvent)event);
		}
	}

	protected void evaluateWithClassRule(
			Statement statement, TestClass junitTestClass, Object target,
			Description description, boolean firstMethod, boolean lastMethod)
		throws Throwable {

		if (!firstMethod && !lastMethod) {
			statement.evaluate();

			return;
		}

		List<TestRule> testRules = junitTestClass.getAnnotatedMethodValues(
			target, ClassRule.class, TestRule.class);

		testRules.addAll(
			junitTestClass.getAnnotatedFieldValues(
				target, ClassRule.class, TestRule.class));

		if (testRules.isEmpty()) {
			statement.evaluate();

			return;
		}

		handleClassRules(testRules, firstMethod, lastMethod, true);

		statement = new RunRules(statement, testRules, description);

		try {
			statement.evaluate();
		}
		finally {
			handleClassRules(testRules, firstMethod, lastMethod, false);
		}
	}

	protected void handleClassRules(
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

	protected Statement withAfters(
		Statement statement, Class<? extends Annotation> afterClass,
		TestClass junitTestClass, Object target) {

		List<FrameworkMethod> frameworkMethods =
			junitTestClass.getAnnotatedMethods(afterClass);

		if (!frameworkMethods.isEmpty()) {
			statement = new RunAfters(statement, frameworkMethods, target);
		}

		return statement;
	}

	protected Statement withBefores(
		Statement statement, Class<? extends Annotation> beforeClass,
		TestClass junitTestClass, Object target) {

		List<FrameworkMethod> frameworkMethods =
			junitTestClass.getAnnotatedMethods(beforeClass);

		if (!frameworkMethods.isEmpty()) {
			statement = new RunBefores(statement, frameworkMethods, target);
		}

		return statement;
	}

	protected Statement withRules(
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

	private void _handleTestEvent(TestEvent testEvent) throws Throwable {
		Object target = testEvent.getTarget();
		Method method = testEvent.getMethod();

		Class<?> clazz = target.getClass();

		Statement statement = new InvokeMethod(null, target) {

			@Override
			public void evaluate() {
				TestResult testResult = TestResult.passed();

				Thread currentThread = Thread.currentThread();

				ClassLoader classLoader = currentThread.getContextClassLoader();

				currentThread.setContextClassLoader(clazz.getClassLoader());

				try {
					testEvent.invoke();
				}
				catch (Throwable t) {
					State.caughtTestException(t);

					testResult = TestResult.failed(t);
				}
				finally {
					testResult.setEnd(System.currentTimeMillis());

					currentThread.setContextClassLoader(classLoader);
				}

				_registry.set(TestResult.class, testResult);
			}

		};

		TestClass testClass = new TestClass(clazz);

		statement = withBefores(statement, Before.class, testClass, target);

		statement = withAfters(statement, After.class, testClass, target);

		statement = withRules(
			statement, Rule.class, testClass, target,
			Description.createTestDescription(
				clazz, method.getName(), method.getAnnotations()));

		List<FrameworkMethod> frameworkMethods = new ArrayList<>(
			testClass.getAnnotatedMethods(Test.class));

		frameworkMethods.removeAll(testClass.getAnnotatedMethods(Ignore.class));

		frameworkMethods.sort(Comparator.comparing(FrameworkMethod::getName));

		FrameworkMethod firstFrameworkMethod = frameworkMethods.get(0);

		boolean firstMethod = false;

		if (method.equals(firstFrameworkMethod.getMethod())) {
			firstMethod = true;

			statement = withBefores(
				statement, BeforeClass.class, testClass, null);
		}

		FrameworkMethod lastFrameworkMethod = frameworkMethods.get(
			frameworkMethods.size() - 1);

		boolean lastMethod = false;

		if (method.equals(lastFrameworkMethod.getMethod())) {
			lastMethod = true;

			statement = withAfters(
				statement, AfterClass.class, testClass, null);
		}

		evaluateWithClassRule(
			statement, testClass, target,
			Description.createSuiteDescription(clazz), firstMethod, lastMethod);
	}

	private final Registry _registry;

}