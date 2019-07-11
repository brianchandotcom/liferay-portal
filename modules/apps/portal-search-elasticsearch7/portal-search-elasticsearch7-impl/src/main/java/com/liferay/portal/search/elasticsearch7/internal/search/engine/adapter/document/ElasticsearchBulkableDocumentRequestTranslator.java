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

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.document.ElasticsearchDocumentFactory;
import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequestTranslator;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.WriteRequestBuilder;
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
	implements BulkableDocumentRequestTranslator {

	@Override
	public DeleteRequestBuilder translate(
		DeleteDocumentRequest deleteDocumentRequest) {

		Client client = _elasticsearchClientResolver.getClient();

		DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete();

		_setRefreshPolicy(
			deleteRequestBuilder, deleteDocumentRequest.isRefresh());

		return deleteRequestBuilder.setId(
			deleteDocumentRequest.getUid()
		).setIndex(
			deleteDocumentRequest.getIndexName()
		).setType(
			_getType(deleteDocumentRequest.getType())
		);
	}

	@Override
	public GetRequestBuilder translate(GetDocumentRequest getDocumentRequest) {
		Client client = _elasticsearchClientResolver.getClient();

		GetRequestBuilder getRequestBuilder = client.prepareGet();

		return getRequestBuilder.setId(
			getDocumentRequest.getId()
		).setIndex(
			getDocumentRequest.getIndexName()
		).setRefresh(
			getDocumentRequest.isRefresh()
		).setFetchSource(
			getDocumentRequest.getFetchSourceIncludes(),
			getDocumentRequest.getFetchSourceExcludes()
		).setStoredFields(
			getDocumentRequest.getStoredFields()
		).setType(
			_getType(getDocumentRequest.getType())
		);
	}

	@Override
	public IndexRequestBuilder translate(
		IndexDocumentRequest indexDocumentRequest) {

		Client client = _elasticsearchClientResolver.getClient();

		IndexRequestBuilder indexRequestBuilder = client.prepareIndex();

		_setRefreshPolicy(
			indexRequestBuilder, indexDocumentRequest.isRefresh());
		_setSource(indexRequestBuilder, indexDocumentRequest);

		return indexRequestBuilder.setId(
			_getUid(indexDocumentRequest)
		).setIndex(
			indexDocumentRequest.getIndexName()
		).setType(
			_getType(indexDocumentRequest.getType())
		);
	}

	@Override
	public UpdateRequestBuilder translate(
		UpdateDocumentRequest updateDocumentRequest) {

		Client client = _elasticsearchClientResolver.getClient();

		UpdateRequestBuilder updateRequestBuilder = client.prepareUpdate();

		_setDoc(updateRequestBuilder, updateDocumentRequest);
		_setRefreshPolicy(
			updateRequestBuilder, updateDocumentRequest.isRefresh());

		return updateRequestBuilder.setId(
			_getUid(updateDocumentRequest)
		).setIndex(
			updateDocumentRequest.getIndexName()
		).setType(
			_getType(updateDocumentRequest.getType())
		);
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

	private String _getType(String type) {
		if (type != null) {
			return type;
		}

		return "_doc";
	}

	private String _getUid(IndexDocumentRequest indexDocumentRequest) {
		String uid = indexDocumentRequest.getUid();

		if (!Validator.isBlank(uid)) {
			return uid;
		}

		if (indexDocumentRequest.getDocument() != null) {
			Document document = indexDocumentRequest.getDocument();

			return document.getString(Field.UID);
		}

		@SuppressWarnings("deprecation")
		com.liferay.portal.kernel.search.Document document =
			indexDocumentRequest.getDocument71();

		Field field = document.getField(Field.UID);

		if (field != null) {
			return field.getValue();
		}

		return uid;
	}

	private String _getUid(UpdateDocumentRequest updateDocumentRequest) {
		String uid = updateDocumentRequest.getUid();

		if (!Validator.isBlank(uid)) {
			return uid;
		}

		if (updateDocumentRequest.getDocument() != null) {
			Document document = updateDocumentRequest.getDocument();

			return document.getString(Field.UID);
		}

		@SuppressWarnings("deprecation")
		com.liferay.portal.kernel.search.Document document =
			updateDocumentRequest.getDocument71();

		Field field = document.getField(Field.UID);

		if (field != null) {
			uid = field.getValue();
		}

		return uid;
	}

	private void _setDoc(
		UpdateRequestBuilder updateRequestBuilder,
		UpdateDocumentRequest updateDocumentRequest) {

		if (updateDocumentRequest.getDocument() != null) {
			XContentBuilder xContentBuilder =
				_elasticsearchDocumentFactory.getElasticsearchDocument(
					updateDocumentRequest.getDocument());

			updateRequestBuilder.setDoc(xContentBuilder);
		}
		else {
			@SuppressWarnings("deprecation")
			String elasticsearchDocument =
				_elasticsearchDocumentFactory.getElasticsearchDocument(
					updateDocumentRequest.getDocument71());

			updateRequestBuilder.setDoc(
				elasticsearchDocument, XContentType.JSON);
		}
	}

	private void _setRefreshPolicy(
		WriteRequestBuilder writeRequestBuilder, boolean refresh) {

		if (refresh) {
			writeRequestBuilder.setRefreshPolicy(
				WriteRequest.RefreshPolicy.IMMEDIATE);
		}
	}

	private void _setSource(
		IndexRequestBuilder indexRequestBuilder,
		IndexDocumentRequest indexDocumentRequest) {

		if (indexDocumentRequest.getDocument() != null) {
			XContentBuilder xContentBuilder =
				_elasticsearchDocumentFactory.getElasticsearchDocument(
					indexDocumentRequest.getDocument());

			indexRequestBuilder.setSource(xContentBuilder);
		}
		else {
			@SuppressWarnings("deprecation")
			String elasticsearchDocument =
				_elasticsearchDocumentFactory.getElasticsearchDocument(
					indexDocumentRequest.getDocument71());

			indexRequestBuilder.setSource(
				elasticsearchDocument, XContentType.JSON);
		}
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private ElasticsearchDocumentFactory _elasticsearchDocumentFactory;

}