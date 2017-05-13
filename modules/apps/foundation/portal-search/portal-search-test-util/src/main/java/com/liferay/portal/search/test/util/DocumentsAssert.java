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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;

/**
 * @author André de Oliveira
 */
public class DocumentsAssert {

	public static void assertCount(
		String message, Document[] documents, String field, int expectedCount) {

		if (documents.length == expectedCount) {
			return;
		}

		List<String> actualValues = _getValues(field, documents);

		Assert.assertEquals(
			message + "->" + actualValues, expectedCount, documents.length);
	}

	public static void assertValues(
		String message, Document[] documents, String field,
		List<String> expectedValues) {

		List<String> actualValues = _getValues(field, documents);

		Assert.assertEquals(
			message + "->" + actualValues, expectedValues.toString(),
			actualValues.toString());
	}

	private static List<String> _getValues(
		String field, Document... documents) {

		Stream<Document> documentsStream = Stream.of(documents);

		Stream<String> valuesStream = documentsStream.map(
			document -> document.get(field));

		return valuesStream.collect(Collectors.toList());
	}

}