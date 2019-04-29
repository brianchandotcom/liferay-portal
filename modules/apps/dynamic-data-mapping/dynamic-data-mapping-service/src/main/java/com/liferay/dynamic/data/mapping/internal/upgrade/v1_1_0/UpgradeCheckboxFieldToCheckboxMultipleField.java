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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_1_0;

import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldValueTransformer;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesTransformer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Rafael Praxedes
 */
public class UpgradeCheckboxFieldToCheckboxMultipleField
	extends UpgradeProcess {

	public UpgradeCheckboxFieldToCheckboxMultipleField(
		DDMFormDeserializer ddmFormDeserializer,
		DDMFormValuesDeserializer ddmFormValuesDeserializer,
		DDMFormValuesSerializer ddmFormValuesSerializer,
		JSONFactory jsonFactory) {

		_ddmFormDeserializer = ddmFormDeserializer;
		_ddmFormValuesDeserializer = ddmFormValuesDeserializer;
		_ddmFormValuesSerializer = ddmFormValuesSerializer;
		_jsonFactory = jsonFactory;
	}

	protected DDMForm deserialize(String content) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	protected DDMFormValues deserialize(String content, DDMForm ddmForm) {
		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				_ddmFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("select DDMStructure.definition, DDMStructure.version, ");
		sb.append("DDMStructure.structureId, DDLRecordSet.recordSetId from ");
		sb.append("DDLRecordSet inner join DDMStructure on ");
		sb.append("DDLRecordSet.DDMStructureId = DDMStructure.structureId ");
		sb.append("where DDLRecordSet.scope = ? and DDMStructure.definition ");
		sb.append("like ?");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructure set definition = ? where " +
						"structureId = ?");
			PreparedStatement ps3 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMStructureVersion set definition = ? where " +
						"structureId = ? and version = ?")) {

			ps1.setInt(1, _SCOPE_FORMS);
			ps1.setString(2, "%checkbox%");

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String definition = rs.getString(1);
					String version = rs.getString(2);
					long structureId = rs.getLong(3);
					long recordSetId = rs.getLong(4);

					String newDefinition = upgradeRecordSetStructureDefinition(
						definition);

					ps2.setString(1, newDefinition);

					ps2.setLong(2, structureId);

					ps2.addBatch();

					ps3.setString(1, newDefinition);
					ps3.setLong(2, structureId);
					ps3.setString(3, version);

					ps3.addBatch();

					updateRecords(deserialize(definition), recordSetId);
				}

				ps2.executeBatch();

				ps3.executeBatch();
			}
		}
	}

	protected JSONArray getOptionsJSONArray(
		JSONObject checkboxFieldJSONObject) {

		JSONArray optionsJSONArray = _jsonFactory.createJSONArray();

		JSONObject optionJSONObject = _jsonFactory.createJSONObject();

		optionJSONObject.put(
			"label", checkboxFieldJSONObject.getJSONObject("label")
		).put(
			"value", checkboxFieldJSONObject.getString("name")
		);

		optionsJSONArray.put(optionJSONObject);

		return optionsJSONArray;
	}

	protected JSONObject getPredefinedValueJSONObject(
		JSONObject checkboxFieldJSONObject) {

		JSONObject oldPredefinedValueJSONObject =
			checkboxFieldJSONObject.getJSONObject("predefinedValue");

		JSONObject newPredefinedValueJSONObject =
			_jsonFactory.createJSONObject();

		Iterator<String> languageKeys = oldPredefinedValueJSONObject.keys();

		while (languageKeys.hasNext()) {
			String languageKey = languageKeys.next();

			String predefinedValue = oldPredefinedValueJSONObject.getString(
				languageKey);

			if (Objects.equals(predefinedValue, "true")) {
				predefinedValue = checkboxFieldJSONObject.getString("name");
			}
			else {
				predefinedValue = StringPool.BLANK;
			}

			newPredefinedValueJSONObject.put(languageKey, predefinedValue);
		}

		return newPredefinedValueJSONObject;
	}

	protected String serialize(DDMFormValues ddmFormValues) {
		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				_ddmFormValuesSerializer.serialize(builder.build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
	}

	protected void transformCheckboxDDMFormField(
		JSONObject checkboxFieldJSONObject) {

		checkboxFieldJSONObject.put(
			"dataType", "string"
		).put(
			"options", getOptionsJSONArray(checkboxFieldJSONObject)
		).put(
			"predefinedValue",
			getPredefinedValueJSONObject(checkboxFieldJSONObject)
		).put(
			"type", "checkbox_multiple"
		);
	}

	protected void transformCheckboxDDMFormFieldValues(
			DDMFormValues ddmFormValues)
		throws Exception {

		DDMFormValuesTransformer ddmFormValuesTransformer =
			new DDMFormValuesTransformer(ddmFormValues);

		ddmFormValuesTransformer.addTransformer(
			new CheckboxDDMFormFieldValueTransformer(_jsonFactory));

		ddmFormValuesTransformer.transform();
	}

	protected void updateRecords(DDMForm ddmForm, long recordSetId)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("select DDLRecordVersion.DDMStorageId, DDMContent.data_ ");
		sb.append("from DDLRecordVersion inner join DDLRecordSet on ");
		sb.append("DDLRecordVersion.recordSetId = DDLRecordSet.recordSetId ");
		sb.append("inner join DDMContent on DDLRecordVersion.DDMStorageId = ");
		sb.append("DDMContent.contentId where DDLRecordSet.recordSetId = ? ");

		try (PreparedStatement ps1 = connection.prepareStatement(sb.toString());
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					"update DDMContent set data_ = ? where contentId = ? ")) {

			ps1.setLong(1, recordSetId);

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					String data_ = rs.getString("data_");

					DDMFormValues ddmFormValues = deserialize(data_, ddmForm);

					transformCheckboxDDMFormFieldValues(ddmFormValues);

					ps2.setString(1, serialize(ddmFormValues));

					long contentId = rs.getLong("DDMStorageId");

					ps2.setLong(2, contentId);

					ps2.addBatch();
				}

				ps2.executeBatch();
			}
		}
	}

	protected String upgradeRecordSetStructureDefinition(String definition)
		throws JSONException {

		JSONObject definitionJSONObject = _jsonFactory.createJSONObject(
			definition);

		JSONArray fieldsJSONArray = definitionJSONObject.getJSONArray("fields");

		upgradeRecordSetStructureFields(fieldsJSONArray);

		return definitionJSONObject.toString();
	}

	protected void upgradeRecordSetStructureFields(JSONArray fieldsJSONArray) {
		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

			String type = fieldJSONObject.getString("type");

			if (type.equals("checkbox")) {
				transformCheckboxDDMFormField(fieldJSONObject);
			}

			JSONArray nestedFieldsJSONArray = fieldJSONObject.getJSONArray(
				"nestedFields");

			if (nestedFieldsJSONArray != null) {
				upgradeRecordSetStructureFields(nestedFieldsJSONArray);
			}
		}
	}

	private static final int _SCOPE_FORMS = 2;

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormValuesDeserializer _ddmFormValuesDeserializer;
	private final DDMFormValuesSerializer _ddmFormValuesSerializer;
	private final JSONFactory _jsonFactory;

	private static class CheckboxDDMFormFieldValueTransformer
		implements DDMFormFieldValueTransformer {

		public CheckboxDDMFormFieldValueTransformer(JSONFactory jsonFactory) {
			_jsonFactory = jsonFactory;
		}

		@Override
		public String getFieldType() {
			return "checkbox";
		}

		@Override
		public void transform(DDMFormFieldValue ddmFormFieldValue)
			throws PortalException {

			Value value = ddmFormFieldValue.getValue();

			for (Locale locale : value.getAvailableLocales()) {
				String valueString = value.getString(locale);

				JSONArray valueJSONArray = _jsonFactory.createJSONArray();

				if (Objects.equals(valueString, "true")) {
					DDMFormField ddmFormField =
						ddmFormFieldValue.getDDMFormField();

					valueJSONArray.put(ddmFormField.getName());
				}

				value.addString(locale, valueJSONArray.toString());
			}
		}

		private JSONFactory _jsonFactory;

	}

}