/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.facet;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.util.FacetFactory;

/**
 * @author Raymond Augé
 */
public class RangeFacetFactory implements FacetFactory {

	@Override
	public String getFacetClassName() {
		return RangeFacet.class.getName();
	}

	@Override
	public Facet newInstance(SearchContext searchContext) {
		return new RangeFacet(searchContext);
	}

}