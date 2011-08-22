/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Shuyang Zhou
 */
public class UpgradeMessageBoards extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateMessage();
		updateThread();
		updateThreadFlag();

		runSQL("drop table MBMessageFlag");
	}

	protected void insertThreadFlag(
			long userId, long threadId, Timestamp modifiedDate)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into MBThreadFlag (threadFlagId, userId, " +
					"modifiedDate, threadId) values (?, ? ,? ,?)");

			ps.setLong(1, increment());
			ps.setLong(2, userId);
			ps.setTimestamp(3, modifiedDate);
			ps.setLong(4, threadId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateMessage() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select messageId, body from MBMessage where (body like " +
					"'%<3%') or (body like '%>_>%') or (body like '%<_<%')");

			rs = ps.executeQuery();

			while (rs.next()) {
				long messageId = rs.getLong("messageId");
				String body = rs.getString("body");

				body = StringUtil.replace(
					body,
					new String[] {"<3", ">_>", "<_<"},
					new String[] {":love:", ":glare:", ":dry:"});

				updateMessage(messageId, body);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select flag.messageId from MBMessageFlag flag inner join " +
					"MBMessage message on flag.messageId = message.messageId " +
						"where message.parentMessageId != 0 and flag = 3");

			rs = ps.executeQuery();

			while (rs.next()) {
				long messageId = rs.getLong("flag.messageId");

				updateMessage(messageId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateMessage(long messageId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update MBMessage set answer = ? where messageId = " +
					messageId);

			ps.setBoolean(1, true);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateMessage(long messageId, String body)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update MBMessage set body = ? where messageId = " + messageId);

			ps.setString(1, body);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateThread() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select MBThread.threadId, MBMessage.companyId, " +
					"MBMessage.userId from MBThread inner join MBMessage on " +
						"MBThread.rootMessageId = MBMessage.messageId");

			rs = ps.executeQuery();

			while (rs.next()) {
				long threadId = rs.getLong("threadId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");

				runSQL(
					"update MBThread set companyId = " + companyId +
						", rootMessageUserId = " + userId +
							" where threadId = " + threadId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select threadId from MBMessageFlag where flag = 2");

			rs = ps.executeQuery();

			while (rs.next()) {
				long threadId = rs.getLong("threadId");

				updateThread(threadId);
			}

			ps = con.prepareStatement(
				"select flag.threadId from MBMessageFlag flag inner join " +
					"MBMessage message on flag.messageId = message.messageId " +
						"where message.parentMessageId = 0 and flag = 3");

			rs = ps.executeQuery();

			while (rs.next()) {
				long threadId = rs.getLong("flag.threadId");

				updateThread(threadId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateThread(long threadId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update MBThread set question = ? where threadId =" +
					threadId);

			ps.setBoolean(1, true);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateThreadFlag() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select userId, threadId, modifiedDate from MBMessageFlag " +
					"where flag = 1");

			rs = ps.executeQuery();

			while (rs.next()) {
				long userId = rs.getLong("userId");
				long threadId = rs.getLong("threadId");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");

				insertThreadFlag(userId, threadId, modifiedDate);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}