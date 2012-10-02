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

import org.junit.runners.model.Statement;

/**
 * @author Miguel Pastor
 */
public class RunBeforeTestClassesCallback extends AbstractStatementCallback {

	public RunBeforeTestClassesCallback(
		Statement statement, TestContextHandler testContextHandler) {

		super(statement, testContextHandler);
	}

	@Override
	public void evaluate() throws Throwable {
		TestContextHandler testContextHandler = getTestContextHandler();

		testContextHandler.runBeforeTestClasses();

		Statement statement = getStatement();

		if (statement != null) {
			statement.evaluate();
		}
	}

}