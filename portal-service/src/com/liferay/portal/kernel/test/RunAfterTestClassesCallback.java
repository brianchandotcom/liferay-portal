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

package com.liferay.portal.kernel.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.internal.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

/**
 * @author Miguel Pastor
 */
public class RunAfterTestClassesCallback extends AbstractStatementCallback {

	public RunAfterTestClassesCallback(
		Statement statement, TestContextHandler testContextHandler) {

		super(statement, testContextHandler);
	}

	@Override
	public void evaluate() throws Throwable {
		List<Throwable> throwables = new ArrayList<Throwable>();

		Statement statement = getStatement();

		if ( statement != null) {
			try {
				statement.evaluate();
			}
			catch (Throwable t) {
				throwables.add(t);
			}
		}

		try {
			TestContextHandler testContextHandler = getTestContextHandler();

			testContextHandler.runAfterTestClasses();
		}
		catch (Exception e) {
			throwables.add(e);
		}

		if (!throwables.isEmpty()) {
			throw new MultipleFailureException(throwables);
		}
	}

}