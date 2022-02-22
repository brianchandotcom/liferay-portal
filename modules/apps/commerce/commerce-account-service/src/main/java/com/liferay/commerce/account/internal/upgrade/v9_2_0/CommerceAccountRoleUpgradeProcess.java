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

package com.liferay.commerce.account.internal.upgrade.v9_2_0;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.Arrays;
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

		_companyLocalService.forEachCompany(
			company -> {
				for (String name :
						Arrays.asList(
							CommerceAccountConstants.
								ROLE_NAME_ACCOUNT_ADMINISTRATOR,
							CommerceAccountConstants.ROLE_NAME_ACCOUNT_BUYER,
							CommerceAccountConstants.
								ROLE_NAME_ACCOUNT_ORDER_MANAGER)) {

					Role role = _roleLocalService.fetchRole(
						company.getCompanyId(), name);

					if (role == null) {
						break;
					}

					for (String actionId :
							new String[] {
								"MANAGE_COMMERCE_ORDER_PAYMENT_METHODS",
								"MANAGE_COMMERCE_ORDER_SHIPPING_OPTIONS"
							}) {

						_resourcePermissionLocalService.addResourcePermission(
							company.getCompanyId(),
							"com.liferay.commerce.order",
							ResourceConstants.SCOPE_GROUP_TEMPLATE,
							String.valueOf(
								GroupConstants.DEFAULT_PARENT_GROUP_ID),
							role.getRoleId(), actionId);
					}
				}
			});
	}

	private final CompanyLocalService _companyLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}