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

import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.document.DocumentFieldsTranslator;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentResponse;
import com.liferay.portal.search.geolocation.GeoBuilders;

import java.io.IOException;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(immediate = true, service = GetDocumentRequestExecutor.class)
public class GetDocumentRequestExecutorImpl
	implements GetDocumentRequestExecutor {

	@Override
	public GetDocumentResponse execute(GetDocumentRequest getDocumentRequest) {
		GetRequest getRequest =
			_elasticsearchBulkableDocumentRequestTranslator.translate(
				getDocumentRequest);

		GetResponse getResponse = _getGetResponse(
			getRequest, getDocumentRequest);

		GetDocumentResponse getDocumentResponse = new GetDocumentResponse(
			getResponse.isExists());

		if (!getResponse.isExists()) {
			return getDocumentResponse;
		}

		getDocumentResponse.setSource(getResponse.getSourceAsString());
		getDocumentResponse.setVersion(getResponse.getVersion());

		DocumentFieldsTranslator documentFieldsTranslator =
			new DocumentFieldsTranslator(_geoBuilders);

		DocumentBuilder documentBuilder = _documentBuilderFactory.builder();

		documentFieldsTranslator.translate(
			documentBuilder, getResponse.getSourceAsMap());

		getDocumentResponse.setDocument(documentBuilder.build());

		return getDocumentResponse;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setBulkableDocumentRequestTranslator(
		ElasticsearchBulkableDocumentRequestTranslator
			elasticsearchBulkableDocumentRequestTranslator) {

		_elasticsearchBulkableDocumentRequestTranslator =
			elasticsearchBulkableDocumentRequestTranslator;
	}

	@Reference(unbind = "-")
	protected void setDocumentBuilderFactory(
		DocumentBuilderFactory documentBuilderFactory) {

		_documentBuilderFactory = documentBuilderFactory;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	@Reference(unbind = "-")
	protected void setGeoBuilders(GeoBuilders geoBuilders) {
		_geoBuilders = geoBuilders;
	}

	private GetResponse _getGetResponse(
		GetRequest getRequest, GetDocumentRequest getDocumentRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				getDocumentRequest.getConnectionId(),
				getDocumentRequest.isPreferLocalCluster());

		try {
			return restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private DocumentBuilderFactory _documentBuilderFactory;
	private ElasticsearchBulkableDocumentRequestTranslator
		_elasticsearchBulkableDocumentRequestTranslator;
	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private GeoBuilders _geoBuilders;

}