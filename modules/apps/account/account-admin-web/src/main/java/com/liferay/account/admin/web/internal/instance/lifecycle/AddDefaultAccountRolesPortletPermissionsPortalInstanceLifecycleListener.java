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

package com.liferay.account.admin.web.internal.instance.lifecycle;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountPortletKeys;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class
	AddDefaultAccountRolesPortletPermissionsPortalInstanceLifecycleListener
		extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		long companyId = company.getCompanyId();

		try {
			Role role = _roleLocalService.getRole(
				companyId,
				AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER);

			_checkResourcePermissions(
				companyId, role.getRoleId(),
				HashMapBuilder.put(
					AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN,
					new String[] {ActionKeys.ACCESS_IN_CONTROL_PANEL}
				).put(
					AccountPortletKeys.ACCOUNT_USERS_ADMIN,
					new String[] {
						AccountActionKeys.ASSIGN_ACCOUNTS,
						ActionKeys.ACCESS_IN_CONTROL_PANEL
					}
				).build());
		}
		catch (NoSuchRoleException noSuchRoleException) {
			_log.error(noSuchRoleException, noSuchRoleException);
		}
	}

	private void _checkResourcePermissions(
			long companyId, long roleId,
			Map<String, String[]> resourceActionsMap)
		throws Exception {

		for (Map.Entry<String, String[]> entry :
				resourceActionsMap.entrySet()) {

			for (String resourceAction : entry.getValue()) {
				String resourceName = entry.getKey();

				ResourcePermission resourcePermission =
					_resourcePermissionLocalService.fetchResourcePermission(
						companyId, resourceName,
						ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", roleId);

				if ((resourcePermission == null) ||
					!resourcePermission.hasActionId(resourceAction)) {

					_resourcePermissionLocalService.addResourcePermission(
						companyId, resourceName,
						ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", roleId,
						resourceAction);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddDefaultAccountRolesPortletPermissionsPortalInstanceLifecycleListener.
			class);

	@Reference(
		target = "(javax.portlet.name=" + AccountPortletKeys.ACCOUNT_ENTRIES_ADMIN + ")"
	)
	private Portlet _accountEntriesAdminPortlet;

	@Reference(
		target = "(javax.portlet.name=" + AccountPortletKeys.ACCOUNT_USERS_ADMIN + ")"
	)
	private Portlet _accountUsersAdminPortlet;

	@Reference(
		target = "(component.name=com.liferay.account.internal.instance.lifecycle.AddDefaultAccountRolesPortalInstanceLifecycleListener)"
	)
	private PortalInstanceLifecycleListener _portalInstanceLifecycleListener;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.account.service)(&(release.schema.version>=1.0.2)))"
	)
	private Release _release;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}