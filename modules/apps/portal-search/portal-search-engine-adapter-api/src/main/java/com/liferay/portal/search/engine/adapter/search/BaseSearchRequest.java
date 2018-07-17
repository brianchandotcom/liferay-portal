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

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.filter.Filter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class BaseSearchRequest<T extends SearchResponse>
	implements SearchRequest {

	@Override
	public abstract T accept(SearchRequestExecutor searchRequestExecutor);

	public Map<String, Facet> getFacets() {
		return _facets;
	}

	public String[] getIndexNames() {
		return _indexNames;
	}

	public float getMinimumScore() {
		return _minimumScore;
	}

	public Filter getPostFilter() {
		return _postFilter;
	}

	public Query getQuery() {
		return _query;
	}

	public long getTimeoutInMilliseconds() {
		return _timeoutInMilliseconds;
	}

	public boolean isBasicFacetSelection() {
		return _basicFacetSelection;
	}

	public boolean isRequestCache() {
		return _requestCache;
	}

	public boolean isTrackTotalHits() {
		return _trackTotalHits;
	}

	public void putAllFacets(Map<String, Facet> faects) {
		_facets.putAll(faects);
	}

	public void putFacet(String fieldName, Facet facet) {
		_facets.put(fieldName, facet);
	}

	public void setBasicFacetSelection(boolean basicFacetSelection) {
		_basicFacetSelection = basicFacetSelection;
	}

	public void setIndexNames(String[] indexNames) {
		_indexNames = indexNames;
	}

	public void setMinimumScore(float minimumScore) {
		_minimumScore = minimumScore;
	}

	public void setPostFilter(Filter postFilter) {
		_postFilter = postFilter;
	}

	public void setQuery(Query query) {
		_query = query;
	}

	public void setRequestCache(boolean requestCache) {
		_requestCache = requestCache;
	}

	public void setTimeoutInMilliseconds(long timeoutInMilliseconds) {
		_timeoutInMilliseconds = timeoutInMilliseconds;
	}

	public void setTrackTotalHits(boolean trackTotalHits) {
		_trackTotalHits = trackTotalHits;
	}

	private boolean _basicFacetSelection;
	private final Map<String, Facet> _facets = new HashMap<>();
	private String[] _indexNames;
	private float _minimumScore;
	private Filter _postFilter;
	private Query _query;
	private boolean _requestCache;
	private long _timeoutInMilliseconds;
	private boolean _trackTotalHits = true;

}