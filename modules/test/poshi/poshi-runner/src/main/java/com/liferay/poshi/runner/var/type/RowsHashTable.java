/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.var.type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yi-Chen Tsai
 */
public class RowsHashTable extends BaseTable<Map<String, String>> {

	@Override
	public List<Map<String, String>> getTable() {
		return _rowsHashTable;
	}

	@Override
	public Iterator<Map<String, String>> iterator() {
		return _rowsHashTable.iterator();
	}

	protected RowsHashTable(List<List<String>> rawData) {
		super(rawData);

		_verifyRawDataWidth(2);

		LinkedHashMap<String, String> row = new LinkedHashMap<>();

		for (List<String> rawDataRow : rawData) {
			row.put(rawDataRow.get(0), rawDataRow.get(1));
		}

		_rowsHashTable.add(row);
	}

	private int _getRawDataWidth() {
		if (rawData.isEmpty()) {
			return 0;
		}

		List<String> firstRow = rawData.get(0);

		return firstRow.size();
	}

	private void _verifyRawDataWidth(int width) {
		if (_getRawDataWidth() != width) {
			throw new RuntimeException(
				"The raw data must have exactly " + width + " columns");
		}
	}

	private final List<Map<String, String>> _rowsHashTable = new ArrayList<>();

}