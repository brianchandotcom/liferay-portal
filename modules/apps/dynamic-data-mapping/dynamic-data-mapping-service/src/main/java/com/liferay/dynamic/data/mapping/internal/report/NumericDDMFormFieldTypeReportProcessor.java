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
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

		BigDecimal bigDecimalValue = _getBigDecimalValue(ddmFormFieldValue);

		if (bigDecimalValue == null) {
			return jsonObject;
		}

		JSONObject summaryJSONObject = jsonObject.getJSONObject("summary");

		if (summaryJSONObject == null) {
			summaryJSONObject = JSONFactoryUtil.createJSONObject();
		}

		BigDecimal sum = new BigDecimal(
			summaryJSONObject.getString("sum", "0"));

		if (ddmFormInstanceReportEvent.equals(
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION)) {

			BigDecimal maxValue = null;

			if (summaryJSONObject.has("max")) {
				maxValue = new BigDecimal(summaryJSONObject.getString("max"));
			}

			if ((maxValue == null) ||
				(bigDecimalValue.compareTo(maxValue) == 1)) {

				summaryJSONObject.put("max", bigDecimalValue.toString());
			}

			BigDecimal minValue = null;

			if (summaryJSONObject.has("min")) {
				minValue = new BigDecimal(summaryJSONObject.getString("min"));
			}

			if ((minValue == null) ||
				(bigDecimalValue.compareTo(minValue) == -1)) {

				summaryJSONObject.put("min", bigDecimalValue.toString());
			}

			sum = sum.add(bigDecimalValue);
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

			BigDecimal maxValue = _getBigDecimalValuesStream(
				ddmFormFieldValue.getName(), streamSupplier.get()
			).max(
				comparator
			).get();

			BigDecimal minValue = _getBigDecimalValuesStream(
				ddmFormFieldValue.getName(), streamSupplier.get()
			).min(
				comparator
			).get();

			summaryJSONObject.put(
				"max", maxValue.toString()
			).put(
				"min", minValue.toString()
			);

			sum = sum.subtract(bigDecimalValue);
		}

		BigDecimal average = new BigDecimal("0.0");

		if (jsonObject.getInt("totalEntries") > 0) {
			average = sum.divide(
				new BigDecimal(jsonObject.getInt("totalEntries")), 10,
				RoundingMode.HALF_UP);
		}

		summaryJSONObject.put(
			"average", average
		).put(
			"sum", sum.toString()
		);

		jsonObject.put("summary", summaryJSONObject);

		return jsonObject;
	}

	private BigDecimal _getBigDecimalValue(
		DDMFormFieldValue ddmFormFieldValue) {

		String value = getValue(ddmFormFieldValue);

		if (Validator.isNull(value)) {
			return null;
		}

		return new BigDecimal(value);
	}

	private Stream<BigDecimal> _getBigDecimalValuesStream(
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
						ddmFormFieldValue -> _getBigDecimalValue(
							ddmFormFieldValue)
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