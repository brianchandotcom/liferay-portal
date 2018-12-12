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

package com.liferay.portal.search.internal.searcher;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.search.internal.legacy.searcher.SearchRequestBuilderImpl.SearchRequestImpl;
import com.liferay.portal.search.legacy.searcher.SearchResponseBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.SearchResponseBuilder;
import com.liferay.portal.search.searcher.Searcher;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = Searcher.class)
public class SearcherImpl implements Searcher {

	@Override
	public SearchResponse search(SearchRequest searchRequest) {
		FacetedSearcher facetedSearcher =
			facetedSearcherManager.createFacetedSearcher();

		SearchContext searchContext =
			((SearchRequestImpl)searchRequest).getSearchContext();

		search(facetedSearcher, searchContext);

		SearchResponseBuilder searchResponseBuilder =
			searchResponseBuilderFactory.getSearchResponseBuilder(
				searchContext);

		return searchResponseBuilder.build();
	}

	protected Hits search(
		FacetedSearcher facetedSearcher, SearchContext searchContext) {

		try {
			return facetedSearcher.search(searchContext);
		}
		catch (SearchException se) {
			Throwable t = se.getCause();

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}

			throw new RuntimeException(t);
		}
	}

	@Reference
	protected FacetedSearcherManager facetedSearcherManager;

	@Reference
	protected SearchResponseBuilderFactory searchResponseBuilderFactory;

}