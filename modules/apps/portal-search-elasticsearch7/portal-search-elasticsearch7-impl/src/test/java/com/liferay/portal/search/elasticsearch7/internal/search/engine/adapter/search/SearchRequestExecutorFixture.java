/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.ElasticsearchAggregationTranslatorFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.facet.FacetTranslator;
import com.liferay.portal.search.elasticsearch7.internal.search.response.SearchResponseTranslator;
import com.liferay.portal.search.elasticsearch7.internal.suggest.ElasticsearchSuggesterTranslator;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.filter.ComplexQueryBuilderFactory;
import com.liferay.portal.search.internal.aggregation.AggregationResultsImpl;
import com.liferay.portal.search.internal.document.DocumentBuilderFactoryImpl;
import com.liferay.portal.search.internal.filter.ComplexQueryBuilderFactoryImpl;
import com.liferay.portal.search.internal.geolocation.GeoBuildersImpl;
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

	protected static CommonSearchSourceBuilderAssembler
		createCommonSearchSourceBuilderAssembler(
			ComplexQueryBuilderFactory complexQueryBuilderFactory) {

		CommonSearchSourceBuilderAssembler commonSearchSourceBuilderAssembler =
			new CommonSearchSourceBuilderAssemblerImpl();

		ElasticsearchAggregationTranslatorFixture
			elasticsearchAggregationTranslatorFixture =
				new ElasticsearchAggregationTranslatorFixture();

		ReflectionTestUtil.setFieldValue(
			commonSearchSourceBuilderAssembler, "_aggregationTranslator",
			elasticsearchAggregationTranslatorFixture.
				getElasticsearchAggregationTranslator());

		ReflectionTestUtil.setFieldValue(
			commonSearchSourceBuilderAssembler, "_complexQueryBuilderFactory",
			complexQueryBuilderFactory);
		ReflectionTestUtil.setFieldValue(
			commonSearchSourceBuilderAssembler, "_facetTranslator",
			new FacetTranslator());

		return commonSearchSourceBuilderAssembler;
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

	private ClosePointInTimeRequestExecutor
		_createClosePointInTimeRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		ClosePointInTimeRequestExecutor closePointInTimeRequestExecutor =
			new ClosePointInTimeRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			closePointInTimeRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return closePointInTimeRequestExecutor;
	}

	private CountSearchRequestExecutor _createCountSearchRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver,
		CommonSearchSourceBuilderAssembler commonSearchSourceBuilderAssembler) {

		CountSearchRequestExecutor countSearchRequestExecutor =
			new CountSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			countSearchRequestExecutor, "_commonSearchSourceBuilderAssembler",
			commonSearchSourceBuilderAssembler);
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

	private OpenPointInTimeRequestExecutor
		_createOpenPointInTimeRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		OpenPointInTimeRequestExecutor openPointInTimeRequestExecutor =
			new OpenPointInTimeRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			openPointInTimeRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return openPointInTimeRequestExecutor;
	}

	private SearchRequestExecutor _createSearchRequestExecutor(
		ComplexQueryBuilderFactory complexQueryBuilderFactory,
		ElasticsearchClientResolver elasticsearchClientResolver,
		StatsRequestBuilderFactory statsRequestBuilderFactory) {

		SearchRequestExecutor searchRequestExecutor =
			new ElasticsearchSearchRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			searchRequestExecutor, "_closePointInTimeRequestExecutor",
			_createClosePointInTimeRequestExecutor(
				elasticsearchClientResolver));

		CommonSearchSourceBuilderAssembler commonSearchSourceBuilderAssembler =
			createCommonSearchSourceBuilderAssembler(
				complexQueryBuilderFactory);

		ReflectionTestUtil.setFieldValue(
			searchRequestExecutor, "_countSearchRequestExecutor",
			_createCountSearchRequestExecutor(
				elasticsearchClientResolver,
				commonSearchSourceBuilderAssembler));

		SearchSearchRequestAssembler searchSearchRequestAssembler =
			_createSearchSearchRequestAssembler(
				commonSearchSourceBuilderAssembler, statsRequestBuilderFactory);

		SearchSearchResponseAssembler searchSearchResponseAssembler =
			_createSearchSearchResponseAssembler(statsRequestBuilderFactory);

		ReflectionTestUtil.setFieldValue(
			searchRequestExecutor, "_multisearchSearchRequestExecutor",
			_createMultisearchSearchRequestExecutor(
				elasticsearchClientResolver, searchSearchRequestAssembler,
				searchSearchResponseAssembler));

		ReflectionTestUtil.setFieldValue(
			searchRequestExecutor, "_openPointInTimeRequestExecutor",
			_createOpenPointInTimeRequestExecutor(elasticsearchClientResolver));
		ReflectionTestUtil.setFieldValue(
			searchRequestExecutor, "_searchSearchRequestExecutor",
			_createSearchSearchRequestExecutor(
				elasticsearchClientResolver, searchSearchRequestAssembler,
				searchSearchResponseAssembler));
		ReflectionTestUtil.setFieldValue(
			searchRequestExecutor, "_suggestSearchRequestExecutor",
			_createSuggestSearchRequestExecutor(elasticsearchClientResolver));

		return searchRequestExecutor;
	}

	private SearchSearchRequestAssembler _createSearchSearchRequestAssembler(
		CommonSearchSourceBuilderAssembler commonSearchSourceBuilderAssembler,
		StatsRequestBuilderFactory statsRequestBuilderFactory) {

		SearchSearchRequestAssembler searchSearchRequestAssembler =
			new SearchSearchRequestAssemblerImpl();

		ReflectionTestUtil.setFieldValue(
			searchSearchRequestAssembler, "_commonSearchSourceBuilderAssembler",
			commonSearchSourceBuilderAssembler);
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
			searchSearchResponseAssembler, "_documentBuilderFactory",
			new DocumentBuilderFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			searchSearchResponseAssembler, "_geoBuilders",
			new GeoBuildersImpl());
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