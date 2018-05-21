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
import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.kernel.search.facet.Facet;

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

	public Map<String, Facet> getFacets() {
		return _facets;
	}

	public GroupBy getGroupBy() {
		return _groupBy;
	}

	public int getSize() {
		return _size;
	}

	public int getStart() {
		return _start;
	}

	public Map<String, Stats> getStats() {
		return _stats;
	}

	public boolean isScoreEnabled() {
		return _scoreEnabled;
	}

	public void putAllFacets(Map<String, Facet> faects) {
		_facets.putAll(faects);
	}

	public void putAllStats(Map<String, Stats> stats) {
		if (_stats == null) {
			_stats = new HashMap<>();
		}

		_stats.putAll(stats);
	}

	public void putFacet(String fieldName, Facet facet) {
		_facets.put(fieldName, facet);
	}

	public void setGroupBy(GroupBy groupBy) {
		_groupBy = groupBy;
	}

	public void setScoreEnabled(boolean scoreEnabled) {
		_scoreEnabled = scoreEnabled;
	}

	public void setSize(int size) {
		_size = size;
	}

	public void setStart(int start) {
		_start = start;
	}

	public void setStats(Map<String, Stats> stats) {
		_stats = stats;
	}

	private final Map<String, Facet> _facets = new HashMap<>();
	private GroupBy _groupBy;
	private boolean _scoreEnabled;
	private int _size;
	private int _start;
	private Map<String, Stats> _stats;

}