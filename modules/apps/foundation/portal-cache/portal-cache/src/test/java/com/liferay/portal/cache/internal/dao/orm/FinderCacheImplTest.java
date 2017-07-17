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

package com.liferay.portal.cache.internal.dao.orm;

import com.liferay.portal.cache.key.HashCodeHexStringCacheKeyGenerator;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class FinderCacheImplTest {

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		_classLoader = FinderCacheImplTest.class.getClassLoader();

		CacheKeyGeneratorUtil cacheKeyGeneratorUtil =
			new CacheKeyGeneratorUtil();

		cacheKeyGeneratorUtil.setDefaultCacheKeyGenerator(_cacheKeyGenerator);

		_finderPath = new FinderPath(
			true, true, FinderCacheImplTest.class,
			FinderCacheImplTest.class.getName(), "test",
			new String[] {String.class.getName()});

		_finderCacheImpl = new FinderCacheImpl();

		Props props = (Props)ProxyUtil.newProxyInstance(
			_classLoader, new Class<?>[] {Props.class},
			new PropsInvocationHandler());

		_finderCacheImpl.setProps(props);
	}

	@Test
	public void testPutEmptyListInvalid() {
		_assertPutEmptyListInvalid(false);
		_assertPutEmptyListInvalid(true);
	}

	@Test
	public void testPutEmptyListValid() {
		_assertPutEmptyListValid(false);
		_assertPutEmptyListValid(true);
	}

	@Test
	public void testTestKeysCollide() {
		Assert.assertEquals(
			_cacheKeyGenerator.getCacheKey(_key1),
			_cacheKeyGenerator.getCacheKey(_key2));
	}

	private void _activateFinderCache(boolean serialized) {
		MultiVMPool multiVMPool = (MultiVMPool)ProxyUtil.newProxyInstance(
			_classLoader, new Class<?>[] {MultiVMPool.class},
			new MultiVMPoolInvocationHandler(_classLoader, serialized));

		EntityCacheImpl entityCacheImpl = new EntityCacheImpl();

		entityCacheImpl.setMultiVMPool(multiVMPool);

		_finderCacheImpl.setEntityCache(entityCacheImpl);

		_finderCacheImpl.setMultiVMPool(multiVMPool);

		_finderCacheImpl.activate();
	}

	private void _assertPutEmptyListInvalid(boolean serialized) {
		_activateFinderCache(serialized);

		_finderCacheImpl.putResult(
			_finderPath, _key1, Collections.emptyList(), true);

		Object result = _finderCacheImpl.getResult(_finderPath, _key2, null);

		Assert.assertNull(result);
	}

	private void _assertPutEmptyListValid(boolean serialized) {
		_activateFinderCache(serialized);

		_finderCacheImpl.putResult(
			_finderPath, _key1, Collections.emptyList(), true);

		Object result = _finderCacheImpl.getResult(_finderPath, _key1, null);

		Assert.assertSame(Collections.emptyList(), result);
	}

	private static final CacheKeyGenerator _cacheKeyGenerator =
		new HashCodeHexStringCacheKeyGenerator();
	private static final String[] _key1 = new String[] {"home"};
	private static final String[] _key2 = new String[] {"j1me"};

	private ClassLoader _classLoader;
	private FinderCacheImpl _finderCacheImpl;
	private FinderPath _finderPath;

}