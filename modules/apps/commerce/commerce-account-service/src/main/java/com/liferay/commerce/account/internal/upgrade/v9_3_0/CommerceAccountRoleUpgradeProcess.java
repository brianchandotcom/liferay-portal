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

package com.liferay.commerce.account.internal.upgrade.v9_3_0;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Luca Pellizzon
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

		actionIds.add("MANAGE_COMMERCE_ORDER_DELIVERY_TERMS");
		actionIds.add("MANAGE_COMMERCE_ORDER_PAYMENT_TERMS");
		actionIds.add("VIEW_BILLING_ADDRESS");

		_resourceActionLocalService.checkResourceActions(
			"com.liferay.commerce.order", actionIds);

		List<String> orderActionIds = Collections.unmodifiableList(actionIds);

		_companyLocalService.forEachCompanyId(
			companyId -> {
				_updateCommerceAccountRoles(
					companyId,
					CommerceAccountConstants.ROLE_NAME_ACCOUNT_ADMINISTRATOR,
					"com.liferay.commerce.order", orderActionIds);
				_updateCommerceAccountRoles(
					companyId, CommerceAccountConstants.ROLE_NAME_ACCOUNT_BUYER,
					"com.liferay.commerce.order", orderActionIds);
				_updateCommerceAccountRoles(
					companyId,
					CommerceAccountConstants.ROLE_NAME_ACCOUNT_ORDER_MANAGER,
					"com.liferay.commerce.order", orderActionIds);
			});
	}

	private void _addResourcePermission(
			long companyId, Role role, String resourceName, String actionId)
		throws PortalException {

		_resourcePermissionLocalService.addResourcePermission(
			companyId, resourceName, ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			role.getRoleId(), actionId);
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
			_addResourcePermission(companyId, role, resourceName, actionId);
		}
	}

	private final CompanyLocalService _companyLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}