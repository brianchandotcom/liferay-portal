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

package com.liferay.portal.cache;

import com.liferay.portal.cache.test.TestPortalCache;
import com.liferay.portal.cache.test.TestPortalCacheListener;
import com.liferay.portal.cache.test.TestPortalCacheReplicator;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.transactional.TransactionalPortalCacheHelper;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionAttribute.Builder;
import com.liferay.portal.kernel.transaction.TransactionLifecycleListener;
import com.liferay.portal.kernel.transaction.TransactionStatus;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
				Class<TransactionalPortalCacheHelper> clazz =
					TransactionalPortalCacheHelper.class;

				assertClasses.add(clazz);
				assertClasses.addAll(Arrays.asList(clazz.getDeclaredClasses()));

				TransactionLifecycleListener transactionLifecycleListener =
					TransactionalPortalCacheHelper.
						TRANSACTION_LIFECYCLE_LISTENER;

				assertClasses.add(transactionLifecycleListener.getClass());
			}

		};

	@BeforeClass
	public static void setUpClass() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(
			EntityCache.class,
			(EntityCache)ProxyUtil.newProxyInstance(
				EntityCache.class.getClassLoader(),
				new Class<?>[] {EntityCache.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
						Object proxy, Method method, Object[] args) {

						return null;
					}

				}));
		registry.registerService(
			FinderCache.class,
			(FinderCache)ProxyUtil.newProxyInstance(
				FinderCache.class.getClassLoader(),
				new Class<?>[] {FinderCache.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
						Object proxy, Method method, Object[] args) {

						return null;
					}

				}));
	}

	@Before
	public void setUp() {
		_portalCache = new TestPortalCache<>(_PORTAL_CACHE_NAME);

		_transactionalPortalCache = new TransactionalPortalCache<>(
			_portalCache);

		_portalCache.put(_KEY_1, _VALUE_1);

		_testCacheListener = new TestPortalCacheListener<>();

		_portalCache.registerPortalCacheListener(_testCacheListener);

		_testCacheReplicator = new TestPortalCacheReplicator<>();

		_portalCache.registerPortalCacheListener(_testCacheReplicator);

		ReflectionTestUtil.setFieldValue(
			TransactionalPortalCacheHelper.class, "_transactionalCacheEnabled",
			null);
	}

	@Test
	public void testConstructor() {
		new TransactionalPortalCacheHelper();
	}

	@Test
	public void testNoneTransactionalCache1() {
		setEnableTransactionalCache(false);

		Assert.assertFalse(TransactionalPortalCacheHelper.isEnabled());

		doTestNoneTransactionalCache();
	}

	@Test
	public void testNoneTransactionalCache2() {
		setEnableTransactionalCache(true);

		doTestNoneTransactionalCache();
	}

	@Test
	public void testTransactionalCacheWithoutTTL() {
		setEnableTransactionalCache(true);

		doTestTransactionalCache(false);
	}

	@Test
	public void testTransactionalCacheWithParameterValidation() {
		setEnableTransactionalCache(true);

		TransactionalPortalCacheHelper.begin();

		// Get

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));

		// Get with null key

		try {
			_transactionalPortalCache.get(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		// Put

		_transactionalPortalCache.put(_KEY_1, _VALUE_2);

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));

		// Put with null key

		try {
			_transactionalPortalCache.put(null, _VALUE_1);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		// Put with null value

		try {
			_transactionalPortalCache.put(_KEY_1, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Value is null", npe.getMessage());
		}

		// Put with negative ttl

		try {
			_transactionalPortalCache.put(_KEY_1, _VALUE_1, -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Time to live is negative", iae.getMessage());
		}

		// Remove

		_transactionalPortalCache.remove(_KEY_1);

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));

		// Remove with null key

		try {
			_transactionalPortalCache.remove(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		TransactionalPortalCacheHelper.commit();
	}

	@Test
	public void testTransactionLifecycleListenerDisabled() {
		setEnableTransactionalCache(false);

		TransactionLifecycleListener transactionLifecycleListener =
			TransactionalPortalCacheHelper.TRANSACTION_LIFECYCLE_LISTENER;

		transactionLifecycleListener.created(null, null);

		transactionLifecycleListener.committed(null, null);

		transactionLifecycleListener.rollbacked(null, null, null);
	}

	@Test
	public void testTransactionLifecycleListenerEnabledWithBarrier() {
		doTestTransactionLifecycleListenerEnabledWithBarrier(
			Propagation.NOT_SUPPORTED);
		doTestTransactionLifecycleListenerEnabledWithBarrier(Propagation.NEVER);
		doTestTransactionLifecycleListenerEnabledWithBarrier(
			Propagation.NESTED);
	}

	@Test
	public void testTransactionLifecycleListenerEnabledWithExistTransaction() {
		setEnableTransactionalCache(true);

		Assert.assertEquals(0, getTransactionStackSize());

		TransactionLifecycleListener transactionLifecycleListener =
			TransactionalPortalCacheHelper.TRANSACTION_LIFECYCLE_LISTENER;

		Builder builder = new Builder();

		TransactionAttribute transactionAttribute = builder.build();

		TransactionStatus transactionStatus = new TransactionStatus(
			false, false, false);

		transactionLifecycleListener.created(
			transactionAttribute, transactionStatus);

		Assert.assertEquals(0, getTransactionStackSize());

		transactionLifecycleListener.committed(
			transactionAttribute, transactionStatus);

		Assert.assertEquals(0, getTransactionStackSize());

		transactionLifecycleListener.created(
			transactionAttribute, transactionStatus);

		Assert.assertEquals(0, getTransactionStackSize());

		transactionLifecycleListener.rollbacked(
			transactionAttribute, transactionStatus, null);

		Assert.assertEquals(0, getTransactionStackSize());
	}

	@Test
	public void testTransactionLifecycleListenerEnabledWithoutBarrier() {
		doTestTransactionLifecycleListenerEnabledWithoutBarrier(
			Propagation.REQUIRED);
		doTestTransactionLifecycleListenerEnabledWithoutBarrier(
			Propagation.SUPPORTS);
		doTestTransactionLifecycleListenerEnabledWithoutBarrier(
			Propagation.MANDATORY);
		doTestTransactionLifecycleListenerEnabledWithoutBarrier(
			Propagation.REQUIRES_NEW);
	}

	protected void doTestNoneTransactionalCache() {
		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		// Put 1

		_transactionalPortalCache.put(_KEY_2, _VALUE_2);

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(1);
		_testCacheListener.assertPut(_KEY_2, _VALUE_2);

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(1);
		_testCacheReplicator.assertPut(_KEY_2, _VALUE_2);

		_testCacheReplicator.reset();

		// Put 2

		_transactionalPortalCache.put(_KEY_1, _VALUE_2, 10);

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(1);
		_testCacheListener.assertUpdated(_KEY_1, _VALUE_2, 10);

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(1);
		_testCacheReplicator.assertUpdated(_KEY_1, _VALUE_2, 10);

		_testCacheReplicator.reset();

		// Put 3

		try {
			_transactionalPortalCache.put(_KEY_1, _VALUE_2, -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Time to live is negative", iae.getMessage());
		}

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(0);
		_testCacheReplicator.assertActionsCount(0);

		// Put 4

		PortalCacheHelperUtil.putWithoutReplicator(
			_transactionalPortalCache, _KEY_1, _VALUE_1);

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(1);
		_testCacheListener.assertUpdated(_KEY_1, _VALUE_1);

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(0);

		// Put 5

		PortalCacheHelperUtil.putWithoutReplicator(
			_transactionalPortalCache, _KEY_1, _VALUE_2, 10);

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(1);
		_testCacheListener.assertUpdated(_KEY_1, _VALUE_2, 10);

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(0);

		// Remove 1

		_transactionalPortalCache.remove(_KEY_1);

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(1);
		_testCacheListener.assertRemoved(_KEY_1, _VALUE_2);

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(1);
		_testCacheReplicator.assertRemoved(_KEY_1, _VALUE_2);

		_testCacheReplicator.reset();

		// Remove 2

		PortalCacheHelperUtil.removeWithoutReplicator(
			_transactionalPortalCache, _KEY_2);

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(1);
		_testCacheListener.assertRemoved(_KEY_2, _VALUE_2);

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(0);

		// Remove all 1

		_transactionalPortalCache.put(_KEY_1, _VALUE_1);
		_transactionalPortalCache.put(_KEY_2, _VALUE_2);

		_transactionalPortalCache.removeAll();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(3);
		_testCacheListener.assertPut(_KEY_1, _VALUE_1);
		_testCacheListener.assertPut(_KEY_2, _VALUE_2);
		_testCacheListener.assertRemoveAll();

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(3);
		_testCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_testCacheReplicator.assertPut(_KEY_2, _VALUE_2);
		_testCacheReplicator.assertRemoveAll();

		_testCacheReplicator.reset();

		// Remove all 2

		_transactionalPortalCache.put(_KEY_1, _VALUE_1);
		_transactionalPortalCache.put(_KEY_2, _VALUE_2);

		PortalCacheHelperUtil.removeAllWithoutReplicator(
			_transactionalPortalCache);

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(3);
		_testCacheListener.assertPut(_KEY_1, _VALUE_1);
		_testCacheListener.assertPut(_KEY_2, _VALUE_2);
		_testCacheListener.assertRemoveAll();

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(2);
		_testCacheReplicator.assertPut(_KEY_1, _VALUE_1);
		_testCacheReplicator.assertPut(_KEY_2, _VALUE_2);

		_testCacheReplicator.reset();
	}

	protected void doTestTransactionalCache(boolean ttl) {
		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		// Rollback

		TransactionalPortalCacheHelper.begin();

		_transactionalPortalCache.removeAll();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		if (ttl) {
			_transactionalPortalCache.put(_KEY_2, _VALUE_2, 10);
		}
		else {
			_transactionalPortalCache.put(_KEY_2, _VALUE_2);
		}

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_transactionalPortalCache.remove(_KEY_2);

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		if (ttl) {
			_transactionalPortalCache.put(_KEY_1, _VALUE_1, 10);
		}
		else {
			_transactionalPortalCache.put(_KEY_1, _VALUE_1);
		}

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_transactionalPortalCache.removeAll();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		PortalCacheHelperUtil.putWithoutReplicator(
			_transactionalPortalCache, _KEY_1, _VALUE_1);

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		PortalCacheHelperUtil.putWithoutReplicator(
			_transactionalPortalCache, _KEY_1, _VALUE_2, 10);

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(0);
		_testCacheReplicator.assertActionsCount(0);

		TransactionalPortalCacheHelper.rollback();

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(0);
		_testCacheReplicator.assertActionsCount(0);

		// Commit 1

		TransactionalPortalCacheHelper.begin();

		if (ttl) {
			_transactionalPortalCache.put(_KEY_2, _VALUE_2, 10);
		}
		else {
			_transactionalPortalCache.put(_KEY_2, _VALUE_2);
		}

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		if (ttl) {
			PortalCacheHelperUtil.putWithoutReplicator(
				_transactionalPortalCache, _KEY_1, _VALUE_2, 10);
		}
		else {
			PortalCacheHelperUtil.putWithoutReplicator(
				_transactionalPortalCache, _KEY_1, _VALUE_2);
		}

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		if (ttl) {
			PortalCacheHelperUtil.putWithoutReplicator(
				_transactionalPortalCache, _KEY_2, _VALUE_1, 10);
		}
		else {
			PortalCacheHelperUtil.putWithoutReplicator(
				_transactionalPortalCache, _KEY_2, _VALUE_1);
		}

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_transactionalPortalCache.removeAll();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(0);
		_testCacheReplicator.assertActionsCount(0);

		TransactionalPortalCacheHelper.commit();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(1);
		_testCacheListener.assertRemoveAll();

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(1);
		_testCacheReplicator.assertRemoveAll();

		_testCacheReplicator.reset();

		// Commit 2

		TransactionalPortalCacheHelper.begin();

		if (ttl) {
			_transactionalPortalCache.put(_KEY_1, _VALUE_1, 10);
		}
		else {
			_transactionalPortalCache.put(_KEY_1, _VALUE_1);
		}

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		if (ttl) {
			PortalCacheHelperUtil.putWithoutReplicator(
				_transactionalPortalCache, _KEY_1, _VALUE_2, 10);
		}
		else {
			PortalCacheHelperUtil.putWithoutReplicator(
				_transactionalPortalCache, _KEY_1, _VALUE_2);
		}

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(0);
		_testCacheReplicator.assertActionsCount(0);

		TransactionalPortalCacheHelper.commit();

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(1);

		if (ttl) {
			_testCacheListener.assertPut(_KEY_1, _VALUE_2, 10);
		}
		else {
			_testCacheListener.assertPut(_KEY_1, _VALUE_2);
		}

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(1);

		if (ttl) {
			_testCacheReplicator.assertPut(_KEY_1, _VALUE_2, 10);
		}
		else {
			_testCacheReplicator.assertPut(_KEY_1, _VALUE_2);
		}

		_testCacheReplicator.reset();

		// Commit 3

		TransactionalPortalCacheHelper.begin();

		_transactionalPortalCache.remove(_KEY_1);

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		if (ttl) {
			PortalCacheHelperUtil.putWithoutReplicator(
				_transactionalPortalCache, _KEY_2, _VALUE_2, 10);
		}
		else {
			PortalCacheHelperUtil.putWithoutReplicator(
				_transactionalPortalCache, _KEY_2, _VALUE_2);
		}

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		if (ttl) {
			PortalCacheHelperUtil.putWithoutReplicator(
				_transactionalPortalCache, _KEY_2, _VALUE_1, 10);
		}
		else {
			PortalCacheHelperUtil.putWithoutReplicator(
				_transactionalPortalCache, _KEY_2, _VALUE_1);
		}

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(0);
		_testCacheReplicator.assertActionsCount(0);

		TransactionalPortalCacheHelper.commit();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(2);
		_testCacheListener.assertRemoved(_KEY_1, _VALUE_2);

		if (ttl) {
			_testCacheListener.assertPut(_KEY_2, _VALUE_1, 10);
		}
		else {
			_testCacheListener.assertPut(_KEY_2, _VALUE_1);
		}

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(1);
		_testCacheReplicator.assertRemoved(_KEY_1, _VALUE_2);

		_testCacheReplicator.reset();

		// Commit 4

		TransactionalPortalCacheHelper.begin();

		PortalCacheHelperUtil.removeWithoutReplicator(
			_transactionalPortalCache, _KEY_2);

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(0);
		_testCacheReplicator.assertActionsCount(0);

		TransactionalPortalCacheHelper.commit();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(1);
		_testCacheListener.assertRemoved(_KEY_2, _VALUE_1);

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(0);

		// Commit 5

		_transactionalPortalCache.put(_KEY_1, _VALUE_1);

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(1);
		_testCacheListener.assertPut(_KEY_1, _VALUE_1);

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(1);
		_testCacheReplicator.assertPut(_KEY_1, _VALUE_1);

		_testCacheReplicator.reset();

		TransactionalPortalCacheHelper.begin();

		PortalCacheHelperUtil.removeAllWithoutReplicator(
			_transactionalPortalCache);

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		if (ttl) {
			PortalCacheHelperUtil.putWithoutReplicator(
				_transactionalPortalCache, _KEY_2, _VALUE_2, 10);
		}
		else {
			PortalCacheHelperUtil.putWithoutReplicator(
				_transactionalPortalCache, _KEY_2, _VALUE_2);
		}

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		PortalCacheHelperUtil.removeAllWithoutReplicator(
			_transactionalPortalCache);

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(0);
		_testCacheReplicator.assertActionsCount(0);

		TransactionalPortalCacheHelper.commit();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(1);
		_testCacheListener.assertRemoveAll();

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(0);
	}

	protected void doTestTransactionLifecycleListenerEnabledWithBarrier(
		Propagation propagation) {

		setEnableTransactionalCache(true);

		Assert.assertEquals(0, getTransactionStackSize());

		TransactionLifecycleListener transactionLifecycleListener =
			TransactionalPortalCacheHelper.TRANSACTION_LIFECYCLE_LISTENER;

		// Start parent transaction

		Builder parentBuilder = new Builder();

		TransactionAttribute parentTransactionAttribute = parentBuilder.build();

		TransactionStatus parentTransactionStatus = new TransactionStatus(
			true, false, false);

		transactionLifecycleListener.created(
			parentTransactionAttribute, parentTransactionStatus);

		Assert.assertEquals(1, getTransactionStackSize());

		// Start child transaction with barrier

		Builder childBuilder = new Builder();

		childBuilder.setPropagation(propagation);

		TransactionAttribute childTransactionAttribute = childBuilder.build();

		TransactionStatus childTransactionStatus = new TransactionStatus(
			true, false, false);

		transactionLifecycleListener.created(
			childTransactionAttribute, childTransactionStatus);

		Assert.assertEquals(0, getTransactionStackSize());

		// Start grandchild transaction

		Builder grandchildBuilder = new Builder();

		TransactionAttribute grandchildTransactionAttribute =
			grandchildBuilder.build();

		TransactionStatus grandchildTransactionStatus = new TransactionStatus(
			true, false, false);

		transactionLifecycleListener.created(
			grandchildTransactionAttribute, grandchildTransactionStatus);

		Assert.assertEquals(1, getTransactionStackSize());

		// Commit grandchild transaction

		transactionLifecycleListener.committed(
			grandchildTransactionAttribute, grandchildTransactionStatus);

		Assert.assertEquals(0, getTransactionStackSize());

		// Start grandchild transaction again

		transactionLifecycleListener.created(
			grandchildTransactionAttribute, grandchildTransactionStatus);

		Assert.assertEquals(1, getTransactionStackSize());

		// Rollback grandchild transaction

		transactionLifecycleListener.rollbacked(
			grandchildTransactionAttribute, grandchildTransactionStatus, null);

		Assert.assertEquals(0, getTransactionStackSize());

		// Commit child transaction

		transactionLifecycleListener.committed(
			childTransactionAttribute, childTransactionStatus);

		Assert.assertEquals(1, getTransactionStackSize());

		// Start child transaction with barrier with barrier again

		transactionLifecycleListener.created(
			childTransactionAttribute, childTransactionStatus);

		Assert.assertEquals(0, getTransactionStackSize());

		// Rollback child transaction

		transactionLifecycleListener.rollbacked(
			childTransactionAttribute, childTransactionStatus, null);

		Assert.assertEquals(1, getTransactionStackSize());

		// Commit parent transaction

		transactionLifecycleListener.committed(
			parentTransactionAttribute, parentTransactionStatus);

		Assert.assertEquals(0, getTransactionStackSize());
	}

	protected void doTestTransactionLifecycleListenerEnabledWithoutBarrier(
		Propagation propagation) {

		setEnableTransactionalCache(true);

		Assert.assertEquals(0, getTransactionStackSize());

		TransactionLifecycleListener transactionLifecycleListener =
			TransactionalPortalCacheHelper.TRANSACTION_LIFECYCLE_LISTENER;

		// Start parent transaction

		Builder parentBuilder = new Builder();

		TransactionAttribute parentTransactionAttribute = parentBuilder.build();

		TransactionStatus parentTransactionStatus = new TransactionStatus(
			true, false, false);

		transactionLifecycleListener.created(
			parentTransactionAttribute, parentTransactionStatus);

		Assert.assertEquals(1, getTransactionStackSize());

		// Start child transaction

		Builder childBuilder = new Builder();

		childBuilder.setPropagation(propagation);

		TransactionAttribute childTransactionAttribute = parentBuilder.build();

		TransactionStatus childTransactionStatus = new TransactionStatus(
			true, false, false);

		transactionLifecycleListener.created(
			childTransactionAttribute, childTransactionStatus);

		Assert.assertEquals(2, getTransactionStackSize());

		// Commit child transaction

		transactionLifecycleListener.committed(
			childTransactionAttribute, childTransactionStatus);

		Assert.assertEquals(1, getTransactionStackSize());

		// Start child transaction again

		transactionLifecycleListener.created(
			childTransactionAttribute, childTransactionStatus);

		Assert.assertEquals(2, getTransactionStackSize());

		// Rollback child transaction

		transactionLifecycleListener.rollbacked(
			childTransactionAttribute, childTransactionStatus, null);

		Assert.assertEquals(1, getTransactionStackSize());

		// Commit parent transaction

		transactionLifecycleListener.committed(
			parentTransactionAttribute, parentTransactionStatus);

		Assert.assertEquals(0, getTransactionStackSize());

		// Start parent transaction again

		transactionLifecycleListener.created(
			parentTransactionAttribute, parentTransactionStatus);

		Assert.assertEquals(1, getTransactionStackSize());

		// Rollback parent transaction

		transactionLifecycleListener.rollbacked(
			parentTransactionAttribute, parentTransactionStatus, null);

		Assert.assertEquals(0, getTransactionStackSize());
	}

	protected int getTransactionStackSize() {
		ThreadLocal<List<?>>
			portalCacheMapsThreadLocal = ReflectionTestUtil.getFieldValue(
				TransactionalPortalCacheHelper.class,
				"_portalCacheMapsThreadLocal");

		List<?> portalCacheMaps = portalCacheMapsThreadLocal.get();

		return portalCacheMaps.size();
	}

	private void setEnableTransactionalCache(boolean enabled) {
		TestProps testProps = new TestProps();

		testProps.setProperty(
			PropsKeys.TRANSACTIONAL_CACHE_ENABLED, Boolean.toString(enabled));

		PropsUtil.setProps(testProps);
	}

	private static final String _KEY_1 = "KEY_1";

	private static final String _KEY_2 = "KEY_2";

	private static final String _PORTAL_CACHE_NAME = "PORTAL_CACHE_NAME";

	private static final String _VALUE_1 = "VALUE_1";

	private static final String _VALUE_2 = "VALUE_2";

	private PortalCache<String, String> _portalCache;
	private TestPortalCacheListener<String, String> _testCacheListener;
	private TestPortalCacheReplicator<String, String> _testCacheReplicator;
	private TransactionalPortalCache<String, String> _transactionalPortalCache;

	private static class TestProps implements Props {

		@Override
		public boolean contains(String key) {
			return _properties.containsKey(key);
		}

		@Override
		public String get(String key) {
			return _properties.get(key);
		}

		@Override
		public String get(String key, Filter filter) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String[] getArray(String key) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String[] getArray(String key, Filter filter) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Properties getProperties() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Properties getProperties(String prefix, boolean removePrefix) {
			throw new UnsupportedOperationException();
		}

		public void setProperty(String key, String value) {
			_properties.put(key, value);
		}

		private final Map<String, String> _properties = new HashMap<>();

	}

}