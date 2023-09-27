/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v8_9_5;

import com.liferay.account.constants.AccountRoleConstants;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stefano Motta
 */
public class CommerceAccountRoleUpgradeProcess extends UpgradeProcess {

	public CommerceAccountRoleUpgradeProcess(
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
		List<String> actionIds = new ArrayList<>();

		actionIds.add(ActionKeys.VIEW);

		_resourceActionLocalService.checkResourceActions(
			"com.liferay.commerce.product.model.CommerceCatalog", actionIds);

		_companyLocalService.forEachCompanyId(
			companyId -> _updateCommerceAccountRoles(
				companyId,
				AccountRoleConstants.ROLE_NAME_ACCOUNT_DISCOUNT_MANAGER,
				"com.liferay.commerce.product.model.CommerceCatalog",
				actionIds));
	}

	private void _updateCommerceAccountRoles(
			long companyId, String name, String resourceName,
			List<String> actionIds)
		throws PortalException {

		Role role = _roleLocalService.fetchRole(companyId, name);

		if (role == null) {
			return;
		}

		for (String actionId : actionIds) {
			_resourcePermissionLocalService.addResourcePermission(
				companyId, resourceName, ResourceConstants.SCOPE_COMPANY,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
				role.getRoleId(), actionId);
		}
	}

	private final CompanyLocalService _companyLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}