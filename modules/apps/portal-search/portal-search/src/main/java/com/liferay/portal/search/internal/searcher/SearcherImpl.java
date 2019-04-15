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

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.search.legacy.searcher.SearchResponseBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.SearchResponseBuilder;
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
		if (!(searchRequest instanceof SearchRequestImpl)) {
			throw new UnsupportedOperationException();
		}

		SearchRequestImpl searchRequestImpl = (SearchRequestImpl)searchRequest;

		SearchResponseBuilder searchResponseBuilder =
			searchResponseBuilderFactory.builder(
				searchRequestImpl.getSearchContext());

		doSmartSearch(searchRequestImpl, searchResponseBuilder);

		doFederatedSearches(searchRequestImpl, searchResponseBuilder);

		return searchResponseBuilder.federatedSearchKey(
			searchRequestImpl.getFederatedSearchKey()
		).request(
			searchRequestImpl
		).build();
	}

	protected static RuntimeException uncheck(SearchException se) {
		if (se.getCause() instanceof RuntimeException) {
			return (RuntimeException)se.getCause();
		}

		if (se.getCause() != null) {
			return new RuntimeException(se.getCause());
		}

		return new RuntimeException(se);
	}

	protected void doFederatedSearches(
		SearchRequest searchRequest,
		SearchResponseBuilder searchResponseBuilder) {

		List<SearchRequest> list = searchRequest.getFederatedSearchRequests();

		list.stream(
		).map(
			this::search
		).forEach(
			searchResponseBuilder::addFederatedSearchResponse
		);
	}

	protected void doIndexerSearch(
		SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		Class<?> singleIndexerClass = getSingleIndexerClass(searchRequestImpl);

		if (singleIndexerClass != null) {
			doSingleIndexerSearch(
				singleIndexerClass, searchRequestImpl, searchResponseBuilder);
		}
		else {
			doMultiIndexerSearch(searchRequestImpl, searchResponseBuilder);
		}
	}

	protected void doLowLevelSearch(
		SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		SearchContext searchContext = searchRequestImpl.getSearchContext();

		Hits hits = indexSearcherHelper.search(searchContext, null);

		searchResponseBuilder.hits(hits);
	}

	protected void doMultiIndexerSearch(
		SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		FacetedSearcher facetedSearcher =
			facetedSearcherManager.createFacetedSearcher();

		SearchContext searchContext = searchRequestImpl.getSearchContext();

		Hits hits = search(facetedSearcher, searchContext);

		searchResponseBuilder.hits(hits);
	}

	protected void doSingleIndexerSearch(
		Class<?> clazz, SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		Indexer<?> indexer = indexerRegistry.getIndexer(clazz);

		SearchContext searchContext = searchRequestImpl.getSearchContext();

		Hits hits = search(indexer, searchContext);

		searchResponseBuilder.hits(hits);
	}

	protected void doSmartSearch(
		SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		boolean indexerSearch = false;

		if (getCompanyId(searchRequestImpl) != 0) {
			indexerSearch = true;
		}

		if (indexerSearch) {
			doIndexerSearch(searchRequestImpl, searchResponseBuilder);
		}
		else {
			doLowLevelSearch(searchRequestImpl, searchResponseBuilder);
		}
	}

	protected long getCompanyId(SearchRequestImpl searchRequestImpl) {
		SearchContext searchContext = searchRequestImpl.getSearchContext();

		return searchContext.getCompanyId();
	}

	protected Class<?> getSingleIndexerClass(
		SearchRequestImpl searchRequestImpl) {

		List<Class<?>> modelIndexerClasses =
			searchRequestImpl.getModelIndexerClasses();

		if (modelIndexerClasses.size() == 1) {
			return modelIndexerClasses.get(0);
		}

		return null;
	}

	protected Hits search(
		FacetedSearcher facetedSearcher, SearchContext searchContext) {

		try {
			return facetedSearcher.search(searchContext);
		}
		catch (SearchException se) {
			throw uncheck(se);
		}
	}

	protected Hits search(Indexer indexer, SearchContext searchContext) {
		try {
			return indexer.search(searchContext);
		}
		catch (SearchException se) {
			throw uncheck(se);
		}
	}

	@Reference
	protected FacetedSearcherManager facetedSearcherManager;

	@Reference
	protected IndexerRegistry indexerRegistry;

	@Reference
	protected IndexSearcherHelper indexSearcherHelper;

	@Reference
	protected SearchResponseBuilderFactory searchResponseBuilderFactory;

}