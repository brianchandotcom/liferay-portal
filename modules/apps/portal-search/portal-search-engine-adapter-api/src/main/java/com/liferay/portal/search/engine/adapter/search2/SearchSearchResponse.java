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

import com.liferay.portal.search.hits.SearchHits;

/**
 * @author Michael C. Han
 */
@ProviderType
public class SearchSearchResponse extends BaseSearchResponse {

	public String getScrollId() {
		return _scrollId;
	}

	public SearchHits getSearchHits() {
		return _searchHits;
	}

	public void setScrollId(String scrollId) {
		_scrollId = scrollId;
	}

	public void setSearchHits(SearchHits searchHits) {
		_searchHits = searchHits;
	}

	private String _scrollId;
	private SearchHits _searchHits;

}