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

package com.liferay.portal.kernel.upgrade.v6_2_0;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseUpgradeAttachments extends UpgradeProcess {

	protected long addDLFileEntry(
			long groupId, long companyId, long userId, String className,
			long classPK, String userName, Timestamp createDate,
			long repositoryId, long folderId, String name, String extension,
			String mimeType, String title, long size)
		throws Exception {

		PreparedStatement ps = null;

		try {
			long fileEntryId = increment();

			StringBundler sb = new StringBundler(9);

			sb.append("insert into DLFileEntry (uuid_, fileEntryId, groupId, ");
			sb.append("companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, classNameId, classPK, repositoryId, ");
			sb.append("folderId, name, extension, mimeType, title, ");
			sb.append("description, extraSettings, fileEntryTypeId, version, ");
			sb.append("size_, readCount, smallImageId, largeImageId, ");
			sb.append("custom1ImageId, custom2ImageId) values (?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?)");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, fileEntryId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setTimestamp(7, createDate);
			ps.setTimestamp(8, createDate);
			ps.setLong(9, PortalUtil.getClassNameId(className));
			ps.setLong(10, classPK);
			ps.setLong(11, repositoryId);
			ps.setLong(12, folderId);
			ps.setString(13, name);
			ps.setString(14, extension);
			ps.setString(15, mimeType);
			ps.setString(16, title);
			ps.setString(17, StringPool.BLANK);
			ps.setString(18, StringPool.BLANK);
			ps.setLong(19, 0);
			ps.setString(20, "1.0");
			ps.setLong(21, size);
			ps.setInt(22, 0);
			ps.setLong(23, 0);
			ps.setLong(24, 0);
			ps.setLong(25, 0);
			ps.setLong(26, 0);

			ps.executeUpdate();

			Map<String, Long> bitwiseValues = getBitwiseValues(
				"com.liferay.portlet.documentlibrary.model.DLFileEntry");

			long bitwiseValue = getBitwiseValue(
				bitwiseValues, ListUtil.fromArray(ActionKeys.VIEW));

			addResourcePermission(
				companyId,
				"com.liferay.portlet.documentlibrary.model.DLFileEntry",
				fileEntryId, getRoleId(companyId, RoleConstants.GUEST),
				bitwiseValue);
			addResourcePermission(
				companyId,
				"com.liferay.portlet.documentlibrary.model.DLFileEntry",
				fileEntryId, getRoleId(companyId, RoleConstants.SITE_MEMBER),
				bitwiseValue);

			return fileEntryId;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add file entry " + name, e);
			}

			return -1;
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void addDLFileVersion(
			long fileVersionId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, long repositoryId,
			long folderId, long fileEntryId, String extension, String mimeType,
			String title, long size)
		throws Exception {

		PreparedStatement ps = null;

		try {
			StringBundler sb = new StringBundler(8);

			sb.append("insert into DLFileVersion (uuid_, fileVersionId, ");
			sb.append("groupId, companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, repositoryId, folderId, fileEntryId, ");
			sb.append("extension, mimeType, title, description, changeLog, ");
			sb.append("extraSettings, fileEntryTypeId, version, size_, ");
			sb.append("status, statusByUserId, statusByUserName, statusDate) ");
			sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, fileVersionId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setTimestamp(7, createDate);
			ps.setTimestamp(8, createDate);
			ps.setLong(9, repositoryId);
			ps.setLong(10, folderId);
			ps.setLong(11, fileEntryId);
			ps.setString(12, extension);
			ps.setString(13, mimeType);
			ps.setString(14, title);
			ps.setString(15, StringPool.BLANK);
			ps.setString(16, StringPool.BLANK);
			ps.setString(17, StringPool.BLANK);
			ps.setLong(18, 0);
			ps.setString(19, "1.0");
			ps.setLong(20, size);
			ps.setInt(21, 0);
			ps.setLong(22, userId);
			ps.setString(23, userName);
			ps.setTimestamp(24, createDate);

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to add file version 1.0 for file entry " + title,
					e);
			}
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected long addDLFolder(
			long folderId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, long repositoryId,
			boolean mountPoint, long parentFolderId, String name,
			boolean hidden)
		throws Exception {

		PreparedStatement ps = null;

		try {
			StringBundler sb = new StringBundler(8);

			sb.append("insert into DLFolder (uuid_, folderId, groupId, ");
			sb.append("companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, repositoryId, mountPoint, ");
			sb.append("parentFolderId, name, description, lastPostDate, ");
			sb.append("defaultFileEntryTypeId, hidden_, ");
			sb.append("overrideFileEntryTypes, status, statusByUserId, ");
			sb.append("statusByUserName, statusDate) values (?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, folderId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setTimestamp(7, createDate);
			ps.setTimestamp(8, createDate);
			ps.setLong(9, repositoryId);
			ps.setBoolean(10, mountPoint);
			ps.setLong(11, parentFolderId);
			ps.setString(12, name);
			ps.setString(13, StringPool.BLANK);
			ps.setTimestamp(14, createDate);
			ps.setLong(15, 0);
			ps.setBoolean(16, hidden);
			ps.setBoolean(17, false);
			ps.setLong(18, 0);
			ps.setLong(19, userId);
			ps.setString(20, userName);
			ps.setTimestamp(21, createDate);

			ps.executeUpdate();

			Map<String, Long> bitwiseValues = getBitwiseValues(
				"com.liferay.portlet.documentlibrary.model.DLFolder");

			long guestBitwiseValue = getBitwiseValue(
				bitwiseValues, ListUtil.fromArray(ActionKeys.VIEW));

			addResourcePermission(
				companyId, "com.liferay.portlet.documentlibrary.model.DLFolder",
				folderId, getRoleId(companyId, RoleConstants.GUEST),
				guestBitwiseValue);

			List<String> siteMemberActionIds = new ArrayList<>();

			siteMemberActionIds.add(ActionKeys.ADD_DOCUMENT);
			siteMemberActionIds.add(ActionKeys.ADD_SUBFOLDER);
			siteMemberActionIds.add(ActionKeys.VIEW);

			long siteMemberBitwiseValue = getBitwiseValue(
				bitwiseValues, siteMemberActionIds);

			addResourcePermission(
				companyId, "com.liferay.portlet.documentlibrary.model.DLFolder",
				folderId, getRoleId(companyId, RoleConstants.SITE_MEMBER),
				siteMemberBitwiseValue);

			return folderId;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add folder " + name, e);
			}

			return -1;
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected long addRepository(
			long groupId, long companyId, long userId, String userName,
			Timestamp createDate, long classNameId, String portletId)
		throws Exception {

		long repositoryId = increment();

		long folderId = addDLFolder(
			increment(), groupId, companyId, userId, userName, createDate,
			repositoryId, true, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			portletId, true);

		if (folderId < 0) {
			return -1;
		}

		PreparedStatement ps = null;

		try {
			StringBundler sb = new StringBundler(5);

			sb.append("insert into Repository (uuid_, repositoryId, groupId, ");
			sb.append("companyId, userId, userName, createDate, ");
			sb.append("modifiedDate, classNameId, name, description, ");
			sb.append("portletId, typeSettings, dlFolderId) values (?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			ps = connection.prepareStatement(sb.toString());

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, repositoryId);
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setTimestamp(7, createDate);
			ps.setTimestamp(8, createDate);
			ps.setLong(9, classNameId);
			ps.setString(10, portletId);
			ps.setString(11, StringPool.BLANK);
			ps.setString(12, portletId);
			ps.setString(13, StringPool.BLANK);
			ps.setLong(14, folderId);

			ps.executeUpdate();

			return repositoryId;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to add repository for portlet " + portletId, e);
			}

			return -1;
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void addResourcePermission(
			long companyId, String className, long primKey, long roleId,
			long actionIds)
		throws Exception {

		PreparedStatement ps = null;

		try {
			long resourcePermissionId = increment(
				ResourcePermission.class.getName());

			StringBundler sb = new StringBundler(3);

			sb.append("insert into ResourcePermission (resourcePermissionId, ");
			sb.append("companyId, name, scope, primKey, roleId, ownerId, ");
			sb.append("actionIds) values (?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = connection.prepareStatement(sql);

			ps.setLong(1, resourcePermissionId);
			ps.setLong(2, companyId);
			ps.setString(3, className);
			ps.setInt(4, ResourceConstants.SCOPE_INDIVIDUAL);
			ps.setLong(5, primKey);
			ps.setLong(6, roleId);
			ps.setLong(7, 0);
			ps.setLong(8, actionIds);

			ps.executeUpdate();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add resource permission " + className, e);
			}
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateAttachments();
	}

	protected String[] getAttachments(
			long companyId, long containerModelId, long resourcePrimKey)
		throws Exception {

		String dirName = getDirName(containerModelId, resourcePrimKey);

		return DLStoreUtil.getFileNames(
			companyId, CompanyConstants.SYSTEM, dirName);
	}

	protected long getBitwiseValue(
		Map<String, Long> bitwiseValues, List<String> actionIds) {

		long bitwiseValue = 0;

		for (String actionId : actionIds) {
			Long actionIdBitwiseValue = bitwiseValues.get(actionId);

			if (actionIdBitwiseValue == null) {
				continue;
			}

			bitwiseValue |= actionIdBitwiseValue;
		}

		return bitwiseValue;
	}

	protected Map<String, Long> getBitwiseValues(String name) throws Exception {
		Map<String, Long> bitwiseValues = _bitwiseValues.get(name);

		if (bitwiseValues != null) {
			return bitwiseValues;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select actionId, bitwiseValue from ResourceAction where " +
					"name = ?");

			ps.setString(1, name);

			rs = ps.executeQuery();

			bitwiseValues = new HashMap<>();

			while (rs.next()) {
				String actionId = rs.getString("actionId");
				long bitwiseValue = rs.getLong("bitwiseValue");

				bitwiseValues.put(actionId, bitwiseValue);
			}

			_bitwiseValues.put(name, bitwiseValues);

			return bitwiseValues;
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected abstract String getClassName();

	protected long getClassNameId() {
		return PortalUtil.getClassNameId(getClassName());
	}

	protected long getContainerModelFolderId(
			long groupId, long companyId, long resourcePrimKey,
			long containerModelId, long userId, String userName,
			Timestamp createDate)
		throws Exception {

		return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}

	protected abstract String getDirName(
		long containerModelId, long resourcePrimKey);

	protected long getFolderId(
			long groupId, long companyId, long userId, String userName,
			Timestamp createDate, long repositoryId, long parentFolderId,
			String name, boolean hidden)
		throws Exception {

		if ((repositoryId < 0) || (parentFolderId < 0)) {
			return -1;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select folderId from DLFolder where repositoryId = ? and " +
					"parentFolderId = ? and name = ?");

			ps.setLong(1, repositoryId);
			ps.setLong(2, parentFolderId);
			ps.setString(3, name);

			rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getLong(1);
			}
		}
		finally {
			DataAccess.cleanUp(ps);
		}

		return addDLFolder(
			increment(), groupId, companyId, userId, userName, createDate,
			repositoryId, false, parentFolderId, name, hidden);
	}

	protected abstract String getPortletId();

	protected long getRepositoryId(
			long groupId, long companyId, long userId, String userName,
			Timestamp createDate, long classNameId, String portletId)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select repositoryId from Repository where groupId = ? and " +
					"name = ? and portletId = ?");

			ps.setLong(1, groupId);
			ps.setString(2, portletId);
			ps.setString(3, portletId);

			rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getLong(1);
			}
		}
		finally {
			DataAccess.cleanUp(ps);
		}

		return addRepository(
			groupId, companyId, userId, userName, createDate, classNameId,
			portletId);
	}

	protected long getRoleId(long companyId, String name) throws Exception {
		String roleIdsKey = companyId + StringPool.POUND + name;

		Long roleId = _roleIds.get(roleIdsKey);

		if (roleId != null) {
			return roleId;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select roleId from Role_ where companyId = ? and name = ?");

			ps.setLong(1, companyId);
			ps.setString(2, name);

			rs = ps.executeQuery();

			if (rs.next()) {
				roleId = rs.getLong("roleId");
			}

			_roleIds.put(roleIdsKey, roleId);

			return roleId;
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected abstract void updateAttachments() throws Exception;

	protected void updateEntryAttachments(
			long companyId, long groupId, long resourcePrimKey,
			long containerModelId, long userId, String userName)
		throws Exception {

		String[] attachments = getAttachments(
			companyId, containerModelId, resourcePrimKey);

		if (ArrayUtil.isEmpty(attachments)) {
			return;
		}

		Timestamp createDate = new Timestamp(System.currentTimeMillis());

		long repositoryId = getRepositoryId(
			groupId, companyId, userId, userName, createDate,
			PortalUtil.getClassNameId(_LIFERAY_REPOSITORY_CLASS_NAME),
			getPortletId());

		if (repositoryId < 0) {
			return;
		}

		long containerModelFolderId = getContainerModelFolderId(
			groupId, companyId, resourcePrimKey, containerModelId, userId,
			userName, createDate);

		if (containerModelFolderId < 0) {
			return;
		}

		for (String attachment : attachments) {
			String name = String.valueOf(
				increment(
					"com.liferay.portlet.documentlibrary.model.DLFileEntry"));

			String title = FileUtil.getShortFileName(attachment);

			String extension = FileUtil.getExtension(title);

			String mimeType = MimeTypesUtil.getExtensionContentType(extension);

			try {
				long size = DLStoreUtil.getFileSize(
					companyId, CompanyConstants.SYSTEM, attachment);

				long fileEntryId = addDLFileEntry(
					groupId, companyId, userId, getClassName(), resourcePrimKey,
					userName, createDate, repositoryId, containerModelFolderId,
					name, extension, mimeType, title, size);

				if (fileEntryId < 0) {
					continue;
				}

				addDLFileVersion(
					increment(), groupId, companyId, userId, userName,
					createDate, repositoryId, containerModelFolderId,
					fileEntryId, extension, mimeType, title, size);

				byte[] bytes = DLStoreUtil.getFileAsBytes(
					companyId, CompanyConstants.SYSTEM, attachment);

				DLStoreUtil.addFile(
					companyId, containerModelFolderId, name, false, bytes);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to add attachment " + attachment, e);
				}
			}

			try {
				DLStoreUtil.deleteFile(
					companyId, CompanyConstants.SYSTEM, attachment);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to delete attachment " + attachment, e);
				}
			}
		}
	}

	private static final String _LIFERAY_REPOSITORY_CLASS_NAME =
		"com.liferay.portal.repository.liferayrepository.LiferayRepository";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUpgradeAttachments.class);

	private final Map<String, Map<String, Long>> _bitwiseValues =
		new HashMap<>();
	private final Map<String, Long> _roleIds = new HashMap<>();

}