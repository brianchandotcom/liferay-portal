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

package com.liferay.commerce.inventory.internal.upgrade.v2_6_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Crescenzo Rega
 */
public class CommercePermissionUpgradeProcess extends UpgradeProcess {

	public CommercePermissionUpgradeProcess(
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
			StringBundler.concat(
				"select companyId, resourcePermissionId, roleId from ",
				"ResourcePermission where name = 'com.liferay.commerce.",
				"inventory.model.CommerceInventoryWarehouse' and scope = 4"));

			 ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long roleId = resultSet.getLong(3);

				Role role = _roleLocalService.fetchRole(
					resultSet.getLong(1), RoleConstants.GUEST);

				if ((role != null) && (roleId == role.getRoleId())) {
					_resourcePermissionLocalService.deleteResourcePermission(
						resultSet.getLong(2));
				}

				role = _roleLocalService.fetchRole(
					resultSet.getLong(1), RoleConstants.SITE_MEMBER);

				if ((role != null) && (roleId == role.getRoleId())) {
					_resourcePermissionLocalService.deleteResourcePermission(
						resultSet.getLong(2));
				}

				role = _roleLocalService.fetchRole(
					resultSet.getLong(1), RoleConstants.USER);

				if ((role != null) && (roleId == role.getRoleId())) {
					_resourcePermissionLocalService.deleteResourcePermission(
						resultSet.getLong(2));
				}
			}
		}
	}

	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}