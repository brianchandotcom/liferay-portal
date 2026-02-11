/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;

/**
 * @author Dylan Rebelak
 */
public class DocumentRequestExecutorFixture {

	public DocumentRequestExecutor getDocumentRequestExecutor() {
		return _documentRequestExecutor;
	}

	public void setUp() {
		_documentRequestExecutor = _createDocumentRequestExecutor(
			_elasticsearchClientResolver);
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private BulkDocumentRequestExecutor _createBulkDocumentRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		BulkDocumentRequestExecutor bulkDocumentRequestExecutor =
			new BulkDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			bulkDocumentRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return bulkDocumentRequestExecutor;
	}

	private DeleteByQueryDocumentRequestExecutor
		_createDeleteByQueryDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		DeleteByQueryDocumentRequestExecutor
			deleteByQueryDocumentRequestExecutor =
				new DeleteByQueryDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			deleteByQueryDocumentRequestExecutor,
			"_elasticsearchClientResolver", elasticsearchClientResolver);

		return deleteByQueryDocumentRequestExecutor;
	}

	private DeleteDocumentRequestExecutor _createDeleteDocumentRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		DeleteDocumentRequestExecutor deleteDocumentRequestExecutor =
			new DeleteDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			deleteDocumentRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return deleteDocumentRequestExecutor;
	}

	private DocumentRequestExecutor _createDocumentRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		DocumentRequestExecutor documentRequestExecutor =
			new ElasticsearchDocumentRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			documentRequestExecutor, "_bulkDocumentRequestExecutor",
			_createBulkDocumentRequestExecutor(elasticsearchClientResolver));

		ReflectionTestUtil.setFieldValue(
			documentRequestExecutor, "_deleteByQueryDocumentRequestExecutor",
			_createDeleteByQueryDocumentRequestExecutor(
				elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			documentRequestExecutor, "_deleteDocumentRequestExecutor",
			_createDeleteDocumentRequestExecutor(elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			documentRequestExecutor, "_getDocumentRequestExecutor",
			_createGetDocumentRequestExecutor(elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			documentRequestExecutor, "_indexDocumentRequestExecutor",
			_createIndexDocumentRequestExecutor(elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			documentRequestExecutor, "_updateByQueryDocumentRequestExecutor",
			_createUpdateByQueryDocumentRequestExecutor(
				elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			documentRequestExecutor, "_updateDocumentRequestExecutor",
			_createUpdateDocumentRequestExecutor(elasticsearchClientResolver));

		return documentRequestExecutor;
	}

	private GetDocumentRequestExecutor _createGetDocumentRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		GetDocumentRequestExecutor getDocumentRequestExecutor =
			new GetDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			getDocumentRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return getDocumentRequestExecutor;
	}

	private IndexDocumentRequestExecutor _createIndexDocumentRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		IndexDocumentRequestExecutor indexDocumentRequestExecutor =
			new IndexDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			indexDocumentRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return indexDocumentRequestExecutor;
	}

	private UpdateByQueryDocumentRequestExecutor
		_createUpdateByQueryDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		UpdateByQueryDocumentRequestExecutor
			updateByQueryDocumentRequestExecutor =
				new UpdateByQueryDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			updateByQueryDocumentRequestExecutor,
			"_elasticsearchClientResolver", elasticsearchClientResolver);

		return updateByQueryDocumentRequestExecutor;
	}

	private UpdateDocumentRequestExecutor _createUpdateDocumentRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		UpdateDocumentRequestExecutor updateDocumentRequestExecutor =
			new UpdateDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			updateDocumentRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return updateDocumentRequestExecutor;
	}

	private DocumentRequestExecutor _documentRequestExecutor;
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}