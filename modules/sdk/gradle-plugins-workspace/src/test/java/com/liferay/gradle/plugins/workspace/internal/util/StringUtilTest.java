/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		Assert.assertEquals(
			"-tt-tes-ttt", StringUtil.getDockerSafeName("-TTTesTTT"));
		Assert.assertEquals(
			"b2b-new-quote-process",
			StringUtil.getDockerSafeName("B2BNewQuoteProcess"));
		Assert.assertEquals(
			"b2b-new-quote-process",
			StringUtil.getDockerSafeName("b2BNewQuoteProcess"));
		Assert.assertEquals("t-est", StringUtil.getDockerSafeName("TEst"));
		Assert.assertEquals("test", StringUtil.getDockerSafeName("Test"));
		Assert.assertEquals("test", StringUtil.getDockerSafeName("test"));
		Assert.assertEquals(
			"test-test", StringUtil.getDockerSafeName("TestTest"));
		Assert.assertEquals(
			"test-test-test", StringUtil.getDockerSafeName("TestTestTest"));
		Assert.assertEquals(
			"tt-tes-ttt", StringUtil.getDockerSafeName("TTTesTTT"));
	}

}