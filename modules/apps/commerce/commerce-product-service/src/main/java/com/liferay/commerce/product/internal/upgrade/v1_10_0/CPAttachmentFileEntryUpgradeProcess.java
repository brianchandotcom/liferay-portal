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

		try (PreparedStatement ps =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, updateCPAttachmentFileEntrySQL);
			Statement s1 = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			Statement s2 = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			Statement s3 = connection.createStatement(
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs1 = s1.executeQuery(selectCPAttachmentFileEntrySQL)) {

			while (rs1.next()) {
				JSONArray outputJSONArray = _jsonFactory.createJSONArray();

				JSONArray inputJSONArray = _jsonFactory.createJSONArray(
					rs1.getString("json"));

				for (int i = 0; i < inputJSONArray.length(); i++) {
					JSONObject inputJSONObject = inputJSONArray.getJSONObject(
						i);

					ResultSet rs2 = s2.executeQuery(
						"select key_ from CPDefinitionOptionRel where " +
							"CPDefinitionOptionRelId = " +
								inputJSONObject.getLong("key"));

					if (!rs2.next()) {
						continue;
					}

					JSONArray valueOutputJSONArray =
						_jsonFactory.createJSONArray();

					JSONArray valueInputJSONArray =
						inputJSONObject.getJSONArray("value");

					for (int j = 0; j < valueInputJSONArray.length(); j++) {
						ResultSet rs3 = s3.executeQuery(
							"select key_ from CPDefinitionOptionValueRel " +
								"where CPDefinitionOptionValueRelId = " +
									valueInputJSONArray.getLong(j));

						if (!rs3.next()) {
							continue;
						}

						valueOutputJSONArray.put(rs3.getString("key_"));
					}

					JSONObject outputJSONObject =
						_jsonFactory.createJSONObject();

					outputJSONObject.put(
						"key", rs2.getString("key_")
					).put(
						"value", valueOutputJSONArray
					);

					outputJSONArray.put(outputJSONObject);
				}

				ps.setString(1, outputJSONArray.toString());
				ps.setLong(2, rs1.getLong("CPAttachmentFileEntryId"));

				ps.addBatch();
			}

			ps.executeBatch();
		}
	}

	private final JSONFactory _jsonFactory;

}