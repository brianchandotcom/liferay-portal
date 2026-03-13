/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v2_12_1;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.Arrays;

/**
 * @author Tancredi Covioli
 */
public class AccountEntryResourcePermissionUpgradeProcess
	extends UpgradeProcess {

	public AccountEntryResourcePermissionUpgradeProcess(
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String originalActionKey = ActionKeys.MANAGE_USERS;

		ResourceAction resourceAction =
			_resourceActionLocalService.fetchResourceAction(
				AccountEntry.class.getName(), originalActionKey);

		if (resourceAction == null) {
			return;
		}

		_resourceActionLocalService.checkResourceActions(
			AccountEntry.class.getName(),
			Arrays.asList(
				AccountActionKeys.ASSIGN_USERS, AccountActionKeys.CREATE_USERS,
				AccountActionKeys.EDIT_USERS, AccountActionKeys.UNASSIGN_USERS,
				AccountActionKeys.VIEW_USERS),
			true);

		for (ResourcePermission resourcePermission :
				_resourcePermissionLocalService.getResourcePermissions(
					AccountEntry.class.getName())) {

			if (!_resourcePermissionLocalService.hasActionId(
					resourcePermission, resourceAction) ||
				(resourcePermission.getScope() ==
					ResourceConstants.SCOPE_INDIVIDUAL)) {

				continue;
			}

			_addResourcePermission(
				AccountActionKeys.ASSIGN_USERS, resourcePermission);
			_addResourcePermission(
				AccountActionKeys.CREATE_USERS, resourcePermission);
			_addResourcePermission(
				AccountActionKeys.EDIT_USERS, resourcePermission);
			_addResourcePermission(
				AccountActionKeys.UNASSIGN_USERS, resourcePermission);
			_addResourcePermission(
				AccountActionKeys.VIEW_USERS, resourcePermission);

			_resourcePermissionLocalService.removeResourcePermission(
				resourcePermission.getCompanyId(), resourcePermission.getName(),
				resourcePermission.getScope(), resourcePermission.getPrimKey(),
				resourcePermission.getRoleId(), originalActionKey);
		}

		_resourceActionLocalService.deleteResourceAction(resourceAction);
	}

	private void _addResourcePermission(
			String actionId, ResourcePermission resourcePermission)
		throws Exception {

		if (!_resourcePermissionLocalService.hasResourcePermission(
				resourcePermission.getCompanyId(), AccountEntry.class.getName(),
				resourcePermission.getScope(), resourcePermission.getPrimKey(),
				resourcePermission.getRoleId(), actionId)) {

			_resourcePermissionLocalService.addResourcePermission(
				resourcePermission.getCompanyId(), AccountEntry.class.getName(),
				resourcePermission.getScope(), resourcePermission.getPrimKey(),
				resourcePermission.getRoleId(), actionId);
		}
	}

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}