/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.TimeUnit;
import co.elastic.clients.elasticsearch._types.WaitForActiveShards;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.OpenRequest;
import co.elastic.clients.elasticsearch.indices.OpenResponse;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.IndicesOptions;
import com.liferay.portal.search.engine.adapter.index.OpenIndexRequest;
import com.liferay.portal.search.engine.adapter.index.OpenIndexResponse;

import java.io.IOException;

/**
 * @author Michael C. Han
 */
public class OpenIndexRequestExecutor {

	public OpenIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public OpenIndexResponse execute(OpenIndexRequest openIndexRequest) {
		OpenResponse openResponse = _getOpenResponse(
			openIndexRequest, createOpenRequest(openIndexRequest));

		return new OpenIndexResponse(openResponse.acknowledged());
	}

	protected OpenRequest createOpenRequest(OpenIndexRequest openIndexRequest) {
		OpenRequest.Builder builder = new OpenRequest.Builder();

		IndicesOptions indicesOptions = openIndexRequest.getIndicesOptions();

		if (indicesOptions != null) {
			builder.allowNoIndices(indicesOptions.isAllowNoIndices());
			builder.ignoreUnavailable(indicesOptions.isIgnoreUnavailable());
		}

		builder.index(ListUtil.fromArray(openIndexRequest.getIndexNames()));

		if (openIndexRequest.getTimeout() > 0) {
			Time time = Time.of(
				openSearchTime -> openSearchTime.time(
					openIndexRequest.getTimeout() +
						TimeUnit.Milliseconds.jsonValue()));

			builder.masterTimeout(time);
			builder.timeout(time);
		}

		if (openIndexRequest.getWaitForActiveShards() > 0) {
			builder.waitForActiveShards(
				WaitForActiveShards.of(
					waitForActiveShards -> waitForActiveShards.count(
						openIndexRequest.getWaitForActiveShards())));
		}

		return builder.build();
	}

	private OpenResponse _getOpenResponse(
		OpenIndexRequest openIndexRequest, OpenRequest openRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				openIndexRequest.getConnectionId(),
				openIndexRequest.isPreferLocalCluster());

		ElasticsearchIndicesClient elasticsearchIndicesClient =
			elasticsearchClient.indices();

		try {
			return elasticsearchIndicesClient.open(openRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}