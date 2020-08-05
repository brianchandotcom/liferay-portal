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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.cluster;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.cluster.UpdateSettingsClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.UpdateSettingsClusterResponse;

import java.io.IOException;

import java.util.Map;

import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsResponse;
import org.elasticsearch.client.ClusterClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	immediate = true, service = UpdateSettingsClusterRequestExecutor.class
)
public class UpdateSettingsClusterRequestExecutorImpl
	implements UpdateSettingsClusterRequestExecutor {

	@Override
	public UpdateSettingsClusterResponse execute(
		UpdateSettingsClusterRequest updateSettingsClusterRequest) {

		ClusterUpdateSettingsRequest clusterUpdateSettingsRequest =
			createClusterUpdateSettingsRequest(updateSettingsClusterRequest);

		ClusterUpdateSettingsResponse clusterUpdateSettingsResponse =
			getClusterUpdateSettingsResponse(
				clusterUpdateSettingsRequest, updateSettingsClusterRequest);

		Settings persistentSettings =
			clusterUpdateSettingsResponse.getPersistentSettings();

		Settings transientSettings =
			clusterUpdateSettingsResponse.getTransientSettings();

		return new UpdateSettingsClusterResponse(
			persistentSettings.toString(), transientSettings.toString());
	}

	protected ClusterUpdateSettingsRequest createClusterUpdateSettingsRequest(
		UpdateSettingsClusterRequest updateSettingsClusterRequest) {

		ClusterUpdateSettingsRequest clusterUpdateSettingsRequest =
			new ClusterUpdateSettingsRequest();

		Settings.Builder persistentSettingsBuilder = Settings.builder();

		Map<String, String> persistentSettings =
			updateSettingsClusterRequest.getPersistentSettings();

		for (Map.Entry<String, String> entry : persistentSettings.entrySet()) {
			persistentSettingsBuilder.put(entry.getKey(), entry.getValue());
		}

		clusterUpdateSettingsRequest.persistentSettings(
			persistentSettingsBuilder);

		Settings.Builder transientSettingsBuilder = Settings.builder();

		Map<String, String> transientSettings =
			updateSettingsClusterRequest.getTransientSettings();

		for (Map.Entry<String, String> entry : transientSettings.entrySet()) {
			transientSettingsBuilder.put(entry.getKey(), entry.getValue());
		}

		clusterUpdateSettingsRequest.transientSettings(
			transientSettingsBuilder);

		return clusterUpdateSettingsRequest;
	}

	protected ClusterUpdateSettingsResponse getClusterUpdateSettingsResponse(
		ClusterUpdateSettingsRequest clusterUpdateSettingsRequest,
		UpdateSettingsClusterRequest updateSettingsClusterRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				updateSettingsClusterRequest.getConnectionId(),
				updateSettingsClusterRequest.isPreferLocalCluster());

		ClusterClient clusterClient = restHighLevelClient.cluster();

		try {
			return clusterClient.putSettings(
				clusterUpdateSettingsRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}