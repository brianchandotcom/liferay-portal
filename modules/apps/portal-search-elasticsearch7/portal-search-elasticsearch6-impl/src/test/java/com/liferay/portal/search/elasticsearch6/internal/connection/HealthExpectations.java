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
		_activePrimaryShards = clusterHealthResponse.getActivePrimaryShards();
		_activeShards = clusterHealthResponse.getActiveShards();
		_numberOfDataNodes = clusterHealthResponse.getNumberOfDataNodes();
		_numberOfNodes = clusterHealthResponse.getNumberOfNodes();
		_status = clusterHealthResponse.getStatus();
		_timedOut = clusterHealthResponse.isTimedOut();
		_unassignedShards = clusterHealthResponse.getUnassignedShards();
	}

	public int getActivePrimaryShards() {
		return _activePrimaryShards;
	}

	public int getActiveShards() {
		return _activeShards;
	}

	public int getNumberOfDataNodes() {
		return _numberOfDataNodes;
	}

	public int getNumberOfNodes() {
		return _numberOfNodes;
	}

	public ClusterHealthStatus getStatus() {
		return _status;
	}

	public int getUnassignedShards() {
		return _unassignedShards;
	}

	public boolean isTimedOut() {
		return _timedOut;
	}

	public void setActivePrimaryShards(int activePrimaryShards) {
		_activePrimaryShards = activePrimaryShards;
	}

	public void setActiveShards(int activeShards) {
		_activeShards = activeShards;
	}

	public void setNumberOfDataNodes(int numberOfDataNodes) {
		_numberOfDataNodes = numberOfDataNodes;
	}

	public void setNumberOfNodes(int numberOfNodes) {
		_numberOfNodes = numberOfNodes;
	}

	public void setStatus(ClusterHealthStatus status) {
		_status = status;
	}

	public void setTimedOut(boolean timedOut) {
		_timedOut = timedOut;
	}

	public void setUnassignedShards(int unassignedShards) {
		_unassignedShards = unassignedShards;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{activePrimaryShards=");
		sb.append(_activePrimaryShards);
		sb.append(", activeShards=");
		sb.append(_activeShards);
		sb.append(", numberOfDataNodes=");
		sb.append(_numberOfDataNodes);
		sb.append(", numberOfNodes=");
		sb.append(_numberOfNodes);
		sb.append(", status=");
		sb.append(_status);
		sb.append(", timedOut=");
		sb.append(_timedOut);
		sb.append(", unassignedShards=");
		sb.append(_unassignedShards);
		sb.append("}");

		return sb.toString();
	}

	private int _activePrimaryShards;
	private int _activeShards;
	private int _numberOfDataNodes;
	private int _numberOfNodes;
	private ClusterHealthStatus _status;
	private boolean _timedOut;
	private int _unassignedShards;

}