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

package com.liferay.commerce.internal.upgrade.v8_1_2;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.Collections;

/**
 * @author Ivica Cardic
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
		_resourceActionLocalService.checkResourceActions(
			"com.liferay.commerce.order",
			Collections.singletonList("MANAGE_COMMERCE_ORDER_PAYMENT_METHODS"));

		_companyLocalService.forEachCompanyId(
			companyId -> {
				_updateCommerceAccountRoles(
					companyId,
					AccountRoleConstants.
						REQUIRED_ROLE_NAME_ACCOUNT_ADMINISTRATOR);
				_updateCommerceAccountRoles(
					companyId, AccountRoleConstants.ROLE_NAME_ACCOUNT_BUYER);
				_updateCommerceAccountRoles(
					companyId,
					AccountRoleConstants.ROLE_NAME_ACCOUNT_ORDER_MANAGER);
			});
	}

	private void _addResourcePermission(
			long companyId, Role role, String actionId)
		throws PortalException {

		_resourcePermissionLocalService.addResourcePermission(
			companyId, "com.liferay.commerce.order",
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			role.getRoleId(), actionId);
	}

	private void _updateCommerceAccountRoles(long companyId, String name)
		throws PortalException {

		Role role = _roleLocalService.fetchRole(companyId, name);

		if (role == null) {
			return;
		}

		_addResourcePermission(
			companyId, role, "MANAGE_COMMERCE_ORDER_PAYMENT_METHODS");
		_addResourcePermission(
			companyId, role, "MANAGE_COMMERCE_ORDER_SHIPPING_OPTIONS");
	}

	private final CompanyLocalService _companyLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}