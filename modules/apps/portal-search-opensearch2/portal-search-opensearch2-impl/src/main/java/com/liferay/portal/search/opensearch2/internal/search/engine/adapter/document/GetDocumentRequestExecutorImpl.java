/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentResponse;
import com.liferay.portal.search.geolocation.GeoBuilders;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.hits.FieldsTranslator;

import java.io.IOException;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.GetRequest;
import org.opensearch.client.opensearch.core.GetResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 * @author Petteri Karttunen
 */
@Component(service = GetDocumentRequestExecutor.class)
public class GetDocumentRequestExecutorImpl
	implements GetDocumentRequestExecutor {

	@Override
	public GetDocumentResponse execute(GetDocumentRequest getDocumentRequest) {
		GetResponse<JsonData> getResponse = _getGetResponse(
			getDocumentRequest,
			_openSearchDocumentRequestTranslator.translate(getDocumentRequest));

		GetDocumentResponse getDocumentResponse = new GetDocumentResponse(
			getResponse.found());

		if (!getResponse.found()) {
			return getDocumentResponse;
		}

		DocumentBuilder documentBuilder = _documentBuilderFactory.builder();

		JsonData jsonData = getResponse.source();

		FieldsTranslator fieldsTranslator = new FieldsTranslator(_geoBuilders);

		fieldsTranslator.translateSource(documentBuilder, jsonData);

		getDocumentResponse.setDocument(documentBuilder.build());
		getDocumentResponse.setSource(jsonData.toString());
		getDocumentResponse.setVersion(getResponse.version());

		return getDocumentResponse;
	}

	private GetResponse<JsonData> _getGetResponse(
		GetDocumentRequest getDocumentRequest, GetRequest getRequest) {

		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient(
				getDocumentRequest.getConnectionId(),
				getDocumentRequest.isPreferLocalCluster());

		try {
			return openSearchClient.get(getRequest, JsonData.class);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private DocumentBuilderFactory _documentBuilderFactory;

	@Reference
	private GeoBuilders _geoBuilders;

	@Reference
	private OpenSearchConnectionManager _openSearchConnectionManager;

	@Reference
	private OpenSearchDocumentRequestTranslator
		_openSearchDocumentRequestTranslator;

}