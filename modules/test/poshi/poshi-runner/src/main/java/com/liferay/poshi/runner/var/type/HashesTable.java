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
public class HashesTable extends BaseTable<Map<String, String>> {

	@Override
	public List<Map<String, String>> getTable() {
		return _hashesTable;
	}

	@Override
	public Iterator<Map<String, String>> iterator() {
		return _hashesTable.iterator();
	}

	protected HashesTable(List<List<String>> rawData) {
		super(rawData);

		if (rawData.size() < 2) {
			return;
		}

		List<String> rowKeys = rawData.get(0);

		for (int i = 1; i < rawData.size(); i++) {
			List<String> rowEntries = rawData.get(i);

			LinkedHashMap<String, String> hashesRow = new LinkedHashMap<>();

			for (int j = 0; j < rowEntries.size(); j++) {
				hashesRow.put(rowKeys.get(j), rowEntries.get(j));
			}

			_hashesTable.add(hashesRow);
		}
	}

	private final List<Map<String, String>> _hashesTable = new ArrayList<>();

}