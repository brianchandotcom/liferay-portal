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

package com.liferay.portlet.preferences.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.service.test.SynchronousInvocationHandler;
import com.liferay.portal.spring.aop.AopInvocationHandler;
import com.liferay.portal.spring.transaction.DefaultTransactionExecutor;
import com.liferay.portal.spring.transaction.TransactionAttributeAdapter;
import com.liferay.portal.spring.transaction.TransactionInterceptor;
import com.liferay.portal.spring.transaction.TransactionStatusAdapter;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.PortalPreferencesWrapperCacheUtil;

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
import org.junit.runner.RunWith;

import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Matthew Tambara
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class PortalPreferencesImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws NoSuchMethodException {
		_updatePreferencesMethod =
			PortalPreferencesLocalService.class.getMethod(
				"updatePortalPreferences",
				com.liferay.portal.kernel.model.PortalPreferences.class);

		_aopInvocationHandler = ProxyUtil.fetchInvocationHandler(
			_portalPreferencesLocalService, AopInvocationHandler.class);

		_transactionInterceptor = ReflectionTestUtil.getFieldValue(
			_aopInvocationHandler, "_transactionInterceptor");

		_originalTransactionExecutor = ReflectionTestUtil.getFieldValue(
			_transactionInterceptor, "_transactionHandler");

		_platformTransactionManager = ReflectionTestUtil.getFieldValue(
			_originalTransactionExecutor, "_platformTransactionManager");

		_synchronizeThreadLocal = ReflectionTestUtil.getFieldValue(
			SynchronousInvocationHandler.class, "_synchronizeThreadLocal");
	}

	@Before
	public void setUp() throws Exception {
		_testOwnerId = RandomTestUtil.nextLong();

		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(_testOwnerId, true);

		portalPreferences.setValue(_NAMESPACE, "testKey", "testValue");

		SynchronousInvocationHandler.enable();
	}

	@After
	public void tearDown() throws Throwable {
		SynchronousInvocationHandler.disable();

		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		TransactionInvokerUtil.invoke(
			builder.build(),
			() -> {
				com.liferay.portal.kernel.model.PortalPreferences
					portalPreferences =
						_portalPreferencesLocalService.fetchPortalPreferences(
							_testOwnerId, PortletKeys.PREFS_OWNER_TYPE_USER);

				if (portalPreferences != null) {
					_portalPreferencesLocalService.deletePortalPreferences(
						portalPreferences);
				}

				return null;
			});

		PortalPreferencesWrapperCacheUtil.remove(
			_testOwnerId, PortletKeys.PREFS_OWNER_TYPE_USER);
	}

	@Test
	public void testReset() {
		Callable<Void> callable = () -> {
			PortalPreferences portalPreferences =
				_portletPreferencesFactory.getPortalPreferences(
					_testOwnerId, true);

			portalPreferences.resetValues(_NAMESPACE);

			return null;
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
	public void testSetSameKeyDifferentValues() {
		FutureTask<Void> futureTask1 = new FutureTask<>(
			() -> {
				PortalPreferences portalPreferences =
					_portletPreferencesFactory.getPortalPreferences(
						_testOwnerId, true);

				portalPreferences.setValues(
					_NAMESPACE, _KEY_1, new String[] {null, _VALUE_2});

				return null;
			});

		FutureTask<Void> futureTask2 = new FutureTask<>(
			() -> {
				PortalPreferences portalPreferences =
					_portletPreferencesFactory.getPortalPreferences(
						_testOwnerId, true);

				portalPreferences.setValue(_NAMESPACE, _KEY_1, _VALUE_1);

				return null;
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
			() -> {
				PortalPreferences portalPreferences =
					_portletPreferencesFactory.getPortalPreferences(
						_testOwnerId, true);

				portalPreferences.setValue(_NAMESPACE, _KEY_1, _VALUE_1);

				return null;
			});

		FutureTask<Void> futureTask2 = new FutureTask<>(
			() -> {
				PortalPreferences portalPreferences =
					_portletPreferencesFactory.getPortalPreferences(
						_testOwnerId, true);

				portalPreferences.setValue(_NAMESPACE, _KEY_2, _VALUE_1);

				return null;
			});

		updateSynchronously(futureTask1, futureTask2);

		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(_testOwnerId, true);

		Assert.assertEquals(
			_VALUE_1, portalPreferences.getValue(_NAMESPACE, _KEY_1));
		Assert.assertEquals(
			_VALUE_1, portalPreferences.getValue(_NAMESPACE, _KEY_2));
	}

	@Test
	public void testSetValueSameKey() {
		FutureTask<Void> futureTask1 = new FutureTask<>(
			() -> {
				PortalPreferences portalPreferences =
					_portletPreferencesFactory.getPortalPreferences(
						_testOwnerId, true);

				portalPreferences.setValue(_NAMESPACE, _KEY_1, _VALUE_1);

				return null;
			});

		FutureTask<Void> futureTask2 = new FutureTask<>(
			() -> {
				PortalPreferences portalPreferences =
					_portletPreferencesFactory.getPortalPreferences(
						_testOwnerId, true);

				portalPreferences.setValue(_NAMESPACE, _KEY_1, _VALUE_2);

				return null;
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
			() -> {
				PortalPreferences portalPreferences =
					_portletPreferencesFactory.getPortalPreferences(
						_testOwnerId, true);

				portalPreferences.setValues(_NAMESPACE, _KEY_1, _VALUES_1);

				return null;
			});

		FutureTask<Void> futureTask2 = new FutureTask<>(
			() -> {
				PortalPreferences portalPreferences =
					_portletPreferencesFactory.getPortalPreferences(
						_testOwnerId, true);

				portalPreferences.setValues(_NAMESPACE, _KEY_2, _VALUES_1);

				return null;
			});

		updateSynchronously(futureTask1, futureTask2);

		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(_testOwnerId, true);

		Assert.assertArrayEquals(
			_VALUES_1, portalPreferences.getValues(_NAMESPACE, _KEY_1));
		Assert.assertArrayEquals(
			_VALUES_1, portalPreferences.getValues(_NAMESPACE, _KEY_2));
	}

	@Test
	public void testSetValuesSameKey() {
		FutureTask<Void> futureTask1 = new FutureTask<>(
			() -> {
				PortalPreferences portalPreferences =
					_portletPreferencesFactory.getPortalPreferences(
						_testOwnerId, true);

				portalPreferences.setValues(_NAMESPACE, _KEY_1, _VALUES_1);

				return null;
			});

		FutureTask<Void> futureTask2 = new FutureTask<>(
			() -> {
				PortalPreferences portalPreferences =
					_portletPreferencesFactory.getPortalPreferences(
						_testOwnerId, true);

				portalPreferences.setValues(_NAMESPACE, _KEY_1, _VALUES_2);

				return null;
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
				new SynchronousInvocationHandler(
					2,
					() -> {
						ReflectionTestUtil.setFieldValue(
							_transactionInterceptor, "_transactionHandler",
							new SynchronizedTransactionExecutor(_testOwnerId));

						_aopInvocationHandler.setTarget(
							_aopInvocationHandler.getTarget());

						ReflectionTestUtil.setFieldValue(
							PortalPreferencesLocalServiceUtil.class, "_service",
							_portalPreferencesLocalService);
					},
					_updatePreferencesMethod, _portalPreferencesLocalService)));

		Thread thread1 = new Thread(futureTask1, "Update Thread 1");

		thread1.start();

		Thread thread2 = new Thread(futureTask2, "Update Thread 2");

		thread2.start();

		futureTask1.get();

		futureTask2.get();

		_entityCache.clearLocalCache();
		_finderCache.clearLocalCache();
	}

	protected static class SynchronizedTransactionExecutor
		extends DefaultTransactionExecutor {

		@Override
		public void commit(
			TransactionAttributeAdapter transactionAttributeAdapter,
			TransactionStatusAdapter transactionStatusAdapter) {

			if (!_synchronizeThreadLocal.get()) {
				_originalTransactionExecutor.commit(
					transactionAttributeAdapter, transactionStatusAdapter);

				return;
			}

			try {
				_cyclicBarrier.await();

				_originalTransactionExecutor.commit(
					transactionAttributeAdapter, transactionStatusAdapter);
			}
			catch (Throwable t) {
				ReflectionUtil.throwException(t);
			}
			finally {
				PortalPreferencesWrapperCacheUtil.remove(
					_testOwnerId, PortletKeys.PREFS_OWNER_TYPE_USER);
			}
		}

		private SynchronizedTransactionExecutor(long testOwnerId) {
			super(_platformTransactionManager);

			_testOwnerId = testOwnerId;
		}

		private final CyclicBarrier _cyclicBarrier = new CyclicBarrier(
			2,
			new Runnable() {

				@Override
				public void run() {
					ReflectionTestUtil.setFieldValue(
						_transactionInterceptor, "_transactionHandler",
						_originalTransactionExecutor);

					_aopInvocationHandler.setTarget(
						_aopInvocationHandler.getTarget());
				}

			});

		private final long _testOwnerId;

	}

	private static final String _KEY_1 = "key1";

	private static final String _KEY_2 = "key2";

	private static final String _NAMESPACE = "test";

	private static final String _VALUE_1 = "value1";

	private static final String _VALUE_2 = "value2";

	private static final String[] _VALUES_1 = {"values1"};

	private static final String[] _VALUES_2 = {"values2"};

	private static AopInvocationHandler _aopInvocationHandler;
	private static DefaultTransactionExecutor _originalTransactionExecutor;
	private static PlatformTransactionManager _platformTransactionManager;

	@Inject
	private static PortalPreferencesLocalService _portalPreferencesLocalService;

	private static ThreadLocal<Boolean> _synchronizeThreadLocal;
	private static TransactionInterceptor _transactionInterceptor;
	private static Method _updatePreferencesMethod;

	@Inject
	private EntityCache _entityCache;

	@Inject
	private FinderCache _finderCache;

	@Inject
	private PortletPreferencesFactory _portletPreferencesFactory;

	private long _testOwnerId;

}