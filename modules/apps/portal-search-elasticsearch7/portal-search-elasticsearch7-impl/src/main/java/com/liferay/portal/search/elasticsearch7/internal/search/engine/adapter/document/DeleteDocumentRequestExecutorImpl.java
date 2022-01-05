/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentResponse;

import java.io.IOException;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(immediate = true, service = DeleteDocumentRequestExecutor.class)
public class DeleteDocumentRequestExecutorImpl
	implements DeleteDocumentRequestExecutor {

	@Override
	public DeleteDocumentResponse execute(
		DeleteDocumentRequest deleteDocumentRequest) {

		DeleteRequest deleteRequest =
			_elasticsearchBulkableDocumentRequestTranslator.translate(
				deleteDocumentRequest);

		DeleteResponse deleteResponse = _getDeleteResponse(
			deleteRequest, deleteDocumentRequest);

		RestStatus restStatus = deleteResponse.status();

		return new DeleteDocumentResponse(restStatus.getStatus());
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setBulkableDocumentRequestTranslator(
		ElasticsearchBulkableDocumentRequestTranslator
			elasticsearchBulkableDocumentRequestTranslator) {

		_elasticsearchBulkableDocumentRequestTranslator =
			elasticsearchBulkableDocumentRequestTranslator;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private DeleteResponse _getDeleteResponse(
		DeleteRequest deleteRequest,
		DeleteDocumentRequest deleteDocumentRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				deleteDocumentRequest.getConnectionId(),
				deleteDocumentRequest.isPreferLocalCluster());

		try {
			return restHighLevelClient.delete(
				deleteRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private ElasticsearchBulkableDocumentRequestTranslator
		_elasticsearchBulkableDocumentRequestTranslator;
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}