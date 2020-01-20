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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.groupby.GroupByResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.hits.SearchHitsBuilder;
import com.liferay.portal.search.hits.SearchHitsBuilderFactory;
import com.liferay.portal.search.internal.hits.SearchHitsBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.searcher.FacetContextImpl;
import com.liferay.portal.search.searcher.FacetContext;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.stats.StatsResponse;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class SearchResponseImpl implements SearchResponse, Serializable {

	public SearchResponseImpl(SearchContext searchContext) {
		_facetContextImpl = new FacetContextImpl(searchContext);
		_searchContext = searchContext;
	}

	public void addFederatedSearchResponse(SearchResponse searchResponse) {
		_federatedSearchResponsesMap.put(
			searchResponse.getFederatedSearchKey(), searchResponse);
	}

	@Override
	public AggregationResult getAggregationResult(String name) {
		return _aggregationResultsMap.get(name);
	}

	@Override
	public Map<String, AggregationResult> getAggregationResultsMap() {
		return Collections.unmodifiableMap(_aggregationResultsMap);
	}

	@Override
	public long getCount() {
		return _count;
	}

	@Override
	public List<com.liferay.portal.kernel.search.Document> getDocuments71() {
		if (_hits == null) {
			return Collections.emptyList();
		}

		return Arrays.asList(_hits.getDocs());
	}

	@Override
	public Stream<Document> getDocumentsStream() {
		if (_searchHits == null) {
			return Stream.empty();
		}

		List<SearchHit> list = _searchHits.getSearchHits();

		return list.stream(
		).map(
			SearchHit::getDocument
		);
	}

	@Override
	public String getFederatedSearchKey() {
		return _federatedSearchKey;
	}

	@Override
	public SearchResponse getFederatedSearchResponse(String key) {
		if (Validator.isBlank(key)) {
			return this;
		}

		return _federatedSearchResponsesMap.get(key);
	}

	@Override
	public Stream<SearchResponse> getFederatedSearchResponsesStream() {
		Collection<SearchResponse> searchResponses =
			_federatedSearchResponsesMap.values();

		return searchResponses.stream();
	}

	@Override
	public List<GroupByResponse> getGroupByResponses() {
		return Collections.unmodifiableList(_groupByResponses);
	}

	@Override
	public SearchRequest getRequest() {
		return _searchRequest;
	}

	@Override
	public String getRequestString() {
		return _requestString;
	}

	@Override
	public String getResponseString() {
		return _responseString;
	}

	@Override
	public SearchHits getSearchHits() {
		if (_searchHits == null) {
			SearchHitsBuilder searchHitsBuilder =
				_searchHitsBuilderFactory.getSearchHitsBuilder();

			return searchHitsBuilder.build();
		}

		return _searchHits;
	}

	@Override
	public Map<String, StatsResponse> getStatsResponseMap() {
		return Collections.unmodifiableMap(_statsResponseMap);
	}

	@Override
	public int getTotalHits() {
		if (_hits == null) {
			return 0;
		}

		return _hits.getLength();
	}

	public void setAggregationResultsMap(
		Map<String, AggregationResult> aggregationResultsMap) {

		_aggregationResultsMap.clear();

		_aggregationResultsMap.putAll(aggregationResultsMap);
	}

	public void setCount(long count) {
		_count = count;
	}

	public void setFederatedSearchKey(String federatedSearchKey) {
		_federatedSearchKey = federatedSearchKey;
	}

	public void setGroupByResponses(List<GroupByResponse> groupByResponses) {
		_groupByResponses.clear();

		_groupByResponses.addAll(groupByResponses);
	}

	public void setHits(Hits hits) {
		_hits = hits;
	}

	public void setRequest(SearchRequest searchRequest) {
		_searchRequest = searchRequest;
	}

	public void setRequestString(String requestString) {
		_requestString = GetterUtil.getString(requestString);
	}

	public void setResponseString(String responseString) {
		_responseString = GetterUtil.getString(responseString);
	}

	public void setSearchHits(SearchHits searchHits) {
		_searchHits = searchHits;
	}

	public void setStatsResponseMap(Map<String, StatsResponse> map) {
		_statsResponseMap.clear();

		_statsResponseMap.putAll(map);
	}

	@Override
	public void withFacetContext(Consumer<FacetContext> facetContextConsumer) {
		facetContextConsumer.accept(_facetContextImpl);
	}

	@Override
	public <T> T withFacetContextGet(
		Function<FacetContext, T> facetContextFunction) {

		return facetContextFunction.apply(_facetContextImpl);
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

	private final Map<String, AggregationResult> _aggregationResultsMap =
		new LinkedHashMap<>();
	private long _count;
	private final FacetContextImpl _facetContextImpl;
	private String _federatedSearchKey = StringPool.BLANK;
	private final Map<String, SearchResponse> _federatedSearchResponsesMap =
		new LinkedHashMap<>();
	private final List<GroupByResponse> _groupByResponses = new ArrayList<>();
	private Hits _hits;
	private String _requestString = StringPool.BLANK;
	private String _responseString = StringPool.BLANK;
	private final SearchContext _searchContext;
	private SearchHits _searchHits;
	private SearchHitsBuilderFactory _searchHitsBuilderFactory =
		new SearchHitsBuilderFactoryImpl();
	private SearchRequest _searchRequest;
	private final Map<String, StatsResponse> _statsResponseMap =
		new LinkedHashMap<>();

}