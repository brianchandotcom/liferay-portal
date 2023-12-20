/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.util.ConversionUtil;

import java.io.IOException;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.Result;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.IndexResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 * @author Petteri Karttunen
 */
@Component(service = IndexDocumentRequestExecutor.class)
public class IndexDocumentRequestExecutorImpl
	implements IndexDocumentRequestExecutor {

	@Override
	public IndexDocumentResponse execute(
		IndexDocumentRequest indexDocumentRequest) {

		IndexResponse indexResponse = _getIndexResponse(
			indexDocumentRequest,
			_openSearchDocumentRequestTranslator.translate(
				indexDocumentRequest));

		Result result = indexResponse.result();

		return new IndexDocumentResponse(
			ConversionUtil.toHttpStatusCode(result), result.jsonValue(),
			indexResponse.id());
	}

	private IndexResponse _getIndexResponse(
		IndexDocumentRequest indexDocumentRequest,
		IndexRequest<JsonData> indexRequest) {

		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient(
				indexDocumentRequest.getConnectionId(),
				indexDocumentRequest.isPreferLocalCluster());

		try {
			return openSearchClient.index(indexRequest);
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