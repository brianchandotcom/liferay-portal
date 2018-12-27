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
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
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
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;

import org.elasticsearch.action.search.SearchRequestBuilder;

/**
 * @author Michael C. Han
 */
public class SearchRequestExecutorFixture {

	public SearchRequestExecutorFixture(
		ElasticsearchClientResolver elasticsearchClientResolver,
		FacetProcessor<SearchRequestBuilder> facetProcessor) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
		_facetProcessor = facetProcessor;
	}

	public SearchRequestExecutor createExecutor() {
		return new ElasticsearchSearchRequestExecutor() {
			{
				countSearchRequestExecutor = createCountSearchRequestExecutor();
				multisearchSearchRequestExecutor =
					createMultisearchSearchRequestExecutor();
				searchSearchRequestExecutor =
					createSearchSearchRequestExecutor();
			}
		};
	}

	protected CommonSearchRequestBuilderAssembler
		createCommonSearchRequestBuilderAssembler() {

		return new CommonSearchRequestBuilderAssemblerImpl() {
			{
				facetTranslator = createFacetTranslator();

				ElasticsearchFilterTranslatorFixture
					elasticsearchFilterTranslatorFixture =
						new ElasticsearchFilterTranslatorFixture();

				filterTranslator =
					elasticsearchFilterTranslatorFixture.
						getElasticsearchFilterTranslator();

				ElasticsearchQueryTranslatorFixture
					elasticsearchQueryTranslatorFixture =
						new ElasticsearchQueryTranslatorFixture();

				queryTranslator =
					elasticsearchQueryTranslatorFixture.
						getElasticsearchQueryTranslator();
			}
		};
	}

	protected CountSearchRequestExecutor createCountSearchRequestExecutor() {
		return new CountSearchRequestExecutorImpl() {
			{
				commonSearchRequestBuilderAssembler =
					createCommonSearchRequestBuilderAssembler();
				commonSearchResponseAssembler =
					new CommonSearchResponseAssemblerImpl();
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected FacetTranslator createFacetTranslator() {
		return new DefaultFacetTranslator() {
			{
				facetProcessor = _facetProcessor;

				ElasticsearchFilterTranslatorFixture
					elasticsearchFilterTranslatorFixture =
						new ElasticsearchFilterTranslatorFixture();

				filterTranslator =
					elasticsearchFilterTranslatorFixture.
						getElasticsearchFilterTranslator();
			}
		};
	}

	protected MultisearchSearchRequestExecutor
		createMultisearchSearchRequestExecutor() {

		return new MultisearchSearchRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
				searchSearchRequestAssembler =
					createSearchSearchRequestAssembler();
				searchSearchResponseAssembler =
					createSearchSearchResponseAssembler();
			}
		};
	}

	protected SearchSearchRequestAssembler
		createSearchSearchRequestAssembler() {

		return new SearchSearchRequestAssemblerImpl() {
			{
				commonSearchRequestBuilderAssembler =
					createCommonSearchRequestBuilderAssembler();
				groupByTranslator = new DefaultGroupByTranslator();
				highlighterTranslator = new DefaultHighlighterTranslator();
				sortTranslator = new DefaultSortTranslator();
				statsTranslator = new DefaultStatsTranslator();
			}
		};
	}

	protected SearchSearchRequestExecutor createSearchSearchRequestExecutor() {
		return new SearchSearchRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
				searchSearchRequestAssembler =
					createSearchSearchRequestAssembler();
				searchSearchResponseAssembler =
					createSearchSearchResponseAssembler();
			}
		};
	}

	protected SearchSearchResponseAssembler
		createSearchSearchResponseAssembler() {

		return new SearchSearchResponseAssemblerImpl() {
			{
				commonSearchResponseAssembler =
					new CommonSearchResponseAssemblerImpl();
				searchResponseTranslator =
					new DefaultSearchResponseTranslator() {
						{
							searchHitDocumentTranslator =
								new SearchHitDocumentTranslatorImpl();
							statsTranslator = new DefaultStatsTranslator();
						}
					};
			}
		};
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;
	private final FacetProcessor<SearchRequestBuilder> _facetProcessor;

}