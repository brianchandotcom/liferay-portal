/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
public class SearchEngineAdapterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testBasicSearchEngineOperations() throws Exception {
		String uid = "alpha";

		_indexDocument(uid);

		Document document = _getDocument(uid);

		Assert.assertEquals(
			document.toString(), uid, document.getString("uid"));

		Assert.assertEquals(
			document.toString(), "bravo", document.getString("field1"));

		Assert.assertEquals(
			document.toString(), "charlie", document.getString("field2"));

		_updateDocument(uid, "delta", "echo", false);

		document = _getDocument(uid);

		Assert.assertEquals(
			document.toString(), uid, document.getString("uid"));

		Assert.assertEquals(
			document.toString(), "bravo", document.getString("field1"));

		Assert.assertEquals(
			document.toString(), "delta", document.getString("field2"));

		Assert.assertEquals(
			document.toString(), "echo", document.getString("field3"));

		_deleteDocument(uid);

		document = _getDocument(uid);

		Assert.assertNull(document);

		_updateDocument(uid, "delta", "echo", true);

		document = _getDocument(uid);

		Assert.assertEquals(
			document.toString(), uid, document.getString("uid"));

		Assert.assertEquals(
			document.toString(), "delta", document.getString("field2"));

		Assert.assertEquals(
			document.toString(), "echo", document.getString("field3"));

		_deleteDocument(uid);

