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

package com.liferay.portal.json;

import com.fasterxml.jackson.databind.JsonNode;

import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONValidator;

/**
 * @author Pablo Carvalho
 */
public class JSONValidatorImpl implements JSONValidator {

	public JSONValidatorImpl(String json) throws JSONException {
		try {
			JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.byDefault();

			JsonNode jsonNode = JsonLoader.fromString(json);

			_jsonSchema = jsonSchemaFactory.getJsonSchema(jsonNode);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	@Override
	public boolean isValid(String json) throws JSONException {
		try {
			JsonNode jsonNode = JsonLoader.fromString(json);

			ProcessingReport processingReport = _jsonSchema.validate(jsonNode);

			return processingReport.isSuccess();
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	private final JsonSchema _jsonSchema;

}