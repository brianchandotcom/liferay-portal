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

package com.liferay.portal.search.solr7.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.internal.groupby.GroupByResponseFactoryImpl;
import com.liferay.portal.search.internal.legacy.groupby.GroupByRequestFactoryImpl;
import com.liferay.portal.search.internal.legacy.stats.StatsRequestBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.stats.StatsResultsTranslatorImpl;
import com.liferay.portal.search.internal.stats.StatsResponseBuilderFactoryImpl;
import com.liferay.portal.search.solr7.internal.connection.SolrClientManager;
import com.liferay.portal.search.solr7.internal.facet.FacetProcessor;
import com.liferay.portal.search.solr7.internal.filter.BooleanFilterTranslatorImpl;
import com.liferay.portal.search.solr7.internal.filter.DateRangeFilterTranslatorImpl;
import com.liferay.portal.search.solr7.internal.filter.DateRangeTermFilterTranslatorImpl;
import com.liferay.portal.search.solr7.internal.filter.ExistsFilterTranslatorImpl;
import com.liferay.portal.search.solr7.internal.filter.GeoBoundingBoxFilterTranslatorImpl;
import com.liferay.portal.search.solr7.internal.filter.GeoDistanceFilterTranslatorImpl;
import com.liferay.portal.search.solr7.internal.filter.GeoDistanceRangeFilterTranslatorImpl;
import com.liferay.portal.search.solr7.internal.filter.GeoPolygonFilterTranslatorImpl;
import com.liferay.portal.search.solr7.internal.filter.MissingFilterTranslatorImpl;
import com.liferay.portal.search.solr7.internal.filter.PrefixFilterTranslatorImpl;
import com.liferay.portal.search.solr7.internal.filter.QueryFilterTranslatorImpl;
import com.liferay.portal.search.solr7.internal.filter.RangeTermFilterTranslatorImpl;
import com.liferay.portal.search.solr7.internal.filter.SolrFilterTranslator;
import com.liferay.portal.search.solr7.internal.filter.TermFilterTranslatorImpl;
import com.liferay.portal.search.solr7.internal.filter.TermsFilterTranslatorImpl;
import com.liferay.portal.search.solr7.internal.groupby.DefaultGroupByTranslator;
import com.liferay.portal.search.solr7.internal.search.response.DefaultSearchSearchResponseAssemblerHelperImpl;
import com.liferay.portal.search.solr7.internal.search.response.SearchSearchResponseAssemblerHelper;
import com.liferay.portal.search.solr7.internal.sort.SolrSortFieldTranslator;
import com.liferay.portal.search.solr7.internal.stats.DefaultStatsTranslator;
import com.liferay.portal.search.solr7.internal.stats.StatsTranslator;

import org.apache.solr.client.solrj.SolrQuery;

/**
 * @author Bryan Engler
 */
public class SearchRequestExecutorFixture {

	public SearchRequestExecutor getSearchRequestExecutor() {
		return _searchRequestExecutor;
	}

	public void setUp() {
		_searchRequestExecutor = createSearchRequestExecutor(
			_solrClientManager, _facetProcessor, _queryTranslator);
	}

	protected static BaseSearchResponseAssembler
		createBaseSearchResponseAssembler() {

		return new BaseSearchResponseAssemblerImpl() {
			{
				setStatsTranslator(createStatsTranslator());
			}
		};
	}

	protected static BaseSolrQueryAssembler createBaseSolrQueryAssembler(
		FacetProcessor<SolrQuery> facetProcessor,
		QueryTranslator<String> queryTranslator) {

		return new BaseSolrQueryAssemblerImpl() {
			{
				setQueryTranslator(queryTranslator);
				setStatsTranslator(createStatsTranslator());
				setFilterTranslator(createSolrFilterTranslator());
				setFacetProcessor(facetProcessor);
			}
		};
	}

	protected static CountSearchRequestExecutor
		createCountSearchRequestExecutor(
			SolrClientManager solrClientManager,
			FacetProcessor<SolrQuery> facetProcessor,
			QueryTranslator<String> queryTranslator) {

		return new CountSearchRequestExecutorImpl() {
			{
				setBaseSearchResponseAssembler(
					createBaseSearchResponseAssembler());
				setBaseSolrQueryAssembler(
					createBaseSolrQueryAssembler(
						facetProcessor, queryTranslator));
				setSolrClientManager(solrClientManager);
			}
		};
	}

