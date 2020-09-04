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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.document.ElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch7.internal.query.ElasticsearchQueryTranslatorFixture;
import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequestTranslator;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;
import com.liferay.portal.search.internal.document.DocumentBuilderFactoryImpl;
import com.liferay.portal.search.internal.geolocation.GeoBuildersImpl;
import com.liferay.portal.search.internal.script.ScriptsImpl;
import com.liferay.portal.search.script.Scripts;

/**
 * @author Dylan Rebelak
 */
public class DocumentRequestExecutorFixture {

	public DocumentRequestExecutor getDocumentRequestExecutor() {
		return _documentRequestExecutor;
	}

	public void setUp() {
		_documentRequestExecutor = createDocumentRequestExecutor(
			_elasticsearchClientResolver, _elasticsearchDocumentFactory);
	}

	protected static BulkableDocumentRequestTranslator
		createBulkableDocumentRequestTranslator(
			ElasticsearchDocumentFactory elasticsearchDocumentFactory) {

		return new ElasticsearchBulkableDocumentRequestTranslator() {
			{
				setElasticsearchDocumentFactory(elasticsearchDocumentFactory);
			}
		};
	}

	protected static BulkDocumentRequestExecutor
		createBulkDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			BulkableDocumentRequestTranslator
				bulkableDocumentRequestTranslator) {

		return new BulkDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					bulkableDocumentRequestTranslator);
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static DeleteByQueryDocumentRequestExecutor
		createDeleteByQueryDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new DeleteByQueryDocumentRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);

				com.liferay.portal.search.elasticsearch7.internal.legacy.query.
					ElasticsearchQueryTranslatorFixture
						legacyElasticsearchQueryTranslatorFixture =
							new com.liferay.portal.search.elasticsearch7.
								internal.legacy.query.ElasticsearchQueryTranslatorFixture();

				setLegacyQueryTranslator(
					legacyElasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator());

				ElasticsearchQueryTranslatorFixture
					elasticsearchQueryTranslatorFixture =
						new ElasticsearchQueryTranslatorFixture();

				setQueryTranslator(
					elasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator());
			}
		};
	}

	protected static DeleteDocumentRequestExecutor
		createDeleteDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			BulkableDocumentRequestTranslator
				bulkableDocumentRequestTranslator) {

		return new DeleteDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					bulkableDocumentRequestTranslator);
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static DocumentRequestExecutor createDocumentRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver,
		ElasticsearchDocumentFactory elasticsearchDocumentFactory) {

		BulkableDocumentRequestTranslator bulkableDocumentRequestTranslator =
			createBulkableDocumentRequestTranslator(
				elasticsearchDocumentFactory);

		return new ElasticsearchDocumentRequestExecutor() {
			{
				setBulkDocumentRequestExecutor(
					createBulkDocumentRequestExecutor(
						elasticsearchClientResolver,
						bulkableDocumentRequestTranslator));
				setDeleteByQueryDocumentRequestExecutor(
					createDeleteByQueryDocumentRequestExecutor(
						elasticsearchClientResolver));
				setDeleteDocumentRequestExecutor(
					createDeleteDocumentRequestExecutor(
						elasticsearchClientResolver,
						bulkableDocumentRequestTranslator));
				setGetDocumentRequestExecutor(
					createGetDocumentRequestExecutor(
						elasticsearchClientResolver,
						bulkableDocumentRequestTranslator));
				setIndexDocumentRequestExecutor(
					createIndexDocumentRequestExecutor(
						elasticsearchClientResolver,
						bulkableDocumentRequestTranslator));
				setUpdateByQueryDocumentRequestExecutor(
					createUpdateByQueryDocumentRequestExecutor(
						elasticsearchClientResolver));
				setUpdateDocumentRequestExecutor(
					createUpdateDocumentRequestExecutor(
						elasticsearchClientResolver,
						bulkableDocumentRequestTranslator));
			}
		};
	}

	protected static GetDocumentRequestExecutor
		createGetDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			BulkableDocumentRequestTranslator
				bulkableDocumentRequestTranslator) {

		return new GetDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					bulkableDocumentRequestTranslator);
				setDocumentBuilderFactory(new DocumentBuilderFactoryImpl());
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setGeoBuilders(new GeoBuildersImpl());
			}
		};
	}

	protected static IndexDocumentRequestExecutor
		createIndexDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			BulkableDocumentRequestTranslator
				bulkableDocumentRequestTranslator) {

		return new IndexDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					bulkableDocumentRequestTranslator);
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static UpdateByQueryDocumentRequestExecutor
		createUpdateByQueryDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new UpdateByQueryDocumentRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);

				com.liferay.portal.search.elasticsearch7.internal.legacy.query.
					ElasticsearchQueryTranslatorFixture
						lecacyElasticsearchQueryTranslatorFixture =
							new com.liferay.portal.search.elasticsearch7.
								internal.legacy.query.ElasticsearchQueryTranslatorFixture();

				setLegacyQueryTranslator(
					lecacyElasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator());

				ElasticsearchQueryTranslatorFixture
					elasticsearchQueryTranslatorFixture =
						new ElasticsearchQueryTranslatorFixture();

				setQueryTranslator(
					elasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator());

				setScripts(_scripts);
			}
		};
	}

	protected static UpdateDocumentRequestExecutor
		createUpdateDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			BulkableDocumentRequestTranslator
				bulkableDocumentRequestTranslator) {

		return new UpdateDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					bulkableDocumentRequestTranslator);
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	protected void setElasticsearchDocumentFactory(
		ElasticsearchDocumentFactory elasticsearchDocumentFactory) {

		_elasticsearchDocumentFactory = elasticsearchDocumentFactory;
	}

	private static final Scripts _scripts = new ScriptsImpl();

	private DocumentRequestExecutor _documentRequestExecutor;
	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private ElasticsearchDocumentFactory _elasticsearchDocumentFactory;

}