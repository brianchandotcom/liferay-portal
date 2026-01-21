/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;
import com.liferay.portal.search.internal.document.DocumentBuilderFactoryImpl;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;
import com.liferay.portal.search.solr8.internal.document.SolrDocumentFactory;

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

	protected static SolrBulkableDocumentRequestTranslator
		createBulkableDocumentRequestTranslator(
			SolrDocumentFactory solrDocumentFactory) {

		SolrBulkableDocumentRequestTranslator
			solrBulkableDocumentRequestTranslator =
				new SolrBulkableDocumentRequestTranslator();

		ReflectionTestUtil.setFieldValue(
			solrBulkableDocumentRequestTranslator, "_solrDocumentFactory",
			solrDocumentFactory);

		return solrBulkableDocumentRequestTranslator;
	}

	protected static BulkDocumentRequestExecutor
		createBulkDocumentRequestExecutor(
			SolrClientManager solrClientManager,
			SolrDocumentFactory solrDocumentFactory) {

		BulkDocumentRequestExecutorImpl bulkDocumentRequestExecutorImpl =
			new BulkDocumentRequestExecutorImpl() {
				{
					activate(_properties);
				}
			};

		ReflectionTestUtil.setFieldValue(
			bulkDocumentRequestExecutorImpl, "_solrClientManager",
			solrClientManager);
		ReflectionTestUtil.setFieldValue(
			bulkDocumentRequestExecutorImpl, "_solrDocumentFactory",
			solrDocumentFactory);

		return bulkDocumentRequestExecutorImpl;
	}

	protected static DeleteByQueryDocumentRequestExecutor
		createDeleteByQueryDocumentRequestExecutor(
			QueryTranslator<String> queryTranslator,
			SolrClientManager solrClientManager) {

		DeleteByQueryDocumentRequestExecutorImpl
			deleteByQueryDocumentRequestExecutorImpl =
				new DeleteByQueryDocumentRequestExecutorImpl() {
					{
						activate(_properties);
					}
				};

		ReflectionTestUtil.setFieldValue(
			deleteByQueryDocumentRequestExecutorImpl, "_queryTranslator",
			queryTranslator);
		ReflectionTestUtil.setFieldValue(
			deleteByQueryDocumentRequestExecutorImpl, "_solrClientManager",
			solrClientManager);

		return deleteByQueryDocumentRequestExecutorImpl;
	}

	protected static DeleteDocumentRequestExecutor
		createDeleteDocumentRequestExecutor(
			SolrBulkableDocumentRequestTranslator
				solrBulkableDocumentRequestTranslator,
			SolrClientManager solrClientManager) {

		DeleteDocumentRequestExecutorImpl deleteDocumentRequestExecutorImpl =
			new DeleteDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			deleteDocumentRequestExecutorImpl,
			"_solrBulkableDocumentRequestTranslator",
			solrBulkableDocumentRequestTranslator);
		ReflectionTestUtil.setFieldValue(
			deleteDocumentRequestExecutorImpl, "_solrClientManager",
			solrClientManager);

		return deleteDocumentRequestExecutorImpl;
	}

	protected static DocumentRequestExecutor createDocumentRequestExecutor(
		QueryTranslator<String> queryTranslator,
		SolrClientManager solrClientManager,
		SolrDocumentFactory solrDocumentFactory) {

		SolrDocumentRequestExecutor solrDocumentRequestExecutor =
			new SolrDocumentRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			solrDocumentRequestExecutor, "_bulkDocumentRequestExecutor",
			createBulkDocumentRequestExecutor(
				solrClientManager, solrDocumentFactory));
		ReflectionTestUtil.setFieldValue(
			solrDocumentRequestExecutor,
			"_deleteByQueryDocumentRequestExecutor",
			createDeleteByQueryDocumentRequestExecutor(
				queryTranslator, solrClientManager));

		SolrBulkableDocumentRequestTranslator
			solrBulkableDocumentRequestTranslator =
				createBulkableDocumentRequestTranslator(solrDocumentFactory);

		ReflectionTestUtil.setFieldValue(
			solrDocumentRequestExecutor, "_deleteDocumentRequestExecutor",
			createDeleteDocumentRequestExecutor(
				solrBulkableDocumentRequestTranslator, solrClientManager));
		ReflectionTestUtil.setFieldValue(
			solrDocumentRequestExecutor, "_getDocumentRequestExecutor",
			createGetDocumentRequestExecutor(
				solrBulkableDocumentRequestTranslator, solrClientManager));
		ReflectionTestUtil.setFieldValue(
			solrDocumentRequestExecutor, "_indexDocumentRequestExecutor",
			createIndexDocumentRequestExecutor(
				solrBulkableDocumentRequestTranslator, solrClientManager));

		ReflectionTestUtil.setFieldValue(
			solrDocumentRequestExecutor,
			"_updateByQueryDocumentRequestExecutor",
			createUpdateByQueryDocumentRequestExecutor());
		ReflectionTestUtil.setFieldValue(
			solrDocumentRequestExecutor, "_updateDocumentRequestExecutor",
			createUpdateDocumentRequestExecutor(
				solrBulkableDocumentRequestTranslator, solrClientManager));

		return solrDocumentRequestExecutor;
	}

	protected static GetDocumentRequestExecutor
		createGetDocumentRequestExecutor(
			SolrBulkableDocumentRequestTranslator
				solrBulkableDocumentRequestTranslator,
			SolrClientManager solrClientManager) {

		GetDocumentRequestExecutorImpl getDocumentRequestExecutorImpl =
			new GetDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			getDocumentRequestExecutorImpl,
			"_solrBulkableDocumentRequestTranslator",
			solrBulkableDocumentRequestTranslator);
		ReflectionTestUtil.setFieldValue(
			getDocumentRequestExecutorImpl, "_documentBuilderFactory",
			new DocumentBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			getDocumentRequestExecutorImpl, "_solrClientManager",
			solrClientManager);

		return getDocumentRequestExecutorImpl;
	}

	protected static IndexDocumentRequestExecutor
		createIndexDocumentRequestExecutor(
			SolrBulkableDocumentRequestTranslator
				solrBulkableDocumentRequestTranslator,
			SolrClientManager solrClientManager) {

		IndexDocumentRequestExecutorImpl indexDocumentRequestExecutorImpl =
			new IndexDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			indexDocumentRequestExecutorImpl,
			"_solrBulkableDocumentRequestTranslator",
			solrBulkableDocumentRequestTranslator);
		ReflectionTestUtil.setFieldValue(
			indexDocumentRequestExecutorImpl, "_solrClientManager",
			solrClientManager);

		return indexDocumentRequestExecutorImpl;
	}

	protected static UpdateByQueryDocumentRequestExecutor
		createUpdateByQueryDocumentRequestExecutor() {

		return new UpdateByQueryDocumentRequestExecutorImpl();
	}

	protected static UpdateDocumentRequestExecutor
		createUpdateDocumentRequestExecutor(
			SolrBulkableDocumentRequestTranslator
				solrBulkableDocumentRequestTranslator,
			SolrClientManager solrClientManager) {

		UpdateDocumentRequestExecutorImpl updateDocumentRequestExecutorImpl =
			new UpdateDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			updateDocumentRequestExecutorImpl,
			"_solrBulkableDocumentRequestTranslator",
			solrBulkableDocumentRequestTranslator);
		ReflectionTestUtil.setFieldValue(
			updateDocumentRequestExecutorImpl, "_solrClientManager",
			solrClientManager);

		return updateDocumentRequestExecutorImpl;
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