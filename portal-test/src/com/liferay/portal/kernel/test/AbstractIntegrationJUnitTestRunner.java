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

package com.liferay.portal.kernel.test;

import org.junit.runner.manipulation.Sorter;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * @author Miguel Pastor
 */
public abstract class AbstractIntegrationJUnitTestRunner
	extends BlockJUnit4ClassRunner {

	public AbstractIntegrationJUnitTestRunner(Class<?> clazz)
		throws InitializationError {

		super(clazz);

		initApplicationContext();

		if (System.getProperty("external-properties") == null) {
			System.setProperty("external-properties", "portal-test.properties");
		}

		_testContextHandler = new TestContextHandler(clazz);

		sort(new Sorter(new DescriptionComparator()));
	}

	public abstract void initApplicationContext();

	@Override
	protected Statement withAfterClasses(Statement statement) {
		Statement withAfterClassesStatement = super.withAfterClasses(statement);

		return new RunAfterTestClassesCallback(
			withAfterClassesStatement, _testContextHandler);
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	@Override
	protected Statement withAfters(
		FrameworkMethod frameworkMethod, Object instance, Statement statement) {

		Statement withAftersStatement = super.withAfters(
			frameworkMethod, instance, statement);

		return new RunAfterTestMethodCallback(
			instance, frameworkMethod.getMethod(), withAftersStatement,
			_testContextHandler);
	}

	@Override
	protected Statement withBeforeClasses(Statement statement) {
		Statement withBeforeClassesStatement = super.withBeforeClasses(
			statement);

		return new RunBeforeTestClassesCallback(
			withBeforeClassesStatement, _testContextHandler);
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	@Override
	protected Statement withBefores(
		FrameworkMethod frameworkMethod, Object instance, Statement statement) {

		Statement withBeforesStatement = super.withBefores(
			frameworkMethod, instance, statement);

		return new RunBeforeTestMethodCallback(
			instance, frameworkMethod.getMethod(), withBeforesStatement,
			_testContextHandler);
	}

	private final TestContextHandler _testContextHandler;

}