/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.var.type;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Calum Ragan
 */
public class DefaultTable implements Table {

	public DefaultTable(List<List<String>> rows) {
		this(rows, false, false);
	}

	public DefaultTable(
		List<List<String>> rows, boolean hasRowNames, boolean hasColumnNames) {

		if (hasColumnNames && hasRowNames) {
			throw new IllegalArgumentException(
				"Table must contain either row names or column names");
		}

		_rows = rows;
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

		for (List<String> row : _rows) {
			columnCellValues.add(row.get(index));
		}

		return columnCellValues;
	}

	@Override
	public List<String> getColumnByName(String columnName) {
		if (_hasColumnNames) {
			List<String> columnNames = _rows.get(0);

			if (!columnNames.contains(columnName)) {
				throw new RuntimeException(
					"Table does not contain column name: " + columnName);
			}

			int columnIndex = columnNames.indexOf(columnName);

			return _getListWithoutTitle(
				getColumnByIndex(columnIndex), columnName);
		}

		throw new RuntimeException("Table does not contain column names");
	}

	@Override
	public List<String> getRowByIndex(int index) {
		return _rows.get(index);
	}

	@Override
	public List<String> getRowByName(String rowName) {
		if (_hasRowNames) {
			int index = 0;

			for (List<String> row : _rows) {
				if (rowName.equals(row.get(0))) {
					return _getListWithoutTitle(getRowByIndex(index), rowName);
				}

				index++;
			}

			throw new RuntimeException(
				"Table does not contain row name: " + rowName);
		}

		throw new RuntimeException("Table does not contain row names");
	}

	public List<List<String>> getRows() {
		return _rows;
	}

	@Override
	public int getTableRowWidth(List<List<String>> table) {
		if (table.isEmpty()) {
			return 0;
		}

		List<String> cellValues = table.get(0);

		return cellValues.size();
	}

	public int getTableSize() {
		return _rows.size();
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

	private List<String> _getListWithoutTitle(List<String> list, String title) {
		if (title.equals(list.get(0))) {
			List<String> newList = new ArrayList<>(list);

			newList.remove(0);

			return newList;
		}

		return list;
	}

	private static final Pattern _entryPattern = Pattern.compile(
		"(?<entry>.*?)\\|");
	private static final Pattern _rowPattern = Pattern.compile(
		"\\|(?<row>.*\\|)(\\s*\\R)*");

	private boolean _hasColumnNames;
	private boolean _hasRowNames;
	private List<List<String>> _rows;

}