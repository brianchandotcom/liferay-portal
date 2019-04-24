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

package com.liferay.talend.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 * @author Zoltán Takács
 */
public class JsonUtils {

	public static JsonNode toJsonNode(JsonObject jsonObject) {
		StringWriter stringWriter = new StringWriter();

		try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
			jsonWriter.writeObject(jsonObject);
		}

		String json = stringWriter.toString();

		JsonNode jsonNode = null;

		try {
			jsonNode = _objectMapper.readTree(json);
		}
		catch (IOException ioe) {
			new RuntimeException(ioe);
		}

		return jsonNode;
	}

	private JsonUtils() {
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper();

}