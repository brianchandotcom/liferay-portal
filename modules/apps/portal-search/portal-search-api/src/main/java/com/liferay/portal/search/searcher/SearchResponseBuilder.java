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

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.stats.StatsResponse;

import java.util.Map;

/**
 * Builds a search response with the results of a search.
 *
 * <p/>
 *
 * Use is intended for the Liferay Search Framework only.
 *
 * @author André de Oliveira
 *
 * @review
 */
@ProviderType
public interface SearchResponseBuilder {

	public SearchResponseBuilder aggregationResultsMap(
		Map<String, AggregationResult> aggregationResultsMap);

	/**
	 * Builds the search response.
	 *
	 * @return the search response
	 *
	 * @review
	 */
	public SearchResponse build();

	public SearchResponseBuilder hits(Hits hits);

	public SearchResponseBuilder request(SearchRequest searchRequest);

	/**
	 * Sets the request string submitted to the search engine.
	 *
	 * @param requestString the request string in search engine form
	 * @return the same builder
	 *
	 * @review
	 */
	public SearchResponseBuilder requestString(String requestString);

	/**
	 * Returns the response string returned by the search engine.
	 *
	 * @param responseString the response string in search engine form
	 * @return the same builder
	 *
	 * @review
	 */
	public SearchResponseBuilder responseString(String responseString);

	public SearchResponseBuilder searchHits(SearchHits searchHits);

	/**
	 * Sets the map containg the metrics aggregations computed by the search
	 * engine.
	 *
	 * @param statsResults the map containg the metrics aggregations per field
	 * @return the same builder
	 *
	 * @review
	 */
	public SearchResponseBuilder statsResponseMap(
		Map<String, StatsResponse> statsResponseMap);

}