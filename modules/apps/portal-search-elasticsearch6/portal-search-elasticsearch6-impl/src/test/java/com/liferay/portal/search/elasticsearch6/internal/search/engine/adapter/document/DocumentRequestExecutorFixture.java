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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.document;

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;

/**
 * @author Dylan Rebelak
 */
public class DocumentRequestExecutorFixture {

	public DocumentRequestExecutorFixture(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
	}

	public DocumentRequestExecutor createExecutor() {
		return new ElasticsearchDocumentRequestExecutor() {
			{
				bulkDocumentRequestExecutor =
					createBulkDocumentRequestExecutor();
				deleteByQueryDocumentRequestExecutor =
					createDeleteByQueryDocumentRequestExecutor();
				deleteDocumentRequestExecutor =
					createDeleteDocumentRequestExecutor();
				indexDocumentRequestExecutor =
					createIndexDocumentRequestExecutor();
				updateByQueryDocumentRequestExecutor =
					createUpdateByQueryDocumentRequestExecutor();
				updateDocumentRequestExecutor =
					createUpdateDocumentRequestExecutor();
			}
		};
	}

	protected BulkDocumentRequestExecutor createBulkDocumentRequestExecutor() {
		return new BulkDocumentRequestExecutorImpl() {
			{
				bulkableDocumentRequestTranslator =
					createElasticsearchBulkableDocumentRequestTranslator();
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
			}
		};
	}

	protected DeleteByQueryDocumentRequestExecutor
		createDeleteByQueryDocumentRequestExecutor() {

		return new DeleteByQueryDocumentRequestExecutorImpl() {
			{
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
			}
		};
	}

	protected DeleteDocumentRequestExecutor
		createDeleteDocumentRequestExecutor() {

		return new DeleteDocumentRequestExecutorImpl() {
			{
				bulkableDocumentRequestTranslator =
					createElasticsearchBulkableDocumentRequestTranslator();
			}
		};
	}

	protected ElasticsearchBulkableDocumentRequestTranslator
		createElasticsearchBulkableDocumentRequestTranslator() {

		return new ElasticsearchBulkableDocumentRequestTranslator() {
			{
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
			}
		};
	}

	protected IndexDocumentRequestExecutor
		createIndexDocumentRequestExecutor() {

		return new IndexDocumentRequestExecutorImpl() {
			{
				bulkableDocumentRequestTranslator =
					createElasticsearchBulkableDocumentRequestTranslator();
			}
		};
	}

	protected UpdateByQueryDocumentRequestExecutor
		createUpdateByQueryDocumentRequestExecutor() {

		return new UpdateByQueryDocumentRequestExecutorImpl() {
			{
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
			}
		};
	}

	protected UpdateDocumentRequestExecutor
		createUpdateDocumentRequestExecutor() {

		return new UpdateDocumentRequestExecutorImpl() {
			{
				bulkableDocumentRequestTranslator =
					createElasticsearchBulkableDocumentRequestTranslator();
			}
		};
	}

	private final ElasticsearchConnectionManager
		_elasticsearchConnectionManager;

}