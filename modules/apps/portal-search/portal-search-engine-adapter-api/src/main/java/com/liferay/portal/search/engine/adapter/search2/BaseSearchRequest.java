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

package com.liferay.portal.search.engine.adapter.search2;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.stats.StatsRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class BaseSearchRequest {

	public void addAggregation(Aggregation aggregation) {
		_aggregationsMap.put(aggregation.getName(), aggregation);
	}

	public void addIndexBoost(String index, float boost) {
		_indexBoosts.put(index, boost);
	}

	public void addPipelineAggregation(
		PipelineAggregation pipelineAggregation) {

		_pipelineAggregationsMap.put(
			pipelineAggregation.getName(), pipelineAggregation);
	}

	public Map<String, Aggregation> getAggregationsMap() {
		return Collections.unmodifiableMap(_aggregationsMap);
	}

	public Boolean getExplain() {
		return _explain;
	}

	public Map<String, Float> getIndexBoosts() {
		return Collections.unmodifiableMap(_indexBoosts);
	}

	public String[] getIndexNames() {
		return _indexNames;
	}

	public Float getMinimumScore() {
		return _minimumScore;
	}

	public Map<String, PipelineAggregation> getPipelineAggregationsMap() {
		return Collections.unmodifiableMap(_pipelineAggregationsMap);
	}

	public Query getPostFilterQuery() {
		return _postFilterQuery;
	}

	public Query getQuery() {
		return _query;
	}

	public Boolean getRequestCache() {
		return _requestCache;
	}

	public Query getRescoreQuery() {
		return _rescoreQuery;
	}

	public List<StatsRequest> getStatsRequests() {
		return Collections.unmodifiableList(_statsRequests);
	}

	public Long getTimeoutInMilliseconds() {
		return _timeoutInMilliseconds;
	}

	public Boolean getTrackTotalHits() {
		return _trackTotalHits;
	}

	public String[] getTypes() {
		return _types;
	}

	public boolean isIncludeRequestString() {
		return _includeRequestString;
	}

	public boolean isIncludeResponseString() {
		return _includeResponseString;
	}

	public void setExplain(Boolean explain) {
		_explain = explain;
	}

	public void setIncludeRequestString(boolean includeRequestString) {
		_includeRequestString = includeRequestString;
	}

	public void setIncludeResponseString(boolean includeResponseString) {
		_includeResponseString = includeResponseString;
	}

	public void setIndexNames(String[] indexNames) {
		_indexNames = indexNames;
	}

	public void setMinimumScore(Float minimumScore) {
		_minimumScore = minimumScore;
	}

	public void setPostFilterQuery(Query postFilterQuery) {
		_postFilterQuery = postFilterQuery;
	}

	public void setQuery(Query query) {
		_query = query;
	}

	public void setRequestCache(Boolean requestCache) {
		_requestCache = requestCache;
	}

	public void setRescoreQuery(Query rescoreQuery) {
		_rescoreQuery = rescoreQuery;
	}

	public void setTimeoutInMilliseconds(Long timeoutInMilliseconds) {
		_timeoutInMilliseconds = timeoutInMilliseconds;
	}

	public void setTrackTotalHits(Boolean trackTotalHits) {
		_trackTotalHits = trackTotalHits;
	}

	public void setTypes(String... types) {
		_types = types;
	}

	private final Map<String, Aggregation> _aggregationsMap =
		new LinkedHashMap<>();
	private Boolean _explain;
	private boolean _includeRequestString;
	private boolean _includeResponseString;
	private final Map<String, Float> _indexBoosts = new HashMap<>();
	private String[] _indexNames;
	private Float _minimumScore;
	private final Map<String, PipelineAggregation> _pipelineAggregationsMap =
		new LinkedHashMap<>();
	private Query _postFilterQuery;
	private Query _query;
	private Boolean _requestCache;
	private Query _rescoreQuery;
	private final List<StatsRequest> _statsRequests = Collections.emptyList();
	private Long _timeoutInMilliseconds;
	private Boolean _trackTotalHits;
	private String[] _types;

}