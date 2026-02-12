/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;
import com.liferay.portal.search.solr8.internal.search.engine.adapter.document.DocumentRequestExecutorFixture;

import java.util.Map;

/**
 * @author Bryan Engler
 */
public class SolrSearchEngineAdapterFixture {

	public SearchEngineAdapter getSearchEngineAdapter() {
		return _searchEngineAdapter;
	}

	public void setProperties(Map<String, Object> properties) {
		_properties = properties;
	}

	public void setSolrClientManager(SolrClientManager solrClientManager) {
		_solrClientManager = solrClientManager;
	}

	public void setUp() {
		_searchEngineAdapter = createSearchEngineAdapter(
			_solrClientManager, _properties);
	}

	protected SearchEngineAdapter createSearchEngineAdapter(
		SolrClientManager solrClientManager, Map<String, Object> properties) {

		DocumentRequestExecutorFixture documentRequestExecutorFixture =
			new DocumentRequestExecutorFixture() {
				{
					setProperties(properties);
					setSolrClientManager(solrClientManager);
				}
			};

		documentRequestExecutorFixture.setUp();

		SolrSearchEngineAdapterImpl solrSearchEngineAdapterImpl =
			new SolrSearchEngineAdapterImpl() {
				{
					setThrowOriginalExceptions(true);
				}
			};

		ReflectionTestUtil.setFieldValue(
			solrSearchEngineAdapterImpl, "_solrClientManager",
			solrClientManager);

		ReflectionTestUtil.setFieldValue(
			solrSearchEngineAdapterImpl, "_documentRequestExecutor",
			documentRequestExecutorFixture.getDocumentRequestExecutor());

		solrSearchEngineAdapterImpl.activate();

		return solrSearchEngineAdapterImpl;
	}

	private Map<String, Object> _properties;
	private SearchEngineAdapter _searchEngineAdapter;
	private SolrClientManager _solrClientManager;

}