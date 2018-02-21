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

import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONDeserializerTransformer;
import com.liferay.portal.kernel.util.JavaDetector;

import jodd.json.JsonParser;
import jodd.json.ValueConverter;

import jodd.util.SystemUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class JSONDeserializerImpl<T> implements JSONDeserializer<T> {

	public JSONDeserializerImpl() {
		if (JavaDetector.isIBM()) {
			SystemUtil.disableUnsafeUsage();
		}

		_jsonDeserializer = new PortalJsonParser();
	}

	@Override
	public T deserialize(String input) {
		return _jsonDeserializer.parse(input);
	}

	@Override
	public T deserialize(String input, Class<T> targetType) {
		return _jsonDeserializer.parse(input, targetType);
	}

	@Override
	public <K, V> JSONDeserializer<T> transform(
		JSONDeserializerTransformer<K, V> jsonDeserializerTransformer,
		String field) {

		ValueConverter<K, V> valueConverter =
			new JoddJsonDeserializerTransformer<>(jsonDeserializerTransformer);

		_jsonDeserializer.use(field, valueConverter);

		return this;
	}

	@Override
	public JSONDeserializer<T> use(String path, Class<?> clazz) {
		_jsonDeserializer.map(path, clazz);

		return this;
	}

	private final JsonParser _jsonDeserializer;

}