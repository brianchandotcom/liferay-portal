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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.search;

import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.search.elasticsearch7.internal.SearchHitDocumentTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.ElasticsearchAggregationVisitorFixture;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.pipeline.ElasticsearchPipelineAggregationVisitorFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.facet.CompositeFacetProcessor;
import com.liferay.portal.search.elasticsearch7.internal.facet.DefaultFacetProcessor;
import com.liferay.portal.search.elasticsearch7.internal.facet.DefaultFacetTranslator;
import com.liferay.portal.search.elasticsearch7.internal.facet.FacetProcessor;
import com.liferay.portal.search.elasticsearch7.internal.facet.FacetTranslator;
import com.liferay.portal.search.elasticsearch7.internal.facet.ModifiedFacetProcessor;
import com.liferay.portal.search.elasticsearch7.internal.facet.NestedFacetProcessor;
import com.liferay.portal.search.elasticsearch7.internal.filter.ElasticsearchFilterTranslatorFixture;
import com.liferay.portal.search.elasticsearch7.internal.groupby.DefaultGroupByTranslator;
import com.liferay.portal.search.elasticsearch7.internal.highlight.DefaultHighlighterTranslator;
import com.liferay.portal.search.elasticsearch7.internal.query.ElasticsearchQueryTranslator;
import com.liferay.portal.search.elasticsearch7.internal.query.ElasticsearchQueryTranslatorFixture;
import com.liferay.portal.search.elasticsearch7.internal.search.response.DefaultSearchResponseTranslator;
import com.liferay.portal.search.elasticsearch7.internal.sort.DefaultSortTranslator;
import com.liferay.portal.search.elasticsearch7.internal.sort.ElasticsearchSortFieldTranslator;
import com.liferay.portal.search.elasticsearch7.internal.sort.ElasticsearchSortFieldTranslatorFixture;
import com.liferay.portal.search.elasticsearch7.internal.stats.DefaultStatsTranslator;
import com.liferay.portal.search.elasticsearch7.internal.stats.StatsTranslator;
import com.liferay.portal.search.elasticsearch7.internal.suggest.ElasticsearchSuggesterTranslatorFixture;
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
import com.liferay.portal.search.internal.stats.StatsResponseBuilderFactoryImpl;
import com.liferay.portal.search.legacy.stats.StatsRequestBuilderFactory;
import com.liferay.portal.search.query.Queries;

import java.util.Collections;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author Michael C. Han
 */
public class SearchRequestExecutorFixture {

	public SearchRequestExecutor getSearchRequestExecutor() {
		return _searchRequestExecutor;
	}

	public void setUp() {
		FacetProcessor<?> facetProcessor = _getFacetProcessor();

		ElasticsearchQueryTranslatorFixture
			elasticsearchQueryTranslatorFixture =
				new ElasticsearchQueryTranslatorFixture();

		ElasticsearchQueryTranslator elasticsearchQueryTranslator =
			elasticsearchQueryTranslatorFixture.
				getElasticsearchQueryTranslator();

		ElasticsearchSortFieldTranslatorFixture
			elasticsearchSortFieldTranslatorFixture =
				new ElasticsearchSortFieldTranslatorFixture(
					elasticsearchQueryTranslator);

		_searchRequestExecutor = _createSearchRequestExecutor(
			_elasticsearchClientResolver, elasticsearchQueryTranslator,
			elasticsearchSortFieldTranslatorFixture.
				getElasticsearchSortFieldTranslator(),
			facetProcessor,
			new DefaultStatsTranslator() {
				{
					setStatsResponseBuilderFactory(
						new StatsResponseBuilderFactoryImpl());
				}
			},
			new StatsRequestBuilderFactoryImpl(),
			createComplexQueryBuilderFactory(new QueriesImpl()));
	}

