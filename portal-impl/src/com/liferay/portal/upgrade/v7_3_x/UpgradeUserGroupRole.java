/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_3_x;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Preston Crary
 */
public class UpgradeUserGroupRole extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		String normalizedTableName = dbInspector.normalizeName(
			"UserGroupRole", databaseMetaData);

		try (ResultSet resultSet = databaseMetaData.getColumns(
				dbInspector.getCatalog(), dbInspector.getSchema(),
				normalizedTableName,
				dbInspector.normalizeName(
					"userGroupRoleId", databaseMetaData))) {

			if (resultSet.next()) {
				return;
			}
		}

		removePrimaryKey("UserGroupRole");

		alterTableAddColumn(
			"UserGroupRole", "userGroupRoleId", "LONG default 0 not null");

		long userGroupRoleId = 0;

		try (PreparedStatement selectPreparedStatement =
				connection.prepareStatement(
					"select userId, groupId, roleId from UserGroupRole");
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update UserGroupRole set userGroupRoleId = ? where " +
						"userId = ? and groupId = ? and roleId = ?");
			ResultSet resultSet = selectPreparedStatement.executeQuery()) {

			while (resultSet.next()) {
				updatePreparedStatement.setLong(1, ++userGroupRoleId);
				updatePreparedStatement.setLong(2, resultSet.getLong(1));
				updatePreparedStatement.setLong(3, resultSet.getLong(2));
				updatePreparedStatement.setLong(4, resultSet.getLong(3));

				updatePreparedStatement.addBatch();
			}

			updatePreparedStatement.executeBatch();
		}

		if (userGroupRoleId > 0) {
			runSQL(
				StringBundler.concat(
					"insert into Counter (name, currentId) values ('",
					UserGroupRole.class.getName(), "', ", userGroupRoleId,
					")"));
		}

		runSQL(
			StringBundler.concat(
				"alter table ", normalizedTableName,
				" add primary key (userGroupRoleId)"));
	}

}