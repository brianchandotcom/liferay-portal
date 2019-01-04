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
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;

/**
 * @author Michael C. Han
 */
public class SearchRequestExecutorFixture {

	public SearchRequestExecutor getSearchRequestExecutor() {
		return _searchRequestExecutor;
	}

	public void setUp() {
		FacetProcessor facetProcessor = getFacetProcessor();

		_searchRequestExecutor = createSearchRequestExecutor(
			elasticsearchClientResolver, facetProcessor);
	}

	protected static CommonSearchRequestBuilderAssembler
		createCommonSearchRequestBuilderAssembler(
			FacetProcessor facetProcessor1) {

		return new CommonSearchRequestBuilderAssemblerImpl() {
			{
				facetTranslator = createFacetTranslator(facetProcessor1);

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

	protected static CountSearchRequestExecutor
		createCountSearchRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1,
			FacetProcessor facetProcessor1) {

		return new CountSearchRequestExecutorImpl() {
			{
				commonSearchRequestBuilderAssembler =
					createCommonSearchRequestBuilderAssembler(facetProcessor1);
				commonSearchResponseAssembler =
					new CommonSearchResponseAssemblerImpl();
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static FacetTranslator createFacetTranslator(
		FacetProcessor facetProcessor1) {

		return new DefaultFacetTranslator() {
			{
				facetProcessor = facetProcessor1;

				ElasticsearchFilterTranslatorFixture
					elasticsearchFilterTranslatorFixture =
						new ElasticsearchFilterTranslatorFixture();

				filterTranslator =
					elasticsearchFilterTranslatorFixture.
						getElasticsearchFilterTranslator();
			}
		};
	}

	protected static MultisearchSearchRequestExecutor
		createMultisearchSearchRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1,
			FacetProcessor facetProcessor1) {

		return new MultisearchSearchRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
				searchSearchRequestAssembler =
					createSearchSearchRequestAssembler(facetProcessor1);
				searchSearchResponseAssembler =
					createSearchSearchResponseAssembler();
			}
		};
	}

	protected static SearchRequestExecutor createSearchRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver1,
		FacetProcessor facetProcessor1) {

		return new ElasticsearchSearchRequestExecutor() {
			{
				countSearchRequestExecutor = createCountSearchRequestExecutor(
					elasticsearchClientResolver1, facetProcessor1);
				multisearchSearchRequestExecutor =
					createMultisearchSearchRequestExecutor(
						elasticsearchClientResolver1, facetProcessor1);
				searchSearchRequestExecutor = createSearchSearchRequestExecutor(
					elasticsearchClientResolver1, facetProcessor1);
			}
		};
	}

	protected static SearchSearchRequestAssembler
		createSearchSearchRequestAssembler(FacetProcessor facetProcessor1) {

		return new SearchSearchRequestAssemblerImpl() {
			{
				commonSearchRequestBuilderAssembler =
					createCommonSearchRequestBuilderAssembler(facetProcessor1);
				groupByTranslator = new DefaultGroupByTranslator();
				highlighterTranslator = new DefaultHighlighterTranslator();
				sortTranslator = new DefaultSortTranslator();
				statsTranslator = new DefaultStatsTranslator();
			}
		};
	}

	protected static SearchSearchRequestExecutor
		createSearchSearchRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1,
			FacetProcessor facetProcessor1) {

		return new SearchSearchRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
				searchSearchRequestAssembler =
					createSearchSearchRequestAssembler(facetProcessor1);
				searchSearchResponseAssembler =
					createSearchSearchResponseAssembler();
			}
		};
	}

	protected static SearchSearchResponseAssembler
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

	protected FacetProcessor getFacetProcessor() {
		if (facetProcessor != null) {
			return facetProcessor;
		}

		return new DefaultFacetProcessor();
	}

	protected ElasticsearchClientResolver elasticsearchClientResolver;
	protected FacetProcessor<?> facetProcessor;

	private SearchRequestExecutor _searchRequestExecutor;

}