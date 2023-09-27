/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.portletrepository.PortletRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into ClassName_ (mvccVersion, classNameId, value) " +
					"values (?, ?, ?)")) {

			preparedStatement.setLong(1, 0);
			preparedStatement.setLong(2, classNameId);
			preparedStatement.setString(3, className);

			preparedStatement.executeUpdate();
		}
	}

	protected void addDDMStructureLink(
			long ddmStructureLinkId, long classNameId, long classPK,
			long ddmStructureId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into DDMStructureLink (structureLinkId, classNameId, " +
					"classPK, structureId) values (?, ?, ?, ?)")) {

			preparedStatement.setLong(1, ddmStructureLinkId);
			preparedStatement.setLong(2, classNameId);
			preparedStatement.setLong(3, classPK);
			preparedStatement.setLong(4, ddmStructureId);

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			_log.error(
				"Unable to add dynamic data mapping structure link for file " +
					"entry type " + classPK);

			throw exception;
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

		alterColumnType("DLFolder", "name", "VARCHAR(255) null");

		updateRepositoryClassNameIds();
	}

	protected boolean hasFileEntry(
			long groupId, long folderId, long fileEntryId, String title,
			String fileName)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from DLFileEntry where groupId = ? and " +
					"folderId = ? and ((fileEntryId <> ? and title = ?) or " +
						"fileName = ?)")) {

			preparedStatement.setLong(1, groupId);
			preparedStatement.setLong(2, folderId);
			preparedStatement.setLong(3, fileEntryId);
			preparedStatement.setString(4, title);
			preparedStatement.setString(5, fileName);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int count = resultSet.getInt(1);

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
			alterTableAddColumn("DLFileEntry", "fileName", "VARCHAR(255) null");

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
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from DLFileEntryTypes_DDMStructures");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			long classNameId = PortalUtil.getClassNameId(DLFileEntryType.class);

			while (resultSet.next()) {
				long structureId = resultSet.getLong("structureId");
				long fileEntryTypeId = resultSet.getLong("fileEntryTypeId");

				addDDMStructureLink(
					increment(), classNameId, fileEntryTypeId, structureId);
			}

			dropTable("DLFileEntryTypes_DDMStructures");
		}
	}

	protected void updateFileEntryTypeNamesAndDescriptions() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId, groupId from Group_ where classNameId = " +
					"?")) {

			preparedStatement.setLong(
				1, PortalUtil.getClassNameId(Company.class));

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long companyId = resultSet.getLong(1);
					long groupId = resultSet.getLong(2);

					updateFileEntryTypeNamesAndDescriptions(companyId, groupId);
				}
			}
		}
	}

	protected void updateFileEntryTypeNamesAndDescriptions(
			long companyId, long groupId)
		throws Exception {

		Map<String, String> nameLanguageKeys = HashMapBuilder.put(
			DLFileEntryTypeConstants.NAME_CONTRACT,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_CONTRACT
		).put(
			DLFileEntryTypeConstants.NAME_MARKETING_BANNER,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_MARKETING_BANNER
		).put(
			DLFileEntryTypeConstants.NAME_ONLINE_TRAINING,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_ONLINE_TRAINING
		).put(
			DLFileEntryTypeConstants.NAME_SALES_PRESENTATION,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_SALES_PRESENTATION
		).build();

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

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select fileEntryTypeId, name, description from " +
					"DLFileEntryType where groupId = ? and fileEntryTypeKey " +
						"= ?")) {

			preparedStatement.setLong(1, groupId);
			preparedStatement.setString(2, dlFileEntryTypeKey);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (!resultSet.next()) {
					return;
				}

				long fileEntryTypeId = resultSet.getLong(1);
				String name = resultSet.getString(2);
				String description = resultSet.getString(3);

				if (resultSet.next()) {
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

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update DLFileEntryType set name = ?, description = ? where " +
					"fileEntryTypeId = ?")) {

			String languageId = LanguageUtil.getLanguageId(defaultLocale);

			nameXML = LocalizationUtil.updateLocalization(
				nameMap, nameXML, "Name", languageId);
			descriptionXML = LocalizationUtil.updateLocalization(
				descriptionMap, descriptionXML, "Description", languageId);

			preparedStatement.setString(1, nameXML);
			preparedStatement.setString(2, descriptionXML);
			preparedStatement.setLong(3, fileEntryTypeId);

			int rowCount = preparedStatement.executeUpdate();

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
			alterTableAddColumn(
				"DLFileVersion", "fileName", "VARCHAR(255) null");

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

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"update Repository set classNameId = ? where " +
							"classNameId = ?")) {

				preparedStatement.setLong(1, portletRepositoryClassNameId);
				preparedStatement.setLong(2, liferayRepositoryClassNameId);

				preparedStatement.executeUpdate();
			}
		}
	}

	private void _fixDuplicateFileEntryFileNames() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select groupId, folderId, fileName from DLFileEntry group " +
					"by groupId, folderId, fileName having count(*) > 1");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long groupId = resultSet.getLong("groupId");
				long folderId = resultSet.getLong("folderId");
				String fileName = resultSet.getString("fileName");

				_fixDuplicateFileEntryFileNames(groupId, folderId, fileName);
			}
		}
	}

	private void _fixDuplicateFileEntryFileNames(
			long groupId, long folderId, String fileName)
		throws Exception {

		Set<String> generatedUniqueFileNames = new HashSet<>();
		Set<String> generatedUniqueTitles = new HashSet<>();

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select fileEntryId, extension, title, version from " +
					"DLFileEntry where groupId = ? and folderId = ? and " +
						"fileName = ?");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update DLFileEntry set fileName = ?, title = ? where " +
						"fileEntryId = ?");
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DLFileVersion set title = ? where fileEntryId = " +
						"? and version = ? and status != ?")) {

			preparedStatement1.setLong(1, groupId);
			preparedStatement1.setLong(2, folderId);
			preparedStatement1.setString(3, fileName);

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				resultSet.next();

				int i = 1;

				while (resultSet.next()) {
					long fileEntryId = resultSet.getLong("fileEntryId");
					String extension = GetterUtil.getString(
						resultSet.getString("extension"));
					String title = GetterUtil.getString(
						resultSet.getString("title"));
					String version = resultSet.getString("version");

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

					preparedStatement2.setString(1, uniqueFileName);
					preparedStatement2.setString(2, title);
					preparedStatement2.setLong(3, fileEntryId);

					preparedStatement2.addBatch();

					preparedStatement3.setString(1, title);
					preparedStatement3.setLong(2, fileEntryId);
					preparedStatement3.setString(3, version);
					preparedStatement3.setInt(
						4, WorkflowConstants.STATUS_IN_TRASH);

					preparedStatement3.addBatch();
				}

				preparedStatement2.executeBatch();

				preparedStatement3.executeBatch();
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
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select fileEntryId, title, extension from " + tableName +
					" where fileName = '' or fileName is null");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"update " + tableName +
						" set fileName = ? where fileEntryId = ?");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long fileEntryId = resultSet.getLong("fileEntryId");

				String extension = GetterUtil.getString(
					resultSet.getString("extension"));
				String title = GetterUtil.getString(
					resultSet.getString("title"));

				String fileName = DLUtil.getSanitizedFileName(title, extension);

				preparedStatement2.setString(1, fileName);

				preparedStatement2.setLong(2, fileEntryId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDocumentLibrary.class);

}