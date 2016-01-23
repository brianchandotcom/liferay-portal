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

package com.liferay.users.admin.web.portlet.configuration.icon;

import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.users.admin.web.portlet.action.ActionUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Pei-Jung Lan
 */
public class AssignOrganizationRolesPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public AssignOrganizationRolesPortletConfigurationIcon(
		PortletRequest portletRequest) {

		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "assign-organization-roles";
	}

	@Override
	public String getURL() {
		try {
			Organization organization = ActionUtil.getOrganization(
				portletRequest);

			long organizationGroupId = organization.getGroupId();

			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				portletRequest, UserGroupRole.class.getName(),
				PortletProvider.Action.EDIT);

			portletURL.setParameter("className", User.class.getName());
			portletURL.setParameter(
				"groupId", String.valueOf(organizationGroupId));
			portletURL.setWindowState(LiferayWindowState.POP_UP);

			return portletURL.toString();
		}
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

	@Override
	public boolean isShow() {
		try {
			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			Organization organization = ActionUtil.getOrganization(
				portletRequest);

			long organizationGroupId = organization.getGroupId();

			if (permissionChecker.isGroupOwner(organizationGroupId) ||
				OrganizationPermissionUtil.contains(
					permissionChecker, organization,
					ActionKeys.ASSIGN_USER_ROLES)) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

}