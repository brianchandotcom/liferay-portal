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

package com.liferay.portal.search.hits;

import aQute.bnd.annotation.ProviderType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
@ProviderType
public class SearchHits {

	public void addSearchHit(SearchHit searchHit) {
		_searchHits.add(searchHit);
	}

	public float getMaxScore() {
		return _maxScore;
	}

	public List<SearchHit> getSearchHits() {
		return Collections.unmodifiableList(_searchHits);
	}

	public long getSearchTime() {
		return _searchTime;
	}

	public long getTotalHits() {
		return _totalHits;
	}

	public void setMaxScore(float maxScore) {
		_maxScore = maxScore;
	}

	public void setSearchTime(long searchTime) {
		_searchTime = searchTime;
	}

	public void setTotalHits(long totalHits) {
		_totalHits = totalHits;
	}

	private float _maxScore;
	private final List<SearchHit> _searchHits = new ArrayList<>();
	private long _searchTime;
	private long _totalHits;

}