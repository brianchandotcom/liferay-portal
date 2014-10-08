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

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

/**
 * @author Miguel Pastor
 */
public class RunAfterTestMethodCallback extends AbstractStatementCallback {

	public RunAfterTestMethodCallback(
		Object instance, Method method, Statement statement,
		TestContextHandler testContextHandler) {

		super(statement, testContextHandler);

		_instance = instance;
		_method = method;
	}

	@Override
	public void evaluate() throws Throwable {
		List<Throwable> throwables = new ArrayList<Throwable>();

		Statement statement = getStatement();

		if (statement != null) {
			try {
				statement.evaluate();
			}
			catch (Throwable t) {
				if (!(t instanceof AssumptionViolatedException)) {
					throwables.add(t);
				}
			}
		}

		try {
			TestContextHandler testContextHandler = getTestContextHandler();

			testContextHandler.runAfterTestMethod(_instance, _method);
		}
		catch (Exception e) {
			throwables.add(e);
		}

		if (!throwables.isEmpty()) {
			throw new MultipleFailureException(throwables);
		}
	}

	private final Object _instance;
	private final Method _method;

}