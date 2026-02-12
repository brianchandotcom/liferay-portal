/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.internal.hits.SearchHitBuilderFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitsBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.document.DocumentBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.stats.StatsResultsTranslatorImpl;
import com.liferay.portal.search.solr8.internal.connection.SolrClientManager;
import com.liferay.portal.search.solr8.internal.search.response.DefaultSearchSearchResponseAssemblerHelperImpl;
import com.liferay.portal.search.solr8.internal.search.response.SearchSearchResponseAssemblerHelper;
import com.liferay.portal.search.solr8.internal.sort.SolrSortFieldTranslator;

/**
 * @author Bryan Engler
 */
public class SearchRequestExecutorFixture {

	public SearchRequestExecutor getSearchRequestExecutor() {
		return _searchRequestExecutor;
	}

	public void setUp() {
		_searchRequestExecutor = createSearchRequestExecutor(
			_solrClientManager);
	}

	protected SearchRequestExecutor createSearchRequestExecutor(
		SolrClientManager solrClientManager) {

		SolrSearchRequestExecutor solrSearchRequestExecutor =
			new SolrSearchRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			solrSearchRequestExecutor, "_solrClientManager", solrClientManager);

		ReflectionTestUtil.setFieldValue(
			solrSearchRequestExecutor, "_multisearchSearchRequestExecutor",
			new MultisearchSearchRequestExecutorImpl());
		ReflectionTestUtil.setFieldValue(
			solrSearchRequestExecutor, "_searchSearchRequestExecutor",
			createSearchSearchRequestExecutor(solrClientManager));

		solrSearchRequestExecutor.activate();

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
			searchSolrQueryAssemblerImpl, "_sortFieldTranslator",
			new SolrSortFieldTranslator());

		return searchSolrQueryAssemblerImpl;
	}

	protected void setSolrClientManager(SolrClientManager solrClientManager) {
		_solrClientManager = solrClientManager;
	}

	private final BaseSolrQueryAssembler _baseSolrQueryAssemblerImpl =
		new BaseSolrQueryAssembler();
	private SearchRequestExecutor _searchRequestExecutor;
	private SolrClientManager _solrClientManager;

}