/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.connection;

import co.elastic.clients.elasticsearch._types.HealthStatus;
import co.elastic.clients.elasticsearch.cluster.HealthResponse;

import com.liferay.petra.string.StringBundler;

/**
 * @author André de Oliveira
 */
public class HealthExpectations {

	public HealthExpectations() {
	}

	public HealthExpectations(HealthResponse healthResponse) {
		_activePrimaryShards = healthResponse.activePrimaryShards();
		_activeShards = healthResponse.activeShards();
		_healthStatus = healthResponse.status();
		_numberOfDataNodes = healthResponse.numberOfDataNodes();
		_numberOfNodes = healthResponse.numberOfNodes();
		_timedOut = healthResponse.timedOut();
		_unassignedShards = healthResponse.unassignedShards();
	}

	public int getActivePrimaryShards() {
		return _activePrimaryShards;
	}

	public int getActiveShards() {
		return _activeShards;
	}

	public HealthStatus getHealthStatus() {
		return _healthStatus;
	}

	public int getNumberOfDataNodes() {
		return _numberOfDataNodes;
	}

	public int getNumberOfNodes() {
		return _numberOfNodes;
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

	public void setHealthStatus(HealthStatus healthStatus) {
		_healthStatus = healthStatus;
	}

	public void setNumberOfDataNodes(int numberOfDataNodes) {
		_numberOfDataNodes = numberOfDataNodes;
	}

	public void setNumberOfNodes(int numberOfNodes) {
		_numberOfNodes = numberOfNodes;
	}

	public void setTimedOut(boolean timedOut) {
		_timedOut = timedOut;
	}

	public void setUnassignedShards(int unassignedShards) {
		_unassignedShards = unassignedShards;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{activePrimaryShards=", _activePrimaryShards, ", activeShards=",
			_activeShards, ", healthStatus=", _healthStatus,
			", numberOfDataNodes=", _numberOfDataNodes, ", numberOfNodes=",
			_numberOfNodes, ", timedOut=", _timedOut, ", unassignedShards=",
			_unassignedShards, "}");
	}

	private int _activePrimaryShards;
	private int _activeShards;
	private HealthStatus _healthStatus;
	private int _numberOfDataNodes;
	private int _numberOfNodes;
	private boolean _timedOut;
	private int _unassignedShards;

}