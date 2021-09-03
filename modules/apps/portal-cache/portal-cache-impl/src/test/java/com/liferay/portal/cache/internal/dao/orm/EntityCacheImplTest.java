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
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class EntityCacheImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_classLoader = EntityCacheImplTest.class.getClassLoader();
		_nullModel = ReflectionTestUtil.getFieldValue(
			BasePersistenceImpl.class, "nullModel");

		_props = PropsTestUtil.setProps(
			HashMapBuilder.<String, Object>put(
				PropsKeys.VALUE_OBJECT_ENTITY_CACHE_ENABLED, "true"
			).put(
				PropsKeys.VALUE_OBJECT_FINDER_CACHE_ENABLED, "true"
			).put(
				PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD, "-1"
			).put(
				PropsKeys.VALUE_OBJECT_MVCC_ENTITY_CACHE_ENABLED, "true"
			).build());
	}

	@Test
	public void testNotifyPortalCacheRemovedPortalCacheName() {
		EntityCacheImpl entityCacheImpl = new EntityCacheImpl();

		MultiVMPool multiVMPool = (MultiVMPool)ProxyUtil.newProxyInstance(
			_classLoader, new Class<?>[] {MultiVMPool.class},
			new MultiVMPoolInvocationHandler(_classLoader, true));

		ReflectionTestUtil.setFieldValue(
			entityCacheImpl, "_clusterExecutor",
			ProxyFactory.newDummyInstance(ClusterExecutor.class));
		ReflectionTestUtil.setFieldValue(
			entityCacheImpl, "_multiVMPool", multiVMPool);
		ReflectionTestUtil.setFieldValue(entityCacheImpl, "_props", _props);

		FinderCacheImpl finderCacheImpl = new FinderCacheImpl();

		ReflectionTestUtil.setFieldValue(
			entityCacheImpl, "_finderCacheImpl", finderCacheImpl);
		ReflectionTestUtil.setFieldValue(
			finderCacheImpl, "_multiVMPool", multiVMPool);

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

	private void _testPutAndGetNullModel(boolean serialized) {
		EntityCacheImpl entityCacheImpl = new EntityCacheImpl();

		ReflectionTestUtil.setFieldValue(
			entityCacheImpl, "_multiVMPool",
			ProxyUtil.newProxyInstance(
				_classLoader, new Class<?>[] {MultiVMPool.class},
				new MultiVMPoolInvocationHandler(_classLoader, serialized)));
		ReflectionTestUtil.setFieldValue(entityCacheImpl, "_props", _props);

		entityCacheImpl.activate();

		entityCacheImpl.putResult(EntityCacheImplTest.class, 12345, _nullModel);

		Serializable result = entityCacheImpl.getResult(
			EntityCacheImplTest.class, 12345);

		Assert.assertSame(_nullModel, result);
	}

	private ClassLoader _classLoader;
	private Serializable _nullModel;
	private Props _props;

}