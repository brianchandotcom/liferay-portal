/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.json.JSONFactory;
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

		JSONObject valueJSONObject = _jsonFactory.createJSONObject(
			value.getString(value.getDefaultLocale()));

		Iterator<String> iterator = valueJSONObject.keys();

		while (iterator.hasNext()) {
			String rowName = iterator.next();

			JSONObject rowJSONObject = valuesJSONObject.getJSONObject(rowName);

			if (rowJSONObject == null) {
				rowJSONObject = _jsonFactory.createJSONObject();
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

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		DDMFormFieldOptions ddmFormFieldOptions =
			(DDMFormFieldOptions)ddmFormField.getProperty(propertyName);

		if (ddmFormFieldOptions != null) {
			Set<String> optionsValues = ddmFormFieldOptions.getOptionsValues();

			optionsValues.forEach(optionValue -> jsonArray.put(optionValue));
		}

		return jsonArray;
	}

	@Reference
	private JSONFactory _jsonFactory;

}