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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import com.liferay.petra.string.StringPool;

import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Matija Petanjek
 */
public class MapStdDeserializer extends StdDeserializer<Map<String, Object>> {

	public MapStdDeserializer() {
		this(Map.class);
	}

	@Override
	public Map<String, Object> deserialize(
			JsonParser jsonParser,
			DeserializationContext deserializationContext)
		throws IOException {

		Map<String, Object> map;

		try {
			map = deserializationContext.readValue(
				jsonParser, LinkedHashMap.class);
		}
		catch (Exception exception) {
			map = new LinkedHashMap<>();

			String mapString = jsonParser.getValueAsString();

			for (String entryString :
					mapString.split(StringPool.RETURN_NEW_LINE)) {

				String[] keyValue = entryString.split(StringPool.COLON);

				map.put(keyValue[0], keyValue[1]);
			}
		}

		return map;
	}

	protected MapStdDeserializer(Class<?> clazz) {
		super(clazz);
	}

}