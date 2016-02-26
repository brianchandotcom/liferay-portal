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

package com.liferay.portal.upgrade.v6_0_12_to_6_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

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

		try (PreparedStatement ps = connection.prepareStatement(
				"insert into MBThreadFlag (threadFlagId, userId, " +
					"modifiedDate, threadId) values (?, ?, ?, ?)")) {

			ps.setLong(1, threadFlagId);
			ps.setLong(2, userId);
			ps.setTimestamp(3, modifiedDate);
			ps.setLong(4, threadId);

			ps.executeUpdate();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateMessage();
		updateThread();
		updateThreadFlag();
	}

	protected void updateMessage() throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append("select messageFlag.messageId as messageId from ");
		sb.append("MBMessageFlag messageFlag inner join MBMessage ");
		sb.append("message on messageFlag.messageId = message.messageId ");
		sb.append("where message.parentMessageId != 0 and flag = 3");

		String sql = sb.toString();

		try (PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long messageId = rs.getLong("messageId");

				updateMessageAnswer(messageId, true);
			}
		}
	}

	protected void updateMessageAnswer(long messageId, boolean answer)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update MBMessage set answer = ? where messageId = " +
					messageId)) {

			ps.setBoolean(1, answer);

			ps.executeUpdate();
		}
	}

	protected void updateThread() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select threadId from MBMessageFlag where flag = 2");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long threadId = rs.getLong("threadId");

				updateThreadQuestion(threadId, true);
			}
		}

		StringBundler sb = new StringBundler(4);

		sb.append("select messageFlag.threadId as threadId from ");
		sb.append("MBMessageFlag messageFlag inner join MBMessage ");
		sb.append("message on messageFlag.messageId = message.messageId ");
		sb.append("where message.parentMessageId = 0 and flag = 3");

		try (PreparedStatement ps = connection.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long threadId = rs.getLong("threadId");

				updateThreadQuestion(threadId, true);
			}
		}
	}

	protected void updateThreadFlag() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select userId, threadId, modifiedDate from MBMessageFlag " +
					"where flag = 1");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long userId = rs.getLong("userId");
				long threadId = rs.getLong("threadId");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");

				addThreadFlag(increment(), userId, threadId, modifiedDate);
			}
		}

		runSQL("drop table MBMessageFlag");
	}

	protected void updateThreadQuestion(long threadId, boolean question)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update MBThread set question = ? where threadId =" +
					threadId)) {

			ps.setBoolean(1, question);

			ps.executeUpdate();
		}
	}

}