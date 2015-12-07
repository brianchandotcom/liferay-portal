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

package com.liferay.control.menu.web.application.list;

import com.liferay.application.list.BaseJSPPanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.control.menu.application.list.SimulationPanelCategory;
import com.liferay.control.menu.web.constants.ControlMenuPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.GroupPermissionUtil;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 *
 * @author Eduardo Garcia
 */

@Component(
	immediate = true,
	property = {
		"panel.category.key=" + SimulationPanelCategory.SIMULATION,
		"service.ranking:Integer=100"
	},
	service = PanelApp.class
)
public class DevicePreviewPanelApp extends BaseJSPPanelApp {

	@Override
	public String getJspPath() {
		return "/entries/simulation/device_preview.jsp";
	}

	@Override
	public String getPortletId() {
		return ControlMenuPortletKeys.CONTROL_MENU;
	}

	@Override
	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Group group)
		throws PortalException {

		if (group.isControlPanel()) {
			return false;
		}

		if (!hasPreviewInDevicePermission(permissionChecker, group)) {
			return false;
		}

		return super.hasAccessPermission(permissionChecker, group);
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + ControlMenuPortletKeys.CONTROL_MENU + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.control.menu.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	protected boolean hasPreviewInDevicePermission(
			PermissionChecker permissionChecker, Group group)
		throws PortalException {

		return GroupPermissionUtil.contains(
			permissionChecker, group, ActionKeys.PREVIEW_IN_DEVICE);
	}

}