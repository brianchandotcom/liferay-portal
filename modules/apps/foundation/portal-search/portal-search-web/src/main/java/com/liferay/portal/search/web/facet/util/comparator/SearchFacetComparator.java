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

package com.liferay.portal.search.web.facet.util.comparator;

import com.liferay.portal.search.web.facet.SearchFacet;

import java.util.Comparator;

/**
 * @author Shuyang Zhou
 */
public class SearchFacetComparator implements Comparator<SearchFacet> {

	public static final Comparator<SearchFacet> INSTANCE =
		new SearchFacetComparator();

	@Override
	public int compare(SearchFacet searchFacet1, SearchFacet searchFacet2) {
		return Double.compare(
			searchFacet2.getWeight(), searchFacet1.getWeight());
	}

	private SearchFacetComparator() {
	}

}