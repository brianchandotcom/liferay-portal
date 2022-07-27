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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_3;

import com.liferay.dynamic.data.mapping.constants.DDMFormInstanceReportConstants;
import com.liferay.dynamic.data.mapping.internal.report.CheckboxMultipleDDMFormFieldTypeReportProcessor;
import com.liferay.dynamic.data.mapping.internal.report.NumericDDMFormFieldTypeReportProcessor;
import com.liferay.dynamic.data.mapping.internal.report.RadioDDMFormFieldTypeReportProcessor;
import com.liferay.dynamic.data.mapping.internal.report.TextDDMFormFieldTypeReportProcessor;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.report.DDMFormFieldTypeReportProcessor;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormDeserializeUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Marcos Martins
 */
public class DDMFormInstanceReportUpgradeProcess extends UpgradeProcess {

	public DDMFormInstanceReportUpgradeProcess(
		DDMFormDeserializer ddmFormDeserializer, JSONFactory jsonFactory) {

		_ddmFormDeserializer = ddmFormDeserializer;
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		runSQL("delete from DDMFormInstanceReport");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select formInstanceId, groupId, companyId, createDate from " +
					"DDMFormInstance")) {

			ResultSet resultSet1 = preparedStatement1.executeQuery();

			while (resultSet1.next()) {
				long formInstanceId = resultSet1.getLong("formInstanceId");

				JSONObject dataJSONObject = _jsonFactory.createJSONObject();

				try (PreparedStatement preparedStatement2 =
						connection.prepareStatement(
							StringBundler.concat(
								"select DDMContent.data_, ",
								"DDMFormInstanceRecord.formInstanceRecordId, ",
								"DDMStructureVersion.definition from ",
								"DDMContent inner join ",
								"DDMFormInstanceRecordVersion on ",
								"DDMContent.contentId = ",
								"DDMFormInstanceRecordVersion.storageId inner ",
								"join DDMFormInstanceRecord on ",
								"DDMFormInstanceRecord.formInstanceRecordId = ",
								"DDMFormInstanceRecordVersion.",
								"formInstanceRecordId inner join ",
								"DDMFormInstanceVersion on ",
								"DDMFormInstanceVersion.formInstanceId = ",
								"DDMFormInstanceRecordVersion.formInstanceId ",
								"and DDMFormInstanceVersion.version = ",
								"DDMFormInstanceRecordVersion.",
								"formInstanceVersion inner join ",
								"DDMStructureVersion on ",
								"DDMStructureVersion.structureVersionId = ",
								"DDMFormInstanceVersion.structureVersionId ",
								"where DDMFormInstanceRecord.version = ",
								"DDMFormInstanceRecordVersion.version and ",
								"DDMFormInstanceRecord.formInstanceId = ? and ",
								"DDMFormInstanceRecordVersion.status = ?"))) {

					preparedStatement2.setLong(1, formInstanceId);
					preparedStatement2.setInt(
						2, WorkflowConstants.STATUS_APPROVED);

					ResultSet resultSet2 = preparedStatement2.executeQuery();

					while (resultSet2.next()) {
						dataJSONObject = _processDDMFormValues(
							dataJSONObject,
							_getDDMFormValues(
								resultSet2.getString("data_"),
								DDMFormDeserializeUtil.deserialize(
									_ddmFormDeserializer,
									resultSet2.getString("definition"))),
							resultSet2.getLong("formInstanceRecordId"));

						dataJSONObject.put(
							"totalItems",
							dataJSONObject.getInt("totalItems") + 1);
					}
				}

				long groupId = resultSet1.getLong("groupId");

				long companyId = resultSet1.getLong("companyId");

				Timestamp createDate = resultSet1.getTimestamp("createDate");

				try (PreparedStatement preparedStatement3 =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection,
							StringBundler.concat(
								"insert into DDMFormInstanceReport ",
								"(formInstanceReportId, groupId, companyId, ",
								"createDate, modifiedDate, formInstanceId, ",
								"data_) values (?, ?, ?, ?, ?, ?, ?)"))) {

					preparedStatement3.setLong(1, increment());
					preparedStatement3.setLong(2, groupId);
					preparedStatement3.setLong(3, companyId);
					preparedStatement3.setTimestamp(4, createDate);
					preparedStatement3.setTimestamp(5, createDate);
					preparedStatement3.setLong(6, formInstanceId);
					preparedStatement3.setString(7, dataJSONObject.toString());

					preparedStatement3.execute();
				}
			}
		}
	}

	private DDMFormValues _getDDMFormValues(String data, DDMForm ddmForm)
		throws Exception {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		JSONObject dataJSONObject = _jsonFactory.createJSONObject(data);

		JSONArray fieldValuesJSONArray = dataJSONObject.getJSONArray(
			"fieldValues");

		Iterator<JSONObject> iterator = fieldValuesJSONArray.iterator();

		while (iterator.hasNext()) {
			JSONObject jsonObject = iterator.next();

			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			ddmFormFieldValue.setDDMFormValues(ddmFormValues);
			ddmFormFieldValue.setInstanceId(jsonObject.getString("instanceId"));
			ddmFormFieldValue.setName(jsonObject.getString("name"));

			DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

			if (ddmFormField == null) {
				continue;
			}

			Value value = null;

			if (ddmFormField.isLocalizable()) {
				value = new LocalizedValue();

				JSONObject valueJSONObject = jsonObject.getJSONObject("value");

				if (valueJSONObject == null) {
					continue;
				}

				for (String languageId : valueJSONObject.keySet()) {
					value.addString(
						LocaleUtil.fromLanguageId(languageId),
						GetterUtil.getString(
							valueJSONObject.getString(languageId)));
				}
			}
			else {
				value = new UnlocalizedValue(
					GetterUtil.getString(jsonObject.get("value")));
			}

			ddmFormFieldValue.setValue(value);

			ddmFormFieldValues.add(ddmFormFieldValue);
		}

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);

		return ddmFormValues;
	}

	private JSONObject _processDDMFormValues(
			JSONObject dataJSONObject, DDMFormValues ddmFormValues,
			long formInstanceRecordId)
		throws Exception {

		for (DDMFormFieldValue ddmFormFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			DDMFormFieldTypeReportProcessor ddmFormFieldTypeReportProcessor =
				_ddmFormFieldTypeReportProcessorTracker.
					getDDMFormFieldTypeReportProcessor(
						ddmFormFieldValue.getDDMFormField(),
						ddmFormFieldValue.getType());

			if (ddmFormFieldTypeReportProcessor != null) {
				String ddmFormFieldValueName = ddmFormFieldValue.getName();

				JSONObject fieldJSONObject = dataJSONObject.getJSONObject(
					ddmFormFieldValueName);

				if (fieldJSONObject == null) {
					fieldJSONObject = JSONUtil.put(
						"type", ddmFormFieldValue.getType()
					).put(
						"values", _jsonFactory.createJSONObject()
					);
				}

				try {
					JSONObject processedFieldJSONObject =
						ddmFormFieldTypeReportProcessor.process(
							ddmFormFieldValue,
							_jsonFactory.createJSONObject(
								fieldJSONObject.toString()),
							formInstanceRecordId,
							DDMFormInstanceReportConstants.
								EVENT_ADD_RECORD_VERSION);

					dataJSONObject.put(
						ddmFormFieldValueName, processedFieldJSONObject);
				}
				catch (JSONException jsonException) {
					if (_log.isWarnEnabled()) {
						_log.warn(jsonException);
					}
				}
			}
		}

		return dataJSONObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceReportUpgradeProcess.class);

	private final DDMFormDeserializer _ddmFormDeserializer;
	private final DDMFormFieldTypeReportProcessorTracker
		_ddmFormFieldTypeReportProcessorTracker =
			new DDMFormFieldTypeReportProcessorTracker();
	private final JSONFactory _jsonFactory;

	private class DDMFormFieldTypeReportProcessorTracker {

		public DDMFormFieldTypeReportProcessor
			getDDMFormFieldTypeReportProcessor(
				DDMFormField ddmFormField, String type) {

			if (StringUtil.equals(type, "checkbox_multiple") ||
				StringUtil.equals(type, "select")) {

				return new CheckboxMultipleDDMFormFieldTypeReportProcessor();
			}
			else if (StringUtil.equals(type, "color") ||
					 StringUtil.equals(type, "date") ||
					 StringUtil.equals(type, "text")) {

				return new TextDDMFormFieldTypeReportProcessor();
			}
			else if (StringUtil.equals(type, "grid")) {
				return new UpgradeGridDDMFormFieldTypeReportProcessor(
					ddmFormField);
			}
			else if (StringUtil.equals(type, "numeric")) {
				return new NumericDDMFormFieldTypeReportProcessor();
			}
			else if (StringUtil.equals(type, "radio")) {
				return new RadioDDMFormFieldTypeReportProcessor();
			}

			return null;
		}

	}

	private class UpgradeGridDDMFormFieldTypeReportProcessor
		implements DDMFormFieldTypeReportProcessor {

		public UpgradeGridDDMFormFieldTypeReportProcessor(
			DDMFormField ddmFormField) {

			_ddmFormField = ddmFormField;
		}

		@Override
		public JSONObject process(
				DDMFormFieldValue ddmFormFieldValue, JSONObject fieldJSONObject,
				long formInstanceRecordId, String ddmFormInstanceReportEvent)
			throws Exception {

			JSONObject valuesJSONObject = fieldJSONObject.getJSONObject(
				"values");

			Value value = ddmFormFieldValue.getValue();

			JSONObject valueJSONObject = JSONFactoryUtil.createJSONObject(
				value.getString(value.getDefaultLocale()));

			Iterator<String> iterator = valueJSONObject.keys();

			while (iterator.hasNext()) {
				String rowName = iterator.next();

				JSONObject rowJSONObject = valuesJSONObject.getJSONObject(
					rowName);

				if (rowJSONObject == null) {
					rowJSONObject = JSONFactoryUtil.createJSONObject();
				}

				String columnName = valueJSONObject.getString(rowName);

				rowJSONObject.put(
					columnName, rowJSONObject.getInt(columnName) + 1);

				valuesJSONObject.put(rowName, rowJSONObject);
			}

			int totalEntries = fieldJSONObject.getInt("totalEntries");

			if (valueJSONObject.length() != 0) {
				totalEntries++;
			}

			fieldJSONObject.put(
				"structure",
				JSONUtil.put(
					"columns", _getOptionValuesJSONArray("columns")
				).put(
					"rows", _getOptionValuesJSONArray("rows")
				)
			).put(
				"totalEntries", totalEntries
			);

			return fieldJSONObject;
		}

		private JSONArray _getOptionValuesJSONArray(String propertyName) {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			DDMFormFieldOptions ddmFormFieldOptions =
				(DDMFormFieldOptions)_ddmFormField.getProperty(propertyName);

			if (ddmFormFieldOptions != null) {
				Set<String> optionsValues =
					ddmFormFieldOptions.getOptionsValues();

				optionsValues.forEach(
					optionValue -> jsonArray.put(optionValue));
			}

			return jsonArray;
		}

		private final DDMFormField _ddmFormField;

	}

}