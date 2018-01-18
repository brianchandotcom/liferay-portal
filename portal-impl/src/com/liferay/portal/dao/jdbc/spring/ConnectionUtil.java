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

package com.liferay.portal.dao.jdbc.spring;

import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @author Shuyang Zhou
 */
public class ConnectionUtil {

	public static Connection getConnection(DataSource dataSource)
		throws SQLException {

		Connection connection = CurrentConnectionUtil.getConnection(dataSource);

		if (connection != null) {
			return (Connection)ProxyUtil.newProxyInstance(
				ClassLoader.getSystemClassLoader(), _INTERFACES,
				new UncloseableInvocationHandler(connection));
		}

		return dataSource.getConnection();
	}

	private static final Class<?>[] _INTERFACES = {Connection.class};

	private static final Method _closeMethod;

	static {
		try {
			_closeMethod = Connection.class.getMethod("close");
		}
		catch (ReflectiveOperationException roe) {
			throw new ExceptionInInitializerError(roe);
		}
	}

	private static class UncloseableInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (method.equals(_closeMethod)) {
				return null;
			}

			return method.invoke(_connection, args);
		}

		private UncloseableInvocationHandler(Connection connection) {
			_connection = connection;
		}

		private final Connection _connection;

	}

}