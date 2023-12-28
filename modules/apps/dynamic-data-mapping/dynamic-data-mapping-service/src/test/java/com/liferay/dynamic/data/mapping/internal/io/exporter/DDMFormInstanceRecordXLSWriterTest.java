/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.io.exporter;

import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterRequest;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.junit.Assert;
import org.junit.BeforeClass;
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

	@BeforeClass
	public static void setUpClass() {
		Mockito.when(
			_workbook.createCellStyle()
		).thenReturn(
			_cellStyle
		);

		Mockito.when(
			_workbook.createFont()
		).thenReturn(
			_font
		);

		Mockito.when(
			_workbook.createSheet()
		).thenReturn(
			_sheet
		);

		Mockito.when(
			_sheet.createRow(Mockito.anyInt())
		).thenReturn(
			_row
		);

		CreationHelper creationHelper = Mockito.mock(CreationHelper.class);

		Mockito.when(
			_workbook.getCreationHelper()
		).thenReturn(
			creationHelper
		);

		Mockito.when(
			creationHelper.createDataFormat()
		).thenReturn(
			_dataFormat
		);
	}

	@Test
	public void testCreateCellStyle() {
		_ddmFormInstanceRecordXLSWriter.createCellStyle(
			_workbook, false, "Courier New", (short)12);

		InOrder inOrder = Mockito.inOrder(_workbook, _font, _cellStyle);

		inOrder.verify(
			_workbook, Mockito.times(1)
		).createFont();

		inOrder.verify(
			_font, Mockito.times(1)
		).setBold(
			false
		);

		inOrder.verify(
			_font, Mockito.times(1)
		).setFontHeightInPoints(
			(short)12
		);

		inOrder.verify(
			_font, Mockito.times(1)
		).setFontName(
			"Courier New"
		);

		inOrder.verify(
			_workbook, Mockito.times(1)
		).createCellStyle();

		inOrder.verify(
			_cellStyle, Mockito.times(1)
		).setFont(
			_font
		);
	}

	@Test
	public void testCreateRow() {
		Cell cell1 = Mockito.mock(Cell.class);

		Mockito.when(
			_row.createCell(0)
		).thenReturn(
			cell1
		);

		Cell cell2 = Mockito.mock(Cell.class);

		Mockito.when(
			_row.createCell(1)
		).thenReturn(
			cell2
		);

		_ddmFormInstanceRecordXLSWriter.createRow(
			_cellStyle, _dataFormat, null, _row,
			LinkedHashMapBuilder.put(
				RandomTestUtil.randomString(), "value1"
			).put(
				RandomTestUtil.randomString(), "value2"
			).build());

		InOrder inOrder = Mockito.inOrder(_sheet, _row, cell1, cell2);

		inOrder.verify(
			_row, Mockito.times(1)
		).createCell(
			0
		);

		inOrder.verify(
			cell1, Mockito.times(1)
		).setCellStyle(
			_cellStyle
		);

		inOrder.verify(
			cell1, Mockito.times(1)
		).setCellValue(
			"value1"
		);

		inOrder.verify(
			_row, Mockito.times(1)
		).createCell(
			1
		);

		inOrder.verify(
			cell2, Mockito.times(1)
		).setCellStyle(
			_cellStyle
		);

		inOrder.verify(
			cell2, Mockito.times(1)
		).setCellValue(
			"value2"
		);
	}

	@Test
	public void testWrite() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = Mockito.mock(
			ByteArrayOutputStream.class);

		Mockito.when(
			byteArrayOutputStream.toByteArray()
		).thenReturn(
			new byte[] {1, 2, 3}
		);

		Mockito.doNothing(
		).when(
			_workbook
		).write(
			byteArrayOutputStream
		);

		DDMFormInstanceRecordXLSWriter ddmFormInstanceRecordXLSWriter =
			Mockito.mock(DDMFormInstanceRecordXLSWriter.class);

		Mockito.when(
			ddmFormInstanceRecordXLSWriter.createByteArrayOutputStream()
		).thenReturn(
			byteArrayOutputStream
		);

		Mockito.when(
			ddmFormInstanceRecordXLSWriter.createCellStyle(
				Mockito.any(Workbook.class), Mockito.anyBoolean(),
				Mockito.anyString(), Mockito.anyShort())
		).thenReturn(
			_cellStyle
		);

		Mockito.when(
			ddmFormInstanceRecordXLSWriter.createWorkbook()
		).thenReturn(
			_workbook
		);

		Mockito.when(
			ddmFormInstanceRecordXLSWriter.write(
				Mockito.any(DDMFormInstanceRecordWriterRequest.class))
		).thenCallRealMethod();

		DDMFormInstanceRecordWriterResponse
			ddmFormInstanceRecordWriterResponse =
				ddmFormInstanceRecordXLSWriter.write(
					DDMFormInstanceRecordWriterRequest.Builder.newBuilder(
						Collections.emptyMap(),
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
						}
					).build());

		Assert.assertArrayEquals(
			new byte[] {1, 2, 3},
			ddmFormInstanceRecordWriterResponse.getContent());

		InOrder inOrder = Mockito.inOrder(
			ddmFormInstanceRecordXLSWriter, _workbook, byteArrayOutputStream);

		inOrder.verify(
			_workbook, Mockito.times(1)
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
			Mockito.any(CellStyle.class), Mockito.any(DataFormat.class),
			Mockito.nullable(Map.class), Mockito.any(Row.class),
			Mockito.anyMap()
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
			Mockito.any(CellStyle.class), Mockito.any(DataFormat.class),
			Mockito.nullable(Map.class), Mockito.any(Row.class),
			Mockito.anyMap()
		);

		inOrder.verify(
			_workbook, Mockito.times(1)
		).write(
			byteArrayOutputStream
		);

		inOrder.verify(
			byteArrayOutputStream, Mockito.times(1)
		).toByteArray();
	}

	private static final CellStyle _cellStyle = Mockito.mock(CellStyle.class);
	private static final DataFormat _dataFormat = Mockito.mock(
		DataFormat.class);
	private static final Font _font = Mockito.mock(Font.class);
	private static final Row _row = Mockito.mock(Row.class);
	private static final Sheet _sheet = Mockito.mock(Sheet.class);
	private static final Workbook _workbook = Mockito.mock(Workbook.class);

	private final DDMFormInstanceRecordXLSWriter
		_ddmFormInstanceRecordXLSWriter = new DDMFormInstanceRecordXLSWriter();

}