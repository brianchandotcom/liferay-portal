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

package com.liferay.portal.search.internal.searcher;

import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.filter.ComplexQueryPart;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.stats.StatsRequest;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class SearchRequestImpl implements SearchRequest, Serializable {

	public static boolean isBasicFacetSelection(SearchContext searchContext) {
		return GetterUtil.getBoolean(
			searchContext.getAttribute(
				SearchContextAttributes.ATTRIBUTE_KEY_BASIC_FACET_SELECTION));
	}

	public SearchRequestImpl(SearchContext searchContext) {
		_searchContext = searchContext;
	}

	public void addAggregation(Aggregation aggregation) {
		_aggregationsMap.put(aggregation.getName(), aggregation);
	}

	public void addComplexQueryPart(ComplexQueryPart complexQueryPart) {
		_complexQueryParts.add(complexQueryPart);
	}

	public void addEntryClassNames(String... entryClassNames) {
		_searchContext.setEntryClassNames(entryClassNames);
	}

	public void addFederatedSearchRequest(SearchRequest searchRequest) {
		_federatedSearchRequestsMap.put(
			searchRequest.getFederatedSearchKey(), searchRequest);
	}

	public void addIndex(String index) {
		QueryConfig queryConfig = _searchContext.getQueryConfig();

		queryConfig.setSelectedIndexNames(
			ArrayUtil.append(queryConfig.getSelectedIndexNames(), index));
	}

	public void addPipelineAggregation(
		PipelineAggregation pipelineAggregation) {

		_pipelineAggregationsMap.put(
			pipelineAggregation.getName(), pipelineAggregation);
	}

	public void addSelectedFieldNames(String... selectedFieldNames) {
		QueryConfig queryConfig = _searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(selectedFieldNames);
	}

	@Override
	public Map<String, Aggregation> getAggregationsMap() {
		return Collections.unmodifiableMap(_aggregationsMap);
	}

	@Override
	public List<ComplexQueryPart> getComplexQueryParts() {
		return Collections.unmodifiableList(_complexQueryParts);
	}

	@Override
	public List<String> getEntryClassNames() {
		return Collections.unmodifiableList(
			Arrays.asList(_searchContext.getEntryClassNames()));
	}

	@Override
	public String getFederatedSearchKey() {
		return _federatedSearchKey;
	}

	@Override
	public List<SearchRequest> getFederatedSearchRequests() {
		return new ArrayList<>(_federatedSearchRequestsMap.values());
	}

	@Override
	public List<String> getIndexes() {
		QueryConfig queryConfig = _searchContext.getQueryConfig();

		return Collections.unmodifiableList(
			Arrays.asList(queryConfig.getSelectedIndexNames()));
	}

	@Override
	public List<Class<?>> getModelIndexerClasses() {
		return Collections.unmodifiableList(_modelIndexerClasses);
	}

	@Override
	public Map<String, PipelineAggregation> getPipelineAggregationsMap() {
		return Collections.unmodifiableMap(_pipelineAggregationsMap);
	}

	@Override
	public Query getPostFilterQuery() {
		return _postFilterQuery;
	}

	@Override
	public Query getQuery() {
		return _query;
	}

	@Override
	public String getQueryString() {
		return _searchContext.getKeywords();
	}

	@Override
	public Query getRescoreQuery() {
		return _rescoreQuery;
	}

	@Override
	public List<Sort> getSorts() {
		return Collections.unmodifiableList(_sorts);
	}

	@Override
	public List<StatsRequest> getStatsRequests() {
		return Collections.unmodifiableList(_statsRequests);
	}

	@Override
	public boolean isBasicFacetSelection() {
		return _basicFacetSelection;
	}

	@Override
	public boolean isEmptySearchEnabled() {
		return _emptySearchEnabled;
	}

	@Override
	public boolean isExplain() {
		return _explain;
	}

	@Override
	public boolean isIncludeResponseString() {
		return _includeResponseString;
	}

	public void setBasicFacetSelection(boolean basicFacetSelection) {
		_basicFacetSelection = basicFacetSelection;

		_searchContext.setAttribute(
			SearchContextAttributes.ATTRIBUTE_KEY_BASIC_FACET_SELECTION,
			Boolean.valueOf(basicFacetSelection));
	}

	public void setEmptySearchEnabled(boolean emptySearchEnabled) {
		_emptySearchEnabled = emptySearchEnabled;

		_searchContext.setAttribute(
			SearchContextAttributes.ATTRIBUTE_KEY_EMPTY_SEARCH,
			Boolean.valueOf(emptySearchEnabled));
	}

	public void setExplain(boolean explain) {
		_explain = explain;
	}

	public void setFederatedSearchKey(String federatedSearchKey) {
		_federatedSearchKey = federatedSearchKey;
	}

	public void setHighlightEnabled(boolean highlightEnabled) {
		QueryConfig queryConfig = _searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(highlightEnabled);
	}

	public void setHighlightFields(String... highlightFields) {
		QueryConfig queryConfig = _searchContext.getQueryConfig();

		queryConfig.setHighlightFieldNames(highlightFields);
	}

	public void setIncludeResponseString(boolean includeResponseString) {
		_includeResponseString = includeResponseString;
	}

	public void setIndexes(String... indexes) {
		QueryConfig queryConfig = _searchContext.getQueryConfig();

		queryConfig.setSelectedIndexNames(indexes);
	}

	public void setModelIndexerClasses(Class<?>... classes) {
		_modelIndexerClasses.clear();

		Collections.addAll(_modelIndexerClasses, classes);
	}

	public void setPostFilterQuery(Query query) {
		_postFilterQuery = query;
	}

	public void setQuery(Query query) {
		_query = query;
	}

	public void setQueryString(String queryString) {
		_searchContext.setKeywords(queryString);
	}

	public void setRescoreQuery(Query query) {
		_rescoreQuery = query;
	}

	public void setSelectedFieldNames(String... selectedFieldNames) {
		QueryConfig queryConfig = _searchContext.getQueryConfig();

		queryConfig.setSelectedFieldNames(selectedFieldNames);
	}

	public void setSorts(Sort... sorts) {
		_sorts.clear();

		Collections.addAll(_sorts, sorts);
	}

	public void setStatsRequests(StatsRequest... statsRequests) {
		_statsRequests.clear();

		Collections.addAll(_statsRequests, statsRequests);
	}

	protected SearchContext getSearchContext() {
		return _searchContext;
	}

	private final Map<String, Aggregation> _aggregationsMap =
		new LinkedHashMap<>();
	private boolean _basicFacetSelection;
	private final List<ComplexQueryPart> _complexQueryParts = new ArrayList<>();
	private boolean _emptySearchEnabled;
	private boolean _explain;
	private String _federatedSearchKey;
	private final Map<String, SearchRequest> _federatedSearchRequestsMap =
		new LinkedHashMap<>();
	private boolean _includeResponseString;
	private final List<Class<?>> _modelIndexerClasses = new ArrayList<>();
	private final Map<String, PipelineAggregation> _pipelineAggregationsMap =
		new LinkedHashMap<>();
	private Query _postFilterQuery;
	private Query _query;
	private Query _rescoreQuery;
	private final SearchContext _searchContext;
	private final List<Sort> _sorts = new ArrayList<>();
	private final List<StatsRequest> _statsRequests = new ArrayList<>();

}