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

package com.liferay.portal.dao.jdbc.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Objects;

import javax.sql.DataSource;

/**
 * @author Shuyang Zhou
 */
public class AntiTimeDriftDataSourceWrapper extends DataSourceWrapper {

	public AntiTimeDriftDataSourceWrapper(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return (Connection)ProxyUtil.newProxyInstance(
			AntiTimeDriftDataSourceWrapper.class.getClassLoader(),
			new Class<?>[] {Connection.class},
			new AntiTimeDriftInvocationHandler(super.getConnection()));
	}

	@Override
	public Connection getConnection(String userName, String password)
		throws SQLException {

		return (Connection)ProxyUtil.newProxyInstance(
			AntiTimeDriftDataSourceWrapper.class.getClassLoader(),
			new Class<?>[] {Connection.class},
			new AntiTimeDriftInvocationHandler(
				super.getConnection(userName, password)));
	}

	private static synchronized boolean _checkTimeDrift() {
		boolean drifted = false;

		while (true) {
			long currentTime = System.currentTimeMillis();

			long delta = _previousTime - currentTime;

			if (delta > 0) {
				drifted = true;

				delta += 1000;

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Detected time drifting, delay execution for " + delta +
							"ms");
				}

				try {
					Thread.sleep(delta);
				}
				catch (InterruptedException interruptedException) {
				}
			}
			else {
				_previousTime = currentTime;

				break;
			}
		}

		return drifted;
	}

	private static Object _wrapStatement(Object target) {
		if (target instanceof Statement) {
			Class<?> targetClass = target.getClass();

			target = ProxyUtil.newProxyInstance(
				targetClass.getClassLoader(), targetClass.getInterfaces(),
				new AntiTimeDriftInvocationHandler(target));
		}

		return target;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AntiTimeDriftDataSourceWrapper.class);

	private static long _previousTime = System.currentTimeMillis();

	private static class AntiTimeDriftInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object object, Method method, Object[] args)
			throws Throwable {

			_checkTimeDrift();

			try {
				return _wrapStatement(method.invoke(_target, args));
			}
			catch (InvocationTargetException invocationTargetException1) {
				Throwable throwable1 = invocationTargetException1.getCause();

				if (throwable1 instanceof SQLException) {
					SQLException sqlException = (SQLException)throwable1;

					if ((sqlException.getErrorCode() == -204) &&
						Objects.equals("42704", sqlException.getSQLState()) &&
						_checkTimeDrift()) {

						if (_log.isDebugEnabled()) {
							_log.debug(
								"Caught a \"SQLCODE=-204, SQLSTATE=42704\" " +
									"and time drift, retry on the method call",
								throwable1);
						}

						try {
							return _wrapStatement(method.invoke(_target, args));
						}
						catch (InvocationTargetException
									invocationTargetException2) {

							Throwable throwable2 =
								invocationTargetException2.getCause();

							throwable2.addSuppressed(throwable1);

							throw throwable2;
						}
					}
				}

				throw throwable1;
			}
		}

		private AntiTimeDriftInvocationHandler(Object target) {
			_target = target;
		}

		private final Object _target;

	}

}