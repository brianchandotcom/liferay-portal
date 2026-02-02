/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.document;

import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.DeleteOperation;
import co.elastic.clients.elasticsearch.core.bulk.IndexOperation;
import co.elastic.clients.elasticsearch.core.bulk.UpdateOperation;
import co.elastic.clients.json.JsonData;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch8.internal.document.ElasticsearchDocumentFactoryUtil;
import com.liferay.portal.search.elasticsearch8.internal.util.JsonpUtil;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class ElasticsearchBulkableDocumentRequestTranslatorUtilTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			ElasticsearchBulkableDocumentRequestTranslatorUtilTest.class);

		_elasticsearchFixture.setUp();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Before
	public void setUp() throws Exception {
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

	private void _setUid(Document document, String uid) {
		if (!Validator.isBlank(uid)) {
			document.addKeyword(Field.UID, uid);
		}
	}

	private void _testDeleteDocumentRequestTranslation() {
		String id = "1";

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			_INDEX_NAME, id);

		DeleteOperation deleteOperation =
			ElasticsearchBulkableDocumentRequestTranslatorUtil.translate(
				deleteDocumentRequest);

		Assert.assertEquals(_INDEX_NAME, deleteOperation.index());
		Assert.assertEquals(id, deleteOperation.id());

		BulkRequest bulkRequest = BulkRequest.of(
			elasticsearchBulkRequest -> elasticsearchBulkRequest.operations(
				new BulkOperation(
					ElasticsearchBulkableDocumentRequestTranslatorUtil.
						translate(deleteDocumentRequest))));

		List<BulkOperation> bulkOperations = bulkRequest.operations();

		Assert.assertEquals(
			_bulkOperationsToString(bulkOperations), 1, bulkOperations.size());
	}

	private void _testIndexDocumentRequestTranslation(String id)
		throws Exception {

		Document document = new DocumentImpl();

		_setUid(document, id);

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			_INDEX_NAME, document);

		IndexOperation indexOperation =
			ElasticsearchBulkableDocumentRequestTranslatorUtil.translate(
				indexDocumentRequest);

		Assert.assertEquals(_INDEX_NAME, indexOperation.index());
		Assert.assertEquals(id, indexOperation.id());

		JsonData jsonData1 =
			ElasticsearchDocumentFactoryUtil.getElasticsearchDocument(document);
		JsonData jsonData2 = (JsonData)indexOperation.document();

		Assert.assertEquals(jsonData1.toString(), jsonData2.toString());

		BulkRequest bulkRequest = BulkRequest.of(
			elasticsearchBulkRequest -> elasticsearchBulkRequest.operations(
				new BulkOperation(
					ElasticsearchBulkableDocumentRequestTranslatorUtil.
						translate(indexDocumentRequest))));

		List<BulkOperation> bulkOperations = bulkRequest.operations();

		Assert.assertEquals(
			_bulkOperationsToString(bulkOperations), 1, bulkOperations.size());
	}

	private void _testUpdateDocumentRequestTranslation(String id)
		throws Exception {

		Document document = new DocumentImpl();

		_setUid(document, id);

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			_INDEX_NAME, id, document);

		UpdateOperation updateOperation =
			ElasticsearchBulkableDocumentRequestTranslatorUtil.translate(
				updateDocumentRequest);

		Assert.assertEquals(_INDEX_NAME, updateOperation.index());
		Assert.assertEquals(id, updateOperation.id());

		BulkRequest bulkRequest = BulkRequest.of(
			elasticsearchBulkRequest -> elasticsearchBulkRequest.operations(
				new BulkOperation(
					ElasticsearchBulkableDocumentRequestTranslatorUtil.
						translate(updateDocumentRequest))));

		List<BulkOperation> bulkOperations = bulkRequest.operations();

		Assert.assertEquals(
			_bulkOperationsToString(bulkOperations), 1, bulkOperations.size());
	}

	private static final String _INDEX_NAME = "test_request_index";

	private static ElasticsearchFixture _elasticsearchFixture;

	private final DocumentFixture _documentFixture = new DocumentFixture();

}