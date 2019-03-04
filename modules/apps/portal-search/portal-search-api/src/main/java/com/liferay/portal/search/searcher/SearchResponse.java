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
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.stats.StatsResponse;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Holds various outcomes returned when a search is performed.
 *
 * @author André de Oliveira
 *
 * @review
 */
@ProviderType
public interface SearchResponse {

	public AggregationResult getAggregationResult(String name);

	public Map<String, AggregationResult> getAggregationResultsMap();

	public List<com.liferay.portal.kernel.search.Document> getDocuments71();

	public Stream<Document> getDocumentsStream();

	public SearchRequest getRequest();

	/**
	 * Returns the request string submitted to the search engine.
	 *
	 * @return the request string in search engine form
	 *
	 * @review
	 */
	public String getRequestString();

	/**
	 * Returns the response string returned by the search engine. Can be large
	 * depending on number of results. Must be enabled with
	 * {@link SearchRequest#isIncludeResponseString()}.
	 *
	 * @return the response string in search engine form, or blank if disabled
	 *
	 * @review
	 */
	public String getResponseString();

	public SearchHits getSearchHits();

	/**
	 * Returns the map containing the metrics aggregations computed by the
	 * search engine.
	 *
	 * @return the map containing the metrics aggregations per field
	 *
	 * @review
	 */
	public Map<String, StatsResponse> getStatsResponseMap();

	public int getTotalHits();

	public void withFacetContext(Consumer<FacetContext> facetContextConsumer);

	public <T> T withFacetContextGet(
		Function<FacetContext, T> facetContextFunction);

	public void withHits(Consumer<Hits> hitsConsumer);

	public <T> T withHitsGet(Function<Hits, T> hitsFunction);

	public void withSearchContext(
		Consumer<SearchContext> searchContextConsumer);

	public <T> T withSearchContextGet(
		Function<SearchContext, T> searchContextFunction);

}