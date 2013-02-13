/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import junit.framework.TestCase;

/**
 * @author Igor Spasic
 */
public class JSONIncludesManagerTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		JSONIncludesManagerUtil jsonIncludesManagerUtil =
			new JSONIncludesManagerUtil();

		jsonIncludesManagerUtil.setJSONIncludesManager
			(new JSONIncludesManagerImpl());
	}

	public void testExtendsOne() {
		String[] excludes = JSONIncludesManagerUtil.lookupExcludes(
			ExtendsOne.class);

		assertEquals(1, excludes.length);
		assertEquals("*", excludes[0]);

		String[] includes = JSONIncludesManagerUtil.lookupIncludes(
			ExtendsOne.class);

		assertEquals(1, includes.length);
		assertEquals("ftwo", includes[0]);
	}

	public void testExtendsTwo() {
		String[] excludes = JSONIncludesManagerUtil.lookupExcludes(
			ExtendsTwo.class);

		assertEquals(1, excludes.length);
		assertEquals("*", excludes[0]);

		String[] includes = JSONIncludesManagerUtil.lookupIncludes(
			ExtendsTwo.class);

		assertEquals(1, includes.length);
		assertEquals("ftwo", includes[0]);
	}

	public void testFour() {
		String[] excludes = JSONIncludesManagerUtil.lookupExcludes(Four.class);

		assertEquals(1, excludes.length);
		assertEquals("*", excludes[0]);

		String[] includes = JSONIncludesManagerUtil.lookupIncludes(Four.class);

		Arrays.sort(includes);

		assertEquals(2, includes.length);
		assertEquals("number", includes[0]);
		assertEquals("value", includes[1]);

		Four four = new Four();

		String json = JSONFactoryUtil.looseSerialize(four);

		assertTrue(json.contains("number"));
		assertTrue(json.contains("value"));
	}

	public void testOne() {
		String[] excludes = JSONIncludesManagerUtil.lookupExcludes(One.class);

		assertEquals(1, excludes.length);
		assertEquals("not", excludes[0]);

		String[] includes = JSONIncludesManagerUtil.lookupIncludes(One.class);

		assertEquals(1, includes.length);
		assertEquals("ftwo", includes[0]);
	}

	public void testThree() {
		String[] excludes = JSONIncludesManagerUtil.lookupExcludes(Three.class);

		assertEquals(1, excludes.length);
		assertEquals("ignore", excludes[0]);

		String[] includes = JSONIncludesManagerUtil.lookupIncludes(Three.class);

		assertEquals(0, includes.length);
	}

	public void testTwo() {
		String[] excludes = JSONIncludesManagerUtil.lookupExcludes(Two.class);

		assertEquals(1, excludes.length);
		assertEquals("*", excludes[0]);

		String[] includes = JSONIncludesManagerUtil.lookupIncludes(Two.class);

		assertEquals(1, includes.length);
		assertEquals("ftwo", includes[0]);
	}

}