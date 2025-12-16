/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.cluster;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cluster.ClusterStatsRequest;
import co.elastic.clients.elasticsearch.cluster.ClusterStatsResponse;
import co.elastic.clients.elasticsearch.cluster.ElasticsearchClusterClient;
import co.elastic.clients.elasticsearch.cluster.stats.ClusterFileSystem;
import co.elastic.clients.elasticsearch.cluster.stats.ClusterNodes;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.util.JsonpUtil;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterResponse;

import java.io.IOException;

import java.util.Arrays;

/**
 * @author Dylan Rebelak
 */
public class StatsClusterRequestExecutor {

	public StatsClusterRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public StatsClusterResponse execute(
		StatsClusterRequest statsClusterRequest) {

		try {
			ClusterStatsResponse clusterStatsResponse =
				_getClusterStatsResponse(
					_createClusterStatsRequest(statsClusterRequest),
					statsClusterRequest);

			ClusterNodes clusterNodes = clusterStatsResponse.nodes();

			ClusterFileSystem clusterFileSystem = clusterNodes.fs();

			long availableInBytes = clusterFileSystem.availableInBytes();
			long totalInBytes = clusterFileSystem.totalInBytes();

			return new StatsClusterResponse(
				availableInBytes,
				ClusterHealthStatusTranslatorUtil.translate(
					clusterStatsResponse.status()),
				JsonpUtil.toString(clusterStatsResponse),
				totalInBytes - availableInBytes);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private ClusterStatsRequest _createClusterStatsRequest(
		StatsClusterRequest statsClusterRequest) {

		ClusterStatsRequest.Builder builder = new ClusterStatsRequest.Builder();

		if (ArrayUtil.isNotEmpty(statsClusterRequest.getNodeIds())) {
			builder.nodeId(Arrays.asList(statsClusterRequest.getNodeIds()));
		}

		return builder.build();
	}

	private ClusterStatsResponse _getClusterStatsResponse(
		ClusterStatsRequest clusterStatsRequest,
		StatsClusterRequest statsClusterRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				statsClusterRequest.getConnectionId(),
				statsClusterRequest.isPreferLocalCluster());

		ElasticsearchClusterClient elasticsearchClusterClient =
			elasticsearchClient.cluster();

		try {
			return elasticsearchClusterClient.stats(clusterStatsRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}