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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelper;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;

import org.junit.Test;

/**
 * @author Vagner B.C
 */
public abstract class BaseMultiLanguageSortTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testEnglishCaseSensitive() {
		testLocaleSort(
			LocaleUtil.US, new String[] {"a", "E", "c", "O", "u", "A"},
			"[a, A, c, E, O, u]");
	}

	@Test
	public void testFrance() {
		testLocaleSort(
			LocaleUtil.FRANCE, new String[] {"e", "a", "d", "ç"},
			"[a, ç, d, e]");
	}

	@Test
	public void testGerman() {
		testLocaleSort(
			LocaleUtil.GERMANY,
			new String[] {"a", "x", "ä", "ö", "o", "u", "ź"},
			"[a, ä, o, ö, u, x, ź]");
	}

	@Test
	public void testJapanHiragana() {
		testLocaleSort(
			LocaleUtil.JAPAN, new String[] {"え", "う", "い", "あ", "お"},
			"[あ, い, う, え, お]");
	}

	@Test
	public void testJapanKanji() {
		testLocaleSort(LocaleUtil.JAPAN, new String[] {"色", "赤"}, "[赤, 色]");
	}

	@Test
	public void testJapanKatana() {
		testLocaleSort(
			LocaleUtil.JAPAN, new String[] {"オ", "イ", "ア", "エ", "ウ"},
			"[ア, イ, ウ, エ, オ]");
	}

	@Test
	public void testPolish() {
		testLocaleSort(
			new Locale("pl", "PL"),
			new String[] {"f", "ć", "ź", "d", "ł", "ś", "b"},
			"[b, ć, d, f, ł, ś, ź]");
	}

	@Test
	public void testPortuguese() {
		testLocaleSort(
			LocaleUtil.BRAZIL,
			new String[] {
				"a", "e", "c", "u", "à", "á", "é", "ã", "o", "õ", "ü", "ç"
			},
			"[a, á, à, ã, c, ç, e, é, o, õ, u, ü]");
	}

	@Test
	public void testSpanish() {
		testLocaleSort(
			LocaleUtil.SPAIN,
			new String[] {"a", "d", "c", "ch", "ll", "ñ", "p", "e"},
			"[a, c, ch, d, e, ll, ñ, p]");
	}

	protected void addDocuments(
		Function<Double, DocumentCreationHelper> function, double... values) {

		for (double value : values) {
			addDocument(function.apply(value));
		}
	}

	protected void assertOrder(
		Sort[] sorts, String fieldName, String expected, Locale locale) {

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> {
						searchContext.setSorts(sorts);
						searchContext.setLocale(locale);
					});

				indexingTestHelper.search();

				indexingTestHelper.verify(
					hits -> DocumentsAssert.assertValues(
						indexingTestHelper.getRequestString(), hits.getDocs(),
						fieldName, expected));
			});
	}

	protected void testLocaleSort(
		Locale locale, String[] values, String expected) {

		String fieldName = Field.TITLE;

		String fieldNameSortable =
			fieldName + StringPool.UNDERLINE + LocaleUtil.toLanguageId(locale) +
				_SORTABLE;

		addDocuments(
			value -> DocumentCreationHelpers.singleTextSortable(
				fieldName, fieldNameSortable, value),
			Arrays.asList(values));

		assertOrder(
			new Sort[] {new Sort(fieldNameSortable, Sort.STRING_TYPE, false)},
			fieldName, expected, locale);
	}

	private static final String _SORTABLE = "_sortable";

}