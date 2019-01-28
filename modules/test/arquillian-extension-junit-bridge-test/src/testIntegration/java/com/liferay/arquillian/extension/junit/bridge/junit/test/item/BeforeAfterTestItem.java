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

package com.liferay.arquillian.extension.junit.bridge.junit.test.item;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import java.io.IOException;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class BeforeAfterTestItem {

	public static void assertAndTearDown() throws IOException {
		List<String> lines = _testItemHelper.read();

		Assert.assertEquals(lines.toString(), 10, lines.size());
		Assert.assertEquals(lines.toString(), "setUp2", lines.get(0));
		Assert.assertEquals(lines.toString(), "setUp1", lines.get(1));
		Assert.assertEquals(lines.toString(), "test1", lines.get(2));
		Assert.assertEquals(lines.toString(), "tearDown1", lines.get(3));
		Assert.assertEquals(lines.toString(), "tearDown2", lines.get(4));
		Assert.assertEquals(lines.toString(), "setUp2", lines.get(5));
		Assert.assertEquals(lines.toString(), "setUp1", lines.get(6));
		Assert.assertEquals(lines.toString(), "test2", lines.get(7));
		Assert.assertEquals(lines.toString(), "tearDown1", lines.get(8));
		Assert.assertEquals(lines.toString(), "tearDown2", lines.get(9));
	}

	@Before
	public void setUp1() throws IOException {
		_testItemHelper.write("setUp1");
	}

	@Before
	public void setUp2() throws IOException {
		_testItemHelper.write("setUp2");
	}

	@After
	public void tearDown1() throws IOException {
		_testItemHelper.write("tearDown1");
	}

	@After
	public void tearDown2() throws IOException {
		_testItemHelper.write("tearDown2");
	}

	@Test
	public void test1() throws IOException {
		_testItemHelper.write("test1");
	}

	@Test
	public void test2() throws IOException {
		_testItemHelper.write("test2");
	}

	private static final TestItemHelper _testItemHelper = new TestItemHelper(
		BeforeAfterTestItem.class);

}