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

import com.mchange.v2.c3p0.impl.AbstractPoolBackedDataSource;

import java.sql.SQLException;

/**
 * @author Mladen Cikara
 */
public class C3P0ConnectionPoolMetrics extends BaseConnectionPoolMetrics {

	public C3P0ConnectionPoolMetrics(
		AbstractPoolBackedDataSource abstractPoolBackedDataSource) {

		_abstractPoolBackedDataSource = abstractPoolBackedDataSource;
	}

	public int getNumActive() {
		try {
			return _abstractPoolBackedDataSource.getNumBusyConnections();
		}
		catch (SQLException sqle) {
			if (_log.isDebugEnabled()) {
				_log.debug(sqle.getMessage());
			}

			return -1;
		}
	}

	public int getNumIdle() {
		try {
			return _abstractPoolBackedDataSource.getNumIdleConnections();
		}
		catch (SQLException sqle) {
			if (_log.isDebugEnabled()) {
				_log.debug(sqle.getMessage());
			}

			return -1;
		}
	}

	@Override
	protected Object getDataSource() {
		return _abstractPoolBackedDataSource;
	}

	@Override
	protected String getPoolName() {
		return _abstractPoolBackedDataSource.getDataSourceName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		C3P0ConnectionPoolMetrics.class);

	private final AbstractPoolBackedDataSource _abstractPoolBackedDataSource;

}