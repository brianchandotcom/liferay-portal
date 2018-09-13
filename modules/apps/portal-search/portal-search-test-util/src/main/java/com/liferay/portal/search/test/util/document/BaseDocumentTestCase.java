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

package com.liferay.portal.search.test.util.document;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Before;

/**
 * @author Wade Cao
 */
public abstract class BaseDocumentTestCase extends BaseIndexingTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		populateNumberArrays();

		populateNumbers();

		addDocuments(
			screenName -> document -> populate(document, screenName),
			getScreenNamesStream());
	}

	protected Stream<String> getScreenNamesStream() {
		Collection<String> screenNames = doubles.keySet();

		return screenNames.stream();
	}

	protected void populate(Document document, String screenName) {
		document.addKeyword(
			"firstName", screenName.replaceFirst("user", StringPool.BLANK));
		document.addKeyword("lastName", "Smith");

		document.addText("screenName", screenName);

		document.addNumber(FIELD_DOUBLE, doubles.get(screenName));
		document.addNumber(FIELD_FLOAT, floats.get(screenName));
		document.addNumber(FIELD_INTEGER, integers.get(screenName));
		document.addNumber(FIELD_LONG, longs.get(screenName));

		document.addNumber(FIELD_DOUBLE_ARRAY, doubleArrays.get(screenName));
		document.addNumber(FIELD_FLOAT_ARRAY, floatArrays.get(screenName));
		document.addNumber(FIELD_INTEGER_ARRAY, integerArrays.get(screenName));
		document.addNumber(FIELD_LONG_ARRAY, longArrays.get(screenName));
	}

	protected void populateNumberArrays() {
		populateNumberArrays(
			"firstuser", new Double[] {1e-11, 2e-11, 3e-11},
			new Float[] {8e-5F, 8e-5F, 8e-5F}, new Integer[] {1, 2, 3},
			new Long[] {-3L, -2L, -1L});

		populateNumberArrays(
			"seconduser", new Double[] {1e-11, 2e-11, 5e-11},
			new Float[] {9e-5F, 8e-5F, 7e-5F}, new Integer[] {1, 3, 4},
			new Long[] {-3L, -2L, -2L});

		populateNumberArrays(
			"thirduser", new Double[] {1e-11, 3e-11, 2e-11},
			new Float[] {9e-5F, 8e-5F, 9e-5F}, new Integer[] {2, 1, 1},
			new Long[] {-3L, -3L, -1L});

		populateNumberArrays(
			"fourthuser", new Double[] {1e-11, 2e-11, 4e-11},
			new Float[] {9e-5F, 9e-5F, 7e-5F}, new Integer[] {1, 2, 4},
			new Long[] {-3L, -3L, -2L});

		populateNumberArrays(
			"fifthuser", new Double[] {1e-11, 3e-11, 1e-11},
			new Float[] {9e-5F, 9e-5F, 8e-5F}, new Integer[] {1, 4, 4},
			new Long[] {-4L, -2L, -1L});

		populateNumberArrays(
			"sixthuser", new Double[] {2e-11, 1e-11, 1e-11},
			new Float[] {9e-5F, 9e-5F, 9e-5F}, new Integer[] {2, 1, 2},
			new Long[] {-4L, -2L, -2L});
	}

	protected void populateNumberArrays(
		String screenName, Double[] doubleArray, Float[] floatArray,
		Integer[] integerArray, Long[] longArray) {

		doubleArrays.put(screenName, doubleArray);
		floatArrays.put(screenName, floatArray);
		integerArrays.put(screenName, integerArray);
		longArrays.put(screenName, longArray);
	}

	protected void populateNumbers() {
		int maxInt = Integer.MAX_VALUE;
		long minLong = Long.MIN_VALUE;

		populateNumbers("firstuser", 1e-11, 8e-5F, maxInt, minLong);

		populateNumbers("seconduser", 3e-11, 7e-5F, maxInt - 1, minLong + 1);

		populateNumbers("thirduser", 5e-11, 6e-5F, maxInt - 2, minLong + 2);

		populateNumbers("fourthuser", 2e-11, 5e-5F, maxInt - 3, minLong + 3);

		populateNumbers("fifthuser", 4e-11, 4e-5F, maxInt - 4, minLong + 4);

		populateNumbers("sixthuser", 6e-11, 3e-5F, maxInt - 5, minLong + 5);
	}

	protected void populateNumbers(
		String screenName, Double numberDouble, Float floatNumber,
		Integer numberInteger, Long longNumber) {

		doubles.put(screenName, numberDouble);
		floats.put(screenName, floatNumber);
		integers.put(screenName, numberInteger);
		longs.put(screenName, longNumber);
	}

	protected static final String FIELD_DOUBLE = "sd";

	protected static final String FIELD_DOUBLE_ARRAY = "md";

	protected static final String FIELD_FLOAT = "sf";

	protected static final String FIELD_FLOAT_ARRAY = "mf";

	protected static final String FIELD_INTEGER = "si";

	protected static final String FIELD_INTEGER_ARRAY = "mi";

	protected static final String FIELD_LONG = "sl";

	protected static final String FIELD_LONG_ARRAY = "ml";

	protected final Map<String, Double[]> doubleArrays = new HashMap<>();
	protected final Map<String, Double> doubles = new HashMap<>();
	protected final Map<String, Float[]> floatArrays = new HashMap<>();
	protected final Map<String, Float> floats = new HashMap<>();
	protected final Map<String, Integer[]> integerArrays = new HashMap<>();
	protected final Map<String, Integer> integers = new HashMap<>();
	protected final Map<String, Long[]> longArrays = new HashMap<>();
	protected final Map<String, Long> longs = new HashMap<>();

}