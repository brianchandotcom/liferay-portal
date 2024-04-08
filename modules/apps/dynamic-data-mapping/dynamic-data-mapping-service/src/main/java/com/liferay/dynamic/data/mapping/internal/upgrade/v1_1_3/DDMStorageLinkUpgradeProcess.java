/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_3;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.SetUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Pedro Queiroz
 */
public class DDMStorageLinkUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDMStorageLink.storageLinkId, ",
					"DDMStorageLink.structureId, DDMStorageLink.classPK, ",
					"DDMContent.data_ from DDMStorageLink, DDMContent where ",
					"DDMStorageLink.classPK = DDMContent.contentId"));
			PreparedStatement preparedStatement2 = connection.prepareStatement(
				"select DDMStructureVersion.structureVersionId, " +
					"DDMStructureVersion.definition from DDMStructureVersion " +
						"where structureId = ?");
			PreparedStatement preparedStatement3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStorageLink set structureVersionId = ? where " +
						"storageLinkId = ?");
			ResultSet resultSet1 = preparedStatement1.executeQuery()) {

			Map<Long, Long> ddmStructureVersionIds = new HashMap<>();

			while (resultSet1.next()) {
				Set<String> ddmContentFieldNames = _getFieldNames(
					resultSet1.getString("data_"), "fieldValues");

				if (SetUtil.isEmpty(ddmContentFieldNames)) {
					continue;
				}

				preparedStatement2.setLong(
					1, resultSet1.getLong("structureId"));

				try (ResultSet resultSet2 = preparedStatement2.executeQuery()) {
					while (resultSet2.next()) {
						Set<String> ddmStructureFieldNames = _getFieldNames(
							resultSet2.getString("definition"), "fields");

						if (ddmStructureFieldNames.containsAll(
								ddmContentFieldNames)) {

							ddmStructureVersionIds.put(
								resultSet1.getLong("storageLinkId"),
								resultSet2.getLong("structureVersionId"));
						}
					}
				}
			}

			for (Map.Entry<Long, Long> entry :
					ddmStructureVersionIds.entrySet()) {

				preparedStatement3.setLong(1, entry.getValue());
				preparedStatement3.setLong(2, entry.getKey());

				preparedStatement3.addBatch();
			}

			preparedStatement3.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"DDMStorageLink", "structureVersionId LONG")
		};
	}

	private Set<String> _getFieldNames(String json, String key)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		JSONArray jsonArray = jsonObject.getJSONArray(key);

		if (jsonArray == null) {
			return Collections.emptySet();
		}

		Set<String> fieldNames = new HashSet<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			fieldNames.add(
				JSONUtil.getValueAsString(
					jsonArray, "JSONObject/" + i, "Object/name"));
		}

		return fieldNames;
	}

}