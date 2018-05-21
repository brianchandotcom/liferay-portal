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

package com.liferay.portal.search.elasticsearch6.internal.adapter.document;

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;

/**
 * @author Dylan Rebelak
 */
public class DocumentRequestExecutorFixture {

	public DocumentRequestExecutorFixture(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
	}

	public ElasticsearchDocumentRequestExecutor createExecutor() {
		ElasticsearchDocumentRequestExecutor
			elasticsearchDocumentRequestExecutor =
				new ElasticsearchDocumentRequestExecutor();

		DeleteByQueryDocumentRequestExecutorImpl
			deleteByQueryDocumentRequestExecutorImpl =
				new DeleteByQueryDocumentRequestExecutorImpl();

		deleteByQueryDocumentRequestExecutorImpl.
			elasticsearchConnectionManager = _elasticsearchConnectionManager;

		elasticsearchDocumentRequestExecutor.
			deleteByQueryDocumentRequestExecutor =
				deleteByQueryDocumentRequestExecutorImpl;

		DeleteDocumentRequestExecutorImpl deleteDocumentRequestExecutorImpl =
			new DeleteDocumentRequestExecutorImpl();

		deleteDocumentRequestExecutorImpl.elasticsearchConnectionManager =
			_elasticsearchConnectionManager;

		elasticsearchDocumentRequestExecutor.deleteDocumentRequestExecutor =
			deleteDocumentRequestExecutorImpl;

		IndexDocumentRequestExecutorImpl indexDocumentRequestExecutorImpl =
			new IndexDocumentRequestExecutorImpl();

		indexDocumentRequestExecutorImpl.elasticsearchConnectionManager =
			_elasticsearchConnectionManager;

		elasticsearchDocumentRequestExecutor.indexDocumentRequestExecutor =
			indexDocumentRequestExecutorImpl;

		UpdateByQueryDocumentRequestExecutorImpl
			updateByQueryDocumentRequestExecutorImpl =
				new UpdateByQueryDocumentRequestExecutorImpl();

		updateByQueryDocumentRequestExecutorImpl.
			elasticsearchConnectionManager = _elasticsearchConnectionManager;

		elasticsearchDocumentRequestExecutor.
			updateByQueryDocumentRequestExecutor =
				updateByQueryDocumentRequestExecutorImpl;

		UpdateDocumentRequestExecutorImpl updateDocumentRequestExecutorImpl =
			new UpdateDocumentRequestExecutorImpl();

		updateDocumentRequestExecutorImpl.elasticsearchConnectionManager =
			_elasticsearchConnectionManager;

		elasticsearchDocumentRequestExecutor.updateDocumentRequestExecutor =
			updateDocumentRequestExecutorImpl;

		return elasticsearchDocumentRequestExecutor;
	}

	private final ElasticsearchConnectionManager
		_elasticsearchConnectionManager;

}