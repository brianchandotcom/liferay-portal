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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.FacetContext;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.SearchResponseBuilder;
import com.liferay.portal.search.stats.StatsResponse;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class SearchResponseBuilderImpl implements SearchResponseBuilder {

	public SearchResponseBuilderImpl(SearchContext searchContext) {
		_facetContext = new FacetContextImpl(searchContext);
		_searchContext = searchContext;
	}

	@Override
	public SearchResponseBuilder aggregationResultsMap(
		Map<String, AggregationResult> aggregationResultsMap) {

		_searchContext.setAttribute(
			_SEARCH_CONTEXT_KEY_AGGREGATION_RESULTS_MAP,
			new LinkedHashMap<>(aggregationResultsMap));

		return this;
	}

	@Override
	public SearchResponse build() {
		return new SearchResponseImpl();
	}

	@Override
	public SearchResponseBuilder hits(Hits hits) {
		_hits = hits;

		return this;
	}

	@Override
	public SearchResponseBuilder request(SearchRequest searchRequest) {
		_searchRequest = searchRequest;

		return this;
	}

	@Override
	public SearchResponseBuilder requestString(String requestString) {
		_searchContext.setAttribute(_QUERY_STRING, requestString);
		_searchContext.setAttribute(
			_SEARCH_CONTEXT_KEY_REQUEST_STRING, requestString);

		return this;
	}

	@Override
	public SearchResponseBuilder responseString(String responseString) {
		_searchContext.setAttribute(
			_SEARCH_CONTEXT_KEY_RESPONSE_STRING, responseString);

		return this;
	}

	@Override
	public SearchResponseBuilder searchHits(SearchHits searchHits) {
		_searchContext.setAttribute(
			_SEARCH_CONTEXT_KEY_SEARCH_HITS, searchHits);

		return this;
	}

	@Override
	public SearchResponseBuilder statsResponseMap(
		Map<String, StatsResponse> map) {

		_searchContext.setAttribute(
			_SEARCH_CONTEXT_KEY_STATS_RESPONSE_MAP, new HashMap<>(map));

		return this;
	}

	public class SearchResponseImpl implements SearchResponse {

		@Override
		public AggregationResult getAggregationResult(String name) {
			Map<String, AggregationResult> map =
				(Map<String, AggregationResult>)_searchContext.getAttribute(
					_SEARCH_CONTEXT_KEY_AGGREGATION_RESULTS_MAP);

			if (map != null) {
				return map.get(name);
			}

			return null;
		}

		@Override
		public Map<String, AggregationResult> getAggregationResultsMap() {
			Map<String, AggregationResult> map =
				(Map<String, AggregationResult>)_searchContext.getAttribute(
					_SEARCH_CONTEXT_KEY_AGGREGATION_RESULTS_MAP);

			if (map != null) {
				return map;
			}

			return Collections.emptyMap();
		}

		@Override
		public List<com.liferay.portal.kernel.search.Document>
			getDocuments71() {

			return Arrays.asList(_hits.getDocs());
		}

		@Override
		public Stream<Document> getDocumentsStream() {
			SearchHits searchHits = getSearchHits();

			List<SearchHit> list = searchHits.getSearchHits();

			Stream<SearchHit> stream = list.stream();

			return stream.map(SearchHit::getDocument);
		}

		@Override
		public SearchRequest getRequest() {
			return _searchRequest;
		}

		@Override
		public String getRequestString() {
			return GetterUtil.getString(
				_searchContext.getAttribute(
					_SEARCH_CONTEXT_KEY_REQUEST_STRING));
		}

		@Override
		public String getResponseString() {
			return GetterUtil.getString(
				_searchContext.getAttribute(
					_SEARCH_CONTEXT_KEY_RESPONSE_STRING));
		}

		@Override
		public SearchHits getSearchHits() {
			return (SearchHits)_searchContext.getAttribute(
				_SEARCH_CONTEXT_KEY_SEARCH_HITS);
		}

		@Override
		public Map<String, StatsResponse> getStatsResponseMap() {
			Serializable serializable = _searchContext.getAttribute(
				_SEARCH_CONTEXT_KEY_STATS_RESPONSE_MAP);

			if (serializable != null) {
				return (Map<String, StatsResponse>)serializable;
			}

			return Collections.emptyMap();
		}

		@Override
		public int getTotalHits() {
			return _hits.getLength();
		}

		@Override
		public void withFacetContext(
			Consumer<FacetContext> facetContextConsumer) {

			facetContextConsumer.accept(_facetContext);
		}

		@Override
		public <T> T withFacetContextGet(
			Function<FacetContext, T> facetContextFunction) {

			return facetContextFunction.apply(_facetContext);
		}

		@Override
		public void withHits(Consumer<Hits> hitsConsumer) {
			hitsConsumer.accept(_hits);
		}

		@Override
		public <T> T withHitsGet(Function<Hits, T> hitsFunction) {
			return hitsFunction.apply(_hits);
		}

		@Override
		public void withSearchContext(
			Consumer<SearchContext> searchContextConsumer) {

			searchContextConsumer.accept(_searchContext);
		}

		@Override
		public <T> T withSearchContextGet(Function<SearchContext, T> function) {
			return function.apply(_searchContext);
		}

	}

	private static final String _QUERY_STRING = "queryString";

	private static final String _SEARCH_CONTEXT_KEY_AGGREGATION_RESULTS_MAP =
		"search.response.aggregation.results.map";

	private static final String _SEARCH_CONTEXT_KEY_REQUEST_STRING =
		"search.response.request.string";

	private static final String _SEARCH_CONTEXT_KEY_RESPONSE_STRING =
		"search.response.response.string";

	private static final String _SEARCH_CONTEXT_KEY_SEARCH_HITS =
		"search.response.search.hits";

	private static final String _SEARCH_CONTEXT_KEY_STATS_RESPONSE_MAP =
		"search.response.stats.response.map";

	private final FacetContextImpl _facetContext;
	private Hits _hits;
	private final SearchContext _searchContext;
	private SearchRequest _searchRequest;

}