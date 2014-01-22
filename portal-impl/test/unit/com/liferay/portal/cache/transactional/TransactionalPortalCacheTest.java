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
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJMockingNewClassLoaderJUnitTestRunner;

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

		_recordCacheListener = new RecordCacheListener();

		_portalCache.registerCacheListener(_recordCacheListener);
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

		_recordCacheListener.assertPut(_KEY_2, _VALUE_2);

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_transactionalPortalCache.put(_KEY_1, _VALUE_2, 10);

		_recordCacheListener.assertUpdated(_KEY_1, _VALUE_2);

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

		_recordCacheListener.assertRemoved(_KEY_1, _VALUE_2);

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_2, _portalCache.get(_KEY_2));

		_transactionalPortalCache.removeAll();

		_recordCacheListener.assertRemoveAll();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_transactionalPortalCache.putQuiet(_KEY_1, _VALUE_1);

		_recordCacheListener.assertNothing();

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_transactionalPortalCache.putQuiet(_KEY_1, _VALUE_2, 10);

		_recordCacheListener.assertNothing();

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

		_transactionalPortalCache.putQuiet(_KEY_1, _VALUE_1);

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_transactionalPortalCache.putQuiet(_KEY_1, null);

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_transactionalPortalCache.putQuiet(_KEY_1, _VALUE_1, 10);

		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_transactionalPortalCache.putQuiet(_KEY_1, null, 10);

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		_recordCacheListener.assertNothing();

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

		_recordCacheListener.assertNothing();

		TransactionalPortalCacheHelper.commit();

		_recordCacheListener.assertRemoveAll();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertNull(_transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertNull(_portalCache.get(_KEY_2));

		// Commit 2

		_portalCache.put(_KEY_1, _VALUE_1);

		_recordCacheListener.assertPut(_KEY_1, _VALUE_1);

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

		_recordCacheListener.assertNothing();

		TransactionalPortalCacheHelper.commit();

		Assert.assertTrue(_recordCacheListener._put);
		Assert.assertFalse(_recordCacheListener._removeAll);
		Assert.assertTrue(_recordCacheListener._removed);
		Assert.assertFalse(_recordCacheListener._updated);

		if (_KEY_1.equals(_recordCacheListener._key)) {
			Assert.assertEquals(_VALUE_1, _recordCacheListener._value);
		}
		else {
			Assert.assertEquals(_KEY_2, _recordCacheListener._key);
			Assert.assertEquals(_VALUE_2, _recordCacheListener._value);
		}

		_recordCacheListener.reset();

		Assert.assertNull(_transactionalPortalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _transactionalPortalCache.get(_KEY_2));
		Assert.assertNull(_portalCache.get(_KEY_1));
		Assert.assertEquals(_VALUE_1, _portalCache.get(_KEY_2));

		// Commit 3

		_portalCache.removeAll();

		_recordCacheListener.reset();

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

		_recordCacheListener.assertNothing();

		TransactionalPortalCacheHelper.commit();

		_recordCacheListener.assertNothing();
	}

	private static final String _CACHE_NAME = "CACHE_NAME";

	private static final String _KEY_1 = "KEY_1";

	private static final String _KEY_2 = "KEY_2";

	private static final String _VALUE_1 = "VALUE_1";

	private static final String _VALUE_2 = "VALUE_2";

	private PortalCache<String, String> _portalCache;
	private RecordCacheListener _recordCacheListener;
	private TransactionalPortalCache<String, String> _transactionalPortalCache;

	private static class RecordCacheListener
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

			_put = true;
			_key = key;
			_value = value;
		}

		@Override
		public void notifyEntryRemoved(
			PortalCache<String, String> portalCache, String key, String value) {

			_removed = true;
			_key = key;
			_value = value;
		}

		@Override
		public void notifyEntryUpdated(
			PortalCache<String, String> portalCache, String key, String value) {

			_updated = true;
			_key = key;
			_value = value;
		}

		@Override
		public void notifyRemoveAll(PortalCache<String, String> portalCache) {
			_removeAll = true;
		}

		public void assertNothing() {
			Assert.assertFalse(_put);
			Assert.assertFalse(_removeAll);
			Assert.assertFalse(_removed);
			Assert.assertFalse(_updated);
			Assert.assertNull(_key);
			Assert.assertNull(_value);
		}

		public void assertPut(String key, String value) {
			Assert.assertTrue(_put);
			Assert.assertFalse(_removeAll);
			Assert.assertFalse(_removed);
			Assert.assertFalse(_updated);
			Assert.assertEquals(_key, key);
			Assert.assertEquals(_value, value);

			reset();
		}

		public void assertRemoved(String key, String value) {
			Assert.assertFalse(_put);
			Assert.assertFalse(_removeAll);
			Assert.assertTrue(_removed);
			Assert.assertFalse(_updated);
			Assert.assertEquals(_key, key);
			Assert.assertEquals(_value, value);

			reset();
		}

		public void assertUpdated(String key, String value) {
			Assert.assertFalse(_put);
			Assert.assertFalse(_removeAll);
			Assert.assertFalse(_removed);
			Assert.assertTrue(_updated);
			Assert.assertEquals(_key, key);
			Assert.assertEquals(_value, value);

			reset();
		}

		public void assertRemoveAll() {
			Assert.assertFalse(_put);
			Assert.assertTrue(_removeAll);
			Assert.assertFalse(_removed);
			Assert.assertFalse(_updated);
			Assert.assertNull(_key);
			Assert.assertNull(_value);

			reset();
		}

		public void reset() {
			_put = false;
			_removeAll = false;
			_removed = false;
			_updated = false;
			_key = null;
			_value = null;
		}

		private boolean _put;
		private boolean _removeAll;
		private boolean _removed;
		private boolean _updated;
		private String _key;
		private String _value;

	}

}