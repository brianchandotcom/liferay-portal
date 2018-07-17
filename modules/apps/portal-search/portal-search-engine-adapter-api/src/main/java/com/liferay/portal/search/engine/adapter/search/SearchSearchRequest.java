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

package com.liferay.portal.search.engine.adapter.search;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.Stats;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dylan Rebelak
 */
@ProviderType
public class SearchSearchRequest
	extends BaseSearchRequest<SearchSearchResponse> {

	@Override
	public SearchSearchResponse accept(
		SearchRequestExecutor searchRequestExecutor) {

		return searchRequestExecutor.executeSearchRequest(this);
	}

	public GroupBy getGroupBy() {
		return _groupBy;
	}

	public String getPreference() {
		return _preference;
	}

	public QueryConfig getQueryConfig() {
		return _queryConfig;
	}

	public int getSize() {
		return _size;
	}

	public Sort[] getSorts() {
		return _sorts;
	}

	public int getStart() {
		return _start;
	}

	public Map<String, Stats> getStats() {
		return _stats;
	}

	public boolean isLuceneSyntax() {
		return _luceneSyntax;
	}

	public boolean isScoreEnabled() {
		return _queryConfig.isScoreEnabled();
	}

	public void putAllStats(Map<String, Stats> stats) {
		if (_stats == null) {
			_stats = new HashMap<>();
		}

		_stats.putAll(stats);
	}

	public void setGroupBy(GroupBy groupBy) {
		_groupBy = groupBy;
	}

	public void setLuceneSyntax(boolean luceneSyntax) {
		_luceneSyntax = luceneSyntax;
	}

	public void setPreference(String preference) {
		_preference = preference;
	}

	public void setScoreEnabled(boolean scoreEnabled) {
		_queryConfig.setScoreEnabled(scoreEnabled);
	}

	public void setSize(int size) {
		_size = size;
	}

	public void setSorts(Sort[] sorts) {
		_sorts = sorts;
	}

	public void setStart(int start) {
		_start = start;
	}

	public void setStats(Map<String, Stats> stats) {
		_stats = stats;
	}

	private GroupBy _groupBy;
	private boolean _luceneSyntax;
	private String _preference;
	private final QueryConfig _queryConfig = new QueryConfig();
	private boolean _scoreEnabled;
	private int _size;
	private Sort[] _sorts;
	private int _start;
	private Map<String, Stats> _stats;

}