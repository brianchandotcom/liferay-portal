/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v8_4_1;

import com.liferay.account.model.AccountGroup;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Danny Situ
 */
public class AccountGroupUpgradeProcess extends UpgradeProcess {

	public AccountGroupUpgradeProcess(
		ResourceLocalService resourceLocalService) {

		_resourceLocalService = resourceLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery(
				"select * from AccountGroup order by accountGroupId asc");

			while (resultSet.next()) {
				_resourceLocalService.addResources(
					resultSet.getLong("companyId"), 0,
					resultSet.getLong("userId"), AccountGroup.class.getName(),
					resultSet.getLong("accountGroupId"), false, false, false);
			}
		}
	}

	private final ResourceLocalService _resourceLocalService;

}