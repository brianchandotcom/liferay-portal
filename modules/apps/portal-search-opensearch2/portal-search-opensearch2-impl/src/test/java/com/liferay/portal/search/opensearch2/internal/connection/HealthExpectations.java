/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.connection;

import com.liferay.petra.string.StringBundler;

import org.opensearch.client.opensearch._types.HealthStatus;
import org.opensearch.client.opensearch.cluster.HealthResponse;

/**
 * @author André de Oliveira
 */
public class HealthExpectations {

	public HealthExpectations() {
	}

	public HealthExpectations(HealthResponse healthResponse) {
		_activePrimaryShards = healthResponse.activePrimaryShards();
		_activeShards = healthResponse.activeShards();
		_numberOfDataNodes = healthResponse.numberOfDataNodes();
		_numberOfNodes = healthResponse.numberOfNodes();
		_healthStatus = healthResponse.status();
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

	public void setNumberOfDataNodes(int numberOfDataNodes) {
		_numberOfDataNodes = numberOfDataNodes;
	}

	public void setNumberOfNodes(int numberOfNodes) {
		_numberOfNodes = numberOfNodes;
	}

	public void setStatus(HealthStatus healthStatus) {
		_healthStatus = healthStatus;
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
			_activeShards, ", numberOfDataNodes=", _numberOfDataNodes,
			", numberOfNodes=", _numberOfNodes, ", healthStatus=",
			_healthStatus, ", timedOut=", _timedOut, ", unassignedShards=",
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