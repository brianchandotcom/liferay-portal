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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_8_0;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.util.DDMFormDeserializeUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marcos Martins
 */
public class UpgradeDDMContent extends UpgradeProcess {

	public UpgradeDDMContent(
		DDMFormDeserializer ddmFormDeserializer, JSONFactory jsonFactory) {

		_ddmFormDeserializer = ddmFormDeserializer;
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(13);

		sb.append("select DDMContent.contentId, DDMContent.data_, ");
		sb.append("DDMStructureVersion.structureVersionId, ");
		sb.append("DDMStructureVersion.definition from DDMContent inner join ");
		sb.append("DDMFormInstanceRecordVersion on DDMContent.contentId = ");
		sb.append("DDMFormInstanceRecordVersion.storageId inner join ");
		sb.append("DDMFormInstanceVersion on ");
		sb.append("DDMFormInstanceRecordVersion.formInstanceId = ");
		sb.append("DDMFormInstanceVersion.formInstanceId and ");
		sb.append("DDMFormInstanceRecordVersion.formInstanceVersion = ");
		sb.append("DDMFormInstanceVersion.version inner join ");
		sb.append("DDMStructureVersion on ");
		sb.append("DDMFormInstanceVersion.structureVersionId = ");
		sb.append("DDMStructureVersion.structureVersionId");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMContent set data_ = ? where contentId = ?")) {

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					DDMForm ddmForm = DDMFormDeserializeUtil.deserialize(
						_ddmFormDeserializer, rs.getString("definition"));

					List<DDMFormField> ddmFormFields =
						ddmForm.getDDMFormFields();

					Stream<DDMFormField> stream = ddmFormFields.stream();

					List<DDMFormField> fieldSetDDMFormFields = stream.filter(
						ddmFormField -> ddmFormField.getType(
						).equals(
							"fieldset"
						)
					).collect(
						Collectors.toList()
					);

					if (fieldSetDDMFormFields.isEmpty()) {
						continue;
					}

					String data = rs.getString("data_");

					String newData = _upgradeDDMContentData(
						data, fieldSetDDMFormFields);

					if (data.equals(newData)) {
						continue;
					}

					ps2.setString(1, newData);
					ps2.setLong(2, rs.getLong("contentId"));

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	private boolean _hasDDMFormField(
		DDMFormField ddmFormField, JSONArray fieldValuesJSONArray) {

		String ddmFormFieldName = ddmFormField.getName();

		Iterator<JSONObject> iterator = fieldValuesJSONArray.iterator();

		while (iterator.hasNext()) {
			JSONObject fieldValueJSONObject = iterator.next();

			if (ddmFormFieldName.equals(
					fieldValueJSONObject.getString("name"))) {

				return true;
			}
		}

		return false;
	}

	private String _upgradeDDMContentData(
			String data, List<DDMFormField> fieldSetDDMFormFields)
		throws Exception {

		JSONObject dataJSONObject = _jsonFactory.createJSONObject(data);

		JSONArray fieldValuesJSONArray = dataJSONObject.getJSONArray(
			"fieldValues");

		for (DDMFormField fieldSetDDMFormField : fieldSetDDMFormFields) {
			if (!_hasDDMFormField(fieldSetDDMFormField, fieldValuesJSONArray)) {
				Map<String, DDMFormField> nestedDDMFormFieldsMap =
					fieldSetDDMFormField.getNestedDDMFormFieldsMap();

				Set<String> nestedDDMFormFieldNames =
					nestedDDMFormFieldsMap.keySet();

				JSONArray nestedFieldValuesJSONArray =
					_jsonFactory.createJSONArray();

				JSONArray newFieldValuesJSONArray =
					_jsonFactory.createJSONArray();

				Iterator<JSONObject> iterator = fieldValuesJSONArray.iterator();

				while (iterator.hasNext()) {
					JSONObject fieldValueJSONObject = iterator.next();

					if (nestedDDMFormFieldNames.contains(
							fieldValueJSONObject.getString("name"))) {

						nestedFieldValuesJSONArray.put(fieldValueJSONObject);
					}
					else {
						newFieldValuesJSONArray.put(fieldValueJSONObject);
					}
				}

				newFieldValuesJSONArray.put(
					JSONUtil.put(
						"instanceId", StringUtil.randomString(8)
					).put(
						"name", fieldSetDDMFormField.getName()
					).put(
						"nestedFieldValues", nestedFieldValuesJSONArray
					));

				fieldValuesJSONArray = newFieldValuesJSONArray;
			}
		}

		dataJSONObject.put("fieldValues", fieldValuesJSONArray);

		return dataJSONObject.toJSONString();
	}

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final JSONFactory _jsonFactory;

}