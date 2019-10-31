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

package com.liferay.batch.engine.internal.reader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class XLSBatchEngineTaskItemReaderTest
	extends BaseBatchEngineTaskItemReaderTestCase {

	@Test
	public void testColumnMapping() throws Exception {
		try (XLSBatchEngineTaskItemReader xlsBatchEngineTaskItemReader =
				_getXLSBatchEngineTaskItemReader(
					new String[] {
						"createDate1", "description1", "id1", "name1_en",
						"name1_hr"
					},
					new Object[][] {
						{
							createDateString, "sample description", 1,
							"sample name", "naziv"
						}
					})) {

			validate(
				createDateString, "sample description", 1L,
				new HashMap<String, String>() {
					{
						put("createDate1", "createDate");
						put("description1", "description");
						put("id1", "id");
						put("name1", "name");
					}
				},
				xlsBatchEngineTaskItemReader.read(),
				new HashMap<String, String>() {
					{
						put("en", "sample name");
						put("hr", "naziv");
					}
				});
		}
	}

	@Test
	public void testColumnMappingWitUndefinedColumn() throws Exception {
		try (XLSBatchEngineTaskItemReader xlsBatchEngineTaskItemReader =
				_getXLSBatchEngineTaskItemReader(
					new String[] {
						"createDate1", "description1", "id1", "name1_en",
						"name1_hr"
					},
					new Object[][] {
						{
							createDateString, "sample description", 1,
							"sample name", "naziv"
						}
					})) {

			validate(
				createDateString, "sample description", 1L,
				new HashMap<String, String>() {
					{
						put("createDate1", "createDate");
						put("description1", "description");
						put("id1", "id");
					}
				},
				xlsBatchEngineTaskItemReader.read(), null);
		}
	}

	@Test
	public void testColumnMappingWitUndefinedTargetColumn() throws Exception {
		try (XLSBatchEngineTaskItemReader xlsBatchEngineTaskItemReader =
				_getXLSBatchEngineTaskItemReader(
					new String[] {
						"createDate1", "description1", "id1", "name1_en",
						"name1_hr"
					},
					new Object[][] {
						{
							createDateString, "sample description", 1,
							"sample name", "naziv"
						}
					})) {

			validate(
				createDateString, "sample description", 1L,
				new HashMap<String, String>() {
					{
						put("createDate1", "createDate");
						put("description1", "description");
						put("id1", "id");
						put("name1", null);
					}
				},
				xlsBatchEngineTaskItemReader.read(), null);
		}
	}

	@Test
	public void testInvalidColumnMapping() throws Exception {
		try (XLSBatchEngineTaskItemReader xlsBatchEngineTaskItemReader =
				_getXLSBatchEngineTaskItemReader(
					new String[] {
						"createDate1", "description1", "id1", "name1_en",
						"name1_hr"
					},
					new Object[][] {
						{
							createDateString, "sample description", 1,
							"sample name", "naziv"
						}
					})) {

			try {
				validate(
					createDateString, "sample description", null,
					new HashMap<String, String>() {
						{
							put("createDate1", "description");
							put("description1", "createDate");
							put("id1", "id");
							put("name1", "name");
						}
					},
					xlsBatchEngineTaskItemReader.read(),
					new HashMap<String, String>() {
						{
							put("en", "sample name");
							put("hr", "naziv");
						}
					});

				Assert.fail();
			}
			catch (IllegalArgumentException iae) {
			}
		}
	}

	@Test
	public void testReadInvalidRow() throws Exception {
		try (XLSBatchEngineTaskItemReader xlsBatchEngineTaskItemReader =
				_getXLSBatchEngineTaskItemReader(
					FIELD_NAMES,
					new Object[][] {
						{
							new Date(), "sample description", 1L, "sample name",
							"naziv", "unknown column"
						}
					})) {

			try {
				xlsBatchEngineTaskItemReader.read();

				Assert.fail();
			}
			catch (ArrayIndexOutOfBoundsException aioobe) {
			}
		}
	}

	@Test
	public void testReadMultipleRows() throws Exception {
		try (XLSBatchEngineTaskItemReader xlsBatchEngineTaskItemReader =
				_getXLSBatchEngineTaskItemReader(
					FIELD_NAMES,
					new Object[][] {
						{
							createDate, "sample description 1", 1L,
							"sample name 1", "naziv 1"
						},
						{
							createDate, "sample description 2", 2L,
							"sample name 2", "naziv 2"
						}
					})) {

			for (int i = 1; i < 3; i++) {
				long rowCount = i;

				validate(
					createDateString, "sample description " + rowCount,
					rowCount, Collections.emptyMap(),
					xlsBatchEngineTaskItemReader.read(),
					new HashMap<String, String>() {
						{
							put("en", "sample name " + rowCount);
							put("hr", "naziv " + rowCount);
						}
					});
			}
		}
	}

	@Test
	public void testReadRowsWithCommaInsideQuotes() throws Exception {
		try (XLSBatchEngineTaskItemReader xlsBatchEngineTaskItemReader =
				_getXLSBatchEngineTaskItemReader(
					FIELD_NAMES,
					new Object[][] {
						{
							createDate, "hey, here is comma inside", 1L,
							"sample name", "naziv"
						}
					})) {

			validate(
				createDateString, "hey, here is comma inside", 1L,
				Collections.emptyMap(), xlsBatchEngineTaskItemReader.read(),
				new HashMap<String, String>() {
					{
						put("en", "sample name");
						put("hr", "naziv");
					}
				});
		}
	}

	@Test
	public void testReadRowsWithLessValues() throws Exception {
		try (XLSBatchEngineTaskItemReader xlsBatchEngineTaskItemReader =
				_getXLSBatchEngineTaskItemReader(
					FIELD_NAMES, new Object[][] {{null, null, 1}})) {

			validate(
				null, null, 1L, Collections.emptyMap(),
				xlsBatchEngineTaskItemReader.read(), null);
		}
	}

	@Test
	public void testReadRowsWithNullValues() throws Exception {
		try (XLSBatchEngineTaskItemReader xlsBatchEngineTaskItemReader =
				_getXLSBatchEngineTaskItemReader(
					FIELD_NAMES,
					new Object[][] {
						{createDate, null, 1L, null, "naziv"},
						{
							createDate, "sample description 2", 2L,
							"sample name 2", "naziv 2"
						}
					})) {

			validate(
				createDateString, null, 1L, Collections.emptyMap(),
				xlsBatchEngineTaskItemReader.read(),
				new HashMap<String, String>() {
					{
						put("en", null);
						put("hr", "naziv");
					}
				});

			validate(
				createDateString, "sample description 2", 2L,
				Collections.emptyMap(), xlsBatchEngineTaskItemReader.read(),
				new HashMap<String, String>() {
					{
						put("en", "sample name 2");
						put("hr", "naziv 2");
					}
				});
		}
	}

	private byte[] _getContent(String[] cellNames, Object[][] rowValues)
		throws IOException {

		try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook()) {
			Sheet sheet = xssfWorkbook.createSheet();

			_populateRow(sheet.createRow(0), xssfWorkbook, (Object[])cellNames);

			for (int i = 0; i < rowValues.length; i++) {
				_populateRow(
					sheet.createRow(i + 1), xssfWorkbook, rowValues[i]);
			}

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			xssfWorkbook.write(byteArrayOutputStream);

			return byteArrayOutputStream.toByteArray();
		}
	}

	private XLSBatchEngineTaskItemReader _getXLSBatchEngineTaskItemReader(
			String[] cellNames, Object[][] rowValues)
		throws IOException {

		return new XLSBatchEngineTaskItemReader(
			new ByteArrayInputStream(_getContent(cellNames, rowValues)));
	}

	private void _populateRow(
		Row row, XSSFWorkbook xssfWorkbook, Object... cellValues) {

		for (int i = 0; i < cellValues.length; i++) {
			Cell cell = row.createCell(i);

			if (cellValues[i] instanceof Boolean) {
				cell.setCellValue((Boolean)cellValues[i]);
			}
			else if (cellValues[i] instanceof Date) {
				CellStyle cellStyle = xssfWorkbook.createCellStyle();

				CreationHelper creationHelper =
					xssfWorkbook.getCreationHelper();

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