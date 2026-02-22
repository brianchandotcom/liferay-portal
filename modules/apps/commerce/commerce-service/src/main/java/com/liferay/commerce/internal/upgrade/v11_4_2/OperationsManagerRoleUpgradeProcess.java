/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v11_4_2;

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.portal.kernel.exception.PortalException;
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

	private void _addResourcePermission(
			String actionId, long companyId, String name, long roleId)
		throws PortalException {

		if (!_resourcePermissionLocalService.hasResourcePermission(
				companyId, name, ResourceConstants.SCOPE_COMPANY,
				String.valueOf(companyId), roleId, actionId)) {

			_resourcePermissionLocalService.addResourcePermission(
				companyId, name, ResourceConstants.SCOPE_COMPANY,
				String.valueOf(companyId), roleId, actionId);
		}
	}

	private void _updateOperationsManagerPermissions(long companyId)
		throws PortalException {

		Role role = _roleLocalService.fetchRole(
			companyId, "Operations Manager");

		if ((role != null) &&
			!_resourcePermissionLocalService.hasResourcePermission(
				companyId, CPConstants.RESOURCE_NAME_PRODUCT,
				ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
				role.getRoleId(), ActionKeys.ADD_DOCUMENT)) {

			_addResourcePermission(
				ActionKeys.DELETE, companyId, CPMeasurementUnit.class.getName(),
				role.getRoleId());
			_addResourcePermission(
				ActionKeys.PERMISSIONS, companyId,
				CPMeasurementUnit.class.getName(), role.getRoleId());
			_addResourcePermission(
				ActionKeys.UPDATE, companyId, CPMeasurementUnit.class.getName(),
				role.getRoleId());
			_addResourcePermission(
				ActionKeys.VIEW, companyId, CPMeasurementUnit.class.getName(),
				role.getRoleId());
			_addResourcePermission(
				CPActionKeys.ADD_COMMERCE_PRODUCT_MEASUREMENT_UNIT, companyId,
				CPConstants.RESOURCE_NAME_PRODUCT, role.getRoleId());
			_addResourcePermission(
				CPActionKeys.VIEW_COMMERCE_PRODUCT_MEASUREMENT_UNITS, companyId,
				CPConstants.RESOURCE_NAME_PRODUCT, role.getRoleId());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OperationsManagerRoleUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}