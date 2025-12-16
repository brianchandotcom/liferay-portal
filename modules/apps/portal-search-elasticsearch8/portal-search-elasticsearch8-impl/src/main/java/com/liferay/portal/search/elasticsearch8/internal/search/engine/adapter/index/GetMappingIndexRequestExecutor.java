/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.GetMappingRequest;
import co.elastic.clients.elasticsearch.indices.GetMappingResponse;
import co.elastic.clients.elasticsearch.indices.get_mapping.IndexMappingRecord;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.util.JsonpUtil;
import com.liferay.portal.search.engine.adapter.index.GetMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetMappingIndexResponse;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dylan Rebelak
 */
public class GetMappingIndexRequestExecutor {

	public GetMappingIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public GetMappingIndexResponse execute(
		GetMappingIndexRequest getMappingIndexRequest) {

		GetMappingResponse getMappingResponse = _getGetMappingResponse(
			getMappingIndexRequest,
			createGetMappingRequest(getMappingIndexRequest));

		Map<String, IndexMappingRecord> indexMappingRecords =
			getMappingResponse.result();

		Map<String, String> indexMappings = new HashMap<>();

		for (String indexName : getMappingIndexRequest.getIndexNames()) {
			IndexMappingRecord indexMappingRecord = indexMappingRecords.get(
				indexName);

			indexMappings.put(
				indexName, JsonpUtil.toString(indexMappingRecord.mappings()));
		}

		return new GetMappingIndexResponse(indexMappings);
	}

	protected GetMappingRequest createGetMappingRequest(
		GetMappingIndexRequest getMappingIndexRequest) {

		return GetMappingRequest.of(
			getMappingRequest -> getMappingRequest.index(
				ListUtil.fromArray(getMappingIndexRequest.getIndexNames())));
	}

	private GetMappingResponse _getGetMappingResponse(
		GetMappingIndexRequest getMappingIndexRequest,
		GetMappingRequest getMappingRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				getMappingIndexRequest.getConnectionId(),
				getMappingIndexRequest.isPreferLocalCluster());

		ElasticsearchIndicesClient elasticsearchIndicesClient =
			elasticsearchClient.indices();

		try {
			return elasticsearchIndicesClient.getMapping(getMappingRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}