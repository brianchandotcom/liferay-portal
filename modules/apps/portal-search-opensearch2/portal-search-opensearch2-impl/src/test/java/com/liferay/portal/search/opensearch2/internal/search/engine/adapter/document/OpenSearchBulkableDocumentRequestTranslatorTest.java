/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.opensearch2.internal.BaseOpenSearchTestCase;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.document.OpenSearchDocumentFactory;
import com.liferay.portal.search.opensearch2.internal.document.OpenSearchDocumentFactoryImpl;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.core.BulkRequest;
import org.opensearch.client.opensearch.core.bulk.BulkOperation;
import org.opensearch.client.opensearch.core.bulk.DeleteOperation;
import org.opensearch.client.opensearch.core.bulk.IndexOperation;
import org.opensearch.client.opensearch.core.bulk.UpdateOperation;

/**
 * @author Michael C. Han
 */
public class OpenSearchBulkableDocumentRequestTranslatorTest
	extends BaseOpenSearchTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		OpenSearchDocumentFactory openSearchDocumentFactory =
			_createOpenSearchDocumentFactory();

		OpenSearchBulkableDocumentRequestTranslator
			openSearchBulkableDocumentRequestTranslator =
				_createOpenSearchBulkableDocumentRequestTranslator(
					openSearchDocumentFactory);

		_openSearchBulkableDocumentRequestTranslator =
			openSearchBulkableDocumentRequestTranslator;

		_openSearchDocumentFactory = openSearchDocumentFactory;

		_documentFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_documentFixture.tearDown();
	}

	@Test
	public void testDeleteDocumentRequestTranslation() {
		_testDeleteDocumentRequestTranslation();
	}

	@Test
	public void testIndexDocumentRequestTranslation() throws Exception {
		_testIndexDocumentRequestTranslation("1");
	}

	@Test
	public void testIndexDocumentRequestTranslationWithNoId() throws Exception {
		_testIndexDocumentRequestTranslation(null);
	}

	@Test
	public void testUpdateDocumentRequestTranslation() throws Exception {
		_testUpdateDocumentRequestTranslation("1");
	}

	@Test
	public void testUpdateDocumentRequestTranslationWithNoId()
		throws Exception {

		_testUpdateDocumentRequestTranslation(null);
	}

	private String _bulkOperationsToString(List<BulkOperation> bulkOperations) {
		StringBundler sb = new StringBundler();

		for (BulkOperation bulkOperation : bulkOperations) {
			sb.append(JsonpUtil.toString(bulkOperation));
			sb.append("\n");
		}

		return sb.toString();
	}

	private OpenSearchBulkableDocumentRequestTranslator
		_createOpenSearchBulkableDocumentRequestTranslator(
			OpenSearchDocumentFactory openSearchDocumentFactory) {

		OpenSearchBulkableDocumentRequestTranslator
			openSearchBulkableDocumentRequestTranslator =
				new OpenSearchBulkableDocumentRequestTranslatorImpl();

		ReflectionTestUtil.setFieldValue(
			openSearchBulkableDocumentRequestTranslator,
			"openSearchDocumentFactory", openSearchDocumentFactory);

		return openSearchBulkableDocumentRequestTranslator;
	}

	private OpenSearchDocumentFactory _createOpenSearchDocumentFactory() {
		return new OpenSearchDocumentFactoryImpl();
	}

	private void _setUid(Document document, String uid) {
		if (!Validator.isBlank(uid)) {
			document.addKeyword(Field.UID, uid);
		}
	}

	private void _testDeleteDocumentRequestTranslation() {
		String id = "1";

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			TEST_INDEX_NAME, id);

		DeleteOperation deleteOperation =
			_openSearchBulkableDocumentRequestTranslator.translate(
				deleteDocumentRequest);

		Assert.assertEquals(TEST_INDEX_NAME, deleteOperation.index());
		Assert.assertEquals(id, deleteOperation.id());

		BulkRequest bulkRequest = BulkRequest.of(
			openSearchBulkRequest -> openSearchBulkRequest.operations(
				new BulkOperation(
					_openSearchBulkableDocumentRequestTranslator.translate(
						deleteDocumentRequest))));

		List<BulkOperation> bulkOperations = bulkRequest.operations();

		Assert.assertEquals(
			_bulkOperationsToString(bulkOperations), 1, bulkOperations.size());
	}

	private void _testIndexDocumentRequestTranslation(String id)
		throws Exception {

		Document document = new DocumentImpl();

		_setUid(document, id);

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			TEST_INDEX_NAME, document);

		IndexOperation indexOperation =
			_openSearchBulkableDocumentRequestTranslator.translate(
				indexDocumentRequest);

		Assert.assertEquals(TEST_INDEX_NAME, indexOperation.index());
		Assert.assertEquals(id, indexOperation.id());

		JsonData jsonData1 = _openSearchDocumentFactory.getOpenSearchDocument(
			document);
		JsonData jsonData2 = (JsonData)indexOperation.document();

		Assert.assertEquals(jsonData1.toString(), jsonData2.toString());

		BulkRequest bulkRequest = BulkRequest.of(
			openSearchBulkRequest -> openSearchBulkRequest.operations(
				new BulkOperation(
					_openSearchBulkableDocumentRequestTranslator.translate(
						indexDocumentRequest))));

		List<BulkOperation> bulkOperations = bulkRequest.operations();

		Assert.assertEquals(
			_bulkOperationsToString(bulkOperations), 1, bulkOperations.size());
	}

	private void _testUpdateDocumentRequestTranslation(String id)
		throws Exception {

		Document document = new DocumentImpl();

		_setUid(document, id);

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			TEST_INDEX_NAME, id, document);

		UpdateOperation updateOperation =
			_openSearchBulkableDocumentRequestTranslator.translate(
				updateDocumentRequest);

		Assert.assertEquals(TEST_INDEX_NAME, updateOperation.index());
		Assert.assertEquals(id, updateOperation.id());

		BulkRequest bulkRequest = BulkRequest.of(
			openSearchBulkRequest -> openSearchBulkRequest.operations(
				new BulkOperation(
					_openSearchBulkableDocumentRequestTranslator.translate(
						updateDocumentRequest))));

		List<BulkOperation> bulkOperations = bulkRequest.operations();

		Assert.assertEquals(
			_bulkOperationsToString(bulkOperations), 1, bulkOperations.size());
	}

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private OpenSearchBulkableDocumentRequestTranslator
		_openSearchBulkableDocumentRequestTranslator;
	private OpenSearchDocumentFactory _openSearchDocumentFactory;

}