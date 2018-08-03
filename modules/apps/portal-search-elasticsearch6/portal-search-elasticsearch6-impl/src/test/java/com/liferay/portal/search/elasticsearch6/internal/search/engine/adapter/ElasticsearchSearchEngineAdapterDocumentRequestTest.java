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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.TestElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.document.DocumentRequestExecutorFixture;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentItemResponse;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentResponse;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
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
			ElasticsearchSearchEngineAdapterDocumentRequestTest.class.
				getSimpleName());

		_elasticsearchFixture.setUp();

		_elasticsearchConnectionManager =
			new TestElasticsearchConnectionManager(_elasticsearchFixture);

		_client = _elasticsearchConnectionManager.getClient();

		_searchEngineAdapter = createSearchEngineAdapter(
			_elasticsearchConnectionManager);

		_documentFixture.setUp();

		createIndex();
	}

	@After
	public void tearDown() throws Exception {
		deleteIndex();

		_documentFixture.tearDown();

		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testExecuteBulkDocumentRequest() {
		Document document1 = new DocumentImpl();

		document1.addKeyword(Field.TYPE, _MAPPING_NAME);
		document1.addKeyword(Field.UID, "1");
		document1.addKeyword(_FIELD_NAME, Boolean.TRUE.toString());

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			_INDEX_NAME, document1);

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		bulkDocumentRequest.addBulkableDocumentRequest(indexDocumentRequest);

		Document document2 = new DocumentImpl();

		document2.addKeyword(Field.TYPE, _MAPPING_NAME);
		document2.addKeyword(Field.UID, "2");
		document2.addKeyword(_FIELD_NAME, Boolean.FALSE.toString());

		IndexDocumentRequest indexDocumentRequest2 = new IndexDocumentRequest(
			_INDEX_NAME, document2);

		bulkDocumentRequest.addBulkableDocumentRequest(indexDocumentRequest2);

		BulkDocumentResponse bulkDocumentResponse =
			_searchEngineAdapter.execute(bulkDocumentRequest);

		Assert.assertFalse(bulkDocumentResponse.hasErrors());

		List<BulkDocumentItemResponse> bulkDocumentItemResponses =
			bulkDocumentResponse.getBulkDocumentItemResponses();

		Assert.assertEquals(
			bulkDocumentItemResponses.toString(), 2,
			bulkDocumentItemResponses.size());

		BulkDocumentItemResponse bulkDocumentItemResponse1 =
			bulkDocumentItemResponses.get(0);

		Assert.assertEquals("1", bulkDocumentItemResponse1.getId());

		BulkDocumentItemResponse bulkDocumentItemResponse2 =
			bulkDocumentItemResponses.get(1);

		Assert.assertEquals("2", bulkDocumentItemResponse2.getId());

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			_INDEX_NAME, _MAPPING_NAME, "1");

		BulkDocumentRequest bulkDocumentRequest2 = new BulkDocumentRequest();

		bulkDocumentRequest2.addBulkableDocumentRequest(deleteDocumentRequest);

		Document document2Update = new DocumentImpl();

		document2Update.addKeyword(Field.TYPE, _MAPPING_NAME);
		document2Update.addKeyword(Field.UID, "2");
		document2Update.addKeyword(_FIELD_NAME, Boolean.TRUE.toString());

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			_INDEX_NAME, "2", document2Update);

		bulkDocumentRequest2.addBulkableDocumentRequest(updateDocumentRequest);

		BulkDocumentResponse bulkDocumentResponse2 =
			_searchEngineAdapter.execute(bulkDocumentRequest2);

		Assert.assertFalse(bulkDocumentResponse2.hasErrors());

		List<BulkDocumentItemResponse> bulkDocumentItemResponses2 =
			bulkDocumentResponse2.getBulkDocumentItemResponses();

		Assert.assertEquals(
			bulkDocumentItemResponses2.toString(), 2,
			bulkDocumentItemResponses2.size());

		BulkDocumentItemResponse bulkDocumentItemResponse3 =
			bulkDocumentItemResponses2.get(0);

		Assert.assertEquals("1", bulkDocumentItemResponse3.getId());

		BulkDocumentItemResponse bulkDocumentItemResponse4 =
			bulkDocumentItemResponses2.get(1);

		Assert.assertEquals("2", bulkDocumentItemResponse4.getId());

		GetResponse getResponse1 = _getDocument("1");

		Assert.assertFalse(getResponse1.isExists());

		GetResponse getResponse2 = _getDocument("2");

		Assert.assertTrue(getResponse2.isExists());

		Map<String, Object> map2 = getResponse2.getSource();

		Assert.assertEquals(Boolean.TRUE.toString(), map2.get(_FIELD_NAME));
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
			new DeleteByQueryDocumentRequest(query, new String[] {_INDEX_NAME});

		DeleteByQueryDocumentResponse deleteByQueryDocumentResponse =
			_searchEngineAdapter.execute(deleteByQueryDocumentRequest);

		Assert.assertEquals(1, deleteByQueryDocumentResponse.getDeleted());
	}

	@Test
	public void testExecuteDeleteDocumentRequest() {
		String documentSource = "{\"" + _FIELD_NAME + "\":\"true\"}";
		String id = "1";

		_indexDocument(documentSource, id);

		GetResponse getResponse1 = _getDocument(id);

		Assert.assertTrue(getResponse1.isExists());

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			_INDEX_NAME, _MAPPING_NAME, id);

		DeleteDocumentResponse deleteDocumentResponse =
			_searchEngineAdapter.execute(deleteDocumentRequest);

		Assert.assertEquals(
			RestStatus.OK.getStatus(), deleteDocumentResponse.getStatus());

		GetResponse getResponse2 = _getDocument(id);

		Assert.assertFalse(getResponse2.isExists());
	}

	@Test
	public void testExecuteIndexDocumentRequest() {
		Document document = new DocumentImpl();

		document.addKeyword(Field.TYPE, _MAPPING_NAME);
		document.addKeyword(Field.UID, "1");

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
		String documentSource = "{\"" + _FIELD_NAME + "\":\"true\"}";

		_indexDocument(documentSource, "1");

		BooleanQuery query = new BooleanQueryImpl();

		query.addExactTerm(_FIELD_NAME, true);

		UpdateByQueryDocumentRequest updateByQueryDocumentRequest =
			new UpdateByQueryDocumentRequest(
				query, null, new String[] {_INDEX_NAME});

		UpdateByQueryDocumentResponse updateByQueryDocumentResponse =
			_searchEngineAdapter.execute(updateByQueryDocumentRequest);

		Assert.assertEquals(1, updateByQueryDocumentResponse.getUpdated());
	}

	@Test
	public void testExecuteUpdateDocumentRequest() {
		String documentSource = "{\"" + _FIELD_NAME + "\":\"true\"}";
		String id = "1";

		_indexDocument(documentSource, id);

		GetResponse getResponse1 = _getDocument(id);

		Map<String, Object> map1 = getResponse1.getSource();

		Assert.assertEquals(Boolean.TRUE.toString(), map1.get(_FIELD_NAME));

		Document document = new DocumentImpl();

		document.addKeyword(Field.TYPE, _MAPPING_NAME);
		document.addKeyword(Field.UID, id);
		document.addKeyword(_FIELD_NAME, false);

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			_INDEX_NAME, id, document);

		UpdateDocumentResponse updateDocumentResponse =
			_searchEngineAdapter.execute(updateDocumentRequest);

		Assert.assertEquals(
			RestStatus.OK.getStatus(), updateDocumentResponse.getStatus());

		GetResponse getResponse2 = _getDocument(id);

		Map<String, Object> map2 = getResponse2.getSource();

		Assert.assertEquals(Boolean.FALSE.toString(), map2.get(_FIELD_NAME));
	}

	protected DocumentRequestExecutor createDocumentRequestExecutor(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		DocumentRequestExecutorFixture documentRequestExecutorFixture =
			new DocumentRequestExecutorFixture(elasticsearchConnectionManager);

		return documentRequestExecutorFixture.createExecutor();
	}

	protected void createIndex() {
		AdminClient adminClient =
			_elasticsearchConnectionManager.getAdminClient();

		IndicesAdminClient indicesAdminClient = adminClient.indices();

		CreateIndexRequestBuilder createIndexRequestBuilder =
			indicesAdminClient.prepareCreate(_INDEX_NAME);

		createIndexRequestBuilder.addMapping(
			_MAPPING_NAME, _MAPPING_SOURCE, XContentType.JSON);

		createIndexRequestBuilder.get();
	}

	protected SearchEngineAdapter createSearchEngineAdapter(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		return new ElasticsearchSearchEngineAdapterImpl() {
			{
				documentRequestExecutor = createDocumentRequestExecutor(
					elasticsearchConnectionManager);
			}
		};
	}

	protected void deleteIndex() {
		AdminClient adminClient =
			_elasticsearchConnectionManager.getAdminClient();

		IndicesAdminClient indicesAdminClient = adminClient.indices();

		DeleteIndexRequestBuilder deleteIndexRequestBuilder =
			indicesAdminClient.prepareDelete(_INDEX_NAME);

		deleteIndexRequestBuilder.get();
	}

	private GetResponse _getDocument(String id) {
		GetRequestBuilder getRequestBuilder = _client.prepareGet();

		getRequestBuilder.setId(id);
		getRequestBuilder.setIndex(_INDEX_NAME);

		return getRequestBuilder.get();
	}

	private void _indexDocument(String documentSource, String id) {
		IndexRequestBuilder indexRequestBuilder = _client.prepareIndex(
			_INDEX_NAME, _MAPPING_NAME);

		indexRequestBuilder.setId(id);
		indexRequestBuilder.setIndex(_INDEX_NAME);
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