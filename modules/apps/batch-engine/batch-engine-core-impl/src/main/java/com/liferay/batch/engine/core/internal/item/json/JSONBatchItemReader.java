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

package com.liferay.batch.engine.core.internal.item.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.batch.engine.core.internal.item.BaseBatchItemReader;
import com.liferay.batch.engine.core.item.ParseException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ivica Cardic
 */
public class JSONBatchItemReader<T> extends BaseBatchItemReader<T> {

	public JSONBatchItemReader(
		Class<? extends T> itemType, InputStream inputStream) {

		_itemType = itemType;
		_inputStream = inputStream;
	}

	@Override
	protected void doClose() throws Exception {
		_inputStream.close();
		_jsonParser.close();
	}

	@Override
	protected void doOpen() throws Exception {
		_jsonParser = _JSON_FACTORY.createParser(
			new BufferedInputStream(_inputStream));

		_jsonParser.nextToken();
	}

	@Override
	protected T doRead() throws Exception, ParseException {
		try {
			if (_jsonParser.nextToken() == JsonToken.START_OBJECT) {
				return _OBJECT_MAPPER.readValue(_jsonParser, _itemType);
			}
		}
		catch (IOException ioe) {
			throw new ParseException("Unable to read next JSON object", ioe);
		}

		return null;
	}

	private static final JsonFactory _JSON_FACTORY = new JsonFactory();

	private static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper();

	private final InputStream _inputStream;
	private final Class<? extends T> _itemType;
	private JsonParser _jsonParser;

}