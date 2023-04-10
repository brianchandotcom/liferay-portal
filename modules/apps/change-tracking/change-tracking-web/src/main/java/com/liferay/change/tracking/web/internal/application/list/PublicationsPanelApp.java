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

package com.liferay.change.tracking.web.internal.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.web.internal.configuration.helper.CTSettingsConfigurationHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(
	property = {
		"panel.app.order:Integer=200",
		"panel.category.key=" + PanelCategoryKeys.APPLICATIONS_MENU_APPLICATIONS_PUBLICATIONS
	},
	service = PanelApp.class
)
public class PublicationsPanelApp extends BasePanelApp {

	@Override
	public Portlet getPortlet() {
		return _portlet;
	}

	@Override
	public String getPortletId() {
		return CTPortletKeys.PUBLICATIONS;
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = super.getPortletURL(httpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!_ctSettingsConfigurationHelper.isEnabled(
				themeDisplay.getCompanyId())) {

			portletURL.setParameter(
				"mvcRenderCommandName", "/change_tracking/view_settings");
		}

		return portletURL;
	}

	@Override
	public boolean isShow(PermissionChecker permissionChecker, Group group)
		throws PortalException {

		if (_portletPermission.contains(
				permissionChecker, CTPortletKeys.PUBLICATIONS,
				ActionKeys.CONFIGURATION) ||
			(_ctSettingsConfigurationHelper.isEnabled(group.getCompanyId()) &&
			 _portletPermission.contains(
				 permissionChecker, CTPortletKeys.PUBLICATIONS,
				 ActionKeys.VIEW))) {

			return true;
		}

		return false;
	}

	@Reference
	private CTSettingsConfigurationHelper _ctSettingsConfigurationHelper;

	@Reference(
		target = "(javax.portlet.name=" + CTPortletKeys.PUBLICATIONS + ")"
	)
	private Portlet _portlet;

	@Reference
	private PortletPermission _portletPermission;

}