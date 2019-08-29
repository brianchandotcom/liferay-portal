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

package com.liferay.dynamic.data.mapping.internal.io.exporter;

import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterResponse;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Leonardo Barros
 */
@RunWith(MockitoJUnitRunner.class)
public class DDMFormInstanceRecordXLSWriterTest extends PowerMockito {

	@Test
	public void testCreateCellStyle() {
		DDMFormInstanceRecordXLSWriter ddmFormInstanceRecordXLSWriter =
			new DDMFormInstanceRecordXLSWriter();

		Workbook workbook = mock(Workbook.class);

		Font font = mock(Font.class);

		when(
			workbook.createFont()
		).thenReturn(
			font
		);

		CellStyle cellStyle = mock(CellStyle.class);

		when(
			workbook.createCellStyle()
		).thenReturn(
			cellStyle
		);

		ddmFormInstanceRecordXLSWriter.createCellStyle(
			workbook, false, "Courier New", (short)12);

		InOrder inOrder = Mockito.inOrder(workbook, font, cellStyle);

		inOrder.verify(
			workbook, Mockito.times(1)
		).createFont();

		inOrder.verify(
			font, Mockito.times(1)
		).setBold(
			false
		);

		inOrder.verify(
			font, Mockito.times(1)
		).setFontHeightInPoints(
			(short)12
		);

		inOrder.verify(
			font, Mockito.times(1)
		).setFontName(
			"Courier New"
		);

		inOrder.verify(
			workbook, Mockito.times(1)
		).createCellStyle();

		inOrder.verify(
			cellStyle, Mockito.times(1)
		).setFont(
			font
		);
	}

	@Test
	public void testCreateRow() {
		DDMFormInstanceRecordXLSWriter ddmFormInstanceRecordXLSWriter =
			new DDMFormInstanceRecordXLSWriter();

		CellStyle cellStyle = mock(CellStyle.class);

		Sheet sheet = mock(Sheet.class);

		Row row = mock(Row.class);

		when(
			sheet.createRow(0)
		).thenReturn(
			row
		);

		Cell cell1 = mock(Cell.class);

		when(
			row.createCell(0, CellType.STRING)
		).thenReturn(
			cell1
		);

		Cell cell2 = mock(Cell.class);

		when(
			row.createCell(1, CellType.STRING)
		).thenReturn(
			cell2
		);

		ddmFormInstanceRecordXLSWriter.createRow(
			0, cellStyle, Arrays.asList("value1", "value2"), sheet);

		InOrder inOrder = Mockito.inOrder(sheet, row, cell1, cell2);

		inOrder.verify(
			sheet, Mockito.times(1)
		).createRow(
			0
		);

		inOrder.verify(
			row, Mockito.times(1)
		).createCell(
			0, CellType.STRING
		);

		inOrder.verify(
			cell1, Mockito.times(1)
		).setCellStyle(
			cellStyle
		);

		inOrder.verify(
			cell1, Mockito.times(1)
		).setCellValue(
			"value1"
		);

		inOrder.verify(
			row, Mockito.times(1)
		).createCell(
			1, CellType.STRING
		);

		inOrder.verify(
			cell2, Mockito.times(1)
		).setCellStyle(
			cellStyle
		);

		inOrder.verify(
			cell2, Mockito.times(1)
		).setCellValue(
			"value2"
		);
	}

	@Test
	public void testWrite() throws Exception {
		Map<String, String> ddmFormFieldsLabel = Collections.emptyMap();

		List<Map<String, String>> ddmFormFieldValues =
			new ArrayList<Map<String, String>>() {
				{
					Map<String, String> map1 = new HashMap<String, String>() {
						{
							put("field1", "2");
						}
					};

					add(map1);

					Map<String, String> map2 = new HashMap<String, String>() {
						{
							put("field1", "1");
						}
					};

					add(map2);
				}
			};

		DDMFormInstanceRecordWriterRequest.Builder builder =
			DDMFormInstanceRecordWriterRequest.Builder.newBuilder(
				ddmFormFieldsLabel, ddmFormFieldValues);

		DDMFormInstanceRecordWriterRequest ddmFormInstanceRecordWriterRequest =
			builder.build();

		DDMFormInstanceRecordXLSWriter ddmFormInstanceRecordXLSWriter = mock(
			DDMFormInstanceRecordXLSWriter.class);

		ByteArrayOutputStream byteArrayOutputStream = mock(
			ByteArrayOutputStream.class);

		when(
			ddmFormInstanceRecordXLSWriter.createByteArrayOutputStream()
		).thenReturn(
			byteArrayOutputStream
		);

		when(
			byteArrayOutputStream.toByteArray()
		).thenReturn(
			new byte[] {1, 2, 3}
		);

		Workbook workbook = mock(Workbook.class);

		when(
			ddmFormInstanceRecordXLSWriter.createWorkbook()
		).thenReturn(
			workbook
		);

		Mockito.doNothing(
		).when(
			workbook
		).write(
			byteArrayOutputStream
		);

		when(
			ddmFormInstanceRecordXLSWriter.write(
				ddmFormInstanceRecordWriterRequest)
		).thenCallRealMethod();

		DDMFormInstanceRecordWriterResponse
			ddmFormInstanceRecordWriterResponse =
				ddmFormInstanceRecordXLSWriter.write(builder.build());

		Assert.assertArrayEquals(
			new byte[] {1, 2, 3},
			ddmFormInstanceRecordWriterResponse.getContent());

		InOrder inOrder = Mockito.inOrder(
			ddmFormInstanceRecordXLSWriter, workbook, byteArrayOutputStream);

		inOrder.verify(
			workbook, Mockito.times(1)
		).createSheet();

		inOrder.verify(
			ddmFormInstanceRecordXLSWriter, Mockito.times(1)
		).createCellStyle(
			Matchers.any(Workbook.class), Matchers.anyBoolean(),
			Matchers.anyString(), Matchers.anyByte()
		);

		inOrder.verify(
			ddmFormInstanceRecordXLSWriter, Mockito.times(1)
		).createRow(
			Matchers.anyInt(), Matchers.any(CellStyle.class),
			Matchers.anyCollection(), Matchers.any(Sheet.class)
		);

		inOrder.verify(
			ddmFormInstanceRecordXLSWriter, Mockito.times(1)
		).createCellStyle(
			Matchers.any(Workbook.class), Matchers.anyBoolean(),
			Matchers.anyString(), Matchers.anyByte()
		);

		inOrder.verify(
			ddmFormInstanceRecordXLSWriter, Mockito.times(2)
		).createRow(
			Matchers.anyInt(), Matchers.any(CellStyle.class),
			Matchers.anyCollection(), Matchers.any(Sheet.class)
		);

		inOrder.verify(
			workbook, Mockito.times(1)
		).write(
			byteArrayOutputStream
		);

		inOrder.verify(
			byteArrayOutputStream, Mockito.times(1)
		).toByteArray();
	}

}