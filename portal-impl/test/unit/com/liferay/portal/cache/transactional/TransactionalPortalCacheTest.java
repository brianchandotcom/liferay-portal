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

package com.liferay.portal.cache.transactional;

import com.liferay.portal.cache.MockPortalCacheManager;
import com.liferay.portal.cache.TestCacheListener;
import com.liferay.portal.cache.TestCacheReplicator;
import com.liferay.portal.cache.memory.MemoryPortalCache;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.runners.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class TransactionalPortalCacheTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				Class<TransactionalPortalCacheHelper> clazz =
					TransactionalPortalCacheHelper.class;

				assertClasses.add(clazz);
				assertClasses.addAll(Arrays.asList(clazz.getDeclaredClasses()));
			}

		};

	@Before
	public void setUp() {
		_portalCache = new MemoryPortalCache<String, String>(
			new MockPortalCacheManager<String, String>(
				_PORTAL_CACHE_MANAGER_NAME),
			_PORTAL_CACHE_NAME, 16);
		_transactionalPortalCache =
			new TransactionalPortalCache<String, String>(_portalCache);

		_portalCache.put(_KEY_1, _VALUE_1);

		_testCacheListener = new TestCacheListener<String, String>();

		_portalCache.registerCacheListener(_testCacheListener);

		_testCacheReplicator = new TestCacheReplicator<String, String>();

		_portalCache.registerCacheListener(_testCacheReplicator);
	}

	@Test
	public void testConstructor() {
		new TransactionalPortalCacheHelper();
	}

	@AdviseWith(adviceClasses = {DisableTransactionalCacheAdvice.class})
	@Test
	public void testNoneTransactionalCache1() {
		TransactionalPortalCacheHelper.begin();

		TransactionalPortalCacheHelper.rollback();

		TransactionalPortalCacheHelper.commit();

		testNoneTransactionalCache2();
	}

	@AdviseWith(adviceClasses = {EnableTransactionalCacheAdvice.class})
	@Test
	public void testNoneTransactionalCache2() {
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

	@AdviseWith(adviceClasses = {EnableTransactionalCacheAdvice.class})
	@Test
	public void testTransactionalCacheWithoutTTL() {
		doTestTransactionalCache(false);
	}

	@AdviseWith(adviceClasses = {EnableTransactionalCacheAdvice.class})
	@Test
	public void testTransactionalCacheWithParameterValidation() {
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
	}

	@AdviseWith(adviceClasses = {EnableTransactionalCacheAdvice.class})
	@Test
	public void testTransactionalCacheWithTTL() {
		doTestTransactionalCache(true);
	}

	@Aspect
	public static class DisableTransactionalCacheAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"TRANSACTIONAL_CACHE_ENABLED)")
		public Object disableTransactionalCache(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.FALSE});
		}

	}

	@Aspect
	public static class EnableTransactionalCacheAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"TRANSACTIONAL_CACHE_ENABLED)")
		public Object enableTransactionalCache(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.TRUE});
		}

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

	private static final String _KEY_1 = "KEY_1";

	private static final String _KEY_2 = "KEY_2";

	private static final String _PORTAL_CACHE_MANAGER_NAME =
		"PORTAL_CACHE_MANAGER_NAME";

	private static final String _PORTAL_CACHE_NAME = "PORTAL_CACHE_NAME";

	private static final String _VALUE_1 = "VALUE_1";

	private static final String _VALUE_2 = "VALUE_2";

	private PortalCache<String, String> _portalCache;
	private TestCacheListener<String, String> _testCacheListener;
	private TestCacheReplicator<String, String> _testCacheReplicator;
	private TransactionalPortalCache<String, String> _transactionalPortalCache;

}