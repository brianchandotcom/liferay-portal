/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.upgrade.v1_3_0;

import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.portal.kernel.model.role.RoleConstants;
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
public class CommercePaymentMethodGroupRelUpgradeProcess
	extends UpgradeProcess {

	public CommercePaymentMethodGroupRelUpgradeProcess(
		ResourceActionLocalService resourceActionLocalService,
		ResourceLocalService resourceLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
		_resourceLocalService = resourceLocalService;
	}

	@Override
	public void doUpgrade() throws Exception {
		_resourceActionLocalService.checkResourceActions(
			CommercePaymentMethodGroupRel.class.getName(),
			Arrays.asList(_OWNER_PERMISSIONS), true);

		String selectCommercePaymentMethodGroupRelSQL =
			"select CPaymentMethodGroupRelId, companyId, groupId, userId " +
				"from CommercePaymentMethodGroupRel";

		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				selectCommercePaymentMethodGroupRelSQL)) {

			while (resultSet.next()) {
				long commercePaymentMethodGroupRelId = resultSet.getLong(
					"CPaymentMethodGroupRelId");
				long companyId = resultSet.getLong("companyId");
				long groupId = resultSet.getLong("groupId");
				long userId = resultSet.getLong("userId");

				ModelPermissions modelPermissions =
					ModelPermissionsFactory.create(
						new String[0], new String[0]);

				modelPermissions.addRolePermissions(
					RoleConstants.OWNER, _OWNER_PERMISSIONS);
				modelPermissions.addRolePermissions(
					RoleConstants.GUEST, "VIEW");
				modelPermissions.addRolePermissions(RoleConstants.USER, "VIEW");

				_resourceLocalService.addModelResources(
					companyId, groupId, userId,
					CommercePaymentMethodGroupRel.class.getName(),
					commercePaymentMethodGroupRelId, modelPermissions);
			}
		}
	}

	private static final String[] _OWNER_PERMISSIONS = {
		"DELETE", "PERMISSIONS", "UPDATE", "VIEW"
	};

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceLocalService _resourceLocalService;

}