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