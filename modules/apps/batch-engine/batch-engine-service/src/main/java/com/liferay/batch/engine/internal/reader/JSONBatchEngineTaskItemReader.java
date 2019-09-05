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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ivica Cardic
 */
public class JSONBatchEngineTaskItemReader<T>
	implements BatchEngineTaskItemReader<T> {

	public JSONBatchEngineTaskItemReader(
			Class<? extends T> domainClass, InputStream inputStream)
		throws IOException {

		_domainClass = domainClass;
		_inputStream = inputStream;

		_jsonParser = _JSON_FACTORY.createParser(_inputStream);

		_jsonParser.nextToken();
	}

	@Override
	public void close() throws IOException {
		_inputStream.close();
		_jsonParser.close();
	}

	@Override
	public T read() throws IOException {
		if (_jsonParser.nextToken() == JsonToken.START_OBJECT) {
			return _OBJECT_MAPPER.readValue(_jsonParser, _domainClass);
		}

		return null;
	}

	private static final JsonFactory _JSON_FACTORY = new JsonFactory();

	private static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper();

	private final Class<? extends T> _domainClass;
	private final InputStream _inputStream;
	private final JsonParser _jsonParser;

}