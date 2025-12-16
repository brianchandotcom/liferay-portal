/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.TimeUnit;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.CloseIndexRequest;
import com.liferay.portal.search.engine.adapter.index.CloseIndexResponse;
import com.liferay.portal.search.engine.adapter.index.IndicesOptions;

import java.io.IOException;

/**
 * @author Michael C. Han
 */
public class CloseIndexRequestExecutor {

	public CloseIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public CloseIndexResponse execute(CloseIndexRequest closeIndexRequest) {
		co.elastic.clients.elasticsearch.indices.CloseIndexResponse
			closeIndexResponse = getCloseIndexResponse(
				createCloseIndexRequest(closeIndexRequest), closeIndexRequest);

		return new CloseIndexResponse(closeIndexResponse.acknowledged());
	}

	protected co.elastic.clients.elasticsearch.indices.CloseIndexRequest
		createCloseIndexRequest(CloseIndexRequest closeIndexRequest) {

		co.elastic.clients.elasticsearch.indices.CloseIndexRequest.Builder
			builder =
				new co.elastic.clients.elasticsearch.indices.CloseIndexRequest.
					Builder();

		IndicesOptions indicesOptions = closeIndexRequest.getIndicesOptions();

		if (indicesOptions != null) {
			builder.allowNoIndices(indicesOptions.isAllowNoIndices());
			builder.ignoreUnavailable(indicesOptions.isIgnoreUnavailable());
		}

		builder.index(ListUtil.fromArray(closeIndexRequest.getIndexNames()));

		if (closeIndexRequest.getTimeout() > 0) {
			Time time = Time.of(
				elasticsearchTime -> elasticsearchTime.time(
					closeIndexRequest.getTimeout() +
						TimeUnit.Milliseconds.jsonValue()));

			builder.masterTimeout(time);
			builder.timeout(time);
		}

		return builder.build();
	}

	protected co.elastic.clients.elasticsearch.indices.CloseIndexResponse
		getCloseIndexResponse(
			co.elastic.clients.elasticsearch.indices.CloseIndexRequest
				elasticsearchCloseIndexRequest,
			CloseIndexRequest closeIndexRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				closeIndexRequest.getConnectionId(),
				closeIndexRequest.isPreferLocalCluster());

		ElasticsearchIndicesClient elasticsearchIndicesClient =
			elasticsearchClient.indices();

		try {
			return elasticsearchIndicesClient.close(
				elasticsearchCloseIndexRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}