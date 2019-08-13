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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Ivica Cardic
 */
public class XLSBatchItemReader<T> implements BatchItemReader<T> {

	public XLSBatchItemReader(
			Class<? extends T> itemType, InputStream inputStream)
		throws IOException {

		_itemType = itemType;
		_inputStream = inputStream;

		_workbook = new XSSFWorkbook(_inputStream);

		Sheet sheet = _workbook.getSheetAt(0);

		_rowIterator = sheet.rowIterator();

		Row row = _rowIterator.next();

		List<String> columnNames = new ArrayList<>();

		for (Cell cell : row) {
			columnNames.add(cell.getStringCellValue());
		}

		_columnNames = columnNames.toArray(new String[0]);
	}

	@Override
	public void close() throws IOException {
		_inputStream.close();
		_workbook.close();
	}

	@Override
	public T read() {
		if (!_rowIterator.hasNext()) {
			return null;
		}

		Row row = _rowIterator.next();

		Map<String, Object> columnNameValueMap = new HashMap<>();

		int index = 0;

		for (Cell cell : row) {
			String columnName = _columnNames[index++];

			if (columnName == null) {
				continue;
			}

			if (CellType.BOOLEAN == cell.getCellType()) {
				columnNameValueMap.put(columnName, cell.getBooleanCellValue());
			}
			else if (CellType.NUMERIC == cell.getCellType()) {
				if (DateUtil.isCellDateFormatted(cell)) {
					columnNameValueMap.put(columnName, cell.getDateCellValue());
				}
				else {
					columnNameValueMap.put(
						columnName, cell.getNumericCellValue());
				}
			}
			else {
				String value = cell.getStringCellValue();

				int lastDelimiterIndex = columnName.lastIndexOf('_');

				if (lastDelimiterIndex == -1) {
					columnNameValueMap.put(columnName, value);
				}
				else {
					ColumnUtil.handleLocalizationColumn(
						columnName, value, columnNameValueMap,
						lastDelimiterIndex);
				}
			}
		}

		return _OBJECT_MAPPER.convertValue(columnNameValueMap, _itemType);
	}

	private static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper();

	private final String[] _columnNames;
	private final InputStream _inputStream;
	private final Class<? extends T> _itemType;
	private final Iterator<Row> _rowIterator;
	private final Workbook _workbook;

}