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
		_searchHits.addAll(searchHits);
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
			_searchHits.add(searchHit);

			return this;
		}

		@Override
		public SearchHitsBuilder addSearchHits(
			Collection<SearchHit> searchHits) {

			_searchHits.addAll(searchHits);

			return this;
		}

		@Override
		public SearchHits build() {
			return new SearchHits(
				_maxScore, _searchHits, _searchTime, _totalHits);
		}

		@Override
		public SearchHitsBuilder maxScore(float maxScore) {
			_maxScore = maxScore;

			return this;
		}

		@Override
		public SearchHitsBuilder searchTime(long searchTime) {
			_searchTime = searchTime;

			return this;
		}

		@Override
		public SearchHitsBuilder totalHits(long totalHits) {
			_totalHits = totalHits;

			return this;
		}

		private float _maxScore;
		private final List<SearchHit> _searchHits = new ArrayList<>();
		private long _searchTime;
		private long _totalHits;

	}

	protected SearchHits(
		float maxScore, List<SearchHit> searchHits, long searchTime,
		long totalHits) {

		_maxScore = maxScore;
		_searchHits = searchHits;
		_searchTime = searchTime;
		_totalHits = totalHits;
	}

	private final float _maxScore;
	private final List<SearchHit> _searchHits;
	private final long _searchTime;
	private final long _totalHits;

}