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
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.report.DDMFormFieldTypeReportProcessor;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcos Martins
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=numeric",
	service = DDMFormFieldTypeReportProcessor.class
)
public class NumericDDMFormFieldTypeReportProcessor
	extends TextDDMFormFieldTypeReportProcessor {

	@Override
	public JSONObject process(
			DDMFormFieldValue ddmFormFieldValue, JSONObject fieldJSONObject,
			long formInstanceRecordId, String ddmFormInstanceReportEvent)
		throws Exception {

		JSONObject jsonObject = super.process(
			ddmFormFieldValue, fieldJSONObject, formInstanceRecordId,
			ddmFormInstanceReportEvent);

		Number numberValue = _getNumberValue(ddmFormFieldValue);

		if (numberValue == null) {
			return jsonObject;
		}

		JSONObject summaryJSONObject = jsonObject.getJSONObject("summary");

		if (summaryJSONObject == null) {
			summaryJSONObject = JSONFactoryUtil.createJSONObject();
		}

		double sum = summaryJSONObject.getDouble("sum", 0);

		if (ddmFormInstanceReportEvent.equals(
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION)) {

			if (!summaryJSONObject.has("max") ||
				(numberValue.doubleValue() > summaryJSONObject.getDouble(
					"max"))) {

				summaryJSONObject.put("max", numberValue.doubleValue());
			}

			if (!summaryJSONObject.has("min") ||
				(numberValue.doubleValue() < summaryJSONObject.getDouble(
					"min"))) {

				summaryJSONObject.put("min", numberValue.doubleValue());
			}

			sum += numberValue.doubleValue();
		}
		else if (ddmFormInstanceReportEvent.equals(
					DDMFormInstanceReportConstants.
						EVENT_DELETE_RECORD_VERSION)) {

			DDMFormInstanceRecord ddmFormInstanceRecord =
				ddmFormInstanceRecordLocalService.getFormInstanceRecord(
					formInstanceRecordId);

			DDMFormInstance ddmFormInstance =
				ddmFormInstanceRecord.getFormInstance();

			List<DDMFormInstanceRecord> ddmFormInstanceRecords =
				ddmFormInstance.getFormInstanceRecords();

			Supplier<Stream<DDMFormInstanceRecord>> streamSupplier = () -> {
				Stream<DDMFormInstanceRecord> stream =
					ddmFormInstanceRecords.stream();

				return stream.filter(
					currentDDMFormInstanceRecord ->
						formInstanceRecordId !=
							currentDDMFormInstanceRecord.
								getFormInstanceRecordId());
			};

			Stream<DDMFormInstanceRecord> stream = streamSupplier.get();

			if (stream.count() == 0) {
				jsonObject.remove("summary");

				return jsonObject;
			}

			Comparator<Number> comparator =
				(number1, number2) -> Double.compare(
					number1.doubleValue(), number2.doubleValue());

			Number maxValue = _getNumberValuesStream(
				ddmFormFieldValue.getName(), streamSupplier.get()
			).max(
				comparator
			).get();

			Number minValue = _getNumberValuesStream(
				ddmFormFieldValue.getName(), streamSupplier.get()
			).min(
				comparator
			).get();

			summaryJSONObject.put(
				"max", maxValue.doubleValue()
			).put(
				"min", minValue.doubleValue()
			);

			sum -= numberValue.doubleValue();
		}

		double average = 0.0;

		if (jsonObject.getInt("totalEntries") > 0) {
			average = sum / jsonObject.getInt("totalEntries");
		}

		summaryJSONObject.put(
			"average", average
		).put(
			"sum", sum
		);

		jsonObject.put("summary", summaryJSONObject);

		return jsonObject;
	}

	private Number _getNumberValue(DDMFormFieldValue ddmFormFieldValue) {
		String value = getValue(ddmFormFieldValue);

		if (Validator.isNull(value)) {
			return null;
		}

		return GetterUtil.getNumber(value);
	}

	private Stream<Number> _getNumberValuesStream(
		String ddmFormFieldValueName,
		Stream<DDMFormInstanceRecord> ddmFormInstanceRecordsStream) {

		return ddmFormInstanceRecordsStream.map(
			ddmFormInstanceRecord -> {
				try {
					DDMFormValues ddmFormValues =
						ddmFormInstanceRecord.getDDMFormValues();

					Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
						ddmFormValues.getDDMFormFieldValuesMap(false);

					List<DDMFormFieldValue> ddmFormFieldValues =
						ddmFormFieldValuesMap.get(ddmFormFieldValueName);

					Stream<DDMFormFieldValue> ddmFormFieldValuesStream =
						ddmFormFieldValues.stream();

					return ddmFormFieldValuesStream.map(
						ddmFormFieldValue -> _getNumberValue(ddmFormFieldValue)
					).findFirst(
					).get();
				}
				catch (PortalException portalException) {
					if (_log.isWarnEnabled()) {
						_log.warn(portalException, portalException);
					}

					return null;
				}
			}
		).filter(
			value -> value != null
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NumericDDMFormFieldTypeReportProcessor.class);

}