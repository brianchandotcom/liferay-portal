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
		upgradeAddress();
		upgradeEmailAddress();
		upgradeLayout();
		upgradeLayoutFriendlyURL();
		upgradePasswordPolicy();
		upgradePhone();
		upgradeRepository();
		upgradeRepositoryEntry();
		upgradeRole();
		upgradeUser();
		upgradeBlogsEntry();
		upgradeCalEvent();
		upgradeDLFileEntry();
		upgradeDLFileEntryType();
		upgradeDLFileShortcut();
		upgradeDLFolder();
		upgradeDDMStructure();
		upgradeDDMTemplate();
		upgradeJournalArticle();
		upgradeJournalFeed();
		upgradeJournalFolder();
		upgradeMBBan();
		upgradeMBCategory();
		upgradeMBDiscussion();
		upgradeMBMessage();
		upgradeMBThreadFlag();
		upgradeMDRAction();
		upgradeMDRRule();
		upgradeMDRRuleGroup();
		upgradeMDRRuleGroupInstance();
		upgradeRatingsEntry();
	}

	protected void updateLastPublishDates(String portletId, String entityName)
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

			updateEntitiesLastPublishDates(
				stagedGroupId, entityName, lastPublishDate);
		}
	}

	private Date getLayoutSetLastPublishDate(long groupId) throws Exception {
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
				String settings = rs.getString("settings");

				if (Validator.isNotNull(settings)) {
					int x = settings.indexOf("last-publish-date=");

					String dateString = settings.substring(
						x, settings.length());

					if (Validator.isNotNull(dateString)) {
						return new Date(GetterUtil.getLong(dateString));
					}
				}
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private Date getPortletLastPublishDate(long groupId, String portletId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select preferences from PortletPreferences where plid = ?" +
					" and ownerType = ? and ownerId = ? and portletId = ?");

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

					String dateString = preferences.substring(x, y);

					if (Validator.isNotNull(dateString)) {
						return new Date(GetterUtil.getLong(dateString));
					}
				}
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private List<Long> getStagedGroupIds() throws Exception {
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

	private void updateEntitiesLastPublishDates(
			long groupId, String entityName, Date lastPublishDate)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update " + entityName + " set lastPublishDate = ? " +
					"where groupId = ?");

			ps.setDate(1, new java.sql.Date(lastPublishDate.getTime()));
			ps.setLong(2, groupId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	private void upgradeAddress() throws Exception {
		updateLastPublishDates(PortletKeys.USERS_ADMIN, "Address");
	}

	private void upgradeBlogsEntry() throws Exception {
		updateLastPublishDates(PortletKeys.BLOGS, "BlogsEntry");
	}

	private void upgradeCalEvent() throws Exception {
		updateLastPublishDates(PortletKeys.CALENDAR, "CalEvent");
	}

	private void upgradeDDMStructure() throws Exception {
		updateLastPublishDates(
			PortletKeys.DYNAMIC_DATA_MAPPING, "DDMStructure");
	}

	private void upgradeDDMTemplate() throws Exception {
		updateLastPublishDates(PortletKeys.DYNAMIC_DATA_MAPPING, "DDMTemplate");
	}

	private void upgradeDLFileEntry() throws Exception {
		updateLastPublishDates(PortletKeys.DOCUMENT_LIBRARY, "DLFileEntry");
	}

	private void upgradeDLFileEntryType() throws Exception {
		updateLastPublishDates(PortletKeys.DOCUMENT_LIBRARY, "DLFileEntryType");
	}

	private void upgradeDLFileShortcut() throws Exception {
		updateLastPublishDates(PortletKeys.DOCUMENT_LIBRARY, "DLFileShortcut");
	}

	private void upgradeDLFolder() throws Exception {
		updateLastPublishDates(PortletKeys.DOCUMENT_LIBRARY, "DLFolder");
	}

	private void upgradeEmailAddress() throws Exception {
		updateLastPublishDates(PortletKeys.USERS_ADMIN, "EmailAddress");
	}

	private void upgradeJournalArticle() throws Exception {
		updateLastPublishDates(PortletKeys.JOURNAL, "JournalArticle");
	}

	private void upgradeJournalFeed() throws Exception {
		updateLastPublishDates(PortletKeys.JOURNAL, "JournalFeed");
	}

	private void upgradeJournalFolder() throws Exception {
		updateLastPublishDates(PortletKeys.JOURNAL, "JournalFolder");
	}

	private void upgradeLayout() throws Exception {
		updateLastPublishDates(PortletKeys.LAYOUTS_ADMIN, "Layout");
	}

	private void upgradeLayoutFriendlyURL() throws Exception {
		updateLastPublishDates(PortletKeys.LAYOUTS_ADMIN, "LayoutFriendlyURL");
	}

	private void upgradeMBBan() throws Exception {
		updateLastPublishDates(PortletKeys.MESSAGE_BOARDS, "MBBan");
	}

	private void upgradeMBCategory() throws Exception {
		updateLastPublishDates(PortletKeys.MESSAGE_BOARDS, "MBCategory");
	}

	private void upgradeMBDiscussion() throws Exception {
		updateLastPublishDates(PortletKeys.MESSAGE_BOARDS, "MBDiscussion");
	}

	private void upgradeMBMessage() throws Exception {
		updateLastPublishDates(PortletKeys.MESSAGE_BOARDS, "MBMessage");
	}

	private void upgradeMBThreadFlag() throws Exception {
		updateLastPublishDates(PortletKeys.MESSAGE_BOARDS, "MBThreadFlag");
	}

	private void upgradeMDRAction() throws Exception {
		updateLastPublishDates(
			PortletKeys.MOBILE_DEVICE_SITE_ADMIN, "MDRAction");
	}

	private void upgradeMDRRule() throws Exception {
		updateLastPublishDates(PortletKeys.MOBILE_DEVICE_SITE_ADMIN, "MDRRule");
	}

	private void upgradeMDRRuleGroup() throws Exception {
		updateLastPublishDates(
			PortletKeys.MOBILE_DEVICE_SITE_ADMIN, "MDRRuleGroup");
	}

	private void upgradeMDRRuleGroupInstance() throws Exception {
		updateLastPublishDates(
			PortletKeys.MOBILE_DEVICE_SITE_ADMIN, "MDRRuleGroupInstance");
	}

	private void upgradePasswordPolicy() throws Exception {
		updateLastPublishDates(PortletKeys.ROLES_ADMIN, "PasswordPolicy");
	}

	private void upgradePhone() throws Exception {
		updateLastPublishDates(PortletKeys.USERS_ADMIN, "Phone");
	}

	private void upgradeRatingsEntry() throws Exception {
		updateLastPublishDates(PortletKeys.PAGE_RATINGS, "RatingsEntry");
	}

	private void upgradeRepository() throws Exception {
		updateLastPublishDates(PortletKeys.DOCUMENT_LIBRARY, "Repository");
	}

	private void upgradeRepositoryEntry() throws Exception {
		updateLastPublishDates(PortletKeys.DOCUMENT_LIBRARY, "RepositoryEntry");
	}

	private void upgradeRole() throws Exception {
		updateLastPublishDates(PortletKeys.ROLES_ADMIN, "Role");
	}

	private void upgradeUser() throws Exception {
		updateLastPublishDates(PortletKeys.USERS_ADMIN, "User");
	}

}