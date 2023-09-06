/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.cluster;

/**
 * @author Dylan Rebelak
 */
public class StatsClusterResponse implements ClusterResponse {

	public StatsClusterResponse(
		long availableSpace, ClusterHealthStatus clusterHealthStatus,
		String statsMessage, long totalSpace) {

		_availableSpace = availableSpace;
		_clusterHealthStatus = clusterHealthStatus;
		_statsMessage = statsMessage;
		_totalSpace = totalSpace;
	}

	public long getAvailableSpace() {
		return _availableSpace;
	}

	public ClusterHealthStatus getClusterHealthStatus() {
		return _clusterHealthStatus;
	}

	public String getStatsMessage() {
		return _statsMessage;
	}

	public long getTotalSpace() {
		return _totalSpace;
	}

	public long getUsedSpace() {
		return _totalSpace - _availableSpace;
	}

	private final long _availableSpace;
	private final ClusterHealthStatus _clusterHealthStatus;
	private final String _statsMessage;
	private final long _totalSpace;

}