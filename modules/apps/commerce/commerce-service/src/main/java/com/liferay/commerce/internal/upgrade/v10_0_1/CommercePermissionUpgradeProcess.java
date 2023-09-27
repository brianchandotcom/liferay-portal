/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v10_0_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author João Victor Cordeiro
 */
public class CommercePermissionUpgradeProcess extends UpgradeProcess {

	public CommercePermissionUpgradeProcess(
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String[] newPermissions = {"MANAGE_COMMERCE_ORDER_PAYMENT_STATUSES"};

		_updateCompanyRolePermissions("Operations Manager", newPermissions);
		_updateCompanyRolePermissions("Supplier", newPermissions);
		_updateAccountRolePermissions("Account Administrator", newPermissions);
		_updateAccountRolePermissions("Order Manager", newPermissions);
	}

	private void _updateAccountRolePermissions(
			String name, String[] permissions)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select companyId, resourcePermissionId, roleId from ",
					"ResourcePermission where name = ",
					"'com.liferay.commerce.order' and scope = 3"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long roleId = resultSet.getLong(3);

				Role role = _roleLocalService.fetchRole(
					resultSet.getLong(1), name);

				if ((role == null) || (roleId != role.getRoleId())) {
					continue;
				}

				ResourcePermission resourcePermission =
					_resourcePermissionLocalService.getResourcePermission(
						resultSet.getLong(2));

				for (String permission : permissions) {
					ResourceAction resourceAction =
						_resourceActionLocalService.fetchResourceAction(
							"com.liferay.commerce.order", permission);

					if ((resourceAction == null) ||
						_resourcePermissionLocalService.hasActionId(
							resourcePermission, resourceAction)) {

						continue;
					}

					resourcePermission.addResourceAction(
						resourceAction.getActionId());

					_resourcePermissionLocalService.updateResourcePermission(
						resourcePermission);
				}
			}
		}
	}

	private void _updateCompanyRolePermissions(
			String name, String[] permissions)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select companyId, resourcePermissionId, roleId from ",
					"ResourcePermission where name = ",
					"'com.liferay.commerce.order' and scope = 1"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long roleId = resultSet.getLong(3);

				Role role = _roleLocalService.fetchRole(
					resultSet.getLong(1), name);

				if ((role == null) || (roleId != role.getRoleId())) {
					continue;
				}

				ResourcePermission resourcePermission =
					_resourcePermissionLocalService.getResourcePermission(
						resultSet.getLong(2));

				for (String permission : permissions) {
					ResourceAction resourceAction =
						_resourceActionLocalService.fetchResourceAction(
							"com.liferay.commerce.order", permission);

					if ((resourceAction == null) ||
						_resourcePermissionLocalService.hasActionId(
							resourcePermission, resourceAction)) {

						continue;
					}

					resourcePermission.addResourceAction(
						resourceAction.getActionId());

					_resourcePermissionLocalService.updateResourcePermission(
						resourcePermission);
				}
			}
		}
	}

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}