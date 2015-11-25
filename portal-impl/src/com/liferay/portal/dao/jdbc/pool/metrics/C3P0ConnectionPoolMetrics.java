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

import com.mchange.v2.c3p0.impl.AbstractPoolBackedDataSource;

import java.sql.SQLException;

/**
 * @author Mladen Cikara
 */
public class C3P0ConnectionPoolMetrics extends BaseConnectionPoolMetrics {

	public C3P0ConnectionPoolMetrics(
		AbstractPoolBackedDataSource connectionPool) {

		_connectionPool = connectionPool;
	}

	public int getNumActive() {
		try {
			return _connectionPool.getNumBusyConnections();
		}
		catch (SQLException e) {
			return -1;
		}
	}

	public int getNumIdle() {
		try {
			return _connectionPool.getNumIdleConnections();
		}
		catch (SQLException e) {
			return -1;
		}
	}

	@Override
	protected Object getDataSource() {
		return _connectionPool;
	}

	@Override
	protected String getPoolName() {
		return _connectionPool.getDataSourceName();
	}

	private final AbstractPoolBackedDataSource _connectionPool;

}