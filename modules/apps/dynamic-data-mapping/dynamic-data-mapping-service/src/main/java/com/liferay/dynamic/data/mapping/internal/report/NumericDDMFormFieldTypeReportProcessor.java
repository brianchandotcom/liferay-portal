/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.report;

import com.liferay.dynamic.data.mapping.constants.DDMFormInstanceReportConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.report.DDMFormFieldTypeReportProcessor;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcos Martins
 */
@Component(
	property = "ddm.form.field.type.name=numeric",
	service = DDMFormFieldTypeReportProcessor.class
)
public class NumericDDMFormFieldTypeReportProcessor
	extends TextDDMFormFieldTypeReportProcessor {

	public NumericDDMFormFieldTypeReportProcessor() {
	}

	public NumericDDMFormFieldTypeReportProcessor(JSONFactory jsonFactory) {
		super(jsonFactory);
	}

	@Override
	public JSONObject process(
			DDMFormFieldValue ddmFormFieldValue, JSONObject fieldJSONObject,
			long formInstanceRecordId, String ddmFormInstanceReportEvent)
		throws Exception {

		JSONObject jsonObject = super.process(
			ddmFormFieldValue, fieldJSONObject, formInstanceRecordId,
			ddmFormInstanceReportEvent);

		BigDecimal valueBigDecimal = getValueBigDecimal(ddmFormFieldValue);

		if (valueBigDecimal == null) {
			return jsonObject;
		}

		JSONObject summaryJSONObject = jsonObject.getJSONObject("summary");

		if (summaryJSONObject == null) {
			summaryJSONObject = jsonFactory.createJSONObject();
		}

		BigDecimal sumBigDecimal = new BigDecimal(
			summaryJSONObject.getString("sum", "0"));

		if (ddmFormInstanceReportEvent.equals(
				DDMFormInstanceReportConstants.EVENT_ADD_RECORD_VERSION)) {

			BigDecimal maxValueBigDecimal = null;

			if (summaryJSONObject.has("max")) {
				maxValueBigDecimal = new BigDecimal(
					summaryJSONObject.getString("max"));
			}

			if ((maxValueBigDecimal == null) ||
				(valueBigDecimal.compareTo(maxValueBigDecimal) == 1)) {

				summaryJSONObject.put("max", valueBigDecimal.toString());
			}

			BigDecimal minValueBigDecimal = null;

			if (summaryJSONObject.has("min")) {
				minValueBigDecimal = new BigDecimal(
					summaryJSONObject.getString("min"));
			}

			if ((minValueBigDecimal == null) ||
				(valueBigDecimal.compareTo(minValueBigDecimal) == -1)) {

				summaryJSONObject.put("min", valueBigDecimal.toString());
			}

			sumBigDecimal = sumBigDecimal.add(valueBigDecimal);
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
				new ArrayList<>();

			for (DDMFormInstanceRecord currentDDMFormInstanceRecord :
					ddmFormInstance.getFormInstanceRecords()) {

				if (formInstanceRecordId !=
						currentDDMFormInstanceRecord.
							getFormInstanceRecordId()) {

					ddmFormInstanceRecords.add(currentDDMFormInstanceRecord);
				}
			}

			if (ddmFormInstanceRecords.isEmpty()) {
				jsonObject.remove("summary");

				return jsonObject;
			}

			List<BigDecimal> valueBigDecimals = _getValueBigDecimals(
				ddmFormFieldValue.getName(), ddmFormInstanceRecords);

			BigDecimal maxValueBigDecimal = Collections.max(
				valueBigDecimals,
				Comparator.comparingDouble(Number::doubleValue));
			BigDecimal minValueBigDecimal = Collections.min(
				valueBigDecimals,
				Comparator.comparingDouble(Number::doubleValue));

			summaryJSONObject.put(
				"max", maxValueBigDecimal.toString()
			).put(
				"min", minValueBigDecimal.toString()
			);

			sumBigDecimal = sumBigDecimal.subtract(valueBigDecimal);
		}

		BigDecimal averageBigDecimal = new BigDecimal("0.0");

		if (jsonObject.getInt("totalEntries") > 0) {
			averageBigDecimal = sumBigDecimal.divide(
				new BigDecimal(jsonObject.getInt("totalEntries")), 10,
				RoundingMode.HALF_UP);
		}

		summaryJSONObject.put(
			"average", formatBigDecimal(averageBigDecimal)
		).put(
			"sum", sumBigDecimal.toString()
		);

		jsonObject.put("summary", summaryJSONObject);

		return jsonObject;
	}

	protected String formatBigDecimal(BigDecimal bigDecimal) {
		StringBundler sb = new StringBundler(3);

		String bigDecimalString = bigDecimal.toString();

		String[] bigDecimalStringParts = bigDecimalString.split("\\.");

		sb.append(bigDecimalStringParts[0]);

		if (bigDecimalStringParts.length > 1) {
			String decimalPart = bigDecimalStringParts[1];

			int decimalPartIndex = decimalPart.length() - 1;

			while ((decimalPartIndex > 0) &&
				   (decimalPart.charAt(decimalPartIndex) == '0')) {

				decimalPartIndex--;
			}

			if (decimalPartIndex > 0) {
				decimalPart = decimalPart.substring(0, decimalPartIndex + 1);

				sb.append(StringPool.PERIOD);
				sb.append(decimalPart);
			}
		}

		return sb.toString();
	}

	protected BigDecimal getValueBigDecimal(
		DDMFormFieldValue ddmFormFieldValue) {

		try {
			Value value = ddmFormFieldValue.getValue();

			Locale defaultLocale = value.getDefaultLocale();

			String valueString = value.getString(defaultLocale);

			if (Validator.isNull(valueString)) {
				return null;
			}

			DecimalFormat decimalFormat =
				(DecimalFormat)NumberFormat.getNumberInstance(defaultLocale);

			decimalFormat.setParseBigDecimal(true);

			return new BigDecimal(
				String.valueOf(decimalFormat.parse(valueString)));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return null;
		}
	}

	private List<BigDecimal> _getValueBigDecimals(
		String ddmFormFieldValueName,
		List<DDMFormInstanceRecord> ddmFormInstanceRecords) {

		List<BigDecimal> values = new ArrayList<>();

		for (DDMFormInstanceRecord ddmFormInstanceRecord :
				ddmFormInstanceRecords) {

			try {
				DDMFormValues ddmFormValues =
					ddmFormInstanceRecord.getDDMFormValues();

				Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
					ddmFormValues.getDDMFormFieldValuesMap(true);

				List<DDMFormFieldValue> ddmFormFieldValues =
					ddmFormFieldValuesMap.get(ddmFormFieldValueName);

				BigDecimal value = getValueBigDecimal(
					ddmFormFieldValues.get(0));

				if (value != null) {
					values.add(value);
				}
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(portalException);
				}
			}
		}

		return values;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NumericDDMFormFieldTypeReportProcessor.class);

}