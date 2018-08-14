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

package com.liferay.dynamic.data.lists.internal.upgrade.v1_0_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Queiroz
 */
public class UpgradeRecordGroup extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append("select DDLRecordSet.groupId, DDLRecord.recordId from ");
		sb.append("DDLRecord inner join DDLRecordSet on ");
		sb.append("DDLRecord.recordSetId = DDLRecordSet.recordSetId where ");
		sb.append("DDLRecord.groupId != DDLRecordSet.groupId");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDLRecord set groupId = ? where recordId = ?")) {

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long recordId = rs.getLong("recordId");

				ps2.setLong(1, groupId);
				ps2.setLong(2, recordId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}