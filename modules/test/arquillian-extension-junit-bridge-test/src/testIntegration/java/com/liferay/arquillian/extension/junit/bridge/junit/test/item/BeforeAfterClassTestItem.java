/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.arquillian.extension.junit.bridge.junit.test.item;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import java.io.IOException;

import java.util.Arrays;
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
public class BeforeAfterClassTestItem extends BaseBeforeAfterClassTestItem {

	public static void assertAndTearDown() throws IOException {
		Assert.assertEquals(_lines, testItemHelper.read());
	}

	@BeforeClass
	public static void setUpClass1() throws IOException {
		testItemHelper.write("setUpClass1");
	}

	@BeforeClass
	public static void setUpClass2() throws IOException {
		testItemHelper.write("setUpClass2");
	}

	@BeforeClass
	public static void setUpClassOverridden() throws IOException {
		testItemHelper.write("setUpClassOverriddenChild");
	}

	@AfterClass
	public static void tearDownClass1() throws IOException {
		testItemHelper.write("tearDownClass1");
	}

	@AfterClass
	public static void tearDownClass2() throws IOException {
		testItemHelper.write("tearDownClass2");
	}

	@AfterClass
	public static void tearDownClassOverridden() throws IOException {
		testItemHelper.write("tearDownClassOverriddenChild");
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
		"setUpClassBase", "setUpClassOverriddenChild", "setUpClass2",
		"setUpClass1", "test1", "test2", "tearDownClass1", "tearDownClass2",
		"tearDownClassOverriddenChild", "tearDownClassBase");

}