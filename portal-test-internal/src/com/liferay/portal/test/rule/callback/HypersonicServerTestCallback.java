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

import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.hsqldb.server.Server;

import org.junit.runner.Description;

/**
 * @author William Newbury
 */
public class HypersonicServerTestCallback
	extends BaseTestCallback<Server, Object> {

	public HypersonicServerTestCallback(String databaseName) {
		_databaseName = databaseName;
	}

	@Override
	public void doAfterClass(Description description, Server server)
		throws Throwable {

		if (server != null) {
			try (Connection connection = DriverManager.getConnection(
					"jdbc:hsqldb:hsql://localhost/".concat(databaseName), "sa",
					"");
				Statement statement = connection.createStatement()) {

				statement.execute("SHUTDOWN COMPACT");
			}

			server.stop();
		}
	}

	@Override
	public Server doBeforeClass(Description description) throws Throwable {
		Class.forName("org.hsqldb.jdbcDriver");

		Server server = new Server() {

			@Override
			public int stop() {
				try (PrintWriter logPrintWriter = getLogWriter();
					PrintWriter errPrintWriter = getErrWriter()) {

					return super.stop();
				}
			}

		};

		server.setDatabaseName(0, _databaseName);
		server.setDatabasePath(0, _HSQL_HOME.concat(_databaseName));

		File hsqlHome = new File(_HSQL_HOME);

		hsqlHome.mkdirs();

		server.setErrWriter(
			new UnsyncPrintWriter(
				new File(hsqlHome, _databaseName.concat(".err.log"))));
		server.setLogWriter(
			new UnsyncPrintWriter(
				new File(hsqlHome, _databaseName.concat(".std.log"))));

		if (server.start() == 16) {
			return server;
		}

		return null;
	}

	private static final String _HSQL_HOME = PropsValues.LIFERAY_HOME.concat(
		"/data/hsql/");

	private final String _databaseName;

}