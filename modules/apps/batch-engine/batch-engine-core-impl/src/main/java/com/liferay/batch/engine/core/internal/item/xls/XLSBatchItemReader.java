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

package com.liferay.batch.engine.core.internal.item.xls;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.batch.engine.core.internal.item.BaseBatchItemReader;
import com.liferay.batch.engine.core.item.BatchItemReader;
import com.liferay.batch.engine.core.item.ParseException;
import com.liferay.batch.engine.core.item.file.IncorrectTokenCountException;

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
public class XLSBatchItemReader<T>
	extends BaseBatchItemReader<T> implements BatchItemReader<T> {

	public XLSBatchItemReader(
		Class<? extends T> itemType, String[] columnNames, int linesToSkip,
		InputStream inputStream) {

		_itemType = itemType;
		_columnNames = columnNames;
		_linesToSkip = linesToSkip;
		_inputStream = inputStream;
	}

	@Override
	protected void doClose() throws Exception {
		_inputStream.close();
		_workbook.close();
	}

	@Override
	protected void doOpen() throws Exception {
		_workbook = new XSSFWorkbook(_inputStream);

		Sheet sheet = _workbook.getSheetAt(0);

		_rowIterator = sheet.rowIterator();
	}

	@Override
	protected T doRead() throws Exception, ParseException {
		if (_columnNames == null) {
			Row row = _rowIterator.next();

			List<String> columnNames = new ArrayList<>();

			for (Cell cell : row) {
				columnNames.add(cell.getStringCellValue());
			}

			_columnNames = columnNames.toArray(new String[0]);

			_linesToSkip--;
		}

		while (_linesToSkip-- > 0) {
			if (!_rowIterator.hasNext()) {
				return null;
			}

			_rowIterator.next();
		}

		if (!_rowIterator.hasNext()) {
			return null;
		}

		Row row = _rowIterator.next();

		Map<String, Object> map = new HashMap<>();

		int index = 0;

		for (Cell cell : row) {
			String columnName = _columnNames[index++];

			if (columnName == null) {
				continue;
			}

			if (CellType.BOOLEAN == cell.getCellType()) {
				map.put(columnName, cell.getBooleanCellValue());
			}
			else if (CellType.NUMERIC == cell.getCellType()) {
				if (DateUtil.isCellDateFormatted(cell)) {
					map.put(columnName, cell.getDateCellValue());
				}
				else {
					map.put(columnName, cell.getNumericCellValue());
				}
			}
			else {
				String value = cell.getStringCellValue();

				int lastDelimiterIndex = columnName.lastIndexOf("_");

				if (lastDelimiterIndex != -1) {
					_handleLocalizationColumn(
						columnName, value, map, lastDelimiterIndex);
				}
				else {
					map.put(columnName, value);
				}
			}
		}

		if (map.size() != _columnNames.length) {
			throw new IncorrectTokenCountException(
				_columnNames.length, map.size(), row.toString());
		}

		return _OBJECT_MAPPER.convertValue(map, _itemType);
	}

	@SuppressWarnings("unchecked")
	private void _handleLocalizationColumn(
		String columnName, String value, Map<String, Object> map,
		int lastDelimiterIndex) {

		String language = columnName.substring(lastDelimiterIndex + 1);

		columnName = columnName.substring(0, lastDelimiterIndex);

		Map<String, String> localizationMap = (Map<String, String>)map.get(
			columnName);

		if (localizationMap == null) {
			localizationMap = new HashMap<>();

			map.put(columnName, localizationMap);
		}

		localizationMap.put(language, value);
	}

	private static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper();

	private String[] _columnNames;
	private final InputStream _inputStream;
	private final Class<? extends T> _itemType;
	private int _linesToSkip;
	private Iterator<Row> _rowIterator;
	private Workbook _workbook;

}