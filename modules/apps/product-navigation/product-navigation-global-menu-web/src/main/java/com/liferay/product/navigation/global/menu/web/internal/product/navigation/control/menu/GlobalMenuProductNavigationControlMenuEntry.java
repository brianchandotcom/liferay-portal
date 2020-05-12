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

package com.liferay.product.navigation.global.menu.web.internal.product.navigation.control.menu;

import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.product.navigation.control.menu.BaseJSPProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuCategoryKeys;
import com.liferay.product.navigation.global.menu.web.internal.configuration.FFProductNavigationGlobalMenuConfiguration;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	configurationPid = "com.liferay.product.navigation.global.menu.web.internal.configuration.FFProductNavigationGlobalMenuConfiguration",
	immediate = true,
	property = {
		"product.navigation.control.menu.category.key=" + ProductNavigationControlMenuCategoryKeys.USER,
		"product.navigation.control.menu.entry.order:Integer=400"
	},
	service = ProductNavigationControlMenuEntry.class
)
public class GlobalMenuProductNavigationControlMenuEntry
	extends BaseJSPProductNavigationControlMenuEntry {

	@Override
	public String getIconJspPath() {
		return "/global_menu/global_menu.jsp";
	}

	@Override
	public boolean isShow(HttpServletRequest httpServletRequest)
		throws PortalException {

		if (_ffProductNavigationGlobalMenuConfiguration.showGlobalMenu()) {
			return true;
		}

		return false;
	}

	@Reference(
		target = "(panel.category.key=" + PanelCategoryKeys.HIDDEN + ")",
		unbind = "-"
	)
	public void setPanelCategory(PanelCategory panelCategory) {
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.product.navigation.global.menu.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_ffProductNavigationGlobalMenuConfiguration =
			ConfigurableUtil.createConfigurable(
				FFProductNavigationGlobalMenuConfiguration.class, properties);
	}

	private volatile FFProductNavigationGlobalMenuConfiguration
		_ffProductNavigationGlobalMenuConfiguration;

}