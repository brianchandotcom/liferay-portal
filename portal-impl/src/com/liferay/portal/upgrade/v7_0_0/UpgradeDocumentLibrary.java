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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.portletrepository.PortletRepository;
import com.liferay.portal.upgrade.v7_0_0.util.DLFolderTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Michael Young
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	protected void addDDMStructureLink(
			Connection con, long ddmStructureLinkId, long classNameId,
			long classPK, long ddmStructureId)
		throws Exception {

		String sql =
			"insert into DDMStructureLink (structureLinkId, classNameId, " +
				"classPK, structureId) values (?, ?, ?, ?)";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setLong(1, ddmStructureLinkId);
			ps.setLong(2, classNameId);
			ps.setLong(3, classPK);
			ps.setLong(4, ddmStructureId);

			ps.executeUpdate();
		}
		catch (Exception e) {
			_log.error(
				"Unable to add dynamic data mapping structure link " +
					"for file entry type " + classPK);

			throw e;
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (Connection con = DataAccess.getUpgradeOptimizedConnection()) {

			// DLFileEntry

			updateFileEntryFileNames(con);

			// DLFileEntryType

			updateFileEntryTypeNamesAndDescriptions(con);

			updateFileEntryTypeDDMStructureLinks(con);

			// DLFileVersion

			updateFileVersionFileNames(con);

			// DLFolder

			try {
				runSQL(
					con, "alter_column_type DLFolder name VARCHAR(255) null");
			}
			catch (SQLException sqle) {
				upgradeTable(
					DLFolderTable.TABLE_NAME, DLFolderTable.TABLE_COLUMNS,
					DLFolderTable.TABLE_SQL_CREATE,
					DLFolderTable.TABLE_SQL_ADD_INDEXES);
			}

			updateRepositoryClassNameIds(con);
		}
	}

	protected boolean hasFileEntry(
			Connection con, long groupId, long folderId, String fileName)
		throws Exception {

		String sql =
			"select count(*) from DLFileEntry where groupId = ? and " +
				"folderId = ? and fileName = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setLong(1, groupId);
			ps.setLong(2, folderId);
			ps.setString(3, fileName);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int count = rs.getInt(1);

					if (count > 0) {
						return true;
					}
				}
			}

			return false;
		}
	}

	protected void updateFileEntryFileName(
			Connection con, long fileEntryId, String fileName)
		throws Exception {

		String sql =
			"update DLFileEntry set fileName = ? where fileEntryId = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, fileName);
			ps.setLong(2, fileEntryId);

			ps.executeUpdate();
		}
	}

	protected void updateFileEntryFileNames(Connection con) throws Exception {
		runSQL(con, "alter table DLFileEntry add fileName VARCHAR(255) null");

		String sql =
			"select fileEntryId, groupId, folderId, extension, title, " +
				"version from DLFileEntry";

		try (PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

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

				String uniqueTitle = StringPool.BLANK;

				for (int i = 1;; i++) {
					if (!hasFileEntry(con, groupId, folderId, uniqueFileName)) {
						break;
					}

					uniqueTitle =
						titleWithoutExtension + StringPool.UNDERLINE +
							String.valueOf(i);

					if (Validator.isNotNull(titleExtension)) {
						uniqueTitle += StringPool.PERIOD.concat(titleExtension);
					}

					uniqueFileName = DLUtil.getSanitizedFileName(
						uniqueTitle, extension);
				}

				updateFileEntryFileName(con, fileEntryId, uniqueFileName);

				if (Validator.isNotNull(uniqueTitle)) {
					updateFileEntryTitle(
						con, fileEntryId, uniqueTitle, version);
				}
			}
		}
	}

	protected void updateFileEntryTitle(
			Connection con, long fileEntryId, String title, String version)
		throws Exception {

		String sql = "update DLFileEntry set title = ? where fileEntryId = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, title);
			ps.setLong(2, fileEntryId);

			ps.executeUpdate();
		}

		sql =
			"update DLFileVersion set title = ? where fileEntryId = ? and " +
				"version = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, title);
			ps.setLong(2, fileEntryId);
			ps.setString(3, version);

			ps.executeUpdate();
		}
	}

	protected void updateFileEntryTypeDDMStructureLinks(Connection con)
		throws Exception {

		String sql = "select * from DLFileEntryTypes_DDMStructures";

		try (PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

			long classNameId = PortalUtil.getClassNameId(DLFileEntryType.class);

			while (rs.next()) {
				long structureId = rs.getLong("structureId");
				long fileEntryTypeId = rs.getLong("fileEntryTypeId");

				addDDMStructureLink(
					con, increment(), classNameId, fileEntryTypeId,
					structureId);
			}
		}

		runSQL(con, "drop table DLFileEntryTypes_DDMStructures");
	}

	protected void updateFileEntryTypeNamesAndDescriptions(Connection con)
		throws Exception {

		String sql =
			"select companyId, groupId from Group_ where classNameId = ?";

		long classNameId = PortalUtil.getClassNameId(Company.class);

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setLong(1, classNameId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long companyId = rs.getLong(1);
					long groupId = rs.getLong(2);

					updateFileEntryTypeNamesAndDescriptions(
						con, companyId, groupId);
				}
			}
		}
	}

	protected void updateFileEntryTypeNamesAndDescriptions(
			Connection con, long companyId, long groupId)
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
				con, companyId, groupId, dlFileEntryTypeKey, nameLanguageKey);
		}
	}

	protected void updateFileEntryTypeNamesAndDescriptions(
			Connection con, long companyId, long groupId,
			String dlFileEntryTypeKey, String nameLanguageKey)
		throws Exception {

		String sql =
			"select fileEntryTypeId, name, description from " +
				"DLFileEntryType where groupId = ? and fileEntryTypeKey = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
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
					con, companyId, fileEntryTypeId, nameLanguageKey, name,
					description);
			}
		}
	}

	protected void updateFileEntryTypeNamesAndDescriptions(
			Connection con, long companyId, long dlFileEntryTypeId,
			String nameLanguageKey, String nameXML, String descriptionXML)
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
				con, dlFileEntryTypeId, nameXML, descriptionXML, nameMap,
				descriptionMap, defaultLocale);
		}
	}

	protected void updateFileEntryTypeNamesAndDescriptions(
			Connection con, long fileEntryTypeId, String nameXML,
			String descriptionXML, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, Locale defaultLocale)
		throws Exception {

		String sql =
			"update DLFileEntryType set name = ?, description = ? where " +
				"fileEntryTypeId = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
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
			Connection con, long fileVersionId, String fileName)
		throws Exception {

		String sql =
			"update DLFileVersion set fileName = ? where fileVersionId = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, fileName);
			ps.setLong(2, fileVersionId);

			ps.executeUpdate();
		}
	}

	protected void updateFileVersionFileNames(Connection con) throws Exception {
		runSQL(con, "alter table DLFileVersion add fileName VARCHAR(255) null");

		String sql =
			"select fileVersionId, extension, title from DLFileVersion";

		try (PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long fileVersionId = rs.getLong("fileVersionId");
				String extension = GetterUtil.getString(
					rs.getString("extension"));
				String title = GetterUtil.getString(rs.getString("title"));

				String fileName = DLUtil.getSanitizedFileName(title, extension);

				updateFileVersionFileName(con, fileVersionId, fileName);
			}
		}
	}

	protected void updateRepositoryClassNameIds(Connection con)
		throws Exception {

		long liferayRepositoryClassNameId = PortalUtil.getClassNameId(
			LiferayRepository.class);
		long portletRepositoryClassNameId = PortalUtil.getClassNameId(
			PortletRepository.class);

		String sql =
			"update Repository set classNameId = ? where classNameId = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setLong(1, portletRepositoryClassNameId);
			ps.setLong(2, liferayRepositoryClassNameId);

			ps.executeUpdate();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDocumentLibrary.class);

}