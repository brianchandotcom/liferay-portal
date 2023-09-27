/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v9_6_1;

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
 * @author Luca Pellizzon
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

		Role accountSupplierRole = _roleLocalService.fetchRole(
			companyId, "Supplier");

		if (accountSupplierRole != null) {
			if (_resourcePermissionLocalService.hasResourcePermission(
					companyId,
					"com_liferay_commerce_pricing_web_internal_portlet_" +
						"CommerceDiscountPortlet",
					1, String.valueOf(companyId),
					accountSupplierRole.getRoleId(),
					ActionKeys.ACCESS_IN_CONTROL_PANEL)) {

				_resourcePermissionLocalService.removeResourcePermission(
					companyId,
					"com_liferay_commerce_pricing_web_internal_portlet_" +
						"CommerceDiscountPortlet",
					1, String.valueOf(companyId),
					accountSupplierRole.getRoleId(),
					ActionKeys.ACCESS_IN_CONTROL_PANEL);
			}

			if (_resourcePermissionLocalService.hasResourcePermission(
					companyId,
					"com_liferay_commerce_inventory_web_internal_portlet_" +
						"CommerceInventoryPortlet",
					1, String.valueOf(companyId),
					accountSupplierRole.getRoleId(),
					ActionKeys.ACCESS_IN_CONTROL_PANEL)) {

				_resourcePermissionLocalService.removeResourcePermission(
					companyId,
					"com_liferay_commerce_inventory_web_internal_portlet_" +
						"CommerceInventoryPortlet",
					1, String.valueOf(companyId),
					accountSupplierRole.getRoleId(),
					ActionKeys.ACCESS_IN_CONTROL_PANEL);
			}

			if (_resourcePermissionLocalService.hasResourcePermission(
					companyId,
					"com_liferay_commerce_warehouse_web_internal_portlet_" +
						"CommerceInventoryWarehousePortlet",
					1, String.valueOf(companyId),
					accountSupplierRole.getRoleId(),
					ActionKeys.ACCESS_IN_CONTROL_PANEL)) {

				_resourcePermissionLocalService.removeResourcePermission(
					companyId,
					"com_liferay_commerce_warehouse_web_internal_portlet_" +
						"CommerceInventoryWarehousePortlet",
					1, String.valueOf(companyId),
					accountSupplierRole.getRoleId(),
					ActionKeys.ACCESS_IN_CONTROL_PANEL);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SupplierRoleUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}