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

package com.liferay.commerce.cloud.server.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 */
public class JsonUtil {

	public static <T> List<T> fromJsonArray(
		JsonArray jsonArray, Function<JsonObject, T> function) {

		List<T> values = new ArrayList<>(jsonArray.size());

		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jsonObject = jsonArray.getJsonObject(i);

			T value = function.apply(jsonObject);

			values.add(value);
		}

		return values;
	}

	public static <T> JsonObject getFilterJsonObject(String name, T value) {
		JsonObject filterValueJsonObject = getJsonObject("value", value);

		return getJsonObject(name, filterValueJsonObject);
	}

	public static <T> JsonObject getJsonObject(String key, T value) {
		JsonObject jsonObject = new JsonObject();

		jsonObject.put(key, value);

		return jsonObject;
	}

	public static void init() {
		Json.mapper.configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Json.mapper.configure(
			SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		Json.mapper.configure(
			SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
		Json.mapper.registerModule(new JavaTimeModule());
	}

}