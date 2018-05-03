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

package com.liferay.message.boards.internal.upgrade.v1_1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Adolfo Pérez
 */
public class UpgradeMBThread extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn("MBThread", "title")) {
			runSQL("alter table MBThread add title STRING null");
		}

		try (PreparedStatement ps1 = connection.prepareStatement(
				StringBundler.concat(
					"select MBThread.threadId, MBMessage.subject from ",
					"MBThread inner join MBMessage on MBThread.rootMessageId ",
					"= MBMessage.messageId and MBThread.threadId = ",
					"MBMessage.threadId"));
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update MBThread set title = ? where threadId = ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				String title = rs.getString(2);

				ps2.setString(1, title);

				long threadId = rs.getLong(1);

				ps2.setLong(2, threadId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

}