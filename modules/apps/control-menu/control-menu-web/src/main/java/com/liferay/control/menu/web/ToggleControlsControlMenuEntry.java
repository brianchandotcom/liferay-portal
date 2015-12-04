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

package com.liferay.control.menu.web;

import com.liferay.control.menu.BaseControlMenuEntry;
import com.liferay.control.menu.ControlMenuEntry;
import com.liferay.control.menu.constants.ControlMenuCategoryKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.SessionClicks;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	property = {
		"control.menu.category.key=" + ControlMenuCategoryKeys.TOOLS,
		"service.ranking:Integer=500"
	},
	service = ControlMenuEntry.class
)
public class ToggleControlsControlMenuEntry
	extends BaseControlMenuEntry implements ControlMenuEntry {

	@Override
	public String getIconCssClass(HttpServletRequest request) {
		String stateCss = null;

		String toggleControls = GetterUtil.getString(
			SessionClicks.get(
				request, "com.liferay.frontend.js.web_toggleControls",
				"visible"));

		if (toggleControls.equals("visible")) {
			stateCss = "view";
		}
		else {
			stateCss = "hidden";
		}

		return stateCss;
	}

	@Override
	public String getLabel(Locale locale) {
		return "edit-controls";
	}

	@Override
	public String getLinkCssClass(HttpServletRequest request) {
		return "toggle-controls";
	}

	@Override
	public String getURL(HttpServletRequest request) {
		return "javascript:;";
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

		Group group = layout.getGroup();

		if (group.hasStagingGroup() && !group.isStagingGroup()) {
			return false;
		}

		if (!(hasUpdateLayoutPermission(themeDisplay) ||
			  hasCustomizePermission(themeDisplay) ||
			  hasPortletConfigurationPermission(themeDisplay))) {

			return false;
		}

		return super.hasAccessPermission(request);
	}

	protected boolean hasCustomizePermission(ThemeDisplay themeDisplay)
		throws PortalException {

		Layout layout = themeDisplay.getLayout();
		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		if (!layout.isTypePortlet() || (layoutTypePortlet == null)) {
			return false;
		}

		if (!layoutTypePortlet.isCustomizable() ||
			!layoutTypePortlet.isCustomizedView()) {

			return false;
		}

		if (LayoutPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), layout,
				ActionKeys.CUSTOMIZE)) {

			return true;
		}

		return false;
	}

	protected boolean hasPortletConfigurationPermission(
			ThemeDisplay themeDisplay)
		throws PortalException {

		return PortletPermissionUtil.hasConfigurationPermission(
			themeDisplay.getPermissionChecker(), themeDisplay.getSiteGroupId(),
			themeDisplay.getLayout(), ActionKeys.CONFIGURATION);
	}

	protected boolean hasUpdateLayoutPermission(ThemeDisplay themeDisplay)
		throws PortalException {

		return LayoutPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
			ActionKeys.UPDATE);
	}

}