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
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 * @author Matija Petanjek
 */
public class CSVBatchEngineImportTaskItemReaderImpl
	implements BatchEngineImportTaskItemReader {

	public CSVBatchEngineImportTaskItemReaderImpl(
			String delimiter, InputStream inputStream,
			Map<String, Serializable> parameters)
		throws IOException {

		_csvParser = CSVParser.parse(
			new UnsyncBufferedReader(new InputStreamReader(inputStream)),
			_getCSVFormat(
				(String)parameters.getOrDefault("delimiter", delimiter),
				(String)parameters.getOrDefault("enclosingCharacter", null)));

		_csvRecordIterator = _csvParser.iterator();

		_fieldNames = _getFieldNames(
			Boolean.valueOf(
				(String)parameters.getOrDefault(
					"containsHeaders", StringPool.TRUE)),
			_csvRecordIterator);
	}

	@Override
	public void close() throws IOException {
		_csvParser.close();
	}

	@Override
	public Map<String, Object> read() throws Exception {
		if (!_csvRecordIterator.hasNext()) {
			return null;
		}

		Map<String, Object> fieldNameValueMap = new HashMap<>();

		CSVRecord csvRecord = _csvRecordIterator.next();

		List<String> values = csvRecord.toList();

		for (int i = 0; i < values.size(); i++) {
			String fieldName = _fieldNames[i];

			if (fieldName == null) {
				continue;
			}

			FieldNameValueMapHandlerFactory.FieldNameValueMapHandler
				fieldNameValueMapHandler =
					FieldNameValueMapHandlerFactory.getFieldNameValueMapHandler(
						fieldName);

			fieldNameValueMapHandler.handle(
				fieldName, fieldNameValueMap, values.get(i));
		}

		return fieldNameValueMap;
	}

	private CSVFormat _getCSVFormat(
		String delimiter, String enclosingCharacter) {

		CSVFormat.Builder builder = CSVFormat.Builder.create(
		).setDelimiter(
			delimiter
		).setIgnoreEmptyLines(
			true
		);

		if (Validator.isNotNull(enclosingCharacter)) {
			builder.setQuote(enclosingCharacter.charAt(0));
		}

		return builder.build();
	}

	private String[] _getFieldNames(
		boolean containsHeaders, Iterator<CSVRecord> csvRecordIterator) {

		if (containsHeaders) {
			CSVRecord csvRecord = csvRecordIterator.next();

			List<String> headerFields = csvRecord.toList();

			return headerFields.toArray(new String[0]);
		}

		String[] fieldNames = new String[100];

		for (int i = 0; i < fieldNames.length; i++) {
			fieldNames[i] = String.valueOf(i);
		}

		return fieldNames;
	}

	private final CSVParser _csvParser;
	private final Iterator<CSVRecord> _csvRecordIterator;
	private final String[] _fieldNames;

}