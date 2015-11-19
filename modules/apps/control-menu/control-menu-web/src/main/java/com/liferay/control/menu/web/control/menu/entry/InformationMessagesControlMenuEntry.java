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

package com.liferay.control.menu.web.control.menu.entry;

import com.liferay.control.menu.BaseJSPControlMenuEntry;
import com.liferay.control.menu.ControlMenuEntry;
import com.liferay.control.menu.constants.ControlMenuCategoryKeys;
import com.liferay.control.menu.constants.ControlMenuWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"control.menu.category.key=" + ControlMenuCategoryKeys.TOOLS,
		"service.ranking:Integer=300"
	},
	service = ControlMenuEntry.class
)
public class InformationMessagesControlMenuEntry
	extends BaseJSPControlMenuEntry implements ControlMenuEntry {

	@Override
	public String getJspPath() {
		return "/entries/information_messages.jsp";
	}

	@Override
	public boolean hasAccessPermission(HttpServletRequest request)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			return false;
		}

		if (!isCustomizableLayout(themeDisplay) &&
			!isLinkedLayout(themeDisplay) && !isModifiedLayout(themeDisplay)) {

			return false;
		}

		return super.hasAccessPermission(request);
	}

	public boolean hasUpdateLayoutPermission(ThemeDisplay themeDisplay)
		throws PortalException {

		if (LayoutPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
				ActionKeys.UPDATE)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		request.setAttribute(ControlMenuWebKeys.CONTROL_MENU_ENTRY, this);

		return super.include(request, response);
	}

	public boolean isCustomizableLayout(ThemeDisplay themeDisplay)
		throws PortalException {

		Layout layout = themeDisplay.getLayout();

		Group group = layout.getGroup();

		if (group.isLayoutPrototype() || group.isLayoutSetPrototype() ||
			group.isStagingGroup() || group.isUserGroup()) {

			return false;
		}

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		if (!layout.isTypePortlet() || (layoutTypePortlet == null)) {
			return false;
		}

		if (!layoutTypePortlet.isCustomizable()) {
			return false;
		}

		if (!LayoutPermissionUtil.containsWithoutViewableGroup(
				themeDisplay.getPermissionChecker(), layout, false,
				ActionKeys.CUSTOMIZE)) {

			return false;
		}

		return true;
	}

	public boolean isLinkedLayout(ThemeDisplay themeDisplay)
		throws PortalException {

		Layout layout = themeDisplay.getLayout();

		if (!LayoutPermissionUtil.containsWithoutViewableGroup(
				themeDisplay.getPermissionChecker(), layout, false,
				ActionKeys.UPDATE)) {

			return false;
		}

		Group group = layout.getGroup();

		if (!SitesUtil.isLayoutUpdateable(layout) ||
			(layout.isLayoutPrototypeLinkActive() &&
			 !group.hasStagingGroup())) {

			return true;
		}

		return false;
	}

	public boolean isModifiedLayout(ThemeDisplay themeDisplay)
		throws PortalException {

		Layout layout = themeDisplay.getLayout();

		LayoutSet layoutSet = layout.getLayoutSet();

		if (!layoutSet.isLayoutSetPrototypeLinkActive() ||
			!SitesUtil.isLayoutModifiedSinceLastMerge(layout)) {

			return false;
		}

		if (!hasUpdateLayoutPermission(themeDisplay)) {
			return false;
		}

		return true;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.control.menu.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

}