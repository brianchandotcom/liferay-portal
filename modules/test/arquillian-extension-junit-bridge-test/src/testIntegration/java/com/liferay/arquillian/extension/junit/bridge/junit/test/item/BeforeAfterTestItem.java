/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.arquillian.extension.junit.bridge.junit.test.item;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import java.io.IOException;

import java.util.Arrays;
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
public class BeforeAfterTestItem extends BaseBeforeAfterTestItem {

	public static void assertAndTearDown() throws IOException {
		Assert.assertEquals(_lines, testItemHelper.read());
	}

	@Before
	public static void setUpOverridden() throws IOException {
		testItemHelper.write("setUpOverriddenChild");
	}

	@After
	public static void tearDownOverridden() throws IOException {
		testItemHelper.write("tearDownOverriddenChild");
	}

	@Before
	public void setUp1() throws IOException {
		testItemHelper.write("setUp1");
	}

	@Before
	public void setUp2() throws IOException {
		testItemHelper.write("setUp2");
	}

	@After
	public void tearDown1() throws IOException {
		testItemHelper.write("tearDown1");
	}

	@After
	public void tearDown2() throws IOException {
		testItemHelper.write("tearDown2");
	}

	@Test
	public void test1() throws IOException {
		testItemHelper.write("test1");
	}

	@Test
	public void test2() throws IOException {
		testItemHelper.write("test2");
	}

	private static final List<String> _lines = Arrays.asList(
		"setUpBase", "setUpOverriddenChild", "setUp2", "setUp1", "test1",
		"tearDown1", "tearDown2", "tearDownOverriddenChild", "tearDownBase",
		"setUpBase", "setUpOverriddenChild", "setUp2", "setUp1", "test2",
		"tearDown1", "tearDown2", "tearDownOverriddenChild", "tearDownBase");

}