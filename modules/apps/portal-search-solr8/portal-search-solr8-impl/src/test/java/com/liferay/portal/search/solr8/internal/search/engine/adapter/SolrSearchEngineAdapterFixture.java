/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;
import com.liferay.portal.search.solr8.internal.facet.FacetProcessor;
import com.liferay.portal.search.solr8.internal.search.engine.adapter.document.DocumentRequestExecutorFixture;
import com.liferay.portal.search.solr8.internal.search.engine.adapter.index.IndexRequestExecutorTestUtil;
import com.liferay.portal.search.solr8.internal.search.engine.adapter.search.SearchRequestExecutorFixture;

import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;

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
			_facetProcessor, _solrClientManager, _properties);
	}

	protected SearchEngineAdapter createSearchEngineAdapter(
		FacetProcessor<SolrQuery> facetProcessor,
		SolrClientManager solrClientManager, Map<String, Object> properties) {

		DocumentRequestExecutorFixture documentRequestExecutorFixture =
			new DocumentRequestExecutorFixture() {
				{
					setProperties(properties);
					setSolrClientManager(solrClientManager);
				}
			};

		_searchRequestExecutorFixture = new SearchRequestExecutorFixture() {
			{
				setFacetProcessor(facetProcessor);
				setSolrClientManager(solrClientManager);
			}
		};

		documentRequestExecutorFixture.setUp();
		_searchRequestExecutorFixture.setUp();

		SolrSearchEngineAdapterImpl solrSearchEngineAdapterImpl =
			new SolrSearchEngineAdapterImpl() {
				{
					setThrowOriginalExceptions(true);
				}
			};

		ReflectionTestUtil.setFieldValue(
			solrSearchEngineAdapterImpl, "_documentRequestExecutor",
			documentRequestExecutorFixture.getDocumentRequestExecutor());
		ReflectionTestUtil.setFieldValue(
			solrSearchEngineAdapterImpl, "_indexRequestExecutor",
			IndexRequestExecutorTestUtil.createIndexRequestExecutor(
				solrClientManager));
		ReflectionTestUtil.setFieldValue(
			solrSearchEngineAdapterImpl, "_searchRequestExecutor",
			_searchRequestExecutorFixture.getSearchRequestExecutor());

		return solrSearchEngineAdapterImpl;
	}

	protected void setFacetProcessor(FacetProcessor<SolrQuery> facetProcessor) {
		_facetProcessor = facetProcessor;
	}

	private FacetProcessor<SolrQuery> _facetProcessor;
	private Map<String, Object> _properties;
	private SearchEngineAdapter _searchEngineAdapter;
	private SearchRequestExecutorFixture _searchRequestExecutorFixture;
	private SolrClientManager _solrClientManager;

}