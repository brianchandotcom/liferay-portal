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

package com.liferay.portal.search.elasticsearch6.internal.connection;

import com.liferay.petra.string.StringBundler;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.cluster.health.ClusterHealthStatus;

/**
 * @author André de Oliveira
 */
public class HealthExpectations {

	public HealthExpectations() {
	}

	public HealthExpectations(ClusterHealthResponse clusterHealthResponse) {
		activePrimaryShards = clusterHealthResponse.getActivePrimaryShards();
		activeShards = clusterHealthResponse.getActiveShards();
		numberOfDataNodes = clusterHealthResponse.getNumberOfDataNodes();
		numberOfNodes = clusterHealthResponse.getNumberOfNodes();
		status = clusterHealthResponse.getStatus();
		timedOut = clusterHealthResponse.isTimedOut();
		unassignedShards = clusterHealthResponse.getUnassignedShards();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{activePrimaryShards=");
		sb.append(activePrimaryShards);
		sb.append(", activeShards=");
		sb.append(activeShards);
		sb.append(", numberOfDataNodes=");
		sb.append(numberOfDataNodes);
		sb.append(", numberOfNodes=");
		sb.append(numberOfNodes);
		sb.append(", status=");
		sb.append(status);
		sb.append(", timedOut=");
		sb.append(timedOut);
		sb.append(", unassignedShards=");
		sb.append(unassignedShards);
		sb.append("}");

		return sb.toString();
	}

	public int activePrimaryShards;
	public int activeShards;
	public int numberOfDataNodes;
	public int numberOfNodes;
	public ClusterHealthStatus status;
	public boolean timedOut;
	public int unassignedShards;

}