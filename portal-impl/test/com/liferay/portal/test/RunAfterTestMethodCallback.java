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

import java.util.ArrayList;
import java.util.List;

import org.junit.internal.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

/**
 * @author Miguel Pastor
 */
public class RunAfterTestMethodCallback extends Statement {

	public RunAfterTestMethodCallback(
			Object testInstance, Method method, Statement next,
			TestContextHandler testContextHandler) {

		_method = method;
		_next = next;
		_testContextHandler = testContextHandler;
		_testInstance = testInstance;
	}

	@Override
	public void evaluate() throws Throwable {
		List<Throwable> errors = new ArrayList<Throwable>();

		if (_next != null) {
			try {
				_next.evaluate();
			}
			catch (Throwable t) {
				errors.add(t);
			}

		}

		try {
			_testContextHandler.runAfterTestMethod(_testInstance, _method);
		}
		catch (Exception e) {
			errors.add(e);
		}

		if (!errors.isEmpty()) {
			throw new MultipleFailureException(errors);
		}

	}

	private Method _method;
	private Statement _next;
	private TestContextHandler _testContextHandler;
	private Object _testInstance;

}