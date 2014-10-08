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

	@SuppressWarnings("deprecation")
	@Test
	public void testNormalizeWordReplacingChars() {
		Assert.assertEquals(
			"s-nt-nc-ith-r-plac-chars",
			_friendlyURLNormalizerImpl.normalize(
				"sentence with replace chars", new char[] {'e', 'w'}));
	}

	@Test
	public void testNormalizeWordWithNonASCIICharacters() {
		Assert.assertEquals(
			"wordnc", _friendlyURLNormalizerImpl.normalize("word\u00F1\u00C7"));
	}

	private final FriendlyURLNormalizerImpl _friendlyURLNormalizerImpl =
		new FriendlyURLNormalizerImpl();

}