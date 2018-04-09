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

package com.liferay.site.navigation.menu.item.node.internal.type;

import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.site.navigation.constants.SiteNavigationWebKeys;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = "site.navigation.menu.item.type=" + SiteNavigationMenuItemTypeConstants.NODE,
	service = SiteNavigationMenuItemType.class
)
public class NodeSiteNavigationMenuItemType
	implements SiteNavigationMenuItemType {

	@Override
	public String getIcon() {
		return "pages-tree";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "submenu");
	}

	@Override
	public String getTitle(
		SiteNavigationMenuItem siteNavigationMenuItem, Locale locale) {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.fastLoad(
			siteNavigationMenuItem.getTypeSettings());

		return typeSettingsProperties.get("name");
	}

	@Override
	public String getType() {
		return SiteNavigationMenuItemTypeConstants.NODE;
	}

	@Override
	public void renderAddPage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		_jspRenderer.renderJSP(
			_servletContext, request, response, "/edit_node.jsp");
	}

	@Override
	public void renderEditPage(
			HttpServletRequest request, HttpServletResponse response,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws IOException {

		request.setAttribute(
			SiteNavigationWebKeys.SITE_NAVIGATION_MENU_ITEM,
			siteNavigationMenuItem);

		_jspRenderer.renderJSP(
			_servletContext, request, response, "/edit_node.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.site.navigation.menu.item.node)",
		unbind = "-"
	)
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.menu.item.node)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}