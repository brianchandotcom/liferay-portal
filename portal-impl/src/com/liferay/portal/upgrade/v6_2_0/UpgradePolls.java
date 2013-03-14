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
import java.sql.Timestamp;

/**
 * @author Daniel Kocsis
 */
public class UpgradePolls extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateChoices();
		updateVotes();
	}

	protected Object[] getQuestionArray(long questionId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId, companyId, userId, userName, createDate, " +
					"modifiedDate from PollsQuestion where questionId = ?");

			ps.setLong(1, questionId);

			rs = ps.executeQuery();

			if (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("companyId");
				long userName = rs.getLong("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");

				return new Object[] {
					groupId, companyId, userId, userName, createDate,
					modifiedDate
				};
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Unable to find question " + questionId);
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateChoice(
			long choiceId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, Timestamp modifiedDate)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update PollsChoice set groupId = ?, companyId = ?, userId " +
					"= ?, userName = ?, createDate = ?, modifiedDate = ? " +
						"where choiceId = ?");

			ps.setLong(1, groupId);
			ps.setLong(2, companyId);
			ps.setLong(3, userId);
			ps.setString(4, userName);
			ps.setTimestamp(5, createDate);
			ps.setTimestamp(6, modifiedDate);
			ps.setLong(7, choiceId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
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

				Object[] questionArray = getQuestionArray(questionId);

				if (questionArray == null) {
					continue;
				}

				long groupId = (Long)questionArray[0];
				long companyId = (Long)questionArray[1];
				long userId = (Long)questionArray[2];
				String userName = (String)questionArray[3];
				Timestamp createDate = (Timestamp)questionArray[4];
				Timestamp modifiedDate = (Timestamp)questionArray[5];

				updateChoice(
					choiceId, groupId, companyId, userId, userName, createDate,
					modifiedDate);
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

				Object[] questionArray = getQuestionArray(questionId);

				if (questionArray == null) {
					continue;
				}

				long groupId = (Long)questionArray[0];

				runSQL(
					"update PollsVote set groupId = " + groupId +
						" where voteId = " + voteId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradePolls.class);

}