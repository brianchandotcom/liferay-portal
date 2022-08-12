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

package com.liferay.redirect.internal.util;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class PatternUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAnchoredPattern() {
		Map<Pattern, String> patterns = PatternUtil.parsePatterns(
			new String[] {"^xyz abc"});

		Assert.assertEquals(patterns.toString(), 1, patterns.size());
		Assert.assertEquals("^xyz", _getFistPatternString(patterns));
	}

	@Test
	public void testEmptyPatternOrEmptyReplacement() {
		Assert.assertTrue(
			MapUtil.isEmpty(PatternUtil.parsePatterns(new String[] {"xyz"})));
		Assert.assertTrue(
			MapUtil.isEmpty(PatternUtil.parsePatterns(new String[] {" xyz"})));
		Assert.assertTrue(
			MapUtil.isEmpty(PatternUtil.parsePatterns(new String[] {"xyz "})));
	}

	@Test
	public void testEmptyPatterns() {
		Assert.assertTrue(
			MapUtil.isEmpty(PatternUtil.parsePatterns(new String[0])));
	}

	@Test(expected = PatternSyntaxException.class)
	public void testInvalidRegexPattern() {
		PatternUtil.parsePatterns(new String[] {"*** a"});
	}

	@Test
	public void testUnanchoredPattern() {
		Map<Pattern, String> patterns = PatternUtil.parsePatterns(
			new String[] {"xyz abc"});

		Assert.assertEquals(patterns.toString(), 1, patterns.size());
		Assert.assertEquals("^xyz", _getFistPatternString(patterns));
	}

	private String _getFistPatternString(Map<Pattern, String> patterns) {
		Set<Map.Entry<Pattern, String>> entries = patterns.entrySet();

		Iterator<Map.Entry<Pattern, String>> iterator = entries.iterator();

		Map.Entry<Pattern, String> entry = iterator.next();

		Pattern pattern = entry.getKey();

		return pattern.pattern();
	}

}