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

import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.users.admin.web.portlet.action.ActionUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Pei-Jung Lan
 */
public class ManageSitePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public ManageSitePortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getMessage() {
		return "manage-site";
	}

	@Override
	public String getURL() {
		try {
			Organization organization = ActionUtil.getOrganization(
				portletRequest);

			Group organizationGroup = organization.getGroup();

			PortletURL portletURL = PortletProviderUtil.getPortletURL(
				portletRequest, organizationGroup, Group.class.getName(),
				PortletProvider.Action.EDIT);

			portletURL.setParameter(
				"viewOrganizationsRedirect",
				PortalUtil.getCurrentURL(portletRequest));

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

			Group organizationGroup = organization.getGroup();

			if (organizationGroup.isSite() &&
				(GroupPermissionUtil.contains(
					permissionChecker, organizationGroup,
					ActionKeys.MANAGE_STAGING) ||
				 OrganizationPermissionUtil.contains(
					 permissionChecker, organization, ActionKeys.UPDATE))) {

				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

}