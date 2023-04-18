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

		SearchRequestExecutorFixture searchRequestExecutorFixture =
			new SearchRequestExecutorFixture() {
				{
					setFacetProcessor(facetProcessor);
					setQueryTranslator(queryTranslator);
					setSolrClientManager(solrClientManager);
				}
			};

		documentRequestExecutorFixture.setUp();
		indexRequestExecutorFixture.setUp();
		searchRequestExecutorFixture.setUp();

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
			searchRequestExecutorFixture.getSearchRequestExecutor());

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
	private SolrClientManager _solrClientManager;
	private SolrDocumentFactory _solrDocumentFactory;

}