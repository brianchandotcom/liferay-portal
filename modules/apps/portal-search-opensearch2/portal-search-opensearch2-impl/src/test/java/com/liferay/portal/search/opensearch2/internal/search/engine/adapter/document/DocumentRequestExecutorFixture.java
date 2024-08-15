/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;
import com.liferay.portal.search.internal.document.DocumentBuilderFactoryImpl;
import com.liferay.portal.search.internal.geolocation.GeoBuildersImpl;
import com.liferay.portal.search.internal.script.ScriptsImpl;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.document.OpenSearchDocumentFactory;
import com.liferay.portal.search.opensearch2.internal.document.OpenSearchDocumentFactoryImpl;
import com.liferay.portal.search.opensearch2.internal.query.OpenSearchQueryTranslatorFixture;
import com.liferay.portal.search.opensearch2.internal.script.ScriptTranslator;

/**
 * @author Dylan Rebelak
 */
public class DocumentRequestExecutorFixture {

	public DocumentRequestExecutor getDocumentRequestExecutor() {
		return _documentRequestExecutor;
	}

	public void setUp() {
		_documentRequestExecutor = _createDocumentRequestExecutor(
			_openSearchConnectionManager, _openSearchDocumentFactory);
	}

	protected void setOpenSearchConnectionManager(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_openSearchConnectionManager = openSearchConnectionManager;
	}

	protected void setOpenSearchDocumentFactory(
		OpenSearchDocumentFactory openSearchDocumentFactory) {

		_openSearchDocumentFactory = openSearchDocumentFactory;
	}

	private OpenSearchBulkableDocumentRequestTranslator
		_createBulkableDocumentRequestTranslator(
			OpenSearchDocumentFactory openSearchDocumentFactory) {

		OpenSearchBulkableDocumentRequestTranslator
			openSearchBulkableDocumentRequestTranslator =
				new OpenSearchBulkableDocumentRequestTranslatorImpl();

		ReflectionTestUtil.setFieldValue(
			openSearchBulkableDocumentRequestTranslator,
			"openSearchDocumentFactory", openSearchDocumentFactory);
		ReflectionTestUtil.setFieldValue(
			openSearchBulkableDocumentRequestTranslator, "_scriptTranslator",
			new ScriptTranslator());

		return openSearchBulkableDocumentRequestTranslator;
	}

	private BulkDocumentRequestExecutor _createBulkDocumentRequestExecutor(
		OpenSearchBulkableDocumentRequestTranslator
			openSearchBulkableDocumentRequestTranslator,
		OpenSearchConnectionManager openSearchConnectionManager) {

		BulkDocumentRequestExecutor bulkDocumentRequestExecutor =
			new BulkDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			bulkDocumentRequestExecutor,
			"_openSearchBulkableDocumentRequestTranslator",
			openSearchBulkableDocumentRequestTranslator);
		ReflectionTestUtil.setFieldValue(
			bulkDocumentRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return bulkDocumentRequestExecutor;
	}

	private DeleteByQueryDocumentRequestExecutor
		_createDeleteByQueryDocumentRequestExecutor(
			OpenSearchConnectionManager openSearchConnectionManager) {

		DeleteByQueryDocumentRequestExecutor
			deleteByQueryDocumentRequestExecutor =
				new DeleteByQueryDocumentRequestExecutorImpl();

		com.liferay.portal.search.opensearch2.internal.legacy.query.
			OpenSearchQueryTranslatorFixture
				legacyOpenSearchQueryTranslatorFixture =
					new com.liferay.portal.search.opensearch2.internal.legacy.
						query.OpenSearchQueryTranslatorFixture();

		ReflectionTestUtil.setFieldValue(
			deleteByQueryDocumentRequestExecutor, "_legacyQueryTranslator",
			legacyOpenSearchQueryTranslatorFixture.
				getOpenSearchQueryTranslator());

		ReflectionTestUtil.setFieldValue(
			deleteByQueryDocumentRequestExecutor,
			"_openSearchConnectionManager", openSearchConnectionManager);

		OpenSearchQueryTranslatorFixture openSearchQueryTranslatorFixture =
			new OpenSearchQueryTranslatorFixture();

		ReflectionTestUtil.setFieldValue(
			deleteByQueryDocumentRequestExecutor, "_queryTranslator",
			openSearchQueryTranslatorFixture.getOpenSearchQueryTranslator());

		return deleteByQueryDocumentRequestExecutor;
	}

