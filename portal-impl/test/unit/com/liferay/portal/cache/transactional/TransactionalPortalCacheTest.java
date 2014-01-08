/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.cache.memory.MemoryPortalCache;
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
	}

	@Test
	public void testMisc() throws Exception {
		Assert.assertEquals(_CACHE_NAME, _transactionalPortalCache.getName());

		Field cacheListenersField = ReflectionUtil.getDeclaredField(
			MemoryPortalCache.class, "_cacheListeners");

		Set<MockCacheListener> cacheListeners =
			(Set<MockCacheListener>)cacheListenersField.get(_portalCache);

		Assert.assertTrue(cacheListeners.isEmpty());

		MockCacheListener mockCacheListener1 = new MockCacheListener();

		_transactionalPortalCache.registerCacheListener(mockCacheListener1);

		Assert.assertEquals(1, cacheListeners.size());
		Assert.assertTrue(cacheListeners.contains(mockCacheListener1));

		MockCacheListener mockCacheListener2 = new MockCacheListener();

		_transactionalPortalCache.registerCacheListener(
			mockCacheListener2, CacheListenerScope.ALL);

		Assert.assertEquals(2, cacheListeners.size());
		Assert.assertTrue(cacheListeners.contains(mockCacheListener1));
		Assert.assertTrue(cacheListeners.contains(mockCacheListener2));

		_transactionalPortalCache.unregisterCacheListener(mockCacheListener1);

		Assert.assertEquals(1, cacheListeners.size());
		Assert.assertTrue(cacheListeners.contains(mockCacheListener2));

		_transactionalPortalCache.unregisterCacheListeners();

		Assert.assertTrue(cacheListeners.isEmpty());

		_transactionalPortalCache.destroy();
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

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_transactionalPortalCache.put(_KEY_1, _VALUE_2, 10);

		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_transactionalPortalCache.remove(_KEY_1);

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_transactionalPortalCache.removeAll();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));
	}

	@AdviseWith(adviceClasses = {EnableTransactionalCacheAdvice.class})
	@Test
	public void testTransactionalCacheWithoutTTL() {
		doTestTransactionalCache(false);
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
			_transactionalPortalCache.put(_KEY_1, null, 10);
		}
		else {
			_transactionalPortalCache.put(_KEY_1, null);
		}

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

		TransactionalPortalCacheHelper.rollback();

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		// Commit 1

		TransactionalPortalCacheHelper.begin();

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		if (ttl) {
			_transactionalPortalCache.put(_KEY_1, null, 10);
		}
		else {
			_transactionalPortalCache.put(_KEY_1, null);
		}

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

		if (ttl) {
			_transactionalPortalCache.put(_KEY_1, _VALUE_1, 10);
		}
		else {
			_transactionalPortalCache.put(_KEY_1, _VALUE_1);
		}

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_transactionalPortalCache.removeAll();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		TransactionalPortalCacheHelper.commit();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		// Commit 2

		_portalCache.put(_KEY_1, _VALUE_1);

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

		TransactionalPortalCacheHelper.commit();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));
	}

	private static final String _CACHE_NAME = "CACHE_NAME";

	private static final String _KEY_1 = "KEY_1";

	private static final String _KEY_2 = "KEY_2";

	private static final String _VALUE_1 = "VALUE_1";

	private static final String _VALUE_2 = "VALUE_2";

	private PortalCache<String, String> _portalCache;
	private TransactionalPortalCache<String, String> _transactionalPortalCache;

	private static class MockCacheListener
		implements CacheListener<String, String> {

		@Override
		public void notifyEntryEvicted(
			PortalCache<String, String> portalCache, String key, String value) {
		}

		@Override
		public void notifyEntryExpired(
			PortalCache<String, String> portalCache, String key, String value) {
		}

		@Override
		public void notifyEntryPut(
			PortalCache<String, String> portalCache, String key, String value) {
		}

		@Override
		public void notifyEntryRemoved(
			PortalCache<String, String> portalCache, String key, String value) {
		}

		@Override
		public void notifyEntryUpdated(
			PortalCache<String, String> portalCache, String key, String value) {
		}

		@Override
		public void notifyRemoveAll(PortalCache<String, String> portalCache) {
		}

	}

}