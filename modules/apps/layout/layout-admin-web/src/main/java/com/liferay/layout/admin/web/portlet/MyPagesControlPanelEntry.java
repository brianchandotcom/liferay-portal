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

package com.liferay.layout.admin.web.portlet;

import com.liferay.layout.admin.web.constants.LayoutAdminPortletKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.RoleLocalService;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.BaseControlPanelEntry;
import com.liferay.portlet.ControlPanelEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + LayoutAdminPortletKeys.MY_PAGES},
	service = ControlPanelEntry.class
)
public class MyPagesControlPanelEntry extends BaseControlPanelEntry {

	@Override
	protected boolean hasAccessPermissionDenied(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		if (!PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED &&
			!PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) {

			return true;
		}

		if ((PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_POWER_USER_REQUIRED ||
			 PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED) &&
			!_roleLocalService.hasUserRole(
				permissionChecker.getUserId(), permissionChecker.getCompanyId(),
				RoleConstants.POWER_USER, true)) {

			return true;
		}

		return super.hasAccessPermissionDenied(
			permissionChecker, group, portlet);
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	private volatile RoleLocalService _roleLocalService;

}