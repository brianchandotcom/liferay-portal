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

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.concurrent.FutureListener;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 * @author Preston Crary
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

	public static PreparedStatement concurrentAutoBatch(
			Connection connection, String sql)
		throws SQLException {

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		if (databaseMetaData.supportsBatchUpdates()) {
			return (PreparedStatement)ProxyUtil.newProxyInstance(
				ClassLoader.getSystemClassLoader(), _interfaces,
				new ConcurrentBatchInvocationHandler(connection, sql));
		}

		return (PreparedStatement)ProxyUtil.newProxyInstance(
			ClassLoader.getSystemClassLoader(), _interfaces,
			new ConcurrentNoBatchInvocationHandler(connection, sql));
	}

	private AutoBatchPreparedStatementUtil() {
	}

	private static final Method _addBatchMethod;
	private static final Method _closeMethod;
	private static final Method _executeBatch;
	private static final Class<?>[] _interfaces =
		new Class<?>[] {PreparedStatement.class};

	static {
		try {
			_addBatchMethod = PreparedStatement.class.getMethod("addBatch");
			_closeMethod = PreparedStatement.class.getMethod("close");
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

	private static class ConcurrentBatchInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (method.equals(_addBatchMethod)) {
				_preparedStatement.addBatch();

				if (++_count >= PropsValues.HIBERNATE_JDBC_BATCH_SIZE) {
					_executeBatch();
				}

				return null;
			}

			if (method.equals(_executeBatch)) {
				if (_count > 0) {
					_executeBatch();
				}

				return new int[0];
			}

			if (method.equals(_closeMethod)) {
				Throwable throwable = null;

				for (Future<Void> future : _futures) {
					try {
						future.get();
					}
					catch (Throwable t) {
						if (t instanceof ExecutionException) {
							t = t.getCause();
						}

						if (throwable == null) {
							throwable = t;
						}
						else {
							throwable.addSuppressed(t);
						}
					}
				}

				if (throwable != null) {
					throw throwable;
				}
			}

			return method.invoke(_preparedStatement, args);
		}

		private ConcurrentBatchInvocationHandler(
				Connection connection, String sql)
			throws SQLException {

			_connection = connection;
			_sql = sql;

			_preparedStatement = _connection.prepareStatement(_sql);
		}

		private void _executeBatch() throws SQLException {
			_count = 0;

			final PreparedStatement preparedStatement = _preparedStatement;

			NoticeableFuture<Void> noticeableFuture =
				_threadPoolExecutor.submit(
					new Callable<Void>() {

						@Override
						public Void call() throws SQLException {
							try {
								preparedStatement.executeBatch();
							}
							finally {
								synchronized (_connection) {
									preparedStatement.close();
								}
							}

							return null;
						}

					});

			_futures.add(noticeableFuture);

			noticeableFuture.addFutureListener(
				new FutureListener<Void>() {

					@Override
					public void complete(Future<Void> future) {
						try {
							future.get();

							_futures.remove(future);
						}
						catch (Throwable t) {
						}
					}

				});

			_preparedStatement = _connection.prepareStatement(_sql);
		}

		private final Connection _connection;
		private int _count;
		private final Set<Future<Void>> _futures = new ConcurrentHashSet<>();
		private PreparedStatement _preparedStatement;
		private final String _sql;
		private final ThreadPoolExecutor _threadPoolExecutor =
			PortalExecutorManagerUtil.getPortalExecutor(
				ConcurrentBatchInvocationHandler.class.getName());

	}

	private static class ConcurrentNoBatchInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (method.equals(_addBatchMethod)) {
				_executeUpdate();

				return null;
			}

			if (method.equals(_executeBatch)) {
				return new int[0];
			}

			if (method.equals(_closeMethod)) {
				Throwable throwable = null;

				for (Future<Void> future : _futures) {
					try {
						future.get();
					}
					catch (Throwable t) {
						if (t instanceof ExecutionException) {
							t = t.getCause();
						}

						if (throwable == null) {
							throwable = t;
						}
						else {
							throwable.addSuppressed(t);
						}
					}
				}

				if (throwable != null) {
					throw throwable;
				}
			}

			return method.invoke(_preparedStatement, args);
		}

		private ConcurrentNoBatchInvocationHandler(
				Connection connection, String sql)
			throws SQLException {

			_connection = connection;
			_sql = sql;

			_preparedStatement = _connection.prepareStatement(_sql);
		}

		private void _executeUpdate() throws SQLException {
			final PreparedStatement preparedStatement = _preparedStatement;

			NoticeableFuture<Void> noticeableFuture =
				_threadPoolExecutor.submit(
					new Callable<Void>() {

						@Override
						public Void call() throws SQLException {
							try {
								preparedStatement.executeUpdate();
							}
							finally {
								synchronized (_connection) {
									preparedStatement.close();
								}
							}

							return null;
						}

					});

			_futures.add(noticeableFuture);

			noticeableFuture.addFutureListener(
				new FutureListener<Void>() {

					@Override
					public void complete(Future<Void> future) {
						try {
							future.get();

							_futures.remove(future);
						}
						catch (Throwable t) {
						}
					}

				});

			_preparedStatement = _connection.prepareStatement(_sql);
		}

		private final Connection _connection;
		private final Set<Future<Void>> _futures = new ConcurrentHashSet<>();
		private PreparedStatement _preparedStatement;
		private final String _sql;
		private final ThreadPoolExecutor _threadPoolExecutor =
			PortalExecutorManagerUtil.getPortalExecutor(
				ConcurrentNoBatchInvocationHandler.class.getName());

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