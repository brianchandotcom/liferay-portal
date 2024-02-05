/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
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
public class UpdateDocumentRequestExecutorTest extends BaseOpenSearchTestCase {

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
	public void testUnsetValue() {
		String fieldName = _FIELD_NAME;

		IndexDocumentResponse indexDocumentResponse =
			_requestExecutorFixture.indexDocument(
				buildDocument(fieldName, "example test"), TEST_INDEX_NAME);

		String uid = indexDocumentResponse.getUid();

		assertIndexedFieldEquals("example test", _FIELD_NAME, uid);

		_updateDocumentRequestExecutor.execute(
			new UpdateDocumentRequest(
				TEST_INDEX_NAME, uid, buildDocumentWithUnsetField(fieldName)));

		assertIndexedFieldEquals("example test", _FIELD_NAME, uid);
	}

	@Test
	public void testUnsetValueWithArrayWithNull() {
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
	public void testUnsetValueWithEmptyArray() {
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
	public void testUnsetValueWithNull() {
		IndexDocumentResponse indexDocumentResponse =
			_requestExecutorFixture.indexDocument(
				buildDocument(_FIELD_NAME, "example test"), TEST_INDEX_NAME);

		String uid = indexDocumentResponse.getUid();

		assertIndexedFieldEquals("example test", _FIELD_NAME, uid);

		_updateDocumentRequestExecutor.execute(
			new UpdateDocumentRequest(
				TEST_INDEX_NAME, uid,
				buildDocument(_FIELD_NAME, (String)null)));

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

		Document document = _requestExecutorFixture.getDocumentById(
			TEST_INDEX_NAME, uid);

		Assert.assertEquals(expectedFieldValue, document.getString(fieldName));
	}

	protected Document buildDocument(String fieldName, String... fieldValue) {
		DocumentBuilder documentBuilder = new DocumentBuilderImpl();

		return documentBuilder.setStrings(
			fieldName, fieldValue
		).build();
	}

	protected Document buildDocument(
		String fieldName, String fieldValue, String uid) {

		DocumentBuilder documentBuilder = new DocumentBuilderImpl();

		return documentBuilder.setString(
			fieldName, fieldValue
		).setString(
			Field.UID, uid
		).build();
	}

	protected Document buildDocumentWithUnsetField(String fieldName) {
		DocumentBuilder documentBuilder = new DocumentBuilderImpl();

		return documentBuilder.unsetValue(
			fieldName
		).build();
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