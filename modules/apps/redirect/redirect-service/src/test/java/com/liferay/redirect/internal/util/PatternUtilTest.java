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

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.redirect.model.RedirectPatternEntry;

import java.util.List;
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
		List<RedirectPatternEntry> redirectPatternEntries = PatternUtil.parse(
			new String[] {"^xyz abc"});

		Assert.assertEquals(
			"^xyz", _getFirstPatternString(redirectPatternEntries));
		Assert.assertEquals(
			redirectPatternEntries.toString(), 1,
			redirectPatternEntries.size());
	}

	@Test
	public void testCaretPattern() {
		List<RedirectPatternEntry> redirectPatternEntries = PatternUtil.parse(
			new String[] {"^xyz abc"});

		Assert.assertEquals(
			"^xyz", _getFirstPatternString(redirectPatternEntries));
		Assert.assertEquals(
			redirectPatternEntries.toString(), 1,
			redirectPatternEntries.size());
	}

	@Test
	public void testCaretSlashPattern() {
		List<RedirectPatternEntry> redirectPatternEntries = PatternUtil.parse(
			new String[] {"^/xyz abc"});

		Assert.assertEquals(
			"^xyz", _getFirstPatternString(redirectPatternEntries));
		Assert.assertEquals(
			redirectPatternEntries.toString(), 1,
			redirectPatternEntries.size());
	}

	@Test
	public void testEmptyPatternOrEmptyReplacement() {
		Assert.assertTrue(
			ListUtil.isEmpty(PatternUtil.parse(new String[] {" xyz"})));
		Assert.assertTrue(
			ListUtil.isEmpty(PatternUtil.parse(new String[] {"xyz "})));
		Assert.assertTrue(
			ListUtil.isEmpty(PatternUtil.parse(new String[] {"xyz"})));
	}

	@Test
	public void testEmptyPatterns() {
		Assert.assertTrue(ListUtil.isEmpty(PatternUtil.parse(new String[0])));
	}

	@Test(expected = PatternSyntaxException.class)
	public void testInvalidRegexPattern() {
		PatternUtil.parse(new String[] {"*** a"});
	}

	@Test
	public void testSlashPattern() {
		List<RedirectPatternEntry> redirectPatternEntries = PatternUtil.parse(
			new String[] {"/xyz abc"});

		Assert.assertEquals(
			"^xyz", _getFirstPatternString(redirectPatternEntries));
		Assert.assertEquals(
			redirectPatternEntries.toString(), 1,
			redirectPatternEntries.size());
	}

	@Test
	public void testUnanchoredPattern() {
		List<RedirectPatternEntry> redirectPatternEntries = PatternUtil.parse(
			new String[] {"xyz abc"});

		Assert.assertEquals(
			"^xyz", _getFirstPatternString(redirectPatternEntries));
		Assert.assertEquals(
			redirectPatternEntries.toString(), 1,
			redirectPatternEntries.size());
	}

	private String _getFirstPatternString(
		List<RedirectPatternEntry> redirectPatternEntries) {

		RedirectPatternEntry redirectPatternEntry = redirectPatternEntries.get(
			0);

		Pattern pattern = redirectPatternEntry.getPattern();

		return pattern.pattern();
	}

}