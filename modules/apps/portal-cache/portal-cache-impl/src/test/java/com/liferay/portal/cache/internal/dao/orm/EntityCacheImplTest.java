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

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class EntityCacheImplTest {

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		_classLoader = EntityCacheImplTest.class.getClassLoader();
		_nullModel = ReflectionTestUtil.getFieldValue(
			BasePersistenceImpl.class, "nullModel");

		Map<String, Object> properties = new HashMap<>();

		properties.put(PropsKeys.VALUE_OBJECT_ENTITY_BLOCKING_CACHE, "true");
		properties.put(PropsKeys.VALUE_OBJECT_ENTITY_CACHE_ENABLED, "true");
		properties.put(PropsKeys.VALUE_OBJECT_FINDER_CACHE_ENABLED, "true");
		properties.put(
			PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD, "-1");
		properties.put(
			PropsKeys.VALUE_OBJECT_MVCC_ENTITY_CACHE_ENABLED, "true");

		_props = PropsTestUtil.setProps(properties);
	}

	@Test
	public void testNotifyPortalCacheRemovedPortalCacheName() {
		EntityCacheImpl entityCacheImpl = new EntityCacheImpl();

		entityCacheImpl.setMultiVMPool(
			(MultiVMPool)ProxyUtil.newProxyInstance(
				_classLoader, new Class<?>[] {MultiVMPool.class},
				new MultiVMPoolInvocationHandler(_classLoader, true)));
		entityCacheImpl.setProps(_props);

		entityCacheImpl.activate();

		PortalCache<?, ?> portalCache = entityCacheImpl.getPortalCache(
			EntityCacheImplTest.class);

		Map<String, PortalCache<Serializable, Serializable>> portalCaches =
			ReflectionTestUtil.getFieldValue(entityCacheImpl, "_portalCaches");

		Assert.assertEquals(portalCaches.toString(), 1, portalCaches.size());
		Assert.assertSame(
			portalCache, portalCaches.get(EntityCacheImplTest.class.getName()));

		entityCacheImpl.notifyPortalCacheRemoved(
			portalCache.getPortalCacheName());

		Assert.assertTrue(portalCaches.toString(), portalCaches.isEmpty());
	}

	@Test
	public void testPutAndGetNullModel() throws Exception {
		_testPutAndGetNullModel(false);
		_testPutAndGetNullModel(true);
	}

	private void _testPutAndGetNullModel(boolean serialized) throws Exception {
		EntityCacheImpl entityCacheImpl = new EntityCacheImpl();

		entityCacheImpl.setMultiVMPool(
			(MultiVMPool)ProxyUtil.newProxyInstance(
				_classLoader, new Class<?>[] {MultiVMPool.class},
				new MultiVMPoolInvocationHandler(_classLoader, serialized)));
		entityCacheImpl.setProps(_props);

		entityCacheImpl.activate();

		entityCacheImpl.putResult(
			true, EntityCacheImplTest.class, 12345, _nullModel);

		Serializable result = entityCacheImpl.getResult(
			true, EntityCacheImplTest.class, 12345);

		Assert.assertSame(_nullModel, result);
	}

	private ClassLoader _classLoader;
	private Serializable _nullModel;
	private Props _props;

}