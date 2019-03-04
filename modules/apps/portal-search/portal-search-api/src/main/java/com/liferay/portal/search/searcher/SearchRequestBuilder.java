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

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.stats.StatsRequest;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Builds a search request to be used when performing a search.
 *
 * @author André de Oliveira
 *
 * @review
 */
@ProviderType
public interface SearchRequestBuilder {

	public void addAggregation(Aggregation aggregation);

	public void addPipelineAggregation(PipelineAggregation pipelineAggregation);

	/**
	 * Adds fields to include in search results as a map of keys and values.
	 *
	 * @param selectedFieldNames the fields to return
	 * @return the same builder
	 *
	 * @review
	 */
	public SearchRequestBuilder addSelectedFieldNames(
		String... selectedFieldNames);

	/**
	 * Builds the search request.
	 *
	 * @return the search request
	 *
	 * @review
	 */
	public SearchRequest build();

	public SearchRequestBuilder entryClassNames(String... entryClassNames);

	/**
	 * Enables explanation for each hit on how its score was computed.
	 *
	 * @param explain whether to explain scores
	 * @return the same builder
	 *
	 * @review
	 */
	public SearchRequestBuilder explain(boolean explain);

	/**
	 * Enables inclusion of the search engine's response string with results.
	 *
	 * @param includeResponseString whether to include the response string
	 * @return the same builder
	 *
	 * @review
	 */
	public SearchRequestBuilder includeResponseString(
		boolean includeResponseString);

	public SearchRequestBuilder modelIndexerClasses(Class<?>... classes);

	public SearchRequestBuilder query(Query query);

	/**
	 * Provides a secondary query to reorder the top documents returned.
	 *
	 * @param rescoreQuery the rescore query
	 * @return the same builder
	 *
	 * @review
	 */
	public SearchRequestBuilder rescoreQuery(Query rescoreQuery);

	public SearchRequestBuilder sorts(Sort... sorts);

	/**
	 * Provides a map of field names and the metric aggregations that are to be
	 * computed for each field.
	 *
	 * @param statsMap the map containing the stats that are enabled for each
	 *        field.
	 * @return the same builder
	 *
	 * @review
	 */
	public SearchRequestBuilder statsRequests(StatsRequest... statsRequests);

	public SearchRequestBuilder withFacetContext(
		Consumer<FacetContext> facetContextConsumer);

	public <T> T withFacetContextGet(
		Function<FacetContext, T> facetContextFunction);

	public SearchRequestBuilder withSearchContext(
		Consumer<SearchContext> searchContextConsumer);

	public <T> T withSearchContextGet(
		Function<SearchContext, T> searchContextFunction);

}