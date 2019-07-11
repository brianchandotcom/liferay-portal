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

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.legacy.query.ElasticsearchQueryTranslatorFixture;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;

import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Dylan Rebelak
 */
public class DeleteByQueryDocumentRequestExecutorTest {

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			DeleteByQueryDocumentRequestExecutorTest.class.getSimpleName());

		_elasticsearchFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testDocumentRequestTranslationWithNoRefresh() {
		doTestDocumentRequestTranslation(false);
	}

	@Test
	public void testDocumentRequestTranslationWithRefresh() {
		doTestDocumentRequestTranslation(true);
	}

	protected void doTestDocumentRequestTranslation(boolean refresh) {
		BooleanQuery booleanQuery = new BooleanQueryImpl();

		booleanQuery.addExactTerm(_FIELD_NAME, true);

		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest =
			new DeleteByQueryDocumentRequest(
				booleanQuery, new String[] {_INDEX_NAME});

		deleteByQueryDocumentRequest.setRefresh(refresh);

		DeleteByQueryDocumentRequestExecutorImpl
			deleteByQueryDocumentRequestExecutorImpl =
				new DeleteByQueryDocumentRequestExecutorImpl() {
					{
						setElasticsearchClientResolver(_elasticsearchFixture);

						ElasticsearchQueryTranslatorFixture
							elasticsearchQueryTranslatorFixture =
								new ElasticsearchQueryTranslatorFixture();

						setQueryTranslator(
							elasticsearchQueryTranslatorFixture.
								getElasticsearchQueryTranslator());
					}
				};

		DeleteByQueryRequestBuilder deleteByQueryRequestBuilder =
			deleteByQueryDocumentRequestExecutorImpl.
				createDeleteByQueryRequestBuilder(deleteByQueryDocumentRequest);

		DeleteByQueryRequest deleteByQueryRequest =
			deleteByQueryRequestBuilder.request();

		Assert.assertArrayEquals(
			new String[] {_INDEX_NAME}, deleteByQueryRequest.indices());

		Assert.assertEquals(
			deleteByQueryDocumentRequest.isRefresh(),
			deleteByQueryRequest.isRefresh());

		String queryString = String.valueOf(
			deleteByQueryRequest.getSearchRequest());

		Assert.assertTrue(queryString.contains(_FIELD_NAME));
		Assert.assertTrue(queryString.contains("\"value\":\"true\""));
	}

	private static final String _FIELD_NAME = "testField";

	private static final String _INDEX_NAME = "test_request_index";

	private ElasticsearchFixture _elasticsearchFixture;

}