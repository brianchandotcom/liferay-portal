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

import com.liferay.arquillian.extension.junit.bridge.junit.test.item.AssumeClassRuleTestItem;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.AssumeTestItem;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.BeforeAfterClassTestItem;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.BeforeAfterTestItem;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.ClassRuleTestItem;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.ExpectedExceptionTestItem;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.FailTestItem;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.IgnoreTestItem;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.NoExpectedExceptionTestItem;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.NotSerializableExceptionTestItem;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.NotSerializableExceptionTestItem.UnserializableException;
import com.liferay.arquillian.extension.junit.bridge.junit.test.item.RuleTestItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.junit.BridgeJUnitTestRunner;

import java.io.IOException;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
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
	public void testAssumeClassRule() {
		Result result = BridgeJUnitTestRunner.runBridgeTests(
			new BridgeJUnitTestRunner.BridgeRunListener(ArquillianTest.class),
			AssumeClassRuleTestItem.class);

		assertResult(result, AssumeClassRuleTestItem.class);
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
	public void testFail() {
		AtomicBoolean atomicBoolean = new AtomicBoolean();

		BridgeJUnitTestRunner.runBridgeTests(
			new BridgeJUnitTestRunner.BridgeRunListener(ArquillianTest.class) {

				@Override
				public void testFailure(Failure failure) {
					atomicBoolean.set(true);
				}

			},
			FailTestItem.class);

		Assert.assertTrue(atomicBoolean.get());
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

	@Test
	public void testNoExpectedException() {
		AtomicReference<Throwable> throwableContainer = new AtomicReference<>();

		BridgeJUnitTestRunner.runBridgeTests(
			new BridgeJUnitTestRunner.BridgeRunListener(ArquillianTest.class) {

				@Override
				public void testFailure(Failure failure) {
					throwableContainer.set(failure.getException());
				}

			},
			NoExpectedExceptionTestItem.class);

		Throwable throwable = throwableContainer.get();

		Assert.assertNotNull(throwable);

		String message = throwable.getMessage();

		Assert.assertTrue(
			message,
			message.startsWith(
				StringBundler.concat(
					AssertionError.class.getName(), ": Expected test to throw ",
					IOException.class)));
	}

	@Test
	public void testNotSerializableException() {
		AtomicReference<Throwable> throwableContainer = new AtomicReference<>();

		BridgeJUnitTestRunner.runBridgeTests(
			new BridgeJUnitTestRunner.BridgeRunListener(ArquillianTest.class) {

				@Override
				public void testFailure(Failure failure) {
					throwableContainer.set(failure.getException());
				}

			},
			NotSerializableExceptionTestItem.class);

		Throwable throwable = throwableContainer.get();

		Assert.assertNotNull(throwable);

		String message = throwable.getMessage();

		Assert.assertTrue(
			message,
			message.startsWith(
				StringBundler.concat(
					UnserializableException.class.getName(), ": ",
					NotSerializableExceptionTestItem.class.getName())));
	}

	@Test
	public void testRule() throws IOException {
		try {
			Result result = BridgeJUnitTestRunner.runBridgeTests(
				new BridgeJUnitTestRunner.BridgeRunListener(
					ArquillianTest.class),
				RuleTestItem.class);

			assertResult(result, RuleTestItem.class);
		}
		finally {
			RuleTestItem.assertAndTearDown();
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