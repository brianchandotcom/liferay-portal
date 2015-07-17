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

package com.liferay.portlet;

import com.liferay.portal.NoSuchPreferencesException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.transaction.TransactionAttribute.Builder;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.service.PortalPreferencesLocalService;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.service.persistence.PortalPreferencesUtil;
import com.liferay.portal.spring.transaction.DefaultTransactionExecutor;
import com.liferay.portal.spring.transaction.TransactionInterceptor;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortletKeys;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author Matthew Tambara
 */
public class PortalPreferencesImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_transactionInterceptor =
			(TransactionInterceptor)PortalBeanLocatorUtil.locate(
				"transactionAdvice");

		_originalTransactionExecutor = ReflectionTestUtil.getFieldValue(
			_transactionInterceptor, "transactionExecutor");

		_transactionInterceptor.setTransactionExecutor(
			new SynchronizedTransactionExecutor(_originalTransactionExecutor));

		_originalPortalPreferencesLocalService =
			PortalPreferencesLocalServiceUtil.getService();

		ReflectionTestUtil.setFieldValue(
			PortalPreferencesLocalServiceUtil.class, "_service",
			ProxyUtil.newProxyInstance(
				PortalPreferencesLocalService.class.getClassLoader(),
				new Class<?>[] {PortalPreferencesLocalService.class},
				new SynchronousInvocationHandler(
					_originalPortalPreferencesLocalService)));

		_testThread.set(true);
	}

	@AfterClass
	public static void tearDownClass() {
		_transactionInterceptor.setTransactionExecutor(
			_originalTransactionExecutor);

		ReflectionTestUtil.setFieldValue(
			PortalPreferencesLocalServiceUtil.class, "_service",
			_originalPortalPreferencesLocalService);
	}

	@Before
	public void setUp() {
		PortalPreferences portalPreferences = new PortalPreferencesImpl(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_USER, null,
			new HashMap<String, Preference>(), true);

		portalPreferences.setValue(_NAMESPACE, "testKey", "testValue");
	}

	@After
	public void tearDown() throws Throwable {
		Builder builder = new Builder();

		TransactionInvokerUtil.invoke(
			builder.build(),
			new Callable<Void>() {

				@Override
				public Void call() throws NoSuchPreferencesException {
					PortalPreferencesUtil.removeByO_O(
						PortletKeys.PREFS_OWNER_ID_DEFAULT,
						PortletKeys.PREFS_OWNER_TYPE_USER);

					return null;
				}

			});
	}

	@Test
	public void testReset() throws Exception {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

		portalPreferences.setValue(_NAMESPACE, _KEY_1, _VALUE_1);

		Callable<Void> callable = new Callable<Void>() {

			@Override
			public Void call() {
				PortalPreferences portalPreferences =
					PortletPreferencesFactoryUtil.getPortalPreferences(
						PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

				portalPreferences.resetValues(_NAMESPACE);

				return null;
			}

		};

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					DefaultTransactionExecutor.class.getName(), Level.ERROR)) {

			doSynchronousUpdate(
				new FutureTask<>(callable), new FutureTask<>(callable),
				captureAppender);

			portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

			String value = portalPreferences.getValue(_NAMESPACE, _KEY_1);

			Assert.assertNull(value);
		}
	}

	@Test
	public void testSetValueDifferentKeys() throws Exception {
		FutureTask<Void> futureTask1 = new FutureTask<>(
			new Callable<Void>() {

				@Override
				public Void call() {
					PortalPreferences portalPreferences =
						PortletPreferencesFactoryUtil.getPortalPreferences(
							PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

					portalPreferences.setValue(_NAMESPACE, _KEY_1, _VALUE_1);

					return null;
				}

			});

		FutureTask<Void> futureTask2 = new FutureTask<>(
			new Callable<Void>() {

				@Override
				public Void call() {
					PortalPreferences portalPreferences =
						PortletPreferencesFactoryUtil.getPortalPreferences(
							PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

					portalPreferences.setValue(_NAMESPACE, _KEY_2, _VALUE_1);

					return null;
				}

			});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					DefaultTransactionExecutor.class.getName(), Level.ERROR)) {

			doSynchronousUpdate(futureTask1, futureTask2, captureAppender);

			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

			Assert.assertEquals(
				_VALUE_1, portalPreferences.getValue(_NAMESPACE, _KEY_1));
			Assert.assertEquals(
				_VALUE_1, portalPreferences.getValue(_NAMESPACE, _KEY_2));
		}
	}

	@Test
	public void testSetValueSameKey() throws Exception {
		FutureTask<Void> futureTask1 = new FutureTask<>(
			new Callable<Void>() {

				@Override
				public Void call() {
					PortalPreferences portalPreferences =
						PortletPreferencesFactoryUtil.getPortalPreferences(
							PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

					portalPreferences.setValue(_NAMESPACE, _KEY_1, _VALUE_1);

					return null;
				}

			});

		FutureTask<Void> futureTask2 = new FutureTask<>(
			new Callable<Void>() {

				@Override
				public Void call() {
					PortalPreferences portalPreferences =
						PortletPreferencesFactoryUtil.getPortalPreferences(
							PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

					portalPreferences.setValue(_NAMESPACE, _KEY_1, _VALUE_2);

					return null;
				}

			});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					DefaultTransactionExecutor.class.getName(), Level.ERROR)) {

			doSynchronousUpdate(futureTask1, futureTask2, captureAppender);

			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

			String value = portalPreferences.getValue(_NAMESPACE, _KEY_1);

			Assert.assertTrue(value.equals(_VALUE_1) || value.equals(_VALUE_2));
		}
	}

	@Test
	public void testSetValuesDifferentKeys() throws Exception {
		FutureTask<Void> futureTask1 = new FutureTask<>(
			new Callable<Void>() {

				@Override
				public Void call() {
					PortalPreferences portalPreferences =
						PortletPreferencesFactoryUtil.getPortalPreferences(
							PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

					portalPreferences.setValues(_NAMESPACE, _KEY_1, _VALUES_1);

					return null;
				}

			});

		FutureTask<Void> futureTask2 = new FutureTask<>(
			new Callable<Void>() {

				@Override
				public Void call() {
					PortalPreferences portalPreferences =
						PortletPreferencesFactoryUtil.getPortalPreferences(
							PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

					portalPreferences.setValues(_NAMESPACE, _KEY_2, _VALUES_1);

					return null;
				}

			});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					DefaultTransactionExecutor.class.getName(), Level.ERROR)) {

			doSynchronousUpdate(futureTask1, futureTask2, captureAppender);

			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

			Assert.assertArrayEquals(
				_VALUES_1, portalPreferences.getValues(_NAMESPACE, _KEY_1));
			Assert.assertArrayEquals(
				_VALUES_1, portalPreferences.getValues(_NAMESPACE, _KEY_2));
		}
	}

	@Test
	public void testSetValuesSameKey() throws Exception {
		FutureTask<Void> futureTask1 = new FutureTask<>(
			new Callable<Void>() {

				@Override
				public Void call() {
					PortalPreferences portalPreferences =
						PortletPreferencesFactoryUtil.getPortalPreferences(
							PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

					portalPreferences.setValues(_NAMESPACE, _KEY_1, _VALUES_1);

					return null;
				}

			});

		FutureTask<Void> futureTask2 = new FutureTask<>(
			new Callable<Void>() {

				@Override
				public Void call() {
					PortalPreferences portalPreferences =
						PortletPreferencesFactoryUtil.getPortalPreferences(
							PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

					portalPreferences.setValues(_NAMESPACE, _KEY_1, _VALUES_2);

					return null;
				}

			});

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					DefaultTransactionExecutor.class.getName(), Level.ERROR)) {

			doSynchronousUpdate(futureTask1, futureTask2, captureAppender);

			PortalPreferences portalPreferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

			String[] values = portalPreferences.getValues(_NAMESPACE, _KEY_1);

			Assert.assertTrue(
				Arrays.equals(values, _VALUES_1) ||
					Arrays.equals(values, _VALUES_2));
		}
	}

	protected void doSynchronousUpdate(
			FutureTask<Void> futureTask1, FutureTask<Void> futureTask2,
			CaptureAppender captureAppender)
		throws Exception {

		SynchronousInvocationHandler.synchronize(true);

		Thread thread1 = new Thread(futureTask1);

		thread1.start();

		Thread thread2 = new Thread(futureTask2);

		thread2.start();

		futureTask1.get();
		futureTask2.get();

		SynchronousInvocationHandler.synchronize(false);

		List<LoggingEvent> loggingEvents = captureAppender.getLoggingEvents();

		Assert.assertEquals(1, loggingEvents.size());

		LoggingEvent loggingEvent = loggingEvents.get(0);

		Assert.assertEquals(
			"Application exception overridden by commit exception",
			loggingEvent.getMessage());

		FinderCacheUtil.clearLocalCache();

		EntityCacheUtil.clearLocalCache();
	}

	protected static class SynchronizedTransactionExecutor
		extends DefaultTransactionExecutor {

		@Override
		public void commit(
			PlatformTransactionManager platformTransactionManager,
			TransactionAttribute transactionAttribute,
			TransactionStatus transactionStatus) {

			if (_testThread.get() && _synchronize.get()) {
				try {
					_cyclicBarrier.await();

					_semaphore.acquire();

					_defaultTransactionExecutor.commit(
						platformTransactionManager, transactionAttribute,
						transactionStatus);
				}
				catch (RuntimeException re) {
					throw re;
				}
				catch (Error e) {
					throw e;
				}
				catch (Exception e) {
				}
				finally {
					_semaphore.release();

					_synchronize.set(false);

					PortalPreferencesWrapperCacheUtil.remove(
						PortletKeys.PREFS_OWNER_ID_DEFAULT,
						PortletKeys.PREFS_OWNER_TYPE_USER);
				}
			}
			else {
				_defaultTransactionExecutor.commit(
					platformTransactionManager, transactionAttribute,
					transactionStatus);
			}
		}

		protected static void synchronize(boolean synchronize) {
			_synchronize.set(synchronize);
		}

		protected SynchronizedTransactionExecutor(
			DefaultTransactionExecutor defaultTransactionExecutor) {

			_defaultTransactionExecutor = defaultTransactionExecutor;
		}

		private static final CyclicBarrier _cyclicBarrier = new CyclicBarrier(
			2);
		private static final Semaphore _semaphore = new Semaphore(1);
		private static final AtomicBoolean _synchronize = new AtomicBoolean();

		private final DefaultTransactionExecutor _defaultTransactionExecutor;

	}

	protected static class SynchronousInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			Class<?>[] parameterTypes = method.getParameterTypes();

			Class<?>[] expectedTypes = new Class<?>[] {
				long.class, int.class,
				com.liferay.portlet.PortalPreferences.class
			};

			if (_testThread.get() &&
				method.getName().equals("updatePreferences") &&
				Arrays.equals(parameterTypes, expectedTypes)) {

				if (_synchronize.get()) {
					_cyclicBarrier.await();

					SynchronizedTransactionExecutor.synchronize(true);

					_synchronize.set(false);
				}
			}

			return method.invoke(_target, args);
		}

		protected static void synchronize(boolean synchronize) {
			_synchronize.set(synchronize);
		}

		protected SynchronousInvocationHandler(
			PortalPreferencesLocalService target) {

			_target = target;
		}

		private static final CyclicBarrier _cyclicBarrier = new CyclicBarrier(
			2);
		private static final AtomicBoolean _synchronize = new AtomicBoolean();

		private final PortalPreferencesLocalService _target;

	}

	private static final String _KEY_1 = "key1";

	private static final String _KEY_2 = "key2";

	private static final String _NAMESPACE = "test";

	private static final String _VALUE_1 = "value1";

	private static final String _VALUE_2 = "value2";

	private static final String[] _VALUES_1 = new String[] {"values1"};

	private static final String[] _VALUES_2 = new String[] {"values2"};

	private static PortalPreferencesLocalService
		_originalPortalPreferencesLocalService;
	private static DefaultTransactionExecutor _originalTransactionExecutor;
	private static final InheritableThreadLocal<Boolean> _testThread =
		new InheritableThreadLocal<>();
	private static TransactionInterceptor _transactionInterceptor;

}