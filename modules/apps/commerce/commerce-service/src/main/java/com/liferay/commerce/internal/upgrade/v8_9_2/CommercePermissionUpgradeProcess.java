/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v8_9_2;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Brian I. Kim
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
		_deleteGuestPermission();
		_updateSalesAgentPermission();
	}

	private void _deleteGuestPermission() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
			StringBundler.concat(
				"select companyId, resourcePermissionId, roleId from ",
				"ResourcePermission where name = 'com.liferay.commerce.order' ",
				"and primKey = 'com.liferay.commerce.order' and scope = 4"));

			 ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long roleId = resultSet.getLong(3);

				Role role = _roleLocalService.fetchRole(
					resultSet.getLong(1), RoleConstants.GUEST);

				if ((role != null) && (roleId == role.getRoleId())) {
					_resourcePermissionLocalService.deleteResourcePermission(
						resultSet.getLong(2));
				}
			}
		}
	}

	private void _updateSalesAgentPermission() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
			StringBundler.concat(
				"select companyId, resourcePermissionId, roleId from ",
				"ResourcePermission where name = 'com.liferay.commerce.order' ",
				"and primKey = 'com.liferay.commerce.order' and scope = 1"));

			 ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long roleId = resultSet.getLong(3);

				Role role = _roleLocalService.fetchRole(
					resultSet.getLong(1), "Sales Agent");

				if ((role != null) && (roleId == role.getRoleId())) {
					ResourcePermission resourcePermission =
						_resourcePermissionLocalService.getResourcePermission(
							resultSet.getLong(2));

					ResourceAction resourceAction =
						_resourceActionLocalService.fetchResourceAction(
							"com.liferay.commerce.order", "ADD_COMMERCE_ORDER");

					if ((resourceAction != null) &&
						!_resourcePermissionLocalService.hasActionId(
							resourcePermission, resourceAction)) {

						resourcePermission.addResourceAction(
							resourceAction.getActionId());

						_resourcePermissionLocalService.
							updateResourcePermission(resourcePermission);
					}
				}
			}
		}
	}

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}