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

import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class FlatFileBatchEngineTaskItemReader<T>
	implements BatchEngineTaskItemReader<T> {

	public FlatFileBatchEngineTaskItemReader(
			Class<? extends T> domainClass, InputStream inputStream)
		throws IOException {

		_domainClass = domainClass;
		_inputStream = inputStream;

		_reader = new UnsyncBufferedReader(new InputStreamReader(_inputStream));

		_columnNames = StringUtil.split(_reader.readLine());
	}

	@Override
	public void close() throws IOException {
		_reader.close();
	}

	@Override
	public T read() throws IOException {
		String line = _reader.readLine();

		if (line == null) {
			return null;
		}

		Map<String, Object> columnNameValueMap = new HashMap<>();

		String[] values = StringUtil.split(line);

		for (int i = 0; i < values.length; i++) {
			String columnName = _columnNames[i];

			if (columnName == null) {
				continue;
			}

			String value = values[i].trim();

			int lastDelimiterIndex = columnName.lastIndexOf('_');

			if (lastDelimiterIndex == -1) {
				columnNameValueMap.put(columnName, value);
			}
			else {
				ColumnUtil.handleLocalizationColumn(
					columnName, value, columnNameValueMap, lastDelimiterIndex);
			}
		}

		return _OBJECT_MAPPER.convertValue(columnNameValueMap, _domainClass);
	}

	private static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper();

	private final String[] _columnNames;
	private final Class<? extends T> _domainClass;
	private final InputStream _inputStream;
	private final UnsyncBufferedReader _reader;

}