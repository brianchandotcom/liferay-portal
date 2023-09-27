/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter;

import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;
import com.liferay.portal.search.solr8.internal.document.SolrDocumentFactory;
import com.liferay.portal.search.solr8.internal.facet.FacetProcessor;
import com.liferay.portal.search.solr8.internal.search.engine.adapter.document.DocumentRequestExecutorFixture;
import com.liferay.portal.search.solr8.internal.search.engine.adapter.index.IndexRequestExecutorFixture;
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

	public void setQueryTranslator(QueryTranslator<String> queryTranslator) {
		_queryTranslator = queryTranslator;
	}

	public void setSolrClientManager(SolrClientManager solrClientManager) {
		_solrClientManager = solrClientManager;
	}

	public void setUp() {
		_searchEngineAdapter = createSearchEngineAdapter(
			_facetProcessor, _solrClientManager, _solrDocumentFactory,
			_queryTranslator, _properties);
	}

	protected SearchEngineAdapter createSearchEngineAdapter(
		FacetProcessor<SolrQuery> facetProcessor,
		SolrClientManager solrClientManager,
		SolrDocumentFactory solrDocumentFactory,
		QueryTranslator<String> queryTranslator,
		Map<String, Object> properties) {

		DocumentRequestExecutorFixture documentRequestExecutorFixture =
			new DocumentRequestExecutorFixture() {
				{
					setProperties(properties);
					setQueryTranslator(queryTranslator);
					setSolrClientManager(solrClientManager);
					setSolrDocumentFactory(solrDocumentFactory);
				}
			};

		IndexRequestExecutorFixture indexRequestExecutorFixture =
			new IndexRequestExecutorFixture() {
				{
					setSolrClientManager(solrClientManager);
				}
			};

		_searchRequestExecutorFixture = new SearchRequestExecutorFixture() {
			{
				setFacetProcessor(facetProcessor);
				setQueryTranslator(queryTranslator);
				setSolrClientManager(solrClientManager);
			}
		};

		documentRequestExecutorFixture.setUp();
		indexRequestExecutorFixture.setUp();
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
			indexRequestExecutorFixture.getIndexRequestExecutor());
		ReflectionTestUtil.setFieldValue(
			solrSearchEngineAdapterImpl, "_searchRequestExecutor",
			_searchRequestExecutorFixture.getSearchRequestExecutor());

		return solrSearchEngineAdapterImpl;
	}

	protected void setFacetProcessor(FacetProcessor<SolrQuery> facetProcessor) {
		_facetProcessor = facetProcessor;
	}

	protected void setSolrDocumentFactory(
		SolrDocumentFactory solrDocumentFactory) {

		_solrDocumentFactory = solrDocumentFactory;
	}

	private FacetProcessor<SolrQuery> _facetProcessor;
	private Map<String, Object> _properties;
	private QueryTranslator<String> _queryTranslator;
	private SearchEngineAdapter _searchEngineAdapter;
	private SearchRequestExecutorFixture _searchRequestExecutorFixture;
	private SolrClientManager _solrClientManager;
	private SolrDocumentFactory _solrDocumentFactory;

}