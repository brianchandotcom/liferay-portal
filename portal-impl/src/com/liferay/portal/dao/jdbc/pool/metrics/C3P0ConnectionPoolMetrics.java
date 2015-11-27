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

	@Override
	public int getNumActive() {
		try {
			return _abstractPoolBackedDataSource.getNumBusyConnections();
		}
		catch (SQLException se) {
			_log.error(se.getMessage(), se);

			return -1;
		}
	}

	@Override
	public int getNumIdle() {
		try {
			return _abstractPoolBackedDataSource.getNumIdleConnections();
		}
		catch (SQLException se) {
			_log.error(se.getMessage(), se);

			return -1;
		}
	}

	@Override
	protected String fetchConnectionPoolName() {
		return _abstractPoolBackedDataSource.getDataSourceName();
	}

	@Override
	protected Object getDataSource() {
		return _abstractPoolBackedDataSource;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		C3P0ConnectionPoolMetrics.class);

	private final AbstractPoolBackedDataSource _abstractPoolBackedDataSource;

}