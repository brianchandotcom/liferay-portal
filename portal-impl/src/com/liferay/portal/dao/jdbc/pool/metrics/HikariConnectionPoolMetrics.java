/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.jdbc.pool.metrics;

import com.liferay.portal.dao.jdbc.util.DynamicDataSource;
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

			if (targetDataSource instanceof DynamicDataSource) {
				try {
					DynamicDataSource dynamicDataSource =
						(DynamicDataSource)targetDataSource;

					if (_dataSource.equals(
							dynamicDataSource.getReadDataSource())) {

						return "readDataSource";
					}

					if (_dataSource.equals(
							dynamicDataSource.getWriteDataSource())) {

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