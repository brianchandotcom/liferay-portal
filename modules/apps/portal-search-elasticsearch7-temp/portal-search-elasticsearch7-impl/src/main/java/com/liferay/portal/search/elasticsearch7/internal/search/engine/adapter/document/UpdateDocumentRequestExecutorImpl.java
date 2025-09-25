/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentResponse;

import java.io.IOException;

import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(service = UpdateDocumentRequestExecutor.class)
public class UpdateDocumentRequestExecutorImpl
	implements UpdateDocumentRequestExecutor {

	@Override
	public UpdateDocumentResponse execute(
		UpdateDocumentRequest updateDocumentRequest) {

		UpdateRequest updateRequest =
			_elasticsearchBulkableDocumentRequestTranslator.translate(
				updateDocumentRequest);

		UpdateResponse updateResponse = _getUpdateResponse(
			updateRequest, updateDocumentRequest);

		RestStatus restStatus = updateResponse.status();

		return new UpdateDocumentResponse(restStatus.getStatus());
	}

	private UpdateResponse _getUpdateResponse(
		UpdateRequest updateRequest,
		UpdateDocumentRequest updateDocumentRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				updateDocumentRequest.getConnectionId(),
				updateDocumentRequest.isPreferLocalCluster());

		try {
			return restHighLevelClient.update(
				updateRequest, RequestOptions.DEFAULT);
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