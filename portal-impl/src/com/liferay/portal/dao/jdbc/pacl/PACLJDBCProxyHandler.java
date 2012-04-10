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

package com.liferay.portal.dao.jdbc.pacl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.security.pacl.PACLClassUtil;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLPolicyManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Tomas Polesovsky
 */
public class PACLJDBCProxyHandler implements InvocationHandler {

	public PACLJDBCProxyHandler(Object jdbcObject, PACLPolicy paclPolicy) {
		_jdbcObject = jdbcObject;
		_paclPolicy = paclPolicy;
		for(Class jdbcClass : JDBC_SENSITIVE_METHODS.keySet()){
			if(jdbcClass.isAssignableFrom(jdbcObject.getClass())){
				_sensitiveMethods = JDBC_SENSITIVE_METHODS.get(jdbcClass);
			}
		}
	}

	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		try {
			if(arguments != null &&
				arguments.length > 0 &&
				(arguments[0] instanceof String) &&
				isSensitive(method)){

				String sql = (String) arguments[0];
				if (!_paclPolicy.hasSQLStatement(sql)) {
					throw new SecurityException(
							"Attempted to execute SQL: " + sql);
				}

			}

			Object result = method.invoke(_jdbcObject, arguments);

			return wrapResult(result);
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	protected boolean isSensitive(Method method) {
		if(method != null && _sensitiveMethods != null){

			for(String sensitiveMethod : _sensitiveMethods){
				if(method.getName().startsWith(sensitiveMethod)){
					return true;
				}
			}
		}
		return false;
	}

	protected Object wrapResult(Object result){
		if(result == null){
			return result;
		}
		for(Class jdbcClass : WRAPPED_CLASSES){
			if(jdbcClass.isAssignableFrom(result.getClass())){
				return ProxyUtil.newProxyInstance(
						_paclPolicy.getClassLoader(), new Class<?>[]{jdbcClass},
						new PACLJDBCProxyHandler(result, _paclPolicy));
			}
		}
		return result;
	}

	private static Map<Class, String[]> JDBC_SENSITIVE_METHODS = new HashMap<Class, String[]>();
	static {
		JDBC_SENSITIVE_METHODS.put(Connection.class, new String[]{
				"prepare",
				"nativeSQL"
		});
		JDBC_SENSITIVE_METHODS.put(Statement.class, new String[]{
				"addBatch",
				"execute"
		});
	}
	private static Class[] WRAPPED_CLASSES = new Class[]{
		PreparedStatement.class,
		CallableStatement.class,
		Connection.class,
		ResultSet.class,
		DatabaseMetaData.class};

	private static Log _log = LogFactoryUtil.getLog(PACLJDBCProxyHandler.class.getName());

	private Object _jdbcObject;
	private PACLPolicy _paclPolicy;
	private String[] _sensitiveMethods;
}