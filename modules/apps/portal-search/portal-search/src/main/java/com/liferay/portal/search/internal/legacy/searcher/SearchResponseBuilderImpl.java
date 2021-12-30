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

package com.liferay.portal.search.internal.legacy.searcher;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.groupby.GroupByResponse;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.internal.searcher.SearchResponseImpl;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.SearchResponseBuilder;
import com.liferay.portal.search.searcher.SearchTimeValue;
import com.liferay.portal.search.stats.StatsResponse;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author André de Oliveira
 */
public class SearchResponseBuilderImpl implements SearchResponseBuilder {

	public SearchResponseBuilderImpl(SearchContext searchContext) {
		_searchContext = searchContext;
	}

	@Override
	public SearchResponseBuilder addFederatedSearchResponse(
		SearchResponse searchResponse) {

		_withSearchResponseImpl(
			searchResponseImpl -> searchResponseImpl.addFederatedSearchResponse(
				searchResponse));

		return this;
	}

	@Override
	public SearchResponseBuilder aggregationResultsMap(
		Map<String, AggregationResult> aggregationResultsMap) {

		_withSearchResponseImpl(
			searchResponseImpl -> searchResponseImpl.setAggregationResultsMap(
				aggregationResultsMap));

		return this;
	}

	@Override
	public SearchResponse build() {
		return _withSearchResponseGet(Function.identity());
	}

	@Override
	public SearchResponseBuilder count(long count) {
		_withSearchResponseImpl(
			searchResponseImpl -> searchResponseImpl.setCount(count));

		return this;
	}

	@Override
	public SearchResponseBuilder federatedSearchKey(String key) {
		_withSearchResponseImpl(
			searchResponseImpl -> searchResponseImpl.setFederatedSearchKey(
				key));

		return this;
	}

	@Override
	public SearchResponseBuilder groupByResponses(
		List<GroupByResponse> groupByResponses) {

		_withSearchResponseImpl(
			searchResponseImpl -> searchResponseImpl.setGroupByResponses(
				groupByResponses));

		return this;
	}

	@Override
	public SearchResponseBuilder hits(Hits hits) {
		_withSearchResponseImpl(
			searchResponseImpl -> searchResponseImpl.setHits(hits));

		return this;
	}

	@Override
	public SearchResponseBuilder request(SearchRequest searchRequest) {
		_withSearchResponseImpl(
			searchResponseImpl -> searchResponseImpl.setRequest(searchRequest));

		return this;
	}

	@Override
	public SearchResponseBuilder requestString(String requestString) {
		_searchContext.setAttribute(_QUERY_STRING, requestString);

		_withSearchResponseImpl(
			searchResponseImpl -> searchResponseImpl.setRequestString(
				requestString));

		return this;
	}

	@Override
	public SearchResponseBuilder responseString(String responseString) {
		_withSearchResponseImpl(
			searchResponseImpl -> searchResponseImpl.setResponseString(
				responseString));

		return this;
	}

	@Override
	public SearchResponseBuilder searchHits(SearchHits searchHits) {
		_withSearchResponseImpl(
			searchResponseImpl -> searchResponseImpl.setSearchHits(searchHits));

		return this;
	}

	@Override
	public SearchResponseBuilder searchTimeValue(
		SearchTimeValue searchTimeValue) {

		_withSearchResponseImpl(
			searchResponseImpl -> searchResponseImpl.setSearchTimeValue(
				searchTimeValue));

		return this;
	}

	@Override
	public SearchResponseBuilder statsResponseMap(
		Map<String, StatsResponse> map) {

		_withSearchResponseImpl(
			searchResponseImpl -> searchResponseImpl.setStatsResponseMap(map));

		return this;
	}

	protected static <T extends Serializable> T setAttribute(
		SearchContext searchContext, String key, T value) {

		searchContext.setAttribute(key, value);

		return value;
	}

	private SearchResponseImpl _getSearchResponseImpl(
		SearchContext searchContext) {

		return Optional.ofNullable(
			(SearchResponseImpl)searchContext.getAttribute(
				_SEARCH_CONTEXT_KEY_SEARCH_RESPONSE)
		).orElseGet(
			() -> setAttribute(
				searchContext, _SEARCH_CONTEXT_KEY_SEARCH_RESPONSE,
				new SearchResponseImpl(searchContext))
		);
	}

	private <T> T _withSearchResponseGet(Function<SearchResponse, T> function) {
		synchronized (_searchContext) {
			return function.apply(_getSearchResponseImpl(_searchContext));
		}
	}

	private void _withSearchResponseImpl(
		Consumer<SearchResponseImpl> consumer) {

		synchronized (_searchContext) {
			consumer.accept(_getSearchResponseImpl(_searchContext));
		}
	}

	private static final String _QUERY_STRING = "queryString";

	private static final String _SEARCH_CONTEXT_KEY_SEARCH_RESPONSE =
		"search.response";

	private final SearchContext _searchContext;

}