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

package com.liferay.portal.search.elasticsearch6.internal.adapter;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.search.elasticsearch6.internal.adapter.document.DocumentRequestExecutorFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.TestElasticsearchConnectionManager;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentResponse;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Dylan Rebelak
 */
public class ElasticsearchSearchEngineAdapterDocumentRequestTest {

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			ElasticsearchSearchEngineAdapterIndexRequestTest.class.
				getSimpleName());

		_elasticsearchFixture.setUp();

		_elasticsearchConnectionManager =
			new TestElasticsearchConnectionManager(_elasticsearchFixture);

		AdminClient adminClient =
			_elasticsearchConnectionManager.getAdminClient();

		CreateIndexRequestBuilder createIndexRequestBuilder =
			adminClient.indices().prepareCreate(_INDEX_NAME);

		createIndexRequestBuilder.addMapping(
			_MAPPING_NAME, _MAPPING_SOURCE, XContentType.JSON);

		createIndexRequestBuilder.get();

		_client = _elasticsearchConnectionManager.getClient();

		_configureEngine();

		_documentFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_documentFixture.tearDown();

		AdminClient adminClient =
			_elasticsearchConnectionManager.getAdminClient();

		DeleteIndexRequestBuilder deleteIndexRequestBuilder =
			adminClient.indices().prepareDelete(_INDEX_NAME);

		deleteIndexRequestBuilder.get();
		
		_elasticsearchFixture.tearDown();
	}

	@Ignore
	@Test
	public void testExecuteDeleteByQueryDocumentRequest() {
		String documentSource1 = "{\"" + _FIELD_NAME + "\":\"true\"}";
		String documentSource2 = "{\"" + _FIELD_NAME + "\":\"false\"}";

		_indexDocument(documentSource1, "1");
		_indexDocument(documentSource2, "2");

		BooleanQuery query = new BooleanQueryImpl();

		query.addExactTerm(_FIELD_NAME, true);

		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest =
			new DeleteByQueryDocumentRequest(_INDEX_NAME, query);

		DeleteByQueryDocumentResponse deleteByQueryDocumentResponse =
			_searchEngineAdapter.execute(deleteByQueryDocumentRequest);

		Assert.assertEquals(1, deleteByQueryDocumentResponse.getDeleted());
	}

	@Test
	public void testExecuteDeleteDocumentRequest() {
		String documentSource1 = "{\"" + _FIELD_NAME + "\":\"true\"}";
		String id = "1";

		_indexDocument(documentSource1, id);

		GetResponse getResponse = _getDocument(id);

		Assert.assertTrue(getResponse.isExists());

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			_INDEX_NAME, _MAPPING_NAME, id);

		DeleteDocumentResponse deleteDocumentResponse =
			_searchEngineAdapter.execute(deleteDocumentRequest);

		Assert.assertEquals(
			RestStatus.OK.getStatus(), deleteDocumentResponse.getStatus());

		getResponse = _getDocument(id);

		Assert.assertFalse(getResponse.isExists());
	}

	@Test
	public void testExecuteIndexDocumentRequest() {
		String id = "1";
		Document document = new DocumentImpl();

		document.addKeyword(Field.UID, id);
		document.addKeyword(Field.TYPE, _MAPPING_NAME);

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			_INDEX_NAME, document);

		IndexDocumentResponse indexDocumentResponse =
			_searchEngineAdapter.execute(indexDocumentRequest);

		Assert.assertEquals(
			RestStatus.CREATED.getStatus(), indexDocumentResponse.getStatus());
	}

	@Ignore
	@Test
	public void testExecuteUpdateByQueryDocumentRequest() {
		String documentSource1 = "{\"" + _FIELD_NAME + "\":\"true\"}";
		String id = "1";

		_indexDocument(documentSource1, id);

		BooleanQuery query = new BooleanQueryImpl();

		query.addExactTerm(_FIELD_NAME, true);

		//TODO Test with script
		JSONObject script = null;

		UpdateByQueryDocumentRequest updateByQueryDocumentRequest =
			new UpdateByQueryDocumentRequest(_INDEX_NAME, query, script);

		UpdateByQueryDocumentResponse updateByQueryDocumentResponse =
			_searchEngineAdapter.execute(updateByQueryDocumentRequest);

		Assert.assertEquals(1, updateByQueryDocumentResponse.getUpdated());
	}

	@Test
	public void testExecuteUpdateDocumentRequest() {
		String documentSource1 = "{\"" + _FIELD_NAME + "\":\"true\"}";
		String id = "1";

		_indexDocument(documentSource1, id);

		GetResponse getResponse = _getDocument(id);

		Assert.assertEquals(
			Boolean.TRUE.toString(), getResponse.getSource().get(_FIELD_NAME));

		Document document = new DocumentImpl();

		document.addKeyword(Field.UID, id);
		document.addKeyword(Field.TYPE, _MAPPING_NAME);
		document.addKeyword(_FIELD_NAME, false);

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			_INDEX_NAME, id, document);

		UpdateDocumentResponse updateDocumentResponse =
			_searchEngineAdapter.execute(updateDocumentRequest);

		Assert.assertEquals(
			RestStatus.OK.getStatus(), updateDocumentResponse.getStatus());

		getResponse = _getDocument(id);

		Assert.assertEquals(
			Boolean.FALSE.toString(), getResponse.getSource().get(_FIELD_NAME));
	}

	private void _configureEngine() {
		_searchEngineAdapter = new ElasticsearchSearchEngineAdapterImpl();

		DocumentRequestExecutorFixture documentRequestExecutorFixture =
			new DocumentRequestExecutorFixture(_elasticsearchConnectionManager);

		((ElasticsearchSearchEngineAdapterImpl)_searchEngineAdapter).
			documentRequestExecutor =
				documentRequestExecutorFixture.createExecutor();
	}

	private GetResponse _getDocument(String id) {
		GetRequestBuilder getRequestBuilder = _client.prepareGet();

		getRequestBuilder.setIndex(_INDEX_NAME);
		getRequestBuilder.setId(id);

		return getRequestBuilder.get();
	}

	private void _indexDocument(String documentSource, String id) {
		IndexRequestBuilder indexRequestBuilder = _client.prepareIndex(
			_INDEX_NAME, _MAPPING_NAME);

		indexRequestBuilder.setIndex(_INDEX_NAME);
		indexRequestBuilder.setId(id);
		indexRequestBuilder.setSource(documentSource, XContentType.JSON);

		indexRequestBuilder.get();
	}

	private static final String _FIELD_NAME = "matchDocument";

	private static final String _INDEX_NAME = "test_request_index";

	private static final String _MAPPING_NAME = "testDocumentMapping";

	private static final String _MAPPING_SOURCE =
		"{\"properties\":{\"matchDocument\":{\"type\":\"boolean\"}}}";

	private Client _client;
	private final DocumentFixture _documentFixture = new DocumentFixture();
	private ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private ElasticsearchFixture _elasticsearchFixture;
	private SearchEngineAdapter _searchEngineAdapter;

}