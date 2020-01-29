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

	protected void setClusterName(String clusterName) {
		_clusterName = clusterName;
	}

	protected void setConnectionId(String connectionId) {
		_connectionId = connectionId;
	}

	protected void setError(String error) {
		_error = error;
	}

	protected void setHealth(String health) {
		_health = health;
	}

	protected void setLabels(Set<String> labels) {
		_labels = labels;
	}

	protected void setNodeInformationList(
		List<NodeInformation> nodeInformationList) {

		_nodeInformationList = nodeInformationList;
	}

	protected static class Builder implements ConnectionInformationBuilder {

		@Override
		public ConnectionInformation build() {
			return new ConnectionInformationImpl(_connectionInformationImpl);
		}

		@Override
		public void clusterName(String clusterName) {
			_connectionInformationImpl.setClusterName(clusterName);
		}

		@Override
		public void connectionId(String connectionId) {
			_connectionInformationImpl.setConnectionId(connectionId);
		}

		@Override
		public void error(String error) {
			_connectionInformationImpl.setError(error);
		}

		@Override
		public void health(String health) {
			_connectionInformationImpl.setHealth(health);
		}

		@Override
		public void labels(Set<String> labels) {
			_connectionInformationImpl.setLabels(labels);
		}

		@Override
		public void nodeInformationList(
			List<NodeInformation> nodeInformationList) {

			_connectionInformationImpl.setNodeInformationList(
				nodeInformationList);
		}

		private final ConnectionInformationImpl _connectionInformationImpl =
			new ConnectionInformationImpl();

	}

	private ConnectionInformationImpl() {
	}

	private String _clusterName;
	private String _connectionId;
	private String _error;
	private String _health;
	private Set<String> _labels = new HashSet<>();
	private List<NodeInformation> _nodeInformationList = new ArrayList<>();

}