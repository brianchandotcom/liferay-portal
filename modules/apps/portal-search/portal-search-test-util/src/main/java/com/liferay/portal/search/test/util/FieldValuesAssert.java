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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.Field;
import com.liferay.portal.search.searcher.SearchResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class FieldValuesAssert {

	public static void assertFieldValue(
		String fieldName, Object fieldValue, SearchResponse searchResponse) {

		assertFieldValues(
			Collections.singletonMap(fieldName, fieldValue),
			Predicate.isEqual(fieldName), searchResponse);
	}

	public static void assertFieldValues(
		Map<String, ?> expected,
		com.liferay.portal.kernel.search.Document document, String message) {

		AssertUtils.assertEquals(
			message, _toStringValuesMap(expected),
			_toLegacyFieldValuesMap(document));
	}

	public static void assertFieldValues(
		Map<String, ?> expected, Predicate<String> keysPredicate,
		SearchResponse searchResponse) {

		Map<String, String> expectedFieldValuesMap = _toStringValuesMap(
			expected);

		Stream<Document> stream = searchResponse.getDocumentsStream();

		List<Document> documents = stream.collect(Collectors.toList());

		if (documents.size() == 1) {
			Map<String, String> actualFieldValuesMap = _toFieldValuesMap(
				documents.get(0));

			Map<String, String> filteredFieldValuesMap = _filterOnKey(
				actualFieldValuesMap, keysPredicate);

			AssertUtils.assertEquals(
				() -> StringBundler.concat(
					searchResponse.getRequestString(), "->",
					actualFieldValuesMap, "->", filteredFieldValuesMap),
				expectedFieldValuesMap, filteredFieldValuesMap);
		}
		else {
			AssertUtils.assertEquals(
				() -> StringBundler.concat(
					searchResponse.getRequestString(), "->", documents),
				expectedFieldValuesMap, documents);
		}
	}

	public static void assertFieldValues(
		Map<String, ?> expected, SearchResponse searchResponse) {

		assertFieldValues(expected, null, searchResponse);
	}

	public static void assertFieldValues(
		Map<String, ?> expected, String prefix,
		com.liferay.portal.kernel.search.Document document, String message) {

		AssertUtils.assertEquals(
			message, _toStringValuesMap(expected),
			_filterOnKey(
				_toLegacyFieldValuesMap(document),
				name -> name.startsWith(prefix)));
	}

	public static void assertFieldValues(
		String message, Document document, Map<?, ?> expected) {

		AssertUtils.assertEquals(
			message, expected, _toFieldValuesMap(document));
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static void assertFieldValues(
		String message, Document document, Predicate<String> keysPredicate,
		Map<?, ?> expected) {

		AssertUtils.assertEquals(
			message, expected,
			_filterOnKey(_toFieldValuesMap(document), keysPredicate));
	}

	private static Map<String, String> _filterOnKey(
		Map<String, String> map, Predicate<String> predicate) {

		if (predicate == null) {
			return map;
		}

		Stream<Map.Entry<String, String>> stream = SearchStreamUtil.stream(
			map.entrySet());

		return stream.filter(
			entry -> predicate.test(entry.getKey())
		).collect(
			Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
		);
	}

	private static <E> List<E> _sort(List<E> list) {
		ArrayList<E> sortedList = new ArrayList<>(list);

		try {
			Collections.sort(sortedList, null);
		}
		catch (ClassCastException | NullPointerException exception) {
			return list;
		}

		return sortedList;
	}

	private static String _toFieldString(Field field) {
		return _toListString(field.getValues());
	}

	private static Map<String, String> _toFieldValuesMap(Document document) {
		return _toStringValuesMap(
			document.getFields(), FieldValuesAssert::_toFieldString);
	}

	private static String _toLegacyFieldString(
		com.liferay.portal.kernel.search.Field field) {

		return _toListString(Arrays.asList(field.getValues()));
	}

	private static Map<String, String> _toLegacyFieldValuesMap(
		com.liferay.portal.kernel.search.Document document) {

		return _toStringValuesMap(
			document.getFields(), FieldValuesAssert::_toLegacyFieldString);
	}

	private static String _toListString(List<?> values) {
		if (values == null) {
			return null;
		}

		if (values.size() == 1) {
			return String.valueOf(values.get(0));
		}

		if (!values.isEmpty()) {
			return String.valueOf(_sort(values));
		}

		return "[]";
	}

	private static String _toObjectString(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof List) {
			return _toListString((List)value);
		}

		return String.valueOf(value);
	}

	private static Map<String, String> _toStringValuesMap(Map map) {
		return _toStringValuesMap(map, FieldValuesAssert::_toObjectString);
	}

	private static <T> Map<String, String> _toStringValuesMap(
		Map<String, T> map, Function<T, String> function) {

		Stream<Map.Entry<String, T>> stream = SearchStreamUtil.stream(
			map.entrySet());

		return stream.collect(
			Collectors.toMap(
				entry -> entry.getKey(),
				entry -> function.apply(entry.getValue())));
	}

}