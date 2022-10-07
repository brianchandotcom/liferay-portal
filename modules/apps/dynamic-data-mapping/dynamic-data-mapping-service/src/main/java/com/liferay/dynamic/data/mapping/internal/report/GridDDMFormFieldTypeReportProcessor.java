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

package com.liferay.dynamic.data.mapping.internal.report;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.report.DDMFormFieldTypeReportProcessor;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Iterator;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	property = "ddm.form.field.type.name=grid",
	service = DDMFormFieldTypeReportProcessor.class
)
public class GridDDMFormFieldTypeReportProcessor
	implements DDMFormFieldTypeReportProcessor {

	@Override
	public JSONObject process(
			DDMFormFieldValue ddmFormFieldValue, JSONObject fieldJSONObject,
			long formInstanceRecordId, String ddmFormInstanceReportEvent)
		throws Exception {

		JSONObject valuesJSONObject = fieldJSONObject.getJSONObject("values");

		Value value = ddmFormFieldValue.getValue();

		JSONObject valueJSONObject = JSONFactoryUtil.createJSONObject(
			value.getString(value.getDefaultLocale()));

		Iterator<String> iterator = valueJSONObject.keys();

		while (iterator.hasNext()) {
			String rowName = iterator.next();

			JSONObject rowJSONObject = valuesJSONObject.getJSONObject(rowName);

			if (rowJSONObject == null) {
				rowJSONObject = JSONFactoryUtil.createJSONObject();
			}

			String columnName = valueJSONObject.getString(rowName);

			updateData(ddmFormInstanceReportEvent, rowJSONObject, columnName);

			valuesJSONObject.put(rowName, rowJSONObject);
		}

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordLocalService.getDDMFormInstanceRecord(
				formInstanceRecordId);

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecord.getFormInstance();

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		DDMFormField ddmFormField = ddmStructure.getDDMFormField(
			ddmFormFieldValue.getName());

		if (valueJSONObject.length() != 0) {
			updateData(
				ddmFormInstanceReportEvent, fieldJSONObject, "totalEntries");
		}
		else {
			fieldJSONObject.put(
				"totalEntries", fieldJSONObject.getInt("totalEntries"));
		}

		fieldJSONObject.put(
			"structure",
			JSONUtil.put(
				"columns", _getOptionValuesJSONArray(ddmFormField, "columns")
			).put(
				"rows", _getOptionValuesJSONArray(ddmFormField, "rows")
			));

		return fieldJSONObject;
	}

	@Reference
	protected DDMFormInstanceRecordLocalService
		ddmFormInstanceRecordLocalService;

	private JSONArray _getOptionValuesJSONArray(
		DDMFormField ddmFormField, String propertyName) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		DDMFormFieldOptions ddmFormFieldOptions =
			(DDMFormFieldOptions)ddmFormField.getProperty(propertyName);

		if (ddmFormFieldOptions != null) {
			Set<String> optionsValues = ddmFormFieldOptions.getOptionsValues();

			optionsValues.forEach(optionValue -> jsonArray.put(optionValue));
		}

		return jsonArray;
	}

}