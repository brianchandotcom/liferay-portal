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
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.search.searcher.FacetContext;

import java.util.Map;

/**
 * @author André de Oliveira
 */
public class FacetContextImpl implements FacetContext {

	public FacetContextImpl(SearchContext searchContext) {
		_searchContext = searchContext;
	}

	@Override
	public void addFacet(Facet facet) {
		Map<String, Facet> facets = _searchContext.getFacets();

		facets.put(getAggregationName(facet), facet);
	}

	@Override
	public Facet getFacet(String aggregationName) {
		Map<String, Facet> facets = _searchContext.getFacets();

		return facets.get(aggregationName);
	}

	protected String getAggregationName(Facet facet) {
		if (facet instanceof com.liferay.portal.search.facet.Facet) {
			com.liferay.portal.search.facet.Facet osgiFacet =
				(com.liferay.portal.search.facet.Facet)facet;

			return osgiFacet.getAggregationName();
		}

		return facet.getFieldName();
	}

	private final SearchContext _searchContext;

}