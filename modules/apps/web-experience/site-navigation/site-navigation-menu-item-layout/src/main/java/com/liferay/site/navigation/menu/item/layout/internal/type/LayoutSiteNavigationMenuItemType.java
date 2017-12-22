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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.menu.item.layout.internal.constants.SiteNavigationMenuItemTypeLayoutConstants;
import com.liferay.site.navigation.menu.item.layout.internal.constants.SiteNavigationMenuItemTypeLayoutWebKeys;
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
	property = {"site.navigation.menu.item.type=" + SiteNavigationMenuItemTypeLayoutConstants.LAYOUT},
	service = SiteNavigationMenuItemType.class
)
public class LayoutSiteNavigationMenuItemType
	implements SiteNavigationMenuItemType {

	@Override
	public String getIcon() {
		return "page";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "page");
	}

	@Override
	public String getTitle(
		SiteNavigationMenuItem siteNavigationMenuItem, Locale locale) {

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.fastLoad(
			siteNavigationMenuItem.getTypeSettings());

		String layoutUuid = typeSettingsProperties.get("layoutUuid");

		Layout layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
			layoutUuid, siteNavigationMenuItem.getGroupId(), false);

		if (layout == null) {
			layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
				layoutUuid, siteNavigationMenuItem.getGroupId(), true);
		}

		if (layout != null) {
			return layout.getName(locale);
		}

		return getLabel(locale);
	}

	@Override
	public String getType() {
		return SiteNavigationMenuItemTypeLayoutConstants.LAYOUT;
	}

	@Override
	public void renderAddPage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		request.setAttribute(
			SiteNavigationMenuItemTypeLayoutWebKeys.ITEM_SELECTOR,
			_itemSelector);

		_jspRenderer.renderJSP(
			_servletContext, request, response, "/edit_layout.jsp");
	}

	@Override
	public void renderEditPage(
			HttpServletRequest request, HttpServletResponse response,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws IOException {

		request.setAttribute(
			SiteNavigationMenuItemTypeLayoutWebKeys.ITEM_SELECTOR,
			_itemSelector);

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.fastLoad(
			siteNavigationMenuItem.getTypeSettings());

		String layoutUuid = typeSettingsProperties.get("layoutUuid");
		long groupId = GetterUtil.getLong(
			typeSettingsProperties.get("groupId"));
		boolean privateLayout = GetterUtil.getBoolean(
			typeSettingsProperties.get("privateLayout"));

		Layout layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
			layoutUuid, groupId, privateLayout);

		request.setAttribute(WebKeys.SEL_LAYOUT, layout);

		_jspRenderer.renderJSP(
			_servletContext, request, response, "/edit_layout.jsp");
	}

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.menu.item.layout)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}