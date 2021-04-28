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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.legacy.searcher.SearchResponseBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.SearchResponseBuilder;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.spi.searcher.SearchRequestContributor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = Searcher.class)
public class SearcherImpl implements Searcher {

	@Override
	public SearchResponse search(SearchRequest searchRequest) {
		return doSearch(transformSearchRequest(searchRequest));
	}

	protected static <T> T transform(T t, Stream<Function<T, T>> stream) {
		return stream.reduce(
			(beforeFunction, afterFunction) -> beforeFunction.andThen(
				afterFunction)
		).orElse(
			Function.identity()
		).apply(
			t
		);
	}

	protected static RuntimeException uncheck(SearchException searchException) {
		if (searchException.getCause() instanceof RuntimeException) {
			return (RuntimeException)searchException.getCause();
		}

		if (searchException.getCause() != null) {
			return new RuntimeException(searchException.getCause());
		}

		return new RuntimeException(searchException);
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

		String singleIndexerClassName = getSingleIndexerClassName(
			searchRequestImpl);

		if (singleIndexerClassName != null) {
			doSingleIndexerSearch(
				singleIndexerClassName, searchRequestImpl,
				searchResponseBuilder);
		}
		else {
			doMultiIndexerSearch(searchRequestImpl, searchResponseBuilder);
		}
	}

	protected void doLowLevelSearch(
		SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		SearchContext searchContext = searchRequestImpl.getSearchContext();

		if (isCount(searchRequestImpl)) {
			indexSearcherHelper.searchCount(searchContext, null);

			return;
		}

		Hits hits = indexSearcherHelper.search(searchContext, null);

		searchResponseBuilder.hits(hits);
	}

	protected void doMultiIndexerSearch(
		SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		FacetedSearcher facetedSearcher =
			facetedSearcherManager.createFacetedSearcher();

		Hits hits = search(
			facetedSearcher, searchRequestImpl.getSearchContext());

		if (isCount(searchRequestImpl)) {
			searchResponseBuilder.count(hits.getLength());

			return;
		}

		searchResponseBuilder.hits(hits);
	}

	protected SearchResponse doSearch(SearchRequest searchRequest) {
		if (!(searchRequest instanceof SearchRequestImpl)) {
			throw new UnsupportedOperationException();
		}

		SearchRequestImpl searchRequestImpl = (SearchRequestImpl)searchRequest;

		SearchResponseBuilder searchResponseBuilder =
			searchResponseBuilderFactory.builder(
				searchRequestImpl.getSearchContext());

		doSmartSearch(searchRequestImpl, searchResponseBuilder);

		doFederatedSearches(searchRequestImpl, searchResponseBuilder);

		SearchContext searchContext = searchRequestImpl.getSearchContext();

		String exceptionMessage = (String)searchContext.getAttribute(
			"search.exception.message");

		if (Validator.isNotNull(exceptionMessage)) {
			searchResponseBuilder.responseString(exceptionMessage);
		}

		return searchResponseBuilder.federatedSearchKey(
			searchRequestImpl.getFederatedSearchKey()
		).request(
			searchRequestImpl
		).build();
	}

	protected void doSingleIndexerSearch(
		String singleIndexerClassName, SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		Indexer<?> indexer = indexerRegistry.getIndexer(singleIndexerClassName);

		SearchContext searchContext = searchRequestImpl.getSearchContext();

		if (isCount(searchRequestImpl)) {
			searchResponseBuilder.count(searchCount(indexer, searchContext));

			return;
		}

		Hits hits = search(indexer, searchContext);

		searchResponseBuilder.hits(hits);
	}

	protected void doSmartSearch(
		SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		List<String> indexes = searchRequestImpl.getIndexes();

		if (indexes.isEmpty()) {
			doIndexerSearch(searchRequestImpl, searchResponseBuilder);
		}
		else {
			doLowLevelSearch(searchRequestImpl, searchResponseBuilder);
		}
	}

	protected Stream<Function<SearchRequest, SearchRequest>> getContributors(
		SearchRequest searchRequest) {

		Stream<SearchRequestContributor> stream =
			searchRequestContributorsHolder.stream(
				searchRequest.getIncludeContributors(),
				searchRequest.getExcludeContributors());

		return stream.map(
			searchRequestContributor -> searchRequestContributor::contribute);
	}

	protected String getSingleIndexerClassName(
		SearchRequestImpl searchRequestImpl) {

		List<String> modelIndexerClassNames =
			searchRequestImpl.getModelIndexerClassNames();

		if (modelIndexerClassNames.size() == 1) {
			return modelIndexerClassNames.get(0);
		}

		return null;
	}

	protected boolean isCount(SearchRequestImpl searchRequestImpl) {
		if ((searchRequestImpl.getSize() != null) &&
			(searchRequestImpl.getSize() == 0)) {

			return true;
		}

		return false;
	}

	protected Hits search(
		FacetedSearcher facetedSearcher, SearchContext searchContext) {

		try {
			return facetedSearcher.search(searchContext);
		}
		catch (SearchException searchException) {
			throw uncheck(searchException);
		}
	}

	protected Hits search(Indexer<?> indexer, SearchContext searchContext) {
		try {
			return indexer.search(searchContext);
		}
		catch (SearchException searchException) {
			throw uncheck(searchException);
		}
	}

	protected long searchCount(
		Indexer<?> indexer, SearchContext searchContext) {

		try {
			return indexer.searchCount(searchContext);
		}
		catch (SearchException searchException) {
			throw uncheck(searchException);
		}
	}

	protected SearchRequest transformSearchRequest(
		SearchRequest searchRequest) {

		return transform(searchRequest, getContributors(searchRequest));
	}

	@Reference
	protected FacetedSearcherManager facetedSearcherManager;

	@Reference
	protected IndexerRegistry indexerRegistry;

	@Reference
	protected IndexSearcherHelper indexSearcherHelper;

	@Reference
	protected SearchRequestContributorsHolder searchRequestContributorsHolder;

	@Reference
	protected SearchResponseBuilderFactory searchResponseBuilderFactory;

}