	protected static CommonSearchSourceBuilderAssembler
		createCommonSearchSourceBuilderAssembler(
			ElasticsearchQueryTranslator elasticsearchQueryTranslator,
			FacetProcessor<?> facetProcessor, StatsTranslator statsTranslator,
			ComplexQueryBuilderFactory complexQueryBuilderFactory) {

		com.liferay.portal.search.elasticsearch7.internal.legacy.query.
			ElasticsearchQueryTranslatorFixture
				legacyElasticsearchQueryTranslatorFixture =
					new com.liferay.portal.search.elasticsearch7.internal.
						legacy.query.ElasticsearchQueryTranslatorFixture();

		com.liferay.portal.search.elasticsearch7.internal.legacy.query.
			ElasticsearchQueryTranslator legacyElasticsearchQueryTranslator =
				legacyElasticsearchQueryTranslatorFixture.
					getElasticsearchQueryTranslator();

		ElasticsearchAggregationVisitorFixture
			elasticsearchAggregationVisitorFixture =
				new ElasticsearchAggregationVisitorFixture();

		ElasticsearchFilterTranslatorFixture
			elasticsearchFilterTranslatorFixture =
				new ElasticsearchFilterTranslatorFixture(
					legacyElasticsearchQueryTranslator);

		ElasticsearchPipelineAggregationVisitorFixture
			elasticsearchPipelineAggregationVisitorFixture =
				new ElasticsearchPipelineAggregationVisitorFixture();

		return new CommonSearchSourceBuilderAssemblerImpl() {
			{
				setAggregationTranslator(
					elasticsearchAggregationVisitorFixture.
						getElasticsearchAggregationVisitor());

				setComplexQueryBuilderFactory(complexQueryBuilderFactory);

				setFacetTranslator(
					_createFacetTranslator(
						facetProcessor, legacyElasticsearchQueryTranslator));

				setFilterToQueryBuilderTranslator(
					elasticsearchFilterTranslatorFixture.
						getElasticsearchFilterTranslator());

				setLegacyQueryToQueryBuilderTranslator(
					legacyElasticsearchQueryTranslator);

				setPipelineAggregationTranslator(
					elasticsearchPipelineAggregationVisitorFixture.
						getElasticsearchPipelineAggregationVisitor());

				setQueryToQueryBuilderTranslator(elasticsearchQueryTranslator);

				setStatsTranslator(statsTranslator);
			}
		};
	}

	protected static ComplexQueryBuilderFactory
		createComplexQueryBuilderFactory(Queries queries) {

		return new ComplexQueryBuilderFactoryImpl() {
			{
				setQueries(queries);
			}
		};
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	protected void setFacetProcessor(FacetProcessor<?> facetProcessor) {
		_facetProcessor = facetProcessor;
	}

	private static CountSearchRequestExecutor _createCountSearchRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver,
		CommonSearchSourceBuilderAssembler commonSearchSourceBuilderAssembler,
		StatsTranslator statsTranslator) {

		return new CountSearchRequestExecutorImpl() {
			{
				setCommonSearchResponseAssembler(
					new CommonSearchResponseAssemblerImpl() {
						{
							setStatsTranslator(statsTranslator);
						}
					});
				setCommonSearchSourceBuilderAssembler(
					commonSearchSourceBuilderAssembler);
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	private static FacetTranslator _createFacetTranslator(
		FacetProcessor<?> facetProcessor,
		QueryTranslator<QueryBuilder> queryTranslator) {

		return new DefaultFacetTranslator() {
			{
				setFacetProcessor(
					(FacetProcessor<SearchRequestBuilder>)facetProcessor);

				ElasticsearchFilterTranslatorFixture
					elasticsearchFilterTranslatorFixture =
						new ElasticsearchFilterTranslatorFixture(
							queryTranslator);

				setFilterTranslator(
					elasticsearchFilterTranslatorFixture.
						getElasticsearchFilterTranslator());
			}
		};
	}

	private static MultisearchSearchRequestExecutor
		_createMultisearchSearchRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			SearchSearchRequestAssembler searchSearchRequestAssembler,
			SearchSearchResponseAssembler searchSearchResponseAssembler) {

		return new MultisearchSearchRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setSearchSearchRequestAssembler(searchSearchRequestAssembler);
				setSearchSearchResponseAssembler(searchSearchResponseAssembler);
			}
		};
	}

