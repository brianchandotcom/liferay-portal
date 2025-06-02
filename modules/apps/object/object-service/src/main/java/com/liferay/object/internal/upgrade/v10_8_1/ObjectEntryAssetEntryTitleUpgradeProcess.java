/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v10_8_1;

import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Yuri Monteiro
 */
public class ObjectEntryAssetEntryTitleUpgradeProcess extends UpgradeProcess {

	public ObjectEntryAssetEntryTitleUpgradeProcess(
		ClassNameLocalService classNameLocalService, Localization localization,
		SystemObjectDefinitionManagerRegistry
			systemObjectDefinitionManagerRegistry) {

		_classNameLocalService = classNameLocalService;
		_localization = localization;
		_systemObjectDefinitionManagerRegistry =
			systemObjectDefinitionManagerRegistry;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Map<Long, ObjectEntryInfo> objectEntryInfos = new HashMap<>();

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"select ObjectDefinition.className, ",
						"ObjectDefinition.dbTableName, ",
						"ObjectDefinition.modifiable, ObjectDefinition.name, ",
						"ObjectDefinition.pkObjectFieldDBColumnName, ",
						"ObjectDefinition.system_, ObjectField.dbColumnName ",
						"from ObjectDefinition left join ObjectField on ",
						"ObjectDefinition.titleObjectFieldId = ",
						"ObjectField.objectFieldId where ",
						"ObjectDefinition.enableLocalization = [$TRUE$] and ",
						"ObjectField.localized = [$TRUE$]")));
			ResultSet resultSet1 = preparedStatement1.executeQuery()) {

			while (resultSet1.next()) {
				String dbColumnName = resultSet1.getString("dbColumnName");

				String dbTableName = null;

				if (!resultSet1.getBoolean("modifiable") &&
					resultSet1.getBoolean("system_")) {

					SystemObjectDefinitionManager
						systemObjectDefinitionManager =
							_systemObjectDefinitionManagerRegistry.
								getSystemObjectDefinitionManager(
									resultSet1.getString("name"));

					Table localizationTable =
						systemObjectDefinitionManager.getLocalizationTable();

					dbTableName = localizationTable.getTableName();
				}
				else {
					dbTableName = resultSet1.getString("dbTableName") + "_l";
				}

				try (PreparedStatement preparedStatement2 =
						connection.prepareStatement(
							SQLTransformer.transform(
								StringBundler.concat(
									"select ObjectEntry.objectEntryId, ",
									"ObjectEntry.defaultLanguageId, ",
									dbTableName, ".languageId, ", dbTableName,
									".", dbColumnName,
									" from ObjectEntry inner join ",
									dbTableName,
									" on ObjectEntry.objectEntryId = ",
									dbTableName, ".",
									resultSet1.getString(
										"pkObjectFieldDBColumnName"))));
					ResultSet resultSet2 = preparedStatement2.executeQuery()) {

					while (resultSet2.next()) {
						long objectEntryId = resultSet2.getLong(
							"objectEntryId");

						ObjectEntryInfo objectEntryInfo = objectEntryInfos.get(
							objectEntryId);

						if (objectEntryInfo == null) {
							objectEntryInfo = new ObjectEntryInfo(
								resultSet1.getString("className"),
								resultSet2.getString("defaultLanguageId"));

							objectEntryInfos.put(
								objectEntryId, objectEntryInfo);
						}

						objectEntryInfo.putTitle(
							LocaleUtil.fromLanguageId(
								resultSet2.getString("languageId")),
							resultSet2.getString(dbColumnName));
					}
				}
			}
		}

		try (PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"update AssetEntry set mimeType = '",
						ContentTypes.TEXT_HTML,
						"', title = ? where classNameId = ? and classPK = ",
						"?"))) {

			for (Map.Entry<Long, ObjectEntryInfo> entry :
					objectEntryInfos.entrySet()) {

				ObjectEntryInfo objectEntryInfo = entry.getValue();

				preparedStatement3.setString(
					1,
					_localization.getXml(
						LocalizedMapUtil.getLanguageIdMap(
							objectEntryInfo._titleMap),
						objectEntryInfo._defaultLanguageId, "title"));
				preparedStatement3.setLong(
					2,
					_classNameLocalService.getClassNameId(
						objectEntryInfo._className));

				preparedStatement3.setLong(3, entry.getKey());

				preparedStatement3.addBatch();
			}

			preparedStatement3.executeBatch();
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final Localization _localization;
	private final SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

	private static class ObjectEntryInfo {

		public ObjectEntryInfo(String className, String defaultLanguageId) {
			_className = className;
			_defaultLanguageId = defaultLanguageId;
		}

		public void putTitle(Locale locale, String title) {
			_titleMap.put(locale, title);
		}

		private final String _className;
		private final String _defaultLanguageId;
		private Map<Locale, String> _titleMap = new HashMap<>();

	}

}