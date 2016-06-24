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

package com.liferay.portal.upgrade.v7_0_2;

import com.liferay.message.boards.kernel.model.MBDiscussion;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Adolfo Pérez
 */
public class UpgradeMessageBoards extends UpgradeProcess {

	protected void cleanMBDiscussion() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long classNameId = PortalUtil.getClassNameId(
				MBDiscussion.class.getName());

			runSQL(
				"delete from AssetEntry where classPK in (" +
					"select messageId from MBMessage where threadId in (" +
						"select threadId from MBThread where categoryId = -1 " +
							"and messagecount = 1 )) and classNameId = " +
								classNameId);

			runSQL(
				"delete from MBMessage where threadId in (" +
					"select threadId from MBThread where categoryId = -1 and " +
						"messagecount = 1)");

			runSQL(
				"delete from MBDiscussion where threadId in (" +
					"select threadId from MBThread where categoryId = -1 and " +
						"messagecount = 1)");

			runSQL(
				"delete from MBThread where categoryId = -1 and " +
					"messagecount = 1");
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		cleanMBDiscussion();
		fillMBDiscussionGroupId();
	}

	protected void fillMBDiscussionGroupId() throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("select MBThread.groupId, MBDiscussion.discussionId from ");
		sb.append("MBDiscussion inner join MBThread on MBDiscussion.threadId ");
		sb.append("= MBThread.threadId where MBDiscussion.groupId = 0");

		try (PreparedStatement ps1 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update MBDiscussion set groupId = ? where discussionId " +
						"= ?");
			PreparedStatement ps2 = connection.prepareStatement(
				sb.toString())) {

			try (ResultSet rs = ps2.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong(1);
					long discussionId = rs.getLong(2);

					ps1.setLong(1, groupId);
					ps1.setLong(2, discussionId);

					ps1.addBatch();
				}

				ps1.executeBatch();
			}
		}
	}

}