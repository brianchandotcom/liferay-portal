/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.search;

import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.searcher.SearchTimeValue;
import com.liferay.portal.search.stats.StatsResponse;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

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

	public long getCount() {
		return _count;
	}

	public Map<String, String> getExecutionProfile() {
		return _executionProfile;
	}

	public long getExecutionTime() {
		return _executionTime;
	}

	public String getPointInTimeId() {
		return _pointInTimeId;
	}

	public String getSearchRequestString() {
		return _searchRequestString;
	}

	public String getSearchResponseString() {
		return _searchResponseString;
	}

	public SearchTimeValue getSearchTimeValue() {
		return _searchTimeValue;
	}

	public Map<String, StatsResponse> getStatsResponseMap() {
		return Collections.unmodifiableMap(_statsResponseMap);
	}

	public boolean isTerminatedEarly() {
		return _terminatedEarly;
	}

	public boolean isTimedOut() {
		return _timedOut;
	}

	public void setCount(long count) {
		_count = count;
	}

	public void setExecutionProfile(Map<String, String> executionProfile) {
		_executionProfile = executionProfile;
	}

	public void setExecutionTime(long executionTime) {
		_executionTime = executionTime;
	}

	public void setPointInTimeId(String pointInTimeId) {
		_pointInTimeId = pointInTimeId;
	}

	public void setScrollId(String scrollId) {
		_scrollId = scrollId;
	}

	public void setSearchRequestString(String searchRequestString) {
		_searchRequestString = searchRequestString;
	}

	public void setSearchResponseString(String searchResponseString) {
		_searchResponseString = searchResponseString;
	}

	public void setSearchTimeValue(SearchTimeValue searchTimeValue) {
		_searchTimeValue = searchTimeValue;
	}

	public void setTerminatedEarly(boolean terminatedEarly) {
		_terminatedEarly = terminatedEarly;
	}

	public void setTimedOut(boolean timedOut) {
		_timedOut = timedOut;
	}

	private final Map<String, AggregationResult> _aggregationResultsMap =
		new LinkedHashMap<>();
	private long _count;
	private Map<String, String> _executionProfile;
	private long _executionTime;
	private String _pointInTimeId;
	private String _scrollId;
	private String _searchRequestString;
	private String _searchResponseString;
	private SearchTimeValue _searchTimeValue;
	private final Map<String, StatsResponse> _statsResponseMap =
		new LinkedHashMap<>();
	private boolean _terminatedEarly;
	private boolean _timedOut;

}