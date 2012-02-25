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

import java.lang.reflect.Method;

import org.junit.runners.model.Statement;

/**
 * @author Miguel Pastor
 */
public class RunBeforeTestMethodCallback extends Statement {

	public RunBeforeTestMethodCallback(
		Object instance, Method method, Statement statement,
		TestContextHandler testContextHandler) {

		_instance = instance;
		_method = method;
		_statement = statement;
		_testContextHandler = testContextHandler;
	}

	@Override
	public void evaluate() throws Throwable {
		_testContextHandler.runBeforeTestMethod(_instance, _method);

		if (_statement != null) {
			_statement.evaluate();
		}

	}

	private Object _instance;
	private Method _method;
	private Statement _statement;
	private TestContextHandler _testContextHandler;

}