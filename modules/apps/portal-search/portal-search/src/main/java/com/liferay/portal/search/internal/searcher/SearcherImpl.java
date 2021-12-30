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
import com.liferay.portal.search.internal.searcher.helper.IndexSearcherHelper;
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
		return doSearch(_transformSearchRequest(searchRequest));
	}

	protected SearchResponse doSearch(SearchRequest searchRequest) {
		if (!(searchRequest instanceof SearchRequestImpl)) {
			throw new UnsupportedOperationException();
		}

		SearchRequestImpl searchRequestImpl = (SearchRequestImpl)searchRequest;

		SearchResponseBuilder searchResponseBuilder =
			searchResponseBuilderFactory.builder(
				searchRequestImpl.getSearchContext());

		_smartSearch(searchRequestImpl, searchResponseBuilder);

		_federatedSearches(searchRequestImpl, searchResponseBuilder);

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

	protected Hits search(
		FacetedSearcher facetedSearcher, SearchContext searchContext) {

		try {
			return facetedSearcher.search(searchContext);
		}
		catch (SearchException searchException) {
			throw _uncheck(searchException);
		}
	}

	protected Hits search(Indexer<?> indexer, SearchContext searchContext) {
		try {
			return indexer.search(searchContext);
		}
		catch (SearchException searchException) {
			throw _uncheck(searchException);
		}
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

	private void _federatedSearches(
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

	private Stream<Function<SearchRequest, SearchRequest>> _getContributors(
		SearchRequest searchRequest) {

		Stream<SearchRequestContributor> stream =
			searchRequestContributorsHolder.stream(
				searchRequest.getIncludeContributors(),
				searchRequest.getExcludeContributors());

		return stream.map(
			searchRequestContributor -> searchRequestContributor::contribute);
	}

	private String _getSingleIndexerClassName(
		SearchRequestImpl searchRequestImpl) {

		List<String> modelIndexerClassNames =
			searchRequestImpl.getModelIndexerClassNames();

		if (modelIndexerClassNames.size() == 1) {
			return modelIndexerClassNames.get(0);
		}

		return null;
	}

	private void _indexerSearch(
		SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		String singleIndexerClassName = _getSingleIndexerClassName(
			searchRequestImpl);

		if (singleIndexerClassName != null) {
			_singleIndexerSearch(
				singleIndexerClassName, searchRequestImpl,
				searchResponseBuilder);
		}
		else {
			_multiIndexerSearch(searchRequestImpl, searchResponseBuilder);
		}
	}

	private boolean _isCount(SearchRequestImpl searchRequestImpl) {
		if ((searchRequestImpl.getSize() != null) &&
			(searchRequestImpl.getSize() == 0)) {

			return true;
		}

		return false;
	}

	private void _lowLevelSearch(
		SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		SearchContext searchContext = searchRequestImpl.getSearchContext();

		if (_isCount(searchRequestImpl)) {
			indexSearcherHelper.searchCount(searchContext, null);

			return;
		}

		Hits hits = indexSearcherHelper.search(searchContext, null);

		searchResponseBuilder.hits(hits);
	}

	private void _multiIndexerSearch(
		SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		FacetedSearcher facetedSearcher =
			facetedSearcherManager.createFacetedSearcher();

		Hits hits = search(
			facetedSearcher, searchRequestImpl.getSearchContext());

		if (_isCount(searchRequestImpl)) {
			searchResponseBuilder.count(hits.getLength());

			return;
		}

		searchResponseBuilder.hits(hits);
	}

	private long _searchCount(Indexer<?> indexer, SearchContext searchContext) {
		try {
			return indexer.searchCount(searchContext);
		}
		catch (SearchException searchException) {
			throw _uncheck(searchException);
		}
	}

	private void _singleIndexerSearch(
		String singleIndexerClassName, SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		Indexer<?> indexer = indexerRegistry.getIndexer(singleIndexerClassName);

		SearchContext searchContext = searchRequestImpl.getSearchContext();

		if (_isCount(searchRequestImpl)) {
			searchResponseBuilder.count(_searchCount(indexer, searchContext));

			return;
		}

		Hits hits = search(indexer, searchContext);

		searchResponseBuilder.hits(hits);
	}

	private void _smartSearch(
		SearchRequestImpl searchRequestImpl,
		SearchResponseBuilder searchResponseBuilder) {

		List<String> indexes = searchRequestImpl.getIndexes();

		if (indexes.isEmpty()) {
			_indexerSearch(searchRequestImpl, searchResponseBuilder);
		}
		else {
			_lowLevelSearch(searchRequestImpl, searchResponseBuilder);
		}
	}

	private <T> T _transform(T t, Stream<Function<T, T>> stream) {
		return stream.reduce(
			(beforeFunction, afterFunction) -> beforeFunction.andThen(
				afterFunction)
		).orElse(
			Function.identity()
		).apply(
			t
		);
	}

	private SearchRequest _transformSearchRequest(SearchRequest searchRequest) {
		return _transform(searchRequest, _getContributors(searchRequest));
	}

	private RuntimeException _uncheck(SearchException searchException) {
		if (searchException.getCause() instanceof RuntimeException) {
			return (RuntimeException)searchException.getCause();
		}

		if (searchException.getCause() != null) {
			return new RuntimeException(searchException.getCause());
		}

		return new RuntimeException(searchException);
	}

}