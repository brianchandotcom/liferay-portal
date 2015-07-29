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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class SimpleKeywordTokenizerTest {

	@Test
	public void testRequiresTokenization() {
		SimpleKeywordTokenizer simpleKeywordTokenizer =
			new SimpleKeywordTokenizer();

		Assert.assertTrue(
			simpleKeywordTokenizer.requiresTokenization(
				"This is a simple test"));
		Assert.assertTrue(
			simpleKeywordTokenizer.requiresTokenization(
				"This \"is a simple\" test"));
		Assert.assertFalse(
			simpleKeywordTokenizer.requiresTokenization("\"is a simple\""));
	}

	@Test
	public void testTokenize() {
		SimpleKeywordTokenizer simpleKeywordTokenizer =
			new SimpleKeywordTokenizer();

		List<String> tokens = simpleKeywordTokenizer.tokenize(
			"This is a simple token test");

		Assert.assertEquals(6, tokens.size());
		Assert.assertEquals("This", tokens.get(0));
		Assert.assertEquals("is", tokens.get(1));
		Assert.assertEquals("a", tokens.get(2));
		Assert.assertEquals("simple", tokens.get(3));
		Assert.assertEquals("token", tokens.get(4));
		Assert.assertEquals("test", tokens.get(5));
	}

	@Test
	public void testTokenizeWithQuote() {
		SimpleKeywordTokenizer simpleKeywordTokenizer =
			new SimpleKeywordTokenizer();

		List<String> tokens = simpleKeywordTokenizer.tokenize(
			"This is a \"simple token\" test");

		Assert.assertEquals(5, tokens.size());
		Assert.assertEquals("This", tokens.get(0));
		Assert.assertEquals("is", tokens.get(1));
		Assert.assertEquals("a", tokens.get(2));
		Assert.assertEquals("\"simple token\"", tokens.get(3));
		Assert.assertEquals("test", tokens.get(4));

		List<String> tokens2 = simpleKeywordTokenizer.tokenize(
			"This \"is a\" simple token test");

		Assert.assertEquals(5, tokens.size());
		Assert.assertEquals("This", tokens2.get(0));
		Assert.assertEquals("\"is a\"", tokens2.get(1));
		Assert.assertEquals("simple", tokens2.get(2));
		Assert.assertEquals("token", tokens2.get(3));
		Assert.assertEquals("test", tokens2.get(4));
	}

	@Test
	public void testTokenizeWithQuoteAndMixedSpace() {
		SimpleKeywordTokenizer simpleKeywordTokenizer =
			new SimpleKeywordTokenizer();

		List<String> tokens = simpleKeywordTokenizer.tokenize(
			"This   is  a \"simple token\"   test");

		Assert.assertEquals(5, tokens.size());
		Assert.assertEquals("This", tokens.get(0));
		Assert.assertEquals("is", tokens.get(1));
		Assert.assertEquals("a", tokens.get(2));
		Assert.assertEquals("\"simple token\"", tokens.get(3));
		Assert.assertEquals("test", tokens.get(4));

		List<String> tokens2 = simpleKeywordTokenizer.tokenize(
			"This  is a \"simple   token\"  test");

		Assert.assertEquals(5, tokens2.size());
		Assert.assertEquals("This", tokens2.get(0));
		Assert.assertEquals("is", tokens2.get(1));
		Assert.assertEquals("a", tokens2.get(2));
		Assert.assertEquals("\"simple   token\"", tokens2.get(3));
		Assert.assertEquals("test", tokens2.get(4));
	}

}