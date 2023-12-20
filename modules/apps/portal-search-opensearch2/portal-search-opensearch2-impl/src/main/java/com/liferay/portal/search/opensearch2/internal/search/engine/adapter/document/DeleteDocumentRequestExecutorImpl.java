/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentResponse;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.util.ConversionUtil;

import java.io.IOException;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.Result;
import org.opensearch.client.opensearch.core.DeleteRequest;
import org.opensearch.client.opensearch.core.DeleteResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 * @author Petteri Karttunen
 */
@Component(service = DeleteDocumentRequestExecutor.class)
public class DeleteDocumentRequestExecutorImpl
	implements DeleteDocumentRequestExecutor {

	@Override
	public DeleteDocumentResponse execute(
		DeleteDocumentRequest deleteDocumentRequest) {

		DeleteResponse deleteResponse = _getDeleteResponse(
			deleteDocumentRequest,
			_openSearchDocumentRequestTranslator.translate(
				deleteDocumentRequest));

		Result result = deleteResponse.result();

		return new DeleteDocumentResponse(
			ConversionUtil.toHttpStatusCode(result), result.jsonValue());
	}

	private DeleteResponse _getDeleteResponse(
		DeleteDocumentRequest deleteDocumentRequest,
		DeleteRequest deleteRequest) {

		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient(
				deleteDocumentRequest.getConnectionId(),
				deleteDocumentRequest.isPreferLocalCluster());

		try {
			return openSearchClient.delete(deleteRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private OpenSearchConnectionManager _openSearchConnectionManager;

	@Reference
	private OpenSearchDocumentRequestTranslator
		_openSearchDocumentRequestTranslator;

}