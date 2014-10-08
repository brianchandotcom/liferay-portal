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

import com.liferay.portal.kernel.util.ArrayUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.Statement;

/**
 * @author Shuyang Zhou
 */
public class ResetDatabaseStatementHandler implements InvocationHandler {

	public ResetDatabaseStatementHandler(
		Connection connection, Statement statement) {

		_connection = connection;
		_statement = statement;
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
				else {
					return false;
				}
			}

			if (methodName.equals("hashCode")) {
				return System.identityHashCode(proxy);
			}

			if (methodName.equals("addBatch") || methodName.equals("execute") ||
				methodName.equals("executeQuery") ||
				methodName.equals("executeUpdate")) {

				if (ArrayUtil.isNotEmpty(arguments)) {
					ResetDatabaseUtil.processSQL(
						_connection, (String)arguments[0]);
				}
			}

			return method.invoke(_statement, arguments);
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	private final Connection _connection;
	private final Statement _statement;

}