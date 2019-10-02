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

import com.liferay.portal.search.elasticsearch7.internal.SearchHitDocumentTranslatorImpl;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.ElasticsearchAggregationVisitorFixture;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.pipeline.ElasticsearchPipelineAggregationVisitorFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.facet.DefaultFacetProcessor;
import com.liferay.portal.search.elasticsearch7.internal.facet.DefaultFacetTranslator;
import com.liferay.portal.search.elasticsearch7.internal.facet.FacetProcessor;
import com.liferay.portal.search.elasticsearch7.internal.facet.FacetTranslator;
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
import com.liferay.portal.search.internal.aggregation.AggregationResultsImpl;
import com.liferay.portal.search.internal.document.DocumentBuilderFactoryImpl;
import com.liferay.portal.search.internal.geolocation.GeoBuildersImpl;
import com.liferay.portal.search.internal.groupby.GroupByResponseFactoryImpl;
import com.liferay.portal.search.internal.highlight.HighlightFieldBuilderFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitBuilderFactoryImpl;
import com.liferay.portal.search.internal.hits.SearchHitsBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.groupby.GroupByRequestFactoryImpl;
import com.liferay.portal.search.internal.legacy.stats.StatsRequestBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.stats.StatsResultsTranslatorImpl;
import com.liferay.portal.search.internal.stats.StatsResponseBuilderFactoryImpl;
import com.liferay.portal.search.legacy.stats.StatsRequestBuilderFactory;

/**
 * @author Michael C. Han
 */
public class SearchRequestExecutorFixture {

	public SearchRequestExecutor getSearchRequestExecutor() {
		return _searchRequestExecutor;
	}

	public void setUp() {
		FacetProcessor<?> facetProcessor = getFacetProcessor();

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

		_searchRequestExecutor = createSearchRequestExecutor(
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
			new StatsRequestBuilderFactoryImpl());
	}

