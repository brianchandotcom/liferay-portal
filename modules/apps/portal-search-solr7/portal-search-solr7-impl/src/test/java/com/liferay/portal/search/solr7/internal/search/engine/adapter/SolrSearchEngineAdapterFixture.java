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

package com.liferay.portal.search.solr7.internal.search.engine.adapter;

import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.solr7.internal.connection.SolrClientManager;
import com.liferay.portal.search.solr7.internal.document.SolrDocumentFactory;
import com.liferay.portal.search.solr7.internal.facet.FacetProcessor;
import com.liferay.portal.search.solr7.internal.search.engine.adapter.document.DocumentRequestExecutorFixture;
import com.liferay.portal.search.solr7.internal.search.engine.adapter.index.IndexRequestExecutorFixture;
import com.liferay.portal.search.solr7.internal.search.engine.adapter.search.SearchRequestExecutorFixture;

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

	protected static SearchEngineAdapter createSearchEngineAdapter(
		FacetProcessor<SolrQuery> facetProcessor,
		SolrClientManager solrClientManager,
		SolrDocumentFactory solrDocumentFactory,
		QueryTranslator<String> queryTranslator,
		Map<String, Object> properties) {

		DocumentRequestExecutorFixture documentRequestExecutorFixture =
			new DocumentRequestExecutorFixture() {
				{
					setSolrClientManager(solrClientManager);
					setSolrDocumentFactory(solrDocumentFactory);
					setQueryTranslator(queryTranslator);
					setProperties(properties);
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
					setSolrClientManager(solrClientManager);
					setQueryTranslator(queryTranslator);
				}
			};

		documentRequestExecutorFixture.setUp();
		indexRequestExecutorFixture.setUp();
		searchRequestExecutorFixture.setUp();

		return new SolrSearchEngineAdapterImpl() {
			{
				setDocumentRequestExecutor(
					documentRequestExecutorFixture.
						getDocumentRequestExecutor());
				setSearchRequestExecutor(
					searchRequestExecutorFixture.getSearchRequestExecutor());
				setIndexRequestExecutor(
					indexRequestExecutorFixture.getIndexRequestExecutor());
				setThrowOriginalExceptions(true);
			}
		};
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