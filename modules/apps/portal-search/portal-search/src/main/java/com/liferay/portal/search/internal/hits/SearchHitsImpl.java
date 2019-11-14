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

package com.liferay.portal.search.internal.hits;

import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.hits.SearchHitsBuilder;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Michael C. Han
 */
public class SearchHitsImpl implements SearchHits, Serializable {

	public void addSearchHits(Collection<SearchHit> searchHits) {
		searchHits.forEach(_searchHits::add);
	}

	@Override
	public float getMaxScore() {
		return _maxScore;
	}

	@Override
	public List<SearchHit> getSearchHits() {
		return _searchHits;
	}

	@Override
	public long getSearchTime() {
		return _searchTime;
	}

	@Override
	public long getTotalHits() {
		return _totalHits;
	}

	protected SearchHitsImpl() {
	}

	protected SearchHitsImpl(SearchHitsImpl searchHitsImpl) {
		_maxScore = searchHitsImpl._maxScore;
		_searchTime = searchHitsImpl._searchTime;
		_totalHits = searchHitsImpl._totalHits;

		_searchHits.addAll(searchHitsImpl._searchHits);
	}

	protected void addSearchHit(SearchHit searchHit) {
		_searchHits.add(searchHit);
	}

	protected void setMaxScore(float maxScore) {
		_maxScore = maxScore;
	}

	protected void setSearchTime(long searchTime) {
		_searchTime = searchTime;
	}

	protected void setTotalHits(long totalHits) {
		_totalHits = totalHits;
	}

	protected static class Builder implements SearchHitsBuilder {

		@Override
		public SearchHitsBuilder addSearchHit(SearchHit searchHit) {
			_searchHitsImpl.addSearchHit(searchHit);

			return this;
		}

		@Override
		public SearchHitsBuilder addSearchHits(
			Collection<SearchHit> searchHits) {

			_searchHitsImpl.addSearchHits(searchHits);

			return this;
		}

		/**
		 * @deprecated As of Athanasius (7.3.x), replaced by {@link
		 *             #addSearchHits(Collection)}
		 */
		@Deprecated
		@Override
		public SearchHitsBuilder addSearchHits(
			Stream<SearchHit> searchHitStream) {

			_searchHitsImpl.addSearchHits(
				searchHitStream.collect(Collectors.toList()));

			return this;
		}

		@Override
		public SearchHits build() {
			return new SearchHitsImpl(_searchHitsImpl);
		}

		@Override
		public SearchHitsBuilder maxScore(float maxScore) {
			_searchHitsImpl.setMaxScore(maxScore);

			return this;
		}

		@Override
		public SearchHitsBuilder searchTime(long searchTime) {
			_searchHitsImpl.setSearchTime(searchTime);

			return this;
		}

		@Override
		public SearchHitsBuilder totalHits(long totalHits) {
			_searchHitsImpl.setTotalHits(totalHits);

			return this;
		}

		private final SearchHitsImpl _searchHitsImpl = new SearchHitsImpl();

	}

	private float _maxScore;
	private final List<SearchHit> _searchHits = new ArrayList<>();
	private long _searchTime;
	private long _totalHits;

}