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

package com.liferay.portal.search.solr7.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequestTranslator;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;
import com.liferay.portal.search.internal.document.DocumentBuilderFactoryImpl;
import com.liferay.portal.search.solr7.internal.connection.SolrClientManager;
import com.liferay.portal.search.solr7.internal.document.SolrDocumentFactory;

import java.util.Map;

/**
 * @author Bryan Engler
 */
public class DocumentRequestExecutorFixture {

	public DocumentRequestExecutor getDocumentRequestExecutor() {
		return _documentRequestExecutor;
	}

	public void setUp() {
		_documentRequestExecutor = createDocumentRequestExecutor(
			_queryTranslator, _solrClientManager, _solrDocumentFactory);
	}

	protected static BulkableDocumentRequestTranslator
		createBulkableDocumentRequestTranslator(
			SolrDocumentFactory solrDocumentFactory) {

		return new SolrBulkableDocumentRequestTranslator() {
			{
				setSolrDocumentFactory(solrDocumentFactory);
			}
		};
	}

	protected static BulkDocumentRequestExecutor
		createBulkDocumentRequestExecutor(
			SolrClientManager solrClientManager,
			SolrDocumentFactory solrDocumentFactory) {

		return new BulkDocumentRequestExecutorImpl() {
			{
				setSolrClientManager(solrClientManager);
				setSolrDocumentFactory(solrDocumentFactory);

				activate(_properties);
			}
		};
	}

	protected static DeleteByQueryDocumentRequestExecutor
		createDeleteByQueryDocumentRequestExecutor(
			QueryTranslator<String> queryTranslator,
			SolrClientManager solrClientManager) {

		return new DeleteByQueryDocumentRequestExecutorImpl() {
			{
				setQueryTranslator(queryTranslator);
				setSolrClientManager(solrClientManager);

				activate(_properties);
			}
		};
	}

	protected static DeleteDocumentRequestExecutor
		createDeleteDocumentRequestExecutor(
			BulkableDocumentRequestTranslator bulkableDocumentRequestTranslator,
			SolrClientManager solrClientManager) {

		return new DeleteDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					bulkableDocumentRequestTranslator);
				setSolrClientManager(solrClientManager);
			}
		};
	}

	protected static DocumentRequestExecutor createDocumentRequestExecutor(
		QueryTranslator<String> queryTranslator,
		SolrClientManager solrClientManager,
		SolrDocumentFactory solrDocumentFactory) {

		BulkableDocumentRequestTranslator bulkableDocumentRequestTranslator =
			createBulkableDocumentRequestTranslator(solrDocumentFactory);

		return new SolrDocumentRequestExecutor() {
			{
				setBulkDocumentRequestExecutor(
					createBulkDocumentRequestExecutor(
						solrClientManager, solrDocumentFactory));
				setDeleteByQueryDocumentRequestExecutor(
					createDeleteByQueryDocumentRequestExecutor(
						queryTranslator, solrClientManager));
				setDeleteDocumentRequestExecutor(
					createDeleteDocumentRequestExecutor(
						bulkableDocumentRequestTranslator, solrClientManager));
				setGetDocumentRequestExecutor(
					createGetDocumentRequestExecutor(
						bulkableDocumentRequestTranslator, solrClientManager));
				setIndexDocumentRequestExecutor(
					createIndexDocumentRequestExecutor(
						bulkableDocumentRequestTranslator, solrClientManager));
				setUpdateByQueryDocumentRequestExecutor(
					createUpdateByQueryDocumentRequestExecutor());
				setUpdateDocumentRequestExecutor(
					createUpdateDocumentRequestExecutor(
						bulkableDocumentRequestTranslator, solrClientManager));
			}
		};
	}

	protected static GetDocumentRequestExecutor
		createGetDocumentRequestExecutor(
			BulkableDocumentRequestTranslator bulkableDocumentRequestTranslator,
			SolrClientManager solrClientManager) {

		return new GetDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					bulkableDocumentRequestTranslator);
				setDocumentBuilderFactory(new DocumentBuilderFactoryImpl());
				setSolrClientManager(solrClientManager);
			}
		};
	}

	protected static IndexDocumentRequestExecutor
		createIndexDocumentRequestExecutor(
			BulkableDocumentRequestTranslator bulkableDocumentRequestTranslator,
			SolrClientManager solrClientManager) {

		return new IndexDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					bulkableDocumentRequestTranslator);
				setSolrClientManager(solrClientManager);
			}
		};
	}

	protected static UpdateByQueryDocumentRequestExecutor
		createUpdateByQueryDocumentRequestExecutor() {

		return new UpdateByQueryDocumentRequestExecutorImpl();
	}

	protected static UpdateDocumentRequestExecutor
		createUpdateDocumentRequestExecutor(
			BulkableDocumentRequestTranslator bulkableDocumentRequestTranslator,
			SolrClientManager solrClientManager) {

		return new UpdateDocumentRequestExecutorImpl() {
			{
				setBulkableDocumentRequestTranslator(
					bulkableDocumentRequestTranslator);
				setSolrClientManager(solrClientManager);
			}
		};
	}

	protected void setProperties(Map<String, Object> properties) {
		_properties = properties;
	}

	protected void setQueryTranslator(QueryTranslator<String> queryTranslator) {
		_queryTranslator = queryTranslator;
	}

	protected void setSolrClientManager(SolrClientManager solrClientManager) {
		_solrClientManager = solrClientManager;
	}

	protected void setSolrDocumentFactory(
		SolrDocumentFactory solrDocumentFactory) {

		_solrDocumentFactory = solrDocumentFactory;
	}

	private static Map<String, Object> _properties;

	private DocumentRequestExecutor _documentRequestExecutor;
	private QueryTranslator<String> _queryTranslator;
	private SolrClientManager _solrClientManager;
	private SolrDocumentFactory _solrDocumentFactory;

}