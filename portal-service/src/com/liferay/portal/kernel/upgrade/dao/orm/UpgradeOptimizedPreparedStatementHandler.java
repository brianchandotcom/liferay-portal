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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Minhchau Dang
 */
public class UpgradeOptimizedPreparedStatementHandler
	implements InvocationHandler {

	public UpgradeOptimizedPreparedStatementHandler(
		PreparedStatement statement) {

		_statement = statement;
	}

	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		try {
			String methodName = method.getName();

			if (methodName.equals("executeQuery")) {
				return executeQuery();
			}

			return method.invoke(_statement, arguments);
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	protected ResultSet executeQuery() throws SQLException {
		ResultSet resultSet = _statement.executeQuery();

		return (ResultSet) ProxyUtil.newProxyInstance(
			Thread.currentThread().getContextClassLoader(),
			new Class[] { ResultSet.class },
			new UpgradeOptimizedResultSetHandler(resultSet));
	}

	private PreparedStatement _statement;

}