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

package com.liferay.portal.upgrade.v6_0_12_to_6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.portlet.documentlibrary.util.ImageProcessorUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Douglas Wong
 * @author Alexander Chow
 * @author Minhchau Dang
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	protected void addDLSync(
			long syncId, long companyId, Timestamp createDate,
			Timestamp modifiedDate, long fileId, long repositoryId,
			long parentFolderId, String event, String type)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"insert into DLSync (syncId, companyId, createDate, " +
					"modifiedDate, fileId, repositoryId, parentFolderId, " +
						"event, type_) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

			ps.setLong(1, syncId);
			ps.setLong(2, companyId);
			ps.setTimestamp(3, createDate);
			ps.setTimestamp(4, createDate);
			ps.setLong(5, fileId);
			ps.setLong(6, repositoryId);
			ps.setLong(7, parentFolderId);
			ps.setString(8, event);
			ps.setString(9, type);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateFileVersions();

		if (PropsValues.DL_FILE_ENTRY_PREVIEW_AUTO_CREATE_ON_UPGRADE) {
			updateThumbnails();
		}

		//updateSyncs();
	}

	protected void updateFileVersions() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select fileEntryId, folderId from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileEntryId = rs.getLong("fileEntryId");
				long folderId = rs.getLong("folderId");

				StringBundler sb = new StringBundler(4);

				sb.append("update DLFileVersion set folderId = ");
				sb.append(folderId);
				sb.append(" where fileEntryId = ");
				sb.append(fileEntryId);

				runSQL(sb.toString());
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateSyncs() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(10);

			sb.append("select DLFileEntry.fileEntryId as fileId, ");
			sb.append("DLFileEntry.groupId as groupId, DLFileEntry.companyId");
			sb.append(" as companyId, DLFileEntry.createDate as createDate, ");
			sb.append("DLFileEntry.folderId as parentFolderId, 'file' as ");
			sb.append("type from DLFileEntry union all select ");
			sb.append("DLFolder.folderId as fileId, DLFolder.groupId as ");
			sb.append("groupId, DLFolder.companyId as companyId, ");
			sb.append("DLFolder.createDate as createDate, ");
			sb.append("DLFolder.parentFolderId as parentFolderId, 'folder' ");
			sb.append("as type from DLFolder");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileId = rs.getLong("fileId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				Timestamp createDate = rs.getTimestamp("createDate");
				long parentFolderId = rs.getLong("parentFolderId");
				String type = rs.getString("type");

				addDLSync(
					increment(), companyId, createDate, createDate, fileId,
					groupId, parentFolderId, "add", type);
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
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select fileEntryId from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileEntryId = rs.getLong("fileEntryId");

				updateThumbnails(fileEntryId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateThumbnails(long fileEntryId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select fileVersionId, userId, extension, mimeType, version " +
					"from DLFileVersion where fileEntryId = " + fileEntryId +
						" order by version asc");

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileVersionId = rs.getLong("fileVersionId");
				long userId = rs.getLong("userId");
				String extension = rs.getString("extension");
				String mimeType = rs.getString("mimeType");
				String version = rs.getString("version");

				if (_imageMimeTypes.contains(mimeType)) {
					DLFileVersion dlFileVersion = new DLFileVersionImpl();

					dlFileVersion.setFileVersionId(fileVersionId);
					dlFileVersion.setUserId(userId);
					dlFileVersion.setFileEntryId(fileEntryId);
					dlFileVersion.setExtension(extension);
					dlFileVersion.setMimeType(mimeType);
					dlFileVersion.setVersion(version);

					FileVersion fileVersion = new LiferayFileVersion(
						dlFileVersion);

					try {
						ImageProcessorUtil.generateImages(null, fileVersion);
					}
					catch (Exception e) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to generate thumbnails for " +
									fileVersion.getFileVersionId(),
								e);
						}
					}
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDocumentLibrary.class);

	private static final Set<String> _imageMimeTypes = SetUtil.fromArray(
		PropsValues.DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES);

}