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

package com.liferay.portal.test.rule.callback;

import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.hsqldb.server.Server;

import org.junit.runner.Description;

/**
 * @author William Newbury
 */
public class HypersonicServerTestCallback
	extends BaseTestCallback<Object, Object> {

	public HypersonicServerTestCallback(String databaseName) {
		_databaseName = databaseName;

		_databaseURL = "jdbc:hsqldb:hsql://localhost/" + databaseName;
	}

	@Override
	public void doAfterClass(Description description, Object c)
		throws Throwable {

		if (_server != null) {
			try (Connection con = DriverManager.getConnection(
					_databaseURL, "sa", "")) {

				try (Statement statement = con.createStatement()) {
					statement.execute("SHUTDOWN COMPACT");
				}
			}

			_server.stop();
		}
	}

	@Override
	public Object doBeforeClass(Description description) throws Throwable {
		Class.forName("org.hsqldb.jdbcDriver");

		Server server = new Server();
		server.setDatabaseName(0, _databaseName);
		server.setDatabasePath(
			0, PropsValues.LIFERAY_HOME + "/data/hsql/" + _databaseName);
		server.setLogWriter(null);
		server.setErrWriter(null);

		if (server.start() == 16) {
			_server = server;
		}

		return null;
	}

	private final String _databaseName;
	private final String _databaseURL;
	private Server _server = null;

}