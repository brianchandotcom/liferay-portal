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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Shinn Lok
 */
public class VerifyGroupId extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		for (String[] model : _MODELS) {
			verifyModel(model[0], model[1], model[2], model[3]);
		}
	}

	protected long getGroupId(
			String modelName, String pkColumnName, long primKey)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId from " + modelName + " where " + pkColumnName +
					" = ?");

			ps.setLong(1, primKey);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("groupId");
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find " + modelName + StringPool.SPACE + primKey);
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void verifyModel(
			String modelName, String pkColumnName, String relatedModelName,
			String relatedPKColumnName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select " + pkColumnName + StringPool.COMMA_AND_SPACE +
					relatedPKColumnName + " from " + modelName + " where " +
						"groupId is null");

			rs = ps.executeQuery();

			while (rs.next()) {
				long primKey = rs.getLong(pkColumnName);
				long relatedPrimKey = rs.getLong(relatedPKColumnName);

				long groupId = getGroupId(
					relatedModelName, relatedPKColumnName, relatedPrimKey);

				if (groupId <= 0) {
					continue;
				}

				runSQL(
					"update " + modelName + " set groupId = " + groupId +
						" where " + pkColumnName + " = " + primKey);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String[][] _MODELS = new String[][] {
		new String[] {
			"MBDiscussion", "discussionId", "MBThread", "threadId"
		},
		new String[] {
			"MBThreadFlag", "threadFlagId", "MBThread", "threadId"
		},
		new String[] {
			"PollsChoice", "choiceId", "PollsQuestion", "questionId"
		},
		new String[] {
			"PollsVote", "voteId", "PollsQuestion", "questionId"
		}
	};

	private static Log _log = LogFactoryUtil.getLog(VerifyGroupId.class);

}