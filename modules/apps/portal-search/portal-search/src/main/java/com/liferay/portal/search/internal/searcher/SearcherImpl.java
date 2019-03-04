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

package com.liferay.portal.search.internal.searcher;

import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.search.internal.legacy.searcher.SearchRequestBuilderImpl.SearchRequestImpl;
import com.liferay.portal.search.legacy.searcher.SearchResponseBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = Searcher.class)
public class SearcherImpl implements Searcher {

	@Override
	public SearchResponse search(SearchRequest searchRequest) {
		if (searchRequest instanceof SearchRequestImpl) {
			SearchRequestImpl searchRequestImpl =
				(SearchRequestImpl)searchRequest;

			SearchContext searchContext = searchRequestImpl.getSearchContext();

			return doLegacySearch(searchContext, searchRequest);
		}

		throw new UnsupportedOperationException();
	}

	protected SearchResponse doLegacySearch(
		SearchContext searchContext, SearchRequest searchRequest) {

		List<Class<?>> modelIndexerClasses =
			searchRequest.getModelIndexerClasses();

		if (modelIndexerClasses.size() == 1) {
			return doSingleIndexerSearch(
				modelIndexerClasses.get(0), searchContext, searchRequest);
		}

		return doMultiIndexerSearch(searchContext, searchRequest);
	}

	protected SearchResponse doMultiIndexerSearch(
		SearchContext searchContext, SearchRequest searchRequest) {

		FacetedSearcherSearch facetedSearcherSearch = new FacetedSearcherSearch(
			searchContext, searchRequest, facetedSearcherManager,
			searchResponseBuilderFactory);

		return facetedSearcherSearch.search();
	}

	protected SearchResponse doSingleIndexerSearch(
		Class<?> clazz, SearchContext searchContext,
		SearchRequest searchRequest) {

		IndexerSearch indexerSearch = new IndexerSearch(
			clazz, searchContext, searchRequest, indexerRegistry,
			searchResponseBuilderFactory);

		return indexerSearch.search();
	}

	@Reference
	protected FacetedSearcherManager facetedSearcherManager;

	@Reference
	protected IndexerRegistry indexerRegistry;

	@Reference
	protected SearchResponseBuilderFactory searchResponseBuilderFactory;

}