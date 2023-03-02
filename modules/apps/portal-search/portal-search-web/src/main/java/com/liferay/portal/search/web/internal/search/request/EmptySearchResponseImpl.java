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

package com.liferay.portal.search.web.internal.search.request;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.groupby.GroupByResponse;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.FacetContext;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.SearchTimeValue;
import com.liferay.portal.search.stats.StatsResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Joshua Cords
 */
public class EmptySearchResponseImpl implements SearchResponse {

	public EmptySearchResponseImpl(SearchRequest searchRequest) {
		_searchRequest = searchRequest;
	}

	@Override
	public AggregationResult getAggregationResult(String name) {
		return null;
	}

	@Override
	public Map<String, AggregationResult> getAggregationResultsMap() {
		return null;
	}

	@Override
	public long getCount() {
		return 0;
	}

	@Override
	public List<Document> getDocuments() {
		return new ArrayList<>();
	}

	@Override
	public List<com.liferay.portal.kernel.search.Document> getDocuments71() {
		return new ArrayList<>();
	}

	@Override
	public String getFederatedSearchKey() {
		return null;
	}

	@Override
	public SearchResponse getFederatedSearchResponse(String key) {
		return this;
	}

	@Override
	public Collection<SearchResponse> getFederatedSearchResponses() {
		return null;
	}

	@Override
	public List<GroupByResponse> getGroupByResponses() {
		return null;
	}

	@Override
	public SearchRequest getRequest() {
		return _searchRequest;
	}

	@Override
	public String getRequestString() {
		return null;
	}

	@Override
	public String getResponseString() {
		return null;
	}

	@Override
	public SearchHits getSearchHits() {
		return null;
	}

	@Override
	public SearchTimeValue getSearchTimeValue() {
		return null;
	}

	@Override
	public Map<String, StatsResponse> getStatsResponseMap() {
		return null;
	}

	@Override
	public int getTotalHits() {
		return 0;
	}

	@Override
	public void withFacetContext(Consumer<FacetContext> facetContextConsumer) {
	}

	@Override
	public <T> T withFacetContextGet(
		Function<FacetContext, T> facetContextFunction) {

		return null;
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
	}

	@Override
	public <T> T withSearchContextGet(
		Function<SearchContext, T> searchContextFunction) {

		return null;
	}

	private final Hits _hits = new HitsImpl();
	private final SearchRequest _searchRequest;

}