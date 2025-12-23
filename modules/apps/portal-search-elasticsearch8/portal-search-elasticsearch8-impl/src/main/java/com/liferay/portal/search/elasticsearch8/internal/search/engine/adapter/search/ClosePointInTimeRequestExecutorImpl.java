/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.search.ClosePointInTimeRequest;
import com.liferay.portal.search.engine.adapter.search.ClosePointInTimeResponse;

import java.io.IOException;

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

		co.elastic.clients.elasticsearch.core.ClosePointInTimeResponse
			closePointInTimeResponse = _getClosePointInTimeResponse(
				closePointInTimeRequest,
				_createClosePointInTimeRequest(closePointInTimeRequest));

		return new ClosePointInTimeResponse(
			closePointInTimeResponse.numFreed());
	}

	private co.elastic.clients.elasticsearch.core.ClosePointInTimeRequest
		_createClosePointInTimeRequest(
			ClosePointInTimeRequest closePointInTimeSearchRequest) {

		return co.elastic.clients.elasticsearch.core.ClosePointInTimeRequest.of(
			elasticsearchClosePointInTimeRequest ->
				elasticsearchClosePointInTimeRequest.id(
					closePointInTimeSearchRequest.getPointInTimeId()));
	}

	private co.elastic.clients.elasticsearch.core.ClosePointInTimeResponse
		_getClosePointInTimeResponse(
			ClosePointInTimeRequest closePointInTimeRequest,
			co.elastic.clients.elasticsearch.core.ClosePointInTimeRequest
				elasticsearchClosePointInTimeRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				closePointInTimeRequest.getConnectionId(),
				closePointInTimeRequest.isPreferLocalCluster());

		try {
			return elasticsearchClient.closePointInTime(
				elasticsearchClosePointInTimeRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}