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

package com.liferay.product.navigation.taglib.internal.servlet;

import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.product.navigation.control.menu.util.ProductNavigationControlMenuCategoryRegistry;
import com.liferay.product.navigation.control.menu.util.ProductNavigationControlMenuEntryRegistry;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(immediate = true, service = {})
public class ServletContextUtil {

	public static String getContextPath() {
		return _servletContext.getContextPath();
	}

	public static PanelAppRegistry getPanelAppRegistry() {
		return _panelAppRegistry;
	}

	public static PanelCategoryRegistry getPanelCategoryRegistry() {
		return _panelCategoryRegistry;
	}

	public static ProductNavigationControlMenuCategoryRegistry
		getProductNavigationControlMenuCategoryRegistry() {

		return _productNavigationControlMenuCategoryRegistry;
	}

	public static ProductNavigationControlMenuEntryRegistry
		getProductNavigationControlMenuEntryRegistry() {

		return _productNavigationControlMenuEntryRegistry;
	}

	public static ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(unbind = "-")
	protected void setPanelAppRegistry(PanelAppRegistry panelAppRegistry) {
		_panelAppRegistry = panelAppRegistry;
	}

	@Reference(unbind = "-")
	protected void setPanelCategoryRegistry(
		PanelCategoryRegistry panelCategoryRegistry) {

		_panelCategoryRegistry = panelCategoryRegistry;
	}

	@Reference(unbind = "-")
	protected void setProductNavigationControlMenuCategoryRegistry(
		ProductNavigationControlMenuCategoryRegistry
			productNavigationControlMenuCategoryRegistry) {

		_productNavigationControlMenuCategoryRegistry =
			productNavigationControlMenuCategoryRegistry;
	}

	@Reference(unbind = "-")
	protected void setProductNavigationControlMenuEntryRegistry(
		ProductNavigationControlMenuEntryRegistry
			productNavigationControlMenuEntryRegistry) {

		_productNavigationControlMenuEntryRegistry =
			productNavigationControlMenuEntryRegistry;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.product.navigation.taglib)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static PanelAppRegistry _panelAppRegistry;
	private static PanelCategoryRegistry _panelCategoryRegistry;
	private static ProductNavigationControlMenuCategoryRegistry
		_productNavigationControlMenuCategoryRegistry;
	private static ProductNavigationControlMenuEntryRegistry
		_productNavigationControlMenuEntryRegistry;
	private static ServletContext _servletContext;

}