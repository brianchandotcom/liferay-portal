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

package com.liferay.portal.security.pacl.dao.jdbc;

import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.security.lang.DoPrivilegedFactory;
import com.liferay.portal.security.pacl.PACLPolicy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PACLConnectionHandler implements InvocationHandler {

	public PACLConnectionHandler(Connection connection, PACLPolicy paclPolicy) {
		_connection = connection;
		_paclPolicy = paclPolicy;
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
			else if (methodName.equals("hashCode")) {
				return System.identityHashCode(proxy);
			}
			else if (methodName.equals("prepareCall") ||
					 methodName.equals("prepareStatement")) {

				String sql = (String)arguments[0];

				if (!_paclPolicy.hasSQL(sql)) {
					throw new SecurityException(
						"Attempted to execute unapproved SQL " + sql);
				}
			}

			Object returnValue = method.invoke(_connection, arguments);

			if (methodName.equals("createStatement") ||
				methodName.equals("prepareCall") ||
				methodName.equals("prepareStatement")) {

				Statement statement = (Statement)returnValue;

				statement = DoPrivilegedFactory.wrap(statement);

				return AccessController.doPrivileged(
					new StatementPrivilegedAction(statement, returnValue));
			}

			return returnValue;
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	protected Class<?>[] getInterfaces(Class<?> returnType) {
		List<Class<?>> interfaceClasses = new ArrayList<Class<?>>();

		interfaceClasses.add(Statement.class);

		if (!CallableStatement.class.isAssignableFrom(returnType)) {
			interfaceClasses.add(CallableStatement.class);
		}
		else if (!PreparedStatement.class.isAssignableFrom(returnType)) {
			interfaceClasses.add(PreparedStatement.class);
		}

		return interfaceClasses.toArray(new Class<?>[interfaceClasses.size()]);
	}

	private final Connection _connection;
	private final PACLPolicy _paclPolicy;

	private class StatementPrivilegedAction
		implements PrivilegedAction<Statement> {

		public StatementPrivilegedAction(
			Statement statement, Object returnValue) {

			_statement = statement;
			_returnValue = returnValue;
		}

		@Override
		public Statement run() {
			return (Statement)ProxyUtil.newProxyInstance(
				_paclPolicy.getClassLoader(),
				getInterfaces(_returnValue.getClass()),
				new PACLStatementHandler(_statement, _paclPolicy));
		}

		private final Object _returnValue;
		private final Statement _statement;

	}

}