/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	 * @param  groupByResponses the list of top hits aggregations.
	 * @return the same builder
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

	public SearchResponseBuilder searchTimeValue(
		SearchTimeValue searchTimeValue);

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