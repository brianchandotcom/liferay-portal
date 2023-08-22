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
		Long availableSpace, ClusterHealthStatus clusterHealthStatus,
		String statsMessage, Long totalSpace) {

		_availableSpace = availableSpace;
		_clusterHealthStatus = clusterHealthStatus;
		_statsMessage = statsMessage;
		_totalSpace = totalSpace;
	}

	public Long getAvailableSpace() {
		return _availableSpace;
	}

	public ClusterHealthStatus getClusterHealthStatus() {
		return _clusterHealthStatus;
	}

	public String getStatsMessage() {
		return _statsMessage;
	}

	public Long getTotalSpace() {
		return _totalSpace;
	}

	private final Long _availableSpace;
	private final ClusterHealthStatus _clusterHealthStatus;
	private final String _statsMessage;
	private final Long _totalSpace;

}