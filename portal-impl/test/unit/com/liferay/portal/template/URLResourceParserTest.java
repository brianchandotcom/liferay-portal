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

package com.liferay.portal.template;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class URLResourceParserTest {

	@Test
	public void testNormalizePath() {
		Assert.assertEquals(
			"abc", ClassLoaderResourceParser.normalizePath("abc"));
		Assert.assertEquals(
			"/abc", ClassLoaderResourceParser.normalizePath("/abc"));

		try {
			ClassLoaderResourceParser.normalizePath("//");

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Unable to parse path //", iae.getMessage());
		}

		Assert.assertEquals(
			"abc", ClassLoaderResourceParser.normalizePath("abc/./"));
		Assert.assertEquals(
			"def", ClassLoaderResourceParser.normalizePath("abc/../def"));

		try {
			ClassLoaderResourceParser.normalizePath("../");

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Unable to parse path ../", iae.getMessage());
		}

		Assert.assertEquals(
			"/efg/hij",
			ClassLoaderResourceParser.normalizePath("/abc/../efg/./hij/"));
	}

}