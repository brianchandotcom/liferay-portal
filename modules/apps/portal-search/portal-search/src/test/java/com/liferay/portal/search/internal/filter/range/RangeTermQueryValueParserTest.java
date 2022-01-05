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