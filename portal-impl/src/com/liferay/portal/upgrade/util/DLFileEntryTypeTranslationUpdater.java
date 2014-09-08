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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.util.TranslationUpdater;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class DLFileEntryTypeTranslationUpdater implements TranslationUpdater {

	@Override
	public void update(long companyId, long groupId) throws PortalException {
		try {
			doUpdate(companyId, groupId);
		}
		catch (SQLException sqle) {
			throw new UpgradeException(sqle);
		}
	}

	protected void doUpdate(long companyId, long groupId) throws SQLException {
		for (Map.Entry<String, String> nameAndKey :
				_DEFAULT_FILE_ENTRY_TYPE_MAP.entrySet()) {

			updateGroupDLFileEntryTypes(
				companyId, groupId, nameAndKey.getKey(), nameAndKey.getValue());
		}
	}

	protected DLFileEntryTypeData getDlFileEntryTypeData(
			long groupId, String dlFileEntryTypeKey)
		throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DataAccess.getUpgradeOptimizedConnection();

			preparedStatement = connection.prepareStatement(
				"select fileEntryTypeId, name, description from " +
					"DLFileEntryType where groupId = ? and fileEntryTypeKey " +
					"= ?");

			preparedStatement.setLong(1, groupId);
			preparedStatement.setString(2, dlFileEntryTypeKey);

			resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				return null;
			}

			long fileEntryTypeId = resultSet.getLong(1);
			String name = resultSet.getString(2);
			String description = resultSet.getString(3);

			if (resultSet.next()) {
				throw new IllegalStateException(
					String.format(
						"Found at least two rows for groupId = %s and " +
							"fileEntryTypeKey = %s on table DLFileEntryType; " +
							"expected 1 row",
						groupId, dlFileEntryTypeKey));
			}

			DLFileEntryTypeData dlFileEntryTypeData = new DLFileEntryTypeData(
				fileEntryTypeId, name, description);

			return dlFileEntryTypeData;
		}
		finally {
			DataAccess.cleanUp(connection, preparedStatement, resultSet);
		}
	}

	protected void updateDLFileEntryType(
			DLFileEntryTypeData dlFileEntryTypeData, Map<Locale,
			String> nameMap, Map<Locale, String> descriptionMap,
			Locale defaultLocale)
		throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = DataAccess.getUpgradeOptimizedConnection();

			preparedStatement = connection.prepareStatement(
				"update DLFileEntryType set name = ?, description = ? where " +
					"fileEntryTypeId = ?");

			String languageId = LanguageUtil.getLanguageId(defaultLocale);

			String name = updateLocalizationXML(
				nameMap, dlFileEntryTypeData.getName(), "Name", languageId);

			preparedStatement.setString(1, name);

			String description = updateLocalizationXML(
				descriptionMap, dlFileEntryTypeData.getDescription(),
				"Description", languageId);

			preparedStatement.setString(2, description);

			preparedStatement.setLong(
				3, dlFileEntryTypeData.getDlFileEntryTypeId());

			int rowCount = preparedStatement.executeUpdate();

			if (rowCount != 1) {
				throw new IllegalStateException(
					String.format(
						"Updated %s rows with fileEntryTypeId = %s in table" +
							"DLFileEntryTypeId; expected 1 row", rowCount,
							dlFileEntryTypeData.getDlFileEntryTypeId()));
			}
		}
		finally {
			DataAccess.cleanUp(connection, preparedStatement);
		}
	}

	protected void updateGroupDLFileEntryTypes(
			long companyId, long groupId, String nameLanguageKey,
			String dlFileEntryTypeKey)
		throws SQLException {

		DLFileEntryTypeData dlFileEntryTypeData = getDlFileEntryTypeData(
			groupId, dlFileEntryTypeKey);

		if (dlFileEntryTypeData == null) {
			return;
		}

		Map<Locale, String> descriptionMap =
			dlFileEntryTypeData.getDescriptionMap();
		Map<Locale, String> nameMap = dlFileEntryTypeData.getNameMap();

		boolean needsUpdate = false;

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			UpgradeProcessUtil.getDefaultLanguageId(companyId));
		String defaultValue = LanguageUtil.get(defaultLocale, nameLanguageKey);

		for (Locale locale : LanguageUtil.getSupportedLocales()) {
			String description = descriptionMap.get(locale);
			String name = nameMap.get(locale);

			String localizedValue = LanguageUtil.get(locale, nameLanguageKey);

			if (!locale.equals(defaultLocale) &&
				localizedValue.equals(defaultValue)) {

				continue;
			}

			if (description == null) {
				descriptionMap.put(locale, localizedValue);

				needsUpdate = true;
			}

			if (name == null) {
				nameMap.put(locale, localizedValue);

				needsUpdate = true;
			}
		}

		if (needsUpdate) {
			updateDLFileEntryType(
				dlFileEntryTypeData, nameMap, descriptionMap, defaultLocale);
		}
	}

	protected String updateLocalizationXML(
		Map<Locale, String> localizationMap, String xml, String key,
		String languageId) {

		return LocalizationUtil.updateLocalization(
			localizationMap, xml, key, languageId);
	}

	protected class DLFileEntryTypeData {

		public DLFileEntryTypeData(
			long dlFileEntryTypeId, String name, String description) {

			_description = description;
			_dlFileEntryTypeId = dlFileEntryTypeId;
			_name = name;
		}

		public String getDescription() {
			return _description;
		}

		public Map<Locale, String> getDescriptionMap() {
			return LocalizationUtil.getLocalizationMap(_description);
		}

		public long getDlFileEntryTypeId() {
			return _dlFileEntryTypeId;
		}

		public String getName() {
			return _name;
		}

		public Map<Locale, String> getNameMap() {
			return LocalizationUtil.getLocalizationMap(_name);
		}

		private String _description;
		private long _dlFileEntryTypeId;
		private String _name;

	}

	private static Map<String, String> _DEFAULT_FILE_ENTRY_TYPE_MAP;

	static {
		_DEFAULT_FILE_ENTRY_TYPE_MAP = new HashMap<String, String>();

		_DEFAULT_FILE_ENTRY_TYPE_MAP.put(
			DLFileEntryTypeConstants.NAME_CONTRACT,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_CONTRACT);
		_DEFAULT_FILE_ENTRY_TYPE_MAP.put(
			DLFileEntryTypeConstants.NAME_MARKETING_BANNER,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_MARKETING_BANNER);
		_DEFAULT_FILE_ENTRY_TYPE_MAP.put(
			DLFileEntryTypeConstants.NAME_ONLINE_TRAINING,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_ONLINE_TRAINING);
		_DEFAULT_FILE_ENTRY_TYPE_MAP.put(
			DLFileEntryTypeConstants.NAME_SALES_PRESENTATION,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_SALES_PRESENTATION);
	}

}