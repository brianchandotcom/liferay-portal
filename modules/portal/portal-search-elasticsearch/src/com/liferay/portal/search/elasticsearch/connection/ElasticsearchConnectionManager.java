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

import java.util.concurrent.atomic.AtomicReference;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true, service = ElasticsearchConnectionManager.class
)
public class ElasticsearchConnectionManager {

	public AdminClient getAdminClient() {
		Client client = getClient();

		return client.admin();
	}

	public Client getClient() {
		if (_elasticsearchConnection.get() == null) {
			throw new IllegalStateException(
				"Elasticsearch connection not initialized");
		}

		return _elasticsearchConnection.get().getClient();
	}

	public ClusterAdminClient getClusterAdminClient() {
		AdminClient adminClient = getAdminClient();

		return adminClient.cluster();
	}

	public ClusterHealthResponse getClusterHealthResponse() {
		return _elasticsearchConnection.get().getClusterHealthResponse();
	}

	public ElasticsearchConnection getElasticsearchConnection() {
		return _elasticsearchConnection.get();
	}

	@Reference(
		cardinality = ReferenceCardinality.MANDATORY,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	public void setElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		_elasticsearchConnection.set(elasticsearchConnection);
	}

	public void unsetElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		_elasticsearchConnection.set(null);
	}

	private final AtomicReference<ElasticsearchConnection>
		_elasticsearchConnection =
			new AtomicReference<ElasticsearchConnection>();

}