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

package com.liferay.sync.internal.upgrade.v1_0_2;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.sync.service.DLSyncEventLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.sync.constants.SyncDLObjectConstants;
import com.liferay.sync.internal.configuration.SyncServiceConfigurationValues;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Dennis Ju
 */
public class UpgradeSyncDLObject extends UpgradeProcess {

	public UpgradeSyncDLObject(
		CounterLocalService counterLocalService,
		DLSyncEventLocalService dlSyncEventLocalService,
		GroupLocalService groupLocalService) {

		_counterLocalService = counterLocalService;
		_dlSyncEventLocalService = dlSyncEventLocalService;
		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			ActionableDynamicQuery actionableDynamicQuery =
				_groupLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> {
					Property classNameId = PropertyFactoryUtil.forName(
						"classNameId");
					Property siteProperty = PropertyFactoryUtil.forName("site");

					dynamicQuery.add(
						RestrictionsFactoryUtil.or(
							classNameId.eq(
								PortalUtil.getClassNameId(User.class)),
							siteProperty.eq(true)));
				});
			actionableDynamicQuery.setPerformActionMethod(
				(Group group) -> {
					if (group.isStaged()) {
						return;
					}

					try {
						verifyDLFileEntriesAndFolders(group.getGroupId());

						verifyLocks(group.getGroupId());
						verifyMacPackages(group.getGroupId());
					}
					catch (Exception exception) {
						throw new PortalException(exception);
					}
				});

			actionableDynamicQuery.performActions();

