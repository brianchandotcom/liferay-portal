/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.cluster;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cluster.ElasticsearchClusterClient;
import co.elastic.clients.elasticsearch.cluster.PutClusterSettingsRequest;
import co.elastic.clients.elasticsearch.cluster.PutClusterSettingsResponse;
import co.elastic.clients.json.JsonData;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.cluster.UpdateSettingsClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.UpdateSettingsClusterResponse;

import java.io.IOException;

import java.util.Map;

/**
 * @author Bryan Engler
 */
public class UpdateSettingsClusterRequestExecutor {

	public UpdateSettingsClusterRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public UpdateSettingsClusterResponse execute(
		UpdateSettingsClusterRequest updateSettingsClusterRequest) {

		PutClusterSettingsResponse putClusterSettingsResponse =
			_getPutClusterSettingsResponse(
				_createPutClusterSettingsRequest(updateSettingsClusterRequest),
				updateSettingsClusterRequest);

		JSONObject persistentSettingsJSONObject =
			JSONFactoryUtil.createJSONObject(
				putClusterSettingsResponse.persistent());

		JSONObject transientSettingsJSONObject =
			JSONFactoryUtil.createJSONObject(
				putClusterSettingsResponse.transient_());

		return new UpdateSettingsClusterResponse(
			persistentSettingsJSONObject.toString(),
			transientSettingsJSONObject.toString());
	}

	private PutClusterSettingsRequest _createPutClusterSettingsRequest(
		UpdateSettingsClusterRequest updateSettingsClusterRequest) {

		PutClusterSettingsRequest.Builder builder =
			new PutClusterSettingsRequest.Builder();

		Map<String, String> persistentSettings =
			updateSettingsClusterRequest.getPersistentSettings();

		for (Map.Entry<String, String> entry : persistentSettings.entrySet()) {
			builder.persistent(entry.getKey(), JsonData.of(entry.getValue()));
		}

		Map<String, String> transientSettings =
			updateSettingsClusterRequest.getTransientSettings();

		for (Map.Entry<String, String> entry : transientSettings.entrySet()) {
			builder.transient_(entry.getKey(), JsonData.of(entry.getValue()));
		}

		return builder.build();
	}

	private PutClusterSettingsResponse _getPutClusterSettingsResponse(
		PutClusterSettingsRequest putClusterSettingsRequest,
		UpdateSettingsClusterRequest updateSettingsClusterRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				updateSettingsClusterRequest.getConnectionId(),
				updateSettingsClusterRequest.isPreferLocalCluster());

		ElasticsearchClusterClient elasticsearchClusterClient =
			elasticsearchClient.cluster();

		try {
			return elasticsearchClusterClient.putSettings(
				putClusterSettingsRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}