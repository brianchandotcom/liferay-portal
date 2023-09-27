/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.web.internal.util;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class CSSClassesTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testBasicOutput() {
		Assert.assertEquals(
			"hello",
			CSSClasses.builder(
				"hello"
			).build());
		Assert.assertEquals(
			"hello world",
			CSSClasses.builder(
				"hello", "world"
			).build());
	}

	@Test
	public void testConditionalOutput() {
		Assert.assertEquals(
			"hello world",
			CSSClasses.builder(
				"hello"
			).add(
				"goodbye", false
			).add(
				"sad", false
			).add(
				"world", true
			).build());
		Assert.assertEquals(
			"foo hello world",
			CSSClasses.builder(
				"hello", "world"
			).add(
				"cruel", false
			).add(
				"foo", true
			).build());
	}

	@Test
	public void testDistinctOutput() {
		Assert.assertEquals(
			"hello world",
			CSSClasses.builder(
				"hello", "hello", "world", "world"
			).build());
		Assert.assertEquals(
			"hello world",
			CSSClasses.builder(
				"hello"
			).add(
				"hello"
			).add(
				"world"
			).add(
				"world"
			).build());
	}

	@Test
	public void testSortedOutput() {
		Assert.assertEquals(
			"hello world",
			CSSClasses.builder(
				"world", "hello"
			).build());
		Assert.assertEquals(
			"alpha beta gamma",
			CSSClasses.builder(
				"beta"
			).add(
				"gamma"
			).add(
				"alpha"
			).build());
	}

}