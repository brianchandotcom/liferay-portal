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

package com.liferay.portal.cache.mvcc;

import com.liferay.portal.cache.MockPortalCacheManager;
import com.liferay.portal.cache.TestCacheListener;
import com.liferay.portal.cache.TestCacheReplicator;
import com.liferay.portal.cache.memory.MemoryPortalCache;
import com.liferay.portal.kernel.cache.LowLevelCache;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.NewEnv;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJNewEnvMethodRule;

import java.io.Serializable;

import java.util.concurrent.Semaphore;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class MVCCPortalCacheTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() {
		_portalCache = new MemoryPortalCache<String, MVCCModel>(
			new MockPortalCacheManager<String, MVCCModel>(
				_PORTAL_CACHE_MANAGER_NAME),
			_PORTAL_CACHE_NAME, 16);

		_mvccPortalCache = new MVCCPortalCache<String, MVCCModel>(
			(LowLevelCache<String, MVCCModel>)_portalCache);

		_testCacheListener = new TestCacheListener<String, MVCCModel>();

		_portalCache.registerCacheListener(_testCacheListener);

		_testCacheReplicator = new TestCacheReplicator<String, MVCCModel>();

		_portalCache.registerCacheListener(_testCacheReplicator);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testForHiddenBridge() {
		@SuppressWarnings("rawtypes")
		MVCCPortalCache mvccPortalCache = new MVCCPortalCache(
			new MemoryPortalCache(
				new MockPortalCacheManager(_PORTAL_CACHE_MANAGER_NAME),
				_PORTAL_CACHE_NAME, 16));

		Serializable key = _KEY_1;
		Object value = new MockMVCCModel(_VERSION_1);

		mvccPortalCache.put(key, value);
		mvccPortalCache.put(key, value, 10);
	}

	@AdviseWith(adviceClasses = {MemoryPortalCacheAdvice.class})
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testMVCCCacheWithAdvice() throws Exception {
		Assert.assertNull(_mvccPortalCache.get(_KEY_1));
		Assert.assertNull(_mvccPortalCache.get(_KEY_2));

		// Concurrent put 1

		MemoryPortalCacheAdvice.block();

		Thread thread1 = new Thread() {

			@Override
			public void run() {
				_mvccPortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_1));
			}

		};

		thread1.start();

		MemoryPortalCacheAdvice.waitUntilBlock(1);

		Thread thread2 = new Thread() {

			@Override
			public void run() {
				_mvccPortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_1));
			}

		};

		thread2.start();

		MemoryPortalCacheAdvice.waitUntilBlock(2);

		MemoryPortalCacheAdvice.unblock(2);

		thread1.join();
		thread2.join();

		_assertVersion(_VERSION_1, _mvccPortalCache.get(_KEY_1));
		Assert.assertNull(_mvccPortalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(1);
		_testCacheListener.assertPut(_KEY_1, new MockMVCCModel(_VERSION_1));

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(1);
		_testCacheReplicator.assertPut(_KEY_1, new MockMVCCModel(_VERSION_1));

		_testCacheReplicator.reset();

		// Concurrent put 2

		MemoryPortalCacheAdvice.block();

		thread1 = new Thread() {

			@Override
			public void run() {
				PortalCacheHelperUtil.putWithoutReplicator(
					_mvccPortalCache, _KEY_1, new MockMVCCModel(_VERSION_2));
			}

		};

		thread1.start();

		MemoryPortalCacheAdvice.waitUntilBlock(1);

		thread2 = new Thread() {

			@Override
			public void run() {
				PortalCacheHelperUtil.putWithoutReplicator(
					_mvccPortalCache, _KEY_1, new MockMVCCModel(_VERSION_2));
			}

		};

		thread2.start();

		MemoryPortalCacheAdvice.waitUntilBlock(2);

		MemoryPortalCacheAdvice.unblock(2);

		thread1.join();
		thread2.join();

		_assertVersion(_VERSION_2, _mvccPortalCache.get(_KEY_1));
		Assert.assertNull(_mvccPortalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(1);
		_testCacheListener.assertUpdated(_KEY_1, new MockMVCCModel(_VERSION_2));

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(0);
	}

	@Test
	public void testMVCCCacheWithoutTTL() {
		doTestMVCCCache(false);
	}

	@Test
	public void testMVCCCacheWithTTL() {
		doTestMVCCCache(true);
	}

	@Rule
	public final AspectJNewEnvMethodRule aspectJNewEnvMethodRule =
		new AspectJNewEnvMethodRule();

	@Aspect
	public static class MemoryPortalCacheAdvice {

		public static void block() {
			_semaphore = new Semaphore(0);
		}

		public static void unblock(int permits) {
			Semaphore semaphore = _semaphore;

			_semaphore = null;

			semaphore.release(permits);
		}

		public static void waitUntilBlock(int threadCount) {
			Semaphore semaphore = _semaphore;

			if (semaphore != null) {
				while (semaphore.getQueueLength() < threadCount);
			}
		}

		@Around(
			"execution(protected * com.liferay.portal.cache.memory." +
				"MemoryPortalCache.doPutIfAbsent(..))")
		public Object doPutIfAbsent(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			Semaphore semaphore = _semaphore;

			if (semaphore != null) {
				semaphore.acquire();
			}

			return proceedingJoinPoint.proceed();
		}

		@Around(
			"execution(protected * com.liferay.portal.cache.memory." +
				"MemoryPortalCache.doReplace(..))")
		public Object doReplace(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			Semaphore semaphore = _semaphore;

			if (semaphore != null) {
				semaphore.acquire();
			}

			return proceedingJoinPoint.proceed();
		}

		private static volatile Semaphore _semaphore;

	}

	protected void doTestMVCCCache(final boolean timeToLive) {
		Assert.assertNull(_mvccPortalCache.get(_KEY_1));
		Assert.assertNull(_mvccPortalCache.get(_KEY_2));

		// Put 1

		if (timeToLive) {
			_mvccPortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_1), 10);
		}
		else {
			_mvccPortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_1));
		}

		_assertVersion(_VERSION_1, _mvccPortalCache.get(_KEY_1));
		Assert.assertNull(_mvccPortalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(1);

		if (timeToLive) {
			_testCacheListener.assertPut(
				_KEY_1, new MockMVCCModel(_VERSION_1), 10);
		}
		else {
			_testCacheListener.assertPut(_KEY_1, new MockMVCCModel(_VERSION_1));
		}

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(1);

		if (timeToLive) {
			_testCacheReplicator.assertPut(
				_KEY_1, new MockMVCCModel(_VERSION_1), 10);
		}
		else {
			_testCacheReplicator.assertPut(
				_KEY_1, new MockMVCCModel(_VERSION_1));
		}

		_testCacheReplicator.reset();

		// Put 2

		if (timeToLive) {
			_mvccPortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_0), 10);
		}
		else {
			_mvccPortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_0));
		}

		_assertVersion(_VERSION_1, _mvccPortalCache.get(_KEY_1));
		Assert.assertNull(_mvccPortalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(0);
		_testCacheReplicator.assertActionsCount(0);

		// Put 3

		if (timeToLive) {
			_mvccPortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_2), 10);
		}
		else {
			_mvccPortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_2));
		}

		_assertVersion(_VERSION_2, _mvccPortalCache.get(_KEY_1));
		Assert.assertNull(_mvccPortalCache.get(_KEY_2));

		_testCacheListener.assertActionsCount(1);

		if (timeToLive) {
			_testCacheListener.assertUpdated(
				_KEY_1, new MockMVCCModel(_VERSION_2), 10);
		}
		else {
			_testCacheListener.assertUpdated(
				_KEY_1, new MockMVCCModel(_VERSION_2));
		}

		_testCacheListener.reset();

		_testCacheReplicator.assertActionsCount(1);

		if (timeToLive) {
			_testCacheReplicator.assertUpdated(
				_KEY_1, new MockMVCCModel(_VERSION_2), 10);
		}
		else {
			_testCacheReplicator.assertUpdated(
				_KEY_1, new MockMVCCModel(_VERSION_2));
		}

		_testCacheReplicator.reset();
	}

	private void _assertVersion(long version, MVCCModel mvccModel) {
		Assert.assertEquals(version, mvccModel.getMvccVersion());
	}

	private static final String _KEY_1 = "KEY_1";

	private static final String _KEY_2 = "KEY_2";

	private static final String _PORTAL_CACHE_MANAGER_NAME =
		"PORTAL_CACHE_MANAGER_NAME";

	private static final String _PORTAL_CACHE_NAME = "PORTAL_CACHE_NAME";

	private static final long _VERSION_0 = 0;

	private static final long _VERSION_1 = 1;

	private static final long _VERSION_2 = 2;

	private MVCCPortalCache<String, MVCCModel> _mvccPortalCache;
	private PortalCache<String, MVCCModel> _portalCache;
	private TestCacheListener<String, MVCCModel> _testCacheListener;
	private TestCacheReplicator<String, MVCCModel> _testCacheReplicator;

	private static class MockMVCCModel implements MVCCModel {

		public MockMVCCModel(long version) {
			_version = version;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof MockMVCCModel)) {
				return false;
			}

			MockMVCCModel mockMVCCModel = (MockMVCCModel)object;

			return _version == mockMVCCModel._version;
		}

		@Override
		public long getMvccVersion() {
			return _version;
		}

		@Override
		public int hashCode() {
			return (int)_version;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
			_version = mvccVersion;
		}

		private long _version;

	}

}