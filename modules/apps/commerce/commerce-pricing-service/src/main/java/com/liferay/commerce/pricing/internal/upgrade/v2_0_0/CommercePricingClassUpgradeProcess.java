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

package com.liferay.commerce.pricing.internal.upgrade.v2_0_0;

import com.liferay.commerce.pricing.internal.upgrade.v1_1_0.util.CommercePricingClassTable;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.model.impl.CommercePricingClassImpl;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Arrays;

/**
 * @author Riccardo Alberti
 */
public class CommercePricingClassUpgradeProcess extends UpgradeProcess {

	public CommercePricingClassUpgradeProcess(
		ResourceActionLocalService resourceActionLocalService,
		ResourceLocalService resourceLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
		_resourceLocalService = resourceLocalService;
	}

	@Override
	public void doUpgrade() throws Exception {
		_resourceActionLocalService.checkResourceActions(
			CommercePricingClass.class.getName(),
			Arrays.asList(_OWNER_PERMISSIONS));

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			new String[0], new String[0]);

		modelPermissions.addRolePermissions(
			RoleConstants.OWNER, _OWNER_PERMISSIONS);

		String selectCommercePricingClassSQL =
			"select companyId, groupId, userId, commercePricingClassId from " +
				"CommercePricingClass";

		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				selectCommercePricingClassSQL)) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");

				long groupId = resultSet.getLong("groupId");

				long userId = resultSet.getLong("userId");

				long commercePricingClassId = resultSet.getLong(
					"commercePricingClassId");

				_resourceLocalService.addModelResources(
					companyId, groupId, userId,
					CommercePricingClass.class.getName(),
					commercePricingClassId, modelPermissions);
			}
		}

		if (hasColumn(CommercePricingClassImpl.TABLE_NAME, "groupId")) {
			alter(
				CommercePricingClassTable.class,
				new AlterTableDropColumn("groupId"));
		}
	}

	private static final String[] _OWNER_PERMISSIONS = {
		"DELETE", "PERMISSIONS", "UPDATE", "VIEW"
	};

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceLocalService _resourceLocalService;

}