/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.search.ClearScrollRequest;
import com.liferay.portal.search.engine.adapter.search.ClearScrollResponse;

/**
 * @author Gustavo Lima
 */
public class ClearScrollRequestExecutor {

	public ClearScrollRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public ClearScrollResponse execute(ClearScrollRequest clearScrollRequest) {
		co.elastic.clients.elasticsearch.core.ClearScrollResponse
			clearScrollResponse = _getClearScrollResponse(
				clearScrollRequest,
				createClearScrollRequest(clearScrollRequest));

		return new ClearScrollResponse(clearScrollResponse.numFreed());
	}

	protected co.elastic.clients.elasticsearch.core.ClearScrollRequest
		createClearScrollRequest(ClearScrollRequest clearScrollRequest) {

		return co.elastic.clients.elasticsearch.core.ClearScrollRequest.of(
			elasticsearchClearScrollRequest ->
				elasticsearchClearScrollRequest.scrollId(
					clearScrollRequest.getScrollId()));
	}

	private co.elastic.clients.elasticsearch.core.ClearScrollResponse
		_getClearScrollResponse(
			ClearScrollRequest clearScrollRequest,
			co.elastic.clients.elasticsearch.core.ClearScrollRequest
				elasticsearchClearScrollRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				clearScrollRequest.getConnectionId(),
				clearScrollRequest.isPreferLocalCluster());

		try {
			return elasticsearchClient.clearScroll(
				elasticsearchClearScrollRequest);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception.getMessage(), exception);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}