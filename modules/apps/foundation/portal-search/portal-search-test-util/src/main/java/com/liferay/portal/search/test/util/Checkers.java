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
import com.liferay.portal.kernel.search.Hits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

/**
 * @author André de Oliveira
 */
public class Checkers {

	public static Checker present(
		final String keywords, final String field, final String... values) {

		return new Checker() {

			@Override
			public void check(Hits hits) {
				_assertPresent(hits, keywords, field, values);
			}

		};
	}

	public static Checker size(
		final String keywords, final String field, final int size) {

		return new Checker() {

			@Override
			public void check(Hits hits) {
				_assertSize(hits, keywords, field, size);
			}

		};
	}

	public static Checker values(
		final String keywords, final String field, final String... values) {

		return new Checker() {

			@Override
			public void check(Hits hits) {
				_assertValues(hits, keywords, field, values);
			}

		};
	}

	private static void _assertPresent(
		Hits hits, String keywords, String field, String... expectedValues) {

		Document[] docs = hits.getDocs();

		List<String> values = _getValues(field, docs);

		if (values.containsAll(Arrays.asList(expectedValues))) {
			return;
		}

		Assert.assertEquals(
			keywords + "->" + values,
			String.valueOf(Arrays.asList(expectedValues)),
			String.valueOf(values));
	}

	private static void _assertSize(
		Hits hits, String keywords, String field, int expectedCount) {

		Document[] docs = hits.getDocs();

		if (expectedCount == docs.length) {
			return;
		}

		List<String> values = _getValues(field, docs);

		Assert.assertEquals(
			keywords + "->" + values, expectedCount, docs.length);
	}

	private static void _assertValues(
		Hits hits, String keywords, String field, String... expectedValues) {

		Document[] docs = hits.getDocs();

		List<String> values = _getValues(field, docs);

		Assert.assertEquals(
			keywords + "->" + values,
			String.valueOf(Arrays.asList(expectedValues)),
			String.valueOf(values));
	}

	private static List<String> _getValues(String field, Document... docs) {
		ArrayList<String> values = new ArrayList<>(docs.length);

		for (Document document : docs) {
			values.add(document.get(field));
		}

		return values;
	}

}