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
import com.liferay.portal.image.DatabaseHook;
import com.liferay.portal.image.FileSystemHook;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.image.Hook;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.documentlibrary.util.ImageProcessor;

import java.io.InputStream;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergio González
 * @author Miguel Pastor
 */
public class UpgradeImageGallery extends UpgradeProcess {

	public UpgradeImageGallery() throws Exception {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		_sourceHookClassName = FileSystemHook.class.getName();

		if (Validator.isNotNull(PropsValues.IMAGE_HOOK_IMPL)) {
			_sourceHookClassName = PropsValues.IMAGE_HOOK_IMPL;
		}

		_sourceHook = (Hook)classLoader.loadClass(
			_sourceHookClassName).newInstance();
	}

	protected void addDLFileEntry(
			String uuid, long fileEntryId, long groupId, long companyId,
			long userId, String userName, long versionUserId,
			String versionUserName, Date createDate, Date modifiedDate,
			long repositoryId, long folderId, String name, String extension,
			String mimeType, String title, String description,
			String extraSettings, String version, long size, int readCount,
			long smallImageId, long largeImageId, long custom1ImageId,
			long custom2ImageId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(9);

			sb.append("insert into DLFileEntry (uuid_, fileEntryId, groupId, ");
			sb.append("companyId, userId, userName, versionUserId, ");
			sb.append("versionUserName, createDate, modifiedDate, ");
			sb.append("repositoryId, folderId, name, extension, mimeType, ");
			sb.append("title, description, extraSettings, version, size_, ");
			sb.append("readCount, smallImageId, largeImageId, ");
			sb.append("custom1ImageId, custom2ImageId) values (");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

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
			ps.setLong(11, repositoryId);
			ps.setLong(12, folderId);
			ps.setString(13, name);
			ps.setString(14, extension);
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
			String userName, Date createDate, long repositoryId, long folderId,
			long fileEntryId, String extension, String mimeType, String title,
			String description, String changeLog, String extraSettings,
			long fileEntryTypeId, String version, long size, int status,
			long statusByUserId, String statusByUserName, Date statusDate)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(9);

			sb.append("insert into DLFileVersion (fileVersionId, groupId, ");
			sb.append("companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, repositoryId, folderId, fileEntryId, ");
			sb.append("extension, mimeType, title, description, changeLog, ");
			sb.append("extraSettings, fileEntryTypeId, version, size_, ");
			sb.append("status, statusByUserId, statusByUserName, statusDate) ");
			sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, fileVersionId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setDate(6, createDate);
			ps.setDate(7, statusDate);
			ps.setLong(8, repositoryId);
			ps.setLong(9, folderId);
			ps.setLong(10, fileEntryId);
			ps.setString(11, extension);
			ps.setString(12, mimeType);
			ps.setString(13, title);
			ps.setString(14, description);
			ps.setString(15, changeLog);
			ps.setString(16, extraSettings);
			ps.setLong(17, fileEntryTypeId);
			ps.setString(18, version);
			ps.setLong(19, size);
			ps.setInt(20, status);
			ps.setLong(21, statusByUserId);
			ps.setString(22, statusByUserName);
			ps.setDate(23, statusDate);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addDLFolderEntry(
			String uuid, long folderId, long groupId, long companyId,
			long userId, String userName, Date createDate, Date modifiedDate,
			long repositoryId, long parentFolderId, String name,
			String description, Date lastPostDate)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("insert into DLFolder (uuid_, folderId, groupId, ");
			sb.append("companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, repositoryId, mountPoint, ");
			sb.append("parentFolderId, name, description, lastPostDate) ");
			sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setString(1, uuid);
			ps.setLong(2, folderId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setDate(7, createDate);
			ps.setDate(8, modifiedDate);
			ps.setLong(9, repositoryId);
			ps.setBoolean(10, false);
			ps.setLong(11, parentFolderId);
			ps.setString(12, name);
			ps.setString(13, description);
			ps.setDate(14, lastPostDate);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateIGFolderEntries();
		updateIGImageEntries();
		updateIGFolderPermissions();
		updateIGImagePermissions();

		migrateImageFiles();

		UpgradeDocumentLibrary upgradeDocumentLibrary =
			new UpgradeDocumentLibrary();

		upgradeDocumentLibrary.updateSyncs();
	}

