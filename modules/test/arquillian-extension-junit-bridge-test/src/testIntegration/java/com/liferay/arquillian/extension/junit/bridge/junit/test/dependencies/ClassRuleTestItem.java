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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.BaseTestRule;
import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;

import java.io.IOException;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class ClassRuleTestItem {

	@ClassRule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			false,
			new BaseTestRule<>(
				new BaseTestCallback<Object, Object>() {

					@Override
					public Object beforeClass(Description description)
						throws IOException {

						_testItemHelper.write("doBeforeClass1");

						return "ClassRule1";
					}

					@Override
					public void afterClass(Description description, Object c)
						throws IOException {

						_testItemHelper.write(c.toString());
						_testItemHelper.write("doAfterClass1");
					}

				}),
			new BaseTestRule<>(
				new BaseTestCallback<Object, Object>() {

					@Override
					public Object beforeClass(Description description)
						throws IOException {

						_testItemHelper.write("doBeforeClass2");

						return "ClassRule2";
					}

					@Override
					public void afterClass(Description description, Object c)
						throws IOException {

						_testItemHelper.write(c.toString());
						_testItemHelper.write("doAfterClass2");
					}

				}));

	public static void assertAndTearDown() throws IOException {
		List<String> lines = _testItemHelper.read();

		Assert.assertEquals(lines.toString(), 8, lines.size());
		Assert.assertEquals(lines.toString(), "doBeforeClass1", lines.get(0));
		Assert.assertEquals(lines.toString(), "doBeforeClass2", lines.get(1));
		Assert.assertEquals(lines.toString(), "test1", lines.get(2));
		Assert.assertEquals(lines.toString(), "test2", lines.get(3));
		Assert.assertEquals(lines.toString(), "ClassRule2", lines.get(4));
		Assert.assertEquals(lines.toString(), "doAfterClass2", lines.get(5));
		Assert.assertEquals(lines.toString(), "ClassRule1", lines.get(6));
		Assert.assertEquals(lines.toString(), "doAfterClass1", lines.get(7));
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
		ClassRuleTestItem.class);

}