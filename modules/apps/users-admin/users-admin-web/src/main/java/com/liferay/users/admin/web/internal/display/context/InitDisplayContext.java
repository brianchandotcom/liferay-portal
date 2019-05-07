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

package com.liferay.users.admin.web.internal.display.context;

import com.liferay.admin.kernel.util.PortalMyAccountApplicationType;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class InitDisplayContext {

	public InitDisplayContext(
		HttpServletRequest httpServletRequest, String portletName) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		String myAccountPortletId = PortletProviderUtil.getPortletId(
			PortalMyAccountApplicationType.MyAccount.CLASS_NAME,
			PortletProvider.Action.VIEW);

		if (portletName.equals(myAccountPortletId)) {
			_filterManageableGroups = false;
			_filterManageableOrganizations = false;
			_filterManageableRoles = false;
			_filterManageableUserGroupRoles = false;
			_filterManageableUserGroups = false;
		}
		else if (permissionChecker.isCompanyAdmin()) {
			_filterManageableGroups = false;
			_filterManageableOrganizations = false;
			_filterManageableRoles = true;
			_filterManageableUserGroups = false;
			_filterManageableUserGroupRoles = true;
		}
		else {
			if (permissionChecker.hasPermission(
					null, Organization.class.getName(),
					Organization.class.getName(), ActionKeys.VIEW)) {

				_filterManageableOrganizations = false;
			}
			else {
				_filterManageableOrganizations = true;
			}

			_filterManageableGroups = true;
			_filterManageableRoles = true;
			_filterManageableUserGroupRoles = true;
			_filterManageableUserGroups = true;
		}
	}

	public boolean isFilterManageableGroups() {
		return _filterManageableGroups;
	}

	public boolean isFilterManageableOrganizations() {
		return _filterManageableOrganizations;
	}

	public boolean isFilterManageableRoles() {
		return _filterManageableRoles;
	}

	public boolean isFilterManageableUserGroupRoles() {
		return _filterManageableUserGroupRoles;
	}

	public boolean isFilterManageableUserGroups() {
		return _filterManageableUserGroups;
	}

	private final boolean _filterManageableGroups;
	private final boolean _filterManageableOrganizations;
	private final boolean _filterManageableRoles;
	private final boolean _filterManageableUserGroupRoles;
	private final boolean _filterManageableUserGroups;

}