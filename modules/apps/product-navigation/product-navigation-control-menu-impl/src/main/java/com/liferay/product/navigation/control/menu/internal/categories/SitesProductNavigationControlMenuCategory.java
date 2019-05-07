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

package com.liferay.product.navigation.control.menu.internal.categories;

import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuCategory;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.ROOT,
		"product.navigation.control.menu.category.order:Integer=100"
	},
	service = ProductNavigationControlMenuCategory.class
)
public class SitesProductNavigationControlMenuCategory
	implements ProductNavigationControlMenuCategory {

	@Override
	public String getKey() {
		return ProductNavigationControlMenuCategoryKeys.SITES;
	}

	@Override
	public boolean hasAccessPermission(HttpServletRequest httpServletRequest) {
		return true;
	}

}