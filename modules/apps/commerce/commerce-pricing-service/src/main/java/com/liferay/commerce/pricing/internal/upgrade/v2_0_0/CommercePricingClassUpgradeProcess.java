/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.internal.upgrade.v2_0_0;

import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

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
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropColumns("CommercePricingClass", "groupId")
		};
	}

	private static final String[] _OWNER_PERMISSIONS = {
		"DELETE", "PERMISSIONS", "UPDATE", "VIEW"
	};

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceLocalService _resourceLocalService;

}