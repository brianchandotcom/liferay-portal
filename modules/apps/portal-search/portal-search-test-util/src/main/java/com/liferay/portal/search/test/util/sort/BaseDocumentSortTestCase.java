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

package com.liferay.portal.search.test.util.sort;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.internal.SortFactoryImpl;
import com.liferay.portal.search.test.util.document.BaseDocumentTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public abstract class BaseDocumentSortTestCase extends BaseDocumentTestCase {

	@Test
	public void testDoubleSort() throws Exception {
		addDocuments(
			screenName -> document -> populate(
				document, FIELD_DOUBLE, screenName),
			SCREEN_NAMES);

		assertSort(
			"Smith", FIELD_DOUBLE, Sort.DOUBLE_TYPE,
			_SCREEN_NAMES_DOUBLE_ORDER);
	}

	@Test
	public void testDoubleSortIgnoresScores() throws Exception {
		addDocuments(
			screenName -> document -> populate(
				document, FIELD_DOUBLE, screenName),
			SCREEN_NAMES);

		for (String keywords : _KEYWORDS) {
			assertSort(
				keywords, FIELD_DOUBLE, Sort.DOUBLE_TYPE,
				_SCREEN_NAMES_DOUBLE_ORDER);
		}

		for (String keywords : _KEYWORDS_ODD) {
			assertSort(
				keywords, FIELD_DOUBLE, Sort.DOUBLE_TYPE,
				_SCREEN_NAMES_ODD_DOUBLE_ORDER);
		}
	}

	@Test
	public void testFloatSort() throws Exception {
		addDocuments(
			screenName -> document -> populate(
				document, FIELD_FLOAT, screenName),
			SCREEN_NAMES);

		assertSort(
			"Smith", FIELD_FLOAT, Sort.FLOAT_TYPE, _SCREEN_NAMES_FLOAT_ORDER);
	}

	@Test
	public void testFloatSortIgnoresScores() throws Exception {
		addDocuments(
			screenName -> document -> populate(
				document, FIELD_FLOAT, screenName),
			SCREEN_NAMES);

		for (String keywords : _KEYWORDS) {
			assertSort(
				keywords, FIELD_FLOAT, Sort.FLOAT_TYPE,
				_SCREEN_NAMES_FLOAT_ORDER);
		}

		for (String keywords : _KEYWORDS_ODD) {
			assertSort(
				keywords, FIELD_FLOAT, Sort.FLOAT_TYPE,
				_SCREEN_NAMES_ODD_FLOAT_ORDER);
		}
	}

	@Test
	public void testIntegerSort() throws Exception {
		addDocuments(
			screenName -> document -> populate(
				document, FIELD_INTEGER, screenName),
			SCREEN_NAMES);

		assertSort(
			"Smith", FIELD_INTEGER, Sort.INT_TYPE, _SCREEN_NAMES_INTEGER_ORDER);
	}

	@Test
	public void testIntegerSortIgnoresScores() throws Exception {
		addDocuments(
			screenName -> document -> populate(
				document, FIELD_INTEGER, screenName),
			SCREEN_NAMES);

		for (String keywords : _KEYWORDS) {
			assertSort(
				keywords, FIELD_INTEGER, Sort.INT_TYPE,
				_SCREEN_NAMES_INTEGER_ORDER);
		}

		for (String keywords : _KEYWORDS_ODD) {
			assertSort(
				keywords, FIELD_INTEGER, Sort.INT_TYPE,
				_SCREEN_NAMES_ODD_INTEGER_ORDER);
		}
	}

	@Test
	public void testLongSort() throws Exception {
		addDocuments(
			screenName -> document -> populate(
				document, FIELD_LONG, screenName),
			SCREEN_NAMES);

		assertSort(
			"Smith", FIELD_LONG, Sort.LONG_TYPE, _SCREEN_NAMES_LONG_ORDER);
	}

	@Test
	public void testLongSortIgnoresScores() throws Exception {
		addDocuments(
			screenName -> document -> populate(
				document, FIELD_LONG, screenName),
			SCREEN_NAMES);

		for (String keywords : _KEYWORDS) {
			assertSort(
				keywords, FIELD_LONG, Sort.LONG_TYPE, _SCREEN_NAMES_LONG_ORDER);
		}

		for (String keywords : _KEYWORDS_ODD) {
			assertSort(
				keywords, FIELD_LONG, Sort.LONG_TYPE,
				_SCREEN_NAMES_ODD_LONG_ORDER);
		}
	}

	protected void assertSort(String keywords, Sort sort, String... screenNames)
		throws Exception {

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> searchContext.setSorts(sort));

				indexingTestHelper.setQuery(getQuery(keywords));

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> {
						List<String> searchResultValues = new ArrayList<>(
							screenNames.length);
						List<String> screenNamesList = new ArrayList<>(
							screenNames.length);

						for (int i = 0; i < hits.getLength(); i++) {
							Document document = hits.doc(i);

							searchResultValues.add(
								document.get(sort.getFieldName()));

							screenNamesList.add(document.get("screenName"));
						}

						Assert.assertEquals(
							StringUtil.merge(searchResultValues),
							StringUtil.merge(screenNames),
							StringUtil.merge(screenNamesList));
					});
			});
	}

	protected void assertSort(
			String keywords, String field, int type,
			String... ascendingScreenNames)
		throws Exception {

		SortFactory sortFactory = new SortFactoryImpl();

		assertSort(
			keywords, sortFactory.create(field, type, false),
			ascendingScreenNames);

		String[] descendingScreenNames = Arrays.copyOf(
			ascendingScreenNames, ascendingScreenNames.length);

		ArrayUtil.reverse(descendingScreenNames);

		assertSort(
			keywords, sortFactory.create(field, type, true),
			descendingScreenNames);
	}

	private static final String[] _KEYWORDS = {
		"sixth second first fourth fifth third",
		"second first fourth fifth third sixth",
		"first fourth fifth third sixth second",
		"fourth fifth third sixth second first",
		"fifth third sixth second first fourth",
		"third sixth second first fourth fifth"
	};

	private static final String[] _KEYWORDS_ODD = {
		"first fifth third", "fifth third first", "third first fifth",
		"first third fifth", "fifth first third"
	};

	private static final String[] _SCREEN_NAMES_DOUBLE_ORDER = {
		"firstuser", "fourthuser", "seconduser", "fifthuser", "thirduser",
		"sixthuser"
	};

	private static final String[] _SCREEN_NAMES_FLOAT_ORDER = {
		"sixthuser", "fifthuser", "fourthuser", "thirduser", "seconduser",
		"firstuser"
	};

	private static final String[] _SCREEN_NAMES_INTEGER_ORDER = {
		"sixthuser", "fifthuser", "fourthuser", "thirduser", "seconduser",
		"firstuser"
	};

	private static final String[] _SCREEN_NAMES_LONG_ORDER = {
		"firstuser", "seconduser", "thirduser", "fourthuser", "fifthuser",
		"sixthuser"
	};

	private static final String[] _SCREEN_NAMES_ODD_DOUBLE_ORDER = {
		"firstuser", "fifthuser", "thirduser"
	};

	private static final String[] _SCREEN_NAMES_ODD_FLOAT_ORDER = {
		"fifthuser", "thirduser", "firstuser"
	};

	private static final String[] _SCREEN_NAMES_ODD_INTEGER_ORDER = {
		"fifthuser", "thirduser", "firstuser"
	};

	private static final String[] _SCREEN_NAMES_ODD_LONG_ORDER = {
		"firstuser", "thirduser", "fifthuser"
	};

}