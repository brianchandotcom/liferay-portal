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

import com.zaxxer.hikari.HikariPoolMXBean;

import java.lang.reflect.Method;

/**
 * @author Mladen Cikara
 */
public class HikariConnectionPoolMetrics extends BaseConnectionPoolMetrics {

	public HikariConnectionPoolMetrics(Object dataSource)
		throws ReflectiveOperationException {

		_dataSource = dataSource;

		Class<?> clazz = dataSource.getClass();

		_getPoolNameMethod = clazz.getMethod("getPoolName");

		_getHikariPoolMXBeanMethod = clazz.getMethod("getHikariPoolMXBean");
	}

	@Override
	public int getNumActive() {
		try {
			HikariPoolMXBean hikariPoolMXBean =
				(HikariPoolMXBean)_getHikariPoolMXBeanMethod.invoke(
					_dataSource);

			return hikariPoolMXBean.getActiveConnections();
		}
		catch (ReflectiveOperationException roe) {
			throw new RuntimeException(roe);
		}
	}

	@Override
	public int getNumIdle() {
		try {
			HikariPoolMXBean hikariPoolMXBean =
				(HikariPoolMXBean)_getHikariPoolMXBeanMethod.invoke(
					_dataSource);

			return hikariPoolMXBean.getIdleConnections();
		}
		catch (ReflectiveOperationException roe) {
			throw new RuntimeException(roe);
		}
	}

	@Override
	protected Object getDataSource() {
		return _dataSource;
	}

	@Override
	protected String getPoolName() {
		try {
			return (String)_getPoolNameMethod.invoke(_dataSource);
		}
		catch (ReflectiveOperationException roe) {
			throw new RuntimeException(roe);
		}
	}

	private final Object _dataSource;
	private final Method _getHikariPoolMXBeanMethod;
	private final Method _getPoolNameMethod;

}