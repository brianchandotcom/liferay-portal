/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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