/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.engine;

import com.liferay.portal.search.engine.ConnectionInformation;
import com.liferay.portal.search.engine.ConnectionInformationBuilder;
import com.liferay.portal.search.engine.NodeInformation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Bryan Engler
 */
public class ConnectionInformationImpl implements ConnectionInformation {

	@Override
	public String getClusterName() {
		return _clusterName;
	}

	@Override
	public String getConnectionId() {
		return _connectionId;
	}

	@Override
	public String getError() {
		return _error;
	}

	@Override
	public String getHealth() {
		return _health;
	}

	@Override
	public Set<String> getLabels() {
		return _labels;
	}

	@Override
	public List<NodeInformation> getNodeInformationList() {
		return _nodeInformationList;
	}

	protected ConnectionInformationImpl(
		ConnectionInformationImpl connectionInformationImpl) {

		_clusterName = connectionInformationImpl._clusterName;
		_connectionId = connectionInformationImpl._connectionId;
		_error = connectionInformationImpl._error;
		_health = connectionInformationImpl._health;
		_labels = connectionInformationImpl._labels;
		_nodeInformationList = connectionInformationImpl._nodeInformationList;
	}

	protected static class Builder implements ConnectionInformationBuilder {

		@Override
		public ConnectionInformation build() {
			return new ConnectionInformationImpl(_connectionInformationImpl);
		}

		@Override
		public void clusterName(String clusterName) {
			_connectionInformationImpl._setClusterName(clusterName);
		}

		@Override
		public void connectionId(String connectionId) {
			_connectionInformationImpl._setConnectionId(connectionId);
		}

		@Override
		public void error(String error) {
			_connectionInformationImpl._setError(error);
		}

		@Override
		public void health(String health) {
			_connectionInformationImpl._setHealth(health);
		}

		@Override
		public void labels(Set<String> labels) {
			_connectionInformationImpl._setLabels(labels);
		}

		@Override
		public void nodeInformationList(
			List<NodeInformation> nodeInformationList) {

			_connectionInformationImpl._setNodeInformationList(
				nodeInformationList);
		}

		private final ConnectionInformationImpl _connectionInformationImpl =
			new ConnectionInformationImpl();

	}

	private ConnectionInformationImpl() {
	}

	private void _setClusterName(String clusterName) {
		_clusterName = clusterName;
	}

	private void _setConnectionId(String connectionId) {
		_connectionId = connectionId;
	}

	private void _setError(String error) {
		_error = error;
	}

	private void _setHealth(String health) {
		_health = health;
	}

	private void _setLabels(Set<String> labels) {
		_labels = labels;
	}

	private void _setNodeInformationList(
		List<NodeInformation> nodeInformationList) {

		_nodeInformationList = nodeInformationList;
	}

	private String _clusterName;
	private String _connectionId;
	private String _error;
	private String _health;
	private Set<String> _labels = new HashSet<>();
	private List<NodeInformation> _nodeInformationList = new ArrayList<>();

}