	protected Object[] getImage(long imageId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select type_, size_ from Image where imageId = " + imageId);

			rs = ps.executeQuery();

			if (rs.next()) {
				String type = rs.getString("type_");
				long size = rs.getLong("size_");

				return new Object[] {type, size};
			}

			return null;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void migrateFile(
			long repositoryId, long companyId, String name, Image image)
		throws Exception {

		InputStream is = _sourceHook.getImageAsStream(image);

		byte[] bytes = FileUtil.getBytes(is);

		if (name == null) {
			name = image.getImageId() + StringPool.PERIOD + image.getType();
		}

		if (DLStoreUtil.hasFile(companyId, repositoryId, name)) {
			DLStoreUtil.deleteFile(companyId, repositoryId, name);
		}

		DLStoreUtil.addFile(companyId, repositoryId, name, true, bytes);
	}

	protected void migrateImage(long imageId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(8);

			sb.append("select fileVersionId, fileEntry.fileEntryId ");
			sb.append("as fileEntryId, fileEntry.groupId as groupId, ");
			sb.append("fileEntry.companyId as companyId, fileEntry.folderId ");
			sb.append("as folderId, name, largeImageId, smallImageId, ");
			sb.append("custom1ImageId, custom2ImageId from ");
			sb.append("DLFileVersion fileVersion, DLFileEntry fileEntry ");
			sb.append("where fileEntry.fileEntryId = fileVersion.fileEntryId ");
			sb.append("and (largeImageId = ? or smallImageId = ? or ");
			sb.append("custom1ImageId = ? or custom2ImageId = ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, imageId);
			ps.setLong(2, imageId);
			ps.setLong(3, imageId);
			ps.setLong(4, imageId);

			rs = ps.executeQuery();

			if (rs.next()) {
				long fileVersionId = rs.getLong("fileVersionId");
				long fileEntryId = rs.getLong("fileEntryId");
				long companyId = rs.getLong("companyId");
				long groupId = rs.getLong("groupId");
				long folderId = rs.getLong("folderId");
				String name = rs.getString("name");
				long largeImageId = rs.getLong("largeImageId");
				long custom1ImageId = rs.getLong("custom1ImageId");
				long custom2ImageId = rs.getLong("custom2ImageId");

				Image image = ImageLocalServiceUtil.getImage(imageId);

				if (largeImageId == imageId) {
					long repositoryId = DLFolderConstants.getDataRepositoryId(
						groupId, folderId);

					try {
						migrateFile(repositoryId, companyId, name, image);
					}
					catch (Exception e) {
					}
				}
				else {
					InputStream is = _sourceHook.getImageAsStream(image);

					ImageProcessor.storeThumbnail(
						companyId, groupId, fileEntryId, fileVersionId,
						custom1ImageId, custom2ImageId, is, image.getType());
				}

				_sourceHook.deleteImage(image);
			}
			else if (!_sourceHookClassName.equals(DLHook.class.getName())) {
				Image image = ImageLocalServiceUtil.getImage(imageId);

				try {
					migrateFile(0, 0, null, image);
				}
				catch (Exception e) {
				}

				_sourceHook.deleteImage(image);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void migrateImageFiles() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement("select imageId from Image");

			rs = ps.executeQuery();

			while (rs.next()) {
				long imageId = rs.getLong("imageId");

				migrateImage(imageId);
			}

			StringBundler sb = new StringBundler(5);

			sb.append("delete from Image where imageId in (select ");
			sb.append("smallImageId from DLFileEntry) or imageId in (select ");
			sb.append("largeImageId from DLFileEntry) or imageId in (select ");
			sb.append("custom1ImageId from DLFileEntry) or imageId in ");
			sb.append("(select custom2ImageId from DLFileEntry)");

			runSQL(sb.toString());

			if (_sourceHookClassName.equals(DatabaseHook.class.getName())) {
				runSQL("update Image set text_ = ''");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateIGFolderEntries() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from IGFolder order by folderId asc");

			rs = ps.executeQuery();

			Map<Long, Long> folderIds = new HashMap<Long, Long>();

			while (rs.next()) {
				String uuid = rs.getString("uuid_");
				long folderId = rs.getLong("folderId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Date createDate = rs.getDate("createDate");
				Date modifiedDate = rs.getDate("modifiedDate");
				long parentFolderId = rs.getLong("parentFolderId");
				String name = rs.getString("name");
				String description = rs.getString("description");

				if (folderIds.containsKey(parentFolderId)) {
					parentFolderId = folderIds.get(parentFolderId);
				}

				boolean update = updateIGImageFolderId(
					groupId, name, parentFolderId, folderId, folderIds);

				if (!update) {
					addDLFolderEntry(
						uuid, folderId, groupId, companyId, userId, userName,
						createDate, modifiedDate, groupId, parentFolderId, name,
						description, modifiedDate);
				}
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

		runSQL(
			"delete from ResourcePermission where " +
				"name = 'com.liferay.portlet.imagegallery.model.IGFolder' " +
					"and primKey = '0'");

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("update ResourcePermission set name = '");
			sb.append(DLFolder.class.getName());
			sb.append("' where name = 'com.liferay.portlet.imagegallery.");
			sb.append("model.IGFolder'");

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
				String userName = rs.getString("userName");
				Date createDate = rs.getDate("createDate");
				Date modifiedDate = rs.getDate("modifiedDate");
				long folderId = rs.getLong("folderId");
				String title = rs.getString("name");
				String description = rs.getString("description");
				long smallImageId = rs.getLong("smallImageId");
				long largeImageId = rs.getLong("largeImageId");
				long custom1ImageId = rs.getLong("custom1ImageId");
				long custom2ImageId = rs.getLong("custom2ImageId");

				Object[] image = getImage(largeImageId);

				if (image == null) {
					continue;
				}

				String extension = (String)image[0];

				String mimeType = MimeTypesUtil.getContentType(
					"A." + extension);

				String name = String.valueOf(
					increment(DLFileEntry.class.getName()));

				long size = (Long)image[1];

				addDLFileEntry(
					uuid, imageId, groupId, companyId, userId,
					userName, userId, userName, createDate, modifiedDate,
					groupId, folderId, name, extension, mimeType, title,
					description, StringPool.BLANK, "1.0", size, 0, smallImageId,
					largeImageId, custom1ImageId, custom2ImageId);

				addDLFileVersion(
					increment(), groupId, companyId, userId, userName,
					createDate, groupId, folderId, imageId, extension, mimeType,
					title, description, StringPool.BLANK, StringPool.BLANK, 0,
					"1.0", size, 0, userId, userName, modifiedDate);
			}

			runSQL("drop table IGImage");
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected boolean updateIGImageFolderId(
			long groupId, String name, long parentFolderId, long folderId,
			Map<Long, Long> folderIds)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select folderId from DLFolder where groupId = " + groupId +
					" and parentFolderId = " + parentFolderId +
						" and name = '" + name + "'");

			rs = ps.executeQuery();

			if (rs.next()) {
				long newFolderId = rs.getLong("folderId");

				runSQL(
					"update IGImage set folderId = " + newFolderId +
						" where folderId = " + folderId);

				folderIds.put(folderId, newFolderId);

				return true;
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return false;
	}

	protected void updateIGImagePermissions() throws Exception {
		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			return;
		}

		runSQL(
			"delete from ResourcePermission where name = '" +
				_IG_IMAGE_CLASS_NAME + "' and primKey = '0'");

		runSQL(
			"update ResourcePermission set name = '" +
				DLFileEntry.class.getName() + "' where name = '" +
					_IG_IMAGE_CLASS_NAME + "'");
	}

	private static final String _IG_IMAGE_CLASS_NAME =
		"com.liferay.portlet.imagegallery.model.IGImage";

	private Hook _sourceHook;
	private String _sourceHookClassName;

}