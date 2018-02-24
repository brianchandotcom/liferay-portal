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

package com.liferay.portal.search.internal.contributor.query;

import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.search.spi.model.query.contributor.QueryPreFilterContributor;

import java.util.Map;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {Constants.SERVICE_RANKING + "=" + Integer.MIN_VALUE},
	service = QueryPreFilterContributor.class
)
public class FacetQueryPostFilterContributor
	implements QueryPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter fullQueryBooleanFilter, SearchContext searchContext) {

		Map<String, Facet> facets = searchContext.getFacets();

		BooleanFilter facetBooleanFilter = new BooleanFilter();

		for (Facet facet : facets.values()) {
			BooleanClause<Filter> filterBooleanClause =
				facet.getFacetFilterBooleanClause();

			if (filterBooleanClause != null) {
				facetBooleanFilter.add(
					filterBooleanClause.getClause(),
					filterBooleanClause.getBooleanClauseOccur());
			}
		}

		if (facetBooleanFilter.hasClauses()) {
			fullQueryBooleanFilter.add(
				facetBooleanFilter, BooleanClauseOccur.MUST);
		}
	}

}