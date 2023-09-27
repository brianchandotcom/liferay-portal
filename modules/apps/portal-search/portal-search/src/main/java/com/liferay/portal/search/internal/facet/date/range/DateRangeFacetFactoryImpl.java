/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.facet.date.range;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.FacetFactory;
import com.liferay.portal.search.facet.date.range.DateRangeFacetFactory;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.internal.facet.DateRangeFacetImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = {FacetFactory.class, DateRangeFacetFactory.class})
public class DateRangeFacetFactoryImpl implements DateRangeFacetFactory {

	@Override
	public String getFacetClassName() {
		return DateRangeFacetFactory.class.getName();
	}

	@Override
	public Facet newInstance(SearchContext searchContext) {
		return new DateRangeFacetImpl(searchContext, _filterBuilders);
	}

	@Reference
	private FilterBuilders _filterBuilders;

}