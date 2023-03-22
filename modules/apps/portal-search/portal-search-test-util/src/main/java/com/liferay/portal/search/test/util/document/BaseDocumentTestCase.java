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
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.junit.BeforeClass;

/**
 * @author Wade Cao
 */
public abstract class BaseDocumentTestCase extends BaseIndexingTestCase {

	@BeforeClass
	public static void setUpClassTestData() throws Exception {
		populateNumberArrays();
		populateNumbers();
	}

	protected static Query getQuery(String keywords) {
		BooleanQueryImpl booleanQueryImpl = new BooleanQueryImpl();

		booleanQueryImpl.add(
			new MatchQuery("firstName", keywords), BooleanClauseOccur.SHOULD);
		booleanQueryImpl.add(
			new MatchQuery("lastName", keywords), BooleanClauseOccur.SHOULD);

		return booleanQueryImpl;
	}

	protected static void populateNumberArrays() {
		populateNumberArrays(
			SCREEN_NAMES[0], new Double[] {1e-11, 2e-11, 3e-11},
			new Float[] {8e-5F, 8e-5F, 8e-5F}, new Integer[] {1, 2, 3},
			new Long[] {-3L, -2L, -1L});

		populateNumberArrays(
			SCREEN_NAMES[1], new Double[] {1e-11, 2e-11, 5e-11},
			new Float[] {9e-5F, 8e-5F, 7e-5F}, new Integer[] {1, 3, 4},
			new Long[] {-3L, -2L, -2L});

		populateNumberArrays(
			SCREEN_NAMES[2], new Double[] {1e-11, 3e-11, 2e-11},
			new Float[] {9e-5F, 8e-5F, 9e-5F}, new Integer[] {2, 1, 1},
			new Long[] {-3L, -3L, -1L});

		populateNumberArrays(
			SCREEN_NAMES[3], new Double[] {1e-11, 2e-11, 4e-11},
			new Float[] {9e-5F, 9e-5F, 7e-5F}, new Integer[] {1, 2, 4},
			new Long[] {-3L, -3L, -2L});

		populateNumberArrays(
			SCREEN_NAMES[4], new Double[] {1e-11, 3e-11, 1e-11},
			new Float[] {9e-5F, 9e-5F, 8e-5F}, new Integer[] {1, 4, 4},
			new Long[] {-4L, -2L, -1L});

		populateNumberArrays(
			SCREEN_NAMES[5], new Double[] {2e-11, 1e-11, 1e-11},
			new Float[] {9e-5F, 9e-5F, 9e-5F}, new Integer[] {2, 1, 2},
			new Long[] {-4L, -2L, -2L});
	}

	protected static void populateNumberArrays(
		String screenName, Double[] doubleArray, Float[] floatArray,
		Integer[] integerArray, Long[] longArray) {

		doubleArraysMap.put(screenName, doubleArray);
		floatArraysMap.put(screenName, floatArray);
		integerArraysMap.put(screenName, integerArray);
		longArraysMap.put(screenName, longArray);
	}

	protected static void populateNumbers() {
		int maxInt = Integer.MAX_VALUE;
		long minLong = Long.MIN_VALUE;

		populateNumbers(SCREEN_NAMES[0], 1e-11, 8e-5F, maxInt, minLong);
		populateNumbers(SCREEN_NAMES[1], 3e-11, 7e-5F, maxInt - 1, minLong + 1);
		populateNumbers(SCREEN_NAMES[2], 5e-11, 6e-5F, maxInt - 2, minLong + 2);
		populateNumbers(SCREEN_NAMES[3], 2e-11, 5e-5F, maxInt - 3, minLong + 3);
		populateNumbers(SCREEN_NAMES[4], 4e-11, 4e-5F, maxInt - 4, minLong + 4);
		populateNumbers(SCREEN_NAMES[5], 6e-11, 3e-5F, maxInt - 5, minLong + 5);
	}

