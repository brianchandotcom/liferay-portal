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

package com.liferay.talend.runtime;

import java.util.Optional;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 * @author Igor Beslic
 */
public class LiferayRequestContentAggregatorSink extends LiferaySink {

	@Override
	public Optional<JsonObject> doPatchRequest(
		String resourceURL, JsonValue jsonValue) {

		return _processRequest(resourceURL, jsonValue);
	}

	@Override
	public Optional<JsonObject> doPostRequest(
		String resourceURL, JsonValue jsonValue) {

		return _processRequest(resourceURL, jsonValue);
	}

	public JsonValue getOutputJsonValue() {
		return _outputJsonValue;
	}

	public String getOutputResourceURL() {
		return _outputResourceURL;
	}

	private Optional<JsonObject> _processRequest(
		String resourceURL, JsonValue jsonValue) {

		_outputResourceURL = resourceURL;
		_outputJsonValue = jsonValue;

		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

		if (jsonValue instanceof JsonObject) {
			jsonObjectBuilder = Json.createObjectBuilder(
				jsonValue.asJsonObject());
		}
		else {
			jsonObjectBuilder.add("iterable", jsonValue);
		}

		jsonObjectBuilder.add("success", "true");

		return Optional.of(jsonObjectBuilder.build());
	}

	private JsonValue _outputJsonValue;
	private String _outputResourceURL;

}