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
import com.liferay.portal.search.engine.adapter.search.ClearScrollRequest;
import com.liferay.portal.search.engine.adapter.search.ClearScrollResponse;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 */
@Component(service = ClearScrollRequestExecutor.class)
public class ClearScrollRequestExecutorImpl
	implements ClearScrollRequestExecutor {

	@Override
	public ClearScrollResponse execute(ClearScrollRequest clearScrollRequest) {
		org.elasticsearch.action.search.ClearScrollRequest
			elasticsearchClearScrollRequest = createClearScrollRequest(
				clearScrollRequest);

		org.elasticsearch.action.search.ClearScrollResponse
			clearScrollResponse = getClearScrollResponse(
				clearScrollRequest, elasticsearchClearScrollRequest);

		return new ClearScrollResponse(clearScrollResponse.getNumFreed());
	}

	protected org.elasticsearch.action.search.ClearScrollRequest
		createClearScrollRequest(ClearScrollRequest clearScrollRequest) {

		org.elasticsearch.action.search.ClearScrollRequest
			elasticsearchClearScrollRequest =
				new org.elasticsearch.action.search.ClearScrollRequest();

		elasticsearchClearScrollRequest.addScrollId(
			clearScrollRequest.getScrollId());

		return elasticsearchClearScrollRequest;
	}

	protected org.elasticsearch.action.search.ClearScrollResponse
		getClearScrollResponse(
			ClearScrollRequest clearScrollRequest,
			org.elasticsearch.action.search.ClearScrollRequest
				elasticsearchClearScrollRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				clearScrollRequest.getConnectionId(),
				clearScrollRequest.isPreferLocalCluster());

		try {
			return restHighLevelClient.clearScroll(
				elasticsearchClearScrollRequest, RequestOptions.DEFAULT);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception.getMessage(), exception);
		}
	}

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}