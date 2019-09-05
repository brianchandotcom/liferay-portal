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

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.search.groupby.GroupByResponse;
import com.liferay.portal.search.hits.SearchHits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class SearchSearchResponse extends BaseSearchResponse {

	public void addGroupByResponse(GroupByResponse groupByResponse) {
		_groupByResponses.add(groupByResponse);
	}

	public List<GroupByResponse> getGroupByResponses() {
		return Collections.unmodifiableList(_groupByResponses);
	}

	public Hits getHits() {
		return _hits;
	}

	public String getScrollId() {
		return _scrollId;
	}

	public SearchHits getSearchHits() {
		return _searchHits;
	}

	public void setHits(Hits hits) {
		_hits = hits;
	}

	public void setScrollId(String scrollId) {
		_scrollId = scrollId;
	}

	public void setSearchHits(SearchHits searchHits) {
		_searchHits = searchHits;
	}

	private final List<GroupByResponse> _groupByResponses = new ArrayList<>();
	private Hits _hits;
	private String _scrollId;
	private SearchHits _searchHits;

}