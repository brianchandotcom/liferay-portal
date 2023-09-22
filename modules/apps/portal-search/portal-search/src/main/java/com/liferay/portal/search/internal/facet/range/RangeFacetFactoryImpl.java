/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.facet.range;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.range.RangeFacetFactory;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.internal.facet.RangeFacetImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = RangeFacetFactory.class)
public class RangeFacetFactoryImpl implements RangeFacetFactory {

	@Override
	public String getFacetClassName() {
		return RangeFacetFactory.class.getName();
	}

	@Override
	public Facet newInstance(SearchContext searchContext) {
		return new RangeFacetImpl(searchContext, _filterBuilders);
	}

	@Reference
	private FilterBuilders _filterBuilders;

}