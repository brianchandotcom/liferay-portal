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

package com.liferay.dynamic.data.lists.exporter.impl;

import com.liferay.dynamic.data.lists.exporter.DDLExporter;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.ByteArrayOutputStream;

import java.util.Iterator;
import java.util.List;

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
@Component(immediate = true, service = DDLExporter.class)
public class DDLXLSExporter extends BaseDDLExporter {

	@Override
	public String getFormat() {
		return "xls";
	}

	protected void createDataRow(
		int rowIndex, Sheet sheet, String status, CellStyle style,
		List<DDMFormFieldRenderedValue> values) {

		Row row = sheet.createRow(rowIndex);

		int cellIndex = 0;

		Cell cell = null;

		for (DDMFormFieldRenderedValue value : values) {
			cell = row.createCell(cellIndex++, CellType.STRING);

			cell.setCellStyle(style);
			cell.setCellValue(GetterUtil.getString(value.getValue()));
		}

		cell = row.createCell(cellIndex++, CellType.STRING);

		cell.setCellStyle(style);
		cell.setCellValue(status);
	}

	protected void createHeaderRow(
		List<DDMFormField> ddmFormFields, Sheet sheet, Workbook workbook) {

		Row row = sheet.createRow(0);

		Font font = workbook.createFont();

		font.setBold(true);
		font.setFontHeightInPoints((short)14);
		font.setFontName("Courier New");

		CellStyle style = workbook.createCellStyle();

		style.setFont(font);

		int cellIndex = 0;

		Cell cell = null;

		for (DDMFormField ddmFormField : ddmFormFields) {
			LocalizedValue label = ddmFormField.getLabel();

			cell = row.createCell(cellIndex++, CellType.STRING);

			cell.setCellStyle(style);
			cell.setCellValue(label.getString(getLocale()));
		}

		cell = row.createCell(cellIndex++, CellType.STRING);

		cell.setCellStyle(style);
		cell.setCellValue(LanguageUtil.get(getLocale(), "status"));
	}

	@Override
	protected byte[] doExport(
			long recordSetId, int status, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator)
		throws Exception {

		DDLRecordSet recordSet = _ddlRecordSetService.getRecordSet(recordSetId);

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		List<DDMFormField> ddmFormFields = getDDMFormFields(ddmStructure);

		try (ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();
			Workbook workbook = new HSSFWorkbook()) {

			Sheet sheet = workbook.createSheet();

			createHeaderRow(ddmFormFields, sheet, workbook);

			List<DDLRecord> records = _ddlRecordLocalService.getRecords(
				recordSetId, status, start, end, orderByComparator);

			Iterator<DDLRecord> iterator = records.iterator();

			int rowIndex = 1;

			Font font = workbook.createFont();

			font.setFontHeightInPoints((short)12);
			font.setFontName("Courier New");

			CellStyle style = workbook.createCellStyle();

			style.setFont(font);

			while (iterator.hasNext()) {
				DDLRecord record = iterator.next();

				DDLRecordVersion recordVersion = record.getRecordVersion();

				DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
					recordVersion.getDDMStorageId());

				List<DDMFormFieldRenderedValue> values = getRenderedValues(
					recordSet.getScope(), ddmFormFields, ddmFormValues,
					ddmStructure);

				String statusString = getStatusMessage(
					recordVersion.getStatus());

				createDataRow(rowIndex++, sheet, statusString, style, values);
			}

			workbook.write(byteArrayOutputStream);

			return byteArrayOutputStream.toByteArray();
		}
		catch (Exception e) {
			return new byte[0];
		}
	}

	@Override
	protected
		DDMFormFieldTypeServicesTracker getDDMFormFieldTypeServicesTracker() {

		return _ddmFormFieldTypeServicesTracker;
	}

	@Override
	protected
		DDMFormValuesToFieldsConverter getDDMFormValuesToFieldsConverter() {

		return _ddmFormValuesToFieldsConverter;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordLocalService(
		DDLRecordLocalService ddlRecordLocalService) {

		_ddlRecordLocalService = ddlRecordLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordSetService(
		DDLRecordSetService ddlRecordSetService) {

		_ddlRecordSetService = ddlRecordSetService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesToFieldsConverter(
		DDMFormValuesToFieldsConverter ddmFormValuesToFieldsConverter) {

		_ddmFormValuesToFieldsConverter = ddmFormValuesToFieldsConverter;
	}

	@Reference(unbind = "-")
	protected void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	private DDLRecordLocalService _ddlRecordLocalService;
	private DDLRecordSetService _ddlRecordSetService;
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;
	private StorageEngine _storageEngine;

}