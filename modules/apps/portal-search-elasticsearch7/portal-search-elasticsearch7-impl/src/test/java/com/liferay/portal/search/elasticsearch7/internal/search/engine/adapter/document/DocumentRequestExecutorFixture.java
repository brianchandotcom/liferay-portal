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
		_documentRequestExecutor = _createDocumentRequestExecutor(
			_elasticsearchClientResolver, _elasticsearchDocumentFactory);
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	protected void setElasticsearchDocumentFactory(
		ElasticsearchDocumentFactory elasticsearchDocumentFactory) {

		_elasticsearchDocumentFactory = elasticsearchDocumentFactory;
	}

	private static ElasticsearchBulkableDocumentRequestTranslator
		_createBulkableDocumentRequestTranslator(
			ElasticsearchDocumentFactory elasticsearchDocumentFactory) {

		return new ElasticsearchBulkableDocumentRequestTranslatorImpl() {
			{
				setElasticsearchDocumentFactory(elasticsearchDocumentFactory);
			}
		};
	}

	private static BulkDocumentRequestExecutor
		_createBulkDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			ElasticsearchBulkableDocumentRequestTranslator
				elasticsearchBulkableDocumentRequestTranslator) {

		return new BulkDocumentRequestExecutorImpl() {
			{
				setElasticsearchBulkableDocumentRequestTranslator(
					elasticsearchBulkableDocumentRequestTranslator);
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	private static DeleteByQueryDocumentRequestExecutor
		_createDeleteByQueryDocumentRequestExecutor(
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

	private static DeleteDocumentRequestExecutor
		_createDeleteDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			ElasticsearchBulkableDocumentRequestTranslator
				elasticsearchBulkableDocumentRequestTranslator) {

		return new DeleteDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					elasticsearchBulkableDocumentRequestTranslator);
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	private static DocumentRequestExecutor _createDocumentRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver,
		ElasticsearchDocumentFactory elasticsearchDocumentFactory) {

		ElasticsearchBulkableDocumentRequestTranslator
			elasticsearchBulkableDocumentRequestTranslator =
				_createBulkableDocumentRequestTranslator(
					elasticsearchDocumentFactory);

		return new ElasticsearchDocumentRequestExecutor() {
			{
				setBulkDocumentRequestExecutor(
					_createBulkDocumentRequestExecutor(
						elasticsearchClientResolver,
						elasticsearchBulkableDocumentRequestTranslator));
				setDeleteByQueryDocumentRequestExecutor(
					_createDeleteByQueryDocumentRequestExecutor(
						elasticsearchClientResolver));
				setDeleteDocumentRequestExecutor(
					_createDeleteDocumentRequestExecutor(
						elasticsearchClientResolver,
						elasticsearchBulkableDocumentRequestTranslator));
				setGetDocumentRequestExecutor(
					_createGetDocumentRequestExecutor(
						elasticsearchClientResolver,
						elasticsearchBulkableDocumentRequestTranslator));
				setIndexDocumentRequestExecutor(
					_createIndexDocumentRequestExecutor(
						elasticsearchClientResolver,
						elasticsearchBulkableDocumentRequestTranslator));
				setUpdateByQueryDocumentRequestExecutor(
					_createUpdateByQueryDocumentRequestExecutor(
						elasticsearchClientResolver));
				setUpdateDocumentRequestExecutor(
					_createUpdateDocumentRequestExecutor(
						elasticsearchClientResolver,
						elasticsearchBulkableDocumentRequestTranslator));
			}
		};
	}

	private static GetDocumentRequestExecutor _createGetDocumentRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver,
		ElasticsearchBulkableDocumentRequestTranslator
			elasticsearchBulkableDocumentRequestTranslator) {

		return new GetDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					elasticsearchBulkableDocumentRequestTranslator);
				setDocumentBuilderFactory(new DocumentBuilderFactoryImpl());
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setGeoBuilders(new GeoBuildersImpl());
			}
		};
	}

	private static IndexDocumentRequestExecutor
		_createIndexDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			ElasticsearchBulkableDocumentRequestTranslator
				elasticsearchBulkableDocumentRequestTranslator) {

		return new IndexDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					elasticsearchBulkableDocumentRequestTranslator);
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	private static UpdateByQueryDocumentRequestExecutor
		_createUpdateByQueryDocumentRequestExecutor(
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

	private static UpdateDocumentRequestExecutor
		_createUpdateDocumentRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			ElasticsearchBulkableDocumentRequestTranslator
				elasticsearchBulkableDocumentRequestTranslator) {

		return new UpdateDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					elasticsearchBulkableDocumentRequestTranslator);
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	private static final Scripts _scripts = new ScriptsImpl();

	private DocumentRequestExecutor _documentRequestExecutor;
	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private ElasticsearchDocumentFactory _elasticsearchDocumentFactory;

}