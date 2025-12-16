/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.DynamicTemplate;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.PutMappingRequest;
import co.elastic.clients.elasticsearch.indices.PutMappingResponse;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.util.IndexUtil;
import com.liferay.portal.search.engine.adapter.index.PutMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.PutMappingIndexResponse;

import java.io.IOException;

import java.util.List;
import java.util.Map;

/**
 * @author Dylan Rebelak
 */
public class PutMappingIndexRequestExecutor {

	public PutMappingIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver,
		JSONFactory jsonFactory) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
		_jsonFactory = jsonFactory;
	}

	public PutMappingIndexResponse execute(
		PutMappingIndexRequest putMappingIndexRequest) {

		PutMappingResponse putMappingResponse = getPutMappingResponse(
			putMappingIndexRequest,
			createPutMappingRequest(putMappingIndexRequest));

		return new PutMappingIndexResponse(putMappingResponse.acknowledged());
	}

	protected PutMappingRequest createPutMappingRequest(
		PutMappingIndexRequest putMappingIndexRequest) {

		PutMappingRequest.Builder builder = new PutMappingRequest.Builder();

		try {
			JSONObject mappingJSONObject = _jsonFactory.createJSONObject(
				putMappingIndexRequest.getMapping());

			List<Map<String, DynamicTemplate>> dynamicTemplates =
				IndexUtil.getDynamicTemplatesMap(mappingJSONObject);

			if (dynamicTemplates != null) {
				builder.dynamicTemplates(null);
			}

			builder.index(
				ListUtil.fromArray(putMappingIndexRequest.getIndexNames()));

			Map<String, Property> properties = IndexUtil.getPropertiesMap(
				mappingJSONObject);

			if (properties != null) {
				builder.properties(properties);
			}

			return builder.build();
		}
		catch (JSONException jsonException) {
			throw new RuntimeException(jsonException);
		}
	}

	protected PutMappingResponse getPutMappingResponse(
		PutMappingIndexRequest putMappingIndexRequest,
		PutMappingRequest putMappingRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				putMappingIndexRequest.getConnectionId(),
				putMappingIndexRequest.isPreferLocalCluster());

		ElasticsearchIndicesClient elasticsearchIndicesClient =
			elasticsearchClient.indices();

		try {
			return elasticsearchIndicesClient.putMapping(putMappingRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;
	private final JSONFactory _jsonFactory;

}