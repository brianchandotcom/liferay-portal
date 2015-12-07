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

package com.liferay.portal.kernel.dao.jdbc;

import com.liferay.portal.kernel.jndi.JNDIUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.dao.orm.UpgradeOptimizedConnectionHandler;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 */
public class DataAccess {

	public static void cleanUp(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle.getMessage());
			}
		}
	}

	public static void cleanUp(Connection connection, Statement statement) {
		cleanUp(statement);

		cleanUp(connection);
	}

	public static void cleanUp(
		Connection connection, Statement statement, ResultSet resultSet) {

		cleanUp(resultSet);

		cleanUp(statement);

		cleanUp(connection);
	}

	public static void cleanUp(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle.getMessage());
			}
		}
	}

	public static void cleanUp(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle.getMessage());
			}
		}
	}

	public static void cleanUp(Statement statement, ResultSet resultSet) {
		cleanUp(resultSet);

		cleanUp(statement);
	}

	public static void deepCleanUp(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				Statement statement = resultSet.getStatement();

				Connection connection = statement.getConnection();

				cleanUp(connection, statement, resultSet);
			}
		}
		catch (SQLException sqle) {
			if (_log.isWarnEnabled()) {
				_log.warn(sqle.getMessage());
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		DataSource dataSource = _pacl.getDataSource();

		return dataSource.getConnection();
	}

	public static Connection getConnection(String location)
		throws NamingException, SQLException {

		DataSource dataSource = _pacl.getDataSource(location);

		return dataSource.getConnection();
	}

	public static Connection getUpgradeOptimizedConnection()
		throws SQLException {

		Connection connection = getConnection();

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		return (Connection)ProxyUtil.newProxyInstance(
			classLoader, new Class[] {Connection.class},
			new UpgradeOptimizedConnectionHandler(connection));
	}

	public interface PACL {

		public DataSource getDataSource();

		public DataSource getDataSource(String location) throws NamingException;

	}

	private static final Log _log = LogFactoryUtil.getLog(DataAccess.class);

	private static final PACL _pacl = new NoPACL();

	private static class NoPACL implements PACL {

		@Override
		public DataSource getDataSource() {
			return InfrastructureUtil.getDataSource();
		}

		@Override
		public DataSource getDataSource(String location)
			throws NamingException {

			Properties properties = PropsUtil.getProperties(
				PropsKeys.JNDI_ENVIRONMENT, true);

			Context context = new InitialContext(properties);

			return (DataSource)JNDIUtil.lookup(context, location);
		}

	}

}