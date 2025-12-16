/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ShardStatistics;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.FlushRequest;
import co.elastic.clients.elasticsearch.indices.FlushResponse;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.util.ConversionUtil;
import com.liferay.portal.search.engine.adapter.index.FlushIndexRequest;
import com.liferay.portal.search.engine.adapter.index.FlushIndexResponse;

import java.io.IOException;

/**
 * @author Michael C. Han
 */
public class FlushIndexRequestExecutor {

	public FlushIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public FlushIndexResponse execute(FlushIndexRequest flushIndexRequest) {
		FlushResponse flushResponse = _getFlushResponse(
			flushIndexRequest, createFlushRequest(flushIndexRequest));

		FlushIndexResponse flushIndexResponse = new FlushIndexResponse();

		ShardStatistics shardStatistics = flushResponse.shards();

		ListUtil.isNotEmptyForEach(
			shardStatistics.failures(),
			shardFailure -> flushIndexResponse.addIndexRequestShardFailure(
				IndexRequestShardFailureTranslatorUtil.translate(
					shardFailure)));

		flushIndexResponse.setFailedShards(
			ConversionUtil.toInt(shardStatistics.failed()));
		flushIndexResponse.setSuccessfulShards(
			ConversionUtil.toInt(shardStatistics.successful()));
		flushIndexResponse.setTotalShards(
			ConversionUtil.toInt(shardStatistics.total()));

		return flushIndexResponse;
	}

	protected FlushRequest createFlushRequest(
		FlushIndexRequest flushIndexRequest) {

		return FlushRequest.of(
			flushRequest -> flushRequest.force(
				flushIndexRequest.isForce()
			).index(
				ListUtil.fromArray(flushIndexRequest.getIndexNames())
			).waitIfOngoing(
				flushIndexRequest.isWaitIfOngoing()
			));
	}

	private FlushResponse _getFlushResponse(
		FlushIndexRequest flushIndexRequest, FlushRequest flushRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				flushIndexRequest.getConnectionId(),
				flushIndexRequest.isPreferLocalCluster());

		ElasticsearchIndicesClient elasticsearchIndicesClient =
			elasticsearchClient.indices();

		try {
			return elasticsearchIndicesClient.flush(flushRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}