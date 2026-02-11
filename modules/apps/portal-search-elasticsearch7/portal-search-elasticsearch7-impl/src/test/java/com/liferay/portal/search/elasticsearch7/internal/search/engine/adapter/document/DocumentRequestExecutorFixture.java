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

		elasticsearchDocumentRequestExecutor.activate(Collections.emptyMap());

		return elasticsearchDocumentRequestExecutor;
	}

	private DocumentRequestExecutor _documentRequestExecutor;
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}