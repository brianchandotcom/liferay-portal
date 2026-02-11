/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;

import java.util.Collections;

/**
 * @author Dylan Rebelak
 */
public class DocumentRequestExecutorFixture {

	public DocumentRequestExecutor getDocumentRequestExecutor() {
		return _documentRequestExecutor;
	}

	public void setUp() {
		_documentRequestExecutor = _createDocumentRequestExecutor(
			_openSearchConnectionManager);
	}

	protected void setOpenSearchConnectionManager(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_openSearchConnectionManager = openSearchConnectionManager;
	}

	private DocumentRequestExecutor _createDocumentRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		OpenSearchDocumentRequestExecutor openSearchDocumentRequestExecutor =
			new OpenSearchDocumentRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			openSearchDocumentRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);
		ReflectionTestUtil.setFieldValue(
			openSearchDocumentRequestExecutor, "_getDocumentRequestExecutor",
			_createGetDocumentRequestExecutor(openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			openSearchDocumentRequestExecutor,
			"_updateByQueryDocumentRequestExecutor",
			_createUpdateByQueryDocumentRequestExecutor(
				openSearchConnectionManager));

		openSearchDocumentRequestExecutor.activate(Collections.emptyMap());

		return openSearchDocumentRequestExecutor;
	}

	private GetDocumentRequestExecutor _createGetDocumentRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		GetDocumentRequestExecutor getDocumentRequestExecutor =
			new GetDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			getDocumentRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return getDocumentRequestExecutor;
	}

	private UpdateByQueryDocumentRequestExecutor
		_createUpdateByQueryDocumentRequestExecutor(
			OpenSearchConnectionManager openSearchConnectionManager) {

		UpdateByQueryDocumentRequestExecutor
			updateByQueryDocumentRequestExecutor =
				new UpdateByQueryDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			updateByQueryDocumentRequestExecutor,
			"_openSearchConnectionManager", openSearchConnectionManager);

		return updateByQueryDocumentRequestExecutor;
	}

	private DocumentRequestExecutor _documentRequestExecutor;
	private OpenSearchConnectionManager _openSearchConnectionManager;

}