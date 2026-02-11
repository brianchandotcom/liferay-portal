/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;

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
			_solrClientManager);
	}

	protected static BulkDocumentRequestExecutor
		createBulkDocumentRequestExecutor(SolrClientManager solrClientManager) {

		BulkDocumentRequestExecutorImpl bulkDocumentRequestExecutorImpl =
			new BulkDocumentRequestExecutorImpl() {
				{
					activate(_properties);
				}
			};

		ReflectionTestUtil.setFieldValue(
			bulkDocumentRequestExecutorImpl, "_solrClientManager",
			solrClientManager);

		return bulkDocumentRequestExecutorImpl;
	}

	protected static DeleteByQueryDocumentRequestExecutor
		createDeleteByQueryDocumentRequestExecutor(
			SolrClientManager solrClientManager) {

		DeleteByQueryDocumentRequestExecutorImpl
			deleteByQueryDocumentRequestExecutorImpl =
				new DeleteByQueryDocumentRequestExecutorImpl() {
					{
						activate(_properties);
					}
				};

		ReflectionTestUtil.setFieldValue(
			deleteByQueryDocumentRequestExecutorImpl, "_solrClientManager",
			solrClientManager);

		return deleteByQueryDocumentRequestExecutorImpl;
	}

	protected static DeleteDocumentRequestExecutor
		createDeleteDocumentRequestExecutor(
			SolrClientManager solrClientManager) {

		DeleteDocumentRequestExecutorImpl deleteDocumentRequestExecutorImpl =
			new DeleteDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			deleteDocumentRequestExecutorImpl, "_solrClientManager",
			solrClientManager);

		return deleteDocumentRequestExecutorImpl;
	}

	protected static DocumentRequestExecutor createDocumentRequestExecutor(
		SolrClientManager solrClientManager) {

		SolrDocumentRequestExecutor solrDocumentRequestExecutor =
			new SolrDocumentRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			solrDocumentRequestExecutor, "_bulkDocumentRequestExecutor",
			createBulkDocumentRequestExecutor(solrClientManager));
		ReflectionTestUtil.setFieldValue(
			solrDocumentRequestExecutor,
			"_deleteByQueryDocumentRequestExecutor",
			createDeleteByQueryDocumentRequestExecutor(solrClientManager));

		ReflectionTestUtil.setFieldValue(
			solrDocumentRequestExecutor, "_deleteDocumentRequestExecutor",
			createDeleteDocumentRequestExecutor(solrClientManager));
		ReflectionTestUtil.setFieldValue(
			solrDocumentRequestExecutor, "_getDocumentRequestExecutor",
			createGetDocumentRequestExecutor(solrClientManager));
		ReflectionTestUtil.setFieldValue(
			solrDocumentRequestExecutor, "_indexDocumentRequestExecutor",
			createIndexDocumentRequestExecutor(solrClientManager));

		ReflectionTestUtil.setFieldValue(
			solrDocumentRequestExecutor,
			"_updateByQueryDocumentRequestExecutor",
			createUpdateByQueryDocumentRequestExecutor());
		ReflectionTestUtil.setFieldValue(
			solrDocumentRequestExecutor, "_updateDocumentRequestExecutor",
			createUpdateDocumentRequestExecutor(solrClientManager));

		return solrDocumentRequestExecutor;
	}

	protected static GetDocumentRequestExecutor
		createGetDocumentRequestExecutor(SolrClientManager solrClientManager) {

		GetDocumentRequestExecutorImpl getDocumentRequestExecutorImpl =
			new GetDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			getDocumentRequestExecutorImpl, "_solrClientManager",
			solrClientManager);

		return getDocumentRequestExecutorImpl;
	}

	protected static IndexDocumentRequestExecutor
		createIndexDocumentRequestExecutor(
			SolrClientManager solrClientManager) {

		IndexDocumentRequestExecutorImpl indexDocumentRequestExecutorImpl =
			new IndexDocumentRequestExecutorImpl();

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
			SolrClientManager solrClientManager) {

		UpdateDocumentRequestExecutorImpl updateDocumentRequestExecutorImpl =
			new UpdateDocumentRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			updateDocumentRequestExecutorImpl, "_solrClientManager",
			solrClientManager);

		return updateDocumentRequestExecutorImpl;
	}

	protected void setProperties(Map<String, Object> properties) {
		_properties = properties;
	}

	protected void setSolrClientManager(SolrClientManager solrClientManager) {
		_solrClientManager = solrClientManager;
	}

	private static Map<String, Object> _properties;

	private DocumentRequestExecutor _documentRequestExecutor;
	private SolrClientManager _solrClientManager;

}