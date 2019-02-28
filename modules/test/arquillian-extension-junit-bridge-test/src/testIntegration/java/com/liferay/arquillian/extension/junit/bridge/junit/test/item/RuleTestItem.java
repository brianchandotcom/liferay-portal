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
		List<String> lines = _testItemHelper.read();

		Assert.assertEquals(lines.toString(), _LINES.length, lines.size());

		for (int i = 0; i < _LINES.length; i++) {
			Assert.assertEquals(lines.toString(), _LINES[i], lines.get(i));
		}
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
	public final TestRule testRule = new TestRule() {

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

	private static final String[] _LINES = {
		"beforeTestRule", "setUp", "test1", "tearDown", "afterTestRule",
		"beforeTestRule", "setUp", "test2", "tearDown", "afterTestRule"
	};

	private static final TestItemHelper _testItemHelper = new TestItemHelper(
		RuleTestItem.class);

}