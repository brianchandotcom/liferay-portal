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

import com.liferay.portal.kernel.util.StringPool;

import java.net.URLEncoder;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Julio Camarero
 */
public class FriendlyURLNormalizerImplTest {

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
	public void testNormalizeWithEncoding1() throws Exception {
		Assert.assertEquals(
			URLEncoder.encode("\u5F15", StringPool.UTF8),
			_friendlyURLNormalizerImpl.normalizeWithEncoding("\u5F15"));
	}

	@Test
	public void testNormalizeWithEncoding2() throws Exception {
		Assert.assertEquals(
			URLEncoder.encode("テスト", StringPool.UTF8),
			_friendlyURLNormalizerImpl.normalizeWithEncoding("テスト"));
	}

	@Test
	public void testNormalizeWithEncoding3() throws Exception {
		String encodedURLPart = URLEncoder.encode("テスト", StringPool.UTF8);

		Assert.assertEquals(
			encodedURLPart + StringPool.SLASH + encodedURLPart,
			_friendlyURLNormalizerImpl.normalizeWithEncoding("テスト/テスト"));
	}

	@Test
	public void testNormalizeWithEncoding4() throws Exception {
		Assert.assertEquals(
			URLEncoder.encode("اختبار", StringPool.UTF8),
			_friendlyURLNormalizerImpl.normalizeWithEncoding("اختبار"));
	}

	@Test
	public void testNormalizeWithEncoding5() throws Exception {
		Assert.assertEquals(
			URLEncoder.encode("\uD801\uDC37", StringPool.UTF8),
			_friendlyURLNormalizerImpl.normalizeWithEncoding("\uD801\uDC37"));
	}

	@Test
	public void testNormalizeWithEncoding6() throws Exception {
		Assert.assertEquals(
			StringPool.DASH,
			_friendlyURLNormalizerImpl.normalizeWithEncoding("(-)"));
	}

	@Test
	public void testNormalizeWithEncoding7() throws Exception {
		Assert.assertEquals(
			StringPool.DASH,
			_friendlyURLNormalizerImpl.normalizeWithEncoding("---"));
	}

	@Test
	public void testNormalizeWithEncoding8() throws Exception {
		Assert.assertEquals(
			URLEncoder.encode(
				String.valueOf(Character.MAX_HIGH_SURROGATE), StringPool.UTF8),
			_friendlyURLNormalizerImpl.normalizeWithEncoding(
				String.valueOf(Character.MAX_HIGH_SURROGATE)));
	}

	@Test
	public void testNormalizeWithoutPeriodsAndSlashes() throws Exception {
		Assert.assertEquals(
			"/./.", _friendlyURLNormalizerImpl.normalizeWithEncoding("/./."));
	}

	@Test
	public void testNormalizeWithPeriodsAndSlashes() throws Exception {
		Assert.assertEquals(
			StringPool.DASH,
			_friendlyURLNormalizerImpl.normalizeWithPeriodsAndSlashes("/./."));
	}

	@Test
	public void testNormalizeWordWithNonASCIICharacters() {
		Assert.assertEquals(
			"wordnc", _friendlyURLNormalizerImpl.normalize("word\u00F1\u00C7"));
	}

	private final FriendlyURLNormalizerImpl _friendlyURLNormalizerImpl =
		new FriendlyURLNormalizerImpl();

}