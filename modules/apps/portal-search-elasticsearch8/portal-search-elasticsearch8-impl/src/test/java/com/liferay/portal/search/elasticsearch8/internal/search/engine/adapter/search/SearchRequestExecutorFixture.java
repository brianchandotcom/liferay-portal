/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.facet.FacetTranslator;
import com.liferay.portal.search.elasticsearch8.internal.search.response.SearchResponseTranslator;
import com.liferay.portal.search.elasticsearch8.internal.suggest.ElasticsearchSuggesterTranslator;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.filter.ComplexQueryBuilderFactory;
import com.liferay.portal.search.internal.aggregation.AggregationResultsImpl;
import com.liferay.portal.search.internal.filter.ComplexQueryBuilderFactoryImpl;
import com.liferay.portal.search.internal.groupby.GroupByResponseFactoryImpl;
import com.liferay.portal.search.internal.highlight.HighlightFieldBuilderFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitBuilderFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitsBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.groupby.GroupByRequestFactoryImpl;
import com.liferay.portal.search.internal.legacy.stats.StatsRequestBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.stats.StatsResultsTranslatorImpl;
import com.liferay.portal.search.internal.query.QueriesImpl;
import com.liferay.portal.search.legacy.stats.StatsRequestBuilderFactory;
import com.liferay.portal.search.query.Queries;

/**
 * @author Michael C. Han
 */
public class SearchRequestExecutorFixture {

	public SearchRequestExecutor getSearchRequestExecutor() {
		return _searchRequestExecutor;
	}

	public void setUp() {
		_searchRequestExecutor = _createSearchRequestExecutor(
			createComplexQueryBuilderFactory(new QueriesImpl()),
			_elasticsearchClientResolver, new StatsRequestBuilderFactoryImpl());
	}

	protected static CommonSearchRequestBuilderAssembler
		createCommonSearchRequestBuilderAssembler(
			ComplexQueryBuilderFactory complexQueryBuilderFactory) {

		CommonSearchRequestBuilderAssembler
			commonSearchRequestBuilderAssembler =
				new CommonSearchRequestBuilderAssemblerImpl();

		ReflectionTestUtil.setFieldValue(
			commonSearchRequestBuilderAssembler, "_complexQueryBuilderFactory",
			complexQueryBuilderFactory);
		ReflectionTestUtil.setFieldValue(
			commonSearchRequestBuilderAssembler, "_facetTranslator",
			new FacetTranslator());

		return commonSearchRequestBuilderAssembler;
	}

	protected static ComplexQueryBuilderFactory
		createComplexQueryBuilderFactory(Queries queries) {

		ComplexQueryBuilderFactoryImpl complexQueryBuilderFactoryImpl =
			new ComplexQueryBuilderFactoryImpl();

		ReflectionTestUtil.setFieldValue(
			complexQueryBuilderFactoryImpl, "_queries", queries);

		return complexQueryBuilderFactoryImpl;
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private CountSearchRequestExecutor _createCountSearchRequestExecutor(
		CommonSearchRequestBuilderAssembler commonSearchRequestBuilderAssembler,
		ElasticsearchClientResolver elasticsearchClientResolver) {

		CountSearchRequestExecutor countSearchRequestExecutor =
			new CountSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			countSearchRequestExecutor, "_commonSearchRequestBuilderAssembler",
			commonSearchRequestBuilderAssembler);
		ReflectionTestUtil.setFieldValue(
			countSearchRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return countSearchRequestExecutor;
	}

	private MultisearchSearchRequestExecutor
		_createMultisearchSearchRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			SearchSearchRequestAssembler searchSearchRequestAssembler,
			SearchSearchResponseAssembler searchSearchResponseAssembler) {

		MultisearchSearchRequestExecutor multisearchSearchRequestExecutor =
			new MultisearchSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			multisearchSearchRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);
		ReflectionTestUtil.setFieldValue(
			multisearchSearchRequestExecutor, "_searchSearchRequestAssembler",
			searchSearchRequestAssembler);
		ReflectionTestUtil.setFieldValue(
			multisearchSearchRequestExecutor, "_searchSearchResponseAssembler",
			searchSearchResponseAssembler);

