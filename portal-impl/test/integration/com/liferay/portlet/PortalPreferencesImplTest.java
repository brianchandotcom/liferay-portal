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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.exception.NoSuchPreferencesException;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.PortalPreferencesUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.spring.transaction.DefaultTransactionExecutor;
import com.liferay.portal.spring.transaction.TransactionAttributeAdapter;
import com.liferay.portal.spring.transaction.TransactionInterceptor;
import com.liferay.portal.spring.transaction.TransactionStatusAdapter;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ConcurrentModificationException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.FutureTask;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Matthew Tambara
 * @author Shuyang Zhou
 */
public class PortalPreferencesImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws NoSuchMethodException {
		_transactionInterceptor =
			(TransactionInterceptor)PortalBeanLocatorUtil.locate(
				"transactionAdvice");

		_originalTransactionExecutor = ReflectionTestUtil.getFieldValue(
			_transactionInterceptor, "transactionExecutor");

		_originalPortalPreferencesLocalService =
			PortalPreferencesLocalServiceUtil.getService();

		_updatePreferencesMethod =
			PortalPreferencesLocalService.class.getMethod(
				"updatePortalPreferences",
				com.liferay.portal.kernel.model.PortalPreferences.class);
	}

	@Before
	public void setUp() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

		portalPreferences.setValue(_NAMESPACE, "testKey", "testValue");

		_synchronizeThreadLocal.set(true);
	}

	@After
	public void tearDown() throws Throwable {
		_synchronizeThreadLocal.set(false);

		TransactionConfig.Builder builder = new TransactionConfig.Builder();

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

		PortalPreferencesWrapperCacheUtil.remove(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_USER);
	}

	@Test
	public void testReset() throws Exception {
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

		try {
			updateSynchronously(
				new FutureTask<>(callable), new FutureTask<>(callable));

			Assert.fail();
		}
		catch (Exception e) {
			Throwable cause = e.getCause();

			Assert.assertSame(
				ConcurrentModificationException.class, cause.getClass());
		}
	}

	@Test
	public void testSetSameKeyDifferentValues() throws Exception {
		FutureTask<Void> futureTask1 = new FutureTask<>(
			new Callable<Void>() {

				@Override
				public Void call() {
					PortalPreferences portalPreferences =
						PortletPreferencesFactoryUtil.getPortalPreferences(
							PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

					portalPreferences.setValues(
						_NAMESPACE, _KEY_1, new String[] {null, _VALUE_2});

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

					portalPreferences.setValue(_NAMESPACE, _KEY_1, _VALUE_1);

					return null;
				}

			});

		try {
			updateSynchronously(futureTask1, futureTask2);

			Assert.fail();
		}
		catch (Exception e) {
			Throwable cause = e.getCause();

			Assert.assertSame(
				ConcurrentModificationException.class, cause.getClass());
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

		updateSynchronously(futureTask1, futureTask2);

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

		Assert.assertEquals(
			_VALUE_1, portalPreferences.getValue(_NAMESPACE, _KEY_1));
		Assert.assertEquals(
			_VALUE_1, portalPreferences.getValue(_NAMESPACE, _KEY_2));
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

		try {
			updateSynchronously(futureTask1, futureTask2);

			Assert.fail();
		}
		catch (Exception e) {
			Throwable cause = e.getCause();

			Assert.assertSame(
				ConcurrentModificationException.class, cause.getClass());
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

		updateSynchronously(futureTask1, futureTask2);

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT, true);

		Assert.assertArrayEquals(
			_VALUES_1, portalPreferences.getValues(_NAMESPACE, _KEY_1));
		Assert.assertArrayEquals(
			_VALUES_1, portalPreferences.getValues(_NAMESPACE, _KEY_2));
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

		try {
			updateSynchronously(futureTask1, futureTask2);

			Assert.fail();
		}
		catch (Exception e) {
			Throwable cause = e.getCause();

			Assert.assertSame(
				ConcurrentModificationException.class, cause.getClass());
		}
	}

	protected void updateSynchronously(
			FutureTask<Void> futureTask1, FutureTask<Void> futureTask2)
		throws Exception {

		ReflectionTestUtil.setFieldValue(
			PortalPreferencesLocalServiceUtil.class, "_service",
			ProxyUtil.newProxyInstance(
				PortalPreferencesLocalService.class.getClassLoader(),
				new Class<?>[] {PortalPreferencesLocalService.class},
				new SynchronousInvocationHandler()));

		Thread thread1 = new Thread(futureTask1, "Update Thread 1");

		thread1.start();

		Thread thread2 = new Thread(futureTask2, "Update Thread 2");

		thread2.start();

		futureTask1.get();

		futureTask2.get();

		EntityCacheUtil.clearLocalCache();
		FinderCacheUtil.clearLocalCache();
	}

	protected static class SynchronizedTransactionExecutor
		extends DefaultTransactionExecutor {

		@Override
		public void commit(
			PlatformTransactionManager platformTransactionManager,
			TransactionAttributeAdapter transactionAttributeAdapter,
			TransactionStatusAdapter transactionStatusAdapter) {

			if (!_synchronizeThreadLocal.get()) {
				_originalTransactionExecutor.commit(
					platformTransactionManager, transactionAttributeAdapter,
					transactionStatusAdapter);

				return;
			}

			try {
				_cyclicBarrier.await();

				_originalTransactionExecutor.commit(
					platformTransactionManager, transactionAttributeAdapter,
					transactionStatusAdapter);
			}
			catch (Throwable t) {
				ReflectionUtil.throwException(t);
			}
			finally {
				PortalPreferencesWrapperCacheUtil.remove(
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_USER);
			}
		}

		private final CyclicBarrier _cyclicBarrier = new CyclicBarrier(
			2,
			new Runnable() {

				@Override
				public void run() {
					_transactionInterceptor.setTransactionExecutor(
						_originalTransactionExecutor);
				}

			});

	}

	protected static class SynchronousInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (_synchronizeThreadLocal.get() &&
				_updatePreferencesMethod.equals(method)) {

				_cyclicBarrier.await();
			}

			return method.invoke(_originalPortalPreferencesLocalService, args);
		}

		private final CyclicBarrier _cyclicBarrier = new CyclicBarrier(
			2,
			new Runnable() {

				@Override
				public void run() {
					_transactionInterceptor.setTransactionExecutor(
						new SynchronizedTransactionExecutor());

					ReflectionTestUtil.setFieldValue(
						PortalPreferencesLocalServiceUtil.class, "_service",
						_originalPortalPreferencesLocalService);
				}

			});

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
	private static final ThreadLocal<Boolean> _synchronizeThreadLocal =
		new InheritableThreadLocal<>();
	private static TransactionInterceptor _transactionInterceptor;
	private static Method _updatePreferencesMethod;

}