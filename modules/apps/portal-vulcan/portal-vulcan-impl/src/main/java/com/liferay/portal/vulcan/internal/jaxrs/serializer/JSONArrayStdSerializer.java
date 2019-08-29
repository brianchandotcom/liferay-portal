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

package com.liferay.portal.vulcan.internal.jaxrs.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import com.liferay.portal.kernel.json.JSONArray;

import java.io.IOException;

/**
 * @author Javier Gamarra
 */
public class JSONArrayStdSerializer extends StdSerializer<JSONArray> {

	public JSONArrayStdSerializer(Class<JSONArray> clazz) {
		super(clazz);
	}

	@Override
	public void serialize(
			JSONArray jsonArray, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider)
		throws IOException {

		jsonGenerator.writeStartArray();

		for (Object object : jsonArray) {
			jsonGenerator.writeObject(object);
		}

		jsonGenerator.writeEndArray();
	}

}