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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.test.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.document.DefaultElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document.ElasticsearchBulkableDocumentRequestTranslator;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document.ElasticsearchBulkableDocumentRequestTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document.GetDocumentRequestExecutor;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document.GetDocumentRequestExecutorImpl;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document.IndexDocumentRequestExecutor;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document.IndexDocumentRequestExecutorImpl;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document.UpdateDocumentRequestExecutor;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document.UpdateDocumentRequestExecutorImpl;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index.CreateIndexRequestExecutor;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index.CreateIndexRequestExecutorImpl;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index.DeleteIndexRequestExecutor;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index.DeleteIndexRequestExecutorImpl;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index.IndicesOptionsTranslator;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index.IndicesOptionsTranslatorImpl;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.internal.document.DocumentBuilderFactoryImpl;

/**
 * @author Adam Brandizzi
 */
public class RequestExecutorFixture {

	public RequestExecutorFixture(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public void createIndex(String indexName) {
		_createIndexRequestExecutor.execute(new CreateIndexRequest(indexName));
	}

	public void deleteIndex(String indexName) {
		_deleteIndexRequestExecutor.execute(new DeleteIndexRequest(indexName));
	}

	public CreateIndexRequestExecutor getCreateIndexRequestExecutor() {
		return _createIndexRequestExecutor;
	}

	public DeleteIndexRequestExecutor getDeleteIndexRequestExecutor() {
		return _deleteIndexRequestExecutor;
	}

	public Document getDocumentById(String indexName, String uid) {
		GetDocumentRequest getDocumentRequest = new GetDocumentRequest(
			indexName, uid);

		getDocumentRequest.setFetchSource(true);
		getDocumentRequest.setFetchSourceInclude(StringPool.STAR);

		GetDocumentResponse getDocumentResponse =
			_getDocumentRequestExecutor.execute(getDocumentRequest);

		return getDocumentResponse.getDocument();
	}

	public GetDocumentRequestExecutor getGetDocumentRequestExecutor() {
		return _getDocumentRequestExecutor;
	}

	public IndexDocumentRequestExecutor getIndexDocumentRequestExecutor() {
		return _indexDocumentRequestExecutor;
	}

	public UpdateDocumentRequestExecutor getUpdateDocumentRequestExecutor() {
		return _updateDocumentRequestExecutor;
	}

	public IndexDocumentResponse indexDocument(
		String indexName, com.liferay.portal.kernel.search.Document document) {

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			indexName, document);

		return _indexDocumentRequestExecutor.execute(indexDocumentRequest);
	}

	public IndexDocumentResponse indexDocument(
		String indexName, Document document) {

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			indexName, document);

		return _indexDocumentRequestExecutor.execute(indexDocumentRequest);
	}

	public void setUp() {
		_createIndexRequestExecutor = new CreateIndexRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(_elasticsearchClientResolver);
			}
		};

		IndicesOptionsTranslator indicesOptionsTranslator =
			new IndicesOptionsTranslatorImpl();

		_deleteIndexRequestExecutor = new DeleteIndexRequestExecutorImpl() {
			{
				setIndicesOptionsTranslator(indicesOptionsTranslator);
				setElasticsearchClientResolver(_elasticsearchClientResolver);
			}
		};

		ElasticsearchBulkableDocumentRequestTranslator
			elasticsearchBulkableDocumentRequestTranslator =
				new ElasticsearchBulkableDocumentRequestTranslatorImpl();

		ReflectionTestUtil.setFieldValue(
			elasticsearchBulkableDocumentRequestTranslator,
			"_elasticsearchDocumentFactory",
			new DefaultElasticsearchDocumentFactory());

		_getDocumentRequestExecutor = new GetDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			_getDocumentRequestExecutor,
			"_elasticsearchBulkableDocumentRequestTranslator",
			elasticsearchBulkableDocumentRequestTranslator);
		ReflectionTestUtil.setFieldValue(
			_getDocumentRequestExecutor, "_elasticsearchClientResolver",
			_elasticsearchClientResolver);
		ReflectionTestUtil.setFieldValue(
			_getDocumentRequestExecutor, "_documentBuilderFactory",
			new DocumentBuilderFactoryImpl());

		_indexDocumentRequestExecutor = new IndexDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			_indexDocumentRequestExecutor,
			"_elasticsearchBulkableDocumentRequestTranslator",
			elasticsearchBulkableDocumentRequestTranslator);
		ReflectionTestUtil.setFieldValue(
			_indexDocumentRequestExecutor, "_elasticsearchClientResolver",
			_elasticsearchClientResolver);

		_updateDocumentRequestExecutor =
			new UpdateDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			_updateDocumentRequestExecutor,
			"_elasticsearchBulkableDocumentRequestTranslator",
			elasticsearchBulkableDocumentRequestTranslator);
		ReflectionTestUtil.setFieldValue(
			_updateDocumentRequestExecutor, "_elasticsearchClientResolver",
			_elasticsearchClientResolver);
	}

	private CreateIndexRequestExecutor _createIndexRequestExecutor;
	private DeleteIndexRequestExecutor _deleteIndexRequestExecutor;
	private final ElasticsearchClientResolver _elasticsearchClientResolver;
	private GetDocumentRequestExecutor _getDocumentRequestExecutor;
	private IndexDocumentRequestExecutor _indexDocumentRequestExecutor;
	private UpdateDocumentRequestExecutor _updateDocumentRequestExecutor;

}