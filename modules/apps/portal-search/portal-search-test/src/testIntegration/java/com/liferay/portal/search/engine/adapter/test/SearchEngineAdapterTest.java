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
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
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

		_updateDocument(uid, "delta", "echo");

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
		catch (RuntimeException re) {
			assertClientSideSafeToLoad(re);

			String message = re.getMessage();

			if (isSearchEngine("Solr")) {
				Assert.assertTrue(
					message,
					message.contains(
						"<p>Problem accessing /solr/" + index + "/update"));
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
		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine(
			_searchEngineHelper.getDefaultSearchEngineId());

		String vendor = searchEngine.getVendor();

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
		deleteDocumentRequest.setType("LiferayDocumentType");

		return deleteDocumentRequest;
	}

	private Document _getDocument(String uid) throws Exception {
		GetDocumentRequest getDocumentRequest = new GetDocumentRequest(
			getIndexName(), uid);

		getDocumentRequest.setRefresh(true);
		getDocumentRequest.setType("LiferayDocumentType");

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

		indexDocumentRequest.setType("LiferayDocumentType");
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
			String uid, String field2Value, String field3value)
		throws Exception {

		DocumentBuilder documentBuilder = _documentBuilderFactory.builder();

		documentBuilder.setValue("uid", uid);
		documentBuilder.setValue("field2", field2Value);
		documentBuilder.setValue("field3", field3value);

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			getIndexName(), uid, documentBuilder.build());

		updateDocumentRequest.setRefresh(true);
		updateDocumentRequest.setType("LiferayDocumentType");

		_searchEngineAdapter.execute(updateDocumentRequest);
	}

	@Inject
	private static DocumentBuilderFactory _documentBuilderFactory;

	@Inject
	private static SearchEngineAdapter _searchEngineAdapter;

	@Inject
	private static SearchEngineHelper _searchEngineHelper;

}