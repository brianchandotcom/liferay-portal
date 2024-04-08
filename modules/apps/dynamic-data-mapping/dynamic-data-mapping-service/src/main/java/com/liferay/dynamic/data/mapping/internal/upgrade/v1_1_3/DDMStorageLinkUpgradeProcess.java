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
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
		try (PreparedStatement selectDDMStorageLinkPreparedStatement =
				connection.prepareStatement(
					StringBundler.concat(
						"select DDMStorageLink.storageLinkId, ",
						"DDMStorageLink.structureId, DDMStorageLink.classPK, ",
						"DDMContent.data_ from DDMStorageLink , DDMContent ",
						"where DDMStorageLink.classPK = DDMContent.contentid"));
			PreparedStatement selectDDMStructureVersionPreparedStatement =
				connection.prepareStatement(
					"select DDMStructureVersion.structureVersionId, " +
						"DDMStructureVersion.definition from " +
							"DDMStructureVersion where structureId = ?");
			PreparedStatement updatePreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStorageLink set structureVersionId = ? where " +
						"storageLinkId = ?");
			ResultSet resultSet =
				selectDDMStorageLinkPreparedStatement.executeQuery()) {

			Map<Long, Long> ddmStorageLinkStructureVersionMap = new HashMap<>();

			while (resultSet.next()) {
				String data_ = resultSet.getString("data_");

				if ((data_ == null) || data_.isEmpty()) {
					continue;
				}

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(data_);

				JSONArray jsonArray = jsonObject.getJSONArray("fieldValues");

				if (jsonArray == null) {
					continue;
				}

				Set<String> ddmContentFieldNames = new HashSet<>(
					jsonArray.length());

				for (int i = 0; i < jsonArray.length(); i++) {
					String fieldName = jsonArray.getJSONObject(
						i
					).getString(
						"name"
					);

					ddmContentFieldNames.add(fieldName);
				}

				long structureId = resultSet.getLong("structureId");

				selectDDMStructureVersionPreparedStatement.setLong(
					1, structureId);

				ResultSet resultSet2 =
					selectDDMStructureVersionPreparedStatement.executeQuery();

				long storageLinkId = resultSet.getLong("storageLinkId");

				while (resultSet2.next()) {
					Set<String> ddmFormFieldNames = new HashSet<>();

					JSONObject definitionJSONObject =
						JSONFactoryUtil.createJSONObject(
							resultSet2.getString("definition"));

					JSONArray fieldsJSONArray =
						definitionJSONObject.getJSONArray("fields");

					for (int i = 0; i < fieldsJSONArray.length(); i++) {
						JSONObject fieldJSONObject =
							fieldsJSONArray.getJSONObject(i);

						ddmFormFieldNames.add(
							fieldJSONObject.getString("name"));
					}

					if (ddmFormFieldNames.containsAll(ddmContentFieldNames)) {
						ddmStorageLinkStructureVersionMap.put(
							storageLinkId,
							resultSet2.getLong("structureVersionId"));
					}
				}
			}

			for (Map.Entry<Long, Long> entry :
					ddmStorageLinkStructureVersionMap.entrySet()) {

				updatePreparedStatement.setLong(1, entry.getValue());
				updatePreparedStatement.setLong(2, entry.getKey());

				updatePreparedStatement.addBatch();
			}

			updatePreparedStatement.executeBatch();
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"DDMStorageLink", "structureVersionId LONG")
		};
	}

}