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

import com.liferay.arquillian.extension.junit.bridge.client.ClientExecutorStatement;
import com.liferay.arquillian.extension.junit.bridge.client.DeploymentStatement;

import java.lang.annotation.Annotation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.junit.AssumptionViolatedException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
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
public class Arquillian extends Runner implements Filterable {

	public Arquillian(Class<?> clazz) {
		_clazz = clazz;

		_testClass = new FilteredSortedTestClass(_clazz, null);
	}

	@Override
	public void filter(Filter filter) throws NoTestsRemainException {
		_testClass = new FilteredSortedTestClass(_clazz, filter);

		List<FrameworkMethod> frameworkMethods = _testClass.getAnnotatedMethods(
			Test.class);

		if (frameworkMethods.isEmpty()) {
			throw new NoTestsRemainException();
		}
	}

	@Override
	public Description getDescription() {
		return Description.createSuiteDescription(
			_clazz.getName(), _clazz.getAnnotations());
	}

	@Override
	public void run(RunNotifier runNotifier) {
		try {
			List<FrameworkMethod> frameworkMethods = new ArrayList<>(
				_testClass.getAnnotatedMethods(Test.class));

			frameworkMethods.removeIf(
				frameworkMethod -> {
					if (frameworkMethod.getAnnotation(Ignore.class) != null) {
						runNotifier.fireTestIgnored(
							Description.createTestDescription(
								_clazz, frameworkMethod.getName(),
								frameworkMethod.getAnnotations()));

						return true;
					}

					return false;
				});

			if (frameworkMethods.isEmpty()) {
				return;
			}

			Statement statement = new Statement() {

				@Override
				public void evaluate() {
					for (FrameworkMethod frameworkMethod : frameworkMethods) {
						_runMethod(frameworkMethod, runNotifier);
					}
				}

			};

			statement = new DeploymentStatement(statement);

			statement.evaluate();
		}
		catch (Throwable t) {
			runNotifier.fireTestFailure(new Failure(getDescription(), t));
		}
	}

	private void _runMethod(
		FrameworkMethod frameworkMethod, RunNotifier runNotifier) {

		Description description = Description.createTestDescription(
			_clazz, frameworkMethod.getName(),
			frameworkMethod.getAnnotations());

		runNotifier.fireTestStarted(description);

		try {
			Statement statement = new ClientExecutorStatement(
				_clazz.newInstance(), frameworkMethod.getMethod());

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

	private final Class<?> _clazz;
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
						Description.createTestDescription(
							_clazz, frameworkMethod.getName())));
			}
		}

		private List<FrameworkMethod> _testFrameworkMethods;

	}

}