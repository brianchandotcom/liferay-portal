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
public class SupplierPermissionUpgradeProcess extends UpgradeProcess {

	public SupplierPermissionUpgradeProcess(
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
		SupplierPermissionUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}