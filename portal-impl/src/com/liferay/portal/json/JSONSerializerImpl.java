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

import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.json.JSONTransformer;

import jodd.json.JoddJson;
import jodd.json.JsonContext;
import jodd.json.JsonSerializer;
import jodd.json.TypeJsonSerializer;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Igor Spasic
 */
public class JSONSerializerImpl implements JSONSerializer {

	public JSONSerializerImpl() {
		_jsonSerializer = new JsonSerializer();
	}

	@Override
	public JSONSerializerImpl exclude(String... fields) {
		_jsonSerializer.exclude(fields);

		return this;
	}

	@Override
	public JSONSerializerImpl include(String... fields) {
		_jsonSerializer.include(fields);

		return this;
	}

	@Override
	public String serialize(Object target) {
		return _jsonSerializer.serialize(target);
	}

	@Override
	public String serializeDeep(Object target) {
		return _jsonSerializer.deep(true).serialize(target);
	}

	@Override
	public JSONSerializerImpl transform(
		JSONTransformer jsonTransformer, Class<?> type) {

		TypeJsonSerializer<?> typeJsonSerializer = null;

		if (jsonTransformer instanceof TypeJsonSerializer) {
			typeJsonSerializer = (TypeJsonSerializer<?>)jsonTransformer;
		}
		else {
			typeJsonSerializer = new JoddJsonTransformer(jsonTransformer);
		}

		_jsonSerializer.use(type, typeJsonSerializer);

		return this;
	}

	@Override
	public JSONSerializerImpl transform(
		JSONTransformer jsonTransformer, String field) {

		TypeJsonSerializer<?> typeJsonSerializer = null;

		if (jsonTransformer instanceof TypeJsonSerializer) {
			typeJsonSerializer = (TypeJsonSerializer<?>)jsonTransformer;
		}
		else {
			typeJsonSerializer = new JoddJsonTransformer(jsonTransformer);
		}

		_jsonSerializer.use(field, typeJsonSerializer);

		return this;
	}

	static {
		JoddJson.defaultSerializers.register(
			JSONArray.class, new JSONArrayTypeJSONSerializer());
		JoddJson.defaultSerializers.register(
			JSONObject.class, new JSONObjectTypeJSONSerializer());
		JoddJson.defaultSerializers.register(
			Long.TYPE, new LongToStringTypeJSONSerializer());
		JoddJson.defaultSerializers.register(
			Long.class, new LongToStringTypeJSONSerializer());
	}

	private final JsonSerializer _jsonSerializer;

	private static class JSONArrayTypeJSONSerializer
		implements TypeJsonSerializer<JSONArray> {

		@Override
		public void serialize(JsonContext jsonContext, JSONArray jsonArray) {
			jsonContext.write(jsonArray.toString());
		}

	}

	private static class JSONObjectTypeJSONSerializer
		implements TypeJsonSerializer<JSONObject> {

		@Override
		public void serialize(JsonContext jsonContext, JSONObject jsonObject) {
			jsonContext.write(jsonObject.toString());
		}

	}

	private static class LongToStringTypeJSONSerializer
		implements TypeJsonSerializer<Long> {

		@Override
		public void serialize(JsonContext jsonContext, Long value) {
			jsonContext.writeString(Long.toString(value));
		}

	}

}