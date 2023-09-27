/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseViewActionResourcePermissionUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		long bitwiseValue = _getBitwiseValue();

		processConcurrently(
			StringBundler.concat(
				"select resourcePermissionId, actionIds from ",
				"ResourcePermission where name = ? and primKeyId != ? and ",
				"viewActionId = ?"),
			preparedStatement -> {
				preparedStatement.setString(1, getClassName());
				preparedStatement.setLong(2, 0L);
				preparedStatement.setBoolean(3, true);
			},
			"update ResourcePermission set actionIds = ? where " +
				"resourcePermissionId = ?",
			resultSet -> new Object[] {
				resultSet.getLong("actionIds"),
				resultSet.getLong("resourcePermissionId")
			},
			(values, preparedStatement) -> {
				preparedStatement.setLong(1, bitwiseValue | (Long)values[0]);
				preparedStatement.setLong(2, (Long)values[1]);

				preparedStatement.addBatch();
			},
			getExceptionMessage());
	}

	protected abstract String getActionId();

	protected abstract String getClassName();

	protected abstract String getExceptionMessage();

	private long _getBitwiseValue() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select bitwiseValue from ResourceAction where name = ? and " +
					"actionId = ?")) {

			preparedStatement.setString(1, getClassName());
			preparedStatement.setString(2, getActionId());

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getLong("bitwiseValue");
			}

			return 0;
		}
	}

}