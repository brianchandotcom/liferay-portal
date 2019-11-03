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

package com.liferay.batch.engine.internal.writer;

import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.CharPool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class XLSBatchEngineTaskItemWriterTest
	extends BaseBatchEngineTaskItemWriterTestCase {

	@Test
	public void testWriteRowsWithDefinedFieldNames1() throws Exception {
		_testWriteRows(Arrays.asList("createDate", "description", "id"));
	}

	@Test
	public void testWriteRowsWithDefinedFieldNames2() throws Exception {
		_testWriteRows(
			Arrays.asList(
				"createDate", "description", "id", "name_en", "name_hr"));
	}

	@Test
	public void testWriteRowsWithDefinedFieldNames3() throws Exception {
		_testWriteRows(Arrays.asList("createDate", "id", "name_en"));
	}

	@Test
	public void testWriteRowsWithDefinedFieldNames4() throws Exception {
		_testWriteRows(
			Arrays.asList(
				"id", "name_hr", "name_en", "description", "createDate"));
	}

	@Test
	public void testWriteRowsWithEmptyFieldNames() throws Exception {
		try {
			_testWriteRows(Collections.emptyList());

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}
	}

	private byte[] _getExpectedContent(
			List<String> fieldNames, List<Item> items)
		throws IllegalAccessException, IOException {

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet();

			_populateRow(sheet.createRow(0), workbook, fieldNames);

			for (int i = 0; i < items.size(); i++) {
				Item item = items.get(i);

				List<Object> values = new ArrayList<>();

				for (String fieldName : fieldNames) {
					int index = fieldName.indexOf(CharPool.UNDERLINE);

					if (index == -1) {
						Field field = fieldMap.get(fieldName);

						values.add(field.get(item));
					}
					else {
						Field field = fieldMap.get(
							fieldName.substring(0, index));

						Map<?, ?> valueMap = (Map<?, ?>)field.get(item);

						values.add(
							valueMap.get(fieldName.substring(index + 1)));
					}
				}

				_populateRow(sheet.createRow(i + 1), workbook, values);
			}

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			workbook.write(byteArrayOutputStream);

			return byteArrayOutputStream.toByteArray();
		}
	}

	private Iterator<Row> _getExpectedRowIterator(List<String> fieldNames)
		throws Exception {

		Workbook expectedWorkbook = new XSSFWorkbook(
			new ByteArrayInputStream(
				_getExpectedContent(fieldNames, getItems())));

		Sheet expectedSheet = expectedWorkbook.getSheetAt(0);

		return expectedSheet.rowIterator();
	}

	private void _populateRow(Row row, Workbook workbook, List<?> cellValues) {
		for (int i = 0; i < cellValues.size(); i++) {
			Object value = cellValues.get(i);

			Cell cell = row.createCell(i);

			if (value instanceof Boolean) {
				cell.setCellValue((Boolean)value);
			}
			else if (value instanceof Date) {
				CellStyle cellStyle = workbook.createCellStyle();

				CreationHelper creationHelper = workbook.getCreationHelper();

				DataFormat dataFormat = creationHelper.createDataFormat();

				cellStyle.setDataFormat(
					dataFormat.getFormat("yyyy-mm-dd hh:mm:ss"));

				cell.setCellStyle(cellStyle);

				cell.setCellValue((Date)value);
			}
			else if (value instanceof Number) {
				Number cellValue = (Number)value;

				cell.setCellValue(cellValue.doubleValue());
			}
			else {
				cell.setCellValue((String)value);
			}
		}
	}

	private void _testWriteRows(List<String> fieldNames) throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (XLSBatchEngineTaskItemWriter xlsBatchEngineTaskItemWriter =
				new XLSBatchEngineTaskItemWriter(
					fieldMap, fieldNames, unsyncByteArrayOutputStream)) {

			for (Item[] items : getItemGroups()) {
				xlsBatchEngineTaskItemWriter.write(Arrays.asList(items));
			}
		}

		Workbook actualWorkbook = new XSSFWorkbook(
			new ByteArrayInputStream(
				unsyncByteArrayOutputStream.toByteArray()));

		Sheet actualSheet = actualWorkbook.getSheetAt(0);

		Iterator<Row> actualRowIterator = actualSheet.rowIterator();

		Iterator<Row> expectedRowIterator = _getExpectedRowIterator(fieldNames);

		while (expectedRowIterator.hasNext()) {
			Row actualRow = actualRowIterator.next();
			Row expectedRow = expectedRowIterator.next();

			for (int i = 0; i < expectedRow.getLastCellNum(); i++) {
				Cell actualCell = actualRow.getCell(i);

				Cell expectedCell = expectedRow.getCell(i);

				CellType cellType = expectedCell.getCellType();

				if (cellType == CellType.BOOLEAN) {
					Assert.assertEquals(
						expectedCell.getBooleanCellValue(),
						actualCell.getBooleanCellValue());
				}
				else if (cellType == CellType.NUMERIC) {
					Assert.assertEquals(
						expectedCell.getNumericCellValue(),
						actualCell.getNumericCellValue(), 0);
				}
				else {
					Assert.assertEquals(
						expectedCell.getStringCellValue(),
						actualCell.getStringCellValue());
				}
			}
		}
	}

}