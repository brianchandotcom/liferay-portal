/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.StoreStats;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.IndicesStatsRequest;
import co.elastic.clients.elasticsearch.indices.IndicesStatsResponse;
import co.elastic.clients.elasticsearch.indices.stats.IndexStats;
import co.elastic.clients.elasticsearch.indices.stats.IndicesStats;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.StatsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.StatsIndexResponse;

import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Felipe Lorenz
 */
public class StatsIndexRequestExecutor {

	public StatsIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public StatsIndexResponse execute(StatsIndexRequest statsIndexRequest) {
		try {
			IndicesStatsResponse indicesStatsResponse =
				_getIndicesStatsResponse(
					createIndicesStatsRequest(statsIndexRequest),
					statsIndexRequest);

			Map<String, Long> indexSizes = new HashMap<>();

			Map<String, IndicesStats> indicesStatsMap =
				indicesStatsResponse.indices();

			long sizeOfLargestIndex = 0;

			for (Map.Entry<String, IndicesStats> entry :
					indicesStatsMap.entrySet()) {

				IndicesStats indicesStats = entry.getValue();

				IndexStats indexStats = indicesStats.total();

				StoreStats storeStats = indexStats.store();

				long indexSize = storeStats.sizeInBytes();

				if (indexSize > sizeOfLargestIndex) {
					sizeOfLargestIndex = indexSize;
				}

				indexSizes.put(entry.getKey(), indexSize);
			}

			return new StatsIndexResponse(indexSizes, sizeOfLargestIndex);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	protected IndicesStatsRequest createIndicesStatsRequest(
		StatsIndexRequest statsIndexRequest) {

		IndicesStatsRequest.Builder builder = new IndicesStatsRequest.Builder();

		if (ArrayUtil.isNotEmpty(statsIndexRequest.getIndexNames())) {
			builder.index(Arrays.asList(statsIndexRequest.getIndexNames()));
		}
		else {
			builder.index("_all");
		}

		return builder.build();
	}

	private IndicesStatsResponse _getIndicesStatsResponse(
		IndicesStatsRequest indicesStatsRequest,
		StatsIndexRequest statsIndexRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				statsIndexRequest.getConnectionId(),
				statsIndexRequest.isPreferLocalCluster());

		ElasticsearchIndicesClient elasticsearchIndicesClient =
			elasticsearchClient.indices();

		try {
			return elasticsearchIndicesClient.stats(indicesStatsRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}