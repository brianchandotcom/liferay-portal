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
import com.liferay.portal.search.filter.ComplexQueryPart;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.stats.StatsRequest;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Builds a search request that can be used for executing a search.
 *
 * @author André de Oliveira
 */
@ProviderType
public interface SearchRequestBuilder {

	public SearchRequestBuilder addAggregation(Aggregation aggregation);

	public SearchRequestBuilder addComplexQueryPart(
		ComplexQueryPart complexQueryPart);

	public SearchRequestBuilder addFederatedSearchRequest(
		SearchRequest searchRequest);

	public SearchRequestBuilder addIndex(String index);

	public SearchRequestBuilder addPipelineAggregation(
		PipelineAggregation pipelineAggregation);

	/**
	 * Adds fields to include in the search results as a map of keys and values.
	 *
	 * @param  selectedFieldNames the names of the fields to return
	 * @return the search request builder
	 */
	public SearchRequestBuilder addSelectedFieldNames(
		String... selectedFieldNames);

	public SearchRequestBuilder basicFacetSelection(
		boolean basicFacetSelection);

	/**
	 * Builds the search request.
	 *
	 * @return the search request
	 */
	public SearchRequest build();

	public SearchRequestBuilder emptySearchEnabled(boolean emptySearchEnabled);

	public SearchRequestBuilder entryClassNames(String... entryClassNames);

	/**
	 * Enables explanation of how each hit's score was computed.
	 *
	 * @param  explain whether to explain scores
	 * @return the search request builder
	 */
	public SearchRequestBuilder explain(boolean explain);

	public SearchRequestBuilder federatedSearchKey(String federatedSearchKey);

	public SearchRequestBuilder fields(String... fields);

	public SearchRequestBuilder from(Integer from);

	public SearchRequestBuilder getFederatedSearchRequestBuilder(
		String federatedSearchKey);

	public SearchRequestBuilder highlightEnabled(boolean highlightEnabled);

	public SearchRequestBuilder highlightFields(String... highlightFields);

	/**
	 * Enables inclusion of the search engine's response string with the
	 * returned results.
	 *
	 * @param  includeResponseString whether to include the response string
	 * @return the search request builder
	 */
	public SearchRequestBuilder includeResponseString(
		boolean includeResponseString);

	public SearchRequestBuilder indexes(String... indexes);

	public SearchRequestBuilder modelIndexerClasses(Class<?>... classes);

	public SearchRequestBuilder postFilterQuery(Query query);

	public SearchRequestBuilder query(Query query);

	public SearchRequestBuilder queryString(String queryString);

	/**
	 * Provides a secondary query to reorder the top documents returned.
	 *
	 * @param  rescoreQuery the rescore query
	 * @return the search request builder
	 */
	public SearchRequestBuilder rescoreQuery(Query rescoreQuery);

	public SearchRequestBuilder size(Integer size);

	public SearchRequestBuilder sorts(Sort... sorts);

	/**
	 * Provides the metric aggregations to be computed for each field.
	 *
	 * @param  statsRequests the stats that are enabled for each field
	 * @return the search request builder
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