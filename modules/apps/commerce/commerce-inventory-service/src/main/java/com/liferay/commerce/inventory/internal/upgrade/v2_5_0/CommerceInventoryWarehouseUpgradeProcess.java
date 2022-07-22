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

package com.liferay.commerce.inventory.internal.upgrade.v2_5_0;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Arrays;

/**
 * @author Riccardo Alberti
 */
public class CommerceInventoryWarehouseUpgradeProcess extends UpgradeProcess {

	public CommerceInventoryWarehouseUpgradeProcess(
		CompanyLocalService companyLocalService,
		ResourceActionLocalService resourceActionLocalService,
		ResourceLocalService resourceLocalService) {

		_companyLocalService = companyLocalService;
		_resourceActionLocalService = resourceActionLocalService;
		_resourceLocalService = resourceLocalService;
	}

	@Override
	public void doUpgrade() throws Exception {
		_resourceActionLocalService.checkResourceActions(
			CommerceInventoryWarehouse.class.getName(),
			Arrays.asList(_OWNER_PERMISSIONS), true);

		String selectCommerceInventoryWarehouseSQL =
			"select companyId, CIWarehouseId from CIWarehouse";

		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				selectCommerceInventoryWarehouseSQL)) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");

				Company company = _companyLocalService.getCompany(companyId);

				long commerceInventoryWarehouseId = resultSet.getLong(
					"CIWarehouseId");

				_resourceLocalService.updateResources(
					companyId, company.getGroupId(),
					CommerceInventoryWarehouse.class.getName(),
					commerceInventoryWarehouseId, new String[] {"VIEW"},
					new String[] {"VIEW"});
			}
		}
	}

	private static final String[] _OWNER_PERMISSIONS = {
		"DELETE", "PERMISSIONS", "UPDATE", "VIEW"
	};

	private final CompanyLocalService _companyLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceLocalService _resourceLocalService;

}