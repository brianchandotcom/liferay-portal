/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.RSSUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.portlet.PortletPreferences;

/**
 * @author Eduardo Garcia
 * @author Daniel Kocsis
 */
public class UpgradeMessageBoards extends BaseUpgradePortletPreferences {

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();

		long startTime = System.nanoTime();

		updateThreadFlags();
		updateThreads();

		long endTime = System.nanoTime();

		System.out.println("Estimated time:" + (endTime - startTime));
	}

	protected Object[] getMessageArray(long messageId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select userId, createDate from MBMessage " +
					"where messageId = ?");

			ps.setLong(1, messageId);

			rs = ps.executeQuery();

			if (rs.next()) {
				long userId = rs.getLong("userId");
				Timestamp createDate = rs.getTimestamp("createDate");

				return new Object[] {userId, createDate};
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Unable to find message " + messageId);
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {"19"};
	}

	protected Object[] getThreadArray(long threadId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId, companyId from MBThread where threadId = ?");

			ps.setLong(1, threadId);

			rs = ps.executeQuery();

			if (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");

				return new Object[] {groupId, companyId};
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Unable to find thread " + threadId);
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected String getUserName(long userId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select firstName, middleName, lastName from User_ where " +
					"userId = ?");

			ps.setLong(1, userId);

			rs = ps.executeQuery();

			if (rs.next()) {
				String firstName = rs.getString("firstName");
				String middleName = rs.getString("middleName");
				String lastName = rs.getString("lastName");

				FullNameGenerator fullNameGenerator =
					FullNameGeneratorFactory.getInstance();

				return fullNameGenerator.getFullName(
					firstName, middleName, lastName);
			}

			return StringPool.BLANK;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateThread(
			PreparedStatement preparedStatement, long threadId, long userId,
			String userName, Timestamp createDate)
		throws Exception {

		preparedStatement.setLong(1, userId);
		preparedStatement.setString(2, userName);
		preparedStatement.setTimestamp(3, createDate);
		preparedStatement.setTimestamp(4, createDate);
		preparedStatement.setLong(5, threadId);

		preparedStatement.addBatch();
	}

	protected void updateThreadFlag(
			PreparedStatement preparedStatement, long threadFlagId,
			long groupId, long companyId, String fullName)
		throws Exception {

		preparedStatement.setLong(1, groupId);
		preparedStatement.setLong(2, companyId);
		preparedStatement.setString(3, fullName);
		preparedStatement.setLong(4, threadFlagId);

		preparedStatement.addBatch();
	}

	protected void updateThreadFlags() throws Exception {
		Connection con = null;
		PreparedStatement batchStatement = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select threadFlagId, userId, threadId from MBThreadFlag");

			rs = ps.executeQuery();

			batchStatement = con.prepareStatement(
				"update MBThreadFlag set groupId = ?, companyId = ?, " +
					"userName = ?, createDate = modifiedDate where " +
						"threadFlagId = ?");

			int batchSize = getBatchSize();
			int counter = 0;

			while (rs.next()) {
				long threadFlagId = rs.getLong("threadFlagId");
				long userId = rs.getLong("userId");
				long threadId = rs.getLong("threadId");

				String userName = getUserName(userId);

				Object[] threadArray = getThreadArray(threadId);

				if (threadArray == null) {
					continue;
				}

				updateThreadFlag(
					batchStatement, threadFlagId, (Long)threadArray[0],
					(Long)threadArray[1], userName);

				if ((++counter % batchSize) == 0) {
					ps.executeBatch();

					counter = 0;
				}
			}
		}
		finally {
			DataAccess.cleanUp(batchStatement);
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateThreads() throws Exception {
		Connection con = null;
		PreparedStatement batchStatement = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select threadId, rootMessageId from MBThread");

			rs = ps.executeQuery();

			int batchSize = getBatchSize();
			int counter = 0;

			batchStatement = con.prepareStatement(
				"update MBThread set userId = ?, userName = ?, " +
					"createDate = ?, modifiedDate = ? where threadId = ?");

			while (rs.next()) {
				long threadId = rs.getLong("threadId");
				long messageId = rs.getLong("rootMessageId");

				Object[] messageArray = getMessageArray(messageId);

				if (messageArray == null) {
					continue;
				}

				long userId = (Long)messageArray[0];
				String userName = getUserName(userId);

				updateThread(
					batchStatement, threadId, userId, userName,
					(Timestamp)messageArray[1]);

				if ((++counter % batchSize) == 0) {
					ps.executeBatch();

					counter = 0;
				}
			}
		}
		finally {
			DataAccess.cleanUp(batchStatement);
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String rssFormat = GetterUtil.getString(
			portletPreferences.getValue("rssFormat", null));

		if (Validator.isNotNull(rssFormat)) {
			String rssFeedType = RSSUtil.getFeedType(
				RSSUtil.getFormatType(rssFormat),
				RSSUtil.getFormatVersion(rssFormat));

			portletPreferences.setValue("rssFeedType", rssFeedType);
		}

		portletPreferences.reset("rssFormat");

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeMessageBoards.class);

}