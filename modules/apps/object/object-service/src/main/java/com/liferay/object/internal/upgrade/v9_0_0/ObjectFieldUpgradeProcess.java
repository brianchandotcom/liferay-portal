/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v9_0_0;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Pedro Leite
 */
public class ObjectFieldUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(
					StringBundler.concat(
						"select ObjectField.dbColumnName, ",
						"ObjectField.dbTableName, ObjectField.localized, ",
						"ObjectDefinition.dbTableName as ",
						"objectDefinitionDBTableName from ObjectField inner ",
						"join ObjectDefinition on ",
						"ObjectDefinition.objectDefinitionId = ",
						"ObjectField.objectDefinitionId inner join ",
						"ObjectFieldSetting on ",
						"ObjectFieldSetting.objectFieldId = ",
						"ObjectField.objectFieldId where ",
						"(ObjectField.businessType = '",
						ObjectFieldConstants.BUSINESS_TYPE_AUTO_INCREMENT,
						"') or (ObjectFieldSetting.name = '",
						ObjectFieldSettingConstants.NAME_UNIQUE_VALUES,
						"' and ObjectFieldSetting.value = 'true')")));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				String dbColumnName = resultSet.getString("dbColumnName");
				String dbTableName = resultSet.getString("dbTableName");
				boolean localized = resultSet.getBoolean("localized");

				String[] columnNames = {dbColumnName};

				if (localized) {
					dbTableName =
						resultSet.getString("objectDefinitionDBTableName") +
							"_l";

					columnNames = new String[] {dbColumnName, "languageId"};
				}

				String indexName = IndexMetadataFactoryUtil.createIndexName(
					dbTableName, columnNames);

				if (hasIndex(dbTableName, indexName)) {
					runSQL(
						StringBundler.concat(
							"drop index ", indexName, " on ", dbTableName));
				}
			}
		}
	}

}