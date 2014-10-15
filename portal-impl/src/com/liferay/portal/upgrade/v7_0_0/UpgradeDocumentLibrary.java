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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Michael Young
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {

		// DLFileEntry

		runSQL("alter table DLFileEntry add fileName VARCHAR(255) null");

		updateFileEntryFileNames();

		// DLFileVersion

		runSQL("alter table DLFileVersion add fileName VARCHAR(255) null");

		updateFileVersionFileNames();
	}

	protected boolean hasFileEntry(long groupId, long folderId, String fileName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select count(*) from DLFileEntry where groupId = ? and " +
					"folderId = ? and fileName = ?");

			ps.setLong(1, groupId);
			ps.setLong(2, folderId);
			ps.setString(3, fileName);

			rs = ps.executeQuery();

			while (rs.next()) {
				int count = rs.getInt(1);

				if (count > 0) {
					return true;
				}
			}

			return false;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateFileEntryFileName(long fileEntryId, String fileName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update DLFileEntry set fileName = ? where fileEntryId = ?");

			ps.setString(1, fileName);
			ps.setLong(2, fileEntryId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateFileEntryFileNames() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select fileEntryId, groupId, folderId, extension, title, " +
					"version from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileEntryId = rs.getLong("fileEntryId");
				long groupId = rs.getLong("groupId");
				long folderId = rs.getLong("folderId");
				String extension = GetterUtil.getString(
					rs.getString("extension"));
				String title = GetterUtil.getString(rs.getString("title"));
				String version = rs.getString("version");

				String uniqueFileName = DLUtil.getSanitizedFileName(
					title, extension);

				String titleExtension = StringPool.BLANK;
				String titleWithoutExtension = title;

				if (title.endsWith(StringPool.PERIOD + extension)) {
					titleExtension = extension;
					titleWithoutExtension = FileUtil.stripExtension(title);
				}

				String uniqueTitle = StringPool.BLANK;

				for (int i = 1;; i++) {
					if (!hasFileEntry(groupId, folderId, uniqueFileName)) {
						break;
					}

					uniqueTitle =
						titleWithoutExtension + StringPool.UNDERLINE +
							String.valueOf(i);

					if (Validator.isNotNull(titleExtension)) {
						uniqueTitle += StringPool.PERIOD.concat(titleExtension);
					}

					uniqueFileName = DLUtil.getSanitizedFileName(
						uniqueTitle, extension);
				}

				updateFileEntryFileName(fileEntryId, uniqueFileName);

				if (Validator.isNotNull(uniqueTitle)) {
					updateFileEntryTitle(fileEntryId, uniqueTitle, version);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateFileEntryTitle(
			long fileEntryId, String title, String version)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update DLFileEntry set title = ? where fileEntryId = ?");

			ps.setString(1, title);
			ps.setLong(2, fileEntryId);

			ps.executeUpdate();

			ps = con.prepareStatement(
				"update DLFileVersion set title = ? where fileEntryId = " +
					"? and version = ?");

			ps.setString(1, title);
			ps.setLong(2, fileEntryId);
			ps.setString(3, version);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateFileVersionFileName(
			long fileVersionId, String fileName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update DLFileVersion set fileName = ? where " +
					"fileVersionId = ?");

			ps.setString(1, fileName);
			ps.setLong(2, fileVersionId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateFileVersionFileNames() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select fileVersionId, extension, title from DLFileVersion");

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileVersionId = rs.getLong("fileVersionId");
				String extension = GetterUtil.getString(
					rs.getString("extension"));
				String title = GetterUtil.getString(rs.getString("title"));

				String fileName = DLUtil.getSanitizedFileName(title, extension);

				updateFileVersionFileName(fileVersionId, fileName);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}