/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v15_0_3;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Crescenzo Rega
 */
public class OrderAdministratorRoleUpgradeProcess extends UpgradeProcess {

	public OrderAdministratorRoleUpgradeProcess(
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
					long companyId = company.getCompanyId();

					Role role = _roleLocalService.fetchRole(
						companyId,
						AccountRoleConstants.ROLE_NAME_ORDER_ADMINISTRATOR);

					if (role == null) {
						return;
					}

					_addResourcePermission(
						ActionKeys.VIEW, companyId,
						CommerceOrder.class.getName(),
						String.valueOf(companyId), role.getRoleId(),
						ResourceConstants.SCOPE_COMPANY);
					_addResourcePermission(
						CommerceOrderActionKeys.MANAGE_COMMERCE_ORDERS,
						companyId, CommerceOrderConstants.RESOURCE_NAME,
						String.valueOf(companyId), role.getRoleId(),
						ResourceConstants.SCOPE_COMPANY);
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			});
	}

	private void _addResourcePermission(
			String actionId, long companyId, String name, String primKey,
			long roleId, int scope)
		throws Exception {

		if (!_resourcePermissionLocalService.hasResourcePermission(
				companyId, name, scope, primKey, roleId, actionId)) {

			_resourcePermissionLocalService.addResourcePermission(
				companyId, name, scope, primKey, roleId, actionId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OrderAdministratorRoleUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}