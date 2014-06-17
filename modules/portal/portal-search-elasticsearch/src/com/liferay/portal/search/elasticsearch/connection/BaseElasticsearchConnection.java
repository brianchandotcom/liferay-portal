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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.index.IndexFactory;

import java.util.concurrent.Future;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequestBuilder;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.unit.TimeValue;

/**
 * @author Michael C. Han
 */
public abstract class BaseElasticsearchConnection
	implements ElasticsearchConnection {

	@Override
	public void close() {
		if (_client != null) {
			_client.close();
		}
	}

	@Override
	public Client getClient() {
		return _client;
	}

	public ClusterHealthResponse getClusterHealthResponse() {
		AdminClient adminClient = _client.admin();

		ClusterAdminClient clusterAdminClient = adminClient.cluster();

		ClusterHealthRequestBuilder clusterHealthRequestBuilder =
			clusterAdminClient.prepareHealth();

		clusterHealthRequestBuilder.setTimeout(TimeValue.timeValueSeconds(30));
		clusterHealthRequestBuilder.setWaitForGreenStatus();
		clusterHealthRequestBuilder.setWaitForNodes(">1");

		Future<ClusterHealthResponse> future =
			clusterHealthRequestBuilder.execute();

		try {
			return future.get();
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public String getClusterName() {
		if (Validator.isNull(_clusterName)) {
			_clusterName = CLUSTER_NAME;
		}

		return _clusterName;
	}

	public String getConfigFileName() {
		return _configFileName;
	}

	@Override
	public synchronized void initialize() {
		if (_client != null) {
			return;
		}

		ImmutableSettings.Builder builder = ImmutableSettings.settingsBuilder();

		_client = createClient(builder);
	}

	public void setClusterName(String clusterName) {
		_clusterName = clusterName;
	}

	public void setConfigFileName(String configFileName) {
		_configFileName = configFileName;
	}

	public void setIndexFactory(IndexFactory indexFactory) {
		_indexFactory = indexFactory;
	}

	protected abstract Client createClient(ImmutableSettings.Builder builder);

	protected IndexFactory getIndexFactory() {
		return _indexFactory;
	}

	protected void setClient(Client client) {
		_client = client;
	}

	private Client _client;
	private String _clusterName;
	private String _configFileName;
	private IndexFactory _indexFactory;

}