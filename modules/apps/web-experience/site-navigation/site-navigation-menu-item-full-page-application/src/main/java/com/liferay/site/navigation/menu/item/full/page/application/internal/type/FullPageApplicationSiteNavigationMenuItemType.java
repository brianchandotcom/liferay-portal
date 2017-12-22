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

package com.liferay.site.navigation.menu.item.full.page.application.internal.type;

import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.site.navigation.constants.SiteNavigationWebKeys;
import com.liferay.site.navigation.menu.item.full.page.application.internal.constants.SiteNavigationMenuItemTypeFullPageApplicationConstants;
import com.liferay.site.navigation.menu.item.full.page.application.internal.constants.SiteNavigationMenuItemTypeFullPageApplicationWebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
	property = {"site.navigation.menu.item.type=" + SiteNavigationMenuItemTypeFullPageApplicationConstants.FULL_PAGE_APPLICATION},
	service = SiteNavigationMenuItemType.class
)
public class FullPageApplicationSiteNavigationMenuItemType
	implements SiteNavigationMenuItemType {

	@Override
	public String getIcon() {
		return "full-size";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, "application");
	}

	@Override
	public String getTitle(
		SiteNavigationMenuItem siteNavigationMenuItem, Locale locale) {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.fastLoad(
			siteNavigationMenuItem.getTypeSettings());

		String fullPageApplicationPortletId = typeSettingsProperties.get(
			"fullPageApplicationPortlet");

		Portlet fullPageApplicationPortlet =
			_portletLocalService.getPortletById(fullPageApplicationPortletId);

		return fullPageApplicationPortlet.getDisplayName();
	}

	@Override
	public String getType() {
		return SiteNavigationMenuItemTypeFullPageApplicationConstants.
			FULL_PAGE_APPLICATION;
	}

	@Override
	public void renderAddPage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		request.setAttribute(
			SiteNavigationMenuItemTypeFullPageApplicationWebKeys.
				FULL_PAGE_APPLICATION_PORTLETS,
			_getPortlets());

		_jspRenderer.renderJSP(
			_servletContext, request, response,
			"/edit_full_page_application.jsp");
	}

	@Override
	public void renderEditPage(
			HttpServletRequest request, HttpServletResponse response,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws IOException {

		request.setAttribute(
			SiteNavigationMenuItemTypeFullPageApplicationWebKeys.
				FULL_PAGE_APPLICATION_PORTLETS,
			_getPortlets());

		request.setAttribute(
			SiteNavigationWebKeys.SITE_NAVIGATION_MENU_ITEM,
			siteNavigationMenuItem);

		_jspRenderer.renderJSP(
			_servletContext, request, response,
			"/edit_full_page_application.jsp");
	}

	private List<Portlet> _getPortlets() {
		List<Portlet> portlets = _portletLocalService.getPortlets();

		if (portlets.isEmpty()) {
			return Collections.emptyList();
		}

		portlets = ListUtil.filter(
			portlets,
			new PredicateFilter<Portlet>() {

				@Override
				public boolean filter(Portlet portlet) {
					return portlet.isFullPageDisplayable();
				}

			});

		return portlets;
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.site.navigation.menu.item.full.page.application)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.menu.item.full.page.application)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}