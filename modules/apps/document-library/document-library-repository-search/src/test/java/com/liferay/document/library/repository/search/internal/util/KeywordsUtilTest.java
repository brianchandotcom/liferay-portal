/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.search.internal.util;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Miguel Pastor
 */
public class KeywordsUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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