/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.cluster;

import com.liferay.petra.string.StringBundler;

/**
 * @author Dylan Rebelak
 */
public class HealthClusterResponse implements ClusterResponse {

	public HealthClusterResponse(
		ClusterHealthStatus clusterHealthStatus, String healthStatusMessage) {

		_clusterHealthStatus = clusterHealthStatus;
		_healthStatusMessage = healthStatusMessage;
	}

	public ClusterHealthStatus getClusterHealthStatus() {
		return _clusterHealthStatus;
	}

	public String getHealthStatusMessage() {
		return _healthStatusMessage;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{_clusterHealthStatus=", _clusterHealthStatus,
			", _healthStatusMessage='", _healthStatusMessage, "'}");
	}

	private final ClusterHealthStatus _clusterHealthStatus;
	private final String _healthStatusMessage;

}