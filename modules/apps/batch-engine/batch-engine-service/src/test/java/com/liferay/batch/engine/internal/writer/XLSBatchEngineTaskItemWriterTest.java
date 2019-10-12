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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Arrays;
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
	public void testWriteRows() throws Exception {
		Iterator<Row> expectedRowIterator = _getExpectedRowIterator();

		Iterator<Row> actualRowIterator = _getActualRowIterator();

		while (expectedRowIterator.hasNext()) {
			Row expectedRow = expectedRowIterator.next();
			Row actualRow = actualRowIterator.next();

			for (int i = 0; i < expectedRow.getLastCellNum(); i++) {
				Cell expectedCell = expectedRow.getCell(i);
				Cell actualCell = actualRow.getCell(i);

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

	private Iterator<Row> _getActualRowIterator() throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (XLSBatchEngineTaskItemWriter xlsBatchEngineTaskItemWriter =
				new XLSBatchEngineTaskItemWriter(unsyncByteArrayOutputStream)) {

			for (Item[] items : getItemGroups()) {
				xlsBatchEngineTaskItemWriter.write(Arrays.asList(items));
			}
		}

		Workbook actualWorkbook = new XSSFWorkbook(
			new ByteArrayInputStream(
				unsyncByteArrayOutputStream.toByteArray()));

		Sheet actualSheet = actualWorkbook.getSheetAt(0);

		return actualSheet.rowIterator();
	}

	private byte[] _getExpectedContent(List<Item> items) throws IOException {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet();

			_populateRow(sheet.createRow(0), workbook, (Object[])CELL_NAMES);

			for (int i = 0; i < items.size(); i++) {
				Item item = items.get(i);

				Map<String, String> name = item.getName();

				_populateRow(
					sheet.createRow(i + 1), workbook, item.getCreateDate(),
					item.getDescription(), item.getId(), name.get("en"),
					name.get("hr"));
			}

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			workbook.write(byteArrayOutputStream);

			return byteArrayOutputStream.toByteArray();
		}
	}

	private Iterator<Row> _getExpectedRowIterator() throws IOException {
		Workbook expectedWorkbook = new XSSFWorkbook(
			new ByteArrayInputStream(_getExpectedContent(getItems())));

		Sheet expectedSheet = expectedWorkbook.getSheetAt(0);

		return expectedSheet.rowIterator();
	}

	private void _populateRow(
		Row row, Workbook workbook, Object... cellValues) {

		for (int i = 0; i < cellValues.length; i++) {
			Cell cell = row.createCell(i);

			if (cellValues[i] instanceof Boolean) {
				cell.setCellValue((Boolean)cellValues[i]);
			}
			else if (cellValues[i] instanceof Date) {
				CellStyle cellStyle = workbook.createCellStyle();

				CreationHelper creationHelper = workbook.getCreationHelper();

				DataFormat dataFormat = creationHelper.createDataFormat();

				cellStyle.setDataFormat(
					dataFormat.getFormat("yyyy-mm-dd hh:mm:ss"));

				cell.setCellStyle(cellStyle);

				cell.setCellValue((Date)cellValues[i]);
			}
			else if (cellValues[i] instanceof Number) {
				Number value = (Number)cellValues[i];

				cell.setCellValue(value.doubleValue());
			}
			else {
				cell.setCellValue((String)cellValues[i]);
			}
		}
	}

}