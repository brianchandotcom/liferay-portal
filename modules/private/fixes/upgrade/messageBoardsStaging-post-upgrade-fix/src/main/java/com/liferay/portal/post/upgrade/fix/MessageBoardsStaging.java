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

package com.liferay.portal.post.upgrade.fix;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.osgi.service.component.annotations.Component;

/**
 * <p>
 * Post upgrade fix for https://issues.liferay.com/browse/LPS-66599.
 * </p>
 *
 * @author Alberto Chaparro
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=execute",
		"osgi.command.scope=messageBoardsStagingPostUpgradeFix"
	},
	service = MessageBoardsStaging.class
)
public class MessageBoardsStaging {

	public void execute() {
		if (_log.isInfoEnabled()) {
			_log.info("Executing MessageBoardsStagingPostUpgradeFix");
		}

		StringBundler sb = new StringBundler();

		sb.append("select MBThread.groupId, MBDiscussion.discussionId from ");
		sb.append("MBDiscussion inner join MBThread on MBDiscussion.threadId ");
		sb.append("= MBThread.threadId where MBDiscussion.groupId = 0");

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement ps1 =
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

			if (_log.isInfoEnabled()) {
				_log.info(
					"MessageBoardsStagingPostUpgradeFix execution finished");
			}
		}
		catch (Exception e) {
			_log.error(
				"An exception occurred during " +
					"MessageBoardsStagingPostUpgradeFix execution",
				e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MessageBoardsStaging.class);

}