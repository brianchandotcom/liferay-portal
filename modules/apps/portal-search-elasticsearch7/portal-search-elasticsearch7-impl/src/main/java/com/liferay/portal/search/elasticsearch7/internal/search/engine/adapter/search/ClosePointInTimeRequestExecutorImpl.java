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
import com.liferay.portal.search.engine.adapter.search.ClosePointInTimeRequest;
import com.liferay.portal.search.engine.adapter.search.ClosePointInTimeResponse;

import java.io.IOException;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = ClosePointInTimeRequestExecutor.class)
public class ClosePointInTimeRequestExecutorImpl
	implements ClosePointInTimeRequestExecutor {

	@Override
	public ClosePointInTimeResponse execute(
		ClosePointInTimeRequest closePointInTimeRequest) {

		org.elasticsearch.action.search.ClosePointInTimeRequest
			elasticsearchClosePointInTimeRequest =
				createClosePointInTimeRequest(closePointInTimeRequest);

		org.elasticsearch.action.search.ClosePointInTimeResponse
			closePointInTimeResponse = getClosePointInTimeResponse(
				elasticsearchClosePointInTimeRequest, closePointInTimeRequest);

		return new ClosePointInTimeResponse(
			closePointInTimeResponse.getNumFreed());
	}

	protected org.elasticsearch.action.search.ClosePointInTimeRequest
		createClosePointInTimeRequest(
			ClosePointInTimeRequest closePointInTimeSearchRequest) {

		return new org.elasticsearch.action.search.ClosePointInTimeRequest(
			closePointInTimeSearchRequest.getPitId());
	}

	protected org.elasticsearch.action.search.ClosePointInTimeResponse
		getClosePointInTimeResponse(
			org.elasticsearch.action.search.ClosePointInTimeRequest
				elasticsearchClosePointInTimeRequest,
			ClosePointInTimeRequest closePointInTimeRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				closePointInTimeRequest.getConnectionId(),
				closePointInTimeRequest.isPreferLocalCluster());

		try {
			return restHighLevelClient.closePointInTime(
				elasticsearchClosePointInTimeRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}