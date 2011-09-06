/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.upgrade.v6_1_0.util.DLFileVersionTable;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.portlet.documentlibrary.util.ImageProcessor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Douglas Wong
 * @author Alexander Chow
 * @author Minhchau Dang
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateFileEntries();
		updateFileRanks();
		updateFileShortcuts();
		updateFileVersions();
		updateLocks();
		updateThumbnails();
	}

	protected long getFileEntryId(long groupId, long folderId, String name)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select fileEntryId from DLFileEntry where groupId = ? and " +
					"folderId = ? and name = ?");

			ps.setLong(1, groupId);
			ps.setLong(2, folderId);
			ps.setString(3, name);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("fileEntryId");
			}

			return 0;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long getGroupId(long folderId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long groupId = 0;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select groupId from DLFolder where folderId = ?");

			ps.setLong(1, folderId);

			rs = ps.executeQuery();

			if (rs.next()) {
				groupId = rs.getLong("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return groupId;
	}

	protected void updateFileEntries() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select fileEntryId, extension from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileEntryId = rs.getLong("fileEntryId");
				String extension = rs.getString("extension");

				String mimeType = MimeTypesUtil.getContentType(
					"A." + extension);

				runSQL(
					"update DLFileEntry set mimeType = '" + mimeType +
						"' where fileEntryId = " + fileEntryId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateFileRanks() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select groupId, fileRankId, folderId, name from DLFileRank");

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long fileRankId = rs.getLong("fileRankId");
				long folderId = rs.getLong("folderId");
				String name = rs.getString("name");

				long fileEntryId = getFileEntryId(groupId, folderId, name);

				runSQL(
					"update DLFileRank set fileEntryId = " + fileEntryId +
						" where fileRankId = " + fileRankId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		runSQL("alter table DLFileRank drop column folderId");
		runSQL("alter table DLFileRank drop column name");
	}

	protected void updateFileShortcuts() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select fileShortcutId, toFolderId, toName from " +
					"DLFileShortcut");

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileShortcutId = rs.getLong("fileShortcutId");
				long toFolderId = rs.getLong("toFolderId");
				String toName = rs.getString("toName");

				long groupId = getGroupId(toFolderId);

				long toFileEntryId = getFileEntryId(
					groupId, toFolderId, toName);

				runSQL(
					"update DLFileShortcut set toFileEntryId = " +
						toFileEntryId + " where fileShortcutId = " +
							fileShortcutId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		runSQL("alter table DLFileShortcut drop column toFolderId");
		runSQL("alter table DLFileShortcut drop column toName");
	}

	protected void updateFileVersions() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select groupId, fileVersionId, folderId, name, extension " +
					"from DLFileVersion");

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long fileVersionId = rs.getLong("fileVersionId");
				long folderId = rs.getLong("folderId");
				String name = rs.getString("name");
				String extension = rs.getString("extension");

				String mimeType = MimeTypesUtil.getContentType(
					"A." + extension);

				long fileEntryId = getFileEntryId(groupId, folderId, name);

				runSQL(
					"update DLFileVersion set fileEntryId = " + fileEntryId +
						", mimeType = '" + mimeType +
							"' where fileVersionId = " + fileVersionId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		try {
			runSQL("alter_column_type DLFileVersion extraSettings TEXT null");
			runSQL("alter_column_type DLFileVersion title VARCHAR(255) null");
			runSQL("alter table DLFileVersion drop column folderId");
			runSQL("alter table DLFileVersion drop column name");
		}
		catch (Exception e) {
			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				DLFileVersionTable.TABLE_NAME,
				DLFileVersionTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(DLFileVersionTable.TABLE_SQL_CREATE);
			upgradeTable.setIndexesSQL(
				DLFileVersionTable.TABLE_SQL_ADD_INDEXES);

			upgradeTable.updateTable();
		}
	}

	protected void updateLocks() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select lockId, key_ from Lock_ where className = ?");

			ps.setString(1, DLFileEntry.class.getName());

			rs = ps.executeQuery();

			while (rs.next()) {
				long lockId = rs.getLong("lockId");
				String key = rs.getString("key_");

				String[] keyArray = StringUtil.split(key, CharPool.POUND);

				if (keyArray.length != 3) {
					continue;
				}

				long groupId = GetterUtil.getLong(keyArray[0]);
				long folderId = GetterUtil.getLong(keyArray[1]);
				String name = keyArray[2];

				long fileEntryId = getFileEntryId(groupId, folderId, name);

				if (fileEntryId > 0) {
					runSQL(
						"update Lock_ set key_ = '" + fileEntryId +
							"' where lockId = " + lockId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateThumbnails() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement("select fileEntryId from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileEntryId = rs.getLong("fileEntryId");

				ResultSet rs2 = null;
				PreparedStatement ps2 = null;
				Connection con2 = null;

				try {
					con2 = DataAccess.getConnection();

					ps2 = con2.prepareStatement(
						"select fileVersionId, extension, userId, version " +
							"from DLFileVersion " +
								"where fileEntryId = " + fileEntryId +
									" order by version asc");

					rs2 = ps2.executeQuery();

					long smallImageId = 0;
					long largeImageId = 0;
					long custom1ImageId = 0;
					long custom2ImageId = 0;

					while (rs2.next()) {
						String extension = rs2.getString("extension");
						long fileVersionId = rs2.getLong("fileVersionId");
						long userId = rs2.getLong("userId");
						String version = rs2.getString("version");

						String mimeType = MimeTypesUtil.getContentType(
							"A." + extension);

						DLFileVersion dlFileVersion = new DLFileVersionImpl();

						dlFileVersion.setFileEntryId(fileEntryId);
						dlFileVersion.setFileVersionId(fileVersionId);
						dlFileVersion.setMimeType(mimeType);
						dlFileVersion.setUserId(userId);
						dlFileVersion.setVersion(version);

						smallImageId = increment();
						largeImageId = increment();

						dlFileVersion.setSmallImageId(smallImageId);
						dlFileVersion.setLargeImageId(largeImageId);

						if (PropsValues.IG_IMAGE_CUSTOM_1_MAX_DIMENSION > 0) {
							custom1ImageId = increment();
							dlFileVersion.setCustom1ImageId(custom1ImageId);
						}

						if (PropsValues.IG_IMAGE_CUSTOM_2_MAX_DIMENSION > 0) {
							custom2ImageId = increment();
							dlFileVersion.setCustom2ImageId(custom2ImageId);
						}

						if (_imageMimeTypes.contains(mimeType)) {
							ImageProcessor.generateImages(
								new LiferayFileVersion(dlFileVersion));
						}

						runSQL(
							"update DLFileVersion set " +
								"smallImageId = " + smallImageId +
								", largeImageId = " + largeImageId +
								", custom1ImageId = " + custom1ImageId +
								", custom2ImageId = " + custom2ImageId +
									" where fileVersionId = " +
									fileVersionId);
					}

					runSQL(
						"update DLFileEntry set " +
							"smallImageId = " + smallImageId +
							", largeImageId = " + largeImageId +
							", custom1ImageId = " + custom1ImageId +
							", custom2ImageId = " + custom2ImageId +
								" where fileEntryId = " + fileEntryId);
				}
				finally {
					DataAccess.cleanUp(con2, ps2, rs2);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Set<String> _imageMimeTypes = SetUtil.fromArray(
		PropsValues.IG_IMAGE_THUMBNAIL_MIME_TYPES);
}