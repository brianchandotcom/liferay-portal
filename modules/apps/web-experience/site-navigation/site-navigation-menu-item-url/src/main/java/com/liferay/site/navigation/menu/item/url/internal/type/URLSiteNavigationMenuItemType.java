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

package com.liferay.site.navigation.menu.item.url.internal.type;

import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.site.navigation.constants.SiteNavigationWebKeys;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = "site.navigation.menu.item.type=" + SiteNavigationMenuItemTypeConstants.URL,
	service = SiteNavigationMenuItemType.class
)
public class URLSiteNavigationMenuItemType
	implements SiteNavigationMenuItemType {

	@Override
	public String getIcon() {
		return "link";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "url");
	}

	@Override
	public String getRegularURL(
		HttpServletRequest request,
		SiteNavigationMenuItem siteNavigationMenuItem) {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.fastLoad(
			siteNavigationMenuItem.getTypeSettings());

		return typeSettingsProperties.get("url");
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
		return SiteNavigationMenuItemTypeConstants.URL;
	}

	@Override
	public boolean isBrowsable(SiteNavigationMenuItem siteNavigationMenuItem) {
		return true;
	}

	@Override
	public void renderAddPage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		_jspRenderer.renderJSP(
			_servletContext, request, response, "/edit_url.jsp");
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
			_servletContext, request, response, "/edit_url.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.menu.item.url)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}