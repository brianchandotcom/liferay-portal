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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.cache.key.HashCodeHexStringCacheKeyGenerator;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.key.CacheKeyGeneratorUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;

/**
 * @author Preston Crary
 */
public class FinderCacheImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_properties = HashMapBuilder.<String, Object>put(
			PropsKeys.VALUE_OBJECT_ENTITY_CACHE_ENABLED, "true"
		).put(
			PropsKeys.VALUE_OBJECT_FINDER_CACHE_ENABLED, "true"
		).put(
			PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD, "-1"
		).put(
			PropsKeys.VALUE_OBJECT_MVCC_ENTITY_CACHE_ENABLED, "true"
		).build();

		_serializedMultiVMPool = (MultiVMPool)ProxyUtil.newProxyInstance(
			_classLoader, new Class<?>[] {MultiVMPool.class},
			new MultiVMPoolInvocationHandler(_classLoader, true));
		_notSerializedMultiVMPool = (MultiVMPool)ProxyUtil.newProxyInstance(
			_classLoader, new Class<?>[] {MultiVMPool.class},
			new MultiVMPoolInvocationHandler(_classLoader, false));

		CacheKeyGeneratorUtil cacheKeyGeneratorUtil =
			new CacheKeyGeneratorUtil();

		cacheKeyGeneratorUtil.setDefaultCacheKeyGenerator(_cacheKeyGenerator);
	}

	@Before
	public void setUp() {
		_finderPath = new FinderPath(
			FinderCacheImplTest.class.getName(), "test", new String[0],
			new String[0], true);
	}

	@Test
	public void testNotifyPortalCacheRemovedPortalCacheName() {
		FinderCacheImpl finderCacheImpl = _activateFinderCache(
			(MultiVMPool)ProxyUtil.newProxyInstance(
				_classLoader, new Class<?>[] {MultiVMPool.class},
				new MultiVMPoolInvocationHandler(_classLoader, true)));

		PortalCache<Serializable, Serializable> portalCache =
			ReflectionTestUtil.invoke(
				finderCacheImpl, "_getPortalCache",
				new Class<?>[] {String.class},
				FinderCacheImplTest.class.getName());

		Map<String, PortalCache<Serializable, Serializable>> portalCaches =
			ReflectionTestUtil.getFieldValue(finderCacheImpl, "_portalCaches");

		Assert.assertEquals(portalCaches.toString(), 1, portalCaches.size());
		Assert.assertSame(
			portalCache, portalCaches.get(FinderCacheImplTest.class.getName()));

		finderCacheImpl.notifyPortalCacheRemoved(
			portalCache.getPortalCacheName());

		Assert.assertTrue(portalCaches.toString(), portalCaches.isEmpty());
	}

	@Test
	public void testPutEmptyListInvalid() {
		_assertPutEmptyListInvalid(_notSerializedMultiVMPool);
		_assertPutEmptyListInvalid(_serializedMultiVMPool);
	}

	@Test
	public void testPutEmptyListValid() {
		_assertPutEmptyListValid(_notSerializedMultiVMPool);
		_assertPutEmptyListValid(_serializedMultiVMPool);
	}

	@Test
	public void testPutNonbaseModelList() {
		FinderPath finderPath = new FinderPath(
			FinderCacheImplTest.class.getName(), "test-nonbase-model",
			new String[0], new String[0], false);

		FinderCache finderCache = _activateFinderCache(
			_notSerializedMultiVMPool);

		// Empty list

		finderCache.putResult(finderPath, _KEY1, Collections.emptyList());

		Assert.assertSame(
			Collections.emptyList(),
			finderCache.getResult(finderPath, _KEY1, null));

		// Not empty list

		List<Long> list = Collections.singletonList(1L);

		finderCache.putResult(finderPath, _KEY1, list);

		Assert.assertSame(list, finderCache.getResult(finderPath, _KEY1, null));
	}

	@Test
	public void testTestKeysCollide() {
		Assert.assertEquals(
			_cacheKeyGenerator.getCacheKey(_KEY1),
			_cacheKeyGenerator.getCacheKey(_KEY2));
	}

	@Test
	public void testThreshold() {
		_properties.put(
			PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD, "2");

		FinderCache finderCache = _activateFinderCache(
			_notSerializedMultiVMPool);

		Map<Serializable, TestBaseModel> map =
			HashMapBuilder.<Serializable, TestBaseModel>put(
				"a", new TestBaseModel("a")
			).put(
				"b", new TestBaseModel("b")
			).build();

		List<TestBaseModel> values = new ArrayList<>(map.values());

		finderCache.putResult(_finderPath, _KEY1, values);

		Assert.assertEquals(
			values,
			finderCache.getResult(
				_finderPath, _KEY1, new TestBasePersistence(map)));

		map.put("c", new TestBaseModel("c"));

		finderCache.putResult(
			_finderPath, _KEY1, new ArrayList<>(map.values()));

		Assert.assertNull(
			finderCache.getResult(
				_finderPath, _KEY1, new TestBasePersistence(map)));
	}

	private FinderCacheImpl _activateFinderCache(MultiVMPool multiVMPool) {
		FinderCacheImpl finderCacheImpl = new FinderCacheImpl();

		ReflectionTestUtil.setFieldValue(
			finderCacheImpl, "_multiVMPool", multiVMPool);
		ReflectionTestUtil.setFieldValue(
			finderCacheImpl, "_props", PropsTestUtil.setProps(_properties));

		finderCacheImpl.activate(
			(BundleContext)ProxyUtil.newProxyInstance(
				BundleContext.class.getClassLoader(),
				new Class<?>[] {BundleContext.class},
				(proxy, method, args) -> {
					if (Objects.equals("createFilter", method.getName())) {
						return ProxyFactory.newDummyInstance(Filter.class);
					}

					return null;
				}));

		return finderCacheImpl;
	}

	private void _assertPutEmptyListInvalid(MultiVMPool multiVMPool) {
		FinderCache finderCache = _activateFinderCache(multiVMPool);

		finderCache.putResult(_finderPath, _KEY1, Collections.emptyList());

		Assert.assertNull(finderCache.getResult(_finderPath, _KEY2, null));
	}

	private void _assertPutEmptyListValid(MultiVMPool multiVMPool) {
		FinderCache finderCache = _activateFinderCache(multiVMPool);

		finderCache.putResult(_finderPath, _KEY1, Collections.emptyList());

		Assert.assertSame(
			Collections.emptyList(),
			finderCache.getResult(_finderPath, _KEY1, null));
	}

	private static final String[] _KEY1 = {"home"};

	private static final String[] _KEY2 = {"j1me"};

	private static final CacheKeyGenerator _cacheKeyGenerator =
		new HashCodeHexStringCacheKeyGenerator();
	private static final ClassLoader _classLoader =
		FinderCacheImplTest.class.getClassLoader();
	private static MultiVMPool _notSerializedMultiVMPool;
	private static Map<String, Object> _properties;
	private static MultiVMPool _serializedMultiVMPool;

	private FinderPath _finderPath;

	private static class TestBaseModel extends BaseModelImpl<TestBaseModel> {

		@Override
		public Object clone() {
			throw new UnsupportedOperationException();
		}

		@Override
		public TestBaseModel cloneWithOriginalValues() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int compareTo(TestBaseModel testBaseModel) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Class<?> getModelClass() {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getModelClassName() {
			return StringPool.BLANK;
		}

		@Override
		public Serializable getPrimaryKeyObj() {
			return _primaryKey;
		}

		@Override
		public boolean isEntityCacheEnabled() {
			return true;
		}

		@Override
		public boolean isFinderCacheEnabled() {
			return true;
		}

		@Override
		public void setPrimaryKeyObj(Serializable primaryKeyObj) {
			throw new UnsupportedOperationException();
		}

		private TestBaseModel(Serializable primaryKey) {
			_primaryKey = primaryKey;
		}

		private final Serializable _primaryKey;

	}

	private static class TestBasePersistence
		extends BasePersistenceImpl<TestBaseModel> {

		@Override
		public TestBaseModel fetchByPrimaryKey(Serializable serializable) {
			return _map.get(serializable);
		}

		@Override
		public Map<Serializable, TestBaseModel> fetchByPrimaryKeys(
			Set<Serializable> primaryKeys) {

			Assert.assertNotNull(_map);
			Assert.assertEquals(_map.keySet(), primaryKeys);

			return _map;
		}

		private TestBasePersistence(Map<Serializable, TestBaseModel> map) {
			_map = map;
		}

		private final Map<Serializable, TestBaseModel> _map;

	}

}