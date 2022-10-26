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

package com.liferay.account.internal.instance.lifecycle;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Organization;
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
@Component(
	property = "service.ranking:Integer=200",
	service = PortalInstanceLifecycleListener.class
)
public class AddDefaultAccountRolesPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_accountRoleLocalService.checkCompanyAccountRoles(
			company.getCompanyId());

		_checkResourcePermissions(
			company.getCompanyId(),
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MEMBER,
			_accountMemberResourceActionsMap);
		_checkResourcePermissions(
			company.getCompanyId(),
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR,
			_accountMemberResourceActionsMap,
			_accountAdministratorResourceActionsMap);
		_checkResourcePermissions(
			company.getCompanyId(),
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MANAGER,
			_accountMemberResourceActionsMap,
			_accountManagerResourceActionsMap);
	}

	private void _checkResourcePermissions(
			long companyId, String roleName,
			Map<String, String[]>... resourceActionsMaps)
		throws Exception {

		Role role = _roleLocalService.fetchRole(companyId, roleName);

		for (Map<String, String[]> resourceActionsMap : resourceActionsMaps) {
			for (Map.Entry<String, String[]> entry :
					resourceActionsMap.entrySet()) {

				for (String resourceAction : entry.getValue()) {
					String resourceName = entry.getKey();

					ResourcePermission resourcePermission =
						_resourcePermissionLocalService.fetchResourcePermission(
							companyId, resourceName,
							ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
							role.getRoleId());

					if ((resourcePermission == null) ||
						!resourcePermission.hasActionId(resourceAction)) {

						_resourcePermissionLocalService.addResourcePermission(
							companyId, resourceName,
							ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
							role.getRoleId(), resourceAction);
					}
				}
			}
		}
	}

	private static final Map<String, String[]>
		_accountAdministratorResourceActionsMap = HashMapBuilder.put(
			AccountEntry.class.getName(),
			new String[] {
				ActionKeys.UPDATE, ActionKeys.MANAGE_USERS,
				AccountActionKeys.MANAGE_ADDRESSES,
				AccountActionKeys.VIEW_ADDRESSES,
				AccountActionKeys.VIEW_ACCOUNT_ROLES,
				AccountActionKeys.VIEW_ORGANIZATIONS,
				AccountActionKeys.VIEW_USERS
			}
		).build();
	private static final Map<String, String[]>
		_accountManagerResourceActionsMap = HashMapBuilder.put(
			AccountEntry.class.getName(),
			new String[] {
				AccountActionKeys.MANAGE_ADDRESSES,
				AccountActionKeys.VIEW_ACCOUNT_ROLES,
				AccountActionKeys.VIEW_ADDRESSES,
				AccountActionKeys.VIEW_ORGANIZATIONS,
				AccountActionKeys.VIEW_USERS, ActionKeys.MANAGE_USERS,
				ActionKeys.UPDATE
			}
		).put(
			Organization.class.getName(),
			new String[] {
				AccountActionKeys.MANAGE_ACCOUNTS,
				AccountActionKeys.MANAGE_SUBORGANIZATIONS_ACCOUNTS
			}
		).build();
	private static final Map<String, String[]>
		_accountMemberResourceActionsMap = HashMapBuilder.put(
			AccountEntry.class.getName(), new String[] {ActionKeys.VIEW}
		).build();

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.account.service)(&(release.schema.version>=1.0.2)))"
	)
	private Release _release;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}