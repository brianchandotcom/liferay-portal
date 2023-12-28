/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.io.exporter;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriter;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterResponse;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.util.DateDDMFormFieldUtil;
import com.liferay.dynamic.data.mapping.util.NumericDDMFormFieldUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.io.ByteArrayOutputStream;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	property = "ddm.form.instance.record.writer.type=xls",
	service = DDMFormInstanceRecordWriter.class
)
public class DDMFormInstanceRecordXLSWriter
	implements DDMFormInstanceRecordWriter {

	@Override
	public DDMFormInstanceRecordWriterResponse write(
			DDMFormInstanceRecordWriterRequest
				ddmFormInstanceRecordWriterRequest)
		throws Exception {

		Map<String, String> ddmFormFieldsLabel =
			ddmFormInstanceRecordWriterRequest.getDDMFormFieldsLabel();

		if ((ddmFormFieldsLabel.size() > _COLUMNS_MAX_COUNT) &&
			_log.isWarnEnabled()) {

			_log.warn(
				StringBundler.concat(
					"Form has ", ddmFormFieldsLabel.size(),
					" fields. Due to XLS file format limitations, the first",
					_COLUMNS_MAX_COUNT,
					" will be included in the exported file."));
		}

		int rowIndex = 0;

		try (ByteArrayOutputStream byteArrayOutputStream =
				createByteArrayOutputStream();
			Workbook workbook = createWorkbook()) {

			CreationHelper creationHelper = workbook.getCreationHelper();

			DataFormat dataFormat = creationHelper.createDataFormat();

			Sheet sheet = workbook.createSheet();

			createRow(
				createCellStyle(workbook, true, "Courier New", (short)14),
				dataFormat, null, sheet.createRow(rowIndex++),
				ddmFormFieldsLabel);

			CellStyle rowCellStyle = createCellStyle(
				workbook, false, "Courier New", (short)12);

			rowCellStyle.setQuotePrefixed(true);

			for (Map<String, String> ddmFormFieldsValue :
					ddmFormInstanceRecordWriterRequest.
						getDDMFormFieldValues()) {

				createRow(
					rowCellStyle, dataFormat,
					ddmFormInstanceRecordWriterRequest.getDDMFormFields(),
					sheet.createRow(rowIndex++), ddmFormFieldsValue);
			}

			workbook.write(byteArrayOutputStream);

			return DDMFormInstanceRecordWriterResponse.Builder.newBuilder(
				byteArrayOutputStream.toByteArray()
			).build();
		}
	}

	protected ByteArrayOutputStream createByteArrayOutputStream() {
		return new ByteArrayOutputStream();
	}

	protected CellStyle createCellStyle(
		Workbook workbook, boolean bold, String fontName,
		short heightInPoints) {

		Font font = workbook.createFont();

		font.setBold(bold);
		font.setFontHeightInPoints(heightInPoints);
		font.setFontName(fontName);

		CellStyle style = workbook.createCellStyle();

		style.setFont(font);

		return style;
	}

	protected void createRow(
		CellStyle cellStyle, DataFormat dataFormat,
		Map<String, DDMFormField> ddmFormFields, Row row,
		Map<String, String> values) {

		int cellIndex = 0;

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		for (Map.Entry<String, String> entry : values.entrySet()) {
			if (cellIndex == _COLUMNS_MAX_COUNT) {
				break;
			}

			String value = entry.getValue();

			if (value.length() > _CELL_MAX_LENGTH) {
				value = value.substring(0, _CELL_MAX_LENGTH - 1);

				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Cell ", row.getRowNum(), ",", cellIndex,
							" value trimmed to ", _CELL_MAX_LENGTH,
							" characters"));
				}
			}

			Cell cell = row.createCell(cellIndex++);

			cell.setCellStyle(cellStyle);
			cell.setCellValue(value);

			if (ddmFormFields == null) {
				continue;
			}

			DDMFormField ddmFormField = ddmFormFields.get(entry.getKey());

			if (ddmFormField == null) {
				if (Objects.equals(entry.getKey(), "modifiedDate")) {
					SimpleDateFormat simpleDateFormat =
						DateDDMFormFieldUtil.getSimpleDateFormat(true, locale);

					_setCellValue(
						cell, dataFormat, true, simpleDateFormat.toPattern(),
						value);
				}

				continue;
			}

			if (ddmFormField.isRepeatable() &&
				value.contains(StringPool.COMMA_AND_SPACE)) {

				continue;
			}

			if (Objects.equals(
					ddmFormField.getType(), DDMFormFieldTypeConstants.DATE)) {

				_setCellValue(
					cell, dataFormat, false,
					DateDDMFormFieldUtil.getPattern(false, locale), value);
			}
			else if (Objects.equals(
						ddmFormField.getType(),
						DDMFormFieldTypeConstants.DATE_TIME)) {

				_setCellValue(
					cell, dataFormat, true,
					DateDDMFormFieldUtil.getPattern(true, locale), value);
			}
			else if (Objects.equals(
						ddmFormField.getType(),
						DDMFormFieldTypeConstants.NUMERIC)) {

				DecimalFormat decimalFormat =
					NumericDDMFormFieldUtil.getDecimalFormat(locale);

				try {
					cell.setCellValue(
						GetterUtil.getDouble(
							decimalFormat.parse(
								NumericDDMFormFieldUtil.getFormattedValue(
									locale, value))));
				}
				catch (ParseException parseException) {
					if (_log.isDebugEnabled()) {
						_log.debug(parseException);
					}
				}
			}
		}
	}

	protected Workbook createWorkbook() {
		return new HSSFWorkbook();
	}

	private void _setCellValue(
		Cell cell, DataFormat dataFormat, boolean dateTime, String pattern,
		String value) {

		try {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
				pattern);

			if (dateTime) {
				cell.setCellValue(
					LocalDateTime.parse(value, dateTimeFormatter));
			}
			else {
				cell.setCellValue(LocalDate.parse(value, dateTimeFormatter));
			}

			CellUtil.setCellStyleProperty(
				cell, CellUtil.DATA_FORMAT, dataFormat.getFormat(pattern));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private static final int _CELL_MAX_LENGTH = 32767;

	private static final int _COLUMNS_MAX_COUNT = 256;

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordXLSWriter.class);

}