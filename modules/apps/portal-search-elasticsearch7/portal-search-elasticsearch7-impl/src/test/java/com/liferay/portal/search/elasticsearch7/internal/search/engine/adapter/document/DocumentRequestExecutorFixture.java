/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;

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
			_elasticsearchClientResolver);
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private DocumentRequestExecutor _createDocumentRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		ElasticsearchDocumentRequestExecutor
			elasticsearchDocumentRequestExecutor =
				new ElasticsearchDocumentRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			elasticsearchDocumentRequestExecutor,
			"_elasticsearchClientResolver", elasticsearchClientResolver);

		ReflectionTestUtil.setFieldValue(
			elasticsearchDocumentRequestExecutor, "_getDocumentRequestExecutor",
			_createGetDocumentRequestExecutor(elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			elasticsearchDocumentRequestExecutor,
			"_updateByQueryDocumentRequestExecutor",
			_createUpdateByQueryDocumentRequestExecutor(
				elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			elasticsearchDocumentRequestExecutor,
			"_updateDocumentRequestExecutor",
			_createUpdateDocumentRequestExecutor(elasticsearchClientResolver));

		elasticsearchDocumentRequestExecutor.activate(Collections.emptyMap());

		return elasticsearchDocumentRequestExecutor;
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