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

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch6.internal.document.ElasticsearchDocumentFactory;
import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequestTranslator;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteAction;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexAction;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateAction;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
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

		Client client = _elasticsearchClientResolver.getClient();

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

		Client client = _elasticsearchClientResolver.getClient();

		IndexRequestBuilder indexRequestBuilder =
			IndexAction.INSTANCE.newRequestBuilder(client);

		setIndexRequestBuilderId(indexRequestBuilder, indexDocumentRequest);

		indexRequestBuilder.setIndex(indexDocumentRequest.getIndexName());

		if (indexDocumentRequest.isRefresh()) {
			indexRequestBuilder.setRefreshPolicy(
				WriteRequest.RefreshPolicy.IMMEDIATE);
		}

		indexRequestBuilder.setType(indexDocumentRequest.getType());

		setSource(indexDocumentRequest, indexRequestBuilder);

		if (searchEngineAdapterRequest != null) {
			searchEngineAdapterRequest.add(indexRequestBuilder);
		}

		return indexRequestBuilder;
	}

	@Override
	public UpdateRequestBuilder translate(
		UpdateDocumentRequest updateDocumentRequest,
		BulkRequestBuilder searchEngineAdapterRequest) {

		Client client = _elasticsearchClientResolver.getClient();

		UpdateRequestBuilder updateRequestBuilder =
			UpdateAction.INSTANCE.newRequestBuilder(client);

		setUpdateRequestBuilderId(updateRequestBuilder, updateDocumentRequest);

		updateRequestBuilder.setIndex(updateDocumentRequest.getIndexName());

		if (updateDocumentRequest.isRefresh()) {
			updateRequestBuilder.setRefreshPolicy(
				WriteRequest.RefreshPolicy.IMMEDIATE);
		}

		updateRequestBuilder.setType(updateDocumentRequest.getType());

		setDoc(updateDocumentRequest, updateRequestBuilder);

		if (searchEngineAdapterRequest != null) {
			searchEngineAdapterRequest.add(updateRequestBuilder);
		}

		return updateRequestBuilder;
	}

	protected String getUid(IndexDocumentRequest indexDocumentRequest) {
		String uid = indexDocumentRequest.getUid();

		if (!Validator.isBlank(uid)) {
			return uid;
		}

		if (indexDocumentRequest.getDocument() != null) {
			Document document = indexDocumentRequest.getDocument();

			return document.getString(Field.UID);
		}

		com.liferay.portal.kernel.search.Document document =
			indexDocumentRequest.getDocument71();

		Field field = document.getField(Field.UID);

		if (field != null) {
			return field.getValue();
		}

		return uid;
	}

	protected String getUid(UpdateDocumentRequest updateDocumentRequest) {
		String uid = updateDocumentRequest.getUid();

		if (!Validator.isBlank(uid)) {
			return uid;
		}

		if (updateDocumentRequest.getDocument() != null) {
			Document document = updateDocumentRequest.getDocument();

			return document.getString(Field.UID);
		}

		com.liferay.portal.kernel.search.Document document =
			updateDocumentRequest.getDocument71();

		Field field = document.getField(Field.UID);

		if (field != null) {
			uid = field.getValue();
		}

		return uid;
	}

	protected void setDoc(
		UpdateDocumentRequest updateDocumentRequest,
		UpdateRequestBuilder updateRequestBuilder) {

		if (updateDocumentRequest.getDocument() != null) {
			XContentBuilder xContentBuilder =
				_elasticsearchDocumentFactory.getElasticsearchDocument(
					updateDocumentRequest.getDocument());

			updateRequestBuilder.setDoc(xContentBuilder);
		}
		else {
			String elasticsearchDocument =
				_elasticsearchDocumentFactory.getElasticsearchDocument(
					updateDocumentRequest.getDocument71());

			updateRequestBuilder.setDoc(
				elasticsearchDocument, XContentType.JSON);
		}
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchDocumentFactory(
		ElasticsearchDocumentFactory elasticsearchDocumentFactory) {

		_elasticsearchDocumentFactory = elasticsearchDocumentFactory;
	}

	protected void setIndexRequestBuilderId(
		IndexRequestBuilder indexRequestBuilder,
		IndexDocumentRequest indexDocumentRequest) {

		indexRequestBuilder.setId(getUid(indexDocumentRequest));
	}

	protected void setSource(
		IndexDocumentRequest indexDocumentRequest,
		IndexRequestBuilder indexRequestBuilder) {

		if (indexDocumentRequest.getDocument() != null) {
			XContentBuilder xContentBuilder =
				_elasticsearchDocumentFactory.getElasticsearchDocument(
					indexDocumentRequest.getDocument());

			indexRequestBuilder.setSource(xContentBuilder);
		}
		else {
			String elasticsearchDocument =
				_elasticsearchDocumentFactory.getElasticsearchDocument(
					indexDocumentRequest.getDocument71());

			indexRequestBuilder.setSource(
				elasticsearchDocument, XContentType.JSON);
		}
	}

	protected void setUpdateRequestBuilderId(
		UpdateRequestBuilder updateRequestBuilder,
		UpdateDocumentRequest updateDocumentRequest) {

		updateRequestBuilder.setId(getUid(updateDocumentRequest));
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private ElasticsearchDocumentFactory _elasticsearchDocumentFactory;

}