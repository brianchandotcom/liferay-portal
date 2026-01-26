/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.document;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.util.ConversionUtil;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentResponse;

import java.io.IOException;

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

		DeleteResponse deleteResponse = _getDeleteResponse(
			deleteDocumentRequest,
			ElasticsearchDocumentRequestTranslatorUtil.translate(
				deleteDocumentRequest));

		Result result = deleteResponse.result();

		return new DeleteDocumentResponse(
			ConversionUtil.toHttpStatusCode(result), result.jsonValue());
	}

	private DeleteResponse _getDeleteResponse(
		DeleteDocumentRequest deleteDocumentRequest,
		DeleteRequest deleteRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				deleteDocumentRequest.getConnectionId(),
				deleteDocumentRequest.isPreferLocalCluster());

		try {
			return elasticsearchClient.delete(deleteRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}