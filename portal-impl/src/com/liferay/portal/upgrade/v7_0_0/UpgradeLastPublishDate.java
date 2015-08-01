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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.util.PortletKeys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Levente Hudák
 */
public class UpgradeLastPublishDate extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeAssetCategoriesAdmin();
		upgradeBlogs();
		upgradeDocumentLibrary();
		upgradeDynamicDataMapping();
		upgradeLayoutsAdmin();
		upgradeMessageBoards();
		upgradeMobileDeviceRules();
		upgradeRatings();
		upgradeRolesAdmin();
		upgradeSitesAdmin();
		upgradeUsersAdmin();
	}

	protected Date getLayoutSetLastPublishDate(long groupId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select settings_ from LayoutSet where groupId = ?");

			ps.setLong(1, groupId);

			rs = ps.executeQuery();

			while (rs.next()) {
				UnicodeProperties settingsProperties = new UnicodeProperties(
					true);

				settingsProperties.load(rs.getString("settings"));

				String lastPublishDateString = settingsProperties.getProperty(
					"last-publish-date");

				if (Validator.isNotNull(lastPublishDateString)) {
					return new Date(GetterUtil.getLong(lastPublishDateString));
				}
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected Date getPortletLastPublishDate(long groupId, String portletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select preferences from PortletPreferences where plid = ? " +
					"and ownerType = ? and ownerId = ? and portletId = ?");

			ps.setLong(1, LayoutConstants.DEFAULT_PLID);
			ps.setInt(2, PortletKeys.PREFS_OWNER_TYPE_GROUP);
			ps.setString(3, portletId);
			ps.setLong(4, groupId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String preferences = rs.getString("preferences");

				if (Validator.isNotNull(preferences)) {
					int x = preferences.lastIndexOf(
						"last-publish-date</name><value>");
					int y = preferences.indexOf("</value>", x);

					String lastPublishDateString = preferences.substring(x, y);

					if (Validator.isNotNull(lastPublishDateString)) {
						return new Date(
							GetterUtil.getLong(lastPublishDateString));
					}
				}
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected List<Long> getStagedGroupIds() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select groupId from Group_ where typeSettings like " +
					"'%staged=true%'");

			rs = ps.executeQuery();

			List<Long> stagedGroupIds = new ArrayList<>();

			while (rs.next()) {
				long stagedGroupId = rs.getLong("groupId");

				stagedGroupIds.add(stagedGroupId);
			}

			return stagedGroupIds;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateLastPublishDates(String portletId, String tableName)
		throws Exception {

		List<Long> stagedGroupIds = getStagedGroupIds();

		for (long stagedGroupId : stagedGroupIds) {
			Date lastPublishDate = getPortletLastPublishDate(
				stagedGroupId, portletId);

			if (lastPublishDate == null) {
				lastPublishDate = getLayoutSetLastPublishDate(stagedGroupId);
			}

			if (lastPublishDate == null) {
				continue;
			}

			updateStagedModelLastPublishDates(
				stagedGroupId, tableName, lastPublishDate);
		}
	}

	protected void updateStagedModelLastPublishDates(
			long groupId, String tableName, Date lastPublishDate)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update " + tableName + " set lastPublishDate = ? where " +
					"groupId = ?");

			ps.setDate(1, new java.sql.Date(lastPublishDate.getTime()));
			ps.setLong(2, groupId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void upgradeAssetCategoriesAdmin() throws Exception {
		runSQL("alter table AssetCategory add lastPublishDate DATE null");

		updateLastPublishDates("147", "AssetCategory");

		runSQL("alter table AssetTag add lastPublishDate DATE null");

		updateLastPublishDates("147", "AssetTag");

		runSQL("alter table AssetVocabulary add lastPublishDate DATE null");

		updateLastPublishDates("147", "AssetVocabulary");
	}

	protected void upgradeBlogs() throws Exception {
		runSQL("alter table BlogsEntry add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.BLOGS, "BlogsEntry");
	}

	protected void upgradeDocumentLibrary() throws Exception {
		runSQL("alter table DLFileEntry add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.DOCUMENT_LIBRARY, "DLFileEntry");

		runSQL("alter table DLFileEntryType add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.DOCUMENT_LIBRARY, "DLFileEntryType");

		runSQL("alter table DLFileShortcut add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.DOCUMENT_LIBRARY, "DLFileShortcut");

		runSQL("alter table DLFolder add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.DOCUMENT_LIBRARY, "DLFolder");

		runSQL("alter table Repository add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.DOCUMENT_LIBRARY, "Repository");

		runSQL("alter table RepositoryEntry add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.DOCUMENT_LIBRARY, "RepositoryEntry");
	}

	protected void upgradeDynamicDataMapping() throws Exception {
		runSQL("alter table DDMStructure add lastPublishDate DATE null");

		updateLastPublishDates(
			PortletKeys.DYNAMIC_DATA_MAPPING, "DDMStructure");

		runSQL("alter table DDMTemplate add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.DYNAMIC_DATA_MAPPING, "DDMTemplate");
	}

	protected void upgradeLayoutsAdmin() throws Exception {
		runSQL("alter table Layout add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.LAYOUTS_ADMIN, "Layout");

		runSQL("alter table LayoutFriendlyURL add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.LAYOUTS_ADMIN, "LayoutFriendlyURL");
	}

	protected void upgradeMessageBoards() throws Exception {
		runSQL("alter table MBBan add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.MESSAGE_BOARDS, "MBBan");

		runSQL("alter table MBCategory add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.MESSAGE_BOARDS, "MBCategory");

		runSQL("alter table MBDiscussion add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.MESSAGE_BOARDS, "MBDiscussion");

		runSQL("alter table MBMessage add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.MESSAGE_BOARDS, "MBMessage");

		runSQL("alter table MBThreadFlag add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.MESSAGE_BOARDS, "MBThreadFlag");
	}

	protected void upgradeMobileDeviceRules() throws Exception {
		runSQL("alter table MDRAction add lastPublishDate DATE null");

		updateLastPublishDates("178", "MDRAction");

		runSQL("alter table MDRRule add lastPublishDate DATE null");

		updateLastPublishDates("178", "MDRRule");

		runSQL("alter table MDRRuleGroup add lastPublishDate DATE null");

		updateLastPublishDates("178", "MDRRuleGroup");

		runSQL(
			"alter table MDRRuleGroupInstance add lastPublishDate DATE null");

		updateLastPublishDates("178", "MDRRuleGroupInstance");
	}

	protected void upgradeRatings() throws Exception {
		runSQL("alter table RatingsEntry add lastPublishDate DATE null");

		updateLastPublishDates("108", "RatingsEntry");
	}

	protected void upgradeRolesAdmin() throws Exception {
		runSQL("alter table PasswordPolicy add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.ROLES_ADMIN, "PasswordPolicy");

		runSQL("alter table Role_ add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.ROLES_ADMIN, "Role_");
	}

	protected void upgradeSitesAdmin() throws Exception {
		runSQL("alter table Team add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.SITE_ADMIN, "Team");
	}

	protected void upgradeUsersAdmin() throws Exception {
		runSQL("alter table Address add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.USERS_ADMIN, "Address");

		runSQL("alter table EmailAddress add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.USERS_ADMIN, "EmailAddress");

		runSQL("alter table Phone add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.USERS_ADMIN, "Phone");

		runSQL("alter table User_ add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.USERS_ADMIN, "User_");
	}

}