/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.BaseCompanyIdUpgradeProcess;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.util.PortalInstances;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

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
			new PortletPreferencesTableUpdater("PortletPreferences"),
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

	protected class PortletPreferencesTableUpdater extends TableUpdater {

		public PortletPreferencesTableUpdater(String tableName) {
			super(tableName, "", "");
		}

		@Override
		public void update(Connection connection)
			throws IOException, SQLException {

			long[] companyIds = PortalInstances.getCompanyIdsBySQL();

			if (companyIds.length == 1) {
				runSQL(connection, getUpdateSQL(String.valueOf(companyIds[0])));

				return;
			}

			// Company

			runSQL(
				connection,
				_getUpdateSQL(
					"Company", "companyId", "ownerId",
					PortletKeys.PREFS_OWNER_TYPE_COMPANY));

			// Group

			runSQL(
				connection,
				_getUpdateSQL(
					"Group_", "groupId", "ownerId",
					PortletKeys.PREFS_OWNER_TYPE_GROUP));

			// Layout

			runSQL(
				connection,
				_getUpdateSQL(
					"Layout", "plid", "plid",
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT));

			// LayoutRevision

			runSQL(
				connection,
				_getUpdateSQL(
					"LayoutRevision", "layoutRevisionId", "plid",
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT));

			// Organization

			runSQL(
				connection,
				_getUpdateSQL(
					"Organization_", "organizationId", "ownerId",
					PortletKeys.PREFS_OWNER_TYPE_ORGANIZATION));

			// PortletItem

			runSQL(
				connection,
				_getUpdateSQL(
					"PortletItem", "portletItemId", "ownerId",
					PortletKeys.PREFS_OWNER_TYPE_ARCHIVED));

			// User_

			runSQL(
				connection,
				_getUpdateSQL(
					"User_", "userId", "ownerId",
					PortletKeys.PREFS_OWNER_TYPE_USER));
		}

		private String _getSelectSQL(
				String foreignTableName, String foreignColumnName,
				String columnName)
			throws SQLException {

			List<Long> companyIds = new ArrayList<>();

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select distinct companyId from " + foreignTableName);
				ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					long companyId = resultSet.getLong(1);

					companyIds.add(companyId);
				}
			}

			if (companyIds.size() == 1) {
				return String.valueOf(companyIds.get(0));
			}

			return StringBundler.concat(
				"select companyId from ", foreignTableName, " where ",
				foreignTableName, ".", foreignColumnName, " = ", getTableName(),
				".", columnName);
		}

		private String _getUpdateSQL(
				String foreignTableName, String foreignColumnName,
				String columnName, int ownerType)
			throws IOException, SQLException {

			return StringBundler.concat(
				getUpdateSQL(
					_getSelectSQL(
						foreignTableName, foreignColumnName, columnName)),
				" where ownerType = ", ownerType,
				" and (companyId is null or companyId = 0)");
		}

	}

}