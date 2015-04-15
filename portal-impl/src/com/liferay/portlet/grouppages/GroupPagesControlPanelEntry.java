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

package com.liferay.portlet.grouppages;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.BaseControlPanelEntry;

/**
 * @author Jorge Ferrer
 * @author Sergio González
 * @author Tibor Lipusz
 */
public class GroupPagesControlPanelEntry extends BaseControlPanelEntry {

	@Override
	protected boolean hasAccessPermissionDenied(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		if (group.isUser()) {
			return hasUserLayoutsAccesPermissionDenied(permissionChecker);
		}

		return group.isCompany();
	}

	@Override
	protected boolean hasPermissionImplicitlyGranted(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		if (group.isUser()) {
			return super.hasAccessPermissionExplicitlyGranted(
				permissionChecker, group, portlet);
		}

		return GroupPermissionUtil.contains(
			permissionChecker, group, ActionKeys.MANAGE_LAYOUTS);
	}

	protected boolean hasUserLayoutsAccesPermissionDenied(
			PermissionChecker permissionChecker)
		throws Exception {

		if (!PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED) {
			return true;
		}

		if (PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_POWER_USER_REQUIRED &&
			!RoleLocalServiceUtil.hasUserRole(
				permissionChecker.getUserId(), permissionChecker.getCompanyId(),
				RoleConstants.POWER_USER, true)) {

			return true;
		}

		return false;
	}

}