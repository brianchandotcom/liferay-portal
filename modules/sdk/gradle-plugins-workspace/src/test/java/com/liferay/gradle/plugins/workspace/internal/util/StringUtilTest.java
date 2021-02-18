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

package com.liferay.gradle.plugins.workspace.internal.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Gregory Amerson
 */
public class StringUtilTest {

	@Test
	public void testiFromCamelCaseToHyphenated() throws Exception {
		Assert.assertEquals("test", StringUtil.getDockerSafeName("test"));
		Assert.assertEquals("test", StringUtil.getDockerSafeName("Test"));
		Assert.assertEquals("t-est", StringUtil.getDockerSafeName("TEst"));
		Assert.assertEquals(
			"test-test", StringUtil.getDockerSafeName("TestTest"));
		Assert.assertEquals(
			"test-test-test", StringUtil.getDockerSafeName("TestTestTest"));
		Assert.assertEquals(
			"b2b-new-quote-process",
			StringUtil.getDockerSafeName("B2BNewQuoteProcess"));
		Assert.assertEquals(
			"b2b-new-quote-process",
			StringUtil.getDockerSafeName("b2BNewQuoteProcess"));
		Assert.assertEquals(
			"tt-tes-ttt", StringUtil.getDockerSafeName("TTTesTTT"));
		Assert.assertEquals(
			"-tt-tes-ttt", StringUtil.getDockerSafeName("-TTTesTTT"));
	}

}