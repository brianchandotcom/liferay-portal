/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
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
import com.liferay.portal.search.opensearch2.internal.aggregation.OpenSearchAggregationTranslator;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.facet.FacetTranslator;
import com.liferay.portal.search.opensearch2.internal.highlight.HighlightTranslator;
import com.liferay.portal.search.opensearch2.internal.search.response.SearchResponseTranslator;
import com.liferay.portal.search.opensearch2.internal.suggest.OpenSearchSuggesterTranslator;
import com.liferay.portal.search.query.Queries;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public class SearchRequestExecutorFixture {

	public SearchRequestExecutor getSearchRequestExecutor() {
		return _searchRequestExecutor;
	}

	public void setUp() {
		_searchRequestExecutor = _createSearchRequestExecutor(
			createComplexQueryBuilderFactory(new QueriesImpl()),
			_openSearchConnectionManager, new StatsRequestBuilderFactoryImpl());
	}

	protected static CommonSearchRequestBuilderAssembler
		createCommonSearchRequestBuilderAssembler(
			ComplexQueryBuilderFactory complexQueryBuilderFactory) {

		CommonSearchRequestBuilderAssembler
			commonSearchRequestBuilderAssembler =
				new CommonSearchRequestBuilderAssemblerImpl();

		ReflectionTestUtil.setFieldValue(
			commonSearchRequestBuilderAssembler, "_aggregationTranslator",
			new OpenSearchAggregationTranslator());
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

	protected void setOpenSearchConnectionManager(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_openSearchConnectionManager = openSearchConnectionManager;
	}

	private ClosePointInTimeRequestExecutor
		_createClosePointInTimeRequestExecutor(
			OpenSearchConnectionManager openSearchConnectionManager) {

		ClosePointInTimeRequestExecutor closePointInTimeRequestExecutor =
			new ClosePointInTimeRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			closePointInTimeRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return closePointInTimeRequestExecutor;
	}

	private CountSearchRequestExecutor _createCountSearchRequestExecutor(
		CommonSearchRequestBuilderAssembler commonSearchRequestBuilderAssembler,
		OpenSearchConnectionManager openSearchConnectionManager) {

		CountSearchRequestExecutor countSearchRequestExecutor =
			new CountSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			countSearchRequestExecutor, "_commonSearchRequestBuilderAssembler",
			commonSearchRequestBuilderAssembler);
		ReflectionTestUtil.setFieldValue(
			countSearchRequestExecutor, "_commonSearchResponseAssembler",
			new CommonSearchResponseAssemblerImpl());
		ReflectionTestUtil.setFieldValue(
			countSearchRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return countSearchRequestExecutor;
	}

	private MultisearchSearchRequestExecutor
		_createMultisearchSearchRequestExecutor(
			OpenSearchConnectionManager openSearchConnectionManager,
			SearchSearchRequestAssembler searchSearchRequestAssembler,
			SearchSearchResponseAssembler searchSearchResponseAssembler) {

		MultisearchSearchRequestExecutor multisearchSearchRequestExecutor =
			new MultisearchSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			multisearchSearchRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);
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
			OpenSearchConnectionManager openSearchConnectionManager) {

		OpenPointInTimeRequestExecutor openPointInTimeRequestExecutor =
			new OpenPointInTimeRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			openPointInTimeRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return openPointInTimeRequestExecutor;
	}

	private SearchRequestExecutor _createSearchRequestExecutor(
		ComplexQueryBuilderFactory complexQueryBuilderFactory,
		OpenSearchConnectionManager openSearchConnectionManager,
		StatsRequestBuilderFactory statsRequestBuilderFactory) {

		SearchRequestExecutor searchRequestExecutor =
			new OpenSearchSearchRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			searchRequestExecutor, "_closePointInTimeRequestExecutor",
			_createClosePointInTimeRequestExecutor(
				openSearchConnectionManager));

		CommonSearchRequestBuilderAssembler
			commonSearchRequestBuilderAssembler =
				createCommonSearchRequestBuilderAssembler(
					complexQueryBuilderFactory);

		ReflectionTestUtil.setFieldValue(
			searchRequestExecutor, "_countSearchRequestExecutor",
			_createCountSearchRequestExecutor(
				commonSearchRequestBuilderAssembler,
				openSearchConnectionManager));

		SearchSearchRequestAssembler searchSearchRequestAssembler =
			_createSearchSearchRequestAssembler(
				commonSearchRequestBuilderAssembler,
				statsRequestBuilderFactory);

		SearchSearchResponseAssembler searchSearchResponseAssembler =
			_createSearchSearchResponseAssembler(statsRequestBuilderFactory);

		ReflectionTestUtil.setFieldValue(
			searchRequestExecutor, "_multisearchSearchRequestExecutor",
			_createMultisearchSearchRequestExecutor(
				openSearchConnectionManager, searchSearchRequestAssembler,
				searchSearchResponseAssembler));

		ReflectionTestUtil.setFieldValue(
			searchRequestExecutor, "_openPointInTimeRequestExecutor",
			_createOpenPointInTimeRequestExecutor(openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			searchRequestExecutor, "_searchSearchRequestExecutor",
			_createSearchSearchRequestExecutor(
				openSearchConnectionManager, searchSearchRequestAssembler,
				searchSearchResponseAssembler));
		ReflectionTestUtil.setFieldValue(
			searchRequestExecutor, "_suggestSearchRequestExecutor",
			_createSuggestSearchRequestExecutor(openSearchConnectionManager));

		return searchRequestExecutor;
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
			searchSearchRequestAssembler, "_highlightTranslator",
			new HighlightTranslator());
		ReflectionTestUtil.setFieldValue(
			searchSearchRequestAssembler, "_statsRequestBuilderFactory",
			statsRequestBuilderFactory);

		return searchSearchRequestAssembler;
	}

	private SearchSearchRequestExecutor _createSearchSearchRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager,
		SearchSearchRequestAssembler searchSearchRequestAssembler,
		SearchSearchResponseAssembler searchSearchResponseAssembler) {

		SearchSearchRequestExecutor searchSearchRequestExecutor =
			new SearchSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			searchSearchRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);
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
			searchSearchResponseAssembler, "_commonSearchResponseAssembler",
			new CommonSearchResponseAssemblerImpl());
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
		OpenSearchConnectionManager openSearchConnectionManager) {

		SuggestSearchRequestExecutor suggestSearchRequestExecutor =
			new SuggestSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			suggestSearchRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		ReflectionTestUtil.setFieldValue(
			suggestSearchRequestExecutor, "_suggesterTranslator",
			new OpenSearchSuggesterTranslator());

		return suggestSearchRequestExecutor;
	}

	private OpenSearchConnectionManager _openSearchConnectionManager;
	private SearchRequestExecutor _searchRequestExecutor;

}