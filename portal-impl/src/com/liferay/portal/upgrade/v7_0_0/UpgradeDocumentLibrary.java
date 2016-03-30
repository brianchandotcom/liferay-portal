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
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.portletrepository.PortletRepository;
import com.liferay.portal.upgrade.v7_0_0.util.DLFolderTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

		updateFileEntryFileNames();

		// DLFileEntryType

		updateFileEntryTypeNamesAndDescriptions();

		updateFileEntryTypeDDMStructureLinks();

		// DLFileVersion

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

	protected void updateFileEntryFileNames() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL("alter table DLFileEntry add fileName VARCHAR(255) null");

			try (PreparedStatement ps1 = connection.prepareStatement(
					"select fileEntryId, groupId, folderId, extension, title," +
						" version from DLFileEntry");
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.autoBatch(
						connection.prepareStatement(
							"update DLFileEntry set fileName = ?, title = ? " +
								"where fileEntryId = ?"));
				PreparedStatement ps3 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"update DLFileVersion set title = ? where " +
							"fileEntryId = " + "? and version = ?");
				ResultSet rs = ps1.executeQuery()) {

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

					String uniqueTitle = title;

					for (int i = 1;; i++) {
						if (!hasFileEntry(
								groupId, folderId, fileEntryId, uniqueTitle,
								uniqueFileName)) {

							break;
						}

						uniqueTitle =
							titleWithoutExtension + StringPool.UNDERLINE +
								String.valueOf(i);

						if (Validator.isNotNull(titleExtension)) {
							uniqueTitle += StringPool.PERIOD.concat(
								titleExtension);
						}

						uniqueFileName = DLUtil.getSanitizedFileName(
							uniqueTitle, extension);
					}

					ps2.setString(1, uniqueFileName);

					if (Validator.isNotNull(uniqueTitle)) {
						ps2.setString(2, uniqueTitle);
					}
					else {
						ps2.setString(2, title);
					}

					ps2.setLong(3, fileEntryId);

					ps2.addBatch();

					if (Validator.isNotNull(uniqueTitle)) {
						ps3.setString(1, uniqueTitle);
						ps3.setLong(2, fileEntryId);
						ps3.setString(3, version);

						ps3.addBatch();
					}
				}

				ps2.executeBatch();

				ps3.executeBatch();
			}
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
							"Found more than one row in table DLFileEntryType" +
								" with groupId %s and fileEntryTypeKey %s",
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

		String defaultValue = LanguageUtil.get(defaultLocale, nameLanguageKey);

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			nameXML);
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(descriptionXML);

		for (Locale locale : LanguageUtil.getSupportedLocales()) {
			String value = LanguageUtil.get(locale, nameLanguageKey);

			if (!locale.equals(defaultLocale) && value.equals(defaultValue)) {
				continue;
			}

			String description = descriptionMap.get(locale);

			if (description == null) {
				descriptionMap.put(locale, value);

				update = true;
			}

			String name = nameMap.get(locale);

			if (name == null) {
				nameMap.put(locale, value);

				update = true;
			}
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

			try (PreparedStatement ps1 = connection.prepareStatement(
					"select fileVersionId, extension, title from " +
						"DLFileVersion");
				PreparedStatement ps2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"update DLFileVersion set fileName = ? where " +
							"fileVersionId = ?");
				ResultSet rs = ps1.executeQuery()) {

				while (rs.next()) {
					long fileVersionId = rs.getLong("fileVersionId");
					String extension = GetterUtil.getString(
						rs.getString("extension"));
					String title = GetterUtil.getString(rs.getString("title"));

					String fileName = DLUtil.getSanitizedFileName(
						title, extension);

					ps2.setString(1, fileName);
					ps2.setLong(2, fileVersionId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
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

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDocumentLibrary.class);

}