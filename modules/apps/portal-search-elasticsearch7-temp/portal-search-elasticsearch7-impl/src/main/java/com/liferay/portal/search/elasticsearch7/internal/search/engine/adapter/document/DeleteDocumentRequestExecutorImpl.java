/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = DeleteDocumentRequestExecutor.class)
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

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	private ElasticsearchBulkableDocumentRequestTranslator
		_elasticsearchBulkableDocumentRequestTranslator;

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}