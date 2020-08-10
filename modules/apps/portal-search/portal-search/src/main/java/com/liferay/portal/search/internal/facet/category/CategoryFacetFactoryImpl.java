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

package com.liferay.portal.search.internal.facet.category;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.facet.Facet;
import com.liferay.portal.search.facet.FacetFactory;
import com.liferay.portal.search.facet.category.CategoryFacetFactory;
import com.liferay.portal.search.internal.facet.FacetImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(
	immediate = true, service = {CategoryFacetFactory.class, FacetFactory.class}
)
public class CategoryFacetFactoryImpl implements CategoryFacetFactory {

	@Override
	public String getFacetClassName() {
		return Field.ASSET_CATEGORY_IDS;
	}

	@Override
	public Facet newInstance(SearchContext searchContext) {
		if (searchContext.isIncludeInternalAssetCategories()) {
			return new FacetImpl(Field.ASSET_CATEGORY_IDS, searchContext);
		}

		return new FacetImpl(Field.ASSET_PUBLIC_CATEGORY_IDS, searchContext);
	}

}