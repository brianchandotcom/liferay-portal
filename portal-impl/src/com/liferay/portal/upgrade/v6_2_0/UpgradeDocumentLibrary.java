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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.kernel.tree.TreeModelTasksAdapter;
import com.liferay.portal.kernel.tree.TreePathUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.upgrade.v6_2_0.util.DLFileEntryTypeTable;
import com.liferay.portal.util.PortalInstances;

import java.io.Serializable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Dennis Ju
 * @author Mate Thurzo
 * @author Alexander Chow
 * @author Roberto Díaz
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	protected void deleteChecksumDirectory() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select distinct companyId from DLFileEntry");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong("companyId");

				DLStoreUtil.deleteDirectory(companyId, 0, "checksum");
			}
		}
	}

	protected void deleteTempDirectory() {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			DLStoreUtil.deleteDirectory(0, 0, "liferay_temp/");
		}
	}

	@Override
	protected void doUpgrade() throws Exception {

		// DLFileEntryType

		alter(
			DLFileEntryTypeTable.class,
			new AlterTableAddColumn("fileEntryTypeKey STRING"),
			new AlterColumnType("name", "STRING null"));

		updateFileEntryTypes();

		// Checksum directory

		deleteChecksumDirectory();

		// Temp directory

		deleteTempDirectory();

		// DLFolder

		updateDLFolderUserName();

		// Tree path

		updateTreePath();
	}

	protected String getUserName(long userId) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select firstName, middleName, lastName from User_ where " +
					"userId = ?")) {

			ps.setLong(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String firstName = rs.getString("firstName");
					String middleName = rs.getString("middleName");
					String lastName = rs.getString("lastName");

					FullNameGenerator fullNameGenerator =
						FullNameGeneratorFactory.getInstance();

					return fullNameGenerator.getFullName(
						firstName, middleName, lastName);
				}

				return StringPool.BLANK;
			}
		}
	}

	protected String localize(long companyId, String content, String key)
		throws Exception {

		String languageId = UpgradeProcessUtil.getDefaultLanguageId(companyId);

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		Map<Locale, String> localizationMap = new HashMap<>();

		localizationMap.put(locale, content);

		return LocalizationUtil.updateLocalization(
			localizationMap, StringPool.BLANK, key, languageId);
	}

	protected void rebuildTree(
			long companyId, PreparedStatement folderPreparedStatement,
			PreparedStatement fileEntryPreparedStatement,
			PreparedStatement fileShortcutPreparedStatement,
			PreparedStatement fileVersionPreparedStatement)
		throws PortalException {

		TreePathUtil.rebuildTree(
			companyId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringPool.SLASH,
			new TreeModelTasksAdapter<DLFolderTreeModel>() {

				@Override
				public List<DLFolderTreeModel> findTreeModels(
					long previousId, long companyId, long parentPrimaryKey,
					int size) {

					List<DLFolderTreeModel> treeModels = new ArrayList<>();

					try (PreparedStatement ps = connection.prepareStatement(
							_SELECT_DLFOLDER_BY_PARENT)) {

						ps.setLong(1, previousId);
						ps.setLong(2, companyId);
						ps.setLong(3, parentPrimaryKey);
						ps.setInt(4, WorkflowConstants.STATUS_IN_TRASH);
						ps.setFetchSize(size);

						try (ResultSet rs = ps.executeQuery()) {
							while (rs.next()) {
								long folderId = rs.getLong(1);

								DLFolderTreeModel treeModel =
									new DLFolderTreeModel(
										folderPreparedStatement);

								treeModel.setPrimaryKeyObj(folderId);

								treeModels.add(treeModel);
							}
						}
					}
					catch (SQLException sqle) {
						_log.error(
							"Unable to get folders with parent primary key " +
								parentPrimaryKey,
							sqle);
					}

					return treeModels;
				}

				@Override
				public void rebuildDependentModelsTreePaths(
						long parentPrimaryKey, String treePath)
					throws PortalException {

					try {
						fileEntryPreparedStatement.setString(1, treePath);
						fileEntryPreparedStatement.setLong(2, parentPrimaryKey);

						fileEntryPreparedStatement.addBatch();
					}
					catch (SQLException sqle) {
						_log.error(
							"Unable to update file entries with tree path " +
								treePath,
							sqle);
					}

					try {
						fileShortcutPreparedStatement.setString(1, treePath);
						fileShortcutPreparedStatement.setLong(
							2, parentPrimaryKey);

						fileShortcutPreparedStatement.addBatch();
					}
					catch (SQLException sqle) {
						_log.error(
							"Unable to update file shortcuts with tree path " +
								treePath,
							sqle);
					}

					try {
						fileVersionPreparedStatement.setString(1, treePath);
						fileVersionPreparedStatement.setLong(
							2, parentPrimaryKey);

						fileVersionPreparedStatement.addBatch();
					}
					catch (SQLException sqle) {
						_log.error(
							"Unable to update file versions with tree path " +
								treePath,
							sqle);
					}
				}

			});
	}

	protected void updateDLFolderUserName() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select distinct userId from DLFolder where userName is null " +
					"or userName = ''");
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update DLFolder set userName = ? where userId = ? and " +
						"(userName is null or userName = '')"))) {

			while (rs.next()) {
				long userId = rs.getLong("userId");

				String userName = getUserName(userId);

				if (Validator.isNotNull(userName)) {
					ps2.setString(1, userName);
					ps2.setLong(2, userId);

					ps2.addBatch();
				}
				else {
					if (_log.isInfoEnabled()) {
						_log.info("User " + userId + " does not exist");
					}
				}
			}

			ps2.executeBatch();
		}
	}

	protected void updateFileEntryType(
			long fileEntryTypeId, long companyId, String fileEntryTypeKey,
			String name, String description)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update DLFileEntryType set fileEntryTypeKey = ?, name = ?, " +
					"description = ? where fileEntryTypeId = ?")) {

			ps.setString(1, fileEntryTypeKey);
			ps.setString(2, localize(companyId, name, "Name"));
			ps.setString(3, localize(companyId, description, "Description"));
			ps.setLong(4, fileEntryTypeId);

			ps.executeUpdate();
		}
	}

	protected void updateFileEntryTypes() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select fileEntryTypeId, companyId, name, description from " +
					"DLFileEntryType");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long fileEntryTypeId = rs.getLong("fileEntryTypeId");
				long companyId = rs.getLong("companyId");
				String name = GetterUtil.getString(rs.getString("name"));
				String description = rs.getString("description");

				if (fileEntryTypeId ==
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

					name = DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT;
				}

				updateFileEntryType(
					fileEntryTypeId, companyId, StringUtil.toUpperCase(name),
					name, description);
			}
		}
	}

	protected void updateTreePath() {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long[] companyIds = PortalInstances.getCompanyIdsBySQL();

			for (long companyId : companyIds) {
				try (PreparedStatement folderPreparedStatement =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection,
							"update DLFolder set treePath = ? where folderId " +
								"= ?");
					PreparedStatement fileEntryPreparedStatement =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection,
							"update DLFileEntry set treePath = ? where " +
								"folderId = ?");
					PreparedStatement fileShortcutPreparedStatement =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection,
							"update DLFileShortcut set treePath = ? where " +
								"folderId = ?");
					PreparedStatement fileVersionPreparedStatement =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection,
							"update DLFileVersion set treePath = ? where " +
								"folderId = ?")) {

					try {
						rebuildTree(
							companyId, folderPreparedStatement,
							fileEntryPreparedStatement,
							fileShortcutPreparedStatement,
							fileVersionPreparedStatement);
					}
					catch (PortalException pe) {
						_log.error(
							"Unable to update tree paths for company " +
								companyId,
							pe);
					}

					folderPreparedStatement.executeBatch();

					fileEntryPreparedStatement.executeBatch();
					fileShortcutPreparedStatement.executeBatch();
					fileVersionPreparedStatement.executeBatch();
				}
			}
		}
		catch (SQLException sqle) {
			_log.error("Unable to update tree paths", sqle);
		}
	}

	private static final String _SELECT_DLFOLDER_BY_PARENT =
		"select folderId from DLFolder dlFolder where dlFolder.folderId > ? " +
			"and dlFolder.companyId = ? and dlFolder.parentFolderId = ? and " +
				"dlFolder.status != ?";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDocumentLibrary.class);

	private class DLFolderTreeModel implements TreeModel {

		public DLFolderTreeModel(PreparedStatement ps) {
			_ps = ps;
		}

		@Override
		public String buildTreePath() throws PortalException {
			return null;
		}

		@Override
		public Serializable getPrimaryKeyObj() {
			return _folderId;
		}

		@Override
		public String getTreePath() {
			return null;
		}

		public void setPrimaryKeyObj(Serializable primaryKeyObj) {
			_folderId = (Long)primaryKeyObj;
		}

		@Override
		public void updateTreePath(String treePath) {
			try {
				_ps.setString(1, treePath);
				_ps.setLong(2, _folderId);

				_ps.addBatch();
			}
			catch (SQLException sqle) {
				_log.error("Unable to update tree path: " + treePath, sqle);
			}
		}

		private long _folderId;
		private final PreparedStatement _ps;

	}

}