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

	@Override
	protected Statement withAfters(
		FrameworkMethod method, Object testInstance, Statement statement) {

		Statement junitAfterClasses = super.withAfters(
			method, testInstance, statement);

		return new RunAfterTestMethodCallback(
			testInstance, method.getMethod(), junitAfterClasses,
				_testContextHandler);

	}

	@Override
	protected Statement withBefores(
			FrameworkMethod method, Object testInstance, Statement statement) {

		Statement junitBeforeClasses = super.withBefores(
				method, testInstance, statement);

		return new RunBeforeTestMethodCallback(
			testInstance, method.getMethod(), junitBeforeClasses,
				_testContextHandler);
	}

	private TestContextHandler _testContextHandler;

}