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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch6.internal.document.DefaultElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch6.internal.document.ElasticsearchDocumentFactory;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;

import java.io.IOException;

import org.elasticsearch.action.index.IndexAction;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(immediate = true, service = IndexDocumentRequestExecutor.class)
public class IndexDocumentRequestExecutorImpl
	implements IndexDocumentRequestExecutor {

	@Override
	public IndexDocumentResponse execute(
		IndexDocumentRequest indexDocumentRequest) {

		try {
			IndexRequestBuilder indexRequestBuilder = createIndexRequestBuilder(
				indexDocumentRequest);

			IndexResponse indexResponse = indexRequestBuilder.get();

			RestStatus restStatus = indexResponse.status();

			IndexDocumentResponse indexDocumentResponse =
				new IndexDocumentResponse(restStatus.getStatus());

			return indexDocumentResponse;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected IndexRequestBuilder createIndexRequestBuilder(
			IndexDocumentRequest indexDocumentRequest)
		throws IOException {

		Client client = elasticsearchConnectionManager.getClient();

		IndexRequestBuilder indexRequestBuilder =
			IndexAction.INSTANCE.newRequestBuilder(client);

		Document document = indexDocumentRequest.getDocument();

		indexRequestBuilder.setIndex(indexDocumentRequest.getIndexName());
		indexRequestBuilder.setId(document.getUID());
		indexRequestBuilder.setType(document.get(Field.TYPE));

		ElasticsearchDocumentFactory elasticsearchDocumentFactory =
			new DefaultElasticsearchDocumentFactory();

		String elasticsearchDocument =
			elasticsearchDocumentFactory.getElasticsearchDocument(document);

		indexRequestBuilder.setSource(elasticsearchDocument, XContentType.JSON);

		return indexRequestBuilder;
	}

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

}