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
import com.liferay.portal.search.elasticsearch6.internal.document.ElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch6.internal.query.ElasticsearchQueryTranslatorFixture;
import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequestTranslator;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;

/**
 * @author Dylan Rebelak
 */
public class DocumentRequestExecutorFixture {

	public DocumentRequestExecutor getDocumentRequestExecutor() {
		return _documentRequestExecutor;
	}

	public void setUp() {
		_documentRequestExecutor = createDocumentRequestExecutor(
			elasticsearchClientResolver, elasticsearchDocumentFactory);
	}

	protected static BulkableDocumentRequestTranslator
		createBulkableDocumentRequestTranslator(
			ElasticsearchClientResolver elasticsearchClientResolver1,
			ElasticsearchDocumentFactory elasticsearchDocumentFactory1) {

		return new ElasticsearchBulkableDocumentRequestTranslator() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
				elasticsearchDocumentFactory = elasticsearchDocumentFactory1;
			}
		};
	}

	protected static BulkDocumentRequestExecutor
		createBulkDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1,
			BulkableDocumentRequestTranslator
				bulkableDocumentRequestTranslator1) {

		return new BulkDocumentRequestExecutorImpl() {
			{
				bulkableDocumentRequestTranslator =
					bulkableDocumentRequestTranslator1;
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static DeleteByQueryDocumentRequestExecutor
		createDeleteByQueryDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new DeleteByQueryDocumentRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;

				ElasticsearchQueryTranslatorFixture
					elasticsearchQueryTranslatorFixture =
						new ElasticsearchQueryTranslatorFixture();

				queryTranslator =
					elasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator();
			}
		};
	}

	protected static DeleteDocumentRequestExecutor
		createDeleteDocumentRequestExecutor(
			BulkableDocumentRequestTranslator
				bulkableDocumentRequestTranslator1) {

		return new DeleteDocumentRequestExecutorImpl() {
			{
				bulkableDocumentRequestTranslator =
					bulkableDocumentRequestTranslator1;
			}
		};
	}

	protected static DocumentRequestExecutor createDocumentRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver1,
		ElasticsearchDocumentFactory elasticsearchDocumentFactory1) {

		BulkableDocumentRequestTranslator bulkableDocumentRequestTranslator1 =
			createBulkableDocumentRequestTranslator(
				elasticsearchClientResolver1, elasticsearchDocumentFactory1);

		return new ElasticsearchDocumentRequestExecutor() {
			{
				bulkDocumentRequestExecutor = createBulkDocumentRequestExecutor(
					elasticsearchClientResolver1,
					bulkableDocumentRequestTranslator1);
				deleteByQueryDocumentRequestExecutor =
					createDeleteByQueryDocumentRequestExecutor(
						elasticsearchClientResolver1);
				deleteDocumentRequestExecutor =
					createDeleteDocumentRequestExecutor(
						bulkableDocumentRequestTranslator1);
				indexDocumentRequestExecutor =
					createIndexDocumentRequestExecutor(
						bulkableDocumentRequestTranslator1);
				updateByQueryDocumentRequestExecutor =
					createUpdateByQueryDocumentRequestExecutor(
						elasticsearchClientResolver1);
				updateDocumentRequestExecutor =
					createUpdateDocumentRequestExecutor(
						bulkableDocumentRequestTranslator1);
			}
		};
	}

	protected static IndexDocumentRequestExecutor
		createIndexDocumentRequestExecutor(
			BulkableDocumentRequestTranslator
				bulkableDocumentRequestTranslator1) {

		return new IndexDocumentRequestExecutorImpl() {
			{
				bulkableDocumentRequestTranslator =
					bulkableDocumentRequestTranslator1;
			}
		};
	}

	protected static UpdateByQueryDocumentRequestExecutor
		createUpdateByQueryDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new UpdateByQueryDocumentRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;

				ElasticsearchQueryTranslatorFixture
					elasticsearchQueryTranslatorFixture =
						new ElasticsearchQueryTranslatorFixture();

				queryTranslator =
					elasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator();
			}
		};
	}

	protected static UpdateDocumentRequestExecutor
		createUpdateDocumentRequestExecutor(
			BulkableDocumentRequestTranslator
				bulkableDocumentRequestTranslator1) {

		return new UpdateDocumentRequestExecutorImpl() {
			{
				bulkableDocumentRequestTranslator =
					bulkableDocumentRequestTranslator1;
			}
		};
	}

	protected ElasticsearchClientResolver elasticsearchClientResolver;
	protected ElasticsearchDocumentFactory elasticsearchDocumentFactory;

	private DocumentRequestExecutor _documentRequestExecutor;

}