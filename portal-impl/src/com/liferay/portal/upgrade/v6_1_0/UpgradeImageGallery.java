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

import com.liferay.portal.image.DLHook;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Sergio González
 */
public class UpgradeImageGallery extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (PropsValues.IMAGE_HOOK_IMPL.equals(DLHook.class.getName())) {
			updateIGFolderEntries();
			updateIGImageEntries();
			updateIGFolderPermissions();
			updateIGImagePermissions();
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn("Required manual Image Gallery upgrade. Check in " +
					"Control Panel, Server, Data Migration");
			}
		}
	}

	public static void startManualUpgrade() throws Exception {
		_instance.updateIGFolderEntries();
		_instance.updateIGImageEntries();
		_instance.updateIGFolderPermissions();
		_instance.updateIGImagePermissions();
	}

	protected void addDLFileEntry(
		String uuid, long fileEntryId, long groupId, long companyId,
		long userId, String userName, long versionUserId,
		String versionUserName, Date createDate, Date modifiedDate,
		long folderId, String name, String extension, long repositoryId,
		String mimeType, String title, String description, String extraSettings,
		String version, long size, int readCount, long smallImageId,
		long largeImageId, long custom1ImageId, long custom2ImageId)
	throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("insert into DLFileEntry (uuid_, fileEntryId, groupId, ");
			sb.append("companyId, userId, userName, versionUserId, ");
			sb.append("versionUserName, createDate, modifiedDate, folderId, ");
			sb.append("name, extension, repositoryId, mimeType, title, ");
			sb.append("description, extraSettings, version, size_, ");
			sb.append("readCount, smallImageId, largeImageId, ");
			sb.append("custom1ImageId, custom2ImageId) values (");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?)");

			ps = con.prepareStatement(sb.toString());

			ps.setString(1, uuid);
			ps.setLong(2, fileEntryId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setLong(7, versionUserId);
			ps.setString(8, versionUserName);
			ps.setDate(9, createDate);
			ps.setDate(10, modifiedDate);
			ps.setLong(11, folderId);
			ps.setString(12, name);
			ps.setString(13, extension);
			ps.setLong(14, repositoryId);
			ps.setString(15, mimeType);
			ps.setString(16, title);
			ps.setString(17, description);
			ps.setString(18, extraSettings);
			ps.setString(19, version);
			ps.setLong(20, size);
			ps.setInt(21, readCount);
			ps.setLong(22, smallImageId);
			ps.setLong(23, largeImageId);
			ps.setLong(24, custom1ImageId);
			ps.setLong(25, custom2ImageId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addDLFileVersion(
		long fileVersionId, long groupId, long companyId, long userId,
		String userName, Date createDate, String extension, String title,
		String description, String changeLog, String extraSettings,
		String version, long size, int status, long statusByUserId,
		String statusByUserName, Date statusDate, long repositoryId,
		long fileEntryId, String mimeType, long fileEntryTypeId,
		long smallImageId, long largeImageId, long custom1ImageId,
		long custom2ImageId)
	throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("insert into DLFileVersion (fileVersionId, groupId, ");
			sb.append("companyId, userId, userName, createDate, extension, ");
			sb.append("title, description, changeLog, extraSettings, ");
			sb.append("version, size_, status, statusByUserId, ");
			sb.append("statusByUserName, statusDate, repositoryId, ");
			sb.append("fileEntryId, mimeType, fileEntryTypeId, smallImageId, ");
			sb.append("largeImageId, custom1ImageId, custom2ImageId) values (");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?)");

			ps = con.prepareStatement(sb.toString());

			ps.setLong(1, fileVersionId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setDate(6, createDate);
			ps.setString(7, extension);
			ps.setString(8, title);
			ps.setString(9, description);
			ps.setString(10, changeLog);
			ps.setString(11, extraSettings);
			ps.setString(12, version);
			ps.setLong(13, size);
			ps.setInt(14, status);
			ps.setLong(15, statusByUserId);
			ps.setString(16, statusByUserName);
			ps.setDate(17, statusDate);
			ps.setLong(18, repositoryId);
			ps.setLong(19, fileEntryId);
			ps.setString(20, mimeType);
			ps.setLong(21, fileEntryTypeId);
			ps.setLong(22, smallImageId);
			ps.setLong(23, largeImageId);
			ps.setLong(24, custom1ImageId);
			ps.setLong(25, custom2ImageId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addDLFolderEntry(
		String uuid, long folderId, long groupId, long companyId,
		long userId, String userName, Date createDate, Date modifiedDate,
		long parentFolderId, String name, String description, Date lastPostDate,
		long repositoryId)
	throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("insert into DLFolder (uuid_, folderId, groupId, ");
			sb.append("companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, parentFolderId, name, description, ");
			sb.append("lastPostDate, repositoryId, mountPoint) values (");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			ps = con.prepareStatement(sb.toString());

			ps.setString(1, uuid);
			ps.setLong(2, folderId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setDate(7, createDate);
			ps.setDate(8, modifiedDate);
			ps.setLong(9, parentFolderId);
			ps.setString(10, name);
			ps.setString(11, description);
			ps.setDate(12, lastPostDate);
			ps.setLong(13, repositoryId);
			ps.setBoolean(14, false);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateIGFolderEntries() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement("select * from IGFolder");

			rs = ps.executeQuery();

			while (rs.next()) {
				String uuid = rs.getString("uuid_");
				long folderId = rs.getLong("folderId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				Date createDate = rs.getDate("createDate");
				Date modifiedDate = rs.getDate("modifiedDate");
				long parentFolderId = rs.getLong("parentFolderId");
				String name = rs.getString("name");
				String description = rs.getString("description");

				addDLFolderEntry(uuid, folderId, groupId, companyId, userId,
					StringPool.BLANK, createDate, modifiedDate, parentFolderId,
					name, description, modifiedDate, groupId);
			}

			runSQL("drop table IGFolder");
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateIGFolderPermissions() throws Exception {
		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			return;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("update ResourcePermission set name='");
			sb.append(DLFolder.class.getName());
			sb.append("' where name='");
			sb.append(IGFolder.class.getName());
			sb.append("'");

			ps = con.prepareStatement(sb.toString());

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateIGImageEntries() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement("select * from IGImage");

			rs = ps.executeQuery();

			while (rs.next()) {
				String uuid = rs.getString("uuid_");
				long imageId = rs.getLong("imageId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				Date createDate = rs.getDate("createDate");
				Date modifiedDate = rs.getDate("modifiedDate");
				long folderId = rs.getLong("folderId");
				String title = rs.getString("name");
				String description = rs.getString("description");
				long smallImageId = rs.getLong("smallImageId");
				long largeImageId = rs.getLong("largeImageId");
				long custom1ImageId = rs.getLong("custom1ImageId");
				long custom2ImageId = rs.getLong("custom2ImageId");

				User user = UserLocalServiceUtil.getUser(userId);

				String userName = user.getFullName();

				IGImage image = IGImageLocalServiceUtil.getImage(imageId);

				String extension = image.getImageType();

				String mimeType = MimeTypesUtil.getContentType(
					"A." + extension);

				String name = String.valueOf(
					increment(DLFileEntry.class.getName()));

				long size = image.getImageSize();

				addDLFileEntry(uuid, imageId, groupId, companyId, userId,
					userName, userId, userName, createDate, modifiedDate,
					folderId, name, extension, groupId, StringPool.BLANK, title,
					description, StringPool.BLANK, "1.0", size, 0, smallImageId,
					largeImageId, custom1ImageId, custom2ImageId);

				addDLFileVersion(increment(), groupId, companyId, userId,
					userName, createDate, extension, title,	description,
					StringPool.BLANK, StringPool.BLANK, "1.0", size, 0, userId,
					userName, modifiedDate, groupId, imageId, mimeType, 0,
					smallImageId, largeImageId, custom1ImageId, custom2ImageId);
			}

			runSQL("drop table IGImage");
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateIGImagePermissions() throws Exception {
		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			return;
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("update ResourcePermission set name='");
			sb.append(DLFileEntry.class.getName());
			sb.append("' where name='");
			sb.append(IGImage.class.getName());
			sb.append("'");

			ps = con.prepareStatement(sb.toString());

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static UpgradeImageGallery _instance = new UpgradeImageGallery();

	private static Log _log = LogFactoryUtil.getLog(UpgradeImageGallery.class);

}