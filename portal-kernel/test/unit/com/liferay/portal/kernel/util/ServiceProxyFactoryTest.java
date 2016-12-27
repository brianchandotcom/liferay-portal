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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.TimeoutTestRule;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

/**
 * @author Tina Tian
 */
public class ServiceProxyFactoryTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	@Test
	public void testBlockingProxy() throws Exception {
		_testBlockingProxy(false);
	}

	@Test
	public void testBlockingProxyWithProxyService() throws Exception {
		_testBlockingProxy(true);
	}

	@Test
	public void testMisc() throws Exception {

		// Test 1, wrong field

		try {
			ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, "wrongFieldName",
				false);

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(NoSuchFieldException.class, throwable.getClass());
			Assert.assertEquals("wrongFieldName", throwable.getMessage());
		}

		// Test 2, field is not static

		try {
			ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, "nonStaticField",
				false);

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(
				IllegalArgumentException.class, throwable.getClass());

			Field testServiceField = ReflectionUtil.getDeclaredField(
				TestServiceUtil.class, "nonStaticField");

			Assert.assertEquals(
				testServiceField + " is not static", throwable.getMessage());
		}

		// Test 3, test constructor

		new ServiceProxyFactory();
	}

	@Test
	public void testNonblockingProxy() throws Exception {
		_testNonBlockingProxy(false);
	}

	@Test
	public void testNonblockingProxyWithFilter() throws Exception {
		_testNonBlockingProxy(true);
	}

	@Rule
	public final TestRule testRule = TimeoutTestRule.INSTANCE;

	public static class TestServiceImpl implements TestService {

		@Override
		public long getTestServiceId() {
			return _TEST_SERVICE_ID;
		}

		@Override
		public String getTestServiceName() {
			return _TEST_SERVICE_NAME;
		}

	}

	public interface TestService {

		public long getTestServiceId();

		public String getTestServiceName();

	}

	private void _testBlockingProxy(boolean proxyService) throws Exception {
		final TestService testService =
			ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, "testService", true);

		Assert.assertTrue(ProxyUtil.isProxyClass(testService.getClass()));
		Assert.assertNotSame(TestServiceImpl.class, testService.getClass());

		FutureTask<Void> futureTask = new FutureTask<>(
			new Callable<Void>() {

				@Override
				public Void call() {
					Assert.assertEquals(
						_TEST_SERVICE_NAME, testService.getTestServiceName());
					Assert.assertEquals(
						_TEST_SERVICE_ID, testService.getTestServiceId());

					TestService newTestService = TestServiceUtil.testService;

					if (proxyService) {
						Assert.assertTrue(
							ProxyUtil.isProxyClass(newTestService.getClass()));
						Assert.assertNotSame(
							TestServiceImpl.class, newTestService.getClass());
					}
					else {
						Assert.assertFalse(
							ProxyUtil.isProxyClass(newTestService.getClass()));
						Assert.assertSame(
							TestServiceImpl.class, newTestService.getClass());
					}

					return null;
				}

			});

		Thread thread = new Thread(futureTask);

		thread.start();

		_waitForBlocked(testService, thread);

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<TestService> serviceRegistration = null;

		if (proxyService) {
			serviceRegistration = registry.registerService(
				TestService.class,
				(TestService)ProxyFactory.newInstance(
					TestService.class.getClassLoader(),
					new Class<?>[] {TestService.class},
					TestServiceImpl.class.getName()));
		}
		else {
			serviceRegistration = registry.registerService(
				TestService.class, new TestServiceImpl());
		}

		futureTask.get();

		serviceRegistration.unregister();
	}

	private void _testNonBlockingProxy(boolean filterEnabled) throws Exception {
		TestService testService = null;

		if (filterEnabled) {
			testService = ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, "testService",
				"(test.filter=true)", false);
		}
		else {
			testService = ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, "testService", false);
		}

		Assert.assertTrue(ProxyUtil.isProxyClass(testService.getClass()));
		Assert.assertNotSame(TestServiceImpl.class, testService.getClass());

		Assert.assertEquals(0, testService.getTestServiceId());
		Assert.assertEquals(null, testService.getTestServiceName());

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<TestService> serviceRegistration = null;

		if (filterEnabled) {
			serviceRegistration = registry.registerService(
				TestService.class, new TestServiceImpl(),
				Collections.singletonMap("test.filter", "true"));
		}
		else {
			serviceRegistration = registry.registerService(
				TestService.class, new TestServiceImpl());
		}

		TestService newTestService = TestServiceUtil.testService;

		Assert.assertEquals(
			_TEST_SERVICE_NAME, newTestService.getTestServiceName());
		Assert.assertEquals(
			_TEST_SERVICE_ID, newTestService.getTestServiceId());

		Assert.assertFalse(ProxyUtil.isProxyClass(newTestService.getClass()));
		Assert.assertSame(TestServiceImpl.class, newTestService.getClass());

		serviceRegistration.unregister();
	}

	private void _waitForBlocked(TestService testService, Thread targetThread) {
		InvocationHandler invocationHandler = ProxyUtil.getInvocationHandler(
			testService);

		Lock lock = ReflectionTestUtil.getFieldValue(
			invocationHandler, "_lock");

		Object sync = ReflectionTestUtil.getFieldValue(lock, "sync");

		Condition condition = ReflectionTestUtil.getFieldValue(
			invocationHandler, "_realServiceSet");

		while (true) {
			Collection<Thread> waitingThreads = null;

			lock.lock();

			try {
				waitingThreads = ReflectionTestUtil.invoke(
					sync, "getWaitingThreads",
					new Class<?>[] {
						AbstractQueuedSynchronizer.ConditionObject.class
					},
					condition);
			}
			finally {
				lock.unlock();
			}

			if (waitingThreads.contains(targetThread)) {
				return;
			}
		}
	}

	private static final long _TEST_SERVICE_ID = 1234L;

	private static final String _TEST_SERVICE_NAME = "TestServiceName";

	private static class TestServiceUtil {

		public static volatile TestService testService;

		public TestService nonStaticField;

	}

}