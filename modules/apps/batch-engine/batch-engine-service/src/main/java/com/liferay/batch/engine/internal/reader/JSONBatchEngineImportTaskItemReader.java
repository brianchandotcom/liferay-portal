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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class JSONBatchEngineImportTaskItemReader
	implements BatchEngineImportTaskItemReader {

	public JSONBatchEngineImportTaskItemReader(InputStream inputStream)
		throws IOException {

		_inputStream = inputStream;

		_jsonParser = _jsonFactory.createParser(_inputStream);

		_jsonParser.nextToken();
	}

	@Override
	public void close() throws IOException {
		_inputStream.close();
		_jsonParser.close();
	}

	@Override
	public Map<String, Object> read() throws Exception {
		if (_jsonParser.nextToken() == JsonToken.START_OBJECT) {
			return _objectMapper.readValue(
				_jsonParser,
				new TypeReference<Map<String, Object>>() {
				});
		}

		return null;
	}

	private static final JsonFactory _jsonFactory = new JsonFactory();
	private static final ObjectMapper _objectMapper = new ObjectMapper();

	private final InputStream _inputStream;
	private final JsonParser _jsonParser;

}