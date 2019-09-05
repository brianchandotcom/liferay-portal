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

package com.liferay.portal.search.engine.adapter.cluster;

/**
 * @author Dylan Rebelak
 */
public class HealthClusterRequest
	implements ClusterRequest<HealthClusterResponse> {

	public HealthClusterRequest() {
	}

	public HealthClusterRequest(String... indexNames) {
		_indexNames = indexNames;
	}

	@Override
	public HealthClusterResponse accept(
		ClusterRequestExecutor clusterRequestExecutor) {

		return clusterRequestExecutor.executeClusterRequest(this);
	}

	@Override
	public String[] getIndexNames() {
		return _indexNames;
	}

	public long getTimeout() {
		return _timeout;
	}

	public ClusterHealthStatus getWaitForClusterHealthStatus() {
		return _waitForClusterHealthStatus;
	}

	public void setTimeout(long timeout) {
		_timeout = timeout;
	}

	public void setWaitForClusterHealthStatus(
		ClusterHealthStatus waitForClusterHealthStatus) {

		_waitForClusterHealthStatus = waitForClusterHealthStatus;
	}

	private String[] _indexNames;
	private long _timeout;
	private ClusterHealthStatus _waitForClusterHealthStatus;

}