/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.BaseCompanyIdUpgradeProcess;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Brian Wing Shun Chan
 * @author Luis Ortiz
 */
public class UpgradeCompanyId extends BaseCompanyIdUpgradeProcess {

	@Override
	protected TableUpdater[] getTableUpdaters() {
		return new TableUpdater[] {
			new TableUpdater("AnnouncementsFlag", "User_", "userId"),
			new CompanyIdNotNullTableUpdater(
				"AssetEntries_AssetCategories", "AssetCategory", "categoryId"),
			new CompanyIdNotNullTableUpdater(
				"AssetEntries_AssetTags", "AssetTag", "tagId"),
			new TableUpdater("AssetTagStats", "AssetTag", "tagId"),
			new TableUpdater("BrowserTracker", "User_", "userId"),
			new TableUpdater(
				"DLFileEntryMetadata", "DLFileEntry", "fileEntryId"),
			new CompanyIdNotNullTableUpdater(
				"DLFileEntryTypes_DLFolders", "DLFolder", "folderId"),
			new DLSyncEventTableUpdater("DLSyncEvent"),
			new CompanyIdNotNullTableUpdater(
				"Groups_Orgs", "Group_", "groupId"),
			new CompanyIdNotNullTableUpdater(
				"Groups_Roles", "Group_", "groupId"),
			new CompanyIdNotNullTableUpdater(
				"Groups_UserGroups", "Group_", "groupId"),
			new TableUpdater(
				"Image", "imageId",
				new String[][] {
					{"BlogsEntry", "smallImageId"}, {"Company", "logoId"},
					{"DDMTemplate", "smallImageId"},
					{"DLFileEntry", "largeImageId"},
					{"JournalArticle", "smallImageId"},
					{"Layout", "iconImageId"},
					{"LayoutRevision", "iconImageId"},
					{"LayoutSetBranch", "logoId"}, {"Organization_", "logoId"},
					{"User_", "portraitId"}
				}),
			new TableUpdater("MBStatsUser", "Group_", "groupId"),
			new TableUpdater("OrgGroupRole", "Organization_", "organizationId"),
			new TableUpdater("OrgLabor", "Organization_", "organizationId"),
			new TableUpdater(
				"PasswordPolicyRel", "PasswordPolicy", "passwordPolicyId"),
			new TableUpdater("PasswordTracker", "User_", "userId"),
			new BaseCompanyIdUpgradeProcess.PortletPreferencesTableUpdater(
				"PortletPreferences"),
			new TableUpdater(
				"RatingsStats", "classPK",
				new String[][] {
					{"BookmarksEntry", "entryId"},
					{"BookmarksFolder", "folderId"}, {"BlogsEntry", "entryId"},
					{"DDLRecord", "recordId"}, {"DLFileEntry", "fileEntryId"},
					{"DLFolder", "folderId"},
					{"JournalArticle", "resourcePrimKey"},
					{"JournalFolder", "folderId"},
					{"MBDiscussion", "discussionId"},
					{"MBMessage", "messageId"}, {"WikiPage", "pageId"}
				}),
			new TableUpdater(
				"ResourceBlockPermission", "ResourceBlock", "resourceBlockId"),
			new TableUpdater("TrashVersion", "TrashEntry", "entryId"),
			new TableUpdater("UserGroupGroupRole", "UserGroup", "userGroupId"),
			new TableUpdater("UserGroupRole", "User_", "userId"),
			new CompanyIdNotNullTableUpdater(
				"UserGroups_Teams", "UserGroup", "userGroupId"),
			new TableUpdater("UserIdMapper", "User_", "userId"),
			new CompanyIdNotNullTableUpdater("Users_Groups", "User_", "userId"),
			new CompanyIdNotNullTableUpdater("Users_Orgs", "User_", "userId"),
			new CompanyIdNotNullTableUpdater("Users_Roles", "User_", "userId"),
			new CompanyIdNotNullTableUpdater("Users_Teams", "User_", "userId"),
			new CompanyIdNotNullTableUpdater(
				"Users_UserGroups", "User_", "userId"),
			new TableUpdater("UserTrackerPath", "UserTracker", "userTrackerId")
		};
	}

	protected class CompanyIdNotNullTableUpdater extends TableUpdater {

		public CompanyIdNotNullTableUpdater(
			String tableName, String foreignTableName, String columnName) {

			super(tableName, foreignTableName, columnName);
		}

		@Override
		public void update(Connection connection) throws Exception {
			super.update(connection);

			alterColumnType(getTableName(), "companyId", "LONG NOT NULL");
		}

	}

	protected class DLSyncEventTableUpdater extends TableUpdater {

		public DLSyncEventTableUpdater(String tableName) {
			super(tableName, "", "");
		}

		@Override
		public void update(Connection connection)
			throws IOException, SQLException {

			// DLFileEntry

			String selectSQL =
				"select companyId from DLFileEntry where DLSyncEvent.type_ = " +
					"'file' and DLFileEntry.fileEntryId = DLSyncEvent.typePK";

			runSQL(connection, getUpdateSQL(selectSQL));

			// DLFolder

			selectSQL =
				"select companyId from DLFolder where DLSyncEvent.type_ = " +
					"'folder' and DLFolder.folderId = DLSyncEvent.typePK";

			runSQL(connection, getUpdateSQL(selectSQL));
		}

	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link BaseCompanyIdUpgradeProcess.PortletPreferencesTableUpdater}
	 */
	@Deprecated
	protected class PortletPreferencesTableUpdater extends TableUpdater {

		public PortletPreferencesTableUpdater(String tableName) {
			super(tableName, "", "");
		}

		@Override
		public void update(Connection connection) throws Exception {
			super.update(connection);
		}

	}

}