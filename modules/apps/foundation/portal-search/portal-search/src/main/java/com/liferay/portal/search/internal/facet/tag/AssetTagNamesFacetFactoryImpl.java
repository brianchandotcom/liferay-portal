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

package com.liferay.portal.search.internal.facet.tag;

import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.util.FacetFactory;
import com.liferay.portal.search.facet.tag.AssetTagNamesFacetFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	service = {AssetTagNamesFacetFactory.class, FacetFactory.class}
)
public class AssetTagNamesFacetFactoryImpl
	implements AssetTagNamesFacetFactory, FacetFactory {

	@Override
	public String getFacetClassName() {
		return AssetTagNamesFacetFactory.class.getName();
	}

	@Override
	public Facet newInstance(SearchContext searchContext) {
		return new AssetTagNamesFacet(searchContext);
	}

}