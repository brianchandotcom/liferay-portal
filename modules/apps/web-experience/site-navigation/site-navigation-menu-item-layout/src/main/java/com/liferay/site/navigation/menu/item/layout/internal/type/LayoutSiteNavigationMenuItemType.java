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

package com.liferay.site.navigation.menu.item.layout.internal.type;

import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.menu.item.layout.internal.constants.SiteNavigationMenuItemTypeLayoutWebKeys;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;

import java.io.IOException;

import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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
	property = "site.navigation.menu.item.type=" + SiteNavigationMenuItemTypeConstants.LAYOUT,
	service = SiteNavigationMenuItemType.class
)
public class LayoutSiteNavigationMenuItemType
	implements SiteNavigationMenuItemType {

	@Override
	public PortletURL getAddURL(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		PortletURL addURL = renderResponse.createActionURL();

		addURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/navigation_menu/add_layout_site_navigation_menu_item");

		return addURL;
	}

	@Override
	public String getIcon() {
		return "page";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "page");
	}

	@Override
	public String getRegularURL(
			HttpServletRequest request,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = _getLayout(siteNavigationMenuItem);

		return _portal.getLayoutFullURL(layout, themeDisplay, true);
	}

	@Override
	public String getTitle(
		SiteNavigationMenuItem siteNavigationMenuItem, Locale locale) {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.fastLoad(
			siteNavigationMenuItem.getTypeSettings());

		String label = typeSettingsProperties.getProperty("title");

		if (Validator.isNotNull(label)) {
			return label;
		}

		Layout layout = _getLayout(siteNavigationMenuItem);

		if (layout != null) {
			return layout.getName(locale);
		}

		return getLabel(locale);
	}

	@Override
	public String getType() {
		return SiteNavigationMenuItemTypeConstants.LAYOUT;
	}

	@Override
	public String getTypeSettingsFromLayout(Layout layout) {
		UnicodeProperties unicodeProperties = new UnicodeProperties();

		unicodeProperties.setProperty(
			"groupId", String.valueOf(layout.getGroupId()));
		unicodeProperties.setProperty("layoutUuid", layout.getUuid());
		unicodeProperties.setProperty(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));

		return unicodeProperties.toString();
	}

	@Override
	public void renderAddPage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		request.setAttribute(
			SiteNavigationMenuItemTypeLayoutWebKeys.ITEM_SELECTOR,
			_itemSelector);

		_jspRenderer.renderJSP(
			_servletContext, request, response, "/add_layout.jsp");
	}

	@Override
	public void renderEditPage(
			HttpServletRequest request, HttpServletResponse response,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws IOException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		request.setAttribute(
			SiteNavigationMenuItemTypeLayoutWebKeys.ITEM_SELECTOR,
			_itemSelector);

		request.setAttribute(
			WebKeys.SEL_LAYOUT, _getLayout(siteNavigationMenuItem));
		request.setAttribute(
			WebKeys.TITLE,
			getTitle(siteNavigationMenuItem, themeDisplay.getLocale()));

		_jspRenderer.renderJSP(
			_servletContext, request, response, "/edit_layout.jsp");
	}

	private Layout _getLayout(SiteNavigationMenuItem siteNavigationMenuItem) {
		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.fastLoad(
			siteNavigationMenuItem.getTypeSettings());

		String layoutUuid = typeSettingsProperties.get("layoutUuid");
		long groupId = GetterUtil.getLong(
			typeSettingsProperties.get("groupId"));
		boolean privateLayout = GetterUtil.getBoolean(
			typeSettingsProperties.get("privateLayout"));

		return _layoutLocalService.fetchLayoutByUuidAndGroupId(
			layoutUuid, groupId, privateLayout);
	}

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.menu.item.layout)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}