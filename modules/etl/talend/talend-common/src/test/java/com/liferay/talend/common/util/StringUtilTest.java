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

package com.liferay.talend.common.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Matchers;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Beslic
 */
public class StringUtilTest {

	@Test
	public void testStripPrefix() {
		Set<String> testSet = new HashSet<>(
			Arrays.asList(
				"prefixTest1", "prefixTest2", "prefixTest3", "Test4"));

		testSet = StringUtil.stripPrefix("prefix", testSet);

		Assert.assertEquals(
			"Transformed string collection size", 4, testSet.size());

		Assert.assertThat(
			testSet, Matchers.hasItems("Test1", "Test2", "Test3", "Test4"));
	}

}