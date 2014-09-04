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

import jodd.json.JsonSerializer;
import jodd.json.TypeJsonSerializer;

/**
 * Wrapper over Jodd type serializer.
 *
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

		TypeJsonSerializer transformer = null;

		if (jsonTransformer instanceof TypeJsonSerializer) {
			transformer = (TypeJsonSerializer)jsonTransformer;
		}
		else {
			transformer = new JoddJsonTransformer(jsonTransformer);
		}

		_jsonSerializer.use(type, transformer);

		return this;
	}

	@Override
	public JSONSerializerImpl transform(
		JSONTransformer jsonTransformer, String field) {

		TypeJsonSerializer transformer = null;

		if (jsonTransformer instanceof TypeJsonSerializer) {
			transformer = (TypeJsonSerializer)jsonTransformer;
		}
		else {
			transformer = new JoddJsonTransformer(jsonTransformer);
		}

		_jsonSerializer.use(field, transformer);

		return this;
	}

	private final jodd.json.JsonSerializer _jsonSerializer;

}