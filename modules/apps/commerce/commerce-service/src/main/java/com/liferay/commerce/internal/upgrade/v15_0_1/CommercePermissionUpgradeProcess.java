/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v15_0_1;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.Arrays;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePermissionUpgradeProcess extends UpgradeProcess {

	public CommercePermissionUpgradeProcess(
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
		List<String> actionIds = Arrays.asList(
			CommerceOrderActionKeys.MANAGE_ACCOUNTS_SCOPED_COMMERCE_ORDERS);

		_resourceActionLocalService.checkResourceActions(
			CommerceOrderConstants.RESOURCE_NAME, actionIds);

		_companyLocalService.forEachCompanyId(
			companyId -> {
				try {
					Role role = _roleLocalService.fetchRole(
						companyId,
						AccountRoleConstants.ROLE_NAME_ACCOUNT_ORDER_MANAGER);

					if (role == null) {
						return;
					}

					for (String actionId : actionIds) {
						_addResourcePermission(
							actionId, companyId,
							String.valueOf(
								GroupConstants.DEFAULT_PARENT_GROUP_ID),
							CommerceOrderConstants.RESOURCE_NAME,
							role.getRoleId(),
							ResourceConstants.SCOPE_GROUP_TEMPLATE);
					}
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			});
	}

	private void _addResourcePermission(
			String actionId, long companyId, String primKey,
			String resourceName, long roleId, int scope)
		throws PortalException {

		if (!_resourcePermissionLocalService.hasResourcePermission(
				companyId, resourceName, scope, primKey, roleId, actionId)) {

			_resourcePermissionLocalService.addResourcePermission(
				companyId, resourceName, scope, primKey, roleId, actionId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePermissionUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}