			_dlSyncEventLocalService.deleteDLSyncEvents();
		}
	}

	protected void verifyDLFileEntriesAndFolders(long groupId)
		throws Exception {

		StringBundler sb1 = new StringBundler(50);

		sb1.append("select DLFolder.companyId, DLFolder.userId, ");
		sb1.append("DLFolder.userName, DLFolder.createDate, ");
		sb1.append("DLFolder.modifiedDate, DLFolder.repositoryId, ");
		sb1.append("DLFolder.parentFolderId as parentFolderId, ");
		sb1.append("DLFolder.treePath, DLFolder.name, '' as extension, '' as ");
		sb1.append("mimeType, DLFolder.description, '' as changeLog, '' as ");
		sb1.append("version, 0 as versionId, 0 as size_, '");
		sb1.append(SyncDLObjectConstants.TYPE_FOLDER);
		sb1.append("' as type, DLFolder.folderId as typePK, DLFolder.uuid_ ");
		sb1.append("as typeUuid, DLFolder.status from DLFolder where ");
		sb1.append("DLFolder.repositoryId = ");
		sb1.append(groupId);
		sb1.append(" union all select DLFileVersion.companyId, ");
		sb1.append("DLFileVersion.userId, DLFileVersion.userName, ");
		sb1.append("DLFileVersion.createDate, DLFileVersion.modifiedDate, ");
		sb1.append("DLFileVersion.repositoryId, DLFileVersion.folderId as ");
		sb1.append("parentFolderId, DLFileVersion.treePath, ");
		sb1.append("DLFileVersion.title as name, DLFileVersion.extension, ");
		sb1.append("DLFileVersion.mimeType, DLFileVersion.description, ");
		sb1.append("DLFileVersion.changeLog, DLFileVersion.version, ");
		sb1.append("DLFileVersion.fileVersionId as versionId, ");
		sb1.append("DLFileVersion.size_ as size_, '");
		sb1.append(SyncDLObjectConstants.TYPE_FILE);
		sb1.append("' as type, DLFileVersion.fileEntryId as typePK, ");
		sb1.append("DLFileEntry.uuid_ as typeUuid, DLFileVersion.status from ");
		sb1.append("DLFileEntry, DLFileVersion where ");
		sb1.append("DLFileEntry.repositoryId = ");
		sb1.append(groupId);
		sb1.append(" and DLFileEntry.fileEntryId = DLFileVersion.fileEntryId ");
		sb1.append("and DLFileEntry.version = DLFileVersion.version union ");
		sb1.append("all select DLFileVersion.companyId, ");
		sb1.append("DLFileVersion.userId, DLFileVersion.userName, ");
		sb1.append("DLFileVersion.createDate, DLFileVersion.modifiedDate, ");
		sb1.append("DLFileVersion.repositoryId, DLFileVersion.folderId as ");
		sb1.append("parentFolderId, DLFileVersion.treePath, ");
		sb1.append("DLFileVersion.title as name, DLFileVersion.extension, ");
		sb1.append("DLFileVersion.mimeType, DLFileVersion.description, ");
		sb1.append("DLFileVersion.changeLog, DLFileVersion.version, ");
		sb1.append("DLFileVersion.fileVersionId as versionId, ");
		sb1.append("DLFileVersion.size_ as size_, '");
		sb1.append(SyncDLObjectConstants.TYPE_PRIVATE_WORKING_COPY);
		sb1.append("' as type, DLFileVersion.fileEntryId as typePK, ");
		sb1.append("DLFileEntry.uuid_ as typeUuid, DLFileVersion.status from ");
		sb1.append("DLFileEntry, DLFileVersion where ");
		sb1.append("DLFileEntry.repositoryId = ");
		sb1.append(groupId);
		sb1.append(" and DLFileEntry.fileEntryId = DLFileVersion.fileEntryId ");
		sb1.append("and DLFileVersion.version = '");
		sb1.append(DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION);
		sb1.append("'");

		StringBundler sb2 = new StringBundler(6);

		sb2.append("insert into SyncDLObject (syncDLObjectId, companyId, ");
		sb2.append("userId, userName, createTime, modifiedTime, ");
		sb2.append("repositoryId, parentFolderId, treePath, name, extension, ");
		sb2.append("mimeType, description, changeLog, version, versionId, ");
		sb2.append("size_, event, type_, typePK, typeUuid) values (?, ?, ?, ");
		sb2.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb2.toString());
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				int status = rs.getInt("status");

				String event = StringPool.BLANK;

				if (status == WorkflowConstants.STATUS_IN_TRASH) {
					event = SyncDLObjectConstants.EVENT_TRASH;
				}
				else {
					event = SyncDLObjectConstants.EVENT_ADD;
				}

				ps2.setLong(1, _counterLocalService.increment());
				ps2.setLong(2, rs.getLong("companyId"));
				ps2.setLong(3, rs.getLong("userId"));
				ps2.setString(4, rs.getString("userName"));
				ps2.setLong(5, createDate.getTime());
				ps2.setLong(6, modifiedDate.getTime());
				ps2.setLong(7, groupId);
				ps2.setLong(8, rs.getLong("parentFolderId"));
				ps2.setString(9, rs.getString("treePath"));
				ps2.setString(10, rs.getString("name"));
				ps2.setString(11, rs.getString("extension"));
				ps2.setString(12, rs.getString("mimeType"));
				ps2.setString(13, rs.getString("description"));
				ps2.setString(14, rs.getString("changeLog"));
				ps2.setString(15, rs.getString("version"));
				ps2.setLong(16, rs.getLong("versionId"));
				ps2.setLong(17, rs.getLong("size_"));
				ps2.setString(18, event);
				ps2.setString(19, rs.getString("type"));
				ps2.setLong(20, rs.getLong("typePK"));
				ps2.setString(21, rs.getString("typeUuid"));

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected void verifyLocks(long groupId) throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("select Lock_.expirationDate, Lock_.userId, ");
		sb.append("Lock_.userName, DLFileVersion.fileEntryId from ");
		sb.append("DLFileVersion, Lock_ where DLFileVersion.version = '");
		sb.append(DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION);
		sb.append("' and CAST_TEXT(DLFileVersion.fileEntryId) = Lock_.key_");

		String sql = SQLTransformer.transform(sb.toString());

		try (PreparedStatement ps1 = connection.prepareStatement(sql);
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"update SyncDLObject set lockExpirationDate = ?, ",
						"lockUserId = ?, lockUserName = ? where typePK = ? ",
						"and repositoryId = ", groupId));
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				ps2.setTimestamp(1, rs.getTimestamp("expirationDate"));
				ps2.setLong(2, rs.getLong("userId"));
				ps2.setString(3, rs.getString("userName"));
				ps2.setLong(4, rs.getLong("fileEntryId"));

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected void verifyMacPackages(long groupId) throws Exception {
		String[] fileNames =
			SyncServiceConfigurationValues.SYNC_MAC_PACKAGE_METADATA_FILE_NAMES;

		StringBundler sb = new StringBundler((fileNames.length * 4) + 5);

		sb.append("select DLFolder.folderId, DLFolder.name from DLFolder, ");
		sb.append("DLFileEntry where DLFolder.repositoryId = ");
		sb.append(groupId);
		sb.append(" and DLFileEntry.folderId = DLFolder.folderId and ");
		sb.append("DLFileEntry.title in (");

		for (int i = 0; i < fileNames.length; i++) {
			sb.append(StringPool.APOSTROPHE);
			sb.append(fileNames[i]);
			sb.append(StringPool.APOSTROPHE);

			if (i != (fileNames.length - 1)) {
				sb.append(CharPool.COMMA);
			}
		}

		sb.append(")");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update SyncDLObject set extraSettings = ? where typePK " +
						"= ?");
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				String name = rs.getString("name");

				if (!ArrayUtil.contains(
						SyncServiceConfigurationValues.
							SYNC_MAC_PACKAGE_FOLDER_EXTENSIONS,
						FileUtil.getExtension(name))) {

					continue;
				}

				JSONObject extraSettingsJSONObject = JSONUtil.put(
					"macPackage", true);

				ps2.setString(1, extraSettingsJSONObject.toString());

				ps2.setLong(2, rs.getLong("folderId"));

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	private final CounterLocalService _counterLocalService;
	private final DLSyncEventLocalService _dlSyncEventLocalService;
	private final GroupLocalService _groupLocalService;

}