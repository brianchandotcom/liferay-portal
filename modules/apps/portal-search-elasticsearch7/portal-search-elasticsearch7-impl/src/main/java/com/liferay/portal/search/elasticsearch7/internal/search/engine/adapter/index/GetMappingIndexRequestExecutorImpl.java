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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.GetMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetMappingIndexResponse;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.compress.CompressedXContent;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(immediate = true, service = GetMappingIndexRequestExecutor.class)
public class GetMappingIndexRequestExecutorImpl
	implements GetMappingIndexRequestExecutor {

	@Override
	public GetMappingIndexResponse execute(
		GetMappingIndexRequest getMappingIndexRequest) {

		GetMappingsRequest getMappingsRequest = createGetMappingsRequest(
			getMappingIndexRequest);

		GetMappingsResponse getMappingsResponse = _getGetMappingsResponse(
			getMappingsRequest, getMappingIndexRequest);

		Map<String, MappingMetadata> mappings = getMappingsResponse.mappings();

		Map<String, String> indexMappings = new HashMap<>();

		for (String indexName : getMappingIndexRequest.getIndexNames()) {
			MappingMetadata mappingMetadata = mappings.get(indexName);

			CompressedXContent mappingContent = mappingMetadata.source();

			indexMappings.put(indexName, mappingContent.toString());
		}

		return new GetMappingIndexResponse(indexMappings);
	}

	protected GetMappingsRequest createGetMappingsRequest(
		GetMappingIndexRequest getMappingIndexRequest) {

		GetMappingsRequest getMappingsRequest = new GetMappingsRequest();

		getMappingsRequest.indices(getMappingIndexRequest.getIndexNames());

		return getMappingsRequest;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private GetMappingsResponse _getGetMappingsResponse(
		GetMappingsRequest getMappingsRequest,
		GetMappingIndexRequest getMappingIndexRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				getMappingIndexRequest.getConnectionId(),
				getMappingIndexRequest.isPreferLocalCluster());

		IndicesClient indicesClient = restHighLevelClient.indices();

		try {
			return indicesClient.getMapping(
				getMappingsRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}