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
		ClusterHealthStatus clusterHealthStatus, String statsMessage) {

		_clusterHealthStatus = clusterHealthStatus;
		_statsMessage = statsMessage;

		_availableSpaceInBytes = 0;
		_usedSpaceInBytes = 0;
	}

	public StatsClusterResponse(
		long availableSpaceInBytes, ClusterHealthStatus clusterHealthStatus,
		String statsMessage, long usedSpaceInBytes) {

		_availableSpaceInBytes = availableSpaceInBytes;
		_clusterHealthStatus = clusterHealthStatus;
		_statsMessage = statsMessage;
		_usedSpaceInBytes = usedSpaceInBytes;
	}

	public long getAvailableSpaceInBytes() {
		return _availableSpaceInBytes;
	}

	public ClusterHealthStatus getClusterHealthStatus() {
		return _clusterHealthStatus;
	}

	public String getStatsMessage() {
		return _statsMessage;
	}

	public long getUsedSpaceInBytes() {
		return _usedSpaceInBytes;
	}

	private final long _availableSpaceInBytes;
	private final ClusterHealthStatus _clusterHealthStatus;
	private final String _statsMessage;
	private final long _usedSpaceInBytes;

}