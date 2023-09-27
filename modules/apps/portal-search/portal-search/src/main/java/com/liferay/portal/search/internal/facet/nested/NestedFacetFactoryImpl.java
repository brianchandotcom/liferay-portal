/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.facet.nested;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.nested.NestedFacetFactory;
import com.liferay.portal.search.internal.facet.NestedFacetImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Díaz
 */
@Component(service = NestedFacetFactory.class)
public class NestedFacetFactoryImpl implements NestedFacetFactory {

	@Override
	public String getFacetClassName() {
		return NestedFacetFactory.class.getName();
	}

	@Override
	public Facet newInstance(SearchContext searchContext) {
		return new NestedFacetImpl(null, searchContext);
	}

}