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

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import java.lang.management.ManagementFactory;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author Mladen Cikara
 */
public class HikariConnectionPoolMetrics extends BaseConnectionPoolMetrics {

	public HikariConnectionPoolMetrics(HikariDataSource hikariDataSource) {
		_hikariDataSource = hikariDataSource;
	}

	@Override
	public int getNumActive() {
		if (!_initializationFailed && (_hikariPoolMXBean == null)) {
			initializeConnectionPool();
		}

		if (_initializationFailed) {
			return -1;
		}

		return _hikariPoolMXBean.getActiveConnections();
	}

	@Override
	public int getNumIdle() {
		if (!_initializationFailed && (_hikariPoolMXBean == null)) {
			initializeConnectionPool();
		}

		if (_initializationFailed) {
			return -1;
		}

		return _hikariPoolMXBean.getIdleConnections();
	}

	@Override
	protected String fetchConnectionPoolName() {
		return _hikariDataSource.getPoolName();
	}

	@Override
	protected Object getDataSource() {
		if (_initializationFailed) {
			return null;
		}

		return _hikariDataSource;
	}

	@Override
	protected void initializeConnectionPool() {
		if (_hikariDataSource.getPoolName() == null ) {
			_initializationFailed = true;

			return;
		}

		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

		try {
			ObjectName poolName = new ObjectName(
				"com.zaxxer.hikari:type=Pool (" +
					_hikariDataSource.getPoolName() + ")");

			_hikariPoolMXBean = JMX.newMXBeanProxy(
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

	private final HikariDataSource _hikariDataSource;
	private HikariPoolMXBean _hikariPoolMXBean;
	private boolean _initializationFailed = false;

}