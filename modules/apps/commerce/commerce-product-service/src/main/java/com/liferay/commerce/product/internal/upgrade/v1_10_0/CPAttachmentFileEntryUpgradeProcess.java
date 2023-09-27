/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.upgrade.v1_10_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Marco Leo
 */
public class CPAttachmentFileEntryUpgradeProcess extends UpgradeProcess {

	public CPAttachmentFileEntryUpgradeProcess(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		String selectCPAttachmentFileEntrySQL =
			"select CPAttachmentFileEntryId, json from CPAttachmentFileEntry " +
				"where json <> ''";
		String updateCPAttachmentFileEntrySQL =
			"update CPAttachmentFileEntry set json = ? where " +
				"CPAttachmentFileEntryId = ?";

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateCPAttachmentFileEntrySQL);
			Statement s1 = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			Statement s2 = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			Statement s3 = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet1 = s1.executeQuery(
				selectCPAttachmentFileEntrySQL)) {

			while (resultSet1.next()) {
				JSONArray outputJSONArray = _jsonFactory.createJSONArray();

				JSONArray inputJSONArray = _jsonFactory.createJSONArray(
					resultSet1.getString("json"));

				for (int i = 0; i < inputJSONArray.length(); i++) {
					JSONObject inputJSONObject = inputJSONArray.getJSONObject(
						i);

					ResultSet resultSet2 = s2.executeQuery(
						"select key_ from CPDefinitionOptionRel where " +
							"CPDefinitionOptionRelId = " +
								inputJSONObject.getLong("key"));

					if (!resultSet2.next()) {
						continue;
					}

					JSONArray valueOutputJSONArray =
						_jsonFactory.createJSONArray();

					JSONArray valueInputJSONArray =
						inputJSONObject.getJSONArray("value");

					for (int j = 0; j < valueInputJSONArray.length(); j++) {
						ResultSet resultSet3 = s3.executeQuery(
							"select key_ from CPDefinitionOptionValueRel " +
								"where CPDefinitionOptionValueRelId = " +
									valueInputJSONArray.getLong(j));

						if (!resultSet3.next()) {
							continue;
						}

						valueOutputJSONArray.put(resultSet3.getString("key_"));
					}

					JSONObject outputJSONObject =
						_jsonFactory.createJSONObject();

					outputJSONObject.put(
						"key", resultSet2.getString("key_")
					).put(
						"value", valueOutputJSONArray
					);

					outputJSONArray.put(outputJSONObject);
				}

				preparedStatement.setString(1, outputJSONArray.toString());
				preparedStatement.setLong(
					2, resultSet1.getLong("CPAttachmentFileEntryId"));

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
	}

	private final JSONFactory _jsonFactory;

}