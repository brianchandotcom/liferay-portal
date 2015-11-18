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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Shuyang Zhou
 */
public class AutoBatchPreparedStatementUtil {

	public static PreparedStatement autoBatch(
			PreparedStatement preparedStatement)
		throws SQLException {

		Connection connection = preparedStatement.getConnection();

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		if (databaseMetaData.supportsBatchUpdates()) {
			return (PreparedStatement)ProxyUtil.newProxyInstance(
				ClassLoader.getSystemClassLoader(), _interfaces,
				new BatchInvocationHandler(preparedStatement));
		}

		return (PreparedStatement)ProxyUtil.newProxyInstance(
			ClassLoader.getSystemClassLoader(), _interfaces,
			new NoBatchInvocationHandler(preparedStatement));
	}

	private AutoBatchPreparedStatementUtil() {
	}

	private static final Method _addBatchMethod;
	private static final Method _executeBatch;
	private static final Class<?>[] _interfaces =
		new Class<?>[] {PreparedStatement.class};

	static {
		try {
			_addBatchMethod = PreparedStatement.class.getMethod("addBatch");
			_executeBatch = PreparedStatement.class.getMethod("executeBatch");
		}
		catch (NoSuchMethodException nsme) {
			throw new ExceptionInInitializerError(nsme);
		}
	}

	private static class BatchInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (method.equals(_executeBatch)) {
				if (_count > 0) {
					_count = 0;

					return _preparedStatement.executeBatch();
				}

				return new int[0];
			}

			if (!method.equals(_addBatchMethod)) {
				return method.invoke(_preparedStatement, args);
			}

			_preparedStatement.addBatch();

			if (++_count >= PropsValues.HIBERNATE_JDBC_BATCH_SIZE) {
				_preparedStatement.executeBatch();

				_count = 0;
			}

			return null;
		}

		private BatchInvocationHandler(PreparedStatement preparedStatement) {
			_preparedStatement = preparedStatement;
		}

		private int _count;
		private final PreparedStatement _preparedStatement;

	}

	private static class NoBatchInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (method.equals(_addBatchMethod)) {
				_preparedStatement.executeUpdate();

				return null;
			}

			if (method.equals(_executeBatch)) {
				return new int[0];
			}

			return method.invoke(_preparedStatement, args);
		}

		private NoBatchInvocationHandler(PreparedStatement preparedStatement) {
			_preparedStatement = preparedStatement;
		}

		private final PreparedStatement _preparedStatement;

	}

}