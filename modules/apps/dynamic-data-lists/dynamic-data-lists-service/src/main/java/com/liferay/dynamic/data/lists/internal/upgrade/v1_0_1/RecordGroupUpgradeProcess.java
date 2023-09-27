/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class RecordGroupUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDLRecordSet.groupId, DDLRecord.recordId from ",
					"DDLRecord inner join DDLRecordSet on ",
					"DDLRecord.recordSetId = DDLRecordSet.recordSetId where ",
					"DDLRecord.groupId != DDLRecordSet.groupId"));
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDLRecord set groupId = ? where recordId = ?")) {

			while (resultSet.next()) {
				long groupId = resultSet.getLong("groupId");
				long recordId = resultSet.getLong("recordId");

				preparedStatement2.setLong(1, groupId);
				preparedStatement2.setLong(2, recordId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}