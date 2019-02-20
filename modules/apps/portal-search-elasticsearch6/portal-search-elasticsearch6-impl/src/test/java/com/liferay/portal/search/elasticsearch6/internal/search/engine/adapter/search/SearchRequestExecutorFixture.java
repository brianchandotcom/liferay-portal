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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.search;

import com.liferay.portal.search.elasticsearch6.internal.SearchHitDocumentTranslatorImpl;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.ElasticsearchAggregationVisitorFixture;
import com.liferay.portal.search.elasticsearch6.internal.aggregation.pipeline.ElasticsearchPipelineAggregationVisitorFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch6.internal.facet.DefaultFacetProcessor;
import com.liferay.portal.search.elasticsearch6.internal.facet.DefaultFacetTranslator;
import com.liferay.portal.search.elasticsearch6.internal.facet.FacetProcessor;
import com.liferay.portal.search.elasticsearch6.internal.facet.FacetTranslator;
import com.liferay.portal.search.elasticsearch6.internal.filter.ElasticsearchFilterTranslatorFixture;
import com.liferay.portal.search.elasticsearch6.internal.groupby.DefaultGroupByTranslator;
import com.liferay.portal.search.elasticsearch6.internal.highlight.DefaultHighlighterTranslator;
import com.liferay.portal.search.elasticsearch6.internal.query.ElasticsearchQueryTranslatorFixture;
import com.liferay.portal.search.elasticsearch6.internal.search.response.DefaultSearchResponseTranslator;
import com.liferay.portal.search.elasticsearch6.internal.sort.DefaultSortTranslator;
import com.liferay.portal.search.elasticsearch6.internal.stats.DefaultStatsTranslator;
import com.liferay.portal.search.elasticsearch6.internal.stats.StatsTranslator;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.internal.aggregation.AggregationResultsImpl;
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

		_searchRequestExecutor = createSearchRequestExecutor(
			_elasticsearchClientResolver, facetProcessor,
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

		ElasticsearchQueryTranslatorFixture
			elasticsearchQueryTranslatorFixture =
				new ElasticsearchQueryTranslatorFixture();

		com.liferay.portal.search.elasticsearch6.internal.legacy.query.
			ElasticsearchQueryTranslatorFixture
			legacyElasticsearchQueryTranslatorFixture =
				new com.liferay.portal.search.elasticsearch6.internal.
					legacy.query.ElasticsearchQueryTranslatorFixture();

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
					legacyElasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator());

				setPipelineAggregationTranslator(
					elasticsearchPipelineAggregationVisitorFixture.
						getElasticsearchPipelineAggregationVisitor());

				setQueryToQueryBuilderTranslator(
					elasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator());

				setStatsTranslator(statsTranslator);
			}
		};
	}

	protected static CountSearchRequestExecutor
		createCountSearchRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver,
			FacetProcessor facetProcessor, StatsTranslator statsTranslator) {

		return new CountSearchRequestExecutorImpl() {
			{
				setCommonSearchRequestBuilderAssembler(
					createCommonSearchRequestBuilderAssembler(
						facetProcessor, statsTranslator));
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
		FacetProcessor facetProcessor, StatsTranslator statsTranslator,
		StatsRequestBuilderFactory statsRequestBuilderFactory) {

		SearchSearchRequestAssembler searchSearchRequestAssembler =
			createSearchSearchRequestAssembler(
				facetProcessor, statsRequestBuilderFactory, statsTranslator);

		SearchSearchResponseAssembler searchSearchResponseAssembler =
			createSearchSearchResponseAssembler(
				statsRequestBuilderFactory, statsTranslator);

		return new ElasticsearchSearchRequestExecutor() {
			{
				setCountSearchRequestExecutor(
					createCountSearchRequestExecutor(
						elasticsearchClientResolver, facetProcessor,
						statsTranslator));
				setMultisearchSearchRequestExecutor(
					createMultisearchSearchRequestExecutor(
						elasticsearchClientResolver,
						searchSearchRequestAssembler,
						searchSearchResponseAssembler));
				setSearchSearchRequestExecutor(
					createSearchSearchRequestExecutor(
						elasticsearchClientResolver,
						searchSearchRequestAssembler,
						searchSearchResponseAssembler));
			}
		};
	}

	protected static SearchSearchRequestAssembler
		createSearchSearchRequestAssembler(
			FacetProcessor facetProcessor,
			StatsRequestBuilderFactory statsRequestBuilderFactory,
			StatsTranslator statsTranslator) {

		return new SearchSearchRequestAssemblerImpl() {
			{
				setCommonSearchRequestBuilderAssembler(
					createCommonSearchRequestBuilderAssembler(
						facetProcessor, statsTranslator));
				setGroupByTranslator(new DefaultGroupByTranslator());
				setHighlighterTranslator(new DefaultHighlighterTranslator());
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

	protected static SearchSearchResponseAssembler
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
				setSearchResponseTranslator(
					new DefaultSearchResponseTranslator() {
						{
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