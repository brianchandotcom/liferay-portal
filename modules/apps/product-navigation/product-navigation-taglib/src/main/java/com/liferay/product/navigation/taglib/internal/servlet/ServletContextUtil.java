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
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.product.navigation.control.menu.manager.ProductNavigationControlMenuManager;
import com.liferay.product.navigation.control.menu.util.ProductNavigationControlMenuCategoryRegistry;
import com.liferay.product.navigation.control.menu.util.ProductNavigationControlMenuEntryRegistry;

import javax.servlet.ServletContext;

/**
 * @author Julio Camarero
 */
public class ServletContextUtil {

	public static PanelAppRegistry getPanelAppRegistry() {
		return _panelAppRegistrySnapshot.get();
	}

	public static PanelCategoryRegistry getPanelCategoryRegistry() {
		return _panelCategoryRegistrySnapshot.get();
	}

	public static ProductNavigationControlMenuCategoryRegistry
		getProductNavigationControlMenuCategoryRegistry() {

		return _productNavigationControlMenuCategoryRegistrySnapshot.get();
	}

	public static ProductNavigationControlMenuEntryRegistry
		getProductNavigationControlMenuEntryRegistry() {

		return _productNavigationControlMenuEntryRegistrySnapshot.get();
	}

	public static ProductNavigationControlMenuManager
		getProductNavigationControlMenuManager() {

		return _productNavigationControlMenuManagerSnapshot.get();
	}

	public static ServletContext getServletContext() {
		return _servletContextSnapshot.get();
	}

	private static final Snapshot<PanelAppRegistry> _panelAppRegistrySnapshot =
		new Snapshot<>(ServletContextUtil.class, PanelAppRegistry.class);
	private static final Snapshot<PanelCategoryRegistry>
		_panelCategoryRegistrySnapshot = new Snapshot<>(
			ServletContextUtil.class, PanelCategoryRegistry.class);
	private static final Snapshot<ProductNavigationControlMenuCategoryRegistry>
		_productNavigationControlMenuCategoryRegistrySnapshot = new Snapshot<>(
			ServletContextUtil.class,
			ProductNavigationControlMenuCategoryRegistry.class);
	private static final Snapshot<ProductNavigationControlMenuEntryRegistry>
		_productNavigationControlMenuEntryRegistrySnapshot = new Snapshot<>(
			ServletContextUtil.class,
			ProductNavigationControlMenuEntryRegistry.class);
	private static final Snapshot<ProductNavigationControlMenuManager>
		_productNavigationControlMenuManagerSnapshot = new Snapshot<>(
			ServletContextUtil.class,
			ProductNavigationControlMenuManager.class);
	private static final Snapshot<ServletContext> _servletContextSnapshot =
		new Snapshot<>(
			ServletContextUtil.class, ServletContext.class,
			"(osgi.web.symbolicname=com.liferay.product.navigation.taglib)");

}