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
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.groupby.GroupByResponse;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.stats.StatsResponse;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Contains the full response of an executed search, as returned from the search
 * engine. The exact response format and the contents of the response depends on
 * the search engine and the search request that was executed.
 *
 * @author André de Oliveira
 */
@ProviderType
public interface SearchResponse {

	public AggregationResult getAggregationResult(String name);

	public Map<String, AggregationResult> getAggregationResultsMap();

	public long getCount();

	public List<com.liferay.portal.kernel.search.Document> getDocuments71();

	public Stream<Document> getDocumentsStream();

	public String getFederatedSearchKey();

	public SearchResponse getFederatedSearchResponse(String key);

	public Stream<SearchResponse> getFederatedSearchResponsesStream();

	/**
	 * Returns the map containing the top hits aggregations for each field.
	 *
	 * @return the map containing the top hits aggregations
	 * @review
	 */
	public List<GroupByResponse> getGroupByResponses();

	public SearchRequest getRequest();

	/**
	 * Returns the request string that was submitted to the search engine. The
	 * format of the string is dependent on the search engine.
	 *
	 * @return the full request string, as returned by the search engine
	 */
	public String getRequestString();

	/**
	 * Returns the response string as returned by the search engine. This can be
	 * large depending on the number of results; it must be enabled with {@link
	 * SearchRequest#isIncludeResponseString()}.
	 *
	 * @return the response string as returned by the search engine, or blank if
	 *         disabled
	 */
	public String getResponseString();

	public SearchHits getSearchHits();

	/**
	 * Returns the map containing the metrics aggregations computed by the
	 * search engine.
	 *
	 * @return the map containing the metrics aggregations per field
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