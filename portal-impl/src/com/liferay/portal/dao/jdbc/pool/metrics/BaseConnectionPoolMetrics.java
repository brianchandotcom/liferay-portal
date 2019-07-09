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

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

/**
 * @author Mladen Cikara
 */
public abstract class BaseConnectionPoolMetrics
	implements ConnectionPoolMetrics {

	@Override
	public String getConnectionPoolName() {
		if (_name == null) {
			initializeConnectionPool();
		}

		return _name;
	}

	protected abstract Object getDataSource();

	protected abstract String getPoolName();

	protected void initializeConnectionPool() {
		Object dataSource = getDataSource();

		if (dataSource == null) {
			return;
		}

		LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy =
			(LazyConnectionDataSourceProxy)PortalBeanLocatorUtil.locate(
				"counterDataSource");

		if (dataSource.equals(
				lazyConnectionDataSourceProxy.getTargetDataSource())) {

			_name = "counterDataSource";

			return;
		}

		lazyConnectionDataSourceProxy =
			(LazyConnectionDataSourceProxy)PortalBeanLocatorUtil.locate(
				"liferayDataSource");

		Object targetDataSource =
			lazyConnectionDataSourceProxy.getTargetDataSource();

		if (dataSource.equals(targetDataSource)) {
			_name = "liferayDataSource";

			return;
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

					if (dataSource.equals(
							defaultDynamicDataSourceTargetSource.
								getReadDataSource())) {

						_name = "readDataSource";

						return;
					}

					if (dataSource.equals(
							defaultDynamicDataSourceTargetSource.
								getWriteDataSource())) {

						_name = "writeDataSource";

						return;
					}
				}
				catch (Exception e) {
					if (_log.isDebugEnabled()) {
						_log.debug(e.getMessage());
					}
				}
			}
		}

		_name = getPoolName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseConnectionPoolMetrics.class);

	private String _name;

}