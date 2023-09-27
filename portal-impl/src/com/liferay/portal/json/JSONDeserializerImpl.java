/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONDeserializerTransformer;

import jodd.json.JsonParser;
import jodd.json.ValueConverter;

/**
 * @author Brian Wing Shun Chan
 */
public class JSONDeserializerImpl<T> implements JSONDeserializer<T> {

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

		_jsonDeserializer.withValueConverter(field, valueConverter);

		return this;
	}

	@Override
	public JSONDeserializer<T> use(String path, Class<?> clazz) {
		_jsonDeserializer.map(path, clazz);

		return this;
	}

	private final JsonParser _jsonDeserializer = new PortalJsonParser();

}