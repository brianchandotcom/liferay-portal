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

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.Aggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.filter.ComplexQueryPart;
import com.liferay.portal.search.internal.searcher.SearchRequestImpl;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.searcher.FacetContext;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.stats.StatsRequest;

import java.io.Serializable;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class SearchRequestBuilderImpl implements SearchRequestBuilder {

	public SearchRequestBuilderImpl(
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		this(searchRequestBuilderFactory, new SearchContext());
	}

	public SearchRequestBuilderImpl(
		SearchRequestBuilderFactory searchRequestBuilderFactory,
		SearchContext searchContext) {

		_searchRequestBuilderFactory = searchRequestBuilderFactory;
		_facetContext = new FacetContextImpl(searchContext);
		_searchContext = searchContext;
	}

	@Override
	public SearchRequestBuilder addAggregation(Aggregation aggregation) {
		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.addAggregation(aggregation));

		return this;
	}

	@Override
	public SearchRequestBuilder addComplexQueryPart(
		ComplexQueryPart complexQueryPart) {

		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.addComplexQueryPart(
				complexQueryPart));

		return this;
	}

	@Override
	public SearchRequestBuilder addFederatedSearchRequest(
		SearchRequest searchRequest) {

		addFederatedSearchRequests(Stream.of(searchRequest));

		return this;
	}

	@Override
	public SearchRequestBuilder addIndex(String index) {
		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.addIndex(index));

		return this;
	}

	@Override
	public SearchRequestBuilder addPipelineAggregation(
		PipelineAggregation pipelineAggregation) {

		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.addPipelineAggregation(
				pipelineAggregation));

		return this;
	}

	@Override
	public SearchRequestBuilder addSelectedFieldNames(
		String... selectedFieldNames) {

		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.addSelectedFieldNames(
				selectedFieldNames));

		return this;
	}

	@Override
	public SearchRequestBuilder basicFacetSelection(
		boolean basicFacetSelection) {

		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.setBasicFacetSelection(
				basicFacetSelection));

		return this;
	}

	@Override
	public SearchRequest build() {
		basicFacetSelection(
			SearchRequestImpl.isBasicFacetSelection(_searchContext));

		addFederatedSearchRequests(buildFederatedSearchRequests());

		return withSearchRequestGet(Function.identity());
	}

	@Override
	public SearchRequestBuilder emptySearchEnabled(boolean allowEmptySearches) {
		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.setEmptySearchEnabled(
				allowEmptySearches));

		return this;
	}

	@Override
	public SearchRequestBuilder entryClassNames(String... entryClassNames) {
		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.addEntryClassNames(
				entryClassNames));

		return this;
	}

	@Override
	public SearchRequestBuilder explain(boolean explain) {
		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.setExplain(explain));

		return this;
	}

	@Override
	public SearchRequestBuilder federatedSearchKey(String federatedSearchKey) {
		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.setFederatedSearchKey(
				federatedSearchKey));

		return this;
	}

	@Override
	public SearchRequestBuilder getFederatedSearchRequestBuilder(
		String federatedSearchKey) {

		if (Validator.isBlank(federatedSearchKey)) {
			return this;
		}

		return _federatedSearchRequestBuildersMap.computeIfAbsent(
			federatedSearchKey, this::newFederatedSearchRequestBuilder);
	}

	@Override
	public SearchRequestBuilder highlightEnabled(boolean highlightEnabled) {
		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.setHighlightEnabled(
				highlightEnabled));

		return this;
	}

	@Override
	public SearchRequestBuilder includeResponseString(
		boolean includeResponseString) {

		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.setIncludeResponseString(
				includeResponseString));

		return this;
	}

	@Override
	public SearchRequestBuilder indexes(String... indexes) {
		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.setIndexes(indexes));

		return this;
	}

	@Override
	public SearchRequestBuilder modelIndexerClasses(Class<?>... classes) {
		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.setModelIndexerClasses(
				classes));

		return this;
	}

	@Override
	public SearchRequestBuilder postFilterQuery(Query query) {
		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.setPostFilterQuery(query));

		return this;
	}

	@Override
	public SearchRequestBuilder query(Query query) {
		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.setQuery(query));

		return this;
	}

	@Override
	public SearchRequestBuilder queryString(String queryString) {
		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.setQueryString(queryString));

		return this;
	}

	@Override
	public SearchRequestBuilder rescoreQuery(Query query) {
		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.setRescoreQuery(query));

		return this;
	}

	@Override
	public SearchRequestBuilder sorts(Sort... sorts) {
		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.setSorts(sorts));

		return this;
	}

	@Override
	public SearchRequestBuilder statsRequests(StatsRequest... statsRequests) {
		withSearchRequestImpl(
			searchRequestImpl -> searchRequestImpl.setStatsRequests(
				statsRequests));

		return this;
	}

	@Override
	public SearchRequestBuilder withFacetContext(
		Consumer<FacetContext> facetContextConsumer) {

		facetContextConsumer.accept(_facetContext);

		return this;
	}

	@Override
	public <T> T withFacetContextGet(
		Function<FacetContext, T> facetContextFunction) {

		return facetContextFunction.apply(_facetContext);
	}

	@Override
	public SearchRequestBuilder withSearchContext(
		Consumer<SearchContext> consumer) {

		consumer.accept(_searchContext);

		return this;
	}

	@Override
	public <T> T withSearchContextGet(
		Function<SearchContext, T> searchContextFunction) {

		return searchContextFunction.apply(_searchContext);
	}

	protected static SearchRequestImpl getSearchRequestImpl(
		SearchContext searchContext) {

		return Optional.ofNullable(
			(SearchRequestImpl)searchContext.getAttribute(
				_SEARCH_CONTEXT_KEY_SEARCH_REQUEST)
		).orElseGet(
			() -> setAttribute(
				searchContext, _SEARCH_CONTEXT_KEY_SEARCH_REQUEST,
				new SearchRequestImpl(searchContext))
		);
	}

	protected static <T extends Serializable> T setAttribute(
		SearchContext searchContext, String key, T value) {

		searchContext.setAttribute(key, value);

		return value;
	}

	protected void addFederatedSearchRequests(Stream<SearchRequest> stream) {
		withSearchRequestImpl(
			searchRequestImpl -> stream.forEach(
				searchRequestImpl::addFederatedSearchRequest));
	}

	protected Stream<SearchRequest> buildFederatedSearchRequests() {
		Collection<SearchRequestBuilder> searchRequestBuilders =
			_federatedSearchRequestBuildersMap.values();

		Stream<SearchRequest> map = searchRequestBuilders.stream(
		).map(
			SearchRequestBuilder::build
		);

		return map;
	}

	protected SearchRequestBuilder newFederatedSearchRequestBuilder(
		String federatedSearchKey) {

		return _searchRequestBuilderFactory.builder(
		).federatedSearchKey(
			federatedSearchKey
		);
	}

	protected <T> T withSearchRequestGet(
		Function<SearchRequest, T> searchRequestFunction) {

		synchronized (_searchContext) {
			return searchRequestFunction.apply(
				getSearchRequestImpl(_searchContext));
		}
	}

	protected void withSearchRequestImpl(
		Consumer<SearchRequestImpl> searchRequestImplConsumer) {

		synchronized (_searchContext) {
			searchRequestImplConsumer.accept(
				getSearchRequestImpl(_searchContext));
		}
	}

	private static final String _SEARCH_CONTEXT_KEY_SEARCH_REQUEST =
		"search.request";

	private final FacetContext _facetContext;
	private final Map<String, SearchRequestBuilder>
		_federatedSearchRequestBuildersMap = new LinkedHashMap<>();
	private final SearchContext _searchContext;
	private final SearchRequestBuilderFactory _searchRequestBuilderFactory;

}