/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
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
@SuppressWarnings("deprecation")
public class UpdateDocumentRequestExecutorLegacyTest
	extends BaseOpenSearchTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
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
		_updateDocumentRequestExecutor =
			_requestExecutorFixture.getUpdateDocumentRequestExecutor();

		_requestExecutorFixture.createIndex(TEST_INDEX_NAME);
	}

	@After
	public void tearDown() {
		_requestExecutorFixture.deleteIndex(TEST_INDEX_NAME);
	}

	@Test
	public void testFieldArrayWithNull() {
		IndexDocumentResponse indexDocumentResponse =
			_requestExecutorFixture.indexDocument(
				buildDocument(_FIELD_NAME, "example test"), TEST_INDEX_NAME);

		String uid = indexDocumentResponse.getUid();

		assertIndexedFieldEquals("example test", _FIELD_NAME, uid);

		_updateDocumentRequestExecutor.execute(
			new UpdateDocumentRequest(
				TEST_INDEX_NAME, uid,
				buildDocument(_FIELD_NAME, new String[] {null})));

		assertIndexedFieldEquals("example test", _FIELD_NAME, uid);
	}

	@Test
	public void testFieldEmptyArray() {
		IndexDocumentResponse indexDocumentResponse =
			_requestExecutorFixture.indexDocument(
				buildDocument(_FIELD_NAME, "example test"), TEST_INDEX_NAME);

		String uid = indexDocumentResponse.getUid();

		assertIndexedFieldEquals("example test", _FIELD_NAME, uid);

		_updateDocumentRequestExecutor.execute(
			new UpdateDocumentRequest(
				TEST_INDEX_NAME, uid,
				buildDocument(_FIELD_NAME, new String[0])));

		assertIndexedFieldEquals("example test", _FIELD_NAME, uid);
	}

	@Test
	public void testFieldNull() {
		IndexDocumentResponse indexDocumentResponse =
			_requestExecutorFixture.indexDocument(
				buildDocument(_FIELD_NAME, "example test"), TEST_INDEX_NAME);

		String uid = indexDocumentResponse.getUid();

		assertIndexedFieldEquals("example test", _FIELD_NAME, uid);

		_updateDocumentRequestExecutor.execute(
			new UpdateDocumentRequest(
				TEST_INDEX_NAME, uid,
				buildDocument(_FIELD_NAME, (String[])null)));

		assertIndexedFieldEquals("example test", _FIELD_NAME, uid);
	}

	@Test
	public void testUnsetValue() {
		IndexDocumentResponse indexDocumentResponse =
			_requestExecutorFixture.indexDocument(
				buildDocument(_FIELD_NAME, "example test"), TEST_INDEX_NAME);

		String uid = indexDocumentResponse.getUid();

		assertIndexedFieldEquals("example test", _FIELD_NAME, uid);

		_updateDocumentRequestExecutor.execute(
			new UpdateDocumentRequest(
				TEST_INDEX_NAME, uid,
				buildDocumentWithUnsetField(_FIELD_NAME)));

		assertIndexedFieldEquals("example test", _FIELD_NAME, uid);
	}

	@Test
	public void testUpdateDocumentWithNoRefresh() {
		doUpdateDocument(false);
	}

	@Test
	public void testUpdateDocumentWithRefresh() {
		doUpdateDocument(true);
	}

	protected void assertIndexedFieldEquals(
		String expectedFieldValue, String fieldName, String uid) {

		com.liferay.portal.search.document.Document document =
			_requestExecutorFixture.getDocumentById(TEST_INDEX_NAME, uid);

		Assert.assertEquals(expectedFieldValue, document.getString(fieldName));
	}

	protected Document buildDocument(String fieldName, String... fieldValue) {
		Document document = new DocumentImpl();

		document.addText(fieldName, fieldValue);

		return document;
	}

	protected Document buildDocument(
		String fieldName, String fieldValue, String uid) {

		Document document = new DocumentImpl();

		document.addKeyword(Field.UID, uid);
		document.addText(fieldName, fieldValue);

		return document;
	}

	protected Document buildDocumentWithUnsetField(String fieldName) {
		Document document = new DocumentImpl();

		document.remove(fieldName);

		return document;
	}

	protected void doUpdateDocument(boolean refresh) {
		IndexDocumentResponse indexDocumentResponse =
			_requestExecutorFixture.indexDocument(
				buildDocument(_FIELD_NAME, "example test"), TEST_INDEX_NAME);

		String uid = indexDocumentResponse.getUid();

		assertIndexedFieldEquals("example test", _FIELD_NAME, uid);

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			TEST_INDEX_NAME, uid, buildDocument(_FIELD_NAME, "updated value"));

		updateDocumentRequest.setRefresh(refresh);

		_updateDocumentRequestExecutor.execute(updateDocumentRequest);

		assertIndexedFieldEquals("updated value", _FIELD_NAME, uid);
	}

	private static final String _FIELD_NAME = "testField";

	private static RequestExecutorFixture _requestExecutorFixture;

	private UpdateDocumentRequestExecutor _updateDocumentRequestExecutor;

}