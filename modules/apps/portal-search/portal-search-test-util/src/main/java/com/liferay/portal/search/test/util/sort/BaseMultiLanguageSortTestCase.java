/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.sort;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelper;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

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
	public void testJapanKatakana() {
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
			new String[] {
				"a", "é", "d", "c", "cu", "ch", "ll", "ña", "nu", "p", "e", "á"
			},
			"[a, á, c, ch, cu, d, e, é, ll, nu, ña, p]");
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
						searchContext.setLocale(locale);
						searchContext.setSorts(sorts);
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

		String fieldNameSortable = StringBundler.concat(
			fieldName, StringPool.UNDERLINE, LocaleUtil.toLanguageId(locale),
			StringPool.UNDERLINE, Field.SORTABLE_FIELD_SUFFIX);

		addDocuments(
			value -> DocumentCreationHelpers.twoKeywords(
				fieldName, value, fieldNameSortable, value),
			values);
		assertOrder(
			new Sort[] {new Sort(fieldNameSortable, Sort.STRING_TYPE, false)},
			fieldName, expected, locale);
	}

}