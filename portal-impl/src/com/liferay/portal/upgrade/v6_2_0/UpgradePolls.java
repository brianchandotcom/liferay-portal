/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Daniel Kocsis
 */
public class UpgradePolls extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateChoices();
		updateVotes();
	}

	protected long getGroupId(long questionId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId from PollsQuestion where questionId = ?");

			ps.setLong(1, questionId);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("groupId");
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Unable to find question " + questionId);
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateChoices() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select choiceId, questionId from PollsChoice");

			rs = ps.executeQuery();

			while (rs.next()) {
				long choiceId = rs.getLong("choiceId");
				long questionId = rs.getLong("questionId");

				long groupId = getGroupId(questionId);

				if (groupId > 0) {
					runSQL(
						"update PollsChoice set groupId = " + groupId +
							" where choiceId = " + choiceId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateVotes() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select voteId, questionId from PollsVote");

			rs = ps.executeQuery();

			while (rs.next()) {
				long voteId = rs.getLong("voteId");
				long questionId = rs.getLong("questionId");

				long groupId = getGroupId(questionId);

				if (groupId > 0) {
					runSQL(
						"update PollsVote set groupId = " + groupId +
							" where voteId = " + voteId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradePolls.class);

}