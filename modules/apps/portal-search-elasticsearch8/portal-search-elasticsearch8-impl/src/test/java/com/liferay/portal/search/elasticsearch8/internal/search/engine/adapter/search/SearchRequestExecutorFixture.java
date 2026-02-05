/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search;

import co.elastic.clients.elasticsearch.core.SearchRequest;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.ElasticsearchAggregationTranslator;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.facet.DefaultFacetTranslator;
import com.liferay.portal.search.elasticsearch8.internal.facet.FacetProcessor;
import com.liferay.portal.search.elasticsearch8.internal.facet.FacetTranslator;
import com.liferay.portal.search.elasticsearch8.internal.facet.NestedFacetProcessor;
import com.liferay.portal.search.elasticsearch8.internal.facet.RangeFacetProcessor;
import com.liferay.portal.search.elasticsearch8.internal.search.response.SearchHitDocumentTranslatorImpl;
import com.liferay.portal.search.elasticsearch8.internal.search.response.SearchResponseTranslator;
import com.liferay.portal.search.elasticsearch8.internal.suggest.ElasticsearchSuggesterTranslator;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.filter.ComplexQueryBuilderFactory;
import com.liferay.portal.search.internal.aggregation.AggregationResultsImpl;
import com.liferay.portal.search.internal.document.DocumentBuilderFactoryImpl;
import com.liferay.portal.search.internal.facet.ModifiedFacetImpl;
import com.liferay.portal.search.internal.facet.NestedFacetImpl;
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

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

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
			_elasticsearchClientResolver, _facetProcessor,
			new StatsRequestBuilderFactoryImpl());
	}

	public void tearDown() {
		_serviceRegistrations.forEach(
			serviceRegistration -> serviceRegistration.unregister());

		ReflectionTestUtil.invoke(
			_defaultFacetTranslator, "deactivate", new Class<?>[0]);
	}

	protected static CommonSearchRequestBuilderAssembler
		createCommonSearchRequestBuilderAssembler(
			ComplexQueryBuilderFactory complexQueryBuilderFactory,
			FacetProcessor<?> facetProcessor) {

		CommonSearchRequestBuilderAssembler
			commonSearchRequestBuilderAssembler =
				new CommonSearchRequestBuilderAssemblerImpl();

		ReflectionTestUtil.setFieldValue(
			commonSearchRequestBuilderAssembler, "_aggregationTranslator",
			new ElasticsearchAggregationTranslator());
		ReflectionTestUtil.setFieldValue(
			commonSearchRequestBuilderAssembler, "_complexQueryBuilderFactory",
			complexQueryBuilderFactory);
		ReflectionTestUtil.setFieldValue(
			commonSearchRequestBuilderAssembler, "_facetTranslator",
			_createFacetTranslator(facetProcessor));

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

	protected void setFacetProcessor(FacetProcessor<?> facetProcessor) {
		_facetProcessor = facetProcessor;
	}

	private static FacetTranslator _createFacetTranslator(
		FacetProcessor<?> facetProcessor) {

		_defaultFacetTranslator = new DefaultFacetTranslator();

		ReflectionTestUtil.invoke(
			_defaultFacetTranslator, "activate",
			new Class<?>[] {BundleContext.class}, _bundleContext);

		if (facetProcessor != null) {
			ReflectionTestUtil.setFieldValue(
				_defaultFacetTranslator, "_defaultFacetProcessor",
				(FacetProcessor<SearchRequest.Builder>)facetProcessor);
		}
		else {
			_serviceRegistrations.add(
				_bundleContext.registerService(
					(Class<FacetProcessor<SearchRequest.Builder>>)
						(Class<?>)FacetProcessor.class,
					new RangeFacetProcessor(),
					MapUtil.singletonDictionary(
						"class.name", ModifiedFacetImpl.class.getName())));

			_serviceRegistrations.add(
				_bundleContext.registerService(
					(Class<FacetProcessor<SearchRequest.Builder>>)
						(Class<?>)FacetProcessor.class,
					new NestedFacetProcessor(),
					MapUtil.singletonDictionary(
						"class.name", NestedFacetImpl.class.getName())));
		}

		return _defaultFacetTranslator;
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
		CommonSearchRequestBuilderAssembler commonSearchRequestBuilderAssembler,
		ElasticsearchClientResolver elasticsearchClientResolver) {

		CountSearchRequestExecutor countSearchRequestExecutor =
			new CountSearchRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			countSearchRequestExecutor, "_commonSearchResponseAssembler",
			new CommonSearchResponseAssemblerImpl());
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
		FacetProcessor<?> facetProcessor,
		StatsRequestBuilderFactory statsRequestBuilderFactory) {

		SearchRequestExecutor searchRequestExecutor =
			new ElasticsearchSearchRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			searchRequestExecutor, "_closePointInTimeRequestExecutor",
			_createClosePointInTimeRequestExecutor(
				elasticsearchClientResolver));

		CommonSearchRequestBuilderAssembler
			commonSearchRequestBuilderAssembler =
				createCommonSearchRequestBuilderAssembler(
					complexQueryBuilderFactory, facetProcessor);

		ReflectionTestUtil.setFieldValue(
			searchRequestExecutor, "_countSearchRequestExecutor",
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
				new GroupByResponseFactoryImpl(),
				new SearchHitDocumentTranslatorImpl(),
				statsRequestBuilderFactory, new StatsResultsTranslatorImpl()));

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

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static DefaultFacetTranslator _defaultFacetTranslator;
	private static final List
		<ServiceRegistration<FacetProcessor<SearchRequest.Builder>>>
			_serviceRegistrations = new ArrayList<>();

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private final ElasticsearchSuggesterTranslator
		_elasticsearchSuggesterTranslator =
			new ElasticsearchSuggesterTranslator();
	private FacetProcessor<?> _facetProcessor;
	private SearchRequestExecutor _searchRequestExecutor;

}