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

package com.liferay.portal.cache.test.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.cache.MVCCPortalCache;
import com.liferay.portal.cache.TransactionalPortalCache;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.transactional.TransactionalPortalCacheHelper;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionLifecycleListener;
import com.liferay.portal.kernel.transaction.TransactionStatus;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class TransactionalPortalCacheTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.add(TransactionalPortalCache.class);

				Class<TransactionalPortalCacheHelper> clazz =
					TransactionalPortalCacheHelper.class;

				assertClasses.add(clazz);

				Collections.addAll(assertClasses, clazz.getDeclaredClasses());

				TransactionLifecycleListener transactionLifecycleListener =
					TransactionalPortalCacheHelper.
						TRANSACTION_LIFECYCLE_LISTENER;

				assertClasses.add(transactionLifecycleListener.getClass());
			}

		};

	@BeforeClass
	public static void setUpClass() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	@Before
	public void setUp() {
		_portalCache = new TestPortalCache<>("Test Portal Cache");

		_testCacheListener = new TestPortalCacheListener<>();
		_testCacheReplicator = new TestPortalCacheReplicator<>();

		_portalCache.registerPortalCacheListener(_testCacheListener);
		_portalCache.registerPortalCacheListener(_testCacheReplicator);
	}

	@Test
	public void testConcurrentTransactionForMVCCPortalCache() throws Exception {
		_setEnableTransactionalCache(true);

		TransactionalPortalCache<String, String> transactionalPortalCache =
			new TransactionalPortalCache<>(_portalCache, true);

		// Two read only transactions do put

		_invokeTransactionalPortalCacheConcurrently(
			transactionalPortalCache, _KEY_1, _VALUE_1, true, _KEY_2, _VALUE_2,
			true, false);

		_testCacheListener.assertPut(_KEY_1, _VALUE_1);
		_testCacheListener.assertPut(_KEY_2, _VALUE_2);
		_testCacheListener.assertActionsCount(2);

		_testCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_testCacheReplicator.assertPut(_KEY_2, _VALUE_2);
		_testCacheReplicator.assertActionsCount(2);

		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// Two read only transactions do remove

		_invokeTransactionalPortalCacheConcurrently(
			transactionalPortalCache, _KEY_1, null, true, _KEY_2, null, true,
			false);

		_testCacheListener.assertActionsCount(0);
		_testCacheReplicator.assertActionsCount(0);

		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		// One read only transaction and one write transaction do put

		_invokeTransactionalPortalCacheConcurrently(
			transactionalPortalCache, _KEY_1, _VALUE_2, true, _KEY_2, _VALUE_1,
			false, false);

		_testCacheListener.assertUpdated(_KEY_1, _VALUE_2);
		_testCacheListener.assertUpdated(_KEY_2, _VALUE_1);
		_testCacheListener.assertActionsCount(2);

		_testCacheReplicator.assertUpdated(_KEY_1, _VALUE_2);
		_testCacheReplicator.assertUpdated(_KEY_2, _VALUE_1);
		_testCacheReplicator.assertActionsCount(2);

		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_2));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// One write transaction and one read only transaction do put

		_invokeTransactionalPortalCacheConcurrently(
			transactionalPortalCache, _KEY_1, _VALUE_1, false, _KEY_2, _VALUE_2,
			true, false);

		_testCacheListener.assertUpdated(_KEY_1, _VALUE_1);
		_testCacheListener.assertUpdated(_KEY_2, _VALUE_2);
		_testCacheListener.assertActionsCount(2);

		_testCacheReplicator.assertUpdated(_KEY_1, _VALUE_1);
		_testCacheListener.assertUpdated(_KEY_2, _VALUE_2);
		_testCacheReplicator.assertActionsCount(2);

		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// Two write transactions do put

		_invokeTransactionalPortalCacheConcurrently(
			transactionalPortalCache, _KEY_1, _VALUE_2, false, _KEY_2, _VALUE_1,
			false, false);

		_testCacheListener.assertUpdated(_KEY_1, _VALUE_2);
		_testCacheListener.assertUpdated(_KEY_2, _VALUE_1);
		_testCacheListener.assertActionsCount(2);

		_testCacheReplicator.assertUpdated(_KEY_1, _VALUE_2);
		_testCacheReplicator.assertUpdated(_KEY_2, _VALUE_1);
		_testCacheReplicator.assertActionsCount(2);

		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_2));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// Two write transactions do remove without replicator

		_invokeTransactionalPortalCacheConcurrently(
			transactionalPortalCache, _KEY_1, null, false, _KEY_2, null, false,
			true);

		_testCacheListener.assertRemoved(_KEY_1, _VALUE_2);
		_testCacheListener.assertRemoved(_KEY_2, _VALUE_1);
		_testCacheListener.assertActionsCount(2);

		_testCacheReplicator.assertActionsCount(0);

		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));
	}

	@Test
	public void testConcurrentTransactionForNonmvccPortalCache()
		throws Exception {

		_setEnableTransactionalCache(true);

		TransactionalPortalCache<String, String> transactionalPortalCache =
			new TransactionalPortalCache<>(_portalCache, false);

		// Two read only transactions do put

		_invokeTransactionalPortalCacheConcurrently(
			transactionalPortalCache, _KEY_1, _VALUE_1, true, _KEY_2, _VALUE_2,
			true, false);

		_testCacheListener.assertPut(_KEY_1, _VALUE_1);
		_testCacheListener.assertPut(_KEY_2, _VALUE_2);
		_testCacheListener.assertActionsCount(2);

		_testCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_testCacheReplicator.assertPut(_KEY_2, _VALUE_2);
		_testCacheReplicator.assertActionsCount(2);

		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// Two read only transactions do remove

		_invokeTransactionalPortalCacheConcurrently(
			transactionalPortalCache, _KEY_1, null, true, _KEY_2, null, true,
			false);

		_testCacheListener.assertActionsCount(0);
		_testCacheReplicator.assertActionsCount(0);

		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		// One read only transaction and one write transaction do put

		_invokeTransactionalPortalCacheConcurrently(
			transactionalPortalCache, _KEY_1, _VALUE_2, true, _KEY_2, _VALUE_1,
			false, false);

		_testCacheListener.assertUpdated(_KEY_1, _VALUE_2);
		_testCacheListener.assertUpdated(_KEY_2, _VALUE_1);
		_testCacheListener.assertActionsCount(2);

		_testCacheReplicator.assertUpdated(_KEY_1, _VALUE_2);
		_testCacheReplicator.assertUpdated(_KEY_2, _VALUE_1);
		_testCacheReplicator.assertActionsCount(2);

		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_2));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// One write transaction and one read only transaction do put

		_invokeTransactionalPortalCacheConcurrently(
			transactionalPortalCache, _KEY_1, _VALUE_1, false, _KEY_2, _VALUE_2,
			true, false);

		_testCacheListener.assertUpdated(_KEY_1, _VALUE_1);
		_testCacheListener.assertActionsCount(1);

		_testCacheReplicator.assertUpdated(_KEY_1, _VALUE_1);
		_testCacheReplicator.assertActionsCount(1);

		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_2));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// Two write transactions do put

		_invokeTransactionalPortalCacheConcurrently(
			transactionalPortalCache, _KEY_1, _VALUE_2, false, _KEY_2, _VALUE_2,
			false, false);

		_testCacheListener.assertUpdated(_KEY_1, _VALUE_2);
		_testCacheListener.assertRemoved(_KEY_2, _VALUE_1);
		_testCacheListener.assertActionsCount(2);

		_testCacheReplicator.assertUpdated(_KEY_1, _VALUE_2);
		_testCacheReplicator.assertRemoved(_KEY_2, _VALUE_1);
		_testCacheReplicator.assertActionsCount(2);

		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// Two write transactions do remove without replicator

		_invokeTransactionalPortalCacheConcurrently(
			transactionalPortalCache, _KEY_1, null, false, _KEY_2, null, false,
			true);

		_testCacheListener.assertRemoved(_KEY_1, _VALUE_2);
		_testCacheListener.assertRemoved(_KEY_2, null);
		_testCacheListener.assertActionsCount(2);

		_testCacheReplicator.assertActionsCount(0);

		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));
	}

	@Test
	public void testMisc() {

		// For code coverage

		new TransactionalPortalCacheHelper();

		_setEnableTransactionalCache(true);

		TransactionalPortalCacheHelper.begin();

		TransactionalPortalCache<String, String> transactionalPortalCache =
			new TransactionalPortalCache(_portalCache);

		TransactionalPortalCacheHelper.put(
			transactionalPortalCache, _KEY_1, _VALUE_1, 0);

		TransactionalPortalCacheHelper.removeAll(transactionalPortalCache);

		TransactionalPortalCacheHelper.commit();

		TransactionLifecycleListener transactionLifecycleListener =
			TransactionalPortalCacheHelper.TRANSACTION_LIFECYCLE_LISTENER;

		_setEnableTransactionalCache(false);

		transactionLifecycleListener.created(null, null);

		transactionLifecycleListener.committed(null, null);

		transactionLifecycleListener.rollbacked(null, null, null);
	}

	@Test
	public void testNoneTransactionalCache() {
		_setEnableTransactionalCache(false);

		Assert.assertFalse(
			"TransactionalPortalCacheHelper should be disabled",
			TransactionalPortalCacheHelper.isEnabled());

		// MVCC portal cache when transactional cache is disabled

		_testNoneTransactionalPortalCache(
			new TransactionalPortalCache<>(_portalCache, true));

		// Non MVCC portal cache when transactional cache is disabled

		_testNoneTransactionalPortalCache(
			new TransactionalPortalCache<>(_portalCache, false));

		// MVCC portal cache when not used in transaction

		_setEnableTransactionalCache(true);

		Assert.assertFalse(
			"TransactionalPortalCacheHelper should be disabled",
			TransactionalPortalCacheHelper.isEnabled());

		_testNoneTransactionalPortalCache(
			new TransactionalPortalCache<>(_portalCache, true));

		// Non MVCC portal cache when not used in transaction

		_testNoneTransactionalPortalCache(
			new TransactionalPortalCache<>(_portalCache, false));
	}

	@Test
	public void testTransactionalCache() {
		_setEnableTransactionalCache(true);

		// MVCC portal cache without ttl

		_testTransactionalPortalCache(
			new TransactionalPortalCache<>(_portalCache, true), false, true);

		// Non MVCC portal cache without ttl

		_testTransactionalPortalCache(
			new TransactionalPortalCache<>(_portalCache, false), false, false);

		// MVCC portal cache with ttl

		_testTransactionalPortalCache(
			new TransactionalPortalCache<>(_portalCache, true), true, true);

		// Non MVCC portal cache with ttl

		_testTransactionalPortalCache(
			new TransactionalPortalCache<>(_portalCache, false), true, false);
	}

	@Test
	public void testTransactionalCacheWithParameterValidation() {
		_setEnableTransactionalCache(true);

		TransactionalPortalCache<String, String> transactionalPortalCache =
			new TransactionalPortalCache<>(_portalCache, true);

		_portalCache.put(_KEY_1, _VALUE_1);

		TransactionalPortalCacheHelper.begin();

		// Get

		Assert.assertEquals(_VALUE_1, transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));

		// Get with null key

		try {
			transactionalPortalCache.get(null);

			Assert.fail("Should throw NullPointerException");
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		// Put

		transactionalPortalCache.put(_KEY_1, _VALUE_2);

		Assert.assertEquals(_VALUE_2, transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));

		// Put with null key

		try {
			transactionalPortalCache.put(null, _VALUE_1);

			Assert.fail("Should throw NullPointerException");
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		// Put with null value

		try {
			transactionalPortalCache.put(_KEY_1, null);

			Assert.fail("Should throw NullPointerException");
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Value is null", npe.getMessage());
		}

		// Put with negative ttl

		try {
			transactionalPortalCache.put(_KEY_1, _VALUE_1, -1);

			Assert.fail("Should throw IllegalArgumentException");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Time to live is negative", iae.getMessage());
		}

		// Remove

		transactionalPortalCache.remove(_KEY_1);

		Assert.assertNull(transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));

		// Remove with null key

		try {
			transactionalPortalCache.remove(null);

			Assert.fail("Should throw NullPointerException");
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		TransactionalPortalCacheHelper.commit(false);
	}

	@Test
	public void testTransactionalPortalCacheHelperEnabled() {
		_setEnableTransactionalCache(false);

		Assert.assertFalse(
			"TransactionalPortalCacheHelper should be disabled",
			TransactionalPortalCacheHelper.isEnabled());

		_setEnableTransactionalCache(true);

		Assert.assertFalse(
			"TransactionalPortalCacheHelper should be disabled",
			TransactionalPortalCacheHelper.isEnabled());

		TransactionalPortalCacheHelper.begin();

		Assert.assertTrue(
			"TransactionalPortalCacheHelper should be enabled",
			TransactionalPortalCacheHelper.isEnabled());

		TransactionalPortalCacheHelper.commit(false);

		ReflectionTestUtil.setFieldValue(
			TransactionalPortalCacheHelper.class, "_transactionalCacheEnabled",
			null);

		PropsTestUtil.setProps(Collections.emptyMap());

		Assert.assertFalse(
			"TransactionalPortalCacheHelper should be disabled",
			TransactionalPortalCacheHelper.isEnabled());
	}

	@Test
	public void testTransactionalPortalCacheWithRealMVCCPortalCache() {
		_setEnableTransactionalCache(true);

		TransactionalPortalCache<String, MVCCModel> transactionalPortalCache =
			new TransactionalPortalCache<>(
				new MVCCPortalCache<>(
					new TestPortalCache<>("Test MVCC Portal Cache")),
				true);

		// Put real value and commit

		TransactionalPortalCacheHelper.begin();

		MockMVCCModel mockMVCCModel = new MockMVCCModel(0);

		transactionalPortalCache.put(_KEY_1, mockMVCCModel);

		TransactionalPortalCacheHelper.commit(false);

		Assert.assertSame(mockMVCCModel, transactionalPortalCache.get(_KEY_1));

		// Remove, put NullModel and commit

		TransactionalPortalCacheHelper.begin();

		transactionalPortalCache.remove(_KEY_1);

		MVCCModel nullMVCCModel = ReflectionTestUtil.getFieldValue(
			BasePersistenceImpl.class, "nullModel");

		transactionalPortalCache.put(_KEY_1, nullMVCCModel);

		TransactionalPortalCacheHelper.commit(false);

		Assert.assertSame(nullMVCCModel, transactionalPortalCache.get(_KEY_1));
	}

	@Test
	public void testTransactionLifecycleListenerEnabledWithBarrier() {
		_setEnableTransactionalCache(true);

		_testTransactionLifecycleListenerEnabledWithBarrier(
			Propagation.NOT_SUPPORTED);
		_testTransactionLifecycleListenerEnabledWithBarrier(Propagation.NEVER);
		_testTransactionLifecycleListenerEnabledWithBarrier(Propagation.NESTED);
	}

	@Test
	public void testTransactionLifecycleListenerEnabledWithExistTransaction() {
		_setEnableTransactionalCache(true);

		Assert.assertEquals(0, _getTransactionStackSize());

		TransactionLifecycleListener transactionLifecycleListener =
			TransactionalPortalCacheHelper.TRANSACTION_LIFECYCLE_LISTENER;

		TransactionAttribute.Builder builder =
			new TransactionAttribute.Builder();

		TransactionAttribute transactionAttribute = builder.build();

		TransactionStatus transactionStatus = new TestTrasactionStatus(
			false, false, false);

		transactionLifecycleListener.created(
			transactionAttribute, transactionStatus);

		Assert.assertEquals(0, _getTransactionStackSize());

		transactionLifecycleListener.committed(
			transactionAttribute, transactionStatus);

		Assert.assertEquals(0, _getTransactionStackSize());

		transactionLifecycleListener.created(
			transactionAttribute, transactionStatus);

		Assert.assertEquals(0, _getTransactionStackSize());

		transactionLifecycleListener.rollbacked(
			transactionAttribute, transactionStatus, null);

		Assert.assertEquals(0, _getTransactionStackSize());
	}

	@Test
	public void testTransactionLifecycleListenerEnabledWithoutBarrier() {
		_setEnableTransactionalCache(true);

		_testTransactionLifecycleListenerEnabledWithoutBarrier(
			Propagation.REQUIRED);
		_testTransactionLifecycleListenerEnabledWithoutBarrier(
			Propagation.SUPPORTS);
		_testTransactionLifecycleListenerEnabledWithoutBarrier(
			Propagation.MANDATORY);
		_testTransactionLifecycleListenerEnabledWithoutBarrier(
			Propagation.REQUIRES_NEW);
	}

	private int _getTransactionStackSize() {
		ThreadLocal<List<?>> portalCacheMapsThreadLocal =
			ReflectionTestUtil.getFieldValue(
				TransactionalPortalCacheHelper.class,
				"_portalCacheMapsThreadLocal");

		List<?> portalCacheMaps = portalCacheMapsThreadLocal.get();

		return portalCacheMaps.size();
	}

	private void _invokeTransactionalPortalCacheConcurrently(
			TransactionalPortalCache<String, String> transactionalPortalCache,
			String key1, String value1, boolean readOnly1, String key2,
			String value2, boolean readOnly2, boolean skipReplicator)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		StackTraceElement[] stackTraceElements = currentThread.getStackTrace();

		StackTraceElement stackTraceElement = stackTraceElements[2];

		StringBundler sb = new StringBundler(5);

		sb.append(stackTraceElement.getClassName());
		sb.append(StringPool.UNDERLINE);
		sb.append(stackTraceElement.getMethodName());
		sb.append("_LineNumber_");
		sb.append(stackTraceElement.getLineNumber());

		String threadNamePrefix = sb.toString();

		TestCallable testCallable1 = new TestCallable(
			transactionalPortalCache, key1, value1, readOnly1, skipReplicator);
		TestCallable testCallable2 = new TestCallable(
			transactionalPortalCache, key2, value2, readOnly2, skipReplicator);

		FutureTask<Void> futureTask1 = new FutureTask<>(testCallable1);
		FutureTask<Void> futureTask2 = new FutureTask<>(testCallable2);

		Thread thread1 = new Thread(
			futureTask1, threadNamePrefix + "_Thread_1");
		Thread thread2 = new Thread(
			futureTask2, threadNamePrefix + "_Thread_2");

		thread1.start();
		thread2.start();

		testCallable1.waitUntilBlock();
		testCallable2.waitUntilBlock();

		testCallable1.unblock();
		futureTask1.get();

		testCallable2.unblock();
		futureTask2.get();
	}

	private void _setEnableTransactionalCache(boolean enabled) {
		ReflectionTestUtil.setFieldValue(
			TransactionalPortalCacheHelper.class, "_transactionalCacheEnabled",
			enabled);
	}

	private void _testNoneTransactionalPortalCache(
		TransactionalPortalCache<String, String> transactionalPortalCache) {

		// Put 1

		transactionalPortalCache.put(_KEY_1, _VALUE_1);

		_testCacheListener.assertPut(_KEY_1, _VALUE_1);
		_testCacheListener.assertActionsCount(1);

		_testCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_testCacheReplicator.assertActionsCount(1);

		Assert.assertEquals(_VALUE_1, transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// Put 2

		transactionalPortalCache.put(_KEY_1, _VALUE_2, 10);

		_testCacheListener.assertUpdated(_KEY_1, _VALUE_2, 10);
		_testCacheListener.assertActionsCount(1);

		_testCacheReplicator.assertUpdated(_KEY_1, _VALUE_2, 10);
		_testCacheReplicator.assertActionsCount(1);

		Assert.assertEquals(_VALUE_2, transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// Put 3

		try {
			transactionalPortalCache.put(_KEY_1, _VALUE_2, -1);

			Assert.fail("Should throw IllegalArgumentException");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Time to live is negative", iae.getMessage());
		}

		// Put 4

		PortalCacheHelperUtil.putWithoutReplicator(
			transactionalPortalCache, _KEY_1, _VALUE_1);

		_testCacheListener.assertUpdated(_KEY_1, _VALUE_1);
		_testCacheListener.assertActionsCount(1);

		_testCacheReplicator.assertActionsCount(0);

		Assert.assertEquals(_VALUE_1, transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));

		_testCacheListener.reset();

		// Put 5

		PortalCacheHelperUtil.putWithoutReplicator(
			transactionalPortalCache, _KEY_1, _VALUE_2, 10);

		_testCacheListener.assertUpdated(_KEY_1, _VALUE_2, 10);
		_testCacheListener.assertActionsCount(1);

		_testCacheReplicator.assertActionsCount(0);

		Assert.assertEquals(_VALUE_2, transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));

		_testCacheListener.reset();

		// Remove 1

		transactionalPortalCache.remove(_KEY_1);

		_testCacheListener.assertRemoved(_KEY_1, _VALUE_2);
		_testCacheListener.assertActionsCount(1);

		_testCacheReplicator.assertRemoved(_KEY_1, _VALUE_2);
		_testCacheReplicator.assertActionsCount(1);

		Assert.assertNull(transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_1));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// Remove 2

		PortalCacheHelperUtil.putWithoutReplicator(
			transactionalPortalCache, _KEY_1, _VALUE_1);
		PortalCacheHelperUtil.removeWithoutReplicator(
			transactionalPortalCache, _KEY_1);

		_testCacheListener.assertPut(_KEY_1, _VALUE_1);
		_testCacheListener.assertRemoved(_KEY_1, _VALUE_1);
		_testCacheListener.assertActionsCount(2);

		_testCacheReplicator.assertActionsCount(0);

		Assert.assertNull(transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_1));

		_testCacheListener.reset();

		// Remove all 1

		transactionalPortalCache.put(_KEY_1, _VALUE_1);
		transactionalPortalCache.put(_KEY_2, _VALUE_2);

		transactionalPortalCache.removeAll();

		_testCacheListener.assertPut(_KEY_1, _VALUE_1);
		_testCacheListener.assertPut(_KEY_2, _VALUE_2);
		_testCacheListener.assertRemoveAll();
		_testCacheListener.assertActionsCount(3);

		_testCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_testCacheReplicator.assertPut(_KEY_2, _VALUE_2);
		_testCacheReplicator.assertRemoveAll();
		_testCacheReplicator.assertActionsCount(3);

		Assert.assertNull(transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// Remove all 2

		transactionalPortalCache.put(_KEY_1, _VALUE_1);
		transactionalPortalCache.put(_KEY_2, _VALUE_2);

		PortalCacheHelperUtil.removeAllWithoutReplicator(
			transactionalPortalCache);

		_testCacheListener.assertPut(_KEY_1, _VALUE_1);
		_testCacheListener.assertPut(_KEY_2, _VALUE_2);
		_testCacheListener.assertRemoveAll();
		_testCacheListener.assertActionsCount(3);

		_testCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_testCacheReplicator.assertPut(_KEY_2, _VALUE_2);
		_testCacheReplicator.assertActionsCount(2);

		Assert.assertNull(transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.reset();
		_testCacheReplicator.reset();
	}

	private void _testTransactionalPortalCache(
		TransactionalPortalCache<String, String> transactionalPortalCache,
		boolean ttl, boolean mvcc) {

		// Rollback

		TransactionalPortalCacheHelper.begin();

		if (ttl) {
			transactionalPortalCache.put(_KEY_1, _VALUE_1, 10);
		}
		else {
			transactionalPortalCache.put(_KEY_1, _VALUE_1);
		}

		Assert.assertEquals(_VALUE_1, transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));

		TransactionalPortalCacheHelper.rollback();

		_testCacheListener.assertActionsCount(0);
		_testCacheReplicator.assertActionsCount(0);

		Assert.assertNull(transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_1));

		// Commit 1

		TransactionalPortalCacheHelper.begin();

		if (ttl) {
			transactionalPortalCache.put(_KEY_1, _VALUE_1, 10);

			PortalCacheHelperUtil.putWithoutReplicator(
				transactionalPortalCache, _KEY_1, _VALUE_2, 10);
		}
		else {
			transactionalPortalCache.put(_KEY_1, _VALUE_1);

			PortalCacheHelperUtil.putWithoutReplicator(
				transactionalPortalCache, _KEY_1, _VALUE_2);
		}

		Assert.assertEquals(_VALUE_2, transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_1));

		TransactionalPortalCacheHelper.commit(false);

		if (ttl) {
			_testCacheListener.assertPut(_KEY_1, _VALUE_2, 10);
		}
		else {
			_testCacheListener.assertPut(_KEY_1, _VALUE_2);
		}

		_testCacheListener.assertActionsCount(1);

		if (ttl) {
			_testCacheReplicator.assertPut(_KEY_1, _VALUE_2, 10);
		}
		else {
			_testCacheReplicator.assertPut(_KEY_1, _VALUE_2);
		}

		_testCacheReplicator.assertActionsCount(1);

		Assert.assertEquals(_VALUE_2, transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// Commit 2

		TransactionalPortalCacheHelper.begin();

		if (ttl) {
			PortalCacheHelperUtil.putWithoutReplicator(
				transactionalPortalCache, _KEY_1, _VALUE_2, 10);

			transactionalPortalCache.put(_KEY_1, _VALUE_1, 10);
		}
		else {
			PortalCacheHelperUtil.putWithoutReplicator(
				transactionalPortalCache, _KEY_1, _VALUE_2);

			transactionalPortalCache.put(_KEY_1, _VALUE_1);
		}

		Assert.assertEquals(_VALUE_1, transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));

		TransactionalPortalCacheHelper.commit(false);

		if (ttl) {
			_testCacheListener.assertUpdated(_KEY_1, _VALUE_1, 10);
		}
		else {
			_testCacheListener.assertUpdated(_KEY_1, _VALUE_1);
		}

		_testCacheListener.assertActionsCount(1);

		if (ttl) {
			_testCacheReplicator.assertUpdated(_KEY_1, _VALUE_1, 10);
		}
		else {
			_testCacheReplicator.assertUpdated(_KEY_1, _VALUE_1);
		}

		_testCacheReplicator.assertActionsCount(1);

		Assert.assertEquals(_VALUE_1, transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// Commit 3

		TransactionalPortalCacheHelper.begin();

		PortalCacheHelperUtil.removeAllWithoutReplicator(
			transactionalPortalCache);

		if (ttl) {
			PortalCacheHelperUtil.putWithoutReplicator(
				transactionalPortalCache, _KEY_1, _VALUE_2, 10);
		}
		else {
			PortalCacheHelperUtil.putWithoutReplicator(
				transactionalPortalCache, _KEY_1, _VALUE_2);
		}

		Assert.assertEquals(_VALUE_2, transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));

		TransactionalPortalCacheHelper.commit(false);

		_testCacheListener.assertRemoveAll();

		if (ttl) {
			_testCacheListener.assertPut(_KEY_1, _VALUE_2, 10);
		}
		else {
			_testCacheListener.assertPut(_KEY_1, _VALUE_2);
		}

		_testCacheListener.assertActionsCount(2);

		_testCacheReplicator.assertActionsCount(0);

		Assert.assertEquals(_VALUE_2, transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));

		_testCacheListener.reset();

		// Commit 4

		TransactionalPortalCacheHelper.begin();

		transactionalPortalCache.remove(_KEY_1);

		if (ttl) {
			PortalCacheHelperUtil.putWithoutReplicator(
				transactionalPortalCache, _KEY_1, _VALUE_1, 10);
		}
		else {
			PortalCacheHelperUtil.putWithoutReplicator(
				transactionalPortalCache, _KEY_1, _VALUE_1);
		}

		Assert.assertEquals(_VALUE_1, transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));

		TransactionalPortalCacheHelper.commit(false);

		if (mvcc) {
			_testCacheListener.assertRemoved(_KEY_1, _VALUE_2);

			if (ttl) {
				_testCacheListener.assertPut(_KEY_1, _VALUE_1, 10);
			}
			else {
				_testCacheListener.assertPut(_KEY_1, _VALUE_1);
			}

			_testCacheListener.assertActionsCount(2);

			_testCacheReplicator.assertRemoved(_KEY_1, _VALUE_2);

			if (ttl) {
				_testCacheReplicator.assertPut(_KEY_1, _VALUE_1, 10);
			}
			else {
				_testCacheReplicator.assertPut(_KEY_1, _VALUE_1);
			}

			_testCacheReplicator.assertActionsCount(2);
		}
		else {
			if (ttl) {
				_testCacheListener.assertUpdated(_KEY_1, _VALUE_1, 10);
			}
			else {
				_testCacheListener.assertUpdated(_KEY_1, _VALUE_1);
			}

			_testCacheListener.assertActionsCount(1);

			if (ttl) {
				_testCacheReplicator.assertUpdated(_KEY_1, _VALUE_1, 10);
			}
			else {
				_testCacheReplicator.assertUpdated(_KEY_1, _VALUE_1);
			}

			_testCacheReplicator.assertActionsCount(1);
		}

		Assert.assertEquals(_VALUE_1, transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// Commit 5

		TransactionalPortalCacheHelper.begin();

		PortalCacheHelperUtil.removeWithoutReplicator(
			transactionalPortalCache, _KEY_1);

		if (ttl) {
			PortalCacheHelperUtil.putWithoutReplicator(
				transactionalPortalCache, _KEY_1, _VALUE_2, 10);
		}
		else {
			PortalCacheHelperUtil.putWithoutReplicator(
				transactionalPortalCache, _KEY_1, _VALUE_2);
		}

		Assert.assertEquals(_VALUE_2, transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));

		TransactionalPortalCacheHelper.commit(false);

		if (mvcc) {
			_testCacheListener.assertRemoved(_KEY_1, _VALUE_1);

			if (ttl) {
				_testCacheListener.assertPut(_KEY_1, _VALUE_2, 10);
			}
			else {
				_testCacheListener.assertPut(_KEY_1, _VALUE_2);
			}

			_testCacheListener.assertActionsCount(2);
			_testCacheReplicator.assertActionsCount(0);
		}
		else {
			if (ttl) {
				_testCacheListener.assertUpdated(_KEY_1, _VALUE_2, 10);
			}
			else {
				_testCacheListener.assertUpdated(_KEY_1, _VALUE_2);
			}

			_testCacheListener.assertActionsCount(1);
			_testCacheReplicator.assertActionsCount(0);
		}

		Assert.assertEquals(_VALUE_2, transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));

		_testCacheListener.reset();
		_testCacheReplicator.reset();

		// Commit 6

		TransactionalPortalCacheHelper.begin();

		transactionalPortalCache.removeAll();

		if (ttl) {
			transactionalPortalCache.put(_KEY_1, _VALUE_1, 10);
		}
		else {
			transactionalPortalCache.put(_KEY_1, _VALUE_1);
		}

		PortalCacheHelperUtil.removeAllWithoutReplicator(
			transactionalPortalCache);

		Assert.assertNull(transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));

		TransactionalPortalCacheHelper.commit(false);

		_testCacheListener.assertRemoveAll();
		_testCacheListener.assertActionsCount(1);

		_testCacheReplicator.assertRemoveAll();
		_testCacheReplicator.assertActionsCount(1);

		Assert.assertNull(transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_1));

		_testCacheListener.reset();
		_testCacheReplicator.reset();
	}

	private void _testTransactionLifecycleListenerEnabledWithBarrier(
		Propagation propagation) {

		Assert.assertEquals(0, _getTransactionStackSize());

		TransactionLifecycleListener transactionLifecycleListener =
			TransactionalPortalCacheHelper.TRANSACTION_LIFECYCLE_LISTENER;

		// Start parent transaction

		TransactionAttribute.Builder parentBuilder =
			new TransactionAttribute.Builder();

		TransactionAttribute parentTransactionAttribute = parentBuilder.build();

		TransactionStatus parentTransactionStatus = new TestTrasactionStatus(
			true, false, false);

		transactionLifecycleListener.created(
			parentTransactionAttribute, parentTransactionStatus);

		Assert.assertEquals(1, _getTransactionStackSize());

		// Start child transaction with barrier

		TransactionAttribute.Builder childBuilder =
			new TransactionAttribute.Builder();

		childBuilder.setPropagation(propagation);

		TransactionAttribute childTransactionAttribute = childBuilder.build();

		TransactionStatus childTransactionStatus = new TestTrasactionStatus(
			true, false, false);

		transactionLifecycleListener.created(
			childTransactionAttribute, childTransactionStatus);

		Assert.assertEquals(0, _getTransactionStackSize());

		// Start grandchild transaction

		TransactionAttribute.Builder grandchildBuilder =
			new TransactionAttribute.Builder();

		TransactionAttribute grandchildTransactionAttribute =
			grandchildBuilder.build();

		TransactionStatus grandchildTransactionStatus =
			new TestTrasactionStatus(true, false, false);

		transactionLifecycleListener.created(
			grandchildTransactionAttribute, grandchildTransactionStatus);

		Assert.assertEquals(1, _getTransactionStackSize());

		// Commit grandchild transaction

		transactionLifecycleListener.committed(
			grandchildTransactionAttribute, grandchildTransactionStatus);

		Assert.assertEquals(0, _getTransactionStackSize());

		// Start grandchild transaction again

		transactionLifecycleListener.created(
			grandchildTransactionAttribute, grandchildTransactionStatus);

		Assert.assertEquals(1, _getTransactionStackSize());

		// Rollback grandchild transaction

		transactionLifecycleListener.rollbacked(
			grandchildTransactionAttribute, grandchildTransactionStatus, null);

		Assert.assertEquals(0, _getTransactionStackSize());

		// Commit child transaction

		transactionLifecycleListener.committed(
			childTransactionAttribute, childTransactionStatus);

		Assert.assertEquals(1, _getTransactionStackSize());

		// Start child transaction with barrier with barrier again

		transactionLifecycleListener.created(
			childTransactionAttribute, childTransactionStatus);

		Assert.assertEquals(0, _getTransactionStackSize());

		// Rollback child transaction

		transactionLifecycleListener.rollbacked(
			childTransactionAttribute, childTransactionStatus, null);

		Assert.assertEquals(1, _getTransactionStackSize());

		// Commit parent transaction

		transactionLifecycleListener.committed(
			parentTransactionAttribute, parentTransactionStatus);

		Assert.assertEquals(0, _getTransactionStackSize());
	}

	private void _testTransactionLifecycleListenerEnabledWithoutBarrier(
		Propagation propagation) {

		Assert.assertEquals(0, _getTransactionStackSize());

		TransactionLifecycleListener transactionLifecycleListener =
			TransactionalPortalCacheHelper.TRANSACTION_LIFECYCLE_LISTENER;

		// Start parent transaction

		TransactionAttribute.Builder parentBuilder =
			new TransactionAttribute.Builder();

		TransactionAttribute parentTransactionAttribute = parentBuilder.build();

		TransactionStatus parentTransactionStatus = new TestTrasactionStatus(
			true, false, false);

		transactionLifecycleListener.created(
			parentTransactionAttribute, parentTransactionStatus);

		Assert.assertEquals(1, _getTransactionStackSize());

		// Start child transaction

		TransactionAttribute.Builder childBuilder =
			new TransactionAttribute.Builder();

		childBuilder.setPropagation(propagation);

		TransactionAttribute childTransactionAttribute = parentBuilder.build();

		TransactionStatus childTransactionStatus = new TestTrasactionStatus(
			true, false, false);

		transactionLifecycleListener.created(
			childTransactionAttribute, childTransactionStatus);

		Assert.assertEquals(2, _getTransactionStackSize());

		// Commit child transaction

		transactionLifecycleListener.committed(
			childTransactionAttribute, childTransactionStatus);

		Assert.assertEquals(1, _getTransactionStackSize());

		// Start child transaction again

		transactionLifecycleListener.created(
			childTransactionAttribute, childTransactionStatus);

		Assert.assertEquals(2, _getTransactionStackSize());

		// Rollback child transaction

		transactionLifecycleListener.rollbacked(
			childTransactionAttribute, childTransactionStatus, null);

		Assert.assertEquals(1, _getTransactionStackSize());

		// Commit parent transaction

		transactionLifecycleListener.committed(
			parentTransactionAttribute, parentTransactionStatus);

		Assert.assertEquals(0, _getTransactionStackSize());

		// Start parent transaction again

		transactionLifecycleListener.created(
			parentTransactionAttribute, parentTransactionStatus);

		Assert.assertEquals(1, _getTransactionStackSize());

		// Rollback parent transaction

		transactionLifecycleListener.rollbacked(
			parentTransactionAttribute, parentTransactionStatus, null);

		Assert.assertEquals(0, _getTransactionStackSize());
	}

	private static final String _KEY_1 = "KEY_1";

	private static final String _KEY_2 = "KEY_2";

	private static final String _VALUE_1 = "VALUE_1";

	private static final String _VALUE_2 = "VALUE_2";

	private PortalCache<String, String> _portalCache;
	private TestPortalCacheListener<String, String> _testCacheListener;
	private TestPortalCacheReplicator<String, String> _testCacheReplicator;

	private static class TestCallable implements Callable<Void> {

		@Override
		public Void call() throws Exception {
			TransactionalPortalCacheHelper.begin();

			if (_skipReplicator) {
				if (_value == null) {
					PortalCacheHelperUtil.removeWithoutReplicator(
						_transactionalPortalCache, _key);
				}
				else {
					PortalCacheHelperUtil.putWithoutReplicator(
						_transactionalPortalCache, _key, _value);
				}
			}
			else {
				if (_value == null) {
					_transactionalPortalCache.remove(_key);
				}
				else {
					_transactionalPortalCache.put(_key, _value);
				}
			}

			_waitCountDownLatch.countDown();

			_blockCountDownLatch.await();

			TransactionalPortalCacheHelper.commit(_readOnly);

			return null;
		}

		public void unblock() {
			_blockCountDownLatch.countDown();
		}

		public void waitUntilBlock() throws InterruptedException {
			_waitCountDownLatch.await();
		}

		private TestCallable(
			TransactionalPortalCache<String, String> transactionalPortalCache,
			String key, String value, boolean readOnly,
			boolean skipReplicator) {

			_transactionalPortalCache = transactionalPortalCache;
			_key = key;
			_value = value;
			_readOnly = readOnly;
			_skipReplicator = skipReplicator;
		}

		private final CountDownLatch _blockCountDownLatch = new CountDownLatch(
			1);
		private final String _key;
		private final boolean _readOnly;
		private final boolean _skipReplicator;
		private final TransactionalPortalCache<String, String>
			_transactionalPortalCache;
		private final String _value;
		private final CountDownLatch _waitCountDownLatch = new CountDownLatch(
			1);

	}

	private static class TestTrasactionStatus implements TransactionStatus {

		@Override
		public Object getPlatformTransactionManager() {
			return null;
		}

		@Override
		public boolean isCompleted() {
			return _completed;
		}

		@Override
		public boolean isNewTransaction() {
			return _newTransaction;
		}

		@Override
		public boolean isRollbackOnly() {
			return _rollbackOnly;
		}

		@Override
		public void suppressLifecycleListenerThrowable(
			Throwable lifecycleThrowable) {
		}

		private TestTrasactionStatus(
			boolean newTransaction, boolean rollbackOnly, boolean completed) {

			_newTransaction = newTransaction;
			_rollbackOnly = rollbackOnly;
			_completed = completed;
		}

		private final boolean _completed;
		private final boolean _newTransaction;
		private final boolean _rollbackOnly;

	}

}