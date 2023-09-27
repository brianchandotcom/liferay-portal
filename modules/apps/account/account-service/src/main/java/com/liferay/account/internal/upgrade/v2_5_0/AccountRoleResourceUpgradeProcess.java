/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v2_5_0;

import com.liferay.account.model.AccountGroup;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pei-Jung Lan
 */
public class AccountRoleResourceUpgradeProcess extends UpgradeProcess {

	public AccountRoleResourceUpgradeProcess(
		ResourceLocalService resourceLocalService) {

		_resourceLocalService = resourceLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select AccountRole.accountRoleId, Role_.companyId, ",
					"Role_.userId from AccountRole inner join Role_ on ",
					"AccountRole.roleId = Role_.roleId"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long accountRoleId = resultSet.getLong("accountRoleId");
				long companyId = resultSet.getLong("companyId");
				long userId = resultSet.getLong("userId");

				_resourceLocalService.addResources(
					companyId, 0, userId, AccountGroup.class.getName(),
					accountRoleId, false, false, false);
			}
		}
	}

	private final ResourceLocalService _resourceLocalService;

}