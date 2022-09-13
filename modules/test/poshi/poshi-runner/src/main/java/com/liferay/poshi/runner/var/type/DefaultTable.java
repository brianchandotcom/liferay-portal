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

package com.liferay.poshi.runner.var.type;

import com.liferay.poshi.core.util.ListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Calum Ragan
 */
public class DefaultTable implements Table {

	public DefaultTable(List<List<String>> table) {
		this(table, false, false);
	}

	public DefaultTable(
		List<List<String>> table, boolean hasRowNames, boolean hasColumnNames) {

		if (hasColumnNames && hasRowNames) {
			throw new IllegalArgumentException(
				"Table must contain either row names or column names");
		}

		_table = table;
		_hasRowNames = hasRowNames;
		_hasColumnNames = hasColumnNames;
	}

	public DefaultTable(String rawTableString) {
		this(_parse(rawTableString), false, false);
	}

	public DefaultTable(
		String rawTableString, boolean hasRowNames, boolean hasColumnNames) {

		this(_parse(rawTableString), hasRowNames, hasColumnNames);
	}

	@Override
	public List<String> getColumnByIndex(int index) {
		List<String> columnCellValues = new ArrayList<>();

		for (List<String> cellValues : _table) {
			String cellValue = cellValues.get(index);

			columnCellValues.add(cellValue);
		}

		return columnCellValues;
	}

	@Override
	public List<String> getColumnByName(String columnName) {
		if (_hasColumnNames) {
			List<String> columnCellValues = new ArrayList<>();
			int columnIndex = -1;

			for (List<String> cellValues : _table) {
				for (String cellValue : cellValues) {
					if (cellValue.equals(columnName)) {
						columnIndex = cellValues.indexOf(cellValue);
					}
				}

				columnCellValues.add(cellValues.get(columnIndex));
			}

			columnCellValues.remove(0);

			return columnCellValues;
		}

		throw new RuntimeException("Table does not contain column names");
	}

	@Override
	public List<String> getRowByIndex(int index) {
		return _table.get(index);
	}

	@Override
	public List<String> getRowByName(String rowName) {
		if (_hasRowNames) {
			for (List<String> cellValues : _table) {
				if (cellValues.get(0) == rowName) {
					List<String> newCellValues = new ArrayList<>(cellValues);

					newCellValues.remove(0);

					return newCellValues;
				}
			}
		}

		throw new RuntimeException("Table does not contain row names");
	}

	@Override
	public int getTableRowWidth(List<List<String>> table) {
		if (ListUtil.isEmpty(table)) {
			return 0;
		}

		List<String> cellValues = table.get(0);

		return cellValues.size();
	}

	public int getTableSize() {
		return _table.size();
	}

	@Override
	public Table getTransposedTable(List<List<String>> table) {
		List<List<String>> transposedTable = new ArrayList<>();

		for (int i = 0; i < getTableRowWidth(table); i++) {
			List<String> transposedCellValues = new ArrayList<>();

			for (List<String> cellValues : table) {
				transposedCellValues.add(cellValues.get(i));
			}

			transposedTable.add(transposedCellValues);
		}

		return new DefaultTable(transposedTable);
	}

	private static List<List<String>> _parse(String tableString) {
		Matcher rowMatcher = _rowPattern.matcher(tableString);

		if (!rowMatcher.find()) {
			throw new IllegalArgumentException(
				"Invalid table string:\n" + tableString);
		}

		rowMatcher.reset();

		List<List<String>> table = new ArrayList<>();

		while (rowMatcher.find()) {
			String rowValue = rowMatcher.group("row");

			Matcher entryMatcher = _entryPattern.matcher(rowValue);

			List<String> cellValues = new ArrayList<>();

			while (entryMatcher.find()) {
				String cellValue = entryMatcher.group("entry");

				cellValues.add(cellValue.trim());
			}

			table.add(cellValues);
		}

		return table;
	}

	private static final Pattern _entryPattern = Pattern.compile(
		"(?<entry>.*?)\\|");
	private static final Pattern _rowPattern = Pattern.compile(
		"\\|(?<row>.*\\|)(\\s*\\R)*");

	private boolean _hasColumnNames;
	private boolean _hasRowNames;
	private List<List<String>> _table;

}