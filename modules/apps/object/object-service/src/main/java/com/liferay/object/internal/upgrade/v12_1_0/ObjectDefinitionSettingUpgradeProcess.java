/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v12_1_0;

import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Guilherme Camacho
 */
public class ObjectDefinitionSettingUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select ObjectDefinition.objectDefinitionId, ",
					"ObjectDefinition.companyId, ObjectDefinition.userId, ",
					"ObjectDefinition.userName, ",
					"ObjectDefinition.externalReferenceCode, ",
					"ObjectDefinitionSetting.value as ",
					"rootObjectDefinitionIdsValue from ObjectDefinition inner ",
					"join ObjectDefinitionSetting on ",
					"ObjectDefinitionSetting.objectDefinitionId = ",
					"ObjectDefinition.objectDefinitionId where ",
					"ObjectDefinitionSetting.name = 'rootObjectDefinitionIds' ",
					"and not exists (select 1 from ObjectDefinitionSetting ",
					"ods where ods.objectDefinitionId = ",
					"ObjectDefinition.objectDefinitionId and ods.name = ",
					"'allowStandaloneObjectEntry')"));
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into ObjectDefinitionSetting (mvccVersion, ",
						"uuid_, objectDefinitionSettingId, companyId, userId, ",
						"userName, createDate, modifiedDate, ",
						"objectDefinitionId, name, value) values (?, ?, ?, ?, ",
						"?, ?, ?, ?, ?, ?, ?)"))) {

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					long objectDefinitionId = resultSet.getLong(
						"objectDefinitionId");

					if (ArrayUtil.contains(
							GetterUtil.getLongValues(
								StringUtil.split(
									resultSet.getString(
										"rootObjectDefinitionIdsValue"))),
							objectDefinitionId)) {

						continue;
					}

					preparedStatement2.setLong(1, 0);
					preparedStatement2.setString(2, PortalUUIDUtil.generate());
					preparedStatement2.setLong(3, increment());
					preparedStatement2.setLong(
						4, resultSet.getLong("companyId"));
					preparedStatement2.setLong(5, resultSet.getLong("userId"));
					preparedStatement2.setString(
						6, resultSet.getString("userName"));

					Timestamp timestamp = new Timestamp(
						System.currentTimeMillis());

					preparedStatement2.setTimestamp(7, timestamp);
					preparedStatement2.setTimestamp(8, timestamp);

					preparedStatement2.setLong(9, objectDefinitionId);
					preparedStatement2.setString(
						10,
						ObjectDefinitionSettingConstants.
							NAME_ALLOW_STANDALONE_OBJECT_ENTRY);

					if (ArrayUtil.contains(
							_DATA_SET_OBJECT_DEFINITION_ERCS,
							resultSet.getString("externalReferenceCode"))) {

						preparedStatement2.setString(11, "false");
					}
					else {
						preparedStatement2.setString(11, "true");
					}

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	private static final String[] _DATA_SET_OBJECT_DEFINITION_ERCS = {
		"L_DATA_SET_ACTION", "L_DATA_SET_CARDS_SECTION",
		"L_DATA_SET_CLIENT_EXTENSION_FILTER", "L_DATA_SET_DATE_FILTER",
		"L_DATA_SET_LIST_SECTION", "L_DATA_SET_SELECTION_FILTER",
		"L_DATA_SET_SORT", "L_DATA_SET_TABLE_SECTION"
	};

}