	protected static CommonSearchRequestBuilderAssembler
		createCommonSearchRequestBuilderAssembler(
			ElasticsearchQueryTranslator elasticsearchQueryTranslator,
			FacetProcessor facetProcessor, StatsTranslator statsTranslator) {

		ElasticsearchAggregationVisitorFixture
			elasticsearchAggregationVisitorFixture =
				new ElasticsearchAggregationVisitorFixture();

		ElasticsearchFilterTranslatorFixture
			elasticsearchFilterTranslatorFixture =
				new ElasticsearchFilterTranslatorFixture();

		ElasticsearchPipelineAggregationVisitorFixture
			elasticsearchPipelineAggregationVisitorFixture =
				new ElasticsearchPipelineAggregationVisitorFixture();

		com.liferay.portal.search.elasticsearch7.internal.legacy.query.
			ElasticsearchQueryTranslatorFixture
				legacyElasticsearchQueryTranslatorFixture =
					new com.liferay.portal.search.elasticsearch7.internal.
						legacy.query.ElasticsearchQueryTranslatorFixture();

		com.liferay.portal.search.elasticsearch7.internal.legacy.query.
			ElasticsearchQueryTranslator legacyElasticsearchQueryTranslator =
				legacyElasticsearchQueryTranslatorFixture.
					getElasticsearchQueryTranslator();

		return new CommonSearchRequestBuilderAssemblerImpl() {
			{
				setAggregationTranslator(
					elasticsearchAggregationVisitorFixture.
						getElasticsearchAggregationVisitor());

				setFacetTranslator(createFacetTranslator(facetProcessor));

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

	protected static CountSearchRequestExecutor
		createCountSearchRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			CommonSearchRequestBuilderAssembler
				commonSearchRequestBuilderAssembler,
			StatsTranslator statsTranslator) {

		return new CountSearchRequestExecutorImpl() {
			{
				setCommonSearchRequestBuilderAssembler(
					commonSearchRequestBuilderAssembler);
				setCommonSearchResponseAssembler(
					new CommonSearchResponseAssemblerImpl() {
						{
							setStatsTranslator(statsTranslator);
						}
					});
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static FacetTranslator createFacetTranslator(
		FacetProcessor facetProcessor) {

		return new DefaultFacetTranslator() {
			{
				setFacetProcessor(facetProcessor);

				ElasticsearchFilterTranslatorFixture
					elasticsearchFilterTranslatorFixture =
						new ElasticsearchFilterTranslatorFixture();

				setFilterTranslator(
					elasticsearchFilterTranslatorFixture.
						getElasticsearchFilterTranslator());
			}
		};
	}

	protected static MultisearchSearchRequestExecutor
		createMultisearchSearchRequestExecutor(
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

	protected static SearchRequestExecutor createSearchRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver,
		ElasticsearchQueryTranslator elasticsearchQueryTranslator,
		ElasticsearchSortFieldTranslator elasticsearchSortFieldTranslator,
		FacetProcessor facetProcessor, StatsTranslator statsTranslator,
		StatsRequestBuilderFactory statsRequestBuilderFactory) {

		CommonSearchRequestBuilderAssembler
			commonSearchRequestBuilderAssembler =
				createCommonSearchRequestBuilderAssembler(
					elasticsearchQueryTranslator, facetProcessor,
					statsTranslator);

		SearchSearchRequestAssembler searchSearchRequestAssembler =
			createSearchSearchRequestAssembler(
				elasticsearchQueryTranslator, elasticsearchSortFieldTranslator,
				commonSearchRequestBuilderAssembler, statsRequestBuilderFactory,
				statsTranslator);

		SearchSearchResponseAssemblerImpl searchSearchResponseAssemblerImpl =
			createSearchSearchResponseAssembler(
				statsRequestBuilderFactory, statsTranslator);

		return new ElasticsearchSearchRequestExecutor() {
			{
				setCountSearchRequestExecutor(
					createCountSearchRequestExecutor(
						elasticsearchClientResolver,
						commonSearchRequestBuilderAssembler, statsTranslator));
				setMultisearchSearchRequestExecutor(
					createMultisearchSearchRequestExecutor(
						elasticsearchClientResolver,
						searchSearchRequestAssembler,
						searchSearchResponseAssemblerImpl));
				setSearchSearchRequestExecutor(
					createSearchSearchRequestExecutor(
						elasticsearchClientResolver,
						searchSearchRequestAssembler,
						searchSearchResponseAssemblerImpl));
				setSuggestSearchRequestExecutor(
					createSuggestSearchRequestExecutor(
						elasticsearchClientResolver));
			}
		};
	}

	protected static SearchSearchRequestAssembler
		createSearchSearchRequestAssembler(
			ElasticsearchQueryTranslator elasticsearchQueryTranslator,
			ElasticsearchSortFieldTranslator elasticsearchSortFieldTranslator,
			CommonSearchRequestBuilderAssembler
				commonSearchRequestBuilderAssembler,
			StatsRequestBuilderFactory statsRequestBuilderFactory,
			StatsTranslator statsTranslator) {

		return new SearchSearchRequestAssemblerImpl() {
			{
				setCommonSearchRequestBuilderAssembler(
					commonSearchRequestBuilderAssembler);
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

	protected static SearchSearchRequestExecutor
		createSearchSearchRequestExecutor(
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

	protected static SearchSearchResponseAssemblerImpl
		createSearchSearchResponseAssembler(
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

	protected static SuggestSearchRequestExecutor
		createSuggestSearchRequestExecutor(
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

	protected FacetProcessor getFacetProcessor() {
		if (_facetProcessor != null) {
			return _facetProcessor;
		}

		return new DefaultFacetProcessor();
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	protected void setFacetProcessor(FacetProcessor<?> facetProcessor) {
		_facetProcessor = facetProcessor;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private FacetProcessor<?> _facetProcessor;
	private SearchRequestExecutor _searchRequestExecutor;

}