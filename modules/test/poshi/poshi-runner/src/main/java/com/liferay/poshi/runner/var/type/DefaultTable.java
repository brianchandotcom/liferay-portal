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
		List<String> columnList = new ArrayList<>();

		for (List<String> row : _table) {
			String column = row.get(index);

			columnList.add(column);
		}

		return columnList;
	}

	@Override
	public List<String> getColumnByName(String columnName) {
		if (_hasColumnNames) {
			List<String> columnList = new ArrayList<>();
			int columnIndex = -1;

			for (List<String> row : _table) {
				for (String column : row) {
					if (column.equals(columnName)) {
						columnIndex = row.indexOf(column);
					}
				}

				columnList.add(row.get(columnIndex));
			}

			columnList.remove(0);

			return columnList;
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
			for (List<String> row : _table) {
				if (row.get(0) == rowName) {
					List<String> newList = row;

					newList.remove(0);

					return newList;
				}
			}
		}

		throw new RuntimeException("Table does not contain row names");
	}

	@Override
	public int getTableRowWidth(List<List<String>> rawTable) {
		if (ListUtil.isEmpty(rawTable)) {
			return 0;
		}

		List<String> firstRow = rawTable.get(0);

		return firstRow.size();
	}

	public int getTableSize() {
		return _table.size();
	}

	@Override
	public Table getTransposedTable(List<List<String>> rawTable) {
		List<List<String>> transposedRawTableList = new ArrayList<>();

		for (int i = 0; i < getTableRowWidth(rawTable); i++) {
			List<String> column = new ArrayList<>();

			for (List<String> row : rawTable) {
				column.add(row.get(i));
			}

			transposedRawTableList.add(column);
		}

		return new DefaultTable(transposedRawTableList);
	}

	private static List<List<String>> _parse(String tableString) {
		Matcher rowMatcher = _rowPattern.matcher(tableString);

		if (!rowMatcher.find()) {
			throw new IllegalArgumentException(
				"Invalid table string:\n" + tableString);
		}

		rowMatcher.reset();

		List<List<String>> tableDataList = new ArrayList<>();

		while (rowMatcher.find()) {
			String row = rowMatcher.group("row");

			Matcher entryMatcher = _entryPattern.matcher(row);

			List<String> rowList = new ArrayList<>();

			while (entryMatcher.find()) {
				String entry = entryMatcher.group("entry");

				rowList.add(entry.trim());
			}
		}

		return tableDataList;
	}

	private static final Pattern _entryPattern = Pattern.compile(
		"(?<entry>.*?)\\|");
	private static final Pattern _rowPattern = Pattern.compile(
		"\\|(?<row>.*\\|)(\\s*\\R)*");

	private boolean _hasColumnNames;
	private boolean _hasRowNames;
	private List<List<String>> _table;

}