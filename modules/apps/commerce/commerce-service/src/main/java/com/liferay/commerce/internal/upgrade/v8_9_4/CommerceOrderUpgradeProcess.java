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

package com.liferay.commerce.internal.upgrade.v8_9_4;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Collections;

/**
 * @author Crescenzo Rega
 */
public class CommerceOrderUpgradeProcess extends UpgradeProcess {

	public CommerceOrderUpgradeProcess(
		CompanyLocalService companyLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourceLocalService resourceLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_companyLocalService = companyLocalService;
		_resourceActionLocalService = resourceActionLocalService;
		_resourceLocalService = resourceLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	@Override
	public void doUpgrade() throws Exception {
		_updateCommerceOrder();
		_updateOperationsManagerPermission();
	}

	private void _updateCommerceOrder() throws Exception {
		_resourceActionLocalService.checkResourceActions(
			CommerceOrder.class.getName(), Collections.singletonList("VIEW"),
			true);

		String selectCommerceOrderSQL =
			"select companyId, commerceOrderId from CommerceOrder";

		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(selectCommerceOrderSQL)) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");

				Company company = _companyLocalService.getCompany(companyId);

				long commerceOrderId = resultSet.getLong("commerceOrderId");

				_resourceLocalService.updateResources(
					companyId, company.getGroupId(),
					CommerceOrder.class.getName(), commerceOrderId,
					new String[] {"VIEW"}, new String[] {"VIEW"});
			}
		}
	}

	private void _updateOperationsManagerPermission() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				Role role = _roleLocalService.fetchRole(
					companyId, "Operations Manager");

				if (role == null) {
					return;
				}

				_resourcePermissionLocalService.addResourcePermission(
					companyId, CommerceOrder.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
					role.getRoleId(), ActionKeys.VIEW);
			});
	}

	private final CompanyLocalService _companyLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceLocalService _resourceLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}