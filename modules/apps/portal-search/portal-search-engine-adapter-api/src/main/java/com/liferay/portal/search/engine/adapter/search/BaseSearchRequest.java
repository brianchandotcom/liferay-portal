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

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.filter.ComplexQueryPart;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.rescore.Rescore;
import com.liferay.portal.search.stats.StatsRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class BaseSearchRequest {

	public void addAggregation(Aggregation aggregation) {
		_aggregationsMap.put(aggregation.getName(), aggregation);
	}

	public void addComplexQueryParts(
		Collection<ComplexQueryPart> complexQueryParts) {

		_complexQueryParts.addAll(complexQueryParts);
	}

	public void addIndexBoost(String index, float boost) {
		_indexBoosts.put(index, boost);
	}

	public void addPipelineAggregation(
		PipelineAggregation pipelineAggregation) {

		_pipelineAggregationsMap.put(
			pipelineAggregation.getName(), pipelineAggregation);
	}

	public Map<String, Aggregation> getAggregationsMap() {
		return Collections.unmodifiableMap(_aggregationsMap);
	}

	public List<ComplexQueryPart> getComplexQueryParts() {
		return Collections.unmodifiableList(_complexQueryParts);
	}

	public Boolean getExplain() {
		return _explain;
	}

	public Map<String, Facet> getFacets() {
		return _facets;
	}

	public Map<String, Float> getIndexBoosts() {
		return Collections.unmodifiableMap(_indexBoosts);
	}

	public String[] getIndexNames() {
		return _indexNames;
	}

	public Float getMinimumScore() {
		return _minimumScore;
	}

	public Map<String, PipelineAggregation> getPipelineAggregationsMap() {
		return Collections.unmodifiableMap(_pipelineAggregationsMap);
	}

	public Filter getPostFilter() {
		return _postFilter;
	}

	public Query getPostFilterQuery() {
		return _postFilterQuery;
	}

	public Query getQuery() {
		return _query;
	}

	public com.liferay.portal.kernel.search.Query getQuery71() {
		return _legacyQuery;
	}

	public Boolean getRequestCache() {
		return _requestCache;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getRescores()}
	 */
	@Deprecated
	public Query getRescoreQuery() {
		return _rescoreQuery;
	}

	public List<Rescore> getRescores() {
		return _rescores;
	}

	public List<StatsRequest> getStatsRequests() {
		return Collections.unmodifiableList(_statsRequests);
	}

	public Long getTimeoutInMilliseconds() {
		return _timeoutInMilliseconds;
	}

	public Boolean getTrackTotalHits() {
		return _trackTotalHits;
	}

	public String[] getTypes() {
		return _types;
	}

	public boolean isBasicFacetSelection() {
		return _basicFacetSelection;
	}

	public boolean isExplain() {
		if (_explain != null) {
			return _explain;
		}

		return false;
	}

	public boolean isIncludeResponseString() {
		return _includeResponseString;
	}

	public boolean isRequestCache() {
		if (_requestCache != null) {
			return _requestCache;
		}

		return false;
	}

	public boolean isTrackTotalHits() {
		if (_trackTotalHits != null) {
			return _trackTotalHits;
		}

		return false;
	}

	public void putAllFacets(Map<String, Facet> facets) {
		_facets.putAll(facets);
	}

	public void putFacet(String fieldName, Facet facet) {
		_facets.put(fieldName, facet);
	}

	public void setBasicFacetSelection(boolean basicFacetSelection) {
		_basicFacetSelection = basicFacetSelection;
	}

	public void setExplain(Boolean explain) {
		_explain = explain;
	}

	public void setIncludeResponseString(boolean includeResponseString) {
		_includeResponseString = includeResponseString;
	}

	public void setIndexNames(String... indexNames) {
		_indexNames = indexNames;
	}

	public void setMinimumScore(Float minimumScore) {
		_minimumScore = minimumScore;
	}

	public void setPostFilter(Filter postFilter) {
		_postFilter = postFilter;
	}

	public void setPostFilterQuery(Query postFilterQuery) {
		_postFilterQuery = postFilterQuery;
	}

	public void setQuery(com.liferay.portal.kernel.search.Query legacyQuery) {
		_legacyQuery = legacyQuery;
	}

	public void setQuery(Query query) {
		_query = query;
	}

	public void setRequestCache(Boolean requestCache) {
		_requestCache = requestCache;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setRescores(List)}
	 */
	@Deprecated
	public void setRescoreQuery(Query rescoreQuery) {
		_rescoreQuery = rescoreQuery;
	}

	public void setRescores(List<Rescore> rescores) {
		_rescores = rescores;
	}

	public void setStatsRequests(Collection<StatsRequest> statsRequests) {
		_statsRequests = new ArrayList<>(statsRequests);
	}

	public void setTimeoutInMilliseconds(Long timeoutInMilliseconds) {
		_timeoutInMilliseconds = timeoutInMilliseconds;
	}

	public void setTrackTotalHits(Boolean trackTotalHits) {
		_trackTotalHits = trackTotalHits;
	}

	public void setTypes(String... types) {
		_types = types;
	}

	private final Map<String, Aggregation> _aggregationsMap =
		new LinkedHashMap<>();
	private boolean _basicFacetSelection;
	private final List<ComplexQueryPart> _complexQueryParts = new ArrayList<>();
	private Boolean _explain;
	private final Map<String, Facet> _facets = new LinkedHashMap<>();
	private boolean _includeResponseString;
	private final Map<String, Float> _indexBoosts = new LinkedHashMap<>();
	private String[] _indexNames;
	private com.liferay.portal.kernel.search.Query _legacyQuery;
	private Float _minimumScore;
	private final Map<String, PipelineAggregation> _pipelineAggregationsMap =
		new LinkedHashMap<>();
	private Filter _postFilter;
	private Query _postFilterQuery;
	private Query _query;
	private Boolean _requestCache;
	private Query _rescoreQuery;
	private List<Rescore> _rescores;
	private List<StatsRequest> _statsRequests = Collections.emptyList();
	private Long _timeoutInMilliseconds;
	private Boolean _trackTotalHits;
	private String[] _types;

}