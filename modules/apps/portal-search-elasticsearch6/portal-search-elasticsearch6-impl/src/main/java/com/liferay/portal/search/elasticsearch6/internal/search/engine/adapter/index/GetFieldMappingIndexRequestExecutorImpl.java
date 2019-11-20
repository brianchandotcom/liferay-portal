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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.index;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexResponse;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true, service = GetFieldMappingIndexRequestExecutor.class
)
public class GetFieldMappingIndexRequestExecutorImpl
	implements GetFieldMappingIndexRequestExecutor {

	@Override
	public GetFieldMappingIndexResponse execute(
		GetFieldMappingIndexRequest getFieldMappingIndexRequest) {

		GetFieldMappingsRequestBuilder getFieldMappingsRequestBuilder =
			createGetFieldMappingsRequestBuilder(getFieldMappingIndexRequest);

		GetFieldMappingsResponse getFieldMappingsResponse =
			getFieldMappingsRequestBuilder.get();

		Map
			<String,
			 Map
				 <String,
				  Map<String, GetFieldMappingsResponse.FieldMappingMetaData>>>
					mappings = getFieldMappingsResponse.mappings();

		Map<String, String> fieldMappings = new HashMap<>();

		for (String indexName : getFieldMappingIndexRequest.getIndexNames()) {
			Map
				<String,
				 Map<String, GetFieldMappingsResponse.FieldMappingMetaData>>
					map1 = mappings.get(indexName);

			Map<String, GetFieldMappingsResponse.FieldMappingMetaData> map2 =
				map1.get(getFieldMappingIndexRequest.getMappingName());

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			for (String fieldName : getFieldMappingIndexRequest.getFields()) {
				GetFieldMappingsResponse.FieldMappingMetaData
					fieldMappingMetaData = map2.get(fieldName);

				Map<String, Object> source = fieldMappingMetaData.sourceAsMap();

				jsonObject.put(fieldName, source);
			}

			fieldMappings.put(indexName, jsonObject.toString());
		}

		return new GetFieldMappingIndexResponse(fieldMappings);
	}

	protected GetFieldMappingsRequestBuilder
		createGetFieldMappingsRequestBuilder(
			GetFieldMappingIndexRequest getFieldMappingIndexRequest) {

		Client client = _elasticsearchClientResolver.getClient();

		AdminClient adminClient = client.admin();

		IndicesAdminClient indicesAdminClient = adminClient.indices();

		GetFieldMappingsRequestBuilder getFieldMappingsRequestBuilder =
			indicesAdminClient.prepareGetFieldMappings(
				getFieldMappingIndexRequest.getIndexNames());

		getFieldMappingsRequestBuilder.setFields(
			getFieldMappingIndexRequest.getFields());
		getFieldMappingsRequestBuilder.setTypes(
			getFieldMappingIndexRequest.getMappingName());

		return getFieldMappingsRequestBuilder;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}