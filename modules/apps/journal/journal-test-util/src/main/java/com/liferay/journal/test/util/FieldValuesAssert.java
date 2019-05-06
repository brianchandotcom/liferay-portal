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

package com.liferay.journal.test.util;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author     André de Oliveira
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.portal.search.test.util.FieldValuesAssert}
 */
@Deprecated
public class FieldValuesAssert {

	public static void assertFieldValues(
		Map<String, String> expected, String prefix, Document document,
		String message) {

		AssertUtils.assertEquals(
			message, expected, _getFieldValues(prefix, document));
	}

	private static Map<String, String> _getFieldValues(
		String prefix, Document document) {

		Map<String, Field> fieldsMap = document.getFields();

		Set<Map.Entry<String, Field>> entrySet = fieldsMap.entrySet();

		Stream<Map.Entry<String, Field>> entries = entrySet.stream();

		Stream<Map.Entry<String, Field>> prefixedEntries = entries.filter(
			entry -> {
				String name = entry.getKey();

				return name.startsWith(prefix);
			});

		return prefixedEntries.collect(
			Collectors.toMap(
				Map.Entry::getKey,
				entry -> {
					Field field = entry.getValue();

					return field.getValue();
				}));
	}

}