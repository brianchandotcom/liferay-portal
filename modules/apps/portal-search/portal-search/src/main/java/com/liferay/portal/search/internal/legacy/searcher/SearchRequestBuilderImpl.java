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

package com.liferay.portal.search.internal.legacy.searcher;

import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
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
public class SearchRequestBuilderImpl implements SearchRequestBuilder {

	public SearchRequestBuilderImpl(SearchContext searchContext) {
		_searchContext = searchContext;
	}

	@Override
	public void addAggregation(Aggregation aggregation) {
		Map<String, Aggregation> map = getAggregationsMap();

		map.put(aggregation.getName(), aggregation);
	}

	@Override
	public void addPipelineAggregation(
		PipelineAggregation pipelineAggregation) {

		Map<String, PipelineAggregation> map = getPipelineAggregationsMap();

		map.put(pipelineAggregation.getName(), pipelineAggregation);
	}

	@Override
	public SearchRequestBuilder addSelectedFieldNames(
		String... selectedFieldNames) {

		QueryConfig queryConfig = _searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(selectedFieldNames);

		return this;
	}

	@Override
	public SearchRequest build() {
		return new SearchRequestImpl();
	}

	@Override
	public SearchRequestBuilder explain(boolean explain) {
		_searchContext.setAttribute(_EXPLAIN, Boolean.valueOf(explain));

		return this;
	}

	@Override
	public SearchRequestBuilder includeResponseString(
		boolean includeResponseString) {

		_searchContext.setAttribute(
			_INCLUDE_RESPONSE_STRING, Boolean.valueOf(includeResponseString));

		return this;
	}

	@Override
	public SearchRequestBuilder rescoreQuery(Query rescoreQuery) {
		_searchContext.setAttribute(_RESCORE_QUERY, rescoreQuery);

		return this;
	}

	@Override
	public SearchRequestBuilder statsRequests(StatsRequest... statsRequests) {
		_searchContext.setAttribute(
			_STATS_REQUESTS, new ArrayList<>(Arrays.asList(statsRequests)));

		return this;
	}

	public class SearchRequestImpl implements SearchRequest {

		@Override
		public Map<String, Aggregation> getAggregationsMap() {
			Map<String, Aggregation> map =
				(Map<String, Aggregation>)_searchContext.getAttribute(
					_AGGREGATIONS_MAP);

			if (map == null) {
				return Collections.emptyMap();
			}

			return map;
		}

		@Override
		public Map<String, PipelineAggregation> getPipelineAggregationsMap() {
			Map<String, PipelineAggregation> map =
				(Map<String, PipelineAggregation>)_searchContext.getAttribute(
					_PIPELINE_AGGREGATIONS_MAP);

			if (map == null) {
				return Collections.emptyMap();
			}

			return map;
		}

		@Override
		public Query getRescoreQuery() {
			return (Query)_searchContext.getAttribute(_RESCORE_QUERY);
		}

		public SearchContext getSearchContext() {
			return _searchContext;
		}

		@Override
		public List<StatsRequest> getStatsRequests() {
			Serializable serializable = _searchContext.getAttribute(
				_STATS_REQUESTS);

			if (serializable != null) {
				return (List<StatsRequest>)serializable;
			}

			return Collections.emptyList();
		}

		@Override
		public boolean isExplain() {
			return GetterUtil.getBoolean(_searchContext.getAttribute(_EXPLAIN));
		}

		@Override
		public boolean isIncludeResponseString() {
			return GetterUtil.getBoolean(
				_searchContext.getAttribute(_INCLUDE_RESPONSE_STRING));
		}

	}

	protected Map<String, Aggregation> getAggregationsMap() {
		synchronized (_searchContext) {
			LinkedHashMap<String, Aggregation> linkedHashMap =
				(LinkedHashMap<String, Aggregation>)_searchContext.getAttribute(
					_AGGREGATIONS_MAP);

			if (linkedHashMap != null) {
				return linkedHashMap;
			}

			linkedHashMap = new LinkedHashMap<>();

			_searchContext.setAttribute(_AGGREGATIONS_MAP, linkedHashMap);

			return linkedHashMap;
		}
	}

	protected Map<String, PipelineAggregation> getPipelineAggregationsMap() {
		synchronized (_searchContext) {
			LinkedHashMap<String, PipelineAggregation> linkedHashMap =
				(LinkedHashMap<String, PipelineAggregation>)
					_searchContext.getAttribute(_PIPELINE_AGGREGATIONS_MAP);

			if (linkedHashMap != null) {
				return linkedHashMap;
			}

			linkedHashMap = new LinkedHashMap<>();

			_searchContext.setAttribute(
				_PIPELINE_AGGREGATIONS_MAP, linkedHashMap);

			return linkedHashMap;
		}
	}

	private static final String _AGGREGATIONS_MAP = "aggregations.map";

	private static final String _EXPLAIN = "explain";

	private static final String _INCLUDE_RESPONSE_STRING =
		"include.response.string";

	private static final String _PIPELINE_AGGREGATIONS_MAP =
		"pipeline.aggregations.map";

	private static final String _RESCORE_QUERY = "rescore.query";

	private static final String _STATS_REQUESTS = "stats.requests";

	private final SearchContext _searchContext;

}