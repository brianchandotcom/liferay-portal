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

	protected static DocumentRequestExecutor createDocumentRequestExecutor(
		SolrClientManager solrClientManager) {

		SolrDocumentRequestExecutor solrDocumentRequestExecutor =
			new SolrDocumentRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			solrDocumentRequestExecutor, "_solrClientManager",
			solrClientManager);

		solrDocumentRequestExecutor.activate(_properties);

		return solrDocumentRequestExecutor;
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