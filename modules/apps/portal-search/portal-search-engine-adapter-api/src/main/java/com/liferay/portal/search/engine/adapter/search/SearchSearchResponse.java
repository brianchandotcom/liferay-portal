/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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