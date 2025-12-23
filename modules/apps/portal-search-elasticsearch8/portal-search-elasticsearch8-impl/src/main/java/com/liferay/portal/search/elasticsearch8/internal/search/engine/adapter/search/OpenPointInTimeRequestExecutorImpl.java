/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.TimeUnit;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.search.OpenPointInTimeRequest;
import com.liferay.portal.search.engine.adapter.search.OpenPointInTimeResponse;

import java.io.IOException;

import java.util.Arrays;

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

		co.elastic.clients.elasticsearch.core.OpenPointInTimeResponse
			openPointInTimeResponse = _getOpenPointInTimeResponse(
				_createOpenPointInTimeRequest(openPointInTimeRequest),
				openPointInTimeRequest);

		return new OpenPointInTimeResponse(openPointInTimeResponse.id());
	}

	private co.elastic.clients.elasticsearch.core.OpenPointInTimeRequest
		_createOpenPointInTimeRequest(
			OpenPointInTimeRequest openPointInTimeRequest) {

		co.elastic.clients.elasticsearch.core.OpenPointInTimeRequest.Builder
			builder =
				new co.elastic.clients.elasticsearch.core.
					OpenPointInTimeRequest.Builder();

		builder.keepAlive(
			Time.of(
				time -> time.time(
					openPointInTimeRequest.getKeepAliveMinutes() +
						TimeUnit.Minutes.jsonValue())));

		if (openPointInTimeRequest.getIndices() != null) {
			builder.index(Arrays.asList(openPointInTimeRequest.getIndices()));
		}

		return builder.build();
	}

	private co.elastic.clients.elasticsearch.core.OpenPointInTimeResponse
		_getOpenPointInTimeResponse(
			co.elastic.clients.elasticsearch.core.OpenPointInTimeRequest
				elasticsearchOpenPointInTimeRequest,
			OpenPointInTimeRequest openPointInTimeRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				openPointInTimeRequest.getConnectionId(),
				openPointInTimeRequest.isPreferLocalCluster());

		try {
			return elasticsearchClient.openPointInTime(
				elasticsearchOpenPointInTimeRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}