/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.facet.modified;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.modified.ModifiedFacetFactory;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.internal.facet.ModifiedFacetImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = ModifiedFacetFactory.class)
public class ModifiedFacetFactoryImpl implements ModifiedFacetFactory {

	@Override
	public String getFacetClassName() {
		return Field.MODIFIED_DATE;
	}

	@Override
	public Facet newInstance(SearchContext searchContext) {
		return new ModifiedFacetImpl(
			Field.MODIFIED_DATE, searchContext, filterBuilders);
	}

	@Reference
	protected FilterBuilders filterBuilders;

}