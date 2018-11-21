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

package com.liferay.dynamic.data.lists.internal.upgrade.v1_1_0;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Queiroz
 */
public class UpgradeDDLRecordSetVersion extends UpgradeProcess {

	public UpgradeDDLRecordSetVersion(CounterLocalService counterLocalService) {
		_counterLocalService = counterLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb1 = new StringBundler(7);

		sb1.append("select DDLRecordSet.*, TEMP_TABLE.structureVersionId ");
		sb1.append("from DDLRecordSet inner join (select structureId, ");
		sb1.append("max(structureVersionId) as structureVersionId from ");
		sb1.append("DDMStructureVersion group by ");
		sb1.append("DDMStructureVersion.structureId) TEMP_TABLE on ");
		sb1.append("DDLRecordSet.DDMStructureId = TEMP_TABLE.structureId ");
		sb1.append("where scope != 2");

		StringBundler sb2 = new StringBundler(6);

		sb2.append("insert into DDLRecordSetVersion (recordSetVersionId, ");
		sb2.append("groupId, companyId, userId, userName, createDate, ");
		sb2.append("recordSetId, DDMStructureVersionId, name, description, ");
		sb2.append("settings_, version,  status, statusByUserId, ");
		sb2.append("statusByUserName, statusDate) values (?, ?, ?, ?, ?, ?, ");
		sb2.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb2.toString())) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					ps2.setLong(1, _counterLocalService.increment());
					ps2.setLong(2, rs.getLong("groupId"));
					ps2.setLong(3, rs.getLong("companyId"));
					ps2.setLong(4, rs.getLong("userId"));
					ps2.setString(5, rs.getString("userName"));
					ps2.setTimestamp(6, rs.getTimestamp("createDate"));
					ps2.setLong(7, rs.getLong("recordSetId"));
					ps2.setLong(8, rs.getLong("structureVersionId"));
					ps2.setString(9, rs.getString("name"));
					ps2.setString(10, rs.getString("description"));
					ps2.setString(11, rs.getString("settings_"));
					ps2.setString(12, DDLRecordSetConstants.VERSION_DEFAULT);
					ps2.setInt(13, WorkflowConstants.STATUS_APPROVED);
					ps2.setLong(14, rs.getLong("userId"));
					ps2.setString(15, rs.getString("userName"));
					ps2.setTimestamp(16, rs.getTimestamp("modifiedDate"));

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private final CounterLocalService _counterLocalService;

}