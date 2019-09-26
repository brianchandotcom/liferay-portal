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

package com.liferay.talend;

import com.liferay.talend.avro.EndpointSchemaInferrer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.avro.Schema;

/**
 * @author Igor Beslic
 */
public abstract class BaseTestCase {

	public JsonObject readObject(String fileName) {
		Class<BaseTestCase> baseTestClass = BaseTestCase.class;

		JsonReader jsonReader = Json.createReader(
			baseTestClass.getResourceAsStream(fileName));

		return jsonReader.readObject();
	}

	protected Schema getSchema(
		String endpoint, String operation, JsonObject oasJsonObject) {

		return _endpointSchemaInferrer.inferSchema(
			endpoint, operation, oasJsonObject);
	}

	private final EndpointSchemaInferrer _endpointSchemaInferrer =
		new EndpointSchemaInferrer();

}