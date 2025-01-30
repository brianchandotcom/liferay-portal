/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v10_3_1;

import com.liferay.petra.string.StringBundler;
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
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		ResultSet resultSet1 = null;
		ResultSet resultSet2 = null;

		try {
			Map<Long, Map<String, String>> titleMap = new HashMap<>();
			Map<Long, String> xmlMap = new HashMap<>();
			Map<Long, String> classNameMap = new HashMap<>();

			preparedStatement1 = connection.prepareStatement(
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

			resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				Long companyId = resultSet1.getLong("companyId");

				String defaultLanguageId =
					UpgradeProcessUtil.getDefaultLanguageId(companyId);

				String query = StringBundler.concat(
					"select ", resultSet1.getString("dbColumnName"),
					", languageId, ",
					resultSet1.getString("pkObjectFieldDBColumnName"), " from ",
					resultSet1.getString("dbTableName"), "_l");

				preparedStatement2 = connection.prepareStatement(query);

				resultSet2 = preparedStatement2.executeQuery();

				while (resultSet2.next()) {
					String titleField = resultSet2.getString(
						resultSet1.getString("dbColumnName"));
					String languageId = resultSet2.getString("languageId");

					Long objectId = resultSet2.getLong(
						resultSet1.getString("pkObjectFieldDBColumnName"));

					if (!titleMap.containsKey(objectId)) {
						titleMap.put(objectId, new HashMap<>());
					}

					titleMap.get(
						objectId
					).put(
						languageId, titleField
					);

					xmlMap.put(
						objectId,
						LocalizationUtil.getXml(
							titleMap.get(objectId), defaultLanguageId,
							"title"));
					classNameMap.put(
						objectId, resultSet1.getString("className"));
				}
			}

			preparedStatement3 = connection.prepareStatement(
				"update AssetEntry set title = ?, mimeType = ? where classPK " +
					"= ? and classNameId = ?");

			for (Map.Entry<Long, String> entry : xmlMap.entrySet()) {
				Long objectId = entry.getKey();
				String localizedTitleXml = entry.getValue();
				String mimeTypeValue = "text/html";

				String className = classNameMap.get(objectId);

				Long classNameId = PortalUtil.getClassNameId(className);

				preparedStatement3.setString(1, localizedTitleXml);
				preparedStatement3.setString(2, mimeTypeValue);
				preparedStatement3.setLong(3, objectId);
				preparedStatement3.setLong(4, classNameId);

				int rowsUpdated = preparedStatement3.executeUpdate();

				if (rowsUpdated > 0) {
					String successMessage =
						"Successfully updated AssetEntry for classPK=" +
							objectId;

					System.out.println(successMessage);
				}
				else {
					String errorMessage = StringBundler.concat(
						"No record found for classPK=", objectId,
						" and classNameId=", classNameId);

					System.out.println(errorMessage);
				}
			}
		}
		finally {
			try {
				if (resultSet2 != null) {
					resultSet2.close();
				}

				if (preparedStatement2 != null) {
					preparedStatement2.close();
				}

				if (resultSet1 != null) {
					resultSet1.close();
				}

				if (preparedStatement1 != null) {
					preparedStatement1.close();
				}

				if (preparedStatement3 != null) {
					preparedStatement3.close();
				}

				if (connection != null) {
					connection.close();
				}
			}
			catch (Exception exception) {
				String exceptionMessage = exception.getMessage();

				System.out.println(exceptionMessage);
			}
		}
	}

}