	protected static void populateNumbers(
		String screenName, Double numberDouble, Float floatNumber,
		Integer numberInteger, Long longNumber) {

		doublesMap.put(screenName, numberDouble);
		floatsMap.put(screenName, floatNumber);
		integersMap.put(screenName, numberInteger);
		longsMap.put(screenName, longNumber);
	}

	protected void populate(Document document, String screenName) {
		document.addKeyword(
			"firstName", screenName.replaceFirst("user", StringPool.BLANK));
		document.addKeyword("lastName", "Smith");
		document.addText("screenName", screenName);

		document.addNumber(FIELD_DOUBLE, doublesMap.get(screenName));
		document.addNumber(FIELD_FLOAT, floatsMap.get(screenName));
		document.addNumber(FIELD_INTEGER, integersMap.get(screenName));
		document.addNumber(FIELD_LONG, longsMap.get(screenName));

		document.addNumber(FIELD_DOUBLE_ARRAY, doubleArraysMap.get(screenName));
		document.addNumber(FIELD_FLOAT_ARRAY, floatArraysMap.get(screenName));
		document.addNumber(
			FIELD_INTEGER_ARRAY, integerArraysMap.get(screenName));
		document.addNumber(FIELD_LONG_ARRAY, longArraysMap.get(screenName));
	}

	protected void populate(
		Document document, String field, String screenName) {

		document.addKeyword(
			"firstName", screenName.replaceFirst("user", StringPool.BLANK));
		document.addKeyword("lastName", "Smith");

		document.addText("screenName", screenName);

		if (Objects.equals(field, FIELD_DOUBLE)) {
			document.addNumber(field, doublesMap.get(screenName));
		}
		else if (Objects.equals(field, FIELD_FLOAT)) {
			document.addNumber(field, floatsMap.get(screenName));
		}
		else if (Objects.equals(field, FIELD_INTEGER)) {
			document.addNumber(field, integersMap.get(screenName));
		}
		else if (Objects.equals(field, FIELD_LONG)) {
			document.addNumber(field, longsMap.get(screenName));
		}
		else if (Objects.equals(field, FIELD_DOUBLE_ARRAY)) {
			document.addNumber(field, doubleArraysMap.get(screenName));
		}
		else if (Objects.equals(field, FIELD_FLOAT_ARRAY)) {
			document.addNumber(field, floatArraysMap.get(screenName));
		}
		else if (Objects.equals(field, FIELD_INTEGER_ARRAY)) {
			document.addNumber(field, integerArraysMap.get(screenName));
		}
		else if (Objects.equals(field, FIELD_LONG_ARRAY)) {
			document.addNumber(field, longArraysMap.get(screenName));
		}
	}

	protected static final String FIELD_DOUBLE = "sd";

	protected static final String FIELD_DOUBLE_ARRAY = "md";

	protected static final String FIELD_FLOAT = "sf";

	protected static final String FIELD_FLOAT_ARRAY = "mf";

	protected static final String FIELD_INTEGER = "si";

	protected static final String FIELD_INTEGER_ARRAY = "mi";

	protected static final String FIELD_LONG = "sl";

	protected static final String FIELD_LONG_ARRAY = "ml";

	protected static final String[] SCREEN_NAMES = {
		"firstuser", "seconduser", "thirduser", "fourthuser", "fifthuser",
		"sixthuser"
	};

	protected static final Map<String, Double[]> doubleArraysMap =
		new HashMap<>();
	protected static final Map<String, Double> doublesMap = new HashMap<>();
	protected static final Map<String, Float[]> floatArraysMap =
		new HashMap<>();
	protected static final Map<String, Float> floatsMap = new HashMap<>();
	protected static final Map<String, Integer[]> integerArraysMap =
		new HashMap<>();
	protected static final Map<String, Integer> integersMap = new HashMap<>();
	protected static final Map<String, Long[]> longArraysMap = new HashMap<>();
	protected static final Map<String, Long> longsMap = new HashMap<>();

}