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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class FieldValuesAssert {

	public static void assertFieldValues(
		Map<String, String> expected, Document document, String message) {

		AssertUtils.assertEquals(
			message, expected, _getFieldValues(document, null));
	}

	public static void assertFieldValues(
		Map<String, String> expected, String prefix, Document document,
		String message) {

		AssertUtils.assertEquals(
			message, expected,
			_getFieldValues(document, name -> name.startsWith(prefix)));
	}

	private static Map<String, String> _getFieldValues(
		Document document, Predicate<String> predicate) {

		Map<String, Field> fieldsMap = document.getFields();

		Set<Map.Entry<String, Field>> entrySet = fieldsMap.entrySet();

		Stream<Map.Entry<String, Field>> stream = entrySet.stream();

		if (predicate != null) {
			stream = stream.filter(entry -> predicate.test(entry.getKey()));
		}

		return stream.collect(
			Collectors.toMap(
				Map.Entry::getKey,
				entry -> {
					Field field = entry.getValue();

					String[] values = field.getValues();

					if (values == null) {
						return null;
					}

					if (values.length == 1) {
						return values[0];
					}

					return String.valueOf(Arrays.asList(values));
				}));
	}

}