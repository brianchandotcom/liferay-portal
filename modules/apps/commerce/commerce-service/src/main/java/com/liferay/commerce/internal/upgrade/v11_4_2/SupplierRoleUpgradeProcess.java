/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v11_4_2;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Andrea Sbarra
 */
public class SupplierRoleUpgradeProcess extends UpgradeProcess {

	public SupplierRoleUpgradeProcess(
		CompanyLocalService companyLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_companyLocalService = companyLocalService;
		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> _updateSupplierRolePermissions(
				companyId, _ROLE_NAME_SUPPLIER, _RESOURCE_NAME,
				ListUtil.fromString(ActionKeys.VIEW)));
	}

	private void _addResourcePermission(
			long companyId, Role role, String resourceName, String actionId)
		throws PortalException {

		_resourcePermissionLocalService.addResourcePermission(
			companyId, resourceName, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			role.getRoleId(), actionId);
	}

	private void _updateSupplierRolePermissions(
			long companyId, String name, String resourceName,
			List<String> actionIds)
		throws PortalException {

		_resourceActionLocalService.checkResourceActions(
			resourceName, actionIds);

		Role role = _roleLocalService.fetchRole(companyId, name);

		if (role == null) {
			return;
		}

		for (String actionId : actionIds) {
			_addResourcePermission(companyId, role, resourceName, actionId);
		}
	}

	private static final String _RESOURCE_NAME =
		"com.liferay.commerce.price.list.model.CommercePriceList";

	private static final String _ROLE_NAME_SUPPLIER = "Supplier";

	private final CompanyLocalService _companyLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}