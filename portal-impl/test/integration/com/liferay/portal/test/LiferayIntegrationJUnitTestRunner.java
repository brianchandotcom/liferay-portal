/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.test;

import com.liferay.portal.util.InitUtil;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * @author Miguel Pastor
 */
public class LiferayIntegrationJUnitTestRunner
	extends BlockJUnit4ClassRunner {

	public LiferayIntegrationJUnitTestRunner(Class<?> clazz)
		throws InitializationError {

		super(clazz);

		if (System.getProperty("external-properties") == null) {
			System.setProperty("external-properties", "portal-test.properties");
		}

		InitUtil.initWithSpring();

		_testContextHandler = new TestContextHandler(clazz);
	}

	/**
	 * @deprecated
	 */
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
	protected Statement withAfterClasses(Statement statement) {
		Statement withAfterClassesStatement = super.withAfterClasses(statement);

		return new RunAfterTestClassesCallback(
			withAfterClassesStatement, _testContextHandler);
	}

	/**
	 * @deprecated
	 */
	@Override
	protected Statement withBefores(
		FrameworkMethod frameworkMethod, Object instance, Statement statement) {

		Statement withBeforesStatement = super.withBefores(
			frameworkMethod, instance, statement);

		return new RunBeforeTestMethodCallback(
			instance, frameworkMethod.getMethod(), withBeforesStatement,
			_testContextHandler);
	}

	@Override
	protected Statement withBeforeClasses(Statement statement) {
		Statement withBeforeClassesStatement = super.withBeforeClasses(
			statement);

		return new RunBeforeTestClassesCallback(
			withBeforeClassesStatement, _testContextHandler);
	}

	private TestContextHandler _testContextHandler;

}