		Assert.assertNull(_getDocument(uid));
	}

	@Test
	public void testBulkDocumentRequest() throws Exception {
		BulkDocumentRequest bulkIndexDocumentRequest =
			new BulkDocumentRequest();

		bulkIndexDocumentRequest.addBulkableDocumentRequest(
			_getIndexDocumentRequest("alpha", false));

		bulkIndexDocumentRequest.addBulkableDocumentRequest(
			_getIndexDocumentRequest("bravo", false));

		bulkIndexDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(bulkIndexDocumentRequest);

		Document alphaDocument = _getDocument("alpha");

		Assert.assertEquals(
			alphaDocument.toString(), "alpha", alphaDocument.getString("uid"));

		Document bravoDocument = _getDocument("bravo");

		Assert.assertEquals(
			bravoDocument.toString(), "bravo", bravoDocument.getString("uid"));

		BulkDocumentRequest bulkDeleteDocumentRequest =
			new BulkDocumentRequest();

		bulkDeleteDocumentRequest.addBulkableDocumentRequest(
			_getDeleteDocumentRequest("alpha", false));

		bulkDeleteDocumentRequest.addBulkableDocumentRequest(
			_getDeleteDocumentRequest("bravo", false));

		bulkDeleteDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(bulkDeleteDocumentRequest);

		alphaDocument = _getDocument("alpha");

		Assert.assertNull(alphaDocument);

		bravoDocument = _getDocument("bravo");

		Assert.assertNull(bravoDocument);
	}

	@Test
	public void testExceptionBoundaries() {
		String index = RandomTestUtil.randomString();

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			index, index);

		try {
			_searchEngineAdapter.execute(deleteDocumentRequest);

			Assert.fail("Exception was not thrown");
		}
		catch (RuntimeException runtimeException) {
			assertClientSideSafeToLoad(runtimeException);

			String message = runtimeException.getMessage();

			if (isSearchEngine("Solr")) {
				Assert.assertTrue(
					message,
					message.contains(
						"org.apache.solr.client.solrj.impl." +
							"HttpSolrClient$RemoteSolrException"));
				Assert.assertTrue(
					message,
					message.contains(
						"<tr><th>URI:</th><td>/solr/" + index +
							"/update</td></tr>"));
			}
			else if (isSearchEngine("Elasticsearch7")) {
				Assert.assertTrue(
					message,
					message.contains("reason=no such index [" + index + "]"));
			}
			else {
				Assert.assertTrue(
					message,
					message.startsWith(
						"org.elasticsearch.index.IndexNotFoundException: [" +
							index + "] IndexNotFoundException[no such index"));
			}
		}
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertClientSideSafeToLoad(Throwable throwable) {
		if (throwable == null) {
			return;
		}

		Class<?> clazz = throwable.getClass();

		String name = clazz.getName();

		if (name.startsWith("org.elasticsearch") ||
			name.startsWith("org.apache.solr")) {

			throw _getTestFrameworkSafeToLoadException(
				name, throwable.getMessage(), throwable.getStackTrace());
		}

		assertClientSideSafeToLoad(throwable.getCause());
	}

	protected String getIndexName() throws Exception {
		if (isSearchEngine("Solr")) {
			return "liferay";
		}

		return "liferay-" + TestPropsValues.getCompanyId();
	}

	protected boolean isSearchEngine(String engine) {
		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		String vendor = searchEngine.getVendor();

		if (engine.equals("Elasticsearch7")) {
			String version = _searchEngineInformation.getClientVersionString();

			if (vendor.equals("Elasticsearch") && version.startsWith("7")) {
				return true;
			}

			return false;
		}

		return vendor.equals(engine);
	}

	private void _deleteDocument(String uid) throws Exception {
		_searchEngineAdapter.execute(_getDeleteDocumentRequest(uid, true));
	}

	private DeleteDocumentRequest _getDeleteDocumentRequest(
			String uid, boolean refresh)
		throws Exception {

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			getIndexName(), uid);

		deleteDocumentRequest.setRefresh(refresh);
		deleteDocumentRequest.setType("_doc");

		return deleteDocumentRequest;
	}

	private Document _getDocument(String uid) throws Exception {
		GetDocumentRequest getDocumentRequest = new GetDocumentRequest(
			getIndexName(), uid);

		getDocumentRequest.setFetchSource(true);
		getDocumentRequest.setRefresh(true);
		getDocumentRequest.setType("_doc");

		GetDocumentResponse getDocumentResponse = _searchEngineAdapter.execute(
			getDocumentRequest);

		return getDocumentResponse.getDocument();
	}

	private IndexDocumentRequest _getIndexDocumentRequest(
			String uid, boolean refresh)
		throws Exception {

		DocumentBuilder documentBuilder = _documentBuilderFactory.builder();

		documentBuilder.setValue("uid", uid);
		documentBuilder.setValue("field1", "bravo");
		documentBuilder.setValue("field2", "charlie");

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			getIndexName(), documentBuilder.build());

		indexDocumentRequest.setType("_doc");
		indexDocumentRequest.setRefresh(refresh);

		return indexDocumentRequest;
	}

	private RuntimeException _getTestFrameworkSafeToLoadException(
		String name, String message, StackTraceElement[] stackTraceElements) {

		RuntimeException runtimeException = new RuntimeException(
			name + ": " + message);

		runtimeException.setStackTrace(stackTraceElements);

		return runtimeException;
	}

	private void _indexDocument(String uid) throws Exception {
		_searchEngineAdapter.execute(_getIndexDocumentRequest(uid, true));
	}

	private void _updateDocument(
			String uid, String field2Value, String field3value, boolean upsert)
		throws Exception {

		DocumentBuilder documentBuilder = _documentBuilderFactory.builder();

		documentBuilder.setValue("uid", uid);
		documentBuilder.setValue("field2", field2Value);
		documentBuilder.setValue("field3", field3value);

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			getIndexName(), uid, documentBuilder.build());

		updateDocumentRequest.setRefresh(true);
		updateDocumentRequest.setType("_doc");
		updateDocumentRequest.setUpsert(upsert);

		_searchEngineAdapter.execute(updateDocumentRequest);
	}

	@Inject
	private static DocumentBuilderFactory _documentBuilderFactory;

	@Inject
	private static SearchEngineAdapter _searchEngineAdapter;

	@Inject
	private static SearchEngineHelper _searchEngineHelper;

	@Inject
	private static SearchEngineInformation _searchEngineInformation;

}