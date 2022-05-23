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

package com.liferay.commerce.account.internal.upgrade.v9_4_0;

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
		String accountGroupSQL =
			"select * from AccountGroup order by accountGroupId asc";

		try (Statement selectStatement = connection.createStatement()) {
			ResultSet resultSet = selectStatement.executeQuery(accountGroupSQL);

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