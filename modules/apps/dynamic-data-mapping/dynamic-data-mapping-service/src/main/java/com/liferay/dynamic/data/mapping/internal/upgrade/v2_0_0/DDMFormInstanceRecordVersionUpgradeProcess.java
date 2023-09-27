/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Queiroz
 */
public class DDMFormInstanceRecordVersionUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDLRecordVersion.* , DDMFormInstance.groupId as ",
					"formInstanceGroupId, DDMFormInstance.version as ",
					"formInstanceVersion from DDLRecordVersion inner join ",
					"DDMFormInstance on DDLRecordVersion.recordSetId = ",
					"DDMFormInstance.formInstanceId"));
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into DDMFormInstanceRecordVersion(",
						"formInstanceRecordVersionId, groupId, companyId, ",
						"userId, userName, createDate, formInstanceId, ",
						"formInstanceVersion, formInstanceRecordId, version, ",
						"status, statusByUserId, statusByUserName, ",
						"statusDate, storageId) values(?, ?, ?, ?, ?, ?, ?, ",
						"?, ?, ?, ?, ?, ?, ?, ?)"))) {

			while (resultSet.next()) {
				long recordVersionId = resultSet.getLong("recordVersionId");

				preparedStatement2.setLong(1, recordVersionId);

				preparedStatement2.setLong(
					2, resultSet.getLong("formInstanceGroupId"));
				preparedStatement2.setLong(3, resultSet.getLong("companyId"));
				preparedStatement2.setLong(4, resultSet.getLong("userId"));
				preparedStatement2.setString(
					5, resultSet.getString("userName"));
				preparedStatement2.setTimestamp(
					6, resultSet.getTimestamp("createDate"));
				preparedStatement2.setLong(7, resultSet.getLong("recordSetId"));
				preparedStatement2.setString(
					8, resultSet.getString("formInstanceVersion"));
				preparedStatement2.setLong(9, resultSet.getLong("recordId"));
				preparedStatement2.setString(
					10, resultSet.getString("version"));
				preparedStatement2.setInt(11, resultSet.getInt("status"));
				preparedStatement2.setLong(
					12, resultSet.getLong("statusByUserId"));
				preparedStatement2.setString(
					13, resultSet.getString("statusByUserName"));
				preparedStatement2.setTimestamp(
					14, resultSet.getTimestamp("statusDate"));
				preparedStatement2.setLong(
					15, resultSet.getLong("DDMStorageId"));

				_deleteDDLRecordVersion(recordVersionId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private void _deleteDDLRecordVersion(long recordVersionId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from DDLRecordVersion where recordVersionId = ?")) {

			preparedStatement.setLong(1, recordVersionId);

			preparedStatement.executeUpdate();
		}
	}

}