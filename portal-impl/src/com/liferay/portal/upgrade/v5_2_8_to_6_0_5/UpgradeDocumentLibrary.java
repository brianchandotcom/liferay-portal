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

package com.liferay.portal.upgrade.v5_2_8_to_6_0_5;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.upgrade.v5_2_8_to_6_0_5.util.DLFileEntryTable;
import com.liferay.portal.upgrade.v5_2_8_to_6_0_5.util.DLFileVersionTable;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileEntryNameUpgradeColumnImpl;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileEntryTitleUpgradeColumnImpl;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileEntryVersionUpgradeColumnImpl;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileRankTable;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileShortcutTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Jorge Ferrer
 * @author Alexander Chow
 * @author Douglas Wong
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	protected void addFileVersion(
			long groupId, long companyId, long userId, String userName,
			long folderId, String name, double version, int size)
		throws Exception {

		Timestamp now = new Timestamp(System.currentTimeMillis());

		StringBundler sb = new StringBundler(5);

		sb.append("insert into DLFileVersion (fileVersionId, groupId, ");
		sb.append("companyId, userId, userName, createDate, folderId, name, ");
		sb.append("version, size_, status, statusByUserId, ");
		sb.append("statusByUserName, statusDate) values (?, ?, ?, ?, ?, ?, ");
		sb.append("?, ?, ?, ?, ?, ?, ?, ?)");

		String sql = sb.toString();

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, increment());
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, now);
			ps.setLong(7, folderId);
			ps.setString(8, name);
			ps.setDouble(9, version);
			ps.setInt(10, size);
			ps.setInt(11, WorkflowConstants.STATUS_APPROVED);
			ps.setLong(12, userId);
			ps.setString(13, userName);
			ps.setTimestamp(14, now);

			ps.executeUpdate();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateFileEntries();

		synchronizeFileVersions();

		// DLFileEntry

		UpgradeColumn nameColumn = new DLFileEntryNameUpgradeColumnImpl("name");
		UpgradeColumn titleColumn = new DLFileEntryTitleUpgradeColumnImpl(
			nameColumn, "title");
		UpgradeColumn versionColumn = new DLFileEntryVersionUpgradeColumnImpl(
			"version");

		upgradeTable(
			DLFileEntryTable.TABLE_NAME, DLFileEntryTable.TABLE_COLUMNS,
			DLFileEntryTable.TABLE_SQL_CREATE,
			DLFileEntryTable.TABLE_SQL_ADD_INDEXES, nameColumn, titleColumn,
			versionColumn);

		// DLFileRank

		upgradeTable(
			DLFileRankTable.TABLE_NAME, DLFileRankTable.TABLE_COLUMNS,
			DLFileRankTable.TABLE_SQL_CREATE,
			DLFileRankTable.TABLE_SQL_ADD_INDEXES, nameColumn);

		// DLFileShortcut

		UpgradeColumn toNameColumn = new DLFileEntryNameUpgradeColumnImpl(
			"toName");

		upgradeTable(
			DLFileShortcutTable.TABLE_NAME, DLFileShortcutTable.TABLE_COLUMNS,
			DLFileShortcutTable.TABLE_SQL_CREATE,
			DLFileShortcutTable.TABLE_SQL_ADD_INDEXES, toNameColumn);

		// DLFileVersion

		String tableSqlCreate = StringUtil.replace(
			DLFileVersionTable.TABLE_SQL_CREATE,
			new String[] {
				",extraSettings VARCHAR(75) null",
				",title VARCHAR(75) null"
			},
			new String[] {
				",extraSettings STRING null",
				",title VARCHAR(255) null"
			});

		upgradeTable(
			DLFileVersionTable.TABLE_NAME, DLFileVersionTable.TABLE_COLUMNS,
			tableSqlCreate, DLFileVersionTable.TABLE_SQL_ADD_INDEXES,
			nameColumn, versionColumn);
	}

	protected void synchronizeFileVersions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			StringBundler sb = new StringBundler(5);

			sb.append("select * from DLFileEntry dlFileEntry where version ");
			sb.append("not in (select version from DLFileVersion ");
			sb.append("dlFileVersion where (dlFileVersion.folderId = ");
			sb.append("dlFileEntry.folderId) and (dlFileVersion.name = ");
			sb.append("dlFileEntry.name))");

			String sql = sb.toString();

			try (PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					long companyId = rs.getLong("companyId");
					long groupId = rs.getLong("groupId");
					long userId = rs.getLong("userId");
					String userName = rs.getString("userName");
					long folderId = rs.getLong("folderId");
					String name = rs.getString("name");
					double version = rs.getDouble("version");
					int size = rs.getInt("size_");

					addFileVersion(
						groupId, companyId, userId, userName, folderId, name,
						version, size);
				}
			}
		}
	}

	protected void updateFileEntries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select * from DLFileEntry");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long groupId = rs.getLong("groupId");
				long folderId = rs.getLong("folderId");
				String name = rs.getString("name");

				long repositoryId = folderId;

				if (repositoryId ==
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

					repositoryId = groupId;
				}

				String newName = DLFileEntryNameUpgradeColumnImpl.getNewName(
					name);

				if (!newName.equals(name)) {
					try {
						DLStoreUtil.updateFile(
							companyId, repositoryId, name, newName);
					}
					catch (Exception e) {
						if (_log.isWarnEnabled()) {
							_log.warn("Unable to update file for " + name, e);
						}
					}
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDocumentLibrary.class);

}