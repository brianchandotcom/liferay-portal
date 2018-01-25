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

package com.liferay.dynamic.data.mapping.io.internal.exporter;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormExporter;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.ByteArrayOutputStream;

import java.time.format.DateTimeFormatter;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DDMFormExporter.class)
public class DDMFormXLSExporter extends BaseDDMFormExporter {

	@Override
	public DDMFormFieldTypeServicesTracker
		getDDMFormFieldTypeServicesTracker() {

		return _ddmFormFieldTypeServicesTracker;
	}

	@Override
	public DDMFormInstanceVersionLocalService
		getDDMFormInstanceVersionLocalService() {

		return _ddmFormInstanceVersionService;
	}

	@Override
	public String getFormat() {
		return "xls";
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

	protected void createDataRow(
		int rowIndex, Sheet sheet, DateTimeFormatter dateTimeFormatter,
		String author, String status, Date statusDate, CellStyle style,
		Map<String, DDMFormField> ddmFormFields,
		Map<String, DDMFormFieldRenderedValue> values) {

		Row row = sheet.createRow(rowIndex);

		int cellIndex = 0;

		Cell cell = null;

		for (Map.Entry<String, DDMFormField> entry : ddmFormFields.entrySet()) {
			cell = row.createCell(cellIndex++, CellType.STRING);

			cell.setCellStyle(style);

			if (values.containsKey(entry.getKey())) {
				DDMFormFieldRenderedValue ddmFormFieldRenderedValue =
					values.get(entry.getKey());

				cell.setCellValue(
					GetterUtil.getString(ddmFormFieldRenderedValue.getValue()));
			}
			else {
				cell.setCellValue(StringPool.BLANK);
			}
		}

		cell = row.createCell(cellIndex++, CellType.STRING);

		cell.setCellStyle(style);
		cell.setCellValue(status);

		cell = row.createCell(cellIndex++, CellType.STRING);

		cell.setCellStyle(style);
		cell.setCellValue(formatDate(statusDate, dateTimeFormatter));

		cell = row.createCell(cellIndex++, CellType.STRING);

		cell.setCellStyle(style);
		cell.setCellValue(author);
	}

	protected void createHeaderRow(
		Collection<DDMFormField> ddmFormFields, Sheet sheet,
		Workbook workbook) {

		Row row = sheet.createRow(0);

		CellStyle cellStyle = createCellStyle(
			workbook, true, "Courier New", (short)14);

		int cellIndex = 0;

		Cell cell = null;

		Locale locale = getLocale();

		for (DDMFormField ddmFormField : ddmFormFields) {
			LocalizedValue label = ddmFormField.getLabel();

			cell = row.createCell(cellIndex++, CellType.STRING);

			cell.setCellStyle(cellStyle);
			cell.setCellValue(label.getString(locale));
		}

		cell = row.createCell(cellIndex++, CellType.STRING);

		cell.setCellStyle(cellStyle);
		cell.setCellValue(LanguageUtil.get(locale, "status"));

		cell = row.createCell(cellIndex++, CellType.STRING);

		cell.setCellStyle(cellStyle);
		cell.setCellValue(LanguageUtil.get(locale, "modified-date"));

		cell = row.createCell(cellIndex++, CellType.STRING);

		cell.setCellStyle(cellStyle);
		cell.setCellValue(LanguageUtil.get(locale, "author"));
	}

	@Override
	protected byte[] doExport(
			long formInstanceId, int status, int start, int end,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws Exception {

		Map<String, DDMFormField> ddmFormFields = getDistinctFields(
			formInstanceId);

		DateTimeFormatter dateTimeFormatter = getDateTimeFormatter();

		try (ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();
			Workbook workbook = new HSSFWorkbook()) {

			Sheet sheet = workbook.createSheet();

			createHeaderRow(ddmFormFields.values(), sheet, workbook);

			List<DDMFormInstanceRecord> formInstanceRecords =
				_ddmFormInstanceRecordLocalService.getFormInstanceRecords(
					formInstanceId, status, start, end, orderByComparator);

			Iterator<DDMFormInstanceRecord> iterator =
				formInstanceRecords.iterator();

			int rowIndex = 1;

			CellStyle cellStyle = createCellStyle(
				workbook, false, "Courier New", (short)12);

			while (iterator.hasNext()) {
				DDMFormInstanceRecord formInstanceRecord = iterator.next();

				DDMFormInstanceRecordVersion formInstanceRecordVersion =
					formInstanceRecord.getFormInstanceRecordVersion();

				DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
					formInstanceRecordVersion.getStorageId());

				Map<String, DDMFormFieldRenderedValue> values =
					getRenderedValues(ddmFormFields.values(), ddmFormValues);

				createDataRow(
					rowIndex++, sheet, dateTimeFormatter,
					formInstanceRecordVersion.getUserName(),
					getStatusMessage(formInstanceRecordVersion.getStatus()),
					formInstanceRecordVersion.getStatusDate(), cellStyle,
					ddmFormFields, values);
			}

			workbook.write(byteArrayOutputStream);

			return byteArrayOutputStream.toByteArray();
		}
		catch (Exception e) {
			return new byte[0];
		}
	}

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	@Reference
	private DDMFormInstanceVersionLocalService _ddmFormInstanceVersionService;

	@Reference
	private StorageEngine _storageEngine;

}