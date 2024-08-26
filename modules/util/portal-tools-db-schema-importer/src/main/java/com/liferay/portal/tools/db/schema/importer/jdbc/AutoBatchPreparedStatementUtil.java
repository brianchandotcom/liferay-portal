/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.schema.importer.jdbc;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Mariano Álvaro Sáiz
 */
public class AutoBatchPreparedStatementUtil {

	public static PreparedStatement concurrentAutoBatch(
			Connection connection, String sql)
		throws SQLException {

		return (PreparedStatement)Proxy.newProxyInstance(
			ClassLoader.getSystemClassLoader(),
			new Class<?>[] {PreparedStatement.class},
			new ConcurrentBatchInvocationHandler(connection, sql));
	}

	public static void start() throws Exception {
		_executorService = Executors.newFixedThreadPool(10);
	}

	public static void stop() throws Exception {
		_executorService.shutdownNow();

		_executorService.awaitTermination(10, TimeUnit.SECONDS);
	}

	private static final int _BATCH_SIZE = 2500;

	private static final Log _log = LogFactoryUtil.getLog(
		AutoBatchPreparedStatementUtil.class);

	private static ExecutorService _executorService;

	private static class ConcurrentBatchInvocationHandler
		implements InvocationHandler {

		public ConcurrentBatchInvocationHandler(
			Connection connection, String sql) {

			_connection = connection;
			_sql = sql;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (Objects.equals(method.getName(), "getConnection")) {
				return _connection;
			}

			if (Objects.equals(method.getName(), "close")) {
				_close();

				return null;
			}

			if (Objects.equals(method.getName(), "addBatch")) {
				_addBatch();

				return null;
			}

			if (Objects.equals(method.getName(), "executeBatch")) {
				return _executeBatch();
			}

			return method.invoke(getPreparedStatement(), args);
		}

		protected PreparedStatement getPreparedStatement() throws SQLException {
			if (preparedStatement == null) {
				preparedStatement = _connection.prepareStatement(_sql);
			}

			return preparedStatement;
		}

		protected PreparedStatement preparedStatement;

		private void _addBatch() throws SQLException {
			PreparedStatement localPreparedStatement = getPreparedStatement();

			localPreparedStatement.addBatch();

			if (++_count >= _BATCH_SIZE) {
				_count = 0;

				_executeAsync(() -> _executeBatch(localPreparedStatement));
			}
		}

		private void _cleanUp(Statement statement) {
			try {
				if (statement != null) {
					statement.close();
				}
			}
			catch (SQLException sqlException) {
				_log.error(sqlException);
			}
		}

		private void _close() throws Throwable {
			for (Future<?> future : _futures) {
				try {
					future.get();
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}

			_cleanUp(preparedStatement);

			preparedStatement = null;
		}

		private void _executeAsync(Runnable runnable) {
			_futures.add(_executorService.submit(runnable));

			preparedStatement = null;
		}

		private int[] _executeBatch() throws SQLException {
			if (_count > 0) {
				_count = 0;

				PreparedStatement localPreparedStatement =
					getPreparedStatement();

				_executeAsync(() -> _executeBatch(localPreparedStatement));
			}

			return new int[0];
		}

		private void _executeBatch(PreparedStatement preparedStatement) {
			try {
				preparedStatement.executeBatch();
			}
			catch (Exception exception) {
				_log.error(exception);

				throw new RuntimeException(exception);
			}
		}

		private final Connection _connection;
		private int _count;
		private final Set<Future<?>> _futures = Collections.newSetFromMap(
			new ConcurrentHashMap<>());
		private final String _sql;

	}

}