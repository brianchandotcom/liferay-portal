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

package com.liferay.document.library.repository.search.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Pastor
 */
public class KeywordsUtilTest {

	@Test
	public void testEscapeEspecial() {
		Assert.assertEquals(
			"\\{abc \\&& def\\}", KeywordsUtil.escape("{abc && def}"));
	}

	@Test
	public void testEscapeMultipleBracket() {
		Assert.assertEquals("abc\\{", KeywordsUtil.escape("abc{"));
	}

	@Test
	public void testEscapeNoEspecialCharacters() {
		Assert.assertEquals("abc", KeywordsUtil.escape("abc"));
	}

	@Test
	public void testToFuzzyFuzzyText() {
		Assert.assertEquals("abc~", KeywordsUtil.toFuzzy("abc~"));
	}

	@Test
	public void testToFuzzyNonfuzzyText() {
		Assert.assertEquals("abc~", KeywordsUtil.toFuzzy("abc"));
	}

	@Test
	public void testToFuzzyNullText() {
		Assert.assertNull(KeywordsUtil.toFuzzy(null));
	}

	@Test
	public void testToWildcardNullText() {
		Assert.assertNull(KeywordsUtil.toWildcard(null));
	}

	@Test
	public void testToWildcardSimpleText() {
		Assert.assertEquals("abc*", KeywordsUtil.toWildcard("abc"));
	}

	@Test
	public void testToWildcardWildcardText() {
		Assert.assertEquals("abc*", KeywordsUtil.toWildcard("abc*"));
	}

}