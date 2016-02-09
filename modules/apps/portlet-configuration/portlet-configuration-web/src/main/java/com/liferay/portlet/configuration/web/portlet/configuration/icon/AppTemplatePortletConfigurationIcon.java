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

package com.liferay.portlet.configuration.web.portlet.configuration.icon;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.configuration.kernel.util.PortletConfigurationApplicationType;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

/**
 * @author Eudaldo Alonso
 */
public class AppTemplatePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public AppTemplatePortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "app-templates";
	}

	@Override
	public String getURL() {
		try {
			String redirect = ParamUtil.getString(portletRequest, "redirect");
			String returnToFullPageURL = ParamUtil.getString(
				portletRequest, "returnToFullPageURL");
			String portletResource = ParamUtil.getString(
				portletRequest, "portletResource");

			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				portletRequest,
				PortletConfigurationApplicationType.
					PortletConfiguration.CLASS_NAME,
				PortletProvider.Action.VIEW);

			portletURL.setParameter("mvcPath", "/edit_app_templates.jsp");
			portletURL.setParameter("redirect", redirect);
			portletURL.setParameter("returnToFullPageURL", returnToFullPageURL);
			portletURL.setParameter(
				"portletConfiguration", Boolean.TRUE.toString());
			portletURL.setParameter("portletResource", portletResource);
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return portletURL.toString();
		}
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

	@Override
	public boolean isShow() {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		try {
			if (!GroupPermissionUtil.contains(
					permissionChecker, themeDisplay.getScopeGroupId(),
					ActionKeys.MANAGE_ARCHIVED_SETUPS)) {

				return false;
			}
		}
		catch (PortalException pe) {
			return false;
		}

		WindowState windowState = portletRequest.getWindowState();

		if (windowState.equals(LiferayWindowState.EXCLUSIVE)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

}