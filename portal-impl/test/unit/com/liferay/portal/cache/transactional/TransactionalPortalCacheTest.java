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

import com.liferay.portal.cache.TestCacheListener;
import com.liferay.portal.cache.memory.MemoryPortalCache;
import com.liferay.portal.kernel.cache.PortalCache;
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
		_portalCache = new MemoryPortalCache<String, String>(_CACHE_NAME, 16);
		_transactionalPortalCache =
			new TransactionalPortalCache<String, String>(_portalCache);

		_portalCache.put(_KEY_1, _VALUE_1);

		_testCacheListener = new TestCacheListener<String, String>();

		_portalCache.registerCacheListener(_testCacheListener);
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

		_transactionalPortalCache.put(_KEY_2, _VALUE_2);

		_testCacheListener.assertPut(_KEY_2, _VALUE_2);
		_testCacheListener.assertActionsNumber(1);
		_testCacheListener.reset();

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_transactionalPortalCache.put(_KEY_1, _VALUE_2, 10);

		_testCacheListener.assertUpdated(_KEY_1, _VALUE_2);
		_testCacheListener.assertActionsNumber(1);
		_testCacheListener.reset();

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		try {
			_transactionalPortalCache.put(_KEY_1, _VALUE_2, -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Time to live is negative", iae.getMessage());
		}

		_transactionalPortalCache.remove(_KEY_1);

		_testCacheListener.assertRemoved(_KEY_1, _VALUE_2);
		_testCacheListener.assertActionsNumber(1);
		_testCacheListener.reset();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_transactionalPortalCache.removeAll();

		_testCacheListener.assertRemoveAll();
		_testCacheListener.assertActionsNumber(1);
		_testCacheListener.reset();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_transactionalPortalCache.putQuiet(_KEY_1, _VALUE_1);

		_testCacheListener.assertActionsNumber(0);

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_transactionalPortalCache.putQuiet(_KEY_1, _VALUE_2, 10);

		_testCacheListener.assertActionsNumber(0);

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		try {
			_transactionalPortalCache.putQuiet(_KEY_1, _VALUE_2, -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Time to live is negative", iae.getMessage());
		}
	}

	@AdviseWith(adviceClasses = {EnableTransactionalCacheAdvice.class})
	@Test
	public void testTransactionalCacheWithoutTTL() {
		doTestTransactionalCache(false);
	}

	@AdviseWith(adviceClasses = {EnableTransactionalCacheAdvice.class})
	@Test
	public void testTransactionalCacheWithParameterValidatation() {
		TransactionalPortalCacheHelper.begin();

		// Get

		try {
			_transactionalPortalCache.get(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		// Put 1

		try {
			_transactionalPortalCache.put(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Key is null", npe.getMessage());
		}

		// Put 2

		try {
			_transactionalPortalCache.put(_KEY_1, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Value is null", npe.getMessage());
		}

		// Put 3

		try {
			_transactionalPortalCache.put(_KEY_1, _VALUE_1, -1);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Time to live is negative", iae.getMessage());
		}

		// Remove

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

		_transactionalPortalCache.putQuiet(_KEY_1, _VALUE_1);

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_transactionalPortalCache.putQuiet(_KEY_1, _VALUE_2, 10);

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsNumber(0);

		TransactionalPortalCacheHelper.rollback();

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsNumber(0);

		// Commit 1

		TransactionalPortalCacheHelper.begin();

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

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
			_transactionalPortalCache.putQuiet(_KEY_1, _VALUE_2, 10);
		}
		else {
			_transactionalPortalCache.putQuiet(_KEY_1, _VALUE_2);
		}

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		if (ttl) {
			_transactionalPortalCache.putQuiet(_KEY_2, _VALUE_1, 10);
		}
		else {
			_transactionalPortalCache.putQuiet(_KEY_2, _VALUE_1);
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

		_testCacheListener.assertActionsNumber(0);

		TransactionalPortalCacheHelper.commit();

		_testCacheListener.assertRemoveAll();
		_testCacheListener.assertActionsNumber(1);
		_testCacheListener.reset();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		// Commit 2

		_portalCache.put(_KEY_1, _VALUE_1);

		_testCacheListener.assertPut(_KEY_1, _VALUE_1);
		_testCacheListener.assertActionsNumber(1);
		_testCacheListener.reset();

		TransactionalPortalCacheHelper.begin();

		_transactionalPortalCache.remove(_KEY_1);

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

		if (ttl) {
			_transactionalPortalCache.putQuiet(_KEY_2, _VALUE_1, 10);
		}
		else {
			_transactionalPortalCache.putQuiet(_KEY_2, _VALUE_1);
		}

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsNumber(0);

		TransactionalPortalCacheHelper.commit();

		_testCacheListener.assertPut(_KEY_2, _VALUE_1);
		_testCacheListener.assertRemoved(_KEY_1, _VALUE_1);
		_testCacheListener.assertActionsNumber(2);
		_testCacheListener.reset();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_2));

		// Commit 3

		_portalCache.removeAll();

		_testCacheListener.assertRemoveAll();
		_testCacheListener.assertActionsNumber(1);
		_testCacheListener.reset();

		TransactionalPortalCacheHelper.begin();

		if (ttl) {
			_transactionalPortalCache.putQuiet(_KEY_1, _VALUE_2, 10);
		}
		else {
			_transactionalPortalCache.putQuiet(_KEY_1, _VALUE_2);
		}

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		if (ttl) {
			_transactionalPortalCache.putQuiet(_KEY_2, _VALUE_1, 10);
		}
		else {
			_transactionalPortalCache.putQuiet(_KEY_2, _VALUE_1);
		}

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_testCacheListener.assertActionsNumber(0);

		TransactionalPortalCacheHelper.commit();

		_testCacheListener.assertActionsNumber(0);
	}

	private static final String _CACHE_NAME = "CACHE_NAME";

	private static final String _KEY_1 = "KEY_1";

	private static final String _KEY_2 = "KEY_2";

	private static final String _VALUE_1 = "VALUE_1";

	private static final String _VALUE_2 = "VALUE_2";

	private PortalCache<String, String> _portalCache;
	private TestCacheListener<String, String> _testCacheListener;
	private TransactionalPortalCache<String, String> _transactionalPortalCache;

}