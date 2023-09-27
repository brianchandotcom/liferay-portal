/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.junit;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Yi-Chen Tsai
 */
@RunWith(Parameterized.class)
public class CustomScriptTest {

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> getTestNames() {
		return Arrays.asList(new Object[][] {{System.getProperty("testName")}});
	}

	public CustomScriptTest(String testName) {
	}

	@Test
	public void test() {
		String expectedResult = System.getProperty("expected");
		String actualResult = System.getProperty("actual");

		Assert.assertEquals(expectedResult, actualResult);
	}

}