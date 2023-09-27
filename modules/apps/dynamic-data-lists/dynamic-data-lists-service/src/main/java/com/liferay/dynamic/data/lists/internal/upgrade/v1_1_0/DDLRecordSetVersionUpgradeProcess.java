/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.upgrade.v1_1_0;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Queiroz
 */
public class DDLRecordSetVersionUpgradeProcess extends UpgradeProcess {

	public DDLRecordSetVersionUpgradeProcess(
		CounterLocalService counterLocalService) {

		_counterLocalService = counterLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDLRecordSet.*, TEMP_TABLE.structureVersionId ",
					"from DDLRecordSet inner join (select structureId, ",
					"max(structureVersionId) as structureVersionId from ",
					"DDMStructureVersion group by ",
					"DDMStructureVersion.structureId) TEMP_TABLE on ",
					"DDLRecordSet.DDMStructureId = TEMP_TABLE.structureId ",
					"where scope != 2"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into DDLRecordSetVersion (recordSetVersionId, ",
						"groupId, companyId, userId, userName, createDate, ",
						"recordSetId, DDMStructureVersionId, name, ",
						"description, settings_, version,  status, ",
						"statusByUserId, statusByUserName, statusDate) values ",
						"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					preparedStatement2.setLong(
						1, _counterLocalService.increment());
					preparedStatement2.setLong(2, resultSet.getLong("groupId"));
					preparedStatement2.setLong(
						3, resultSet.getLong("companyId"));
					preparedStatement2.setLong(4, resultSet.getLong("userId"));
					preparedStatement2.setString(
						5, resultSet.getString("userName"));
					preparedStatement2.setTimestamp(
						6, resultSet.getTimestamp("createDate"));
					preparedStatement2.setLong(
						7, resultSet.getLong("recordSetId"));
					preparedStatement2.setLong(
						8, resultSet.getLong("structureVersionId"));
					preparedStatement2.setString(
						9, resultSet.getString("name"));
					preparedStatement2.setString(
						10, resultSet.getString("description"));
					preparedStatement2.setString(
						11, resultSet.getString("settings_"));
					preparedStatement2.setString(
						12, DDLRecordSetConstants.VERSION_DEFAULT);
					preparedStatement2.setInt(
						13, WorkflowConstants.STATUS_APPROVED);
					preparedStatement2.setLong(14, resultSet.getLong("userId"));
					preparedStatement2.setString(
						15, resultSet.getString("userName"));
					preparedStatement2.setTimestamp(
						16, resultSet.getTimestamp("modifiedDate"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private final CounterLocalService _counterLocalService;

}