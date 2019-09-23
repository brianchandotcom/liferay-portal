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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.document.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;

/**
 * @author André de Oliveira
 */
public class DocumentsAssert {

	public static void assertCount(
		String message, com.liferay.portal.kernel.search.Document[] documents,
		String fieldName, int expectedCount) {

		if (documents.length == expectedCount) {
			return;
		}

		List<String> actualValues = _getFieldValueStrings(fieldName, documents);

		Assert.assertEquals(
			message + "->" + actualValues, expectedCount, documents.length);
	}

	public static void assertValues(
		String message, com.liferay.portal.kernel.search.Document[] documents,
		String fieldName, List<String> expectedValues) {

		assertValues(
			message, documents, fieldName, String.valueOf(expectedValues));
	}

	public static void assertValues(
		String message, com.liferay.portal.kernel.search.Document[] documents,
		String fieldName, String expected) {

		List<String> actualValues = _getFieldValueStrings(fieldName, documents);

		Assert.assertEquals(
			message + "->" + actualValues, expected,
			String.valueOf(actualValues));
	}

	public static void assertValues(
		String message, Stream<Document> documents, String fieldName,
		String expected) {

		List<String> actualValues = _getFieldValueStrings(fieldName, documents);

		Assert.assertEquals(
			message + "->" + actualValues, expected,
			String.valueOf(actualValues));
	}

	public static void assertValuesIgnoreRelevance(
		String message, com.liferay.portal.kernel.search.Document[] documents,
		String fieldName, Collection<String> expectedValues) {

		List<String> actualValues = _getFieldValueStrings(fieldName, documents);

		Assert.assertEquals(
			StringBundler.concat(
				message, "->", StringUtil.merge(documents), "->", actualValues),
			_sort(expectedValues), _sort(actualValues));
	}

	public static void assertValuesIgnoreRelevance(
		String message, Stream<Document> documents, String fieldName,
		String expected) {

		List<String> actualValues = _getFieldValueStrings(fieldName, documents);

		Assert.assertEquals(
			message + "->" + actualValues, expected, _sort(actualValues));
	}

	private static List<Object> _getFieldValues(
		String fieldName, com.liferay.portal.kernel.search.Document document) {

		return Arrays.asList((Object[])document.getValues(fieldName));
	}

	private static String _getFieldValueString(List<Object> fieldValues) {
		if (fieldValues.isEmpty()) {
			return StringPool.BLANK;
		}

		if (fieldValues.size() == 1) {
			return String.valueOf(fieldValues.get(0));
		}

		return String.valueOf(fieldValues);
	}

	private static List<String> _getFieldValueStrings(
		Stream<List<Object>> stream) {

		return stream.map(
			DocumentsAssert::_getFieldValueString
		).collect(
			Collectors.toList()
		);
	}

	private static List<String> _getFieldValueStrings(
		String fieldName,
		com.liferay.portal.kernel.search.Document... documents) {

		Stream<com.liferay.portal.kernel.search.Document> stream = Stream.of(
			documents);

		return _getFieldValueStrings(
			stream.map(document -> _getFieldValues(fieldName, document)));
	}

	private static List<String> _getFieldValueStrings(
		String fieldName, Stream<Document> stream) {

		return _getFieldValueStrings(
			stream.map(document -> document.getValues(fieldName)));
	}

	private static String _sort(Collection<String> collection) {
		List<String> list = new ArrayList<>(collection);

		Collections.sort(list);

		return list.toString();
	}

}