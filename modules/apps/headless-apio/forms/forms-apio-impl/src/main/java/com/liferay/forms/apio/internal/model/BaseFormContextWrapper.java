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

package com.liferay.forms.apio.internal.model;

import com.liferay.apio.architect.functional.Try;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Paulo Cruz
 */
public class BaseFormContextWrapper {

	public BaseFormContextWrapper(Map<String, Object> wrappedMap) {
		_wrappedMap = wrappedMap;
	}

	public <T extends BaseFormContextWrapper> List<T> getListFromMap(
		Map<String, Object> map, String key,
		Function<Map<String, Object>, T> parseItemFunction) {

		return Try.fromFallible(
			() -> (List<Map<String, Object>>)map.get(key)
		).map(
			List::stream
		).orElseGet(
			Stream::empty
		).map(
			parseItemFunction
		).collect(
			Collectors.toList()
		);
	}

	public <T> T getValue(String key, Class<T> type) {
		return getValueOrDefault(key, type::cast, null);
	}

	public <T> T getValue(String key, Function<Object, T> parseFunction) {
		return getValueOrDefault(key, parseFunction, null);
	}

	public <T> T getValueOrDefault(String key, Class<T> type, T defaultValue) {
		return getValueOrDefault(key, type::cast, defaultValue);
	}

	public <T> T getValueOrDefault(
		String key, Function<Object, T> parseFunction, T defaultValue) {

		return Optional.ofNullable(
			_wrappedMap.get(key)
		).map(
			parseFunction
		).orElse(
			defaultValue
		);
	}

	public <T extends BaseFormContextWrapper> List<T> getWrappedList(
		String key, Function<Map<String, Object>, T> parseItemFunction) {

		return getListFromMap(_wrappedMap, key, parseItemFunction);
	}

	public Map<String, Object> getWrappedMap() {
		return _wrappedMap;
	}

	private final Map<String, Object> _wrappedMap;

}