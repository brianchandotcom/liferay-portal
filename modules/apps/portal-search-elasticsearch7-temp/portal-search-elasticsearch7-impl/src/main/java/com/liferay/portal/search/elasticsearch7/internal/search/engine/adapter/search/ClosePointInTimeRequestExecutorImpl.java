/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			closePointInTimeSearchRequest.getPointInTimeId());
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