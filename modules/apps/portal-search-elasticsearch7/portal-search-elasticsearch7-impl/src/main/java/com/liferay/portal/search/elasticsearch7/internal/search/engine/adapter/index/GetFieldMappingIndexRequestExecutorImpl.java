/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexResponse;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetFieldMappingsRequest;
import org.elasticsearch.client.indices.GetFieldMappingsResponse;
import org.elasticsearch.client.indices.GetFieldMappingsResponse.FieldMappingMetadata;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(service = GetFieldMappingIndexRequestExecutor.class)
public class GetFieldMappingIndexRequestExecutorImpl
	implements GetFieldMappingIndexRequestExecutor {

	@Override
	public GetFieldMappingIndexResponse execute(
		GetFieldMappingIndexRequest getFieldMappingIndexRequest) {

		GetFieldMappingsRequest getFieldMappingsRequest =
			createGetFieldMappingsRequest(getFieldMappingIndexRequest);

		GetFieldMappingsResponse getFieldMappingsResponse =
			_getGetFieldMappingsResponse(
				getFieldMappingsRequest, getFieldMappingIndexRequest);

		Map<String, Map<String, FieldMappingMetadata>> mappings =
			getFieldMappingsResponse.mappings();

		Map<String, String> fieldMappings = new HashMap<>();

		for (String indexName : getFieldMappingIndexRequest.getIndexNames()) {
			Map<String, FieldMappingMetadata> map = mappings.get(indexName);

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			for (String fieldName : getFieldMappingIndexRequest.getFields()) {
				FieldMappingMetadata fieldMappingMetadata = map.get(fieldName);

				Map<String, Object> source = fieldMappingMetadata.sourceAsMap();

				jsonObject.put(fieldName, source);
			}

			fieldMappings.put(indexName, jsonObject.toString());
		}

		return new GetFieldMappingIndexResponse(fieldMappings);
	}

	protected GetFieldMappingsRequest createGetFieldMappingsRequest(
		GetFieldMappingIndexRequest getFieldMappingIndexRequest) {

		GetFieldMappingsRequest getFieldMappingsRequest =
			new GetFieldMappingsRequest();

		getFieldMappingsRequest.fields(getFieldMappingIndexRequest.getFields());
		getFieldMappingsRequest.indices(
			getFieldMappingIndexRequest.getIndexNames());

		return getFieldMappingsRequest;
	}

	private GetFieldMappingsResponse _getGetFieldMappingsResponse(
		GetFieldMappingsRequest getFieldMappingsRequest,
		GetFieldMappingIndexRequest getFieldMappingIndexRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				getFieldMappingIndexRequest.getConnectionId(),
				getFieldMappingIndexRequest.isPreferLocalCluster());

		IndicesClient indicesClient = restHighLevelClient.indices();

		try {
			return indicesClient.getFieldMapping(
				getFieldMappingsRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

	@Reference
	private JSONFactory _jsonFactory;

}