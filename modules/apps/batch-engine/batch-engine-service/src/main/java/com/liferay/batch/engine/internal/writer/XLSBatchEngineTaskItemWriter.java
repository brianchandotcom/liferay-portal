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

import java.io.IOException;
import java.io.OutputStream;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Ivica cardic
 */
public class XLSBatchEngineTaskItemWriter implements BatchEngineTaskItemWriter {

	public XLSBatchEngineTaskItemWriter(OutputStream outputStream) {
		_outputStream = outputStream;
		_columnValueWriter = new ColumnValueWriter();

		_workbook = new XSSFWorkbook();

		_sheet = _workbook.createSheet();
	}

	@Override
	public void close() throws IOException {
		_workbook.write(_outputStream);

		_workbook.close();

		_outputStream.close();
	}

	@Override
	public void write(List<?> items) throws Exception {
		for (Object item : items) {
			_columnValueWriter.write(item, this::_write);
		}
	}

	private void _write(List<?> values) {
		Row row = _sheet.createRow(_rowNum++);

		int column = 0;

		for (Object value : values) {
			Cell cell = row.createCell(column++);

			if (value instanceof Boolean) {
				cell.setCellValue((Boolean)value);
			}
			else if (value instanceof Date) {
				CellStyle cellStyle = _workbook.createCellStyle();

				CreationHelper creationHelper = _workbook.getCreationHelper();

				DataFormat dataFormat = creationHelper.createDataFormat();

				cellStyle.setDataFormat(
					dataFormat.getFormat("yyyy-mm-dd hh:mm:ss"));

				cell.setCellStyle(cellStyle);

				cell.setCellValue((Date)value);
			}
			else if (value instanceof Number) {
				Number number = (Number)value;

				cell.setCellValue(number.doubleValue());
			}
			else {
				cell.setCellValue((String)value);
			}
		}
	}

	private final ColumnValueWriter _columnValueWriter;
	private final OutputStream _outputStream;
	private int _rowNum;
	private final Sheet _sheet;
	private final Workbook _workbook;

}