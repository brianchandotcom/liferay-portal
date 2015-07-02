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

package com.liferay.portal.kernel.cache.index;

import com.liferay.portal.cache.test.TestPortalCache;
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.concurrent.test.MappedMethodCallableInvocationHandler;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class PortalCacheIndexerTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_portalCache = new TestPortalCache<>(RandomTestUtil.randomString());

		_portalCacheIndexer = new PortalCacheIndexer<>(
			_indexEncoder, _portalCache);

		_cacheListener = ReflectionTestUtil.getFieldValue(
			_portalCache, "aggregatedCacheListener");

		_mappedMethodCallableInvocationHandler =
			new MappedMethodCallableInvocationHandler(
				ReflectionTestUtil.getFieldValue(
					_portalCacheIndexer, "_indexedCacheKeys"),
				true);

		ReflectionTestUtil.setFieldValue(
			_portalCacheIndexer, "_indexedCacheKeys",
			ProxyUtil.newProxyInstance(
				ClassLoader.getSystemClassLoader(),
				new Class<?>[] {ConcurrentMap.class},
				_mappedMethodCallableInvocationHandler));
	}

	@Test
	public void testAddIndexedCacheKeyConcurrentPutDifferentKeys()
		throws ReflectiveOperationException {

		_mappedMethodCallableInvocationHandler.putBeforeCallable(
			ConcurrentMap.class.getMethod(
				"putIfAbsent", Object.class, Object.class),
			new Callable<Void>() {

				@Override
				public Void call() {
					_portalCache.put(_INDEX_1_KEY_1, _VALUE);

					return null;
				}

			});

		_portalCache.put(_INDEX_1_KEY_2, _VALUE);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testAddIndexedCacheKeyPutSameKey() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testAddIndexedCacheKeyWithDifferentIndex() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);
		_portalCache.put(_INDEX_2_KEY_3, _VALUE);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testAddIndexedCacheKeyWithSameIndex() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);
		_portalCache.put(_INDEX_1_KEY_2, _VALUE);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testConstructor() {
		_portalCache = new TestPortalCache<>(RandomTestUtil.randomString());

		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCacheIndexer = new PortalCacheIndexer<>(
			_indexEncoder, _portalCache);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testDispose() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCache.unregisterCacheListeners();

		Set<TestKey> testKeys = _portalCacheIndexer.getKeys(
			_indexEncoder.encode(_INDEX_1_KEY_1));

		Assert.assertTrue(testKeys.isEmpty());
	}

	@Test
	public void testGetIndexedCacheKeysWithIndexKey() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		Set<TestKey> testKeys = _portalCacheIndexer.getKeys(
			_indexEncoder.encode(_INDEX_1_KEY_1));

		testKeys.clear();

		assertIndexCacheSynchronization();
	}

	@Test
	public void testGetIndexedCacheKeysWithoutIndexKey() {
		_portalCacheIndexer.getKeys(_indexEncoder.encode(_INDEX_1_KEY_1));

		assertIndexCacheSynchronization();
	}

	@Test
	public void testNotifyEntryEvicted() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCache.remove(_INDEX_1_KEY_1);

		_cacheListener.notifyEntryEvicted(
			_portalCache, _INDEX_1_KEY_1, _VALUE, 0);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testNotifyEntryExpired() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCache.remove(_INDEX_1_KEY_1);

		_cacheListener.notifyEntryExpired(
			_portalCache, _INDEX_1_KEY_1, _VALUE, 0);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testNotifyEntryRemoved() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCache.remove(_INDEX_1_KEY_1);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testNotifyEntryUpdated() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testNotifyRemoveAll() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);
		_portalCache.put(_INDEX_1_KEY_2, _VALUE);
		_portalCache.put(_INDEX_2_KEY_3, _VALUE);

		_portalCache.removeAll();

		assertIndexCacheSynchronization();
	}

	@Test
	public void testRemoveIndexedCacheKeyConcurrentPut()
		throws ReflectiveOperationException {

		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_mappedMethodCallableInvocationHandler.putBeforeCallable(
			ConcurrentMap.class.getMethod("remove", Object.class, Object.class),
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					_portalCache.put(_INDEX_1_KEY_2, _VALUE);

					return null;
				}

			});

		_portalCache.remove(_INDEX_1_KEY_1);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testRemoveIndexedCacheKeyConcurrentRemove()
		throws ReflectiveOperationException {

		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_mappedMethodCallableInvocationHandler.putBeforeCallable(
			ConcurrentMap.class.getMethod("remove", Object.class, Object.class),
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					_portalCacheIndexer.removeKeys(1L);

					return null;
				}

			});

		_portalCache.remove(_INDEX_1_KEY_1);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testRemoveIndexedCacheKeysWithIndex() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCacheIndexer.removeKeys(_indexEncoder.encode(_INDEX_1_KEY_1));

		assertIndexCacheSynchronization();
	}

	@Test
	public void testRemoveIndexedCacheKeysWithoutIndex() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCacheIndexer.removeKeys(_indexEncoder.encode(_INDEX_2_KEY_3));

		assertIndexCacheSynchronization();
	}

	@Test
	public void testRemoveIndexedCacheKeyWithKey() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);
		_portalCache.put(_INDEX_1_KEY_2, _VALUE);

		_portalCache.remove(_INDEX_1_KEY_1);

		assertIndexCacheSynchronization();
	}

	@Test
	public void testRemoveIndexedCacheKeyWithoutKey() {
		_portalCache.put(_INDEX_1_KEY_1, _VALUE);

		_portalCache.remove(_INDEX_1_KEY_2);

		assertIndexCacheSynchronization();
	}

	protected void assertIndexCacheSynchronization() {
		Set<TestKey> expectedTestKeys = new HashSet<>(_portalCache.getKeys());

		Set<Long> indexes = new HashSet<>();

		for (TestKey testKey : expectedTestKeys) {
			indexes.add(_indexEncoder.encode(testKey));
		}

		Set<TestKey> actualTestKeys = new HashSet<>();

		for (Long index : indexes) {
			actualTestKeys.addAll(_portalCacheIndexer.getKeys(index));
		}

		Assert.assertEquals(expectedTestKeys, actualTestKeys);
	}

	private static final TestKey _INDEX_1_KEY_1 = new TestKey(1L, 1L);

	private static final TestKey _INDEX_1_KEY_2 = new TestKey(1L, 2L);

	private static final TestKey _INDEX_2_KEY_3 = new TestKey(2L, 3L);

	private static final String _VALUE = "VALUE";

	private static final IndexEncoder<Long, TestKey> _indexEncoder =
		new TestKeyIndexEncoder();

	private CacheListener<TestKey, String> _cacheListener;
	private MappedMethodCallableInvocationHandler
		_mappedMethodCallableInvocationHandler;
	private PortalCache<TestKey, String> _portalCache;
	private PortalCacheIndexer<Long, TestKey, String> _portalCacheIndexer;

}