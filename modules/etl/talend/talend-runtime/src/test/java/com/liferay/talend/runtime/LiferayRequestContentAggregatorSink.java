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

/**
 * @author Igor Beslic
 */
public class LiferayRequestContentAggregatorSink extends LiferaySink {

	@Override
	public Optional<JsonObject> doPatchRequest(
		String resourceURL, JsonObject jsonObject) {

		return _processRequest(resourceURL, jsonObject);
	}

	@Override
	public Optional<JsonObject> doPostRequest(
		String resourceURL, JsonObject jsonObject) {

		return _processRequest(resourceURL, jsonObject);
	}

	public JsonObject getOutputJsonObject() {
		return _outputJsonObject;
	}

	public String getOutputResourceURL() {
		return _outputResourceURL;
	}

	private Optional<JsonObject> _processRequest(
		String resourceURL, JsonObject jsonObject) {

		_outputResourceURL = resourceURL;
		_outputJsonObject = jsonObject;

		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder(
			jsonObject);

		jsonObjectBuilder.add("success", "true");

		return Optional.of(jsonObjectBuilder.build());
	}

	private JsonObject _outputJsonObject;
	private String _outputResourceURL;

}