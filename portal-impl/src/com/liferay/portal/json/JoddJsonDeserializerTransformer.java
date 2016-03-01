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

import com.liferay.portal.kernel.json.JSONDeserializerTransformer;

import jodd.json.ValueConverter;

/**
 * @author Preston Crary
 */
public class JoddJsonDeserializerTransformer<K, V>
	implements ValueConverter<K, V> {

	public JoddJsonDeserializerTransformer(
		JSONDeserializerTransformer<K, V> jsonDeserializerTransformer) {

		_jsonDeserializerTransformer = jsonDeserializerTransformer;
	}

	@Override
	public V convert(K key) {
		return _jsonDeserializerTransformer.transform(key);
	}

	private final JSONDeserializerTransformer<K, V>
		_jsonDeserializerTransformer;

}