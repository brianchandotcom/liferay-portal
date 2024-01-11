/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v11_4_2;

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Crescenzo Rega
 */
public class OperationsManagerRoleUpgradeProcess extends UpgradeProcess {

	public OperationsManagerRoleUpgradeProcess(
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
					_updateOperationsManagerPermissions(company.getCompanyId());
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			});
	}

	private void _updateOperationsManagerPermissions(long companyId)
		throws PortalException {

		Role operationsManagerRole = _roleLocalService.fetchRole(
			companyId, "Operations Manager");

		if ((operationsManagerRole != null) &&
			!_resourcePermissionLocalService.hasResourcePermission(
				companyId, "com.liferay.commerce.product", 1,
				String.valueOf(companyId), operationsManagerRole.getRoleId(),
				ActionKeys.ADD_DOCUMENT)) {

			_resourcePermissionLocalService.addResourcePermission(
				companyId, "com.liferay.commerce.product", 1,
				String.valueOf(companyId), operationsManagerRole.getRoleId(),
				CPActionKeys.MANAGE_COMMERCE_PRODUCT_MEASUREMENT_UNITS);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OperationsManagerRoleUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}