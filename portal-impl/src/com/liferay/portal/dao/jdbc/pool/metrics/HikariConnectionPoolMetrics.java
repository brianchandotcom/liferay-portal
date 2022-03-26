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

import com.liferay.portal.dao.jdbc.aop.DefaultDynamicDataSourceTargetSource;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.jdbc.pool.metrics.ConnectionPoolMetrics;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.zaxxer.hikari.HikariPoolMXBean;

import java.lang.reflect.Method;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

/**
 * @author Mladen Cikara
 */
public class HikariConnectionPoolMetrics implements ConnectionPoolMetrics {

	public HikariConnectionPoolMetrics(Object dataSource)
		throws ReflectiveOperationException {

		_dataSource = dataSource;

		Class<?> clazz = dataSource.getClass();

		_getPoolNameMethod = clazz.getMethod("getPoolName");

		_getHikariPoolMXBeanMethod = clazz.getMethod("getHikariPoolMXBean");
	}

	@Override
	public String getConnectionPoolName() {
		if (_name == null) {
			_name = _getConnectionPoolName();
		}

		return _name;
	}

	@Override
	public int getNumActive() {
		try {
			HikariPoolMXBean hikariPoolMXBean =
				(HikariPoolMXBean)_getHikariPoolMXBeanMethod.invoke(
					_dataSource);

			return hikariPoolMXBean.getActiveConnections();
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
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
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	private String _getConnectionPoolName() {
		LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy =
			(LazyConnectionDataSourceProxy)PortalBeanLocatorUtil.locate(
				"counterDataSource");

		if (_dataSource.equals(
				lazyConnectionDataSourceProxy.getTargetDataSource())) {

			return "counterDataSource";
		}

		lazyConnectionDataSourceProxy =
			(LazyConnectionDataSourceProxy)PortalBeanLocatorUtil.locate(
				"liferayDataSource");

		Object targetDataSource =
			lazyConnectionDataSourceProxy.getTargetDataSource();

		if (_dataSource.equals(targetDataSource)) {
			return "liferayDataSource";
		}
		else if (AopUtils.isAopProxy(targetDataSource) &&
				 (targetDataSource instanceof Advised)) {

			Advised advised = (Advised)targetDataSource;

			targetDataSource = advised.getTargetSource();

			if (targetDataSource instanceof
					DefaultDynamicDataSourceTargetSource) {

				try {
					DefaultDynamicDataSourceTargetSource
						defaultDynamicDataSourceTargetSource =
							(DefaultDynamicDataSourceTargetSource)
								targetDataSource;

					if (_dataSource.equals(
							defaultDynamicDataSourceTargetSource.
								getReadDataSource())) {

						return "readDataSource";
					}

					if (_dataSource.equals(
							defaultDynamicDataSourceTargetSource.
								getWriteDataSource())) {

						return "writeDataSource";
					}
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception);
					}
				}
			}
		}

		try {
			return (String)_getPoolNameMethod.invoke(_dataSource);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HikariConnectionPoolMetrics.class);

	private final Object _dataSource;
	private final Method _getHikariPoolMXBeanMethod;
	private final Method _getPoolNameMethod;
	private String _name;

}