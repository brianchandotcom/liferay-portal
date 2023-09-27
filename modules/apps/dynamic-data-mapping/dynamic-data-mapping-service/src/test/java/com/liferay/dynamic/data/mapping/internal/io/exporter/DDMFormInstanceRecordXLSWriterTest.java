/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.io.exporter;

import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterResponse;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.InOrder;
import org.mockito.Mockito;

/**
 * @author Leonardo Barros
 */
public class DDMFormInstanceRecordXLSWriterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreateCellStyle() {
		DDMFormInstanceRecordXLSWriter ddmFormInstanceRecordXLSWriter =
			new DDMFormInstanceRecordXLSWriter();

		Workbook workbook = Mockito.mock(Workbook.class);

		Font font = Mockito.mock(Font.class);

		Mockito.when(
			workbook.createFont()
		).thenReturn(
			font
		);

		CellStyle cellStyle = Mockito.mock(CellStyle.class);

		Mockito.when(
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

		CellStyle cellStyle = Mockito.mock(CellStyle.class);

		Sheet sheet = Mockito.mock(Sheet.class);

		Row row = Mockito.mock(Row.class);

		Mockito.when(
			sheet.createRow(0)
		).thenReturn(
			row
		);

		Cell cell1 = Mockito.mock(Cell.class);

		Mockito.when(
			row.createCell(0, CellType.STRING)
		).thenReturn(
			cell1
		);

		Cell cell2 = Mockito.mock(Cell.class);

		Mockito.when(
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
					add(
						HashMapBuilder.put(
							"field1", "2"
						).build());

					add(
						HashMapBuilder.put(
							"field1", "1"
						).build());
				}
			};

		DDMFormInstanceRecordWriterRequest.Builder builder =
			DDMFormInstanceRecordWriterRequest.Builder.newBuilder(
				ddmFormFieldsLabel, ddmFormFieldValues);

		DDMFormInstanceRecordWriterRequest ddmFormInstanceRecordWriterRequest =
			builder.build();

		DDMFormInstanceRecordXLSWriter ddmFormInstanceRecordXLSWriter =
			Mockito.mock(DDMFormInstanceRecordXLSWriter.class);

		ByteArrayOutputStream byteArrayOutputStream = Mockito.mock(
			ByteArrayOutputStream.class);

		Mockito.when(
			byteArrayOutputStream.toByteArray()
		).thenReturn(
			new byte[] {1, 2, 3}
		);

		Mockito.when(
			ddmFormInstanceRecordXLSWriter.createByteArrayOutputStream()
		).thenReturn(
			byteArrayOutputStream
		);

		Workbook workbook = Mockito.mock(Workbook.class);

		Mockito.when(
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

		CellStyle cellStyle = Mockito.mock(CellStyle.class);

		Mockito.when(
			ddmFormInstanceRecordXLSWriter.createCellStyle(
				Mockito.any(Workbook.class), Mockito.anyBoolean(),
				Mockito.anyString(), Mockito.anyShort())
		).thenReturn(
			cellStyle
		);

		Mockito.when(
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
			Mockito.any(Workbook.class), Mockito.anyBoolean(),
			Mockito.anyString(), Mockito.anyShort()
		);

		inOrder.verify(
			ddmFormInstanceRecordXLSWriter, Mockito.times(1)
		).createRow(
			Mockito.anyInt(), Mockito.nullable(CellStyle.class),
			Mockito.anyCollection(), Mockito.nullable(Sheet.class)
		);

		inOrder.verify(
			ddmFormInstanceRecordXLSWriter, Mockito.times(1)
		).createCellStyle(
			Mockito.any(Workbook.class), Mockito.anyBoolean(),
			Mockito.anyString(), Mockito.anyShort()
		);

		inOrder.verify(
			ddmFormInstanceRecordXLSWriter, Mockito.times(2)
		).createRow(
			Mockito.anyInt(), Mockito.nullable(CellStyle.class),
			Mockito.anyCollection(), Mockito.nullable(Sheet.class)
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