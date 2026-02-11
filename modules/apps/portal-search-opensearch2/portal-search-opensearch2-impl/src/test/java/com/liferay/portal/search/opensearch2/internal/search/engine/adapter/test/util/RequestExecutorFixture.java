/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.test.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentResponse;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document.GetDocumentRequestExecutor;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document.GetDocumentRequestExecutorImpl;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.index.CreateIndexRequestExecutor;
import com.liferay.portal.search.opensearch2.internal.search.engine.adapter.index.DeleteIndexRequestExecutor;

/**
 * @author Adam Brandizzi
 * @author Petteri Karttunen
 */
public class RequestExecutorFixture {

	public RequestExecutorFixture(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_openSearchConnectionManager = openSearchConnectionManager;
	}

	public void createIndex(String indexName) {
		_createIndexRequestExecutor.execute(new CreateIndexRequest(indexName));
	}

	public void deleteIndex(String indexName) {
		_deleteIndexRequestExecutor.execute(new DeleteIndexRequest(indexName));
	}

	public CreateIndexRequestExecutor getCreateIndexRequestExecutor() {
		return _createIndexRequestExecutor;
	}

	public DeleteIndexRequestExecutor getDeleteIndexRequestExecutor() {
		return _deleteIndexRequestExecutor;
	}

	public Document getDocumentById(String indexName, String uid) {
		GetDocumentRequest getDocumentRequest = new GetDocumentRequest(
			indexName, uid);

		getDocumentRequest.setFetchSource(true);
		getDocumentRequest.setFetchSourceInclude(StringPool.STAR);

		GetDocumentResponse getDocumentResponse =
			_getDocumentRequestExecutor.execute(getDocumentRequest);

		return getDocumentResponse.getDocument();
	}

	public GetDocumentRequestExecutor getGetDocumentRequestExecutor() {
		return _getDocumentRequestExecutor;
	}

	public void setUp() {
		_createIndexRequestExecutor = new CreateIndexRequestExecutor(
			new JSONFactoryImpl(), _openSearchConnectionManager);
		_deleteIndexRequestExecutor = new DeleteIndexRequestExecutor(
			_openSearchConnectionManager);
		_getDocumentRequestExecutor = _createGetDocumentRequestExecutor();
	}

	private GetDocumentRequestExecutor _createGetDocumentRequestExecutor() {
		GetDocumentRequestExecutor getDocumentRequestExecutor =
			new GetDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			getDocumentRequestExecutor, "_openSearchConnectionManager",
			_openSearchConnectionManager);

		return getDocumentRequestExecutor;
	}

	private CreateIndexRequestExecutor _createIndexRequestExecutor;
	private DeleteIndexRequestExecutor _deleteIndexRequestExecutor;
	private GetDocumentRequestExecutor _getDocumentRequestExecutor;
	private final OpenSearchConnectionManager _openSearchConnectionManager;

}