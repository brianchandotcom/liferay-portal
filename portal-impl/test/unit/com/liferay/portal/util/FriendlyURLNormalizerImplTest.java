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

package com.liferay.portal.util;

import com.liferay.petra.nio.CharsetEncoderUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.URLEncoder;

import java.nio.charset.CharsetEncoder;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Julio Camarero
 */
public class FriendlyURLNormalizerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testNormalizeBlank() {
		Assert.assertEquals(
			StringPool.BLANK,
			_friendlyURLNormalizerImpl.normalize(StringPool.BLANK));
	}

	@Test
	public void testNormalizeNull() {
		Assert.assertEquals(null, _friendlyURLNormalizerImpl.normalize(null));
	}

	@Test
	public void testNormalizePercentageWithEncoding() throws Exception {
		Assert.assertEquals(
			StringPool.DASH,
			_friendlyURLNormalizerImpl.normalizeWithEncoding("%"));
		Assert.assertEquals(
			"0-", _friendlyURLNormalizerImpl.normalizeWithEncoding("0%"));
		Assert.assertEquals(
			"company-grew-100-last-year",
			_friendlyURLNormalizerImpl.normalizeWithEncoding(
				"Company grew 100% last year"));
		Assert.assertEquals(
			"sample-web-content-title",
			_friendlyURLNormalizerImpl.normalizeWithEncoding(
				"Sample Web % Content Title"));
		Assert.assertEquals(
			"%5E_%5E", _friendlyURLNormalizerImpl.normalizeWithEncoding("^_^"));
		Assert.assertEquals(
			"%5E_%5E",
			_friendlyURLNormalizerImpl.normalizeWithEncoding("%5E_%5E"));
	}

	@Test
	public void testNormalizeSentenceWithBlanks() {
		Assert.assertEquals(
			"sentence-with-blanks",
			_friendlyURLNormalizerImpl.normalize("sentence with blanks"));
	}

	@Test
	public void testNormalizeSentenceWithCapitalLetters() {
		Assert.assertEquals(
			"sentence-with-capital-letters",
			_friendlyURLNormalizerImpl.normalize(
				"Sentence WITH CaPital leTTerS"));
	}

	@Test
	public void testNormalizeSentenceWithDash() {
		Assert.assertEquals(
			"sentence-with-dash",
			_friendlyURLNormalizerImpl.normalize("sentence -with-dash"));
	}

	@Test
	public void testNormalizeSentenceWithDoubleBlanks() {
		Assert.assertEquals(
			"sentence-with-double-blanks",
			_friendlyURLNormalizerImpl.normalize(
				"sentence with   double  blanks"));
	}

	@Test
	public void testNormalizeSentenceWithSpecialCharacters() {
		Assert.assertEquals(
			"sentence-with-special-characters",
			_friendlyURLNormalizerImpl.normalize(
				"sentence&: =()with !@special# %+characters"));
	}

	@Test
	public void testNormalizeSimpleWord() {
		Assert.assertEquals(
			"word", _friendlyURLNormalizerImpl.normalize("word"));
	}

	@Test
	public void testNormalizeSpace() {
		Assert.assertEquals(
			StringPool.SPACE,
			_friendlyURLNormalizerImpl.normalize(StringPool.SPACE));
	}

	@Test
	public void testNormalizeWithEncodingRemove() throws Exception {
		Assert.assertEquals(
			StringPool.DASH,
			_friendlyURLNormalizerImpl.normalizeWithEncoding("(-)"));
		Assert.assertEquals(
			StringPool.DASH,
			_friendlyURLNormalizerImpl.normalizeWithEncoding("---"));
		Assert.assertEquals(
			StringPool.DASH,
			_friendlyURLNormalizerImpl.normalizeWithPeriodsAndSlashes("/./."));
		Assert.assertEquals(
			"/./.", _friendlyURLNormalizerImpl.normalizeWithEncoding("/./."));
	}

	@Test
	public void testNormalizeWithEncodingUnicode() throws Exception {
		_testNormalizeWithEncodingUnicode("\u5F15");
		_testNormalizeWithEncodingUnicode("テスト");
		_testNormalizeWithEncodingUnicode("اختبار");
		_testNormalizeWithEncodingUnicode("\uD801\uDC37");
		_testNormalizeWithEncodingUnicode(
			String.valueOf(Character.MAX_HIGH_SURROGATE));

		String value = "テスト";

		String encodedValue = URLEncoder.encode(value, StringPool.UTF8);

		value = value + StringPool.SLASH + value;

		encodedValue = encodedValue + StringPool.SLASH + encodedValue;

		Assert.assertEquals(
			encodedValue,
			_friendlyURLNormalizerImpl.normalizeWithEncoding(value));
	}

	@Test
	public void testNormalizeWithEncodingUnicodeMalformed() throws Exception {
		CharsetEncoder charsetEncoder = CharsetEncoderUtil.getCharsetEncoder(
			StringPool.UTF8);

		String encodedReplacement = URLEncoder.encode(
			new String(charsetEncoder.replacement(), StringPool.UTF8),
			StringPool.UTF8);

		Assert.assertEquals(
			encodedReplacement + "a" + encodedReplacement,
			_friendlyURLNormalizerImpl.normalizeWithEncoding("\uDBFFA\uDFFF"));
		Assert.assertEquals(
			encodedReplacement + StringPool.DASH + encodedReplacement,
			_friendlyURLNormalizerImpl.normalizeWithEncoding("\uDBFF-\uDFFF"));

		String value = "テスト";

		String encodedValue = URLEncoder.encode(value, StringPool.UTF8);

		Assert.assertEquals(
			encodedReplacement + StringPool.DASH + encodedValue,
			_friendlyURLNormalizerImpl.normalizeWithEncoding(
				"\uDBFF-" + value));
	}

	@Test
	public void testNormalizeWordWithNonasciiCharacters() {
		Assert.assertEquals(
			"wordnc", _friendlyURLNormalizerImpl.normalize("word\u00F1\u00C7"));
	}

	private void _testNormalizeWithEncodingUnicode(String s) throws Exception {
		Assert.assertEquals(
			URLEncoder.encode(s, StringPool.UTF8),
			_friendlyURLNormalizerImpl.normalizeWithEncoding(s));
	}

	private final FriendlyURLNormalizerImpl _friendlyURLNormalizerImpl =
		new FriendlyURLNormalizerImpl();

}