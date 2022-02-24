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

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.util.PropsValues;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeGroup extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		dropIndexes("Group_", "name");

		alterColumnType("Group_", "name", "STRING null");

		try (SafeCloseable safeCloseable = addTempIndex(
				"Group_", false, "classNameId", "classPK")) {

			updateGlobalGroupName();
			updateGroupsNames();
		}
	}

	protected void updateGlobalGroupName() throws Exception {
		List<Long> companyIds = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select companyId from Company")) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long companyId = resultSet.getLong("companyId");

					companyIds.add(companyId);
				}
			}
		}

		for (Long companyId : companyIds) {
			LocalizedValuesMap localizedValuesMap = new LocalizedValuesMap();

			for (String languageId : PropsValues.LOCALES_ENABLED) {
				Locale locale = LocaleUtil.fromLanguageId(languageId);

				localizedValuesMap.put(
					locale,
					LanguageUtil.get(
						LanguageResources.getResourceBundle(locale), "global"));
			}

			String nameXML = LocalizationUtil.getXml(
				localizedValuesMap, "global");

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"update Group_ set name = ? where companyId = ? and " +
							"friendlyURL = '/global'")) {

				preparedStatement.setString(1, nameXML);
				preparedStatement.setLong(2, companyId);

				preparedStatement.executeUpdate();
			}
		}
	}

	protected void updateGroupsNames() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select groupId, name, typeSettings from Group_ where site = " +
					"1 and friendlyURL != '/global'");
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update Group_ set name = ? where groupId = ?"))) {

			while (resultSet.next()) {
				long groupId = resultSet.getLong("groupId");
				String name = resultSet.getString("name");

				String typeSettings = resultSet.getString("typeSettings");

				UnicodeProperties typeSettingsUnicodeProperties =
					UnicodePropertiesBuilder.create(
						true
					).fastLoad(
						typeSettings
					).build();

				String defaultLanguageId =
					typeSettingsUnicodeProperties.getProperty("languageId");

				Locale currentDefaultLocale =
					LocaleThreadLocal.getSiteDefaultLocale();

				try {
					LocaleThreadLocal.setSiteDefaultLocale(
						LocaleUtil.fromLanguageId(defaultLanguageId));

					LocalizedValuesMap localizedValuesMap =
						new LocalizedValuesMap();

					for (String languageId :
							StringUtil.split(
								typeSettingsUnicodeProperties.getProperty(
									"locales"))) {

						Locale locale = LocaleUtil.fromLanguageId(languageId);

						localizedValuesMap.put(locale, name);
					}

					String nameXML = LocalizationUtil.updateLocalization(
						localizedValuesMap.getValues(), StringPool.BLANK,
						"name", defaultLanguageId);

					preparedStatement2.setString(1, nameXML);

					preparedStatement2.setLong(2, groupId);

					preparedStatement2.addBatch();
				}
				finally {
					LocaleThreadLocal.setSiteDefaultLocale(
						currentDefaultLocale);
				}
			}

			preparedStatement2.executeBatch();
		}
	}

}