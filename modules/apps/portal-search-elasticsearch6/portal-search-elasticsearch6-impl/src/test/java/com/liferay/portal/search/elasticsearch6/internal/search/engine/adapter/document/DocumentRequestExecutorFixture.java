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

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch6.internal.query.ElasticsearchQueryTranslatorFixture;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;

/**
 * @author Dylan Rebelak
 */
public class DocumentRequestExecutorFixture {

	public DocumentRequestExecutorFixture(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
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
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected DeleteByQueryDocumentRequestExecutor
		createDeleteByQueryDocumentRequestExecutor() {

		return new DeleteByQueryDocumentRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;

				ElasticsearchQueryTranslatorFixture
					elasticsearchQueryTranslatorFixture =
						new ElasticsearchQueryTranslatorFixture();

				queryTranslator =
					elasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator();
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
				elasticsearchClientResolver = _elasticsearchClientResolver;
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
				elasticsearchClientResolver = _elasticsearchClientResolver;

				ElasticsearchQueryTranslatorFixture
					elasticsearchQueryTranslatorFixture =
						new ElasticsearchQueryTranslatorFixture();

				queryTranslator =
					elasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator();
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

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}