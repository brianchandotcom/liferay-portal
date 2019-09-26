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

package com.liferay.portal.configuration.metatype.bnd.util;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.aspects.ReflectionUtilAdvice;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import java.util.Collections;
import java.util.Dictionary;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class ConfigurableUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE, CodeCoverageAssertor.INSTANCE);

	@Test
	public void testBigString() {
		_testBigString(65535);
		_testBigString(65536);
	}

	@AdviseWith(adviceClasses = ReflectionUtilAdvice.class)
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testClassInitializationFailure() throws Exception {
		Throwable throwable = new Throwable();

		ReflectionUtilAdvice.setDeclaredMethodThrowable(throwable);

		try {
			Class.forName(ConfigurableUtil.class.getName());

			Assert.fail();
		}
		catch (ExceptionInInitializerError eiie) {
			Assert.assertSame(throwable, eiie.getCause());
		}
	}

	@AdviseWith(adviceClasses = ConfigurableUtilAdvice.class)
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testConcurrentCreateConfigurable() throws Exception {
		Callable<TestConfiguration> callable =
			() -> ConfigurableUtil.createConfigurable(
				TestConfiguration.class,
				Collections.singletonMap(
					"testReqiredString", "testReqiredString"));

		FutureTask<TestConfiguration> futureTask1 = new FutureTask<>(callable);
		FutureTask<TestConfiguration> futureTask2 = new FutureTask<>(callable);

		Thread thread1 = new Thread(
			futureTask1, "ConfigurableUtilTest Thread 1");
		Thread thread2 = new Thread(
			futureTask2, "ConfigurableUtilTest Thread 2");

		thread1.start();
		thread2.start();

		ConfigurableUtilAdvice.waitUntilBlock();

		ConfigurableUtilAdvice.unblock();

		_assertTestConfiguration(futureTask1.get(), "testReqiredString");
		_assertTestConfiguration(futureTask2.get(), "testReqiredString");
	}

	@Test
	public void testCreateConfigurable() {

		// Test dictionary

		Dictionary<String, String> dictionary = new HashMapDictionary<>();

		dictionary.put("testReqiredString", "testReqiredString1");

		_assertTestConfiguration(
			ConfigurableUtil.createConfigurable(
				TestConfiguration.class, dictionary),
			"testReqiredString1");

		// Test map

		_assertTestConfiguration(
			ConfigurableUtil.createConfigurable(
				TestConfiguration.class,
				Collections.singletonMap(
					"testReqiredString", "testReqiredString2")),
			"testReqiredString2");
	}

	@Test
	public void testMisc() {

		// Exception

		try {
			ConfigurableUtil.createConfigurable(
				TestConfiguration.class, Collections.emptyMap());

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertEquals(
				"Unable to create snapshot class for " +
					TestConfiguration.class,
				re.getMessage());

			Throwable throwable = re.getCause();

			throwable = throwable.getCause();

			Assert.assertTrue(throwable instanceof IllegalStateException);
			Assert.assertEquals(
				"Attribute is required but not set testReqiredString",
				throwable.getMessage());
		}

		// Constructor

		new ConfigurableUtil();
	}

	@Aspect
	public static class ConfigurableUtilAdvice {

		public static void unblock() {
			_blockingCountDownLatch.countDown();
		}

		public static void waitUntilBlock() throws InterruptedException {
			_waitingCountDownLatch.await();
		}

		@Around(
			"execution(private * com.liferay.portal.configuration.metatype." +
				"bnd.util.ConfigurableUtil._generateSnapshotClassData(..))"
		)
		public Object generateSnapshotClassData(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			_waitingCountDownLatch.countDown();

			_blockingCountDownLatch.await();

			return proceedingJoinPoint.proceed();
		}

		private static final CountDownLatch _blockingCountDownLatch =
			new CountDownLatch(1);
		private static final CountDownLatch _waitingCountDownLatch =
			new CountDownLatch(2);

	}

	public static class TestClass {

		public TestClass(String name) {
			_name = name;
		}

		public String getName() {
			return _name;
		}

		private final String _name;

	}

	private void _assertTestConfiguration(
		TestConfiguration testConfiguration, String requiredString) {

		Assert.assertTrue(testConfiguration.testBoolean());
		Assert.assertEquals(1, testConfiguration.testByte());
		Assert.assertEquals(1.0, testConfiguration.testDouble(), 0);
		Assert.assertEquals(TestEnum.TEST_VALUE, testConfiguration.testEnum());
		Assert.assertEquals(1.0, testConfiguration.testFloat(), 0);
		Assert.assertEquals(100, testConfiguration.testInt());
		Assert.assertNull(testConfiguration.testNullResult());
		Assert.assertEquals(
			requiredString, testConfiguration.testReqiredString());
		Assert.assertEquals(1, testConfiguration.testShort());
		Assert.assertEquals("test_string", testConfiguration.testString());
		Assert.assertArrayEquals(
			new String[] {"test_string_1", "test_string_2"},
			testConfiguration.testStringArray());

		TestClass testClass = testConfiguration.testClass();

		Assert.assertEquals("test.class", testClass.getName());
	}

	private void _testBigString(int length) {
		StringBuilder stringBuilder = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			stringBuilder.append(CharPool.LOWER_CASE_A);
		}

		String bigString = stringBuilder.toString();

		_assertTestConfiguration(
			ConfigurableUtil.createConfigurable(
				TestConfiguration.class,
				Collections.singletonMap("testReqiredString", bigString)),
			bigString);
	}

	private interface TestConfiguration {

		@Meta.AD(deflt = "true", required = false)
		public boolean testBoolean();

		@Meta.AD(deflt = "1", required = false)
		public byte testByte();

		@Meta.AD(deflt = "test.class", required = false)
		public TestClass testClass();

		@Meta.AD(deflt = "1.0", required = false)
		public double testDouble();

		@Meta.AD(deflt = "TEST_VALUE", required = false)
		public TestEnum testEnum();

		@Meta.AD(deflt = "1.0", required = false)
		public float testFloat();

		@Meta.AD(deflt = "100", required = false)
		public int testInt();

		@Meta.AD(deflt = "100", required = false)
		public long testLong();

		@Meta.AD(required = false)
		public String testNullResult();

		@Meta.AD(required = true)
		public String testReqiredString();

		@Meta.AD(deflt = "1", required = false)
		public short testShort();

		@Meta.AD(deflt = "test_string", required = false)
		public String testString();

		@Meta.AD(deflt = "test_string_1|test_string_2", required = false)
		public String[] testStringArray();

	}

	private enum TestEnum {

		TEST_VALUE

	}

}