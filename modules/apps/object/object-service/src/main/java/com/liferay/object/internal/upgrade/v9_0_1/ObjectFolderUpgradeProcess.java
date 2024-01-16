/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v9_0_1;

import com.liferay.object.model.ObjectFolder;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Guilherme Sa
 */
public class ObjectFolderUpgradeProcess extends UpgradeProcess {

	public ObjectFolderUpgradeProcess(
		CompanyLocalService companyLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_companyLocalService = companyLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				try {
					PreparedStatement preparedStatement =
						connection.prepareStatement(
							"select objectFolderId from ObjectFolder where " +
								"companyId = ? and externalReferenceCode = ?");

					preparedStatement.setLong(1, company.getCompanyId());
					preparedStatement.setString(2, "uncategorized");

					ResultSet resultSet = preparedStatement.executeQuery();

					while (resultSet.next()) {
						Role guestRole = _roleLocalService.getRole(
							company.getCompanyId(), RoleConstants.GUEST);

						_resourcePermissionLocalService.setResourcePermissions(
							company.getCompanyId(),
							ObjectFolder.class.getName(),
							ResourceConstants.SCOPE_INDIVIDUAL,
							String.valueOf(resultSet.getLong("objectFolderId")),
							guestRole.getRoleId(),
							new String[] {ActionKeys.VIEW});
					}
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectFolderUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}