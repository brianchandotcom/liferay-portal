/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

/**
 * @author Pablo Carvalho
 */
public class JSONValidator {
	public JSONValidator (String jsonSchema) 
		throws IOException, ProcessingException {
		JsonNode schemaNode = JsonLoader.fromString(jsonSchema);
		JsonSchemaFactory schemaFactory = JsonSchemaFactory.byDefault();
		_schema = schemaFactory.getJsonSchema(schemaNode);
	}

	public boolean isValid (String testedJson) 
		throws IOException, ProcessingException {
		JsonNode jsonNode = JsonLoader.fromString(testedJson);
		ProcessingReport report = _schema.validate(jsonNode);
		return report.isSuccess();
	}

	private JsonSchema _schema;
}
