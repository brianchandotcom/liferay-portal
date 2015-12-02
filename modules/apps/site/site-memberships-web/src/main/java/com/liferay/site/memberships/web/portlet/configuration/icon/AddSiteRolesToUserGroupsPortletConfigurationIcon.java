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

package com.liferay.site.memberships.web.portlet.configuration.icon;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.GroupPermissionUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Eudaldo Alonso
 */
public class AddSiteRolesToUserGroupsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public AddSiteRolesToUserGroupsPortletConfigurationIcon(
		PortletRequest portletRequest) {

		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "add-site-roles-to-user-groups";
	}

	@Override
	public String getURL() {
		try {
			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				portletRequest, UserGroupRole.class.getName(),
				PortletProvider.Action.EDIT);

			portletURL.setParameter("className", UserGroup.class.getName());
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
			return GroupPermissionUtil.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup(), ActionKeys.ASSIGN_USER_ROLES);
		}
		catch (PortalException pe) {
		}

		return false;
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

}