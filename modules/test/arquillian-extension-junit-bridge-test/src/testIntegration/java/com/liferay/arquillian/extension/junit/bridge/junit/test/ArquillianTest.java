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

package com.liferay.arquillian.extension.junit.bridge.junit.test;

import com.liferay.arquillian.extension.junit.bridge.junit.test.item.AssumeTestItem;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.BeforeAfterClassTestItem;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.BeforeAfterTestItem;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.ClassRuleTestItem;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.ExpectedExceptionTestItem;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.IgnoreTestItem;
import com.liferay.portal.kernel.test.junit.BridgeJUnitTestRunner;

import java.io.IOException;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.model.TestClass;

/**
 * @author Shuyang Zhou
 */
@RunWith(BridgeJUnitTestRunner.class)
public class ArquillianTest {

	@Test
	public void testAssume() throws IOException {
		Result result = BridgeJUnitTestRunner.runBridgeTests(
			new BridgeJUnitTestRunner.BridgeRunListener(ArquillianTest.class),
			AssumeTestItem.class);

		assertResult(result, AssumeTestItem.class);
	}

	@Test
	public void testBeforeAfter() throws IOException {
		try {
			Result result = BridgeJUnitTestRunner.runBridgeTests(
				new BridgeJUnitTestRunner.BridgeRunListener(
					ArquillianTest.class),
				BeforeAfterTestItem.class);

			assertResult(result, BeforeAfterTestItem.class);
		}
		finally {
			BeforeAfterTestItem.assertAndTearDown();
		}
	}

	@Test
	public void testBeforeAfterClass() throws IOException {
		try {
			Result result = BridgeJUnitTestRunner.runBridgeTests(
				new BridgeJUnitTestRunner.BridgeRunListener(
					ArquillianTest.class),
				BeforeAfterClassTestItem.class);

			assertResult(result, BeforeAfterClassTestItem.class);
		}
		finally {
			BeforeAfterClassTestItem.assertAndTearDown();
		}
	}

	@Test
	public void testClassRule() throws IOException {
		try {
			Result result = BridgeJUnitTestRunner.runBridgeTests(
				new BridgeJUnitTestRunner.BridgeRunListener(
					ArquillianTest.class),
				ClassRuleTestItem.class);

			assertResult(result, ClassRuleTestItem.class);
		}
		finally {
			ClassRuleTestItem.assertAndTearDown();
		}
	}

	@Test
	public void testExpectedException() throws IOException {
		Result result = BridgeJUnitTestRunner.runBridgeTests(
			new BridgeJUnitTestRunner.BridgeRunListener(ArquillianTest.class),
			ExpectedExceptionTestItem.class);

		assertResult(result, ExpectedExceptionTestItem.class);
	}

	@Test
	public void testIgnore() throws IOException {
		try {
			Result result = BridgeJUnitTestRunner.runBridgeTests(
				new BridgeJUnitTestRunner.BridgeRunListener(
					ArquillianTest.class),
				IgnoreTestItem.class);

			Assert.assertEquals(1, result.getIgnoreCount());
			Assert.assertEquals(0, result.getFailureCount());
		}
		finally {
			IgnoreTestItem.assertAndTearDown();
		}
	}

	protected void assertResult(Result result, Class<?>... testClasses) {
		Assert.assertEquals(0, result.getFailureCount());

		List<?> failures = result.getFailures();

		Assert.assertTrue(failures.toString(), failures.isEmpty());

		Assert.assertEquals(0, result.getIgnoreCount());

		int runCount = 0;

		for (Class<?> clazz : testClasses) {
			TestClass testClass = new TestClass(clazz);

			List<?> methods = testClass.getAnnotatedMethods(Test.class);

			runCount += methods.size();
		}

		Assert.assertEquals(runCount, result.getRunCount());
	}

}