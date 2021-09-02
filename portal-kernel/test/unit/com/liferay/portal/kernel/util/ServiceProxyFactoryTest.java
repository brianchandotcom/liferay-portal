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

import com.liferay.petra.memory.FinalizeAction;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.FinalizeManagerUtil;
import com.liferay.portal.kernel.test.GCUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.test.rule.TimeoutTestRule;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Tina Tian
 */
public class ServiceProxyFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, NewEnvTestRule.INSTANCE,
			TimeoutTestRule.INSTANCE);

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testBlockingProxy() throws Exception {
		_testBlockingProxy(false);
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testBlockingProxyWithProxyService() throws Exception {
		_testBlockingProxy(true);
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testBlockingProxyWithTimeout() throws InterruptedException {
		_testBlockingProxyWithTimeout(null);
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testBlockingProxyWithTimeoutAndFilterString()
		throws InterruptedException {

		_testBlockingProxyWithTimeout("filter.string");
	}

	@Test
	public void testCloseServiceTrackerFinalizeAction() throws Exception {
		TestServiceUtil testServiceUtil = new TestServiceUtil();

		ServiceProxyFactory.newServiceTrackedInstance(
			TestService.class, TestServiceUtil.class, testServiceUtil,
			"nonstaticField", null, false);

		FinalizeAction finalizeAction = null;

		Map<Object, FinalizeAction> finalizeActions =
			ReflectionTestUtil.getFieldValue(
				FinalizeManager.class, "_finalizeActions");

		for (Map.Entry<Object, FinalizeAction> entry :
				finalizeActions.entrySet()) {

			Reference<?> reference = ReflectionTestUtil.getFieldValue(
				entry.getKey(), "_reference");

			if (!(reference instanceof PhantomReference<?>)) {
				continue;
			}

			Object referent = ReflectionTestUtil.getFieldValue(
				reference, "referent");

			if (referent != testServiceUtil) {
				continue;
			}

			finalizeAction = entry.getValue();

			Class<?> clazz = finalizeAction.getClass();

			Assert.assertEquals(
				"com.liferay.portal.kernel.util.ServiceProxyFactory$" +
					"CloseServiceTrackerFinalizeAction",
				clazz.getName());

			break;
		}

		Assert.assertNotNull(finalizeAction);

		ServiceTracker<TestService, TestService> serviceTracker =
			ReflectionTestUtil.getFieldValue(finalizeAction, "_serviceTracker");

		Assert.assertNotNull(
			ReflectionTestUtil.getFieldValue(serviceTracker, "tracked"));

		testServiceUtil = null;

		GCUtil.gc(true);

		FinalizeManagerUtil.drainPendingFinalizeActions();

		Assert.assertNull(
			ReflectionTestUtil.getFieldValue(serviceTracker, "tracked"));
	}

	@Test
	public void testMisc() throws Exception {

		// Test 1, wrong field

		try {
			ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, null,
				"wrongFieldName", null, false);

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(NoSuchFieldException.class, throwable.getClass());
			Assert.assertEquals("wrongFieldName", throwable.getMessage());
		}

		try {
			ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, new TestServiceUtil(),
				"wrongFieldName", null, false);

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(NoSuchFieldException.class, throwable.getClass());
			Assert.assertEquals("wrongFieldName", throwable.getMessage());
		}

		// Test 2, field is static

		try {
			ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, new TestServiceUtil(),
				"testService", null, false);

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(
				IllegalArgumentException.class, throwable.getClass());

			Field testServiceField = ReflectionUtil.getDeclaredField(
				TestServiceUtil.class, "testService");

			Assert.assertEquals(
				testServiceField + " is static", throwable.getMessage());
		}

		// Test 3, field is not static

		try {
			ServiceProxyFactory.newServiceTrackedInstance(
				TestService.class, TestServiceUtil.class, "nonstaticField",
				false);

			Assert.fail();
		}
		catch (Throwable throwable) {
			Assert.assertSame(
				IllegalArgumentException.class, throwable.getClass());

			Field testServiceField = ReflectionUtil.getDeclaredField(
				TestServiceUtil.class, "nonstaticField");

			Assert.assertEquals(
				testServiceField + " is not static", throwable.getMessage());
		}

		// Test 4, field already set

		TestServiceUtil testServiceUtil = new TestServiceUtil();

		TestService testService = new TestServiceImpl();

		testServiceUtil.nonstaticField = testService;

		ServiceProxyFactory.newServiceTrackedInstance(
			TestService.class, TestServiceUtil.class, testServiceUtil,
			"nonstaticField", null, false);

		Assert.assertSame(testService, testServiceUtil.nonstaticField);

		// Test 5, test constructor

		new ServiceProxyFactory();
	}

	@Test
	public void testNonblockingProxy() throws Exception {
		_testNonblockingProxy(false);
	}

	@Test
	public void testNonblockingProxyWithFilter() throws Exception {
		_testNonblockingProxy(true);
	}

	@Test
	public void testNonblockingProxyWithInstanceField() throws Exception {
		TestServiceUtil testServiceUtil = new TestServiceUtil();

		TestService testService = ServiceProxyFactory.newServiceTrackedInstance(
			TestService.class, TestServiceUtil.class, testServiceUtil,
			"nonstaticField", null, false);

		_testNonblockingProxy(false, testService, testServiceUtil);
	}

	@Test
	public void testNullDummyService() throws Exception {
		TestService testService = ServiceProxyFactory.newServiceTrackedInstance(
			TestService.class, TestServiceUtil.class, "testService", false,
			true);

		Assert.assertNull(testService);

		ServiceRegistration<TestService> serviceRegistration =
			_bundleContext.registerService(
				TestService.class, new TestServiceImpl(), null);

		TestService newTestService = TestServiceUtil.testService;

		Assert.assertEquals(
			_TEST_SERVICE_NAME, newTestService.getTestServiceName());
		Assert.assertEquals(
			_TEST_SERVICE_ID, newTestService.getTestServiceId());

		try {
			newTestService.throwException();

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertSame(TestServiceImpl._exception, exception);
		}

		Assert.assertFalse(ProxyUtil.isProxyClass(newTestService.getClass()));
		Assert.assertSame(TestServiceImpl.class, newTestService.getClass());

		serviceRegistration.unregister();
	}

	public static class TestServiceImpl implements TestService {

		@Override
		public long getTestServiceId() {
			return _TEST_SERVICE_ID;
		}

		@Override
		public String getTestServiceName() {
			return _TEST_SERVICE_NAME;
		}

		@Override
		public void throwException() throws Exception {
			throw _exception;
		}

		private static final Exception _exception = new Exception();

	}

	public interface TestService {

		public long getTestServiceId();

		public String getTestServiceName();

		public void throwException() throws Exception;

	}

	private void _testBlockingProxy(boolean proxyService) throws Exception {
		System.setProperty(
			ServiceProxyFactory.class.getName() + ".timeout",
			String.valueOf(Long.MAX_VALUE));

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

					try {
						testService.throwException();

						Assert.fail();
					}
					catch (Exception exception) {
						Assert.assertSame(
							TestServiceImpl._exception, exception);
					}

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

		ServiceRegistration<TestService> serviceRegistration = null;

		if (proxyService) {
			serviceRegistration = _bundleContext.registerService(
				TestService.class,
				(TestService)ProxyFactory.newInstance(
					TestService.class.getClassLoader(),
					new Class<?>[] {TestService.class},
					TestServiceImpl.class.getName()),
				null);
		}
		else {
			serviceRegistration = _bundleContext.registerService(
				TestService.class, new TestServiceImpl(), null);
		}

		futureTask.get();

		serviceRegistration.unregister();
	}

	private void _testBlockingProxyWithTimeout(String filterString)
		throws InterruptedException {

		System.setProperty(
			ServiceProxyFactory.class.getName() + ".timeout", "0");

		TestService testService = ServiceProxyFactory.newServiceTrackedInstance(
			TestService.class, TestServiceUtil.class, "testService",
			filterString, true);

		Assert.assertTrue(ProxyUtil.isProxyClass(testService.getClass()));
		Assert.assertNotSame(TestServiceImpl.class, testService.getClass());

		try (LogCapture logCapture = LoggerTestUtil.configureJDKLogger(
				ServiceProxyFactory.class.getName(), Level.SEVERE)) {

			ReflectionTestUtil.setFieldValue(
				logCapture, "_logEntries",
				new CopyOnWriteArrayList<LogEntry>() {

					@Override
					public boolean add(LogEntry e) {
						if (_logged) {
							Thread currentThread = Thread.currentThread();

							currentThread.interrupt();
						}

						_logged = true;

						return super.add(e);
					}

					private boolean _logged;

				});

			List<LogEntry> logEntries = logCapture.getLogEntries();

			FutureTask<String> futureTask = new FutureTask<>(
				testService::getTestServiceName);

			Thread thread = new Thread(futureTask, "Invoke Service Thread");

			thread.start();

			thread.join();

			Assert.assertEquals(logEntries.toString(), 2, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			StringBundler sb = new StringBundler(9);

			sb.append("Service \"");
			sb.append(TestService.class.getName());

			if (Validator.isNotNull(filterString)) {
				sb.append("{");
				sb.append(filterString);
				sb.append("}");
			}

			sb.append("\" is unavailable in 0 milliseconds while setting ");
			sb.append("field \"testService\" for class \"");
			sb.append(TestServiceUtil.class.getName());
			sb.append("\", will retry...");

			Assert.assertEquals(sb.toString(), logEntry.getMessage());
		}
	}

	private void _testNonblockingProxy(boolean filterEnabled) throws Exception {
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

		_testNonblockingProxy(filterEnabled, testService, null);
	}

	private void _testNonblockingProxy(
			boolean filterEnabled, TestService testService,
			TestServiceUtil testServiceUtil)
		throws Exception {

		Assert.assertTrue(ProxyUtil.isProxyClass(testService.getClass()));
		Assert.assertNotSame(TestServiceImpl.class, testService.getClass());

		Assert.assertEquals(0, testService.getTestServiceId());
		Assert.assertEquals(null, testService.getTestServiceName());

		testService.throwException();

		ServiceRegistration<TestService> serviceRegistration = null;

		if (filterEnabled) {
			serviceRegistration = _bundleContext.registerService(
				TestService.class, new TestServiceImpl(),
				MapUtil.singletonDictionary("test.filter", "true"));
		}
		else {
			serviceRegistration = _bundleContext.registerService(
				TestService.class, new TestServiceImpl(), null);
		}

		TestService newTestService = null;

		if (testServiceUtil == null) {
			newTestService = TestServiceUtil.testService;
		}
		else {
			newTestService = testServiceUtil.nonstaticField;
		}

		Assert.assertEquals(
			_TEST_SERVICE_NAME, newTestService.getTestServiceName());
		Assert.assertEquals(
			_TEST_SERVICE_ID, newTestService.getTestServiceId());

		try {
			newTestService.throwException();

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertSame(TestServiceImpl._exception, exception);
		}

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

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private static class TestServiceUtil {

		public static volatile TestService testService;

		public volatile TestService nonstaticField;

	}

}