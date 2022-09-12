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

package com.liferay.translation.translator.deepl.internal.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;

import java.net.URISyntaxException;

/**
 * @author Yasuyuki Takeo
 */
public class JSONUtil {

	public static <T> T toObject(String jsonString, Class<T> clazz)
		throws IOException, JsonMappingException, JsonParseException {

		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.enable(
			DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES);

		return objectMapper.readValue(jsonString, clazz);
	}

	public static <T> T toObject(String jsonString, TypeReference<T> type)
		throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.readValue(jsonString, type);
	}

	public static <T> void writer(String fullPath, T obj)
		throws IOException, URISyntaxException {

		ObjectMapper objectMapper = new ObjectMapper();

		ObjectWriter objectWriter = objectMapper.writer();

		objectWriter = objectWriter.withDefaultPrettyPrinter();

		objectWriter.writeValue(new File(fullPath), obj);
	}

	public static <T> String writeValueAsString(T obj)
		throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();

		ObjectWriter objectWriter = objectMapper.writer();

		objectWriter = objectWriter.withDefaultPrettyPrinter();

		return objectWriter.writeValueAsString(obj);
	}

}