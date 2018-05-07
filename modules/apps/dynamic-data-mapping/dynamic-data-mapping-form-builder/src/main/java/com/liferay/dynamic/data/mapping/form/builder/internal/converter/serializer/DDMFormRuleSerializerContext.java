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

package com.liferay.dynamic.data.mapping.form.builder.internal.converter.serializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Rafael Praxedes
 */
public class DDMFormRuleSerializerContext {

	public void addAttribute(String key, Object value) {
		_serializerContext.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String key) {
		return (T)_serializerContext.get(key);
	}

	private final Map<String, Object> _serializerContext =
		new ConcurrentHashMap<>();

}