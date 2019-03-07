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

import com.liferay.arquillian.extension.junit.bridge.statement.ServerExecutorStatement;

import java.lang.annotation.Annotation;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.junit.AssumptionViolatedException;
import org.junit.Test;
import org.junit.internal.runners.statements.FailOnTimeout;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * @author Shuyang Zhou
 */
public class ServerRunner extends Runner implements Filterable {

	public ServerRunner(Class<?> clazz) {
		_clazz = clazz;
	}

	@Override
	public void filter(Filter filter) {
		_testClass = new FilteredSortedTestClass(_clazz, filter);
	}

	@Override
	public Description getDescription() {
		return Description.createSuiteDescription(
			_clazz.getName(), _clazz.getAnnotations());
	}

	@Override
	public void run(RunNotifier runNotifier) {
		Description description = getDescription();

		try {
			for (FrameworkMethod frameworkMethod :
					_testClass.getAnnotatedMethods(Test.class)) {

				_runMethod(frameworkMethod, runNotifier);
			}
		}
		catch (Throwable t) {
			runNotifier.fireTestFailure(new Failure(description, t));
		}
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

	private void _runMethod(
		FrameworkMethod frameworkMethod, RunNotifier runNotifier) {

		Description description = _describeChild(frameworkMethod);

		Statement statement = _withTimeout(
			frameworkMethod,
			new ServerExecutorStatement(_clazz, frameworkMethod.getMethod()));

		runNotifier.fireTestStarted(description);

		try {
			statement.evaluate();
		}
		catch (AssumptionViolatedException ave) {
			runNotifier.fireTestAssumptionFailed(new Failure(description, ave));
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

	private Statement _withTimeout(
		FrameworkMethod frameworkMethod, Statement statement) {

		Test test = frameworkMethod.getAnnotation(Test.class);

		if ((test == null) || (test.timeout() <= 0)) {
			return statement;
		}

		FailOnTimeout.Builder builder = FailOnTimeout.builder();

		builder.withTimeout(test.timeout(), TimeUnit.MILLISECONDS);

		return builder.build(statement);
	}

	private final Class<?> _clazz;
	private final Map<FrameworkMethod, Description> _methodDescriptions =
		new ConcurrentHashMap<>();
	private TestClass _testClass;

	private class FilteredSortedTestClass extends TestClass {

		@Override
		protected void scanAnnotatedMembers(
			Map<Class<? extends Annotation>, List<FrameworkMethod>>
				frameworkMethodsMap,
			Map<Class<? extends Annotation>, List<FrameworkField>>
				frameworkFieldsMap) {

			super.scanAnnotatedMembers(frameworkMethodsMap, frameworkFieldsMap);

			_testFrameworkMethods = frameworkMethodsMap.get(Test.class);

			_testFrameworkMethods.sort(
				Comparator.comparing(FrameworkMethod::getName));
		}

		private FilteredSortedTestClass(Class<?> clazz, Filter filter) {
			super(clazz);

			if (filter != null) {
				_testFrameworkMethods.removeIf(
					frameworkMethod -> !filter.shouldRun(
						_describeChild(frameworkMethod)));
			}
		}

		private List<FrameworkMethod> _testFrameworkMethods;

	}

}