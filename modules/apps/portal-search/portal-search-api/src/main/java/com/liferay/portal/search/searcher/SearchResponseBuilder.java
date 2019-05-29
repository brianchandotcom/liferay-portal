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

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.groupby.GroupByResponse;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.stats.StatsResponse;

import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Builds a search response with the results of a search. This interface's usage
 * is intended for the Liferay Search Framework only.
 *
 * @author André de Oliveira
 */
@ProviderType
public interface SearchResponseBuilder {

	public SearchResponseBuilder addFederatedSearchResponse(
		SearchResponse searchResponse);

	public SearchResponseBuilder aggregationResultsMap(
		Map<String, AggregationResult> aggregationResultsMap);

	/**
	 * Builds the search response.
	 *
	 * @return the search response
	 */
	public SearchResponse build();

	public SearchResponseBuilder count(long count);

	public SearchResponseBuilder federatedSearchKey(String key);

	/**
	 * Sets the list of top hits aggregations.
	 *
	 * @param groupByResponses the list of top hits aggregations.
	 * @return the same builder
	 *
	 * @review
	 */
	public SearchResponseBuilder groupByResponses(
		List<GroupByResponse> groupByResponses);

	public SearchResponseBuilder hits(Hits hits);

	public SearchResponseBuilder request(SearchRequest searchRequest);

	/**
	 * Sets the request string submitted to the search engine.
	 *
	 * @param  requestString the request string, as returned by the search
	 *         engine
	 * @return the search response builder
	 */
	public SearchResponseBuilder requestString(String requestString);

	/**
	 * Returns the response string from the search engine.
	 *
	 * @param  responseString the response string formatted by the search engine
	 * @return the search response builder
	 */
	public SearchResponseBuilder responseString(String responseString);

	public SearchResponseBuilder searchHits(SearchHits searchHits);

	/**
	 * Sets the map containing the metrics aggregations computed by the search
	 * engine.
	 *
	 * @param  statsResponseMap the map containing the metrics aggregations per
	 *         field
	 * @return the search response builder
	 */
	public SearchResponseBuilder statsResponseMap(
		Map<String, StatsResponse> statsResponseMap);

}