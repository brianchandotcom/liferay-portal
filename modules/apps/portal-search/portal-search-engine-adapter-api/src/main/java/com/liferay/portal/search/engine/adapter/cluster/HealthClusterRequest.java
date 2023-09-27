/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.cluster;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Dylan Rebelak
 */
public class HealthClusterRequest
	extends CrossClusterRequest
	implements ClusterRequest<HealthClusterResponse> {

	public HealthClusterRequest() {
		setPreferLocalCluster(true);
	}

	public HealthClusterRequest(String... indexNames) {
		_indexNames = indexNames;

		setPreferLocalCluster(true);
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