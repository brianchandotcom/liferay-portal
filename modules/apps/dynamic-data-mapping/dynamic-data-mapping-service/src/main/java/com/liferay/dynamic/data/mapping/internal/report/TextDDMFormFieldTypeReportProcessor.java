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

import com.liferay.dynamic.data.mapping.constants.DDMFormInstanceReportConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.report.DDMFormFieldTypeReportProcessor;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.comparator.DDMFormInstanceRecordModifiedDateComparator;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	immediate = true,
	property = {
		"ddm.form.field.type.name=date", "ddm.form.field.type.name=text"
	},
	service = DDMFormFieldTypeReportProcessor.class
)
public class TextDDMFormFieldTypeReportProcessor
	implements DDMFormFieldTypeReportProcessor {

	@Override
	public JSONObject process(
			DDMFormFieldValue ddmFormFieldValue, JSONObject fieldJSONObject,
			long formInstanceRecordId, String formInstanceReportEvent)
		throws Exception {

		DDMFormInstanceRecord formInstanceRecord =
			ddmFormInstanceRecordLocalService.getFormInstanceRecord(
				formInstanceRecordId);

		JSONArray valuesJSONArray = JSONFactoryUtil.createJSONArray();

		if (formInstanceReportEvent.equals(
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION)) {

			valuesJSONArray.put(
				JSONUtil.put(
					"formInstanceRecordId", formInstanceRecordId
				).put(
					"value", _getValue(ddmFormFieldValue)
				));

			JSONArray jsonArray = fieldJSONObject.getJSONArray("values");

			if (jsonArray != null) {
				Iterator<JSONObject> iterator = jsonArray.iterator();

				while (iterator.hasNext() && (valuesJSONArray.length() < 5)) {
					JSONObject jsonObject = iterator.next();

					if (jsonObject.getLong("formInstanceRecordId") ==
							formInstanceRecordId) {

						continue;
					}

					valuesJSONArray.put(jsonObject);
				}
			}
		}
		else if (formInstanceReportEvent.equals(
					DDMFormInstanceReportConstants.
						EVENT_DELETE_RECORD_VERSION)) {

			List<DDMFormInstanceRecord> formInstanceRecords =
				ddmFormInstanceRecordLocalService.getFormInstanceRecords(
					formInstanceRecord.getFormInstanceId(),
					WorkflowConstants.STATUS_APPROVED, 0, 5,
					new DDMFormInstanceRecordModifiedDateComparator(false));

			for (DDMFormInstanceRecord ddmFormInstanceRecord :
					formInstanceRecords) {

				DDMFormValues ddmFormValues =
					ddmFormInstanceRecord.getDDMFormValues();

				Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
					ddmFormValues.getDDMFormFieldValuesMap();

				List<DDMFormFieldValue> formFieldValues =
					ddmFormFieldValuesMap.get(ddmFormFieldValue.getName());

				for (DDMFormFieldValue formFieldValue : formFieldValues) {
					valuesJSONArray.put(
						JSONUtil.put(
							"formInstanceRecordId",
							ddmFormInstanceRecord.getFormInstanceRecordId()
						).put(
							"value", _getValue(formFieldValue)
						));
				}
			}
		}

		fieldJSONObject.put(
			"totalEntries",
			ddmFormInstanceRecordLocalService.getFormInstanceRecordsCount(
				formInstanceRecord.getFormInstanceId(),
				WorkflowConstants.STATUS_APPROVED)
		).put(
			"values", valuesJSONArray
		);

		return fieldJSONObject;
	}

	@Reference
	protected DDMFormInstanceRecordLocalService
		ddmFormInstanceRecordLocalService;

	private String _getValue(DDMFormFieldValue ddmFormFieldValue) {
		Value value = ddmFormFieldValue.getValue();

		return value.getString(value.getDefaultLocale());
	}

}