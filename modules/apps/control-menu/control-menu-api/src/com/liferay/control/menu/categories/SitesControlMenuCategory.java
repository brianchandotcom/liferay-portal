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

package com.liferay.control.menu.categories;

import com.liferay.control.menu.ControlMenuCategory;
import com.liferay.control.menu.constants.ControlMenuCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"control.menu.category.key=" + ControlMenuCategoryKeys.ROOT,
		"service.ranking:Integer=100"
	},
	service = ControlMenuCategory.class
)
public class SitesControlMenuCategory implements ControlMenuCategory {

	@Override
	public String getKey() {
		return ControlMenuCategoryKeys.SITES;
	}

	@Override
	public boolean hasAccessPermission(HttpServletRequest request)
		throws PortalException {

		return true;
	}

}