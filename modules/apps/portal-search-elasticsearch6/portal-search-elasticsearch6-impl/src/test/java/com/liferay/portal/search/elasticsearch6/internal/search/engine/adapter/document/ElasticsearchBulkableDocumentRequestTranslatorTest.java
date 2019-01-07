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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch6.internal.document.DefaultElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch6.internal.document.ElasticsearchDocumentFactory;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;

import java.io.IOException;

import org.elasticsearch.action.bulk.BulkAction;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.common.xcontent.XContentType;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class ElasticsearchBulkableDocumentRequestTranslatorTest {

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			ElasticsearchBulkableDocumentRequestTranslatorTest.class.
				getSimpleName());

		_elasticsearchFixture.setUp();

		_documentFixture.setUp();

		_elasticsearchBulkableDocumentRequestTranslator =
			new ElasticsearchBulkableDocumentRequestTranslator() {
				{
					elasticsearchClientResolver = _elasticsearchFixture;
				}
			};
	}

	@After
	public void tearDown() throws Exception {
		_documentFixture.tearDown();

		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testDeleteDocumentRequestTranslationWithNoRefresh() {
		doTestDeleteDocumentRequestTranslation(
			false, WriteRequest.RefreshPolicy.NONE);
	}

	@Test
	public void testDeleteDocumentRequestTranslationWithRefresh() {
		doTestDeleteDocumentRequestTranslation(
			true, WriteRequest.RefreshPolicy.IMMEDIATE);
	}

	@Test
	public void testIndexDocumentRequestTranslationWithNoRefresh()
		throws IOException {

		doTestIndexDocumentRequestTranslation(
			"1", false, WriteRequest.RefreshPolicy.NONE);
	}

	@Test
	public void testIndexDocumentRequestTranslationWithNoRefreshNoId()
		throws IOException {

		doTestIndexDocumentRequestTranslation(
			null, false, WriteRequest.RefreshPolicy.NONE);
	}

	@Test
	public void testIndexDocumentRequestTranslationWithRefresh()
		throws IOException {

		doTestIndexDocumentRequestTranslation(
			"1", true, WriteRequest.RefreshPolicy.IMMEDIATE);
	}

	@Test
	public void testIndexDocumentRequestTranslationWithRefreshNoId()
		throws IOException {

		doTestIndexDocumentRequestTranslation(
			null, true, WriteRequest.RefreshPolicy.IMMEDIATE);
	}

	@Test
	public void testUpdateDocumentRequestTranslationWithNoRefresh()
		throws IOException {

		doTestUpdateDocumentRequestTranslation(
			"1", false, WriteRequest.RefreshPolicy.NONE);
	}

	@Test
	public void testUpdateDocumentRequestTranslationWithNoRefreshNoId()
		throws IOException {

		doTestUpdateDocumentRequestTranslation(
			null, false, WriteRequest.RefreshPolicy.NONE);
	}

	@Test
	public void testUpdateDocumentRequestTranslationWithRefresh()
		throws IOException {

		doTestUpdateDocumentRequestTranslation(
			"1", true, WriteRequest.RefreshPolicy.IMMEDIATE);
	}

	@Test
	public void testUpdateDocumentRequestTranslationWithRefreshNoId()
		throws IOException {

		doTestUpdateDocumentRequestTranslation(
			null, true, WriteRequest.RefreshPolicy.IMMEDIATE);
	}

	protected void doTestDeleteDocumentRequestTranslation(
		boolean refreshPolicy,
		WriteRequest.RefreshPolicy expectedRefreshPolicy) {

		String id = "1";

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			_INDEX_NAME, id);

		deleteDocumentRequest.setRefresh(refreshPolicy);
		deleteDocumentRequest.setType(_MAPPING_NAME);

		DeleteRequestBuilder deleteRequestBuilder =
			_elasticsearchBulkableDocumentRequestTranslator.translate(
				deleteDocumentRequest, null);

		DeleteRequest deleteRequest = deleteRequestBuilder.request();

		Assert.assertEquals(
			expectedRefreshPolicy, deleteRequest.getRefreshPolicy());
		Assert.assertEquals(_INDEX_NAME, deleteRequest.index());
		Assert.assertEquals(_MAPPING_NAME, deleteRequest.type());
		Assert.assertEquals(id, deleteRequest.id());

		Client client = _elasticsearchFixture.getClient();

		BulkRequestBuilder bulkRequestBuilder =
			BulkAction.INSTANCE.newRequestBuilder(client);

		_elasticsearchBulkableDocumentRequestTranslator.translate(
			deleteDocumentRequest, bulkRequestBuilder);

		Assert.assertEquals(1, bulkRequestBuilder.numberOfActions());
	}

	protected void doTestIndexDocumentRequestTranslation(
			String id, boolean refreshPolicy,
			WriteRequest.RefreshPolicy expectedRefreshPolicy)
		throws IOException {

		Document document = new DocumentImpl();

		_setUid(document, id);

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			_INDEX_NAME, document);

		indexDocumentRequest.setRefresh(refreshPolicy);
		indexDocumentRequest.setType(_MAPPING_NAME);

		IndexRequestBuilder indexRequestBuilder =
			_elasticsearchBulkableDocumentRequestTranslator.translate(
				indexDocumentRequest, null);

		IndexRequest indexRequest = indexRequestBuilder.request();

		Assert.assertEquals(
			expectedRefreshPolicy, indexRequest.getRefreshPolicy());
		Assert.assertEquals(_INDEX_NAME, indexRequest.index());
		Assert.assertEquals(_MAPPING_NAME, indexRequest.type());
		Assert.assertEquals(id, indexRequest.id());

		ElasticsearchDocumentFactory elasticsearchDocumentFactory =
			new DefaultElasticsearchDocumentFactory();

		String source = XContentHelper.convertToJson(
			indexRequest.source(), false, XContentType.JSON);

		Assert.assertEquals(
			elasticsearchDocumentFactory.getElasticsearchDocument(document),
			source);

		Client client = _elasticsearchFixture.getClient();

		BulkRequestBuilder bulkRequestBuilder =
			BulkAction.INSTANCE.newRequestBuilder(client);

		_elasticsearchBulkableDocumentRequestTranslator.translate(
			indexDocumentRequest, bulkRequestBuilder);

		Assert.assertEquals(1, bulkRequestBuilder.numberOfActions());
	}

	protected void doTestUpdateDocumentRequestTranslation(
			String id, boolean refreshPolicy,
			WriteRequest.RefreshPolicy expectedRefreshPolicy)
		throws IOException {

		Document document = new DocumentImpl();

		_setUid(document, id);

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			_INDEX_NAME, id, document);

		updateDocumentRequest.setRefresh(refreshPolicy);
		updateDocumentRequest.setType(_MAPPING_NAME);

		UpdateRequestBuilder updateRequestBuilder =
			_elasticsearchBulkableDocumentRequestTranslator.translate(
				updateDocumentRequest, null);

		UpdateRequest updateRequest = updateRequestBuilder.request();

		Assert.assertEquals(
			expectedRefreshPolicy, updateRequest.getRefreshPolicy());
		Assert.assertEquals(_INDEX_NAME, updateRequest.index());
		Assert.assertEquals(id, updateRequest.id());

		ElasticsearchDocumentFactory elasticsearchDocumentFactory =
			new DefaultElasticsearchDocumentFactory();

		IndexRequest indexRequest = updateRequest.doc();

		String source = XContentHelper.convertToJson(
			indexRequest.source(), false, XContentType.JSON);

		Assert.assertEquals(
			elasticsearchDocumentFactory.getElasticsearchDocument(document),
			source);

		Client client = _elasticsearchFixture.getClient();

		BulkRequestBuilder bulkRequestBuilder =
			BulkAction.INSTANCE.newRequestBuilder(client);

		_elasticsearchBulkableDocumentRequestTranslator.translate(
			updateDocumentRequest, bulkRequestBuilder);

		Assert.assertEquals(1, bulkRequestBuilder.numberOfActions());
	}

	private void _setUid(Document document, String uid) {
		if (!Validator.isBlank(uid)) {
			document.addKeyword(Field.UID, uid);
		}
	}

	private static final String _INDEX_NAME = "test_request_index";

	private static final String _MAPPING_NAME = "testMapping";

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private ElasticsearchBulkableDocumentRequestTranslator
		_elasticsearchBulkableDocumentRequestTranslator;
	private ElasticsearchFixture _elasticsearchFixture;

}