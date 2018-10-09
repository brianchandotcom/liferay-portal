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

package com.liferay.users.admin.web.internal;

import com.liferay.users.admin.web.internal.util.CSSClassNames;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class CSSClassNamesTest {

	@Test
	public void testBasicOutput() {
		Assert.assertEquals(
			"hello", CSSClassNames.build(builder -> builder.add("hello")));

		Assert.assertEquals(
			"hello world",
			CSSClassNames.build(builder -> builder.add("hello", "world")));
	}

	@Test
	public void testConditionalOutput() {
		Assert.assertEquals(
			"hello world",
			CSSClassNames.build(
				builder -> builder.add(
					"hello"
				).add(
					"goodbye", false
				).add(
					"sad", false
				).add(
					"world", true
				)));

		Assert.assertEquals(
			"foo hello world",
			CSSClassNames.build(
				builder -> builder.add(
					"hello", "world"
				).add(
					"cruel", false
				).add(
					"foo", true
				)));
	}

	@Test
	public void testDistinctOutput() {
		Assert.assertEquals(
			"hello world",
			CSSClassNames.build(
				builder -> builder.add(
					"hello", "hello", "world", "world"
				)));

		Assert.assertEquals(
			"hello world",
			CSSClassNames.build(
				builder -> builder.add(
					"hello"
				).add(
					"hello"
				).add(
					"world"
				).add(
					"world"
				)));
	}

	@Test
	public void testSortedOutput() {
		Assert.assertEquals(
			"hello world",
			CSSClassNames.build(
				builder -> builder.add(
					"world", "hello"
				)));

		Assert.assertEquals(
			"alpha beta gamma",
			CSSClassNames.build(
				builder -> builder.add(
					"beta"
				).add(
					"gamma"
				).add(
					"alpha"
				)));
	}

}