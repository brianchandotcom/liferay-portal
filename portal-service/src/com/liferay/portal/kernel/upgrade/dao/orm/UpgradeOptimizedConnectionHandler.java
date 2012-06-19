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

package com.liferay.portal.kernel.upgrade.dao.orm;

import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Minhchau Dang
 */
public class UpgradeOptimizedConnectionHandler implements InvocationHandler {

	public UpgradeOptimizedConnectionHandler(Connection connection)
		throws SQLException {

		_connection = connection;
	}

	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		try {
			String methodName = method.getName();

			if (methodName.equals("prepareStatement") &&
				(arguments.length == 1)) {

				return prepareStatement((String) arguments[0]);
			}

			return method.invoke(_connection, arguments);
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	protected PreparedStatement prepareStatement(String sql)
		throws SQLException {

		PreparedStatement statement =
			_connection.prepareStatement(
				sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		return (PreparedStatement) ProxyUtil.newProxyInstance(
			Thread.currentThread().getContextClassLoader(),
			new Class[] { PreparedStatement.class },
			new UpgradeOptimizedPreparedStatementHandler(statement));
	}

	private Connection _connection;

}