/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Igor Costa
 */
public abstract class BaseStaleUserIdUpgradeProcess extends UpgradeProcess {

	public BaseStaleUserIdUpgradeProcess(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected void upgradeUserId(String columnName, String tableName)
		throws Exception {

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select ", tableName, ".", columnName, ", ", tableName,
					".companyId from ", tableName, " left join User_ on ",
					tableName,
					".userId = User_.userId where User_.userId is null"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"update ", tableName, " set userId = ? where ",
						columnName, " = ?"));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				User user = _userLocalService.addDefaultServiceAccountUser(
					resultSet.getLong("companyId"));

				preparedStatement2.setLong(1, user.getUserId());

				preparedStatement2.setLong(2, resultSet.getLong(columnName));

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private final UserLocalService _userLocalService;

}