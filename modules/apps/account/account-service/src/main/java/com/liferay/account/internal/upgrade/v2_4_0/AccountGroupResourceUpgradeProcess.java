/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.upgrade.v2_4_0;

import com.liferay.account.model.AccountGroup;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pei-Jung Lan
 */
public class AccountGroupResourceUpgradeProcess extends UpgradeProcess {

	public AccountGroupResourceUpgradeProcess(
		ResourceLocalService resourceLocalService) {

		_resourceLocalService = resourceLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select accountGroupId, companyId, userId from AccountGroup");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long accountGroupId = resultSet.getLong("accountGroupId");
				long companyId = resultSet.getLong("companyId");
				long userId = resultSet.getLong("userId");

				_resourceLocalService.addResources(
					companyId, 0, userId, AccountGroup.class.getName(),
					accountGroupId, false, false, false);
			}
		}
	}

	private final ResourceLocalService _resourceLocalService;

}