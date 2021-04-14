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

import jodd.json.JsonContext;
import jodd.json.JsonSerializer;
import jodd.json.TypeJsonSerializer;
import jodd.json.TypeJsonSerializerMap;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Igor Spasic
 */
public class JSONSerializerImpl implements JSONSerializer {

	public JSONSerializerImpl() {
		_jsonSerializer = new JsonSerializer();

		_jsonSerializer.strictStringEncoding(true);
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
		JsonSerializer jsonSerializer = _jsonSerializer.deep(true);

		return jsonSerializer.serialize(target);
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

		_jsonSerializer.withSerializer(type, typeJsonSerializer);

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

		_jsonSerializer.withSerializer(field, typeJsonSerializer);

		return this;
	}

	private final JsonSerializer _jsonSerializer;

	private static class JSONArrayTypeJSONSerializer
		implements TypeJsonSerializer<JSONArray> {

		@Override
		public boolean serialize(JsonContext jsonContext, JSONArray jsonArray) {
			jsonContext.write(jsonArray.toString());

			return true;
		}

	}

	private static class JSONObjectTypeJSONSerializer
		implements TypeJsonSerializer<JSONObject> {

		@Override
		public boolean serialize(
			JsonContext jsonContext, JSONObject jsonObject) {

			jsonContext.write(jsonObject.toString());

			return true;
		}

	}

	private static class LongToStringTypeJSONSerializer
		implements TypeJsonSerializer<Long> {

		@Override
		public boolean serialize(JsonContext jsonContext, Long value) {
			jsonContext.writeString(String.valueOf(value));

			return true;
		}

	}

	static {
		TypeJsonSerializerMap typeJsonSerializerMap =
			TypeJsonSerializerMap.get();

		typeJsonSerializerMap.register(
			JSONArray.class, new JSONArrayTypeJSONSerializer());
		typeJsonSerializerMap.register(
			JSONObject.class, new JSONObjectTypeJSONSerializer());
		typeJsonSerializerMap.register(
			Long.TYPE, new LongToStringTypeJSONSerializer());
		typeJsonSerializerMap.register(
			Long.class, new LongToStringTypeJSONSerializer());
	}

}