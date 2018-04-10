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

package com.liferay.site.navigation.type;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;

import java.io.IOException;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pavel Savinov
 */
public interface SiteNavigationMenuItemType {

	public default PortletURL getAddURL(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return null;
	}

	public default String getIcon() {
		return "magic";
	}

	public String getLabel(Locale locale);

	public default Layout getLayout(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		return null;
	}

	public default String getRegularURL(
			HttpServletRequest request,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception {

		return StringPool.BLANK;
	}

	public default String getResetLayoutURL(
			HttpServletRequest request,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception {

		return StringPool.BLANK;
	}

	public default String getResetMaxStateURL(
			HttpServletRequest request,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws Exception {

		return StringPool.BLANK;
	}

	public default String getTarget(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		return StringPool.BLANK;
	}

	public String getTitle(
		SiteNavigationMenuItem siteNavigationMenuItem, Locale locale);

	public default String getType() {
		return StringPool.BLANK;
	}

	public default String getTypeSettingsFromLayout(Layout layout) {
		return layout.getTypeSettings();
	}

	public default String getUnescapedName(
		SiteNavigationMenuItem siteNavigationMenuItem, String languageId) {

		return getTitle(
			siteNavigationMenuItem, LanguageUtil.getLocale(languageId));
	}

	public default String iconURL(
		SiteNavigationMenuItem siteNavigationMenuItem, String pathImage) {

		return StringPool.BLANK;
	}

	public default boolean isBrowsable(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		return false;
	}

	public default boolean isChildSelected(
			boolean selectable, SiteNavigationMenuItem siteNavigationMenuItem,
			Layout curLayout)
		throws PortalException {

		return false;
	}

	public default boolean isSelected(
			boolean selectable, SiteNavigationMenuItem siteNavigationMenuItem,
			Layout curLayout)
		throws Exception {

		return false;
	}

	public default void renderAddPage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {
	}

	public default void renderEditPage(
			HttpServletRequest request, HttpServletResponse response,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws IOException {
	}

}