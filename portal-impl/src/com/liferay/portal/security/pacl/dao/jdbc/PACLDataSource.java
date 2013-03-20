/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl.dao.jdbc;

import com.liferay.portal.dao.jdbc.util.DataSourceWrapper;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.security.lang.DoPrivilegedFactory;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLUtil;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 */
public class PACLDataSource extends DataSourceWrapper {

	public PACLDataSource(DataSource dataSource) {
		super(dataSource);

		_dataSource = dataSource;
	}

	@Override
	public Connection getConnection() throws SQLException {
		Connection connection = _dataSource.getConnection();

		PACLPolicy paclPolicy = PACLUtil.getPACLPolicy();

		if (paclPolicy == null) {
			return connection;
		}

		connection = DoPrivilegedFactory.wrap(connection);
		paclPolicy = DoPrivilegedFactory.wrap(paclPolicy);

		return AccessController.doPrivileged(
			new ConnectionPrivilegedAction(connection, paclPolicy));
	}

	private DataSource _dataSource;

	private class ConnectionPrivilegedAction
		implements PrivilegedAction<Connection> {

		public ConnectionPrivilegedAction(
			Connection connection, PACLPolicy paclPolicy) {

			_connection = connection;
			_paclPolicy = paclPolicy;
		}

		public Connection run() {
			return (Connection)ProxyUtil.newProxyInstance(
				_paclPolicy.getClassLoader(), new Class<?>[] {Connection.class},
				new PACLConnectionHandler(_connection, _paclPolicy));
		}

		private Connection _connection;
		private PACLPolicy _paclPolicy;

	}

}