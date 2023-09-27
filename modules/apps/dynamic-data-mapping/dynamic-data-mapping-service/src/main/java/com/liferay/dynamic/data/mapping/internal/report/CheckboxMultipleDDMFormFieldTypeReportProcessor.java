/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.report;

import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.report.DDMFormFieldTypeReportProcessor;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Iterator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	property = {
		"ddm.form.field.type.name=checkbox_multiple",
		"ddm.form.field.type.name=select"
	},
	service = DDMFormFieldTypeReportProcessor.class
)
public class CheckboxMultipleDDMFormFieldTypeReportProcessor
	implements DDMFormFieldTypeReportProcessor {

	public CheckboxMultipleDDMFormFieldTypeReportProcessor() {
	}

	public CheckboxMultipleDDMFormFieldTypeReportProcessor(
		JSONFactory jsonFactory) {

		_jsonFactory = jsonFactory;
	}

	@Override
	public JSONObject process(
			DDMFormFieldValue ddmFormFieldValue, JSONObject fieldJSONObject,
			long formInstanceRecordId, String ddmFormInstanceReportEvent)
		throws Exception {

		JSONObject valuesJSONObject = fieldJSONObject.getJSONObject("values");

		Value value = ddmFormFieldValue.getValue();

		JSONArray valueJSONArray = _jsonFactory.createJSONArray(
			value.getString(value.getDefaultLocale()));

		if (valuesJSONObject != null) {
			Iterator<String> iterator = valueJSONArray.iterator();

			while (iterator.hasNext()) {
				String key = iterator.next();

				updateData(ddmFormInstanceReportEvent, valuesJSONObject, key);
			}
		}

		if (valueJSONArray.length() != 0) {
			updateData(
				ddmFormInstanceReportEvent, fieldJSONObject, "totalEntries");
		}
		else {
			fieldJSONObject.put(
				"totalEntries", fieldJSONObject.getInt("totalEntries"));
		}

		return fieldJSONObject;
	}

	@Reference
	private JSONFactory _jsonFactory;

}