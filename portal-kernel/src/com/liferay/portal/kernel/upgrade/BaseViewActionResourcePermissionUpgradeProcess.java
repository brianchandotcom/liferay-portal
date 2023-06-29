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