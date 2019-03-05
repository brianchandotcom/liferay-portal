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

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class RuleTestItem {

	public static void assertAndTearDown() throws IOException {
		Assert.assertEquals(_lines, _testItemHelper.read());
	}

	@Before
	public void setUp() throws IOException {
		_testItemHelper.write("setUp");
	}

	@After
	public void tearDown() throws IOException {
		_testItemHelper.write("tearDown");
	}

	@Test
	public void test1() throws IOException {
		_testItemHelper.write("test1");
	}

	@Test
	public void test2() throws IOException {
		_testItemHelper.write("test2");
	}

	@Rule
	public final TestRule testRule1 = new TestRule() {

		@Override
		public Statement apply(Statement statement, Description description) {
			return new Statement() {

				@Override
				public void evaluate() throws Throwable {
					_testItemHelper.write("beforeTestRule");

					statement.evaluate();

					_testItemHelper.write("afterTestRule");
				}

			};
		}

	};

	@Rule
	public final TestRule testRule2 = new TestRule() {

		@Override
		public Statement apply(Statement statement, Description description) {
			return new Statement() {

				@Override
				public void evaluate() throws Throwable {
					_testItemHelper.write("beforeTestRule");

					statement.evaluate();

					_testItemHelper.write("afterTestRule");
				}

			};
		}

	};

	private static final List<String> _lines = Arrays.asList(
		"beforeTestRule", "beforeTestRule", "setUp", "test1", "tearDown",
		"afterTestRule", "afterTestRule", "beforeTestRule", "beforeTestRule",
		"setUp", "test2", "tearDown", "afterTestRule", "afterTestRule");
	private static final TestItemHelper _testItemHelper = new TestItemHelper(
		RuleTestItem.class);

}