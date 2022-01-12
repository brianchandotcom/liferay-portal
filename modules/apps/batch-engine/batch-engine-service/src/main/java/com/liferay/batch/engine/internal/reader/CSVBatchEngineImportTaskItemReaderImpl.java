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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class CSVBatchEngineImportTaskItemReaderImpl
	implements BatchEngineImportTaskItemReader {

	public CSVBatchEngineImportTaskItemReaderImpl(
			String delimiter, Map<String, Serializable> parameters,
			InputStream inputStream)
		throws IOException {

		_delimiter = (String)parameters.getOrDefault("delimiter", delimiter);

		_enclosingCharacter = _getEnclosingCharacter(parameters);

		_inputStream = inputStream;

		_unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(_inputStream));

		_fieldNames = StringUtil.split(
			_unsyncBufferedReader.readLine(), _delimiter);
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

		String escapedDelimiter = _delimiter;

		for (String delimiter : _ESCAPED_DELIMITERS) {
			if (delimiter.equals(escapedDelimiter)) {
				escapedDelimiter = StringPool.BACK_SLASH + _delimiter;

				break;
			}
		}

		String regex = StringBundler.concat(
			StringPool.OPEN_BRACKET, escapedDelimiter,
			StringPool.CLOSE_BRACKET);

		if (_enclosingCharacter != null) {
			regex = StringBundler.concat(
				escapedDelimiter, "(?=(?:[^", _enclosingCharacter, "]*",
				_enclosingCharacter, "[^", _enclosingCharacter);

			regex = StringBundler.concat(
				regex, "]*", _enclosingCharacter, ")*[^", _enclosingCharacter,
				"]*$)");
		}

		Map<String, Object> fieldNameValueMap = new HashMap<>();
		String[] values =
			Validator.isNull(line) ? _EMPTY_STRING_ARRAY : line.split(regex);

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

	private String _getEnclosingCharacter(
		Map<String, Serializable> parameters) {

		String enclosingCharacter = (String)parameters.getOrDefault(
			"enclosingCharacter", null);

		if (enclosingCharacter == null) {
			return null;
		}

		// return ' or \"

		if (enclosingCharacter.equals(StringPool.APOSTROPHE)) {
			return enclosingCharacter;
		}

		return StringPool.BACK_SLASH + enclosingCharacter;
	}

	private static final String[] _EMPTY_STRING_ARRAY = new String[0];

	private static final String[] _ESCAPED_DELIMITERS = {
		StringPool.OPEN_BRACKET, StringPool.CLOSE_BRACKET,
		StringPool.OPEN_PARENTHESIS, StringPool.CLOSE_PARENTHESIS,
		StringPool.OPEN_CURLY_BRACE, StringPool.CLOSE_CURLY_BRACE,
		StringPool.QUESTION, StringPool.PERIOD, StringPool.STAR,
		StringPool.CARET, StringPool.DOLLAR, StringPool.PLUS,
		StringPool.EXCLAMATION, StringPool.PIPE
	};

	private final String _delimiter;
	private final String _enclosingCharacter;
	private final String[] _fieldNames;
	private final InputStream _inputStream;
	private final UnsyncBufferedReader _unsyncBufferedReader;

}