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

package com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Queiroz
 */
public class UpgradeDDMFormInstanceRecordVersion extends UpgradeProcess {

	protected void deleteDDLRecordVersion(long recordVersionId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"delete from DDLRecordVersion where recordVersionId = ?")) {

			ps.setLong(1, recordVersionId);

			ps.executeUpdate();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb1 = new StringBundler(5);

		sb1.append("select DDLRecordVersion.* , DDMFormInstance.groupId as ");
		sb1.append("formInstanceGroupId, DDMFormInstance.version as ");
		sb1.append("formInstanceVersion from DDLRecordVersion inner join ");
		sb1.append("DDMFormInstance on DDLRecordVersion.recordSetId = ");
		sb1.append("DDMFormInstance.formInstanceId");

		StringBundler sb = new StringBundler(7);

		sb.append("insert into DDMFormInstanceRecordVersion(");
		sb.append("formInstanceRecordVersionId, groupId, companyId, userId, ");
		sb.append("userName, createDate, formInstanceId, ");
		sb.append("formInstanceVersion, formInstanceRecordId, version, ");
		sb.append("status, statusByUserId, statusByUserName, statusDate, ");
		sb.append("storageId) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		sb.append("?, ?)");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb.toString())) {

			while (rs.next()) {
				long recordVersionId = rs.getLong("recordVersionId");

				ps2.setLong(1, recordVersionId);

				ps2.setLong(2, rs.getLong("formInstanceGroupId"));
				ps2.setLong(3, rs.getLong("companyId"));
				ps2.setLong(4, rs.getLong("userId"));
				ps2.setString(5, rs.getString("userName"));
				ps2.setTimestamp(6, rs.getTimestamp("createDate"));
				ps2.setLong(7, rs.getLong("recordSetId"));
				ps2.setString(8, rs.getString("formInstanceVersion"));
				ps2.setLong(9, rs.getLong("recordId"));
				ps2.setString(10, rs.getString("version"));
				ps2.setInt(11, rs.getInt("status"));
				ps2.setLong(12, rs.getLong("statusByUserId"));
				ps2.setString(13, rs.getString("statusByUserName"));
				ps2.setTimestamp(14, rs.getTimestamp("statusDate"));
				ps2.setInt(15, rs.getInt("DDMStorageId"));

				deleteDDLRecordVersion(recordVersionId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}