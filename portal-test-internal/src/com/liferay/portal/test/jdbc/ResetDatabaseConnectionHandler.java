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

package com.liferay.portal.test.jdbc;

import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author Shuyang Zhou
 */
public class ResetDatabaseConnectionHandler implements InvocationHandler {

	public ResetDatabaseConnectionHandler(Connection connection) {
		_connection = connection;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		try {
			String methodName = method.getName();

			if (methodName.equals("equals")) {
				if (proxy == arguments[0]) {
					return true;
				}

				return false;
			}

			if (methodName.equals("hashCode")) {
				return System.identityHashCode(proxy);
			}

			if (methodName.equals("prepareCall") ||
				methodName.equals("prepareStatement")) {

				ResetDatabaseUtil.processSQL(_connection, (String)arguments[0]);
			}

			Object returnValue = method.invoke(_connection, arguments);

			if (methodName.equals("createStatement") ||
				methodName.equals("prepareCall") ||
				methodName.equals("prepareStatement")) {

				Statement statement = (Statement)returnValue;

				return ProxyUtil.newProxyInstance(
					ResetDatabaseConnectionHandler.class.getClassLoader(),
					getInterfaces(statement),
					new ResetDatabaseStatementHandler(_connection, statement));
			}

			return returnValue;
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	protected Class<?>[] getInterfaces(Statement statement) {
		if (statement instanceof CallableStatement) {
			return new Class<?>[] {CallableStatement.class};
		}

		if (statement instanceof PreparedStatement) {
			return new Class<?>[] {PreparedStatement.class};
		}

		return new Class<?>[] {Statement.class};
	}

	private final Connection _connection;

}