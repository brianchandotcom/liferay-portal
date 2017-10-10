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

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Alexander Chow
 * @author Shuyang Zhou
 * @author Hugo Huijser
 * @author Preston Crary
 */
public class StringUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Test
	public void testConstructor() {
		new StringUtil();
	}

	@Test
	public void testSplit() {
		Assert.assertSame(Collections.emptyList(), StringUtil.split(null));
		Assert.assertSame(
			Collections.emptyList(), StringUtil.split(StringPool.BLANK));
		Assert.assertSame(
			Collections.emptyList(), StringUtil.split(StringPool.SPACE));

		Assert.assertEquals(
			Collections.emptyList(), StringUtil.split(StringPool.COMMA));
		Assert.assertEquals(Collections.emptyList(), StringUtil.split(",,,"));

		Assert.assertEquals(
			Collections.singletonList("test"), StringUtil.split("test"));
		Assert.assertEquals(
			Collections.singletonList("test"), StringUtil.split("test,"));
		Assert.assertEquals(
			Collections.singletonList("test"), StringUtil.split(",test"));

		Assert.assertEquals(
			Arrays.asList("test1", "test2"),
			StringUtil.split("test1-test2", CharPool.DASH));
	}

}