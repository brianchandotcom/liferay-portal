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

package com.liferay.jenkins.results.parser.junit;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Yi-Chen Tsai
 */
@RunWith(Parameterized.class)
public class CustomScriptTest {

	@Parameters(name = "{0}")
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