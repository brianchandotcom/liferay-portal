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

package com.liferay.portal.search.web.internal.search.request;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ScopeFacet;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.search.web.internal.util.SearchStringUtil;
import com.liferay.portal.search.web.search.request.SearchRequest;
import com.liferay.portal.search.web.search.request.SearchSettings;
import com.liferay.portal.search.web.search.request.SearchSettingsContributor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author André de Oliveira
 */
public class SearchRequestImpl implements SearchRequest {

	public SearchRequestImpl(
		SearchContextBuilder searchContextBuilder,
		SearchContainerBuilder searchContainerBuilder,
		FacetedSearcherManager facetedSearcherManager) {

		_searchContextBuilder = searchContextBuilder;
		_searchContainerBuilder = searchContainerBuilder;
		_facetedSearcherManager = facetedSearcherManager;
	}

	@Override
	public void addSearchSettingsContributor(
		SearchSettingsContributor searchSettingsContributor) {

		_searchSettingsContributors.add(searchSettingsContributor);
	}

	@Override
	public void removeSearchSettingsContributor(
		SearchSettingsContributor searchSettingsContributor) {

		_searchSettingsContributors.remove(searchSettingsContributor);
	}

	@Override
	public SearchResponseImpl search() {
		SearchContext searchContext = buildSearchContext();

		SearchSettingsImpl searchSettingsImpl = buildSettings(searchContext);

		SearchContainer<Document> searchContainer = buildSearchContainer(
			searchSettingsImpl);

		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setStart(searchContainer.getStart());

		Hits hits = search(searchContext);

		searchContainer.setResults(hits.toList());

		searchContainer.setSearch(true);

		searchContainer.setTotal(hits.getLength());

		SearchResponseImpl searchResponseImpl = buildSearchResponse(
			hits, searchContext, searchContainer, searchSettingsImpl);

		return searchResponseImpl;
	}

	protected void addScopeFacet(SearchContext searchContext) {
		Facet scopeFacet = new ScopeFacet(searchContext);

		scopeFacet.setStatic(true);

		searchContext.addFacet(scopeFacet);
	}

	protected SearchContainer<Document> buildSearchContainer(
		SearchSettingsImpl searchSettingsImpl) {

		return _searchContainerBuilder.getSearchContainer(searchSettingsImpl);
	}

	protected SearchContext buildSearchContext() {
		SearchContext searchContext = _searchContextBuilder.getSearchContext();

		searchContext.setAttribute("paginationType", "more");

		addScopeFacet(searchContext);

		return searchContext;
	}

	protected SearchResponseImpl buildSearchResponse(
		Hits hits, SearchContext searchContext,
		SearchContainer<Document> searchContainer,
		SearchSettings searchSettings) {

		SearchResponseImpl searchResponseImpl = new SearchResponseImpl();

		searchResponseImpl.setDocuments(hits.toList());
		searchResponseImpl.setHits(hits);
		searchResponseImpl.setKeywords(searchContext.getKeywords());
		searchResponseImpl.setPaginationDelta(searchContainer.getDelta());
		searchResponseImpl.setPaginationStart(searchContainer.getCur());
		searchResponseImpl.setQueryString(
			(String)searchContext.getAttribute("queryString"));
		searchResponseImpl.setSearchContainer(searchContainer);
		searchResponseImpl.setSearchContext(searchContext);
		searchResponseImpl.setSearchSettings(searchSettings);
		searchResponseImpl.setTotalHits(hits.getLength());

		return searchResponseImpl;
	}

	protected SearchSettingsImpl buildSettings(SearchContext searchContext) {
		SearchSettingsImpl searchSettingsImpl = new SearchSettingsImpl(
			searchContext);

		_searchSettingsContributors.forEach(
			searchContributor -> searchContributor.contribute(
				searchSettingsImpl));

		return searchSettingsImpl;
	}

	protected Hits search(
		FacetedSearcher facetedSearcher, SearchContext searchContext) {

		try {
			return facetedSearcher.search(searchContext);
		}
		catch (SearchException se) {
			throw new RuntimeException(se);
		}
	}

	protected Hits search(SearchContext searchContext) {
		Optional<String> keywordsOptional = SearchStringUtil.maybe(
			searchContext.getKeywords());

		Optional<Hits> hitsOptional = keywordsOptional.map(
			keywords -> {
				FacetedSearcher facetedSearcher =
					_facetedSearcherManager.createFacetedSearcher();

				return search(facetedSearcher, searchContext);
			});

		return hitsOptional.orElseGet(HitsImpl::new);
	}

	private final FacetedSearcherManager _facetedSearcherManager;
	private final SearchContainerBuilder _searchContainerBuilder;
	private final SearchContextBuilder _searchContextBuilder;
	private final Set<SearchSettingsContributor> _searchSettingsContributors =
		new HashSet<>();

}