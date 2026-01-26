/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.document;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.json.JsonData;

import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.document.FieldsTranslator;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentResponse;
import com.liferay.portal.search.geolocation.GeoBuilders;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = GetDocumentRequestExecutor.class)
public class GetDocumentRequestExecutorImpl
	implements GetDocumentRequestExecutor {

	@Override
	public GetDocumentResponse execute(GetDocumentRequest getDocumentRequest) {
		GetResponse<JsonData> getResponse = _getGetResponse(
			getDocumentRequest,
			ElasticsearchDocumentRequestTranslatorUtil.translate(
				getDocumentRequest));

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

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				getDocumentRequest.getConnectionId(),
				getDocumentRequest.isPreferLocalCluster());

		try {
			return elasticsearchClient.get(getRequest, JsonData.class);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private DocumentBuilderFactory _documentBuilderFactory;

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

	@Reference
	private GeoBuilders _geoBuilders;

}