	private DeleteDocumentRequestExecutor _createDeleteDocumentRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager,
		OpenSearchDocumentRequestTranslator
			openSearchDocumentRequestTranslator) {

		DeleteDocumentRequestExecutor deleteDocumentRequestExecutor =
			new DeleteDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			deleteDocumentRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);
		ReflectionTestUtil.setFieldValue(
			deleteDocumentRequestExecutor,
			"_openSearchDocumentRequestTranslator",
			openSearchDocumentRequestTranslator);

		return deleteDocumentRequestExecutor;
	}

	private DocumentRequestExecutor _createDocumentRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager,
		OpenSearchDocumentFactory openSearchDocumentFactory) {

		DocumentRequestExecutor documentRequestExecutor =
			new OpenSearchDocumentRequestExecutor();

		OpenSearchBulkableDocumentRequestTranslator
			openSearchBulkableDocumentRequestTranslator =
				_createBulkableDocumentRequestTranslator(
					openSearchDocumentFactory);

		ReflectionTestUtil.setFieldValue(
			documentRequestExecutor, "_bulkDocumentRequestExecutor",
			_createBulkDocumentRequestExecutor(
				openSearchBulkableDocumentRequestTranslator,
				openSearchConnectionManager));

		ReflectionTestUtil.setFieldValue(
			documentRequestExecutor, "_deleteByQueryDocumentRequestExecutor",
			_createDeleteByQueryDocumentRequestExecutor(
				openSearchConnectionManager));

		OpenSearchDocumentRequestTranslator
			openSearchDocumentRequestTranslator =
				_createOpenSearchDocumentRequestTranslator();

		ReflectionTestUtil.setFieldValue(
			documentRequestExecutor, "_deleteDocumentRequestExecutor",
			_createDeleteDocumentRequestExecutor(
				openSearchConnectionManager,
				openSearchDocumentRequestTranslator));
		ReflectionTestUtil.setFieldValue(
			documentRequestExecutor, "_getDocumentRequestExecutor",
			_createGetDocumentRequestExecutor(
				openSearchConnectionManager,
				openSearchDocumentRequestTranslator));
		ReflectionTestUtil.setFieldValue(
			documentRequestExecutor, "_indexDocumentRequestExecutor",
			_createIndexDocumentRequestExecutor(
				openSearchConnectionManager,
				openSearchDocumentRequestTranslator));

		ReflectionTestUtil.setFieldValue(
			documentRequestExecutor, "_updateByQueryDocumentRequestExecutor",
			_createUpdateByQueryDocumentRequestExecutor(
				openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			documentRequestExecutor, "_updateDocumentRequestExecutor",
			_createUpdateDocumentRequestExecutor(
				openSearchConnectionManager,
				openSearchDocumentRequestTranslator));

		return documentRequestExecutor;
	}

	private GetDocumentRequestExecutor _createGetDocumentRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager,
		OpenSearchDocumentRequestTranslator
			openSearchDocumentRequestTranslator) {

		GetDocumentRequestExecutor getDocumentRequestExecutor =
			new GetDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			getDocumentRequestExecutor, "_documentBuilderFactory",
			new DocumentBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			getDocumentRequestExecutor, "_geoBuilders", new GeoBuildersImpl());
		ReflectionTestUtil.setFieldValue(
			getDocumentRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);
		ReflectionTestUtil.setFieldValue(
			getDocumentRequestExecutor, "_openSearchDocumentRequestTranslator",
			openSearchDocumentRequestTranslator);

		return getDocumentRequestExecutor;
	}

	private IndexDocumentRequestExecutor _createIndexDocumentRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager,
		OpenSearchDocumentRequestTranslator
			openSearchDocumentRequestTranslator) {

		IndexDocumentRequestExecutor indexDocumentRequestExecutor =
			new IndexDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			indexDocumentRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);
		ReflectionTestUtil.setFieldValue(
			indexDocumentRequestExecutor,
			"_openSearchDocumentRequestTranslator",
			openSearchDocumentRequestTranslator);

		return indexDocumentRequestExecutor;
	}

	private OpenSearchDocumentRequestTranslator
		_createOpenSearchDocumentRequestTranslator() {

		OpenSearchDocumentRequestTranslator
			openSearchDocumentRequestTranslator =
				new OpenSearchDocumentRequestTranslatorImpl();

		ReflectionTestUtil.setFieldValue(
			openSearchDocumentRequestTranslator, "openSearchDocumentFactory",
			new OpenSearchDocumentFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			openSearchDocumentRequestTranslator, "_scriptTranslator",
			new ScriptTranslator());

		return openSearchDocumentRequestTranslator;
	}

	private UpdateByQueryDocumentRequestExecutor
		_createUpdateByQueryDocumentRequestExecutor(
			OpenSearchConnectionManager openSearchConnectionManager) {

		UpdateByQueryDocumentRequestExecutor
			updateByQueryDocumentRequestExecutor =
				new UpdateByQueryDocumentRequestExecutorImpl();

		com.liferay.portal.search.opensearch2.internal.legacy.query.
			OpenSearchQueryTranslatorFixture
				lecacyOpenSearchQueryTranslatorFixture =
					new com.liferay.portal.search.opensearch2.internal.legacy.
						query.OpenSearchQueryTranslatorFixture();

		ReflectionTestUtil.setFieldValue(
			updateByQueryDocumentRequestExecutor, "_legacyQueryTranslator",
			lecacyOpenSearchQueryTranslatorFixture.
				getOpenSearchQueryTranslator());

		ReflectionTestUtil.setFieldValue(
			updateByQueryDocumentRequestExecutor,
			"_openSearchConnectionManager", openSearchConnectionManager);

		OpenSearchQueryTranslatorFixture openSearchQueryTranslatorFixture =
			new OpenSearchQueryTranslatorFixture();

		ReflectionTestUtil.setFieldValue(
			updateByQueryDocumentRequestExecutor, "_queryTranslator",
			openSearchQueryTranslatorFixture.getOpenSearchQueryTranslator());

		ReflectionTestUtil.setFieldValue(
			updateByQueryDocumentRequestExecutor, "_scripts",
			new ScriptsImpl());

		return updateByQueryDocumentRequestExecutor;
	}

	private UpdateDocumentRequestExecutor _createUpdateDocumentRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager,
		OpenSearchDocumentRequestTranslator
			openSearchDocumentRequestTranslator) {

		UpdateDocumentRequestExecutor updateDocumentRequestExecutor =
			new UpdateDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			updateDocumentRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);
		ReflectionTestUtil.setFieldValue(
			updateDocumentRequestExecutor,
			"_openSearchDocumentRequestTranslator",
			openSearchDocumentRequestTranslator);

		return updateDocumentRequestExecutor;
	}

	private DocumentRequestExecutor _documentRequestExecutor;
	private OpenSearchConnectionManager _openSearchConnectionManager;
	private OpenSearchDocumentFactory _openSearchDocumentFactory;

}