/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.hits;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class SearchHits implements Serializable {

	public void addSearchHits(Collection<SearchHit> searchHits) {
		searchHits.forEach(_searchHits::add);
	}

	public float getMaxScore() {
		return _maxScore;
	}

	public List<SearchHit> getSearchHits() {
		return _searchHits;
	}

	public long getSearchTime() {
		return _searchTime;
	}

	public long getTotalHits() {
		return _totalHits;
	}

	public static class Builder implements SearchHitsBuilder {

		@Override
		public SearchHitsBuilder addSearchHit(SearchHit searchHit) {
			_searchHits.addSearchHit(searchHit);

			return this;
		}

		@Override
		public SearchHitsBuilder addSearchHits(
			Collection<SearchHit> searchHits) {

			_searchHits.addSearchHits(searchHits);

			return this;
		}

		@Override
		public SearchHits build() {
			return new SearchHits(_searchHits);
		}

		@Override
		public SearchHitsBuilder maxScore(float maxScore) {
			_searchHits._setMaxScore(maxScore);

			return this;
		}

		@Override
		public SearchHitsBuilder searchTime(long searchTime) {
			_searchHits._setSearchTime(searchTime);

			return this;
		}

		@Override
		public SearchHitsBuilder totalHits(long totalHits) {
			_searchHits._setTotalHits(totalHits);

			return this;
		}

		private final SearchHits _searchHits = new SearchHits();

	}

	protected SearchHits() {
	}

	protected SearchHits(SearchHits searchHits) {
		_maxScore = searchHits._maxScore;
		_searchTime = searchHits._searchTime;
		_totalHits = searchHits._totalHits;

		_searchHits.addAll(searchHits._searchHits);
	}

	protected void addSearchHit(SearchHit searchHit) {
		_searchHits.add(searchHit);
	}

	private void _setMaxScore(float maxScore) {
		_maxScore = maxScore;
	}

	private void _setSearchTime(long searchTime) {
		_searchTime = searchTime;
	}

	private void _setTotalHits(long totalHits) {
		_totalHits = totalHits;
	}

	private float _maxScore;
	private final List<SearchHit> _searchHits = new ArrayList<>();
	private long _searchTime;
	private long _totalHits;

}