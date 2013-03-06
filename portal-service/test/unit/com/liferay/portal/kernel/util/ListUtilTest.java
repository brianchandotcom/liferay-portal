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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.TestCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Olaf Kock
 */
public class ListUtilTest extends TestCase {

	@Test
	public void testRemoveEmptyElement() {
		List<String> list = new ArrayList<String>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<String> removeList = new ArrayList<String>();

		List<String> expectedList = new ArrayList<String>();

		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");

		Assert.assertEquals(expectedList, ListUtil.remove(list, removeList));
	}

	@Test
	public void testRemoveFromEmptyList() {
		List<String> removeList = new ArrayList<String>();

		removeList.add("aaa");
		removeList.add("bbb");

		Assert.assertEquals(
			Collections.EMPTY_LIST,
			ListUtil.remove(Collections.EMPTY_LIST, removeList));
	}

	@Test
	public void testRemoveMultipleElements() {
		List<String> list = new ArrayList<String>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<String> removeList = new ArrayList<String>();

		removeList.add("aaa");
		removeList.add("bbb");
		removeList.add("ccc");

		Assert.assertEquals(
			Collections.EMPTY_LIST, ListUtil.remove(list, removeList));
	}

	@Test
	public void testRemoveNullElement() {
		List<String> list = new ArrayList<String>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<String> expectedList = new ArrayList<String>();

		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");

		Assert.assertEquals(expectedList, ListUtil.remove(list, null));
	}

	@Test
	public void testRemoveSingleElement() {
		List<String> list = new ArrayList<String>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<String> removeList = new ArrayList<String>();

		removeList.add("aaa");

		List<String> expectedList = new ArrayList<String>();

		expectedList.add("bbb");
		expectedList.add("ccc");

		Assert.assertEquals(expectedList, ListUtil.remove(list, removeList));
	}

	@Test
	public void testRemoveWrongElement() {
		List<String> list = new ArrayList<String>();

		list.add("aaa");
		list.add("bbb");
		list.add("ccc");

		List<String> removeList = new ArrayList<String>();

		removeList.add("ddd");

		List<String> expectedList = new ArrayList<String>();

		expectedList.add("aaa");
		expectedList.add("bbb");
		expectedList.add("ccc");

		Assert.assertEquals(expectedList, ListUtil.remove(list, removeList));
	}


}