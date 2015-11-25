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

import org.apache.tomcat.jdbc.pool.DataSourceProxy;

/**
 * @author Mladen Cikara
 */
public class TomcatConnectionPoolMetrics extends BaseConnectionPoolMetrics {

	public TomcatConnectionPoolMetrics(DataSourceProxy connectionPool) {
		_connectionPool = connectionPool;
	}

	public int getNumActive() {
		return _connectionPool.getNumActive();
	}

	public int getNumIdle() {
		return _connectionPool.getNumIdle();
	}

	@Override
	protected Object getDataSource() {
		return _connectionPool;
	}

	@Override
	protected String getPoolName() {
		return _connectionPool.getPoolName();
	}

	private final DataSourceProxy _connectionPool;

}