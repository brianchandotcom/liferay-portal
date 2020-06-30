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
public class CSVBatchEngineImportTaskItemReader
	implements BatchEngineImportTaskItemReader {

	public CSVBatchEngineImportTaskItemReader(
			String delimiter, InputStream inputStream)
		throws IOException {

		_delimiter = delimiter;

		_inputStream = inputStream;

		_unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(_inputStream));

		_fieldNames = StringUtil.split(
			_unsyncBufferedReader.readLine(), delimiter);
	}

	@Override
	public void close() throws IOException {
		_unsyncBufferedReader.close();
	}

	@Override
	public Map<String, Object> read() throws Exception {
		String line = _unsyncBufferedReader.readLine();

		if (line == null) {
			return null;
		}

		Map<String, Object> fieldNameValueMap = new HashMap<>();

		String[] values = StringUtil.split(line, _delimiter);

		for (int i = 0; i < values.length; i++) {
			String fieldName = _fieldNames[i];

			if (fieldName == null) {
				continue;
			}

			String value = values[i].trim();

			if (value.isEmpty()) {
				value = null;
			}

			int lastDelimiterIndex = fieldName.lastIndexOf('_');

			if (lastDelimiterIndex == -1) {
				fieldNameValueMap.put(fieldName, value);
			}
			else {
				BatchEngineImportTaskItemReaderUtil.handleMapField(
					fieldName, fieldNameValueMap, lastDelimiterIndex, value);
			}
		}

		return fieldNameValueMap;
	}

	private final String _delimiter;
	private final String[] _fieldNames;
	private final InputStream _inputStream;
	private final UnsyncBufferedReader _unsyncBufferedReader;

}