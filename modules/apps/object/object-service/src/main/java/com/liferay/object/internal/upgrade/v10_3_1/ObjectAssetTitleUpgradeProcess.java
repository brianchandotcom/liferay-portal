/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v10_3_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yuri Monteiro
 */
public class ObjectAssetTitleUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select ObjectDefinition.companyId, ",
					"ObjectDefinition.objectDefinitionId, ",
					"ObjectDefinition.name, ObjectDefinition.className, ",
					"ObjectDefinition.dbTableName, ObjectField.dbColumnName, ",
					"ObjectDefinition.pkObjectFieldDBColumnName from ",
					"ObjectDefinition left join ObjectField on ",
					"ObjectField.objectFieldId = ",
					"ObjectDefinition.titleObjectFieldId where ",
					"ObjectDefinition.enableLocalization = true and ",
					"ObjectField.localized = true"));
			ResultSet resultSet1 = preparedStatement1.executeQuery()) {

			Map<Long, String> classNameMap = new HashMap<>();
			Map<Long, Map<String, String>> titleMap = new HashMap<>();
			Map<Long, String> xmlMap = new HashMap<>();

			while (resultSet1.next()) {
				String query = StringBundler.concat(
					"select ", resultSet1.getString("dbColumnName"),
					", languageId, ",
					resultSet1.getString("pkObjectFieldDBColumnName"), " from ",
					resultSet1.getString("dbTableName"), "_l");

				try (PreparedStatement preparedStatement2 =
						connection.prepareStatement(query);
					ResultSet resultSet2 = preparedStatement2.executeQuery()) {

					while (resultSet2.next()) {
						Long objectId = resultSet2.getLong(
							resultSet1.getString("pkObjectFieldDBColumnName"));

						if (!titleMap.containsKey(objectId)) {
							titleMap.put(objectId, new HashMap<>());
						}

						titleMap.get(
							objectId
						).put(
							resultSet2.getString("languageId"),
							resultSet2.getString(
								resultSet1.getString("dbColumnName"))
						);

						xmlMap.put(
							objectId,
							LocalizationUtil.getXml(
								titleMap.get(objectId),
								UpgradeProcessUtil.getDefaultLanguageId(
									resultSet1.getLong("companyId")),
								"title"));
						classNameMap.put(
							objectId, resultSet1.getString("className"));
					}
				}
			}

			try (PreparedStatement preparedStatement3 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"update AssetEntry set title = ?, mimeType = ? where " +
							"classPK = ? and classNameId = ?")) {

				for (Map.Entry<Long, String> entry : xmlMap.entrySet()) {
					Long objectId = entry.getKey();

					preparedStatement3.setString(1, entry.getValue());
					preparedStatement3.setString(2, "text/html");
					preparedStatement3.setLong(3, objectId);
					preparedStatement3.setLong(
						4,
						PortalUtil.getClassNameId(classNameMap.get(objectId)));

					preparedStatement3.addBatch();
				}

				preparedStatement3.executeBatch();
			}
		}
	}

}