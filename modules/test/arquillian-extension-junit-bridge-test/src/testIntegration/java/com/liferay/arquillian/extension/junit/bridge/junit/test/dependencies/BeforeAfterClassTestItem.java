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

package com.liferay.arquillian.extension.junit.bridge.junit.test.dependencies;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import java.io.IOException;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class BeforeAfterClassTestItem {

	public static void assertAndTearDown() throws IOException {
		List<String> lines = _testItemHelper.read();

		Assert.assertEquals(lines.toString(), 6, lines.size());
		Assert.assertEquals(lines.toString(), "setUpClass2", lines.get(0));
		Assert.assertEquals(lines.toString(), "setUpClass1", lines.get(1));
		Assert.assertEquals(lines.toString(), "test1", lines.get(2));
		Assert.assertEquals(lines.toString(), "test2", lines.get(3));
		Assert.assertEquals(lines.toString(), "tearDownClass1", lines.get(4));
		Assert.assertEquals(lines.toString(), "tearDownClass2", lines.get(5));
	}

	@BeforeClass
	public static void setUpClass1() throws IOException {
		_testItemHelper.write("setUpClass1");
	}

	@BeforeClass
	public static void setUpClass2() throws IOException {
		_testItemHelper.write("setUpClass2");
	}

	@AfterClass
	public static void tearDownClass1() throws IOException {
		_testItemHelper.write("tearDownClass1");
	}

	@AfterClass
	public static void tearDownClass2() throws IOException {
		_testItemHelper.write("tearDownClass2");
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
		BeforeAfterClassTestItem.class);

}