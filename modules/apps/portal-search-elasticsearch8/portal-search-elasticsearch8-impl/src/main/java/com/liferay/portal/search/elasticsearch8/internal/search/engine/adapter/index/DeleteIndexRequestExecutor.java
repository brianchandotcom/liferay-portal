/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexResponse;
import com.liferay.portal.search.engine.adapter.index.IndicesOptions;

import java.io.IOException;

/**
 * @author Michael C. Han
 */
public class DeleteIndexRequestExecutor {

	public DeleteIndexRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public DeleteIndexResponse execute(DeleteIndexRequest deleteIndexRequest) {
		co.elastic.clients.elasticsearch.indices.DeleteIndexResponse
			deleteIndexResponse = _getDeleteIndexResponse(
				createDeleteIndexRequest(deleteIndexRequest),
				deleteIndexRequest);

		return new DeleteIndexResponse(deleteIndexResponse.acknowledged());
	}

	protected co.elastic.clients.elasticsearch.indices.DeleteIndexRequest
		createDeleteIndexRequest(DeleteIndexRequest deleteIndexRequest) {

		co.elastic.clients.elasticsearch.indices.DeleteIndexRequest.Builder
			builder =
				new co.elastic.clients.elasticsearch.indices.DeleteIndexRequest.
					Builder();

		IndicesOptions indicesOptions = deleteIndexRequest.getIndicesOptions();

		if (indicesOptions != null) {
			builder.allowNoIndices(indicesOptions.isAllowNoIndices());
			builder.ignoreUnavailable(indicesOptions.isIgnoreUnavailable());
		}

		builder.index(ListUtil.fromArray(deleteIndexRequest.getIndexNames()));

		return builder.build();
	}

	private co.elastic.clients.elasticsearch.indices.DeleteIndexResponse
		_getDeleteIndexResponse(
			co.elastic.clients.elasticsearch.indices.DeleteIndexRequest
				elasticsearchDeleteIndexRequest,
			DeleteIndexRequest deleteIndexRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				deleteIndexRequest.getConnectionId(),
				deleteIndexRequest.isPreferLocalCluster());

		ElasticsearchIndicesClient elasticsearchIndicesClient =
			elasticsearchClient.indices();

		try {
			return elasticsearchIndicesClient.delete(
				elasticsearchDeleteIndexRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}