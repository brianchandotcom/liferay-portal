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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.search;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.search.OpenPointInTimeRequest;
import com.liferay.portal.search.engine.adapter.search.OpenPointInTimeResponse;

import java.io.IOException;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = OpenPointInTimeRequestExecutor.class)
public class OpenPointInTimeRequestExecutorImpl
	implements OpenPointInTimeRequestExecutor {

	@Override
	public OpenPointInTimeResponse execute(
		OpenPointInTimeRequest openPointInTimeRequest) {

		org.elasticsearch.action.search.OpenPointInTimeRequest
			elasticsearchOpenPointInTimeRequest = createOpenPointInTimeRequest(
				openPointInTimeRequest);

		org.elasticsearch.action.search.OpenPointInTimeResponse
			openPointInTimeResponse = getOpenPointInTimeResponse(
				elasticsearchOpenPointInTimeRequest, openPointInTimeRequest);

		return new OpenPointInTimeResponse(
			openPointInTimeResponse.getPointInTimeId());
	}

	protected org.elasticsearch.action.search.OpenPointInTimeRequest
		createOpenPointInTimeRequest(
			OpenPointInTimeRequest openPointInTimeRequest) {

		org.elasticsearch.action.search.OpenPointInTimeRequest
			elasticsearchOpenPointInTimeRequest =
				new org.elasticsearch.action.search.OpenPointInTimeRequest();

		if (openPointInTimeRequest.getIndices() != null) {
			elasticsearchOpenPointInTimeRequest.indices(
				openPointInTimeRequest.getIndices());
		}

		elasticsearchOpenPointInTimeRequest.keepAlive(
			TimeValue.timeValueMinutes(
				openPointInTimeRequest.getKeepAliveMinutes()));

		return elasticsearchOpenPointInTimeRequest;
	}

	protected org.elasticsearch.action.search.OpenPointInTimeResponse
		getOpenPointInTimeResponse(
			org.elasticsearch.action.search.OpenPointInTimeRequest
				elasticsearchOpenPointInTimeRequest,
			OpenPointInTimeRequest openPointInTimeRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				openPointInTimeRequest.getConnectionId(),
				openPointInTimeRequest.isPreferLocalCluster());

		try {
			return restHighLevelClient.openPointInTime(
				elasticsearchOpenPointInTimeRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}