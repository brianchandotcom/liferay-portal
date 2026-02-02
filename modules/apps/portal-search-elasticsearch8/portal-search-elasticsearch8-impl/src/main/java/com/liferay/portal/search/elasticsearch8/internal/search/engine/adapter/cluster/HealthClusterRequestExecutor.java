/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.cluster;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.TimeUnit;
import co.elastic.clients.elasticsearch.cluster.ElasticsearchClusterClient;
import co.elastic.clients.elasticsearch.cluster.HealthRequest;
import co.elastic.clients.elasticsearch.cluster.HealthResponse;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.util.JsonpUtil;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterResponse;

import java.io.IOException;

import java.util.Arrays;

/**
 * @author Dylan Rebelak
 */
public class HealthClusterRequestExecutor {

	public HealthClusterRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public HealthClusterResponse execute(
		HealthClusterRequest healthClusterRequest) {

		HealthResponse healthResponse = _getHealthResponse(
			healthClusterRequest, createHealthRequest(healthClusterRequest));

		return new HealthClusterResponse(
			ClusterHealthStatusTranslatorUtil.translate(
				healthResponse.status()),
			JsonpUtil.toString(healthResponse));
	}

	protected HealthRequest createHealthRequest(
		HealthClusterRequest healthClusterRequest) {

		HealthRequest.Builder builder = new HealthRequest.Builder();

		if (ArrayUtil.isNotEmpty(healthClusterRequest.getIndexNames())) {
			builder.index(Arrays.asList(healthClusterRequest.getIndexNames()));
		}

		if (healthClusterRequest.getTimeout() > 0) {
			Time time = Time.of(
				elasticsearchTime -> elasticsearchTime.time(
					healthClusterRequest.getTimeout() +
						TimeUnit.Milliseconds.jsonValue()));

			builder.masterTimeout(time);
			builder.timeout(time);
		}

		if (healthClusterRequest.getWaitForClusterHealthStatus() != null) {
			builder.waitForStatus(
				ClusterHealthStatusTranslatorUtil.translate(
					healthClusterRequest.getWaitForClusterHealthStatus()));
		}

		return builder.build();
	}

	private HealthResponse _getHealthResponse(
		HealthClusterRequest healthClusterRequest,
		HealthRequest healthRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				healthClusterRequest.getConnectionId(),
				healthClusterRequest.isPreferLocalCluster());

		ElasticsearchClusterClient elasticsearchClusterClient =
			elasticsearchClient.cluster();

		try {
			return elasticsearchClusterClient.health(healthRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}