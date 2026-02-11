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
import co.elastic.clients.util.NamedValue;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
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
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public PutMappingIndexResponse execute(
		PutMappingIndexRequest putMappingIndexRequest) {

		PutMappingResponse putMappingResponse = _getPutMappingResponse(
			putMappingIndexRequest,
			createPutMappingRequest(putMappingIndexRequest));

		return new PutMappingIndexResponse(putMappingResponse.acknowledged());
	}

	protected PutMappingRequest createPutMappingRequest(
		PutMappingIndexRequest putMappingIndexRequest) {

		PutMappingRequest.Builder builder = new PutMappingRequest.Builder();

		try {
			JSONObject mappingJSONObject = JSONFactoryUtil.createJSONObject(
				putMappingIndexRequest.getMapping());

			List<NamedValue<DynamicTemplate>> dynamicTemplates =
				IndexUtil.getDynamicTemplatesList(mappingJSONObject);

			if (dynamicTemplates != null) {
				builder.dynamicTemplates(dynamicTemplates);
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

	private PutMappingResponse _getPutMappingResponse(
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

}