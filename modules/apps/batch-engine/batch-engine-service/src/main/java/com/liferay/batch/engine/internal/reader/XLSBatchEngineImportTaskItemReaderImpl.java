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
public class XLSBatchEngineImportTaskItemReaderImpl
	implements BatchEngineImportTaskItemReader {

	public XLSBatchEngineImportTaskItemReaderImpl(InputStream inputStream)
		throws IOException {

		_inputStream = inputStream;

		_workbook = new XSSFWorkbook(_inputStream);

		Sheet sheet = _workbook.getSheetAt(0);

		_iterator = sheet.rowIterator();

		Row row = _iterator.next();

		List<String> columnNames = new ArrayList<>();

		for (Cell cell : row) {
			columnNames.add(cell.getStringCellValue());
		}

		_fieldNames = columnNames.toArray(new String[0]);
	}

	@Override
	public void close() throws IOException {
		_inputStream.close();
		_workbook.close();
	}

	@Override
	public Map<String, Object> read() throws Exception {
		if (!_iterator.hasNext()) {
			return null;
		}

		Row row = _iterator.next();

		Map<String, Object> fieldNameValueMap = new HashMap<>();

		int index = 0;

		for (Cell cell : row) {
			String fieldName = _fieldNames[index++];

			if (fieldName == null) {
				continue;
			}

			if (CellType.BOOLEAN == cell.getCellType()) {
				fieldNameValueMap.put(fieldName, cell.getBooleanCellValue());
			}
			else if (CellType.NUMERIC == cell.getCellType()) {
				if (DateUtil.isCellDateFormatted(cell)) {
					fieldNameValueMap.put(fieldName, cell.getDateCellValue());
				}
				else {
					fieldNameValueMap.put(
						fieldName, cell.getNumericCellValue());
				}
			}
			else {
				FieldNameValueMapHandlerFactory.FieldNameValueMapHandler
					handler = FieldNameValueMapHandlerFactory.getHandler(
						fieldName);

				handler.handle(
					fieldName, fieldNameValueMap, cell.getStringCellValue());
			}
		}

		return fieldNameValueMap;
	}

	private final String[] _fieldNames;
	private final InputStream _inputStream;
	private final Iterator<Row> _iterator;
	private final Workbook _workbook;

}