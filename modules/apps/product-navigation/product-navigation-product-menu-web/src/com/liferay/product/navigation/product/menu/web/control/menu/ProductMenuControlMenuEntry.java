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

package com.liferay.product.navigation.product.menu.web.control.menu;

import com.liferay.control.menu.BaseJSPControlMenuEntry;
import com.liferay.control.menu.ControlMenuEntry;
import com.liferay.control.menu.constants.ControlMenuCategoryKeys;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 *
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"control.menu.category.key=" + ControlMenuCategoryKeys.SITES,
		"service.ranking:Integer=100"
	},
	service = ControlMenuEntry.class
)
public class ProductMenuControlMenuEntry extends BaseJSPControlMenuEntry {

	@Override
	public String getJspPath() {
		return "/portlet/control_menu/product_menu_control_menu_entry.jsp";
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.product.navigation.product.menu.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

}