		return multisearchSearchRequestExecutor;
	}

	private SearchRequestExecutor _createSearchRequestExecutor(
		ComplexQueryBuilderFactory complexQueryBuilderFactory,
		ElasticsearchClientResolver elasticsearchClientResolver,
		StatsRequestBuilderFactory statsRequestBuilderFactory) {

		ElasticsearchSearchRequestExecutor elasticsearchSearchRequestExecutor =
			new ElasticsearchSearchRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		CommonSearchRequestBuilderAssembler
			commonSearchRequestBuilderAssembler =
				createCommonSearchRequestBuilderAssembler(
					complexQueryBuilderFactory);

		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchRequestExecutor, "_countSearchRequestExecutor",
			_createCountSearchRequestExecutor(
				commonSearchRequestBuilderAssembler,
				elasticsearchClientResolver));

		SearchSearchRequestAssembler searchSearchRequestAssembler =
			_createSearchSearchRequestAssembler(
				commonSearchRequestBuilderAssembler,
				statsRequestBuilderFactory);

		SearchSearchResponseAssembler searchSearchResponseAssembler =
			_createSearchSearchResponseAssembler(statsRequestBuilderFactory);

		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchRequestExecutor,
			"_multisearchSearchRequestExecutor",
			_createMultisearchSearchRequestExecutor(
				elasticsearchClientResolver, searchSearchRequestAssembler,
				searchSearchResponseAssembler));

		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchRequestExecutor, "_searchSearchRequestExecutor",
			_createSearchSearchRequestExecutor(
				elasticsearchClientResolver, searchSearchRequestAssembler,
				searchSearchResponseAssembler));

		ReflectionTestUtil.setFieldValue(
			elasticsearchSearchRequestExecutor, "_suggestSearchRequestExecutor",
			_createSuggestSearchRequestExecutor(elasticsearchClientResolver));

		elasticsearchSearchRequestExecutor.activate();

		return elasticsearchSearchRequestExecutor;
	}

	private SearchSearchRequestAssembler _createSearchSearchRequestAssembler(
		CommonSearchRequestBuilderAssembler commonSearchRequestBuilderAssembler,
		StatsRequestBuilderFactory statsRequestBuilderFactory) {

		SearchSearchRequestAssembler searchSearchRequestAssembler =
			new SearchSearchRequestAssemblerImpl();

		ReflectionTestUtil.setFieldValue(
			searchSearchRequestAssembler,
			"_commonSearchRequestBuilderAssembler",
			commonSearchRequestBuilderAssembler);
		ReflectionTestUtil.setFieldValue(
			searchSearchRequestAssembler, "_groupByRequestFactory",
			new GroupByRequestFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			searchSearchRequestAssembler, "_statsRequestBuilderFactory",
			statsRequestBuilderFactory);

		return searchSearchRequestAssembler;
	}

	private SearchSearchRequestExecutor _createSearchSearchRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver,
		SearchSearchRequestAssembler searchSearchRequestAssembler,
		SearchSearchResponseAssembler searchSearchResponseAssembler) {

		SearchSearchRequestExecutor searchSearchRequestExecutor =
			new SearchSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			searchSearchRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);
		ReflectionTestUtil.setFieldValue(
			searchSearchRequestExecutor, "_searchSearchRequestAssembler",
			searchSearchRequestAssembler);
		ReflectionTestUtil.setFieldValue(
			searchSearchRequestExecutor, "_searchSearchResponseAssembler",
			searchSearchResponseAssembler);

		return searchSearchRequestExecutor;
	}

	private SearchSearchResponseAssembler _createSearchSearchResponseAssembler(
		StatsRequestBuilderFactory statsRequestBuilderFactory) {

		SearchSearchResponseAssembler searchSearchResponseAssembler =
			new SearchSearchResponseAssemblerImpl();

		ReflectionTestUtil.setFieldValue(
			searchSearchResponseAssembler, "_aggregationResults",
			new AggregationResultsImpl());
		ReflectionTestUtil.setFieldValue(
			searchSearchResponseAssembler, "_highlightFieldBuilderFactory",
			new HighlightFieldBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			searchSearchResponseAssembler, "_searchHitBuilderFactory",
			new SearchHitBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			searchSearchResponseAssembler, "_searchHitsBuilderFactory",
			new SearchHitsBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			searchSearchResponseAssembler, "_searchResponseTranslator",
			new SearchResponseTranslator(
				new GroupByResponseFactoryImpl(), statsRequestBuilderFactory,
				new StatsResultsTranslatorImpl()));

		return searchSearchResponseAssembler;
	}

	private SuggestSearchRequestExecutor _createSuggestSearchRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		SuggestSearchRequestExecutor suggestSearchRequestExecutor =
			new SuggestSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			suggestSearchRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);
		ReflectionTestUtil.setFieldValue(
			suggestSearchRequestExecutor, "_suggesterTranslator",
			_elasticsearchSuggesterTranslator);

		return suggestSearchRequestExecutor;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private final ElasticsearchSuggesterTranslator
		_elasticsearchSuggesterTranslator =
			new ElasticsearchSuggesterTranslator();
	private SearchRequestExecutor _searchRequestExecutor;

}