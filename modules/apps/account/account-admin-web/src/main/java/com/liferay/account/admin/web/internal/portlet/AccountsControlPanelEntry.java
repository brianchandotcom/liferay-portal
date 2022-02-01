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

package com.liferay.account.admin.web.internal.portlet;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.BaseControlPanelEntry;
import com.liferay.portal.kernel.portlet.ControlPanelEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.permission.OrganizationPermission;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = {
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
		"javax.portlet.name=" + AccountPortletKeys.ACCOUNT_USERS_ADMIN
	},
	service = ControlPanelEntry.class
)
public class AccountsControlPanelEntry extends BaseControlPanelEntry {

	@Override
	protected boolean hasPermissionImplicitlyGranted(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		List<Organization> organizations =
			_organizationLocalService.getUserOrganizations(
				permissionChecker.getUserId(), true);

		for (Organization organization : organizations) {
			if (_organizationPermission.contains(
					permissionChecker, organization,
					AccountActionKeys.MANAGE_ACCOUNTS) &&
				permissionChecker.hasPermission(
					organization.getGroupId(), portlet.getPortletId(), 0,
					ActionKeys.ACCESS_IN_CONTROL_PANEL)) {

				return true;
			}
		}

		return super.hasPermissionImplicitlyGranted(
			permissionChecker, group, portlet);
	}

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private OrganizationPermission _organizationPermission;

}