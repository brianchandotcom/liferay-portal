/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		doubleArrays.put(screenName, doubleArray);
		floatArrays.put(screenName, floatArray);
		integerArrays.put(screenName, integerArray);
		longArrays.put(screenName, longArray);
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

		doubles.put(screenName, numberDouble);
		floats.put(screenName, floatNumber);
		integers.put(screenName, numberInteger);
		longs.put(screenName, longNumber);
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

	protected void populate(
		Document document, String field, String screenName) {

		document.addKeyword(
			"firstName", screenName.replaceFirst("user", StringPool.BLANK));
		document.addKeyword("lastName", "Smith");

		document.addText("screenName", screenName);

		if (Objects.equals(field, FIELD_DOUBLE)) {
			document.addNumber(field, doubles.get(screenName));
		}
		else if (Objects.equals(field, FIELD_FLOAT)) {
			document.addNumber(field, floats.get(screenName));
		}
		else if (Objects.equals(field, FIELD_INTEGER)) {
			document.addNumber(field, integers.get(screenName));
		}
		else if (Objects.equals(field, FIELD_LONG)) {
			document.addNumber(field, longs.get(screenName));
		}
		else if (Objects.equals(field, FIELD_DOUBLE_ARRAY)) {
			document.addNumber(field, doubleArrays.get(screenName));
		}
		else if (Objects.equals(field, FIELD_FLOAT_ARRAY)) {
			document.addNumber(field, floatArrays.get(screenName));
		}
		else if (Objects.equals(field, FIELD_INTEGER_ARRAY)) {
			document.addNumber(field, integerArrays.get(screenName));
		}
		else if (Objects.equals(field, FIELD_LONG_ARRAY)) {
			document.addNumber(field, longArrays.get(screenName));
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

	protected static final Map<String, Double[]> doubleArrays = new HashMap<>();
	protected static final Map<String, Double> doubles = new HashMap<>();
	protected static final Map<String, Float[]> floatArrays = new HashMap<>();
	protected static final Map<String, Float> floats = new HashMap<>();
	protected static final Map<String, Integer[]> integerArrays =
		new HashMap<>();
	protected static final Map<String, Integer> integers = new HashMap<>();
	protected static final Map<String, Long[]> longArrays = new HashMap<>();
	protected static final Map<String, Long> longs = new HashMap<>();

}