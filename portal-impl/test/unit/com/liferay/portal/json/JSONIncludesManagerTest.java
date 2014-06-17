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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONIncludesManagerUtil;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Igor Spasic
 */
public class JSONIncludesManagerTest {

	@Before
	public void setUp() throws Exception {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		JSONIncludesManagerUtil jsonIncludesManagerUtil =
			new JSONIncludesManagerUtil();

		jsonIncludesManagerUtil.setJSONIncludesManager(
			new JSONIncludesManagerImpl());
	}

	@Test
	public void testExtendsOne() {
		String[] excludes = JSONIncludesManagerUtil.lookupExcludes(
			ExtendsOne.class);

		Assert.assertEquals(1, excludes.length);
		Assert.assertEquals("*", excludes[0]);

		String[] includes = JSONIncludesManagerUtil.lookupIncludes(
			ExtendsOne.class);

		Assert.assertEquals(1, includes.length);
		Assert.assertEquals("ftwo", includes[0]);
	}

	@Test
	public void testExtendsTwo() {
		String[] excludes = JSONIncludesManagerUtil.lookupExcludes(
			ExtendsTwo.class);

		Assert.assertEquals(1, excludes.length);
		Assert.assertEquals("*", excludes[0]);

		String[] includes = JSONIncludesManagerUtil.lookupIncludes(
			ExtendsTwo.class);

		Assert.assertEquals(1, includes.length);
		Assert.assertEquals("ftwo", includes[0]);
	}

	@Test
	public void testFour() {
		String[] excludes = JSONIncludesManagerUtil.lookupExcludes(Four.class);

		Assert.assertEquals(1, excludes.length);
		Assert.assertEquals("*", excludes[0]);

		String[] includes = JSONIncludesManagerUtil.lookupIncludes(Four.class);

		Arrays.sort(includes);

		Assert.assertEquals(2, includes.length);
		Assert.assertEquals("number", includes[0]);
		Assert.assertEquals("value", includes[1]);

		Four four = new Four();

		String json = JSONFactoryUtil.looseSerialize(four);

		Assert.assertTrue(json.contains("number"));
		Assert.assertTrue(json.contains("value"));
	}

	@Test
	public void testOne() {
		String[] excludes = JSONIncludesManagerUtil.lookupExcludes(One.class);

		Assert.assertEquals(1, excludes.length);
		Assert.assertEquals("not", excludes[0]);

		String[] includes = JSONIncludesManagerUtil.lookupIncludes(One.class);

		Assert.assertEquals(1, includes.length);
		Assert.assertEquals("ftwo", includes[0]);
	}

	@Test
	public void testThree() {
		String[] excludes = JSONIncludesManagerUtil.lookupExcludes(Three.class);

		Assert.assertEquals(1, excludes.length);
		Assert.assertEquals("ignore", excludes[0]);

		String[] includes = JSONIncludesManagerUtil.lookupIncludes(Three.class);

		Assert.assertEquals(0, includes.length);
	}

	@Test
	public void testTwo() {
		String[] excludes = JSONIncludesManagerUtil.lookupExcludes(Two.class);

		Assert.assertEquals(1, excludes.length);
		Assert.assertEquals("*", excludes[0]);

		String[] includes = JSONIncludesManagerUtil.lookupIncludes(Two.class);

		Assert.assertEquals(1, includes.length);
		Assert.assertEquals("ftwo", includes[0]);
	}

}