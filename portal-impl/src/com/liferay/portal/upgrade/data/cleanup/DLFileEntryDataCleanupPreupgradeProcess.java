/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.processor.RawMetadataProcessor;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.TableOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.util.DataCleanupLoggingUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author István András Dézsi
 */
public class DLFileEntryDataCleanupPreupgradeProcess
	extends DataCleanupPreupgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {

		// First cleanup invalid DLFileEntry records, as this affects the next
		// set of cleanups

		upgrade(
			new DataCleanupPreupgradeProcess() {

				@Override
				protected void doUpgrade() throws Exception {
					try (PreparedStatement preparedStatement1 =
							connection.prepareStatement(
								"select fileEntryId, name from DLFileEntry " +
									"where name is null or name = ''");
						PreparedStatement preparedStatement2 =
							connection.prepareStatement(
								"delete from DLFileEntry where name is null " +
									"or name = ''");
						ResultSet resultSet =
							preparedStatement1.executeQuery()) {

						preparedStatement2.execute();

						if (!_log.isInfoEnabled()) {
							return;
						}

						DBInspector dbInspector = new DBInspector(connection);

						while (resultSet.next()) {
							long fileEntryId = resultSet.getLong("fileEntryId");
							String name = resultSet.getString("name");

							DataCleanupLoggingUtil.logDelete(
								_log, 1,
								dbInspector.normalizeName("DLFileEntry"),
								StringBundler.concat(
									"fileEntryId ", fileEntryId, " name was ",
									(name == null) ? "null" : "empty"));
						}
					}
				}

			});

		// Then remove DLFileEntry related data

		upgrade(
			new FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				StringBundler.concat(
					"classNameId in (select classNameId from ClassName_ where ",
					"value in ('", FileEntry.class.getName(), "', '",
					DLFileEntry.class.getName(), "'))"),
				new String[] {"classNameId"}, "classPK",
				new String[] {"fileEntryId"}, "DLFileEntry"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "fileEntryId", "DLFileEntryMetadata", "fileEntryId",
				"DLFileEntry"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "fileEntryId", "DLFileVersion", "fileEntryId",
				"DLFileEntry"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "fileEntryId", "DLFileVersionPreview", "fileEntryId",
				"DLFileEntry"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				null, "toFileEntryId", "DLFileShortcut", "fileEntryId",
				"DLFileEntry"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				"name = '" + DLFileEntry.class.getName() + "'", "primKeyId",
				"ResourcePermission", "fileEntryId", "DLFileEntry"));

		// Then delete DLFileShortcut related data (as some DLFileShortcut
		// could be deleted in the previous set of cleanups

		upgrade(
			new FilterableAllTablesOrphanReferencesDataCleanupPreupgradeProcess(
				StringBundler.concat(
					"classNameId = (select classNameId from ClassName_ where ",
					"value = '", DLFileShortcut.class.getName(), "')"),
				new String[] {"classNameId"}, "classPK",
				new String[] {"fileShortcutId"}, "DLFileShortcut"));
		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				"name = '" + DLFileShortcut.class.getName() + "'", "primKeyId",
				"ResourcePermission", "fileShortcutId", "DLFileShortcut"));

		// Then delete DLFileEntryMetadata related data (as some
		// DLFileEntryMetadata could be deleted in the previous set of cleanups

		upgrade(
			new TableOrphanReferencesDataCleanupPreupgradeProcess(
				StringBundler.concat(
					"exists (select 1 from DDMStructure where ",
					"DDMStorageLink.structureId = DDMStructure.structureId ",
					"and DDMStructure.classNameId in (select classNameId from ",
					"ClassName_ where value in ('",
					DLFileEntryMetadata.class.getName(), "', '",
					RawMetadataProcessor.class.getName(), "')))"),
				"classPK", "DDMStorageLink", "DDMStorageId",
				"DLFileEntryMetadata"));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryDataCleanupPreupgradeProcess.class);

}