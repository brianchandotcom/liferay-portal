/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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