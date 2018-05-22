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

package com.liferay.portal.search.elasticsearch6.internal.adapter.index;

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetFieldMappingIndexResponse;

import java.util.Map;

import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsResponse;
import org.elasticsearch.client.AdminClient;
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

		Map<String,
			Map<String,
				Map<String, GetFieldMappingsResponse.FieldMappingMetaData>>>
					mappings = getFieldMappingsResponse.mappings();

		//TODO This needs to be broken down to JSON
		Map<String, Map<String, GetFieldMappingsResponse.FieldMappingMetaData>>
			fieldMappingsMetaData = mappings.get(
				getFieldMappingIndexRequest.getIndexName());

		Map<String, GetFieldMappingsResponse.FieldMappingMetaData>
			fieldMappings = fieldMappingsMetaData.get(
				getFieldMappingIndexRequest.getMappingName());

		return new GetFieldMappingIndexResponse(fieldMappings.toString());
	}

	protected GetFieldMappingsRequestBuilder
		createGetFieldMappingsRequestBuilder(
			GetFieldMappingIndexRequest getFieldMappingIndexRequest) {

		AdminClient adminClient =
			elasticsearchConnectionManager.getAdminClient();

		IndicesAdminClient indicesAdminClient = adminClient.indices();

		GetFieldMappingsRequestBuilder getFieldMappingsRequestBuilder =
			indicesAdminClient.prepareGetFieldMappings(
				getFieldMappingIndexRequest.getIndexName());

		getFieldMappingsRequestBuilder.setTypes(
			getFieldMappingIndexRequest.getMappingName());

		getFieldMappingsRequestBuilder.setFields(
			getFieldMappingIndexRequest.getFields());

		return getFieldMappingsRequestBuilder;
	}

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

}