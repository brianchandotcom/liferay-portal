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

import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.stats.StatsResponse;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class BaseSearchResponse implements SearchResponse {

	public void addAggregationResult(AggregationResult aggregationResult) {
		_aggregationResultsMap.put(
			aggregationResult.getName(), aggregationResult);
	}

	public void addStatsResponse(StatsResponse statsResponse) {
		_statsResponseMap.put(statsResponse.getField(), statsResponse);
	}

	public Map<String, AggregationResult> getAggregationResultsMap() {
		return Collections.unmodifiableMap(_aggregationResultsMap);
	}

	public Map<String, String> getExecutionProfile() {
		return _executionProfile;
	}

	public long getExecutionTime() {
		return _executionTime;
	}

	public String getSearchRequestString() {
		return _searchRequestString;
	}

	public String getSearchResponseString() {
		return _searchResponseString;
	}

	public boolean isTerminatedEarly() {
		return _terminatedEarly;
	}

	public boolean isTimedOut() {
		return _timedOut;
	}

	public void setExecutionProfile(Map<String, String> executionProfile) {
		_executionProfile = executionProfile;
	}

	public void setExecutionTime(long executionTime) {
		_executionTime = executionTime;
	}

	public void setSearchRequestString(String searchRequestString) {
		_searchRequestString = searchRequestString;
	}

	public void setSearchResponseString(String searchResponseString) {
		_searchResponseString = searchResponseString;
	}

	public void setTerminatedEarly(boolean terminatedEarly) {
		_terminatedEarly = terminatedEarly;
	}

	public void setTimedOut(boolean timedOut) {
		_timedOut = timedOut;
	}

	private final Map<String, AggregationResult> _aggregationResultsMap =
		new LinkedHashMap<>();
	private Map<String, String> _executionProfile;
	private long _executionTime;
	private String _searchRequestString;
	private String _searchResponseString;
	private final Map<String, StatsResponse> _statsResponseMap =
		new LinkedHashMap<>();
	private boolean _terminatedEarly;
	private boolean _timedOut;

}