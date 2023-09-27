/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import com.liferay.portal.kernel.json.JSONObject;

import java.io.IOException;

/**
 * @author Javier Gamarra
 */
public class JSONObjectStdSerializer extends StdSerializer<JSONObject> {

	public JSONObjectStdSerializer(Class<JSONObject> clazz) {
		super(clazz);
	}

	@Override
	public void serialize(
			JSONObject jsonObject, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider)
		throws IOException {

		jsonGenerator.writeStartObject();

		for (String key : jsonObject.keySet()) {
			serializerProvider.defaultSerializeField(
				key, jsonObject.get(key), jsonGenerator);
		}

		jsonGenerator.writeEndObject();
	}

}