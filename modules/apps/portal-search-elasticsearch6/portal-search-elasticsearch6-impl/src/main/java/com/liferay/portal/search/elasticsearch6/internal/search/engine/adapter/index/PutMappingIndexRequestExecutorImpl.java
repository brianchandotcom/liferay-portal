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

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.engine.adapter.index.PutMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.PutMappingIndexResponse;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.xcontent.XContentType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(immediate = true, service = PutMappingIndexRequestExecutor.class)
public class PutMappingIndexRequestExecutorImpl
	implements PutMappingIndexRequestExecutor {

	@Override
	public PutMappingIndexResponse execute(
		PutMappingIndexRequest putMappingIndexRequest) {

		PutMappingRequestBuilder putMappingRequestBuilder =
			createPutMappingRequestBuilder(putMappingIndexRequest);

		PutMappingResponse putMappingResponse = putMappingRequestBuilder.get();

		return new PutMappingIndexResponse(putMappingResponse.isAcknowledged());
	}

	protected PutMappingRequestBuilder createPutMappingRequestBuilder(
		PutMappingIndexRequest putMappingIndexRequest) {

		AdminClient adminClient =
			elasticsearchConnectionManager.getAdminClient();

		IndicesAdminClient indicesAdminClient = adminClient.indices();

		PutMappingRequestBuilder putMappingRequestBuilder =
			indicesAdminClient.preparePutMapping(
				putMappingIndexRequest.getIndexNames());

		putMappingRequestBuilder.setSource(
			putMappingIndexRequest.getMapping(), XContentType.JSON);

		putMappingRequestBuilder.setType(
			putMappingIndexRequest.getMappingName());

		return putMappingRequestBuilder;
	}

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

}