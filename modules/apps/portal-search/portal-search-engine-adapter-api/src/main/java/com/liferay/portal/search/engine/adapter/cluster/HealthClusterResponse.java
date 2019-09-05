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
		StringBundler sb = new StringBundler(5);

		sb.append("{_clusterHealthStatus=");
		sb.append(_clusterHealthStatus);
		sb.append(", _healthStatusMessage='");
		sb.append(_healthStatusMessage);
		sb.append("'}");

		return sb.toString();
	}

	private final ClusterHealthStatus _clusterHealthStatus;
	private final String _healthStatusMessage;

}