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
 * @author Igor Beslic
 */
public class CSVBatchEngineImportTaskItemReaderImpl
	implements BatchEngineImportTaskItemReader {

	public CSVBatchEngineImportTaskItemReaderImpl(
			String delimiter, InputStream inputStream,
			Map<String, Serializable> parameters)
		throws IOException {

		_delimiter = (String)parameters.getOrDefault("delimiter", delimiter);
		_inputStream = inputStream;

		_enclosingCharacter = _getEnclosingCharacter(parameters);

		_delimiterRegex = _getDelimiterRegex(_enclosingCharacter);

		_unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(_inputStream));

		_fieldNames = _getFieldNames(
			Boolean.valueOf(
				(String)parameters.getOrDefault(
					"containsHeaders", StringPool.TRUE)),
			_delimiter, _unsyncBufferedReader);
	}

	@Override
	public void close() throws IOException {
		_unsyncBufferedReader.close();
	}

	@Override
	public Map<String, Object> read() throws Exception {
		String line = _trimEnclosingCharacter(_unsyncBufferedReader.readLine());

		if (Validator.isNull(line)) {
			return null;
		}

		Map<String, Object> fieldNameValueMap = new HashMap<>();
		String[] values = line.split(_delimiterRegex);

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

	private String _getDelimiterRegex(String enclosingCharacter) {
		String escapedDelimiter = _delimiter;

		for (String delimiter : _ESCAPED_DELIMITERS) {
			if (delimiter.equals(escapedDelimiter)) {
				escapedDelimiter = StringPool.BACK_SLASH + _delimiter;

				break;
			}
		}

		if (Validator.isNull(enclosingCharacter)) {
			return escapedDelimiter;
		}

		return StringBundler.concat(
			enclosingCharacter, escapedDelimiter, enclosingCharacter);
	}

	private String _getEnclosingCharacter(
		Map<String, Serializable> parameters) {

		String enclosingCharacter = (String)parameters.getOrDefault(
			"enclosingCharacter", null);

		if (Validator.isNull(enclosingCharacter)) {
			return null;
		}

		return enclosingCharacter;
	}

	private String[] _getFieldNames(
			boolean containsHeaders, String delimiter,
			UnsyncBufferedReader unsyncBufferedReader)
		throws IOException {

		if (containsHeaders) {
			return StringUtil.split(unsyncBufferedReader.readLine(), delimiter);
		}

		String[] fieldNames = new String[100];

		for (int i = 0; i < fieldNames.length; i++) {
			fieldNames[i] = String.valueOf(i);
		}

		return fieldNames;
	}

	private String _trimEnclosingCharacter(String line) {
		if ((_enclosingCharacter == null) || Validator.isNull(line)) {
			return line;
		}

		if (line.startsWith(_enclosingCharacter)) {
			line = line.substring(1);
		}

		if (line.endsWith(_enclosingCharacter)) {
			line = line.substring(0, line.length() - 1);
		}

		return line;
	}

	private static final String[] _ESCAPED_DELIMITERS = {
		StringPool.CARET, StringPool.CLOSE_BRACKET,
		StringPool.CLOSE_CURLY_BRACE, StringPool.CLOSE_PARENTHESIS,
		StringPool.DOLLAR, StringPool.EXCLAMATION, StringPool.OPEN_BRACKET,
		StringPool.OPEN_CURLY_BRACE, StringPool.OPEN_PARENTHESIS,
		StringPool.PERIOD, StringPool.PIPE, StringPool.PLUS,
		StringPool.QUESTION, StringPool.STAR
	};

	private final String _delimiter;
	private final String _delimiterRegex;
	private final String _enclosingCharacter;
	private final String[] _fieldNames;
	private final InputStream _inputStream;
	private final UnsyncBufferedReader _unsyncBufferedReader;

}