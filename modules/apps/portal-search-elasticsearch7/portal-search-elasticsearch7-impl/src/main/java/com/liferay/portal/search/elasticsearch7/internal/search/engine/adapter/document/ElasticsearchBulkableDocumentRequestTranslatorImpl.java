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
import com.liferay.portal.search.elasticsearch7.internal.document.ElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch7.internal.script.ScriptTranslator;
import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequestTranslator;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

import java.util.Collections;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "search.engine.impl=Elasticsearch",
	service = {
		BulkableDocumentRequestTranslator.class,
		ElasticsearchBulkableDocumentRequestTranslator.class
	}
)
public class ElasticsearchBulkableDocumentRequestTranslatorImpl
	implements ElasticsearchBulkableDocumentRequestTranslator {

	@Override
	public DeleteRequest translate(
		DeleteDocumentRequest deleteDocumentRequest) {

		DeleteRequest deleteRequest = new DeleteRequest();

		_setRefreshPolicy(deleteRequest, deleteDocumentRequest.isRefresh());

		deleteRequest.id(deleteDocumentRequest.getUid());
		deleteRequest.index(deleteDocumentRequest.getIndexName());
		deleteRequest.type(deleteDocumentRequest.getType());

		return deleteRequest;
	}

	@Override
	public GetRequest translate(GetDocumentRequest getDocumentRequest) {
		GetRequest getRequest = new GetRequest();

		FetchSourceContext fetchSourceContext = new FetchSourceContext(
			getDocumentRequest.isFetchSource(),
			getDocumentRequest.getFetchSourceIncludes(),
			getDocumentRequest.getFetchSourceExcludes());

		getRequest.fetchSourceContext(fetchSourceContext);

		getRequest.id(getDocumentRequest.getId());
		getRequest.index(getDocumentRequest.getIndexName());
		getRequest.refresh(getDocumentRequest.isRefresh());
		getRequest.storedFields(getDocumentRequest.getStoredFields());
		getRequest.type(_getType(getDocumentRequest.getType()));

		return getRequest;
	}

	@Override
	public IndexRequest translate(IndexDocumentRequest indexDocumentRequest) {
		IndexRequest indexRequest = new IndexRequest();

		_setRefreshPolicy(indexRequest, indexDocumentRequest.isRefresh());
		_setSource(indexRequest, indexDocumentRequest);

		indexRequest.id(_getUid(indexDocumentRequest));
		indexRequest.index(indexDocumentRequest.getIndexName());
		indexRequest.type(_getType(indexDocumentRequest.getType()));

		return indexRequest;
	}

	@Override
	public UpdateRequest translate(
		UpdateDocumentRequest updateDocumentRequest) {

		UpdateRequest updateRequest = new UpdateRequest();

		if (updateDocumentRequest.getScript() != null) {
			updateRequest.script(
				_scriptTranslator.translate(updateDocumentRequest.getScript()));
		}
		else {
			_setDoc(updateRequest, updateDocumentRequest);
		}

		_setDocAsUpsert(updateRequest, updateDocumentRequest.isUpsert());
		_setRefreshPolicy(updateRequest, updateDocumentRequest.isRefresh());
		_setScriptedUpsert(
			updateRequest, updateDocumentRequest.isScriptedUpsert());

		updateRequest.id(_getUid(updateDocumentRequest));
		updateRequest.index(updateDocumentRequest.getIndexName());
		updateRequest.type(_getType(updateDocumentRequest.getType()));

		return updateRequest;
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
		UpdateRequest updateRequest,
		UpdateDocumentRequest updateDocumentRequest) {

		if (updateDocumentRequest.getDocument() != null) {
			XContentBuilder xContentBuilder =
				_elasticsearchDocumentFactory.getElasticsearchDocument(
					updateDocumentRequest.getDocument());

			updateRequest.doc(xContentBuilder);
		}
		else {
			@SuppressWarnings("deprecation")
			String elasticsearchDocument =
				_elasticsearchDocumentFactory.getElasticsearchDocument(
					updateDocumentRequest.getDocument71());

			updateRequest.doc(elasticsearchDocument, XContentType.JSON);
		}
	}

	private void _setDocAsUpsert(UpdateRequest updateRequest, boolean upsert) {
		if (upsert) {
			updateRequest.docAsUpsert(true);
		}
	}

	private void _setRefreshPolicy(WriteRequest writeRequest, boolean refresh) {
		if (refresh) {
			writeRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
		}
	}

	private void _setScriptedUpsert(
		UpdateRequest updateRequest, boolean scriptedUpsert) {

		if (scriptedUpsert) {
			updateRequest.scriptedUpsert(true);
			updateRequest.upsert(Collections.emptyMap());
		}
	}

	private void _setSource(
		IndexRequest indexRequest, IndexDocumentRequest indexDocumentRequest) {

		if (indexDocumentRequest.getDocument() != null) {
			XContentBuilder xContentBuilder =
				_elasticsearchDocumentFactory.getElasticsearchDocument(
					indexDocumentRequest.getDocument());

			indexRequest.source(xContentBuilder);
		}
		else {
			@SuppressWarnings("deprecation")
			String elasticsearchDocument =
				_elasticsearchDocumentFactory.getElasticsearchDocument(
					indexDocumentRequest.getDocument71());

			indexRequest.source(elasticsearchDocument, XContentType.JSON);
		}
	}

	private ElasticsearchDocumentFactory _elasticsearchDocumentFactory;
	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();

}