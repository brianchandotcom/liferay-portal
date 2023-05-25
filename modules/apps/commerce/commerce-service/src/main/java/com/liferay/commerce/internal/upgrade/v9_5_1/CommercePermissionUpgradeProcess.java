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

package com.liferay.commerce.internal.upgrade.v9_5_1;

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
		_updateSalesAgentPermission();
	}

	private void _updateSalesAgentPermission() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
			StringBundler.concat(
				"select companyId, resourcePermissionId, roleId from ",
				"ResourcePermission where name = 'com.liferay.commerce.order' ",
				"and scope = 1"));

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
							"com.liferay.commerce.order", "MANAGE_QUOTES");

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