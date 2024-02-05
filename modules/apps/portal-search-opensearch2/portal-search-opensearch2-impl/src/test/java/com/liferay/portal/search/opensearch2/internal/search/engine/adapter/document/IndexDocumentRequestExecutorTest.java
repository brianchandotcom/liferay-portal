/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.internal.document.DocumentBuilderImpl;
import com.liferay.portal.search.opensearch2.internal.BaseOpenSearchTestCase;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.test.util.RequestExecutorFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class IndexDocumentRequestExecutorTest extends BaseOpenSearchTestCase {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_requestExecutorFixture = new RequestExecutorFixture(
			openSearchConnectionManager);

		_requestExecutorFixture.setUp();
	}

	@Before
	public void setUp() {
		_indexDocumentRequestExecutor =
			_requestExecutorFixture.getIndexDocumentRequestExecutor();

		_requestExecutorFixture.createIndex(TEST_INDEX_NAME);
	}

	@After
	public void tearDown() {
		_requestExecutorFixture.deleteIndex(TEST_INDEX_NAME);
	}

	@Test
	public void testIndexDocumentWithNoRefresh() {
		_indexDocument(false);
	}

	@Test
	public void testIndexDocumentWithRefresh() {
		_indexDocument(true);
	}

	protected Document buildDocument(String fieldName, String fieldValue) {
		DocumentBuilder documentBuilder = new DocumentBuilderImpl();

		return documentBuilder.setString(
			fieldName, fieldValue
		).build();
	}

	private void _assertFieldEquals(
		Document actualDocument, Document expectedDocument, String fieldName) {

		Assert.assertEquals(
			expectedDocument.getString(fieldName),
			actualDocument.getString(fieldName));
	}

	private void _indexDocument(boolean refresh) {
		Document document = buildDocument(_FIELD_NAME, "example test");

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			TEST_INDEX_NAME, document);

		indexDocumentRequest.setRefresh(refresh);

		IndexDocumentResponse indexDocumentResponse =
			_indexDocumentRequestExecutor.execute(indexDocumentRequest);

		_assertFieldEquals(
			_requestExecutorFixture.getDocumentById(
				TEST_INDEX_NAME, indexDocumentResponse.getUid()),
			document, _FIELD_NAME);
	}

	private static final String _FIELD_NAME = "testField";

	private static RequestExecutorFixture _requestExecutorFixture;

	private IndexDocumentRequestExecutor _indexDocumentRequestExecutor;

}