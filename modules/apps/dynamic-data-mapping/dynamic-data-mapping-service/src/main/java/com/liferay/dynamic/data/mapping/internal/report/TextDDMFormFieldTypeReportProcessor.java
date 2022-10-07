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
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.report.DDMFormFieldTypeReportProcessor;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	property = {
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.COLOR,
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.DATE,
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.DATE_TIME,
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.SEARCH_LOCATION,
		"ddm.form.field.type.name=" + DDMFormFieldTypeConstants.TEXT
	},
	service = DDMFormFieldTypeReportProcessor.class
)
public class TextDDMFormFieldTypeReportProcessor
	implements DDMFormFieldTypeReportProcessor {

	@Override
	public JSONObject process(
			DDMFormFieldValue ddmFormFieldValue, JSONObject fieldJSONObject,
			long formInstanceRecordId, String ddmFormInstanceReportEvent)
		throws Exception {

		boolean nullValue = Validator.isNull(getValue(ddmFormFieldValue));
		int totalEntries = fieldJSONObject.getInt("totalEntries");
		JSONArray valuesJSONArray = JSONFactoryUtil.createJSONArray();

		if (ddmFormInstanceReportEvent.equals(
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION)) {

			if (nullValue) {
				return fieldJSONObject;
			}

			valuesJSONArray.put(
				JSONUtil.put(
					"formInstanceRecordId", formInstanceRecordId
				).put(
					"value", getValue(ddmFormFieldValue)
				));

			JSONArray jsonArray = fieldJSONObject.getJSONArray("values");

			if (jsonArray != null) {
				Iterator<JSONObject> iterator = jsonArray.iterator();

				while (iterator.hasNext() &&
					   (valuesJSONArray.length() < _VALUES_MAX_LENGTH)) {

					JSONObject jsonObject = iterator.next();

					valuesJSONArray.put(jsonObject);
				}
			}

			totalEntries++;
		}
		else if (ddmFormInstanceReportEvent.equals(
					DDMFormInstanceReportConstants.
						EVENT_DELETE_RECORD_VERSION)) {

			DDMFormInstanceRecord ddmFormInstanceRecord =
				ddmFormInstanceRecordLocalService.getFormInstanceRecord(
					formInstanceRecordId);

			DDMFormInstance ddmFormInstance =
				ddmFormInstanceRecord.getFormInstance();

			BaseModelSearchResult<DDMFormInstanceRecord> baseModelSearchResult =
				ddmFormInstanceRecordLocalService.searchFormInstanceRecords(
					ddmFormInstance.getFormInstanceId(),
					new String[] {ddmFormFieldValue.getName()},
					WorkflowConstants.STATUS_APPROVED, 0,
					_VALUES_MAX_LENGTH + 1,
					new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, true));

			List<DDMFormInstanceRecord> ddmFormInstanceRecords =
				baseModelSearchResult.getBaseModels();

			Stream<DDMFormInstanceRecord> stream =
				ddmFormInstanceRecords.stream();

			stream.filter(
				currentDDMFormInstanceRecord ->
					currentDDMFormInstanceRecord.getFormInstanceRecordId() !=
						formInstanceRecordId
			).limit(
				_VALUES_MAX_LENGTH
			).forEach(
				currentDDMFormInstanceRecord -> {
					try {
						DDMFormValues ddmFormValues =
							currentDDMFormInstanceRecord.getDDMFormValues();

						Map<String, List<DDMFormFieldValue>>
							ddmFormFieldValuesMap =
								ddmFormValues.getDDMFormFieldValuesMap(true);

						List<DDMFormFieldValue> ddmFormFieldValues =
							ddmFormFieldValuesMap.get(
								ddmFormFieldValue.getName());

						ddmFormFieldValues.forEach(
							currentDDMFormFieldValue -> valuesJSONArray.put(
								JSONUtil.put(
									"formInstanceRecordId",
									currentDDMFormInstanceRecord.
										getFormInstanceRecordId()
								).put(
									"value", getValue(currentDDMFormFieldValue)
								)));
					}
					catch (PortalException portalException) {
						if (_log.isWarnEnabled()) {
							_log.warn(portalException);
						}
					}
				}
			);

			if (!nullValue) {
				totalEntries--;
			}
		}

		fieldJSONObject.put(
			"totalEntries", totalEntries
		).put(
			"values", valuesJSONArray
		);

		return fieldJSONObject;
	}

	protected String getValue(DDMFormFieldValue ddmFormFieldValue) {
		Value value = ddmFormFieldValue.getValue();

		return value.getString(value.getDefaultLocale());
	}

	@Reference
	protected DDMFormInstanceRecordLocalService
		ddmFormInstanceRecordLocalService;

	private static final int _VALUES_MAX_LENGTH = 5;

	private static final Log _log = LogFactoryUtil.getLog(
		TextDDMFormFieldTypeReportProcessor.class);

}