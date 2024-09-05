/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v11_5_1;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Riccardo Alberti
 */
public class SupplierRoleUpgradeProcess extends UpgradeProcess {

	public SupplierRoleUpgradeProcess(
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
					_updateSupplierPermissions(company.getCompanyId());
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			});
	}

	private void _updateSupplierPermissions(long companyId)
		throws PortalException {

		Role role = _roleLocalService.fetchRole(
			companyId, AccountRoleConstants.ROLE_NAME_ACCOUNT_SUPPLIER);

		if (role == null) {
			return;
		}

		_resourcePermissionLocalService.addResourcePermission(
			companyId, AccountEntry.class.getName(), 3,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			role.getRoleId(), AccountActionKeys.VIEW_ACCOUNT_GROUPS);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SupplierRoleUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}