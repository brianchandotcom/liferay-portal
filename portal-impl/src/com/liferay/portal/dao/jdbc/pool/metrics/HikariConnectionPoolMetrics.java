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

package com.liferay.portal.dao.jdbc.pool.metrics;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.zaxxer.hikari.HikariPoolMXBean;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author Mladen Cikara
 */
public class HikariConnectionPoolMetrics extends BaseConnectionPoolMetrics {

	public HikariConnectionPoolMetrics(Object dataSource) {
		_dataSource = dataSource;
	}

	public int getNumActive() {
		if (!_initializationFailed && (_connectionPool == null)) {
			initializeConnectionPool();
		}

		if (_initializationFailed) {
			return -1;
		}

		return _connectionPool.getActiveConnections();
	}

	public int getNumIdle() {
		if (!_initializationFailed && (_connectionPool == null)) {
			initializeConnectionPool();
		}

		if (_initializationFailed) {
			return -1;
		}

		return _connectionPool.getIdleConnections();
	}

	@Override
	protected Object getDataSource() {
		if (_initializationFailed) {
			return null;
		}

		return _dataSource;
	}

	@Override
	protected String getPoolName() {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			Class<?> hikariDataSourceClazz = contextClassLoader.loadClass(
				"com.zaxxer.hikari.HikariDataSource");

			Method poolNameMethod = hikariDataSourceClazz.getMethod(
				"getPoolName");

			return (String)poolNameMethod.invoke(_dataSource);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}
		}

		return null;
	}

	@Override
	protected void initializeConnectionPool() {
		if (getPoolName() == null ) {
			_initializationFailed = true;

			return;
		}

		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

		try {
			ObjectName poolName = new ObjectName(
				"com.zaxxer.hikari:type=Pool (" + getPoolName() + ")");

			_connectionPool = JMX.newMXBeanProxy(
				mBeanServer, poolName, HikariPoolMXBean.class);
		}
		catch (Exception e) {
			_initializationFailed = true;

			if (_log.isDebugEnabled()) {
				_log.debug(e.getMessage());
			}
		}

		super.initializeConnectionPool();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HikariConnectionPoolMetrics.class);

	private HikariPoolMXBean _connectionPool;
	private final Object _dataSource;
	private boolean _initializationFailed = false;

}