/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.document;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.json.JsonData;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.util.ConversionUtil;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentResponse;

import java.io.IOException;

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

		UpdateResponse<JsonData> updateResponse = _getUpdateResponse(
			updateDocumentRequest,
			_elasticsearchDocumentRequestTranslator.translate(
				updateDocumentRequest));

		Result result = updateResponse.result();

		return new UpdateDocumentResponse(
			ConversionUtil.toHttpStatusCode(result), result.jsonValue());
	}

	private UpdateResponse<JsonData> _getUpdateResponse(
		UpdateDocumentRequest updateDocumentRequest,
		UpdateRequest<JsonData, JsonData> updateRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				updateDocumentRequest.getConnectionId(),
				updateDocumentRequest.isPreferLocalCluster());

		try {
			return elasticsearchClient.update(updateRequest, JsonData.class);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

	@Reference(target = "(search.engine.impl=Elasticsearch)")
	private ElasticsearchDocumentRequestTranslator
		_elasticsearchDocumentRequestTranslator;

}