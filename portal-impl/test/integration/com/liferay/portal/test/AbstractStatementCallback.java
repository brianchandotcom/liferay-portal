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

import org.junit.runners.model.Statement;

/**
 * @author Miguel Pastor
 */
public abstract class AbstractStatementCallback extends Statement {

	public AbstractStatementCallback(
			Statement statement, TestContextHandler testContextHandler) {

		_statement = statement;
		_testContextHandler = testContextHandler;
	}

	public Statement getStatement() {
		return _statement;
	}

	public TestContextHandler getTextContextHandler() {
		return _testContextHandler;
	}

	public abstract void evaluate() throws Throwable;

	private Statement _statement;
	private TestContextHandler _testContextHandler;

}