	private static SearchRequestExecutor _createSearchRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver,
		ElasticsearchQueryTranslator elasticsearchQueryTranslator,
		ElasticsearchSortFieldTranslator elasticsearchSortFieldTranslator,
		FacetProcessor<?> facetProcessor, StatsTranslator statsTranslator,
		StatsRequestBuilderFactory statsRequestBuilderFactory,
		ComplexQueryBuilderFactory complexQueryBuilderFactory) {

		CommonSearchSourceBuilderAssembler commonSearchSourceBuilderAssembler =
			createCommonSearchSourceBuilderAssembler(
				elasticsearchQueryTranslator, facetProcessor, statsTranslator,
				complexQueryBuilderFactory);

		SearchSearchRequestAssembler searchSearchRequestAssembler =
			_createSearchSearchRequestAssembler(
				elasticsearchQueryTranslator, elasticsearchSortFieldTranslator,
				commonSearchSourceBuilderAssembler, statsRequestBuilderFactory,
				statsTranslator);

		SearchSearchResponseAssembler searchSearchResponseAssembler =
			_createSearchSearchResponseAssembler(
				statsRequestBuilderFactory, statsTranslator);

		return new ElasticsearchSearchRequestExecutor() {
			{
				setCountSearchRequestExecutor(
					_createCountSearchRequestExecutor(
						elasticsearchClientResolver,
						commonSearchSourceBuilderAssembler, statsTranslator));
				setMultisearchSearchRequestExecutor(
					_createMultisearchSearchRequestExecutor(
						elasticsearchClientResolver,
						searchSearchRequestAssembler,
						searchSearchResponseAssembler));
				setSearchSearchRequestExecutor(
					_createSearchSearchRequestExecutor(
						elasticsearchClientResolver,
						searchSearchRequestAssembler,
						searchSearchResponseAssembler));
				setSuggestSearchRequestExecutor(
					_createSuggestSearchRequestExecutor(
						elasticsearchClientResolver));
			}
		};
	}

	private static SearchSearchRequestAssembler
		_createSearchSearchRequestAssembler(
			ElasticsearchQueryTranslator elasticsearchQueryTranslator,
			ElasticsearchSortFieldTranslator elasticsearchSortFieldTranslator,
			CommonSearchSourceBuilderAssembler
				commonSearchSourceBuilderAssembler,
			StatsRequestBuilderFactory statsRequestBuilderFactory,
			StatsTranslator statsTranslator) {

		return new SearchSearchRequestAssemblerImpl() {
			{
				setCommonSearchSourceBuilderAssembler(
					commonSearchSourceBuilderAssembler);
				setGroupByRequestFactory(new GroupByRequestFactoryImpl());
				setGroupByTranslator(new DefaultGroupByTranslator());
				setHighlighterTranslator(new DefaultHighlighterTranslator());
				setQueryToQueryBuilderTranslator(elasticsearchQueryTranslator);
				setSortFieldTranslator(elasticsearchSortFieldTranslator);
				setSortTranslator(new DefaultSortTranslator());
				setStatsRequestBuilderFactory(statsRequestBuilderFactory);
				setStatsTranslator(statsTranslator);
			}
		};
	}

	private static SearchSearchRequestExecutor
		_createSearchSearchRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			SearchSearchRequestAssembler searchSearchRequestAssembler,
			SearchSearchResponseAssembler searchSearchResponseAssembler) {

		return new SearchSearchRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
				setSearchSearchRequestAssembler(searchSearchRequestAssembler);
				setSearchSearchResponseAssembler(searchSearchResponseAssembler);
			}
		};
	}

	private static SearchSearchResponseAssembler
		_createSearchSearchResponseAssembler(
			StatsRequestBuilderFactory statsRequestBuilderFactory,
			StatsTranslator statsTranslator) {

		return new SearchSearchResponseAssemblerImpl() {
			{
				setAggregationResults(new AggregationResultsImpl());
				setCommonSearchResponseAssembler(
					new CommonSearchResponseAssemblerImpl() {
						{
							setStatsTranslator(statsTranslator);
						}
					});
				setDocumentBuilderFactory(new DocumentBuilderFactoryImpl());
				setGeoBuilders(new GeoBuildersImpl());
				setHighlightFieldBuilderFactory(
					new HighlightFieldBuilderFactoryImpl());
				setSearchHitBuilderFactory(new SearchHitBuilderFactoryImpl());
				setSearchHitsBuilderFactory(new SearchHitsBuilderFactoryImpl());
				setSearchResponseTranslator(
					new DefaultSearchResponseTranslator() {
						{
							setGroupByResponseFactory(
								new GroupByResponseFactoryImpl());
							setSearchHitDocumentTranslator(
								new SearchHitDocumentTranslatorImpl());
							setStatsRequestBuilderFactory(
								statsRequestBuilderFactory);
							setStatsResultsTranslator(
								new StatsResultsTranslatorImpl());
							setStatsTranslator(statsTranslator);
						}
					});
			}
		};
	}

	private static SuggestSearchRequestExecutor
		_createSuggestSearchRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new SuggestSearchRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);

				ElasticsearchSuggesterTranslatorFixture
					elasticsearchSuggesterTranslatorFixture =
						new ElasticsearchSuggesterTranslatorFixture();

				setSuggesterTranslator(
					elasticsearchSuggesterTranslatorFixture.
						getElasticsearchSuggesterTranslator());
			}
		};
	}

	private FacetProcessor<?> _getFacetProcessor() {
		if (_facetProcessor != null) {
			return _facetProcessor;
		}

		return new CompositeFacetProcessor() {
			{
				defaultFacetProcessor = new DefaultFacetProcessor();
				setFacetProcessor(
					new ModifiedFacetProcessor(),
					Collections.singletonMap(
						"class.name", ModifiedFacetImpl.class.getName()));
				setFacetProcessor(
					new NestedFacetProcessor(),
					Collections.singletonMap(
						"class.name", NestedFacetImpl.class.getName()));
			}
		};
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private FacetProcessor<?> _facetProcessor;
	private SearchRequestExecutor _searchRequestExecutor;

}