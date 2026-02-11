/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.document;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.JsonData;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.util.ConversionUtil;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;

import java.io.IOException;

/**
 * @author Dylan Rebelak
 */
public class IndexDocumentRequestExecutor {

	public IndexDocumentRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public IndexDocumentResponse execute(
		IndexDocumentRequest indexDocumentRequest) {

		IndexResponse indexResponse = _getIndexResponse(
			indexDocumentRequest,
			ElasticsearchDocumentRequestTranslatorUtil.translate(
				indexDocumentRequest));

		Result result = indexResponse.result();

		return new IndexDocumentResponse(
			ConversionUtil.toHttpStatusCode(result), result.jsonValue(),
			indexResponse.id());
	}

	private IndexResponse _getIndexResponse(
		IndexDocumentRequest indexDocumentRequest,
		IndexRequest<JsonData> indexRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				indexDocumentRequest.getConnectionId(),
				indexDocumentRequest.isPreferLocalCluster());

		try {
			return elasticsearchClient.index(indexRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}