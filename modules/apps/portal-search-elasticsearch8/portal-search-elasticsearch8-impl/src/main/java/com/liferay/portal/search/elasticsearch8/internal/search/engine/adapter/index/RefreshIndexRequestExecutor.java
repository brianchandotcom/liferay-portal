/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ShardStatistics;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.RefreshRequest;
import co.elastic.clients.elasticsearch.indices.RefreshResponse;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.util.ConversionUtil;
import com.liferay.portal.search.engine.adapter.index.RefreshIndexRequest;
import com.liferay.portal.search.engine.adapter.index.RefreshIndexResponse;

import java.io.IOException;

/**
 * @author Michael C. Han
 */
public class RefreshIndexRequestExecutor {

	public RefreshIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public RefreshIndexResponse execute(
		RefreshIndexRequest refreshIndexRequest) {

		RefreshIndexResponse refreshIndexResponse = new RefreshIndexResponse();

		RefreshResponse refreshResponse = _getRefreshResponse(
			refreshIndexRequest, createRefreshRequest(refreshIndexRequest));

		ShardStatistics shardStatistics = refreshResponse.shards();

		ListUtil.isNotEmptyForEach(
			shardStatistics.failures(),
			shardFailure -> refreshIndexResponse.addIndexRequestShardFailure(
				IndexRequestShardFailureTranslatorUtil.translate(
					shardFailure)));

		refreshIndexResponse.setFailedShards(
			ConversionUtil.toInt(shardStatistics.failed()));
		refreshIndexResponse.setSuccessfulShards(
			ConversionUtil.toInt(shardStatistics.successful()));
		refreshIndexResponse.setTotalShards(
			ConversionUtil.toInt(shardStatistics.total()));

		return refreshIndexResponse;
	}

	protected RefreshRequest createRefreshRequest(
		RefreshIndexRequest refreshIndexRequest) {

		return RefreshRequest.of(
			refreshRequest -> refreshRequest.index(
				ListUtil.fromArray(refreshIndexRequest.getIndexNames())));
	}

	private RefreshResponse _getRefreshResponse(
		RefreshIndexRequest refreshIndexRequest,
		RefreshRequest refreshRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				refreshIndexRequest.getConnectionId(),
				refreshIndexRequest.isPreferLocalCluster());

		ElasticsearchIndicesClient elasticsearchIndicesClient =
			elasticsearchClient.indices();

		try {
			return elasticsearchIndicesClient.refresh(refreshRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}