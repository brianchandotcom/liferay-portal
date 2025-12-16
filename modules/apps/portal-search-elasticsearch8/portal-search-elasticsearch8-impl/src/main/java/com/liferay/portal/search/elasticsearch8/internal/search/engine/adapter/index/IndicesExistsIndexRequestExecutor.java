/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.transport.endpoints.BooleanResponse;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;

import java.io.IOException;

/**
 * @author Michael C. Han
 */
public class IndicesExistsIndexRequestExecutor {

	public IndicesExistsIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public IndicesExistsIndexResponse execute(
		IndicesExistsIndexRequest indicesExistsIndexRequest) {

		BooleanResponse booleanResponse = _indicesExists(
			createExistsRequest(indicesExistsIndexRequest),
			indicesExistsIndexRequest);

		return new IndicesExistsIndexResponse(booleanResponse.value());
	}

	protected ExistsRequest createExistsRequest(
		IndicesExistsIndexRequest indicesExistsIndexRequest) {

		return ExistsRequest.of(
			existsRequest -> existsRequest.index(
				ListUtil.fromArray(indicesExistsIndexRequest.getIndexNames())));
	}

	private BooleanResponse _indicesExists(
		ExistsRequest existsRequest,
		IndicesExistsIndexRequest indicesExistsIndexRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				indicesExistsIndexRequest.getConnectionId(),
				indicesExistsIndexRequest.isPreferLocalCluster());

		ElasticsearchIndicesClient elasticsearchIndicesClient =
			elasticsearchClient.indices();

		try {
			return elasticsearchIndicesClient.exists(existsRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}