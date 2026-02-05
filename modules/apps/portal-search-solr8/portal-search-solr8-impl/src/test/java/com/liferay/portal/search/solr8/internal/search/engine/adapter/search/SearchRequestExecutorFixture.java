/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.internal.groupby.GroupByResponseFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitBuilderFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitsBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.document.DocumentBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.groupby.GroupByRequestFactoryImpl;
import com.liferay.portal.search.internal.legacy.stats.StatsRequestBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.stats.StatsResultsTranslatorImpl;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;
import com.liferay.portal.search.solr8.internal.facet.FacetProcessor;
import com.liferay.portal.search.solr8.internal.search.response.DefaultSearchSearchResponseAssemblerHelperImpl;
import com.liferay.portal.search.solr8.internal.search.response.SearchSearchResponseAssemblerHelper;
import com.liferay.portal.search.solr8.internal.sort.SolrSortFieldTranslator;

import org.apache.solr.client.solrj.SolrQuery;

/**
 * @author Bryan Engler
 */
public class SearchRequestExecutorFixture {

	public SearchRequestExecutor getSearchRequestExecutor() {
		return _searchRequestExecutor;
	}

	public void setUp() {
		createBaseSolrQueryAssembler(_facetProcessor);

		_searchRequestExecutor = createSearchRequestExecutor(
			_solrClientManager);
	}

	protected void createBaseSolrQueryAssembler(
		FacetProcessor<SolrQuery> facetProcessor) {

		_baseSolrQueryAssemblerImpl = new BaseSolrQueryAssemblerImpl();

		if (facetProcessor != null) {
			ReflectionTestUtil.setFieldValue(
				_baseSolrQueryAssemblerImpl, "_defaultFacetProcessor",
				facetProcessor);
		}

		_baseSolrQueryAssemblerImpl.activate(
			SystemBundleUtil.getBundleContext());
	}

	protected CountSearchRequestExecutor createCountSearchRequestExecutor(
		SolrClientManager solrClientManager) {

		CountSearchRequestExecutorImpl countSearchRequestExecutorImpl =
			new CountSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			countSearchRequestExecutorImpl, "_baseSearchResponseAssembler",
			new BaseSearchResponseAssemblerImpl());
		ReflectionTestUtil.setFieldValue(
			countSearchRequestExecutorImpl, "_baseSolrQueryAssembler",
			_baseSolrQueryAssemblerImpl);
		ReflectionTestUtil.setFieldValue(
			countSearchRequestExecutorImpl, "_solrClientManager",
			solrClientManager);

		return countSearchRequestExecutorImpl;
	}

	protected SearchRequestExecutor createSearchRequestExecutor(
		SolrClientManager solrClientManager) {

		SolrSearchRequestExecutor solrSearchRequestExecutor =
			new SolrSearchRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			solrSearchRequestExecutor, "_countSearchRequestExecutor",
			createCountSearchRequestExecutor(solrClientManager));
		ReflectionTestUtil.setFieldValue(
			solrSearchRequestExecutor, "_multisearchSearchRequestExecutor",
			new MultisearchSearchRequestExecutorImpl());
		ReflectionTestUtil.setFieldValue(
			solrSearchRequestExecutor, "_searchSearchRequestExecutor",
			createSearchSearchRequestExecutor(solrClientManager));

		return solrSearchRequestExecutor;
	}

	protected SearchSearchRequestExecutor createSearchSearchRequestExecutor(
		SolrClientManager solrClientManager) {

		SearchSearchRequestExecutorImpl searchSearchRequestExecutorImpl =
			new SearchSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			searchSearchRequestExecutorImpl, "_searchSearchResponseAssembler",
			createSearchSearchResponseAssembler());
		ReflectionTestUtil.setFieldValue(
			searchSearchRequestExecutorImpl, "_searchSolrQueryAssembler",
			createSearchSolrQueryAssembler());
		ReflectionTestUtil.setFieldValue(
			searchSearchRequestExecutorImpl, "_solrClientManager",
			solrClientManager);

		return searchSearchRequestExecutorImpl;
	}

	protected SearchSearchResponseAssembler
		createSearchSearchResponseAssembler() {

		SearchSearchResponseAssemblerImpl searchSearchResponseAssemblerImpl =
			new SearchSearchResponseAssemblerImpl();

		ReflectionTestUtil.setFieldValue(
			searchSearchResponseAssemblerImpl, "_baseSearchResponseAssembler",
			new BaseSearchResponseAssemblerImpl());
		ReflectionTestUtil.setFieldValue(
			searchSearchResponseAssemblerImpl,
			"_searchSearchResponseAssemblerHelper",
			createSearchSearchResponseAssemblerHelper());

		return searchSearchResponseAssemblerImpl;
	}

	protected SearchSearchResponseAssemblerHelper
		createSearchSearchResponseAssemblerHelper() {

		DefaultSearchSearchResponseAssemblerHelperImpl
			defaultSearchSearchResponseAssemblerHelperImpl =
				new DefaultSearchSearchResponseAssemblerHelperImpl();

		ReflectionTestUtil.setFieldValue(
			defaultSearchSearchResponseAssemblerHelperImpl,
			"_documentBuilderFactory", new DocumentBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			defaultSearchSearchResponseAssemblerHelperImpl,
			"_groupByResponseFactory", new GroupByResponseFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			defaultSearchSearchResponseAssemblerHelperImpl,
			"_searchHitBuilderFactory", new SearchHitBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			defaultSearchSearchResponseAssemblerHelperImpl,
			"_searchHitsBuilderFactory", new SearchHitsBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			defaultSearchSearchResponseAssemblerHelperImpl,
			"_statsResultsTranslator", new StatsResultsTranslatorImpl());

		return defaultSearchSearchResponseAssemblerHelperImpl;
	}

	protected SearchSolrQueryAssembler createSearchSolrQueryAssembler() {
		SearchSolrQueryAssemblerImpl searchSolrQueryAssemblerImpl =
			new SearchSolrQueryAssemblerImpl();

		ReflectionTestUtil.setFieldValue(
			searchSolrQueryAssemblerImpl, "_baseSolrQueryAssembler",
			_baseSolrQueryAssemblerImpl);
		ReflectionTestUtil.setFieldValue(
			searchSolrQueryAssemblerImpl, "_groupByRequestFactory",
			new GroupByRequestFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			searchSolrQueryAssemblerImpl, "_sortFieldTranslator",
			new SolrSortFieldTranslator());
		ReflectionTestUtil.setFieldValue(
			searchSolrQueryAssemblerImpl, "_statsRequestBuilderFactory",
			new StatsRequestBuilderFactoryImpl());

		return searchSolrQueryAssemblerImpl;
	}

	protected void setFacetProcessor(FacetProcessor<SolrQuery> facetProcessor) {
		_facetProcessor = facetProcessor;
	}

	protected void setSolrClientManager(SolrClientManager solrClientManager) {
		_solrClientManager = solrClientManager;
	}

	private BaseSolrQueryAssemblerImpl _baseSolrQueryAssemblerImpl;
	private FacetProcessor<SolrQuery> _facetProcessor;
	private SearchRequestExecutor _searchRequestExecutor;
	private SolrClientManager _solrClientManager;

}