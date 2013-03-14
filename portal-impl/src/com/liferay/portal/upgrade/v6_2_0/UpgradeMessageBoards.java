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

		updateThreadFlags();
		updateThreads();

		updateDiscussions();
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
				"select groupId, companyId, userId, createDate, modifiedDate " +
					"from MBThread where threadId = ?");

			ps.setLong(1, threadId);

			rs = ps.executeQuery();

			if (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("companyId");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");

				return new Object[] {
					groupId, companyId, userId, createDate, modifiedDate
				};
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

	protected void updateDiscussion(
			long discussionId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, Timestamp modifiedDate)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update MBDiscussion set groupId = ?, companyId = ?, userId " +
					"= ?, userName = ?, createDate = ?, modifiedDate = ? " +
						"where discussionId = ?");

			ps.setLong(1, groupId);
			ps.setLong(2, companyId);
			ps.setLong(3, userId);
			ps.setString(4, userName);
			ps.setTimestamp(5, createDate);
			ps.setTimestamp(6, modifiedDate);
			ps.setLong(7, discussionId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateDiscussions() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select discussionId, threadId from MBDiscussion");

			rs = ps.executeQuery();

			while (rs.next()) {
				long discussionId = rs.getLong("discussionId");
				long threadId = rs.getLong("threadId");

				Object[] threadArray = getThreadArray(threadId);

				if (threadArray == null) {
					continue;
				}

				long groupId = (Long)threadArray[0];
				long companyId = (Long)threadArray[1];
				long userId = (Long)threadArray[2];
				Timestamp createDate = (Timestamp)threadArray[3];
				Timestamp modifiedDate = (Timestamp)threadArray[4];

				updateDiscussion(
					discussionId, groupId, companyId, userId,
					getUserName(userId), createDate, modifiedDate);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateThread(
			long threadId, long userId, String userName, Timestamp createDate)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update MBThread set userId = ?, userName = ?, " +
					"createDate = ?, modifiedDate = ? where threadId = ?");

			ps.setLong(1, userId);
			ps.setString(2, userName);
			ps.setTimestamp(3, createDate);
			ps.setTimestamp(4, createDate);
			ps.setLong(5, threadId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateThreadFlag(
			long threadFlagId, long groupId, long companyId, String userName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update MBThreadFlag set groupId = ?, companyId = ?, " +
					"userName = ?, createDate = modifiedDate where " +
						"threadFlagId = ?");

			ps.setLong(1, groupId);
			ps.setLong(2, companyId);
			ps.setString(3, userName);
			ps.setLong(4, threadFlagId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateThreadFlags() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select threadFlagId, userId, threadId from MBThreadFlag");

			rs = ps.executeQuery();

			while (rs.next()) {
				long threadFlagId = rs.getLong("threadFlagId");
				long userId = rs.getLong("userId");
				long threadId = rs.getLong("threadId");

				Object[] threadArray = getThreadArray(threadId);

				if (threadArray == null) {
					continue;
				}

				long groupId = (Long)threadArray[0];
				long companyId = (Long)threadArray[1];

				updateThreadFlag(
					threadFlagId, groupId, companyId, getUserName(userId));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateThreads() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select threadId, rootMessageId from MBThread");

			rs = ps.executeQuery();

			while (rs.next()) {
				long threadId = rs.getLong("threadId");
				long messageId = rs.getLong("rootMessageId");

				Object[] messageArray = getMessageArray(messageId);

				if (messageArray == null) {
					continue;
				}

				long userId = (Long)messageArray[0];
				Timestamp createDate = (Timestamp)messageArray[1];

				updateThread(threadId, userId, getUserName(userId), createDate);
			}
		}
		finally {
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