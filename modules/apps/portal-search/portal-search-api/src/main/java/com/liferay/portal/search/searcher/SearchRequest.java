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

package com.liferay.portal.search.searcher;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.stats.StatsRequest;

import java.util.List;
import java.util.Map;

/**
 * Holds parameters to be used when performing a search.
 *
 * @author André de Oliveira
 *
 * @review
 */
@ProviderType
public interface SearchRequest {

	public Map<String, Aggregation> getAggregationsMap();

	public Map<String, PipelineAggregation> getPipelineAggregationsMap();

	/**
	 * Provides a secondary query to reorder the top documents returned.
	 *
	 * @return the rescore query
	 *
	 * @review
	 */
	public Query getRescoreQuery();

	/**
	 * Provides the metric aggregations that are to be computed for each field.
	 *
	 * @return the stats that are enabled for each field.
	 *
	 * @review
	 */
	public List<StatsRequest> getStatsRequests();

	/**
	 * Enables explanation for each hit on how its score was computed.
	 *
	 * @return whether to explain scores
	 *
	 * @review
	 */
	public boolean isExplain();

	/**
	 * Enables inclusion of the search engine's response string with results.
	 *
	 * @return whether to include the response string
	 *
	 * @review
	 */
	public boolean isIncludeResponseString();

}