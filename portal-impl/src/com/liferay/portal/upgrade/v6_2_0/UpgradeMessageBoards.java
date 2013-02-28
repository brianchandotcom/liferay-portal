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
import com.liferay.portal.upgrade.v6_2_0.util.MBThreadFlagTable;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.RSSUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.portlet.PortletPreferences;

/**
 * @author Eduardo Garcia
 * @author Daniel Kocsis
 */
public class UpgradeMessageBoards extends BaseUpgradePortletPreferences {

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();

		// MBThreadFlag

		try {
			runSQL("alter table MBThreadFlag add groupId LONG");

			runSQL("alter table MBThreadFlag add companyId LONG");

			runSQL("alter table MBThreadFlag add userName STRING");

			runSQL("alter table MBThreadFlag add createdDate DATE");
		}
		catch (SQLException sqle) {
			upgradeTable(
				MBThreadFlagTable.TABLE_NAME, MBThreadFlagTable.TABLE_COLUMNS,
				MBThreadFlagTable.TABLE_SQL_CREATE,
				MBThreadFlagTable.TABLE_SQL_ADD_INDEXES);
		}

		updateMBThreadFlags();
	}

	protected String getFullName(long userId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select firstName, middleName, lastName from user_ " +
					"where userId = ?");

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

	protected long[] getGroupCompanyIdsArray(long threadId) throws Exception {
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

				return new long[] {groupId, companyId};
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Unable to find message thread " + threadId);
			}

			return new long[] {0, 0};
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {"19"};
	}

	protected void updateMBThreadFlags() throws Exception {
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

				String fullName = getFullName(userId);

				long[] groupCompanyIdsArray = getGroupCompanyIdsArray(threadId);

				updateThreadFlag(
					threadFlagId, groupCompanyIdsArray[0],
					groupCompanyIdsArray[1], fullName);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateThreadFlag(
			long threadFlagId, long groupId, long companyId, String fullName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update MBThreadFlag set groupId = ?, companyId = ?, " +
					"userName = ?, createdDate = modifiedDate " +
						"where threadFlagId = ?");

			ps.setLong(1, groupId);
			ps.setLong(2, companyId);
			ps.setString(3, fullName);
			ps.setLong(4, threadFlagId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
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