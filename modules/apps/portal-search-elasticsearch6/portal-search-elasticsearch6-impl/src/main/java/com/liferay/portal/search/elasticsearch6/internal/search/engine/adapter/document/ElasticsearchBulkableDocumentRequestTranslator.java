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
import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequestTranslator;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

import java.io.IOException;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteAction;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexAction;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateAction;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "search.engine.impl=Elasticsearch",
	service = BulkableDocumentRequestTranslator.class
)
public class ElasticsearchBulkableDocumentRequestTranslator
	implements BulkableDocumentRequestTranslator
		<DeleteRequestBuilder, IndexRequestBuilder, UpdateRequestBuilder,
		 BulkRequestBuilder> {

	@Override
	public DeleteRequestBuilder translate(
		DeleteDocumentRequest deleteDocumentRequest,
		BulkRequestBuilder searchEngineAdapterRequest) {

		Client client = elasticsearchConnectionManager.getClient();

		DeleteRequestBuilder deleteRequestBuilder =
			DeleteAction.INSTANCE.newRequestBuilder(client);

		deleteRequestBuilder.setId(deleteDocumentRequest.getUid());
		deleteRequestBuilder.setIndex(deleteDocumentRequest.getIndexName());

		if (deleteDocumentRequest.isRefresh()) {
			deleteRequestBuilder.setRefreshPolicy(
				WriteRequest.RefreshPolicy.IMMEDIATE);
		}

		deleteRequestBuilder.setType(deleteDocumentRequest.getType());

		if (searchEngineAdapterRequest != null) {
			searchEngineAdapterRequest.add(deleteRequestBuilder);
		}

		return deleteRequestBuilder;
	}

	@Override
	public IndexRequestBuilder translate(
		IndexDocumentRequest indexDocumentRequest,
		BulkRequestBuilder searchEngineAdapterRequest) {

		try {
			Client client = elasticsearchConnectionManager.getClient();

			IndexRequestBuilder indexRequestBuilder =
				IndexAction.INSTANCE.newRequestBuilder(client);

			Document document = indexDocumentRequest.getDocument();

			indexRequestBuilder.setId(document.getUID());

			indexRequestBuilder.setIndex(indexDocumentRequest.getIndexName());

			if (indexDocumentRequest.isRefresh()) {
				indexRequestBuilder.setRefreshPolicy(
					WriteRequest.RefreshPolicy.IMMEDIATE);
			}

			indexRequestBuilder.setType(document.get(Field.TYPE));

			ElasticsearchDocumentFactory elasticsearchDocumentFactory =
				new DefaultElasticsearchDocumentFactory();

			String elasticsearchDocument =
				elasticsearchDocumentFactory.getElasticsearchDocument(document);

			indexRequestBuilder.setSource(
				elasticsearchDocument, XContentType.JSON);

			if (searchEngineAdapterRequest != null) {
				searchEngineAdapterRequest.add(indexRequestBuilder);
			}

			return indexRequestBuilder;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public UpdateRequestBuilder translate(
		UpdateDocumentRequest updateDocumentRequest,
		BulkRequestBuilder searchEngineAdapterRequest) {

		try {
			Client client = elasticsearchConnectionManager.getClient();

			UpdateRequestBuilder updateRequestBuilder =
				UpdateAction.INSTANCE.newRequestBuilder(client);

			Document document = updateDocumentRequest.getDocument();

			updateRequestBuilder.setId(document.getUID());

			updateRequestBuilder.setIndex(updateDocumentRequest.getIndexName());

			if (updateDocumentRequest.isRefresh()) {
				updateRequestBuilder.setRefreshPolicy(
					WriteRequest.RefreshPolicy.IMMEDIATE);
			}

			updateRequestBuilder.setType(document.get(Field.TYPE));

			ElasticsearchDocumentFactory elasticsearchDocumentFactory =
				new DefaultElasticsearchDocumentFactory();

			String elasticsearchDocument =
				elasticsearchDocumentFactory.getElasticsearchDocument(document);

			updateRequestBuilder.setDoc(
				elasticsearchDocument, XContentType.JSON);

			if (searchEngineAdapterRequest != null) {
				searchEngineAdapterRequest.add(updateRequestBuilder);
			}

			return updateRequestBuilder;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

}