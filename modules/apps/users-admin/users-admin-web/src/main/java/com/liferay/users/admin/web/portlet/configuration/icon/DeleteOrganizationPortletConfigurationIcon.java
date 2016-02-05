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

import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Organization;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.users.admin.web.portlet.action.ActionUtil;

import javax.portlet.PortletRequest;

/**
 * @author Pei-Jung Lan
 */
public class DeleteOrganizationPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public DeleteOrganizationPortletConfigurationIcon(
		PortletRequest portletRequest) {

		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "delete";
	}

	@Override
	public String getOnClick() {
		StringBundler sb = new StringBundler(4);

		try {
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			sb.append(portletDisplay.getNamespace());

			sb.append("deleteOrganization('");

			Organization organization = ActionUtil.getOrganization(
				portletRequest);

			sb.append(organization.getOrganizationId());

			sb.append("');");
		}
		catch (Exception e) {
		}

		return sb.toString();
	}

	@Override
	public String getURL() {
		return "javascript:;";
	}

	@Override
	public boolean isShow() {
		try {
			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			Organization organization = ActionUtil.getOrganization(
				portletRequest);

			if (OrganizationPermissionUtil.contains(
					permissionChecker, organization, ActionKeys.DELETE)) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

}