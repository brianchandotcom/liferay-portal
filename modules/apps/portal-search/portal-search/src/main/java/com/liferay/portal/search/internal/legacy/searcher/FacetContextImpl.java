/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		facets.put(_getAggregationName(facet), facet);
	}

	@Override
	public Facet getFacet(String aggregationName) {
		Map<String, Facet> facets = _searchContext.getFacets();

		return facets.get(aggregationName);
	}

	private String _getAggregationName(Facet facet) {
		if (facet instanceof com.liferay.portal.search.facet.Facet) {
			com.liferay.portal.search.facet.Facet osgiFacet =
				(com.liferay.portal.search.facet.Facet)facet;

			return osgiFacet.getAggregationName();
		}

		return facet.getFieldName();
	}

	private final SearchContext _searchContext;

}