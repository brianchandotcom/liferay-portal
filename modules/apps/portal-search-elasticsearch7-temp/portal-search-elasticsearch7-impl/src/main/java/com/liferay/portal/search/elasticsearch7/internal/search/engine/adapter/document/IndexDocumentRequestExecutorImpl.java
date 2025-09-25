/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;

import java.io.IOException;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(service = IndexDocumentRequestExecutor.class)
public class IndexDocumentRequestExecutorImpl
	implements IndexDocumentRequestExecutor {

	@Override
	public IndexDocumentResponse execute(
		IndexDocumentRequest indexDocumentRequest) {

		IndexRequest indexRequest =
			_elasticsearchBulkableDocumentRequestTranslator.translate(
				indexDocumentRequest);

		IndexResponse indexResponse = _getIndexResponse(
			indexRequest, indexDocumentRequest);

		RestStatus restStatus = indexResponse.status();

		return new IndexDocumentResponse(
			restStatus.getStatus(), indexResponse.getId());
	}

	private IndexResponse _getIndexResponse(
		IndexRequest indexRequest, IndexDocumentRequest indexDocumentRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				indexDocumentRequest.getConnectionId(),
				indexDocumentRequest.isPreferLocalCluster());

		try {
			return restHighLevelClient.index(
				indexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	private ElasticsearchBulkableDocumentRequestTranslator
		_elasticsearchBulkableDocumentRequestTranslator;

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}