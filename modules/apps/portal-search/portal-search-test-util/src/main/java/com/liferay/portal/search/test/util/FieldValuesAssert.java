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

package com.liferay.portal.search.test.util;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.Field;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class FieldValuesAssert {

	public static void assertFieldValues(
		Map<String, String> expected,
		com.liferay.portal.kernel.search.Document document, String message) {

		AssertUtils.assertEquals(message, expected, _toMap(document));
	}

	public static void assertFieldValues(
		Map<String, String> expected, String prefix,
		com.liferay.portal.kernel.search.Document document, String message) {

		AssertUtils.assertEquals(
			message, expected,
			_filterOnKey(_toMap(document), name -> name.startsWith(prefix)));
	}

	public static void assertFieldValues(
		String message, Document document, Map<?, ?> expected) {

		AssertUtils.assertEquals(message, expected, _toMap(document));
	}

	public static void assertFieldValues(
		String message, Document document, Predicate<String> predicate,
		Map<?, ?> expected) {

		AssertUtils.assertEquals(
			message, expected, _filterOnKey(_toMap(document), predicate));
	}

	private static Map<String, String> _filterOnKey(
		Map<String, String> stringsMap, Predicate<String> predicate) {

		Set<Map.Entry<String, String>> entrySet = stringsMap.entrySet();

		Stream<Map.Entry<String, String>> stream = entrySet.stream();

		return stream.filter(
			entry -> predicate.test(entry.getKey())
		).collect(
			Collectors.toMap(map -> map.getKey(), map -> map.getValue())
		);
	}

	private static Map<String, String> _toMap(
		com.liferay.portal.kernel.search.Document document) {

		return _toMap(document.getFields(), FieldValuesAssert::_toString);
	}

	private static Map<String, String> _toMap(Document document) {
		return _toMap(document.getFields(), FieldValuesAssert::_toString);
	}

	private static <T> Map<String, String> _toMap(
		Map<String, T> map, Function<T, String> function) {

		Set<Map.Entry<String, T>> entrySet = map.entrySet();

		Stream<Map.Entry<String, T>> stream = entrySet.stream();

		return stream.collect(
			Collectors.toMap(
				Map.Entry::getKey, entry -> function.apply(entry.getValue())));
	}

	private static String _toString(
		com.liferay.portal.kernel.search.Field field) {

		return _toString(Arrays.asList(field.getValues()));
	}

	private static String _toString(Field field) {
		return _toString(field.getValues());
	}

	private static String _toString(List<?> values) {
		if (values == null) {
			return null;
		}

		if (values.size() == 1) {
			return String.valueOf(values.get(0));
		}

		return String.valueOf(values);
	}

}