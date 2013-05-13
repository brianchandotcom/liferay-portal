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

import com.liferay.portal.security.pacl.PACLPolicy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.Statement;

/**
 * @author Brian Wing Shun Chan
 */
public class PACLStatementHandler implements InvocationHandler {

	public PACLStatementHandler(Statement statement, PACLPolicy paclPolicy) {
		_statement = statement;
		_paclPolicy = paclPolicy;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		try {
			String methodName = method.getName();

			if (methodName.equals("addBatch") || methodName.equals("execute") ||
				methodName.equals("executeQuery") ||
				methodName.equals("executeUpdate")) {

				if ((arguments != null) && (arguments.length > 0)) {
					String sql = (String)arguments[0];

					if (!_paclPolicy.hasSQL(sql)) {
						throw new SecurityException(
							"Attempted to execute unapproved SQL " + sql);
					}
				}
			}
			else if (methodName.equals("equals")) {
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

			return method.invoke(_statement, arguments);
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	private PACLPolicy _paclPolicy;
	private Statement _statement;

}