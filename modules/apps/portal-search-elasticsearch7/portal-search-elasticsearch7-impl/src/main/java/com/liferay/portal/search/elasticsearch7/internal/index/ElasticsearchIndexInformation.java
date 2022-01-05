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

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.index.IndexInformation;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.Strings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true, service = IndexInformation.class)
public class ElasticsearchIndexInformation implements IndexInformation {

	@Override
	public String getCompanyIndexName(long companyId) {
		return _indexNameBuilder.getIndexName(companyId);
	}

	@Override
	public String getFieldMappings(String indexName) {
		GetMappingsRequest getMappingsRequest = new GetMappingsRequest();

		getMappingsRequest.indices(indexName);

		GetMappingsResponse getMappingsResponse = getMappingsResponse(
			getMappingsRequest);

		return Strings.toString(getMappingsResponse, true, true);
	}

	@Override
	public String[] getIndexNames() {
		GetIndexRequest getIndexRequest = new GetIndexRequest(StringPool.STAR);

		GetIndexResponse getIndexResponse = getIndexResponse(getIndexRequest);

		return getIndexResponse.getIndices();
	}

	protected GetIndexResponse getIndexResponse(
		GetIndexRequest getIndexRequest) {

		IndicesClient indicesClient = _getIndicesClient();

		try {
			return indicesClient.get(getIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected GetMappingsResponse getMappingsResponse(
		GetMappingsRequest getMappingsRequest) {

		IndicesClient indicesClient = _getIndicesClient();

		try {
			return indicesClient.getMapping(
				getMappingsRequest, RequestOptions.DEFAULT);
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

	@Reference(unbind = "-")
	protected void setIndexNameBuilder(IndexNameBuilder indexNameBuilder) {
		_indexNameBuilder = indexNameBuilder;
	}

	private IndicesClient _getIndicesClient() {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(null, true);

		return restHighLevelClient.indices();
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private IndexNameBuilder _indexNameBuilder;

}