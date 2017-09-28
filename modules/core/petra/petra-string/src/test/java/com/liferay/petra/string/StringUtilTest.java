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

package com.liferay.petra.string;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Alexander Chow
 * @author Shuyang Zhou
 * @author Hugo Huijser
 */
public class StringUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testSplit() {
		Assert.assertEquals(
			Arrays.asList(new String[] {"Alice", "Bob", "Charlie"}),
			StringUtil.split("Alice,Bob,Charlie"));
		Assert.assertEquals(
			Arrays.asList(new String[] {"First", "Second", "Third"}),
			StringUtil.split("First;Second;Third", ';'));
		Assert.assertEquals(
			Arrays.asList(new String[] {"One", "Two", "Three"}),
			StringUtil.split("OnexTwoxThree", 'x'));
	}

}