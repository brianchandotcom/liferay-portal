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

import com.liferay.portal.cache.LowLevelCache;
import com.liferay.portal.cache.internal.mvcc.MVCCPortalCache;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import java.io.Serializable;

import java.util.List;
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
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE,
			new CodeCoverageAssertor() {

				@Override
				public void appendAssertClasses(List<Class<?>> assertClasses) {
					assertClasses.add(MVCCPortalCache.class);
				}

			});

	@Before
	public void setUp() {
		_portalCache = new TestPortalCache<>(_PORTAL_CACHE_NAME);

		_mvccPortalCache = new MVCCPortalCache<>(
			(LowLevelCache<String, MockMVCCModel>)_portalCache);

		_testPortalCacheListener = new TestPortalCacheListener<>();

		_portalCache.registerPortalCacheListener(_testPortalCacheListener);

		_testPortalCacheReplicator = new TestPortalCacheReplicator<>();

		_portalCache.registerPortalCacheListener(_testPortalCacheReplicator);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testForHiddenBridge() {
		@SuppressWarnings("rawtypes")
		MVCCPortalCache mvccPortalCache = new MVCCPortalCache(
			new TestPortalCache(_PORTAL_CACHE_NAME));

		Serializable key = _KEY_1;
		MockMVCCModel value = new MockMVCCModel(_VERSION_1);

		mvccPortalCache.put(key, value);
		mvccPortalCache.put(key, value, 10);
	}

	@AdviseWith(adviceClasses = {TestPortalCacheAdvice.class})
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testMVCCCacheWithAdvice() throws Exception {
		Assert.assertNull(_mvccPortalCache.get(_KEY_1));
		Assert.assertNull(_mvccPortalCache.get(_KEY_2));

		// Concurrent put 1

		TestPortalCacheAdvice.block();

		Thread thread1 = new Thread() {

			@Override
			public void run() {
				_mvccPortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_1));
			}

		};

		thread1.start();

		TestPortalCacheAdvice.waitUntilBlock(1);

		Thread thread2 = new Thread() {

			@Override
			public void run() {
				_mvccPortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_1));
			}

		};

		thread2.start();

		TestPortalCacheAdvice.waitUntilBlock(2);

		TestPortalCacheAdvice.unblock(2);

		thread1.join();
		thread2.join();

		_assertVersion(_VERSION_1, _mvccPortalCache.get(_KEY_1));
		Assert.assertNull(_mvccPortalCache.get(_KEY_2));

		_testPortalCacheListener.assertActionsCount(1);
		_testPortalCacheListener.assertPut(
			_KEY_1, new MockMVCCModel(_VERSION_1));

		_testPortalCacheListener.reset();

		_testPortalCacheReplicator.assertActionsCount(1);
		_testPortalCacheReplicator.assertPut(
			_KEY_1, new MockMVCCModel(_VERSION_1));

		_testPortalCacheReplicator.reset();

		// Concurrent put 2

		TestPortalCacheAdvice.block();

		thread1 = new Thread() {

			@Override
			public void run() {
				PortalCacheHelperUtil.putWithoutReplicator(
					_mvccPortalCache, _KEY_1, new MockMVCCModel(_VERSION_2));
			}

		};

		thread1.start();

		TestPortalCacheAdvice.waitUntilBlock(1);

		thread2 = new Thread() {

			@Override
			public void run() {
				PortalCacheHelperUtil.putWithoutReplicator(
					_mvccPortalCache, _KEY_1, new MockMVCCModel(_VERSION_2));
			}

		};

		thread2.start();

		TestPortalCacheAdvice.waitUntilBlock(2);

		TestPortalCacheAdvice.unblock(2);

		thread1.join();
		thread2.join();

		_assertVersion(_VERSION_2, _mvccPortalCache.get(_KEY_1));
		Assert.assertNull(_mvccPortalCache.get(_KEY_2));

		_testPortalCacheListener.assertActionsCount(1);
		_testPortalCacheListener.assertUpdated(
			_KEY_1, new MockMVCCModel(_VERSION_2));

		_testPortalCacheListener.reset();

		_testPortalCacheReplicator.assertActionsCount(0);
	}

	@Test
	public void testMVCCCacheWithoutTTL() {
		doTestMVCCCache(false);
	}

	@Test
	public void testMVCCCacheWithTTL() {
		doTestMVCCCache(true);
	}

	@Aspect
	public static class TestPortalCacheAdvice {

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
			"execution(protected * com.liferay.portal.cache.test.util." +
				"TestPortalCache.doPutIfAbsent(..))"
		)
		public Object doPutIfAbsent(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			Semaphore semaphore = _semaphore;

			if (semaphore != null) {
				semaphore.acquire();
			}

			return proceedingJoinPoint.proceed();
		}

		@Around(
			"execution(protected * com.liferay.portal.cache.test.util." +
				"TestPortalCache.doReplace(..))"
		)
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

		_testPortalCacheListener.assertActionsCount(1);

		if (timeToLive) {
			_testPortalCacheListener.assertPut(
				_KEY_1, new MockMVCCModel(_VERSION_1), 10);
		}
		else {
			_testPortalCacheListener.assertPut(
				_KEY_1, new MockMVCCModel(_VERSION_1));
		}

		_testPortalCacheListener.reset();

		_testPortalCacheReplicator.assertActionsCount(1);

		if (timeToLive) {
			_testPortalCacheReplicator.assertPut(
				_KEY_1, new MockMVCCModel(_VERSION_1), 10);
		}
		else {
			_testPortalCacheReplicator.assertPut(
				_KEY_1, new MockMVCCModel(_VERSION_1));
		}

		_testPortalCacheReplicator.reset();

		// Put 2

		if (timeToLive) {
			_mvccPortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_0), 10);
		}
		else {
			_mvccPortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_0));
		}

		_assertVersion(_VERSION_1, _mvccPortalCache.get(_KEY_1));
		Assert.assertNull(_mvccPortalCache.get(_KEY_2));

		_testPortalCacheListener.assertActionsCount(0);
		_testPortalCacheReplicator.assertActionsCount(0);

		// Put 3

		if (timeToLive) {
			_mvccPortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_2), 10);
		}
		else {
			_mvccPortalCache.put(_KEY_1, new MockMVCCModel(_VERSION_2));
		}

		_assertVersion(_VERSION_2, _mvccPortalCache.get(_KEY_1));
		Assert.assertNull(_mvccPortalCache.get(_KEY_2));

		_testPortalCacheListener.assertActionsCount(1);

		if (timeToLive) {
			_testPortalCacheListener.assertUpdated(
				_KEY_1, new MockMVCCModel(_VERSION_2), 10);
		}
		else {
			_testPortalCacheListener.assertUpdated(
				_KEY_1, new MockMVCCModel(_VERSION_2));
		}

		_testPortalCacheListener.reset();

		_testPortalCacheReplicator.assertActionsCount(1);

		if (timeToLive) {
			_testPortalCacheReplicator.assertUpdated(
				_KEY_1, new MockMVCCModel(_VERSION_2), 10);
		}
		else {
			_testPortalCacheReplicator.assertUpdated(
				_KEY_1, new MockMVCCModel(_VERSION_2));
		}

		_testPortalCacheReplicator.reset();
	}

	private void _assertVersion(long version, MockMVCCModel mockMVCCModel) {
		Assert.assertEquals(version, mockMVCCModel.getMvccVersion());
	}

	private static final String _KEY_1 = "KEY_1";

	private static final String _KEY_2 = "KEY_2";

	private static final String _PORTAL_CACHE_NAME = "PORTAL_CACHE_NAME";

	private static final long _VERSION_0 = 0;

	private static final long _VERSION_1 = 1;

	private static final long _VERSION_2 = 2;

	private MVCCPortalCache<String, MockMVCCModel> _mvccPortalCache;
	private PortalCache<String, MockMVCCModel> _portalCache;
	private TestPortalCacheListener<String, MockMVCCModel>
		_testPortalCacheListener;
	private TestPortalCacheReplicator<String, MockMVCCModel>
		_testPortalCacheReplicator;

	private static class MockMVCCModel implements MVCCModel, Serializable {

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

			if (_version == mockMVCCModel._version) {
				return true;
			}

			return false;
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