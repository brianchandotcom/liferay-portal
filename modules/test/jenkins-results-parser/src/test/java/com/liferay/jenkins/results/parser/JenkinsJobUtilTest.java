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

package com.liferay.jenkins.results.parser;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kevin Yen
 */
public class JenkinsJobUtilTest {

	@Test
	public void testAppendURL() throws Exception {
		String expectedString = "http://www.example.com/test";

		String actualString = JenkinsJobUtil.appendURL(
			"http://www.example.com", "test");

		Assert.assertEquals(expectedString, actualString);

		actualString = JenkinsJobUtil.appendURL(
			"http://www.example.com", "/test");

		Assert.assertEquals(expectedString, actualString);

		actualString = JenkinsJobUtil.appendURL(
			"http://www.example.com", "test/");

		Assert.assertEquals(expectedString, actualString);

		actualString = JenkinsJobUtil.appendURL(
			"http://www.example.com", "/test/");

		Assert.assertEquals(expectedString, actualString);

		actualString = JenkinsJobUtil.appendURL(
			"http://www.example.com/", "test");

		Assert.assertEquals(expectedString, actualString);

		actualString = JenkinsJobUtil.appendURL(
			"http://www.example.com/", "/test");

		Assert.assertEquals(expectedString, actualString);

		actualString = JenkinsJobUtil.appendURL(
			"http://www.example.com/", "test/");

		Assert.assertEquals(expectedString, actualString);

		actualString = JenkinsJobUtil.appendURL(
			"http://www.example.com/", "/test/");

		Assert.assertEquals(expectedString, actualString);

		expectedString = "http://www.example.com";

		actualString = JenkinsJobUtil.appendURL("http://www.example.com", "");

		Assert.assertEquals(expectedString, actualString);

		actualString = JenkinsJobUtil.appendURL("http://www.example.com", "/");

		Assert.assertEquals(expectedString, actualString);

		actualString = JenkinsJobUtil.appendURL("http://www.example.com", "//");

		Assert.assertEquals(expectedString, actualString);

		actualString = JenkinsJobUtil.appendURL("http://www.example.com/", "");

		Assert.assertEquals(expectedString, actualString);

		actualString = JenkinsJobUtil.appendURL("http://www.example.com/", "/");

		Assert.assertEquals(expectedString, actualString);

		actualString = JenkinsJobUtil.appendURL(
			"http://www.example.com/", "//");

		Assert.assertEquals(expectedString, actualString);
	}

	@Test
	public void testEncodeAuthorizationFields() throws Exception {
		String encodedString = JenkinsJobUtil.encodeAuthorizationFields(
			"test", "test");

		Assert.assertEquals("dGVzdDp0ZXN0", encodedString);
	}

}