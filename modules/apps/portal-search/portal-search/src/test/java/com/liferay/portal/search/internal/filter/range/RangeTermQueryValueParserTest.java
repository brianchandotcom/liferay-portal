/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.filter.range;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class RangeTermQueryValueParserTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		rangeTermQueryValueParser = new RangeTermQueryValueParser();
	}

	@Test
	public void testParseDateRangeIncludesLower() {
		RangeTermQueryValue rangeTermQueryValue =
			rangeTermQueryValueParser.parse("[now/d now+1d/d[");

		Assert.assertNotNull(rangeTermQueryValue);

		_assertIncludesLower(rangeTermQueryValue);
		_assertDoesNotIncludeUpper(rangeTermQueryValue);
		_assertBounds(rangeTermQueryValue, "now/d", "now+1d/d");
	}

	@Test
	public void testParseDateRangeIncludesLowerIncludesUpper() {
		RangeTermQueryValue rangeTermQueryValue =
			rangeTermQueryValueParser.parse("[now/d now+1d/d]");

		Assert.assertNotNull(rangeTermQueryValue);

		_assertIncludesLower(rangeTermQueryValue);
		_assertIncludesUpper(rangeTermQueryValue);
		_assertBounds(rangeTermQueryValue, "now/d", "now+1d/d");
	}

	@Test
	public void testParseDateRangeIncludesUpper() {
		RangeTermQueryValue rangeTermQueryValue =
			rangeTermQueryValueParser.parse("]now/d now+1d/d]");

		Assert.assertNotNull(rangeTermQueryValue);

		_assertDoesNotIncludeLower(rangeTermQueryValue);
		_assertIncludesUpper(rangeTermQueryValue);
		_assertBounds(rangeTermQueryValue, "now/d", "now+1d/d");
	}

	@Test
	public void testParseDateRangeInvalidEnd() {
		RangeTermQueryValue rangeTermQueryValue =
			rangeTermQueryValueParser.parse("[now/d now+1d/d");

		Assert.assertNull(rangeTermQueryValue);
	}

	@Test
	public void testParseDateRangeInvalidStart() {
		RangeTermQueryValue rangeTermQueryValue =
			rangeTermQueryValueParser.parse("now/d now+1d/d[");

		Assert.assertNull(rangeTermQueryValue);
	}

	@Test
	public void testParseDateRangeNoInclude() {
		RangeTermQueryValue rangeTermQueryValue =
			rangeTermQueryValueParser.parse("]now/d now+1d/d[");

		Assert.assertNotNull(rangeTermQueryValue);

		Assert.assertFalse(rangeTermQueryValue.isIncludesLower());
		_assertDoesNotIncludeUpper(rangeTermQueryValue);
		_assertBounds(rangeTermQueryValue, "now/d", "now+1d/d");
	}

	protected RangeTermQueryValueParser rangeTermQueryValueParser;

	private void _assertBounds(
		RangeTermQueryValue rangeTermQueryValue, String lowerBound,
		String upperBound) {

		Assert.assertEquals(lowerBound, rangeTermQueryValue.getLowerBound());
		Assert.assertEquals(upperBound, rangeTermQueryValue.getUpperBound());
	}

	private void _assertDoesNotIncludeLower(
		RangeTermQueryValue rangeTermQueryValue) {

		Assert.assertFalse(rangeTermQueryValue.isIncludesLower());
	}

	private void _assertDoesNotIncludeUpper(
		RangeTermQueryValue rangeTermQueryValue) {

		Assert.assertFalse(rangeTermQueryValue.isIncludesUpper());
	}

	private void _assertIncludesLower(RangeTermQueryValue rangeTermQueryValue) {
		Assert.assertTrue(rangeTermQueryValue.isIncludesLower());
	}

	private void _assertIncludesUpper(RangeTermQueryValue rangeTermQueryValue) {
		Assert.assertTrue(rangeTermQueryValue.isIncludesUpper());
	}

}