	protected static SearchRequestExecutor createSearchRequestExecutor(
		SolrClientManager solrClientManager,
		FacetProcessor<SolrQuery> facetProcessor,
		QueryTranslator<String> queryTranslator) {

		return new SolrSearchRequestExecutor() {
			{
				setCountSearchRequestExecutor(
					createCountSearchRequestExecutor(
						solrClientManager, facetProcessor, queryTranslator));
				setMultisearchSearchRequestExecutor(
					new MultisearchSearchRequestExecutorImpl());
				setSearchSearchRequestExecutor(
					createSearchSearchRequestExecutor(
						solrClientManager, facetProcessor, queryTranslator));
			}
		};
	}

	protected static SearchSearchRequestExecutor
		createSearchSearchRequestExecutor(
			SolrClientManager solrClientManager,
			FacetProcessor<SolrQuery> facetProcessor,
			QueryTranslator<String> queryTranslator) {

		return new SearchSearchRequestExecutorImpl() {
			{
				setSearchSearchResponseAssembler(
					createSearchSearchResponseAssembler());
				setSearchSolrQueryAssembler(
					createSearchSolrQueryAssembler(
						facetProcessor, queryTranslator));
				setSolrClientManager(solrClientManager);
			}
		};
	}

	protected static SearchSearchResponseAssembler
		createSearchSearchResponseAssembler() {

		return new SearchSearchResponseAssemblerImpl() {
			{
				setBaseSearchResponseAssembler(
					createBaseSearchResponseAssembler());
				setSearchSearchResponseAssemblerHelper(
					createSearchSearchResponseAssemblerHelper());
			}
		};
	}

	protected static SearchSearchResponseAssemblerHelper
		createSearchSearchResponseAssemblerHelper() {

		return new DefaultSearchSearchResponseAssemblerHelperImpl() {
			{
				setGroupByResponseFactory(new GroupByResponseFactoryImpl());
				setStatsTranslator(createStatsTranslator());
				setStatsResultsTranslator(new StatsResultsTranslatorImpl());
			}
		};
	}

	protected static SearchSolrQueryAssembler createSearchSolrQueryAssembler(
		FacetProcessor<SolrQuery> facetProcessor,
		QueryTranslator<String> queryTranslator) {

		return new SearchSolrQueryAssemblerImpl() {
			{
				setBaseSolrQueryAssembler(
					createBaseSolrQueryAssembler(
						facetProcessor, queryTranslator));
				setGroupByRequestFactory(new GroupByRequestFactoryImpl());
				setGroupByTranslator(new DefaultGroupByTranslator());
				setSortFieldTranslator(new SolrSortFieldTranslator());
				setStatsRequestBuilderFactory(
					new StatsRequestBuilderFactoryImpl());
				setStatsTranslator(createStatsTranslator());
			}
		};
	}

	protected static SolrFilterTranslator createSolrFilterTranslator() {
		return new SolrFilterTranslator() {
			{
				dateRangeFilterTranslator = new DateRangeFilterTranslatorImpl();

				setBooleanQueryTranslator(new BooleanFilterTranslatorImpl());
				setDateRangeTermFilterTranslator(
					new DateRangeTermFilterTranslatorImpl());
				setExistsFilterTranslator(new ExistsFilterTranslatorImpl());
				setGeoBoundingBoxFilterTranslator(
					new GeoBoundingBoxFilterTranslatorImpl());
				setGeoDistanceFilterTranslator(
					new GeoDistanceFilterTranslatorImpl());
				setGeoDistanceRangeFilterTranslator(
					new GeoDistanceRangeFilterTranslatorImpl());
				setGeoPolygonFilterTranslator(
					new GeoPolygonFilterTranslatorImpl());
				setMissingFilterTranslator(new MissingFilterTranslatorImpl());
				setPrefixFilterTranslator(new PrefixFilterTranslatorImpl());
				setQueryFilterTranslator(new QueryFilterTranslatorImpl());
				setRangeTermFilterTranslator(
					new RangeTermFilterTranslatorImpl());
				setTermFilterTranslator(new TermFilterTranslatorImpl());
				setTermsFilterTranslator(new TermsFilterTranslatorImpl());
			}
		};
	}

	protected static StatsTranslator createStatsTranslator() {
		return new DefaultStatsTranslator() {
			{
				setStatsResponseBuilderFactory(
					new StatsResponseBuilderFactoryImpl());
			}
		};
	}

	protected void setFacetProcessor(FacetProcessor<SolrQuery> facetProcessor) {
		_facetProcessor = facetProcessor;
	}

	protected void setQueryTranslator(QueryTranslator<String> queryTranslator) {
		_queryTranslator = queryTranslator;
	}

	protected void setSolrClientManager(SolrClientManager solrClientManager) {
		_solrClientManager = solrClientManager;
	}

	private FacetProcessor<SolrQuery> _facetProcessor;
	private QueryTranslator<String> _queryTranslator;
	private SearchRequestExecutor _searchRequestExecutor;
	private SolrClientManager _solrClientManager;

}