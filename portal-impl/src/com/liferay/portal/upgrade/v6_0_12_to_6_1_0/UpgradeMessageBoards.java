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

package com.liferay.portal.upgrade.v6_0_12_to_6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Shuyang Zhou
 */
public class UpgradeMessageBoards extends UpgradeProcess {

	protected void addThreadFlag(
			long threadFlagId, long userId, long threadId,
			Timestamp modifiedDate)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into MBThreadFlag (threadFlagId, userId, " +
					"modifiedDate, threadId) values (?, ? ,? ,?)");

			ps.setLong(1, threadFlagId);
			ps.setLong(2, userId);
			ps.setTimestamp(3, modifiedDate);
			ps.setLong(4, threadId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateMessage();
		updateThread();
		updateThreadFlag();
	}

	protected void updateMessage() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("select messageFlag.messageId from MBMessageFlag ");
			sb.append("messageFlag inner join MBMessage message on ");
			sb.append("messageFlag.messageId = message.messageId where ");
			sb.append("message.parentMessageId != 0 and flag = 3");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				long messageId = rs.getLong("messageFlag.messageId");

				updateMessageAnswer(messageId, true);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateMessageAnswer(long messageId, boolean answer)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update MBMessage set answer = ? where messageId = " +
					messageId);

			ps.setBoolean(1, answer);

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
				"select threadId from MBMessageFlag where flag = 2");

			rs = ps.executeQuery();

			while (rs.next()) {
				long threadId = rs.getLong("threadId");

				updateThreadQuestion(threadId, true);
			}

			StringBundler sb = new StringBundler(4);

			sb.append("select messageFlag.threadId from MBMessageFlag ");
			sb.append("messageFlag inner join MBMessage message on ");
			sb.append("messageFlag.messageId = message.messageId where ");
			sb.append("message.parentMessageId = 0 and flag = 3");

			ps = con.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				long threadId = rs.getLong("messageFlag.threadId");

				updateThreadQuestion(threadId, true);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
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

				addThreadFlag(increment(), userId, threadId, modifiedDate);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		runSQL("drop table MBMessageFlag");
	}

	protected void updateThreadQuestion(long threadId, boolean question)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update MBThread set question = ? where threadId =" +
					threadId);

			ps.setBoolean(1, question);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

}