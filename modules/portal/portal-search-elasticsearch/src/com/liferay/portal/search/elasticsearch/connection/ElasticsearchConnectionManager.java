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

package com.liferay.portal.search.elasticsearch.connection;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration",
	immediate = true, service = ElasticsearchConnectionManager.class
)
public class ElasticsearchConnectionManager {

	public void connect() {
		ElasticsearchConnection elasticsearchConnection =
			getElasticsearchConnection();

		elasticsearchConnection.connect();
	}

	public AdminClient getAdminClient() {
		Client client = getClient();

		return client.admin();
	}

	public Client getClient() {
		ElasticsearchConnection elasticsearchConnection =
			getElasticsearchConnection();

		if (elasticsearchConnection == null) {
			throw new IllegalStateException(
				"Elasticsearch connection not initialized");
		}

		return elasticsearchConnection.getClient();
	}

	public ClusterAdminClient getClusterAdminClient() {
		AdminClient adminClient = getAdminClient();

		return adminClient.cluster();
	}

	public ClusterHealthResponse getClusterHealthResponse(
		long timeout, int nodesCount) {

		ElasticsearchConnection elasticsearchConnection =
			getElasticsearchConnection();

		return elasticsearchConnection.getClusterHealthResponse(
			timeout, nodesCount);
	}

	public ElasticsearchConnection getElasticsearchConnection() {
		return _elasticsearchConnections.get(_operationMode);
	}

	@Reference(
		cardinality = ReferenceCardinality.MANDATORY,
		target = "(operation.mode=EMBEDDED)"
	)
	public void setEmbeddedElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		_elasticsearchConnections.put(
			elasticsearchConnection.getOperationMode(),
			elasticsearchConnection);
	}

	@Reference(
		cardinality = ReferenceCardinality.MANDATORY,
		target = "(operation.mode=REMOTE)"
	)
	public void setRemoteElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		_elasticsearchConnections.put(
			elasticsearchConnection.getOperationMode(),
			elasticsearchConnection);
	}

	public void unsetElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		_elasticsearchConnections.remove(
			elasticsearchConnection.getOperationMode());

		elasticsearchConnection.close();
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_elasticsearchConfiguration = Configurable.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		_clusterName = _elasticsearchConfiguration.clusterName();

		_operationMode = _elasticsearchConfiguration.operationMode();

		if (!_elasticsearchConnections.containsKey(_operationMode)) {
			throw new IllegalArgumentException(
				"No connection available for: " + _operationMode);
		}
	}

	@Modified
	protected synchronized void modified(Map<String, Object> properties) {
		_elasticsearchConfiguration = Configurable.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		OperationMode newOperationMode =
			_elasticsearchConfiguration.operationMode();

		if (Validator.equals(
				_elasticsearchConfiguration.clusterName(), _clusterName) &&
			Validator.equals(
				_elasticsearchConfiguration.operationMode(), _operationMode)) {

			return;
		}

		if (!_elasticsearchConnections.containsKey(newOperationMode)) {
			throw new IllegalArgumentException(
				"No connection available for: " + newOperationMode);
		}

		ElasticsearchConnection elasticsearchConnection =
			_elasticsearchConnections.get(_operationMode);

		boolean closed = elasticsearchConnection.close();

		_clusterName = _elasticsearchConfiguration.clusterName();
		_operationMode = newOperationMode;

		if (closed) {
			getClient();
		}
	}

	private String _clusterName;
	private volatile ElasticsearchConfiguration _elasticsearchConfiguration;
	private final Map<OperationMode, ElasticsearchConnection>
		_elasticsearchConnections = new HashMap<>();
	private OperationMode _operationMode;

}