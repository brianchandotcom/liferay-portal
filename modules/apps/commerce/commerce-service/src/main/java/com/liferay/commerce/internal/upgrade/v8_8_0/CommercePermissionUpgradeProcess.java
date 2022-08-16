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

package com.liferay.commerce.internal.upgrade.v8_8_0;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Danny Situ
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
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(
				"select ResourcePermissionId from ResourcePermission where " +
					"name = 'com.liferay.commerce.account' and scope = 1");

			while (resultSet.next()) {
				ResourcePermission resourcePermission =
					_resourcePermissionLocalService.getResourcePermission(
						resultSet.getLong(1));

				ResourceAction resourceAction =
					_resourceActionLocalService.fetchResourceAction(
						"com.liferay.commerce.account",
						"MANAGE_AVAILABLE_ACCOUNTS");

				if ((resourceAction != null) &&
					_resourcePermissionLocalService.hasActionId(
						resourcePermission, resourceAction)) {

					ResourceAction organizationResourceAction =
						_resourceActionLocalService.fetchResourceAction(
							Organization.class.getName(),
							"MANAGE_AVAILABLE_ACCOUNTS");

					if ((organizationResourceAction != null) &&
						_resourcePermissionLocalService.hasActionId(
							resourcePermission, resourceAction)) {

						Role role = _roleLocalService.getRole(
							resourcePermission.getRoleId());

						_resourcePermissionLocalService.addResourcePermissions(
							Organization.class.getName(), role.getName(),
							resourcePermission.getScope(),
							organizationResourceAction.getBitwiseValue());

						_resourcePermissionLocalService.
							removeResourcePermissions(
								resourcePermission.getCompanyId(),
								resourcePermission.getName(),
								resourcePermission.getScope(),
								resourcePermission.getRoleId(),
								"MANAGE_AVAILABLE_ACCOUNTS");
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