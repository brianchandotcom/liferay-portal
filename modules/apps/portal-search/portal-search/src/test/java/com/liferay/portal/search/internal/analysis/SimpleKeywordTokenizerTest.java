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

package com.liferay.portal.search.internal.analysis;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class SimpleKeywordTokenizerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testJapaneseIdeographicSpace() {
		String ideographicSpace = "\u3000";

		_assertTokenize(ideographicSpace, "[]");
		_assertTokenize("simple" + ideographicSpace + "test", "[simple, test]");
		_assertTokenize(
			"\"simple\"" + ideographicSpace + "\"test\"",
			"[\"simple\", \"test\"]");
		_assertTokenize(
			StringBundler.concat(
				"This", ideographicSpace, "is \"a", ideographicSpace,
				"simple\"", ideographicSpace, "token", ideographicSpace,
				"\"test\""),
			"[This, is, \"a simple\", token, \"test\"]");
	}

	@Test
	public void testRequiresTokenization() {
		Assert.assertTrue(requiresTokenization("This is a simple test"));
		Assert.assertTrue(requiresTokenization("This \"is a simple\" test"));
		Assert.assertFalse(requiresTokenization("\"is a simple\""));
	}

	@Test
	public void testTokenize() {
		_assertTokenize(
			"This is a simple token test",
			"[This, is, a, simple, token, test]");
	}

	@Test(expected = NullPointerException.class)
	public void testTokenizeNull() {
		simpleKeywordTokenizer.tokenize(null);
	}

	@Test
	public void testTokenizeStringBlank() {
		_assertTokenize(StringPool.BLANK, "[]");
	}

	@Test
	public void testTokenizeStringNull() {
		_assertTokenize(StringPool.NULL, "[null]");
	}

	@Test
	public void testTokenizeWithQuote() {
		_assertTokenize(
			"This is a \"simple token\" test",
			"[This, is, a, \"simple token\", test]");

		_assertTokenize(
			"This \"is a\" simple token test",
			"[This, \"is a\", simple, token, test]");

		_assertTokenize(
			"\"This is a token test\"", "[\"This is a token test\"]");
	}

	@Test
	public void testTokenizeWithQuoteAndMixedSpace() {
		_assertTokenize(
			"This   is  a \"simple token\"   test",
			"[This, is, a, \"simple token\", test]");

		_assertTokenize(
			"This  is a \"simple   token\"  test",
			"[This, is, a, \"simple   token\", test]");
	}

	@Test
	public void testTokenizeWithSeveralQuotes() {
		_assertTokenize(
			"\"   This is   \"   a   \"   token test   \"",
			"[\"   This is   \", a, \"   token test   \"]");
	}

	protected boolean requiresTokenization(String string) {
		return simpleKeywordTokenizer.requiresTokenization(string);
	}

	protected final SimpleKeywordTokenizer simpleKeywordTokenizer =
		new SimpleKeywordTokenizer();

	private void _assertTokenize(String string, String expected) {
		List<String> tokens = simpleKeywordTokenizer.tokenize(string);

		Assert.assertEquals(expected, tokens.toString());
	}

}