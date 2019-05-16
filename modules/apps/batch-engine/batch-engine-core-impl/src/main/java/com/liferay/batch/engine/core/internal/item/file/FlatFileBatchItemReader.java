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

package com.liferay.batch.engine.core.internal.item.file;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.batch.engine.core.internal.item.BaseBatchItemReader;
import com.liferay.batch.engine.core.item.ParseException;
import com.liferay.batch.engine.core.item.file.IncorrectTokenCountException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author Ivica Cardic
 */
public class FlatFileBatchItemReader<T> extends BaseBatchItemReader<T> {

	public FlatFileBatchItemReader(
		Class<? extends T> itemType, String delimiter, String[] columnNames,
		int linesToSkip, InputStream inputStream) {

		_itemType = itemType;
		_delimiter = delimiter;
		_columnNames = columnNames;
		_linesToSkip = linesToSkip;
		_inputStream = inputStream;
	}

	@Override
	protected void doClose() throws Exception {
		_reader.close();
	}

	@Override
	protected void doOpen() throws Exception {
		_reader = new BufferedReader(new InputStreamReader(_inputStream));
	}

	@Override
	protected T doRead() throws Exception, ParseException {
		if (_columnNames == null) {
			String line = _reader.readLine();

			StringTokenizer stringTokenizer = new StringTokenizer(
				line, _delimiter);

			List<String> columnNames = new ArrayList<>();

			while (stringTokenizer.hasMoreTokens()) {
				String token = stringTokenizer.nextToken();

				columnNames.add(token);
			}

			_columnNames = columnNames.toArray(new String[0]);

			_linesToSkip--;
		}

		while (_linesToSkip-- > 0) {
			_reader.readLine();
		}

		String line = _reader.readLine();

		if (line == null) {
			return null;
		}

		StringTokenizer stringTokenizer = new StringTokenizer(line, _delimiter);

		Map<String, Object> map = new HashMap<>();

		int index = 0;

		while (stringTokenizer.hasMoreTokens()) {
			String token = stringTokenizer.nextToken();

			token = token.trim();

			String columnName = _columnNames[index++];

			if (columnName == null) {
				continue;
			}

			int lastDelimiterIndex = columnName.lastIndexOf("_");

			if (lastDelimiterIndex != -1) {
				_handleLocalizationColumn(
					columnName, token, map, lastDelimiterIndex);
			}
			else {
				map.put(columnName, token);
			}
		}

		if (map.size() != _columnNames.length) {
			throw new IncorrectTokenCountException(
				_columnNames.length, map.size(), line);
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
	private final String _delimiter;
	private final InputStream _inputStream;
	private final Class<? extends T> _itemType;
	private int _linesToSkip;
	private BufferedReader _reader;

}