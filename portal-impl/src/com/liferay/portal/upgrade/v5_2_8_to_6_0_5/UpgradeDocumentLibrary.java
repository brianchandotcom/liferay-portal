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

package com.liferay.portal.upgrade.v5_2_8_to_6_0_5;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.v5_2_8_to_6_0_5.util.DLFileEntryTable;
import com.liferay.portal.upgrade.v5_2_8_to_6_0_5.util.DLFileVersionTable;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileEntryNameUpgradeColumnImpl;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileEntryTitleUpgradeColumnImpl;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileEntryVersionUpgradeColumnImpl;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileRankTable;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileShortcutTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jorge Ferrer
 * @author Alexander Chow
 * @author Douglas Wong
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement("select * from DLFileEntry");

			rs = ps.executeQuery();

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
					DLStoreUtil.updateFile(
						companyId, repositoryId, name, newName);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		// DLFileEntry

		UpgradeColumn nameColumn = new DLFileEntryNameUpgradeColumnImpl("name");
		UpgradeColumn titleColumn = new DLFileEntryTitleUpgradeColumnImpl(
			nameColumn, "title");
		UpgradeColumn versionColumn = new DLFileEntryVersionUpgradeColumnImpl(
			"version");

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileEntryTable.TABLE_NAME, DLFileEntryTable.TABLE_COLUMNS,
			nameColumn, titleColumn, versionColumn);

		upgradeTable.setCreateSQL(DLFileEntryTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(DLFileEntryTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		// DLFileRank

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileRankTable.TABLE_NAME, DLFileRankTable.TABLE_COLUMNS,
			nameColumn);

		upgradeTable.setCreateSQL(DLFileRankTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(DLFileRankTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		// DLFileShortcut

		UpgradeColumn toNameColumn = new DLFileEntryNameUpgradeColumnImpl(
			"toName");

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileShortcutTable.TABLE_NAME, DLFileShortcutTable.TABLE_COLUMNS,
			toNameColumn);

		upgradeTable.setCreateSQL(DLFileShortcutTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(DLFileShortcutTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		// DLFileVersion

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileVersionTable.TABLE_NAME, DLFileVersionTable.TABLE_COLUMNS,
			nameColumn, versionColumn);

		upgradeTable.setCreateSQL(
			StringUtil.replace(
				DLFileVersionTable.TABLE_SQL_CREATE,
				new String[] {
					",extraSettings VARCHAR(75) null",
					",title VARCHAR(75) null"
				},
				new String[] {
					",extraSettings STRING null",
					",title VARCHAR(255) null"
				}));

		upgradeTable.setIndexesSQL(DLFileVersionTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		// File entries

		updateFileEntries();

		// File versions

		updateFileVersions();
	}

	protected long getLatestFileVersionId(long folderId, String name)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long fileVersionId = 0;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select fileVersionId from DLFileVersion where folderId = ? " +
					"and name = ? order by version desc");

			ps.setLong(1, folderId);
			ps.setString(2, name);

			rs = ps.executeQuery();

			if (rs.next()) {
				fileVersionId = rs.getLong("fileVersionId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return fileVersionId;
	}

	protected void updateFileEntries() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Long> tableIds = new ArrayList<Long>();

		try {
			long classNameId = PortalUtil.getClassNameId(DLFileEntry.class);

			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select tableId from ExpandoTable where classNameId = " +
					classNameId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long tableId = rs.getLong("tableId");

				tableIds.add(tableId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select uuid_, fileEntryId, groupId, folderId, name, title " +
					"from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				String uuid_ = rs.getString("uuid_");
				long fileEntryId = rs.getLong("fileEntryId");
				long groupId = rs.getLong("groupId");
				long folderId = rs.getLong("folderId");
				String name = rs.getString("name");
				String title = rs.getString("title");

				String extension = FileUtil.getExtension(title);

				runSQL(
					"update DLFileEntry set extension = '" + extension +
						"' where uuid_ = '" + uuid_ + "' and groupId = " +
							groupId);

				long fileVersionId = getLatestFileVersionId(folderId, name);

				for (long tableId : tableIds) {
					runSQL(
						"update ExpandoRow set classPK = " + fileVersionId +
							" where tableId = " + tableId + " and classPK = " +
								fileEntryId);

					runSQL(
						"update ExpandoValue set classPK = " + fileVersionId +
							" where tableId = " + tableId + " and classPK = " +
								fileEntryId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateFileVersion(
			long fileVersionId, String extension, String title, String
			description, String extraSettings)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update DLFileVersion set extension = ?, title = ?, " +
					"description = ?, extraSettings = ? where fileVersionId " +
						"= ?");

			ps.setString(1, extension);
			ps.setString(2, title);
			ps.setString(3, description);
			ps.setString(4, extraSettings);
			ps.setLong(5, fileVersionId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateFileVersions() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select folderId, name, extension, title, description, " +
					"extraSettings from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long folderId = rs.getLong("folderId");
				String name = rs.getString("name");
				String extension = rs.getString("extension");
				String title = rs.getString("title");
				String description = rs.getString("description");
				String extraSettings = rs.getString("extraSettings");

				long fileVersionId = getLatestFileVersionId(folderId, name);

				updateFileVersion(
					fileVersionId, extension, title, description,
					extraSettings);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}