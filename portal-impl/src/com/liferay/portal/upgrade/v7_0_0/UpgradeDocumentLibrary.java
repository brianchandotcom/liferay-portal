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

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.portletrepository.PortletRepository;
import com.liferay.portal.upgrade.v7_0_0.util.DLFolderTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael Young
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	protected void addClassName(long classNameId, String className)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"insert into ClassName_ (mvccVersion, classNameId, value) " +
					"values (?, ?, ?)")) {

			ps.setLong(1, 0);
			ps.setLong(2, classNameId);
			ps.setString(3, className);

			ps.executeUpdate();
		}
	}

	protected void addDDMStructureLink(
			long ddmStructureLinkId, long classNameId, long classPK,
			long ddmStructureId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"insert into DDMStructureLink (structureLinkId, classNameId, " +
					"classPK, structureId) values (?, ?, ?, ?)")) {

			ps.setLong(1, ddmStructureLinkId);
			ps.setLong(2, classNameId);
			ps.setLong(3, classPK);
			ps.setLong(4, ddmStructureId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to add dynamic data mapping structure link for file " +
					"entry type " + classPK);

			throw e;
		}
	}

	@Override
	protected void doUpgrade() throws Exception {

		// DLFileEntry

		_populateEmptyTitles("DLFileEntry");

		updateFileEntryFileNames();

		// DLFileEntryType

		updateFileEntryTypeNamesAndDescriptions();

		updateFileEntryTypeDDMStructureLinks();

		// DLFileVersion

		_populateEmptyTitles("DLFileVersion");

		updateFileVersionFileNames();

		// DLFolder

		alter(
			DLFolderTable.class,
			new AlterColumnType("name", "VARCHAR(255) null"));

		updateRepositoryClassNameIds();
	}

	protected boolean hasFileEntry(
			long groupId, long folderId, long fileEntryId, String title,
			String fileName)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select count(*) from DLFileEntry where groupId = ? and " +
					"folderId = ? and ((fileEntryId <> ? and title = ?) or " +
						"fileName = ?)")) {

			ps.setLong(1, groupId);
			ps.setLong(2, folderId);
			ps.setLong(3, fileEntryId);
			ps.setString(4, title);
			ps.setString(5, fileName);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int count = rs.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #hasFileEntry(long, long, long, String, String)}
	 */
	@Deprecated
	protected boolean hasFileEntry(long groupId, long folderId, String fileName)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	protected void updateFileEntryFileNames() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL("alter table DLFileEntry add fileName VARCHAR(255) null");

			runSQL(
				"update DLFileEntry set fileName = title where title like " +
					"CONCAT('%.', extension) or extension = '' or extension " +
						"is null");

			runSQL(
				"update DLFileEntry set fileName = CONCAT(title, '.', " +
					"extension) where (fileName is null or fileName = '') " +
						"and LENGTH(title) + LENGTH(extension) < 255");

			_updateLongFileNames("DLFileEntry");

			runSQL(
				"update DLFileEntry set fileName = REPLACE(fileName, '/', " +
					"'_') where fileName is not null and fileName != ''");

			_fixDuplicateFileEntryFileNames();
		}
	}

	protected void updateFileEntryTypeDDMStructureLinks() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select * from DLFileEntryTypes_DDMStructures");
			ResultSet rs = ps.executeQuery()) {

			long classNameId = PortalUtil.getClassNameId(DLFileEntryType.class);

			while (rs.next()) {
				long structureId = rs.getLong("structureId");
				long fileEntryTypeId = rs.getLong("fileEntryTypeId");

				addDDMStructureLink(
					increment(), classNameId, fileEntryTypeId, structureId);
			}

			runSQL("drop table DLFileEntryTypes_DDMStructures");
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected void updateFileEntryTypeFileEntryTypeKeys() throws Exception {
	}

	protected void updateFileEntryTypeNamesAndDescriptions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps = connection.prepareStatement(
				"select companyId, groupId from Group_ where classNameId = " +
					"?")) {

			long classNameId = PortalUtil.getClassNameId(Company.class);

			ps.setLong(1, classNameId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long companyId = rs.getLong(1);
					long groupId = rs.getLong(2);

					updateFileEntryTypeNamesAndDescriptions(companyId, groupId);
				}
			}
		}
	}

	protected void updateFileEntryTypeNamesAndDescriptions(
			long companyId, long groupId)
		throws Exception {

		Map<String, String> nameLanguageKeys = new HashMap<>();

		nameLanguageKeys.put(
			DLFileEntryTypeConstants.NAME_CONTRACT,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_CONTRACT);
		nameLanguageKeys.put(
			DLFileEntryTypeConstants.NAME_MARKETING_BANNER,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_MARKETING_BANNER);
		nameLanguageKeys.put(
			DLFileEntryTypeConstants.NAME_ONLINE_TRAINING,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_ONLINE_TRAINING);
		nameLanguageKeys.put(
			DLFileEntryTypeConstants.NAME_SALES_PRESENTATION,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_SALES_PRESENTATION);

		for (Map.Entry<String, String> nameAndKey :
				nameLanguageKeys.entrySet()) {

			String dlFileEntryTypeKey = nameAndKey.getValue();
			String nameLanguageKey = nameAndKey.getKey();

			updateFileEntryTypeNamesAndDescriptions(
				companyId, groupId, dlFileEntryTypeKey, nameLanguageKey);
		}
	}

	protected void updateFileEntryTypeNamesAndDescriptions(
			long companyId, long groupId, String dlFileEntryTypeKey,
			String nameLanguageKey)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select fileEntryTypeId, name, description from " +
					"DLFileEntryType where groupId = ? and fileEntryTypeKey " +
						"= ?")) {

			ps.setLong(1, groupId);
			ps.setString(2, dlFileEntryTypeKey);

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					return;
				}

				long fileEntryTypeId = rs.getLong(1);
				String name = rs.getString(2);
				String description = rs.getString(3);

				if (rs.next()) {
					throw new IllegalStateException(
						String.format(
							"Found more than one row in table " +
								"DLFileEntryType with groupId %s and " +
									"fileEntryTypeKey %s",
							groupId, dlFileEntryTypeKey));
				}

				updateFileEntryTypeNamesAndDescriptions(
					companyId, fileEntryTypeId, nameLanguageKey, name,
					description);
			}
		}
	}

	protected void updateFileEntryTypeNamesAndDescriptions(
			long companyId, long dlFileEntryTypeId, String nameLanguageKey,
			String nameXML, String descriptionXML)
		throws Exception {

		boolean update = false;

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			UpgradeProcessUtil.getDefaultLanguageId(companyId));

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			nameXML);
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(descriptionXML);

		String value = LanguageUtil.get(defaultLocale, nameLanguageKey);

		String description = descriptionMap.get(defaultLocale);

		if (description == null) {
			descriptionMap.put(defaultLocale, value);

			update = true;
		}

		String name = nameMap.get(defaultLocale);

		if (name == null) {
			nameMap.put(defaultLocale, value);

			update = true;
		}

		if (update) {
			updateFileEntryTypeNamesAndDescriptions(
				dlFileEntryTypeId, nameXML, descriptionXML, nameMap,
				descriptionMap, defaultLocale);
		}
	}

	protected void updateFileEntryTypeNamesAndDescriptions(
			long fileEntryTypeId, String nameXML, String descriptionXML,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			Locale defaultLocale)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"update DLFileEntryType set name = ?, description = ? where " +
					"fileEntryTypeId = ?")) {

			String languageId = LanguageUtil.getLanguageId(defaultLocale);

			nameXML = LocalizationUtil.updateLocalization(
				nameMap, nameXML, "Name", languageId);
			descriptionXML = LocalizationUtil.updateLocalization(
				descriptionMap, descriptionXML, "Description", languageId);

			ps.setString(1, nameXML);
			ps.setString(2, descriptionXML);
			ps.setLong(3, fileEntryTypeId);

			int rowCount = ps.executeUpdate();

			if (rowCount != 1) {
				throw new IllegalStateException(
					String.format(
						"Updated %s rows in table DLFileEntryType with " +
							"fileEntryTypeId %s",
						rowCount, fileEntryTypeId));
			}
		}
	}

	protected void updateFileVersionFileName(
			long fileVersionId, String fileName)
		throws Exception {
	}

	protected void updateFileVersionFileNames() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL("alter table DLFileVersion add fileName VARCHAR(255) null");

			runSQL(
				"update DLFileVersion set fileName = title where title like " +
					"CONCAT('%.', extension) or extension = '' or extension " +
						"is null");

			runSQL(
				StringBundler.concat(
					"update DLFileVersion set fileName = CONCAT(title, ",
					"CONCAT('.', extension)) where (fileName is null or ",
					"fileName = '') and LENGTH(title) + LENGTH(extension) < ",
					"255"));

			_updateLongFileNames("DLFileVersion");
		}
	}

	protected void updateRepositoryClassNameIds() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long liferayRepositoryClassNameId = PortalUtil.getClassNameId(
				LiferayRepository.class);

			long portletRepositoryClassNameId = PortalUtil.getClassNameId(
				PortletRepository.class);

			if (portletRepositoryClassNameId == 0) {
				portletRepositoryClassNameId = increment();

				addClassName(
					portletRepositoryClassNameId,
					PortletRepository.class.getName());
			}

			try (PreparedStatement ps = connection.prepareStatement(
					"update Repository set classNameId = ? where classNameId " +
						"= ?")) {

				ps.setLong(1, portletRepositoryClassNameId);
				ps.setLong(2, liferayRepositoryClassNameId);

				ps.executeUpdate();
			}
		}
	}

	private void _fixDuplicateFileEntryFileNames() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select groupId, folderId, fileName from DLFileEntry group " +
					"by groupId, folderId, fileName having count(*) > 1");
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long folderId = rs.getLong("folderId");
				String fileName = rs.getString("fileName");

				_fixDuplicateFileEntryFileNames(groupId, folderId, fileName);
			}
		}
	}

	private void _fixDuplicateFileEntryFileNames(
			long groupId, long folderId, String fileName)
		throws Exception {

		Set<String> generatedUniqueFileNames = new HashSet<>();
		Set<String> generatedUniqueTitles = new HashSet<>();

		try (PreparedStatement ps1 = connection.prepareStatement(
				"select fileEntryId, extension, title, version from " +
					"DLFileEntry where groupId = ? and folderId = ? and " +
						"fileName = ?");
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update DLFileEntry set fileName = ?, title = ? where " +
						"fileEntryId = ?"));
			PreparedStatement ps3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DLFileVersion set title = ? where fileEntryId = " +
						"? and version = ? and status != ?")) {

			ps1.setLong(1, groupId);
			ps1.setLong(2, folderId);
			ps1.setString(3, fileName);

			try (ResultSet rs = ps1.executeQuery()) {
				rs.next();

				int i = 1;

				while (rs.next()) {
					long fileEntryId = rs.getLong("fileEntryId");
					String extension = GetterUtil.getString(
						rs.getString("extension"));
					String title = GetterUtil.getString(rs.getString("title"));
					String version = rs.getString("version");

					String uniqueFileName = null;

					String titleExtension = StringPool.BLANK;
					String titleWithoutExtension = title;

					if (title.endsWith(StringPool.PERIOD + extension)) {
						titleExtension = StringPool.PERIOD + extension;

						titleWithoutExtension = titleWithoutExtension.substring(
							0, title.length() - titleExtension.length());
					}

					do {
						String count = String.valueOf(i);

						int availableLength =
							254 - (extension.length() + count.length());

						if (Validator.isNotNull(extension)) {
							availableLength--;
						}

						if (titleWithoutExtension.length() > availableLength) {
							titleWithoutExtension =
								titleWithoutExtension.substring(
									0, availableLength);
						}

						StringBundler sb = new StringBundler(4);

						sb.append(titleWithoutExtension);
						sb.append(StringPool.UNDERLINE);
						sb.append(count);

						if (Validator.isNotNull(titleExtension)) {
							sb.append(titleExtension);
						}

						title = sb.toString();

						uniqueFileName = DLUtil.getSanitizedFileName(
							title, extension);

						i++;
					}
					while (generatedUniqueFileNames.contains(uniqueFileName) ||
						   generatedUniqueTitles.contains(title) ||
						   hasFileEntry(
							   groupId, folderId, fileEntryId, title,
							   uniqueFileName));

					generatedUniqueFileNames.add(uniqueFileName);
					generatedUniqueTitles.add(title);

					ps2.setString(1, uniqueFileName);
					ps2.setString(2, title);
					ps2.setLong(3, fileEntryId);

					ps2.addBatch();

					ps3.setString(1, title);
					ps3.setLong(2, fileEntryId);
					ps3.setString(3, version);
					ps3.setInt(4, WorkflowConstants.STATUS_IN_TRASH);

					ps3.addBatch();
				}

				ps2.executeBatch();

				ps3.executeBatch();
			}
		}
	}

	private void _populateEmptyTitles(String tableName) throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				StringBundler.concat(
					"update ", tableName, " set title = ",
					"CONCAT('unknown-title-', CAST_TEXT(fileEntryId)) where ",
					"title = '' or title is null"));
		}
	}

	private void _updateLongFileNames(String tableName) throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select fileEntryId, title, extension from " + tableName +
					" where fileName = '' or fileName is null");
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(
					"update " + tableName +
						" set fileName = ? where fileEntryId = ?"));
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long fileEntryId = rs.getLong("fileEntryId");
				String extension = GetterUtil.getString(
					rs.getString("extension"));
				String title = GetterUtil.getString(rs.getString("title"));

				int availableLength = 254 - extension.length();

				String fileName =
					title.substring(0, availableLength) + StringPool.PERIOD +
						extension;

				ps2.setString(1, fileName);

				ps2.setLong(2, fileEntryId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDocumentLibrary.class);

}