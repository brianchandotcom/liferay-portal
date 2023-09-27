/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.upgrade.v2_1_2;

import com.liferay.commerce.price.list.model.CommercePriceList;
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
public class CommercePriceListUpgradeProcess extends UpgradeProcess {

	public CommercePriceListUpgradeProcess(
		ResourceActionLocalService resourceActionLocalService,
		ResourceLocalService resourceLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
		_resourceLocalService = resourceLocalService;
	}

	@Override
	public void doUpgrade() throws Exception {
		_resourceActionLocalService.checkResourceActions(
			CommercePriceList.class.getName(),
			Arrays.asList(_OWNER_PERMISSIONS));

		ModelPermissions modelPermissions = ModelPermissionsFactory.create(
			new String[0], new String[0]);

		modelPermissions.addRolePermissions(
			RoleConstants.OWNER, _OWNER_PERMISSIONS);

		String selectCommercePriceListSQL =
			"select companyId, groupId, userId, commercePriceListId from " +
				"CommercePriceList";

		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(selectCommercePriceListSQL)) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong("companyId");

				long groupId = resultSet.getLong("groupId");

				long userId = resultSet.getLong("userId");

				long commercePriceListId = resultSet.getLong(
					"commercePriceListId");

				_resourceLocalService.addModelResources(
					companyId, groupId, userId,
					CommercePriceList.class.getName(), commercePriceListId,
					modelPermissions);
			}
		}
	}

	private static final String[] _OWNER_PERMISSIONS = {
		"DELETE", "PERMISSIONS", "UPDATE", "VIEW"
	};

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceLocalService _resourceLocalService;

}