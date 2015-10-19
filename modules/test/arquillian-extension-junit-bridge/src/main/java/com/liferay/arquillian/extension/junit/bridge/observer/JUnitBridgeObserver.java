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

package com.liferay.arquillian.extension.junit.bridge.observer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.List;

import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.EventContext;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.arquillian.test.spi.event.suite.Test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * @author Shuyang Zhou
 */
public class JUnitBridgeObserver {

	public void aroundTest(final @Observes EventContext<Test> eventContext)
		throws Throwable {

		Test test = eventContext.getEvent();

		Statement statement = new InvokeMethod(null, test.getTestInstance()) {

			@Override
			public void evaluate() {
				eventContext.proceed();
			}

		};

		TestClass arquillianTestClass = test.getTestClass();

		Class<?> clazz = arquillianTestClass.getJavaClass();

		org.junit.runners.model.TestClass junitTestClass =
			new org.junit.runners.model.TestClass(clazz);

		Object target = test.getTestInstance();

		statement = withBefores(
			statement, Before.class, junitTestClass, target);

		statement = withAfters(statement, After.class, junitTestClass, target);

		Method method = test.getTestMethod();

		statement = withRules(
			statement, Rule.class, junitTestClass, target,
			Description.createTestDescription(
				clazz, method.getName(), method.getAnnotations()));

		statement = withBefores(
			statement, BeforeClass.class, junitTestClass, null);

		statement = withAfters(
			statement, AfterClass.class, junitTestClass, null);

		statement = withRules(
			statement, ClassRule.class, junitTestClass, null,
			Description.createSuiteDescription(clazz));

		statement.evaluate();
	}

	protected Statement withAfters(
		Statement statement, Class<? extends Annotation> afterClass,
		org.junit.runners.model.TestClass junitTestClass, Object target) {

		List<FrameworkMethod> frameworkMethods =
			junitTestClass.getAnnotatedMethods(afterClass);

		if (!frameworkMethods.isEmpty()) {
			statement = new RunAfters(statement, frameworkMethods, target);
		}

		return statement;
	}

	protected Statement withBefores(
		Statement statement, Class<? extends Annotation> beforeClass,
		org.junit.runners.model.TestClass junitTestClass, Object target) {

		List<FrameworkMethod> frameworkMethods =
			junitTestClass.getAnnotatedMethods(beforeClass);

		if (!frameworkMethods.isEmpty()) {
			statement = new RunBefores(statement, frameworkMethods, target);
		}

		return statement;
	}

	protected Statement withRules(
		Statement statement, Class<? extends Annotation> ruleClass,
		org.junit.runners.model.TestClass junitTestClass, Object target,
		Description description) {

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

}