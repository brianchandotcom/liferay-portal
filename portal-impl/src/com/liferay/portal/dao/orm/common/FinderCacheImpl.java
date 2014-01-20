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

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.dao.shard.advice.ShardAdvice;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.collections.map.LRUMap;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@DoPrivileged
public class FinderCacheImpl
	implements BeanFactoryAware, CacheRegistryItem, FinderCache {

	public static final String CACHE_NAME = FinderCache.class.getName();

	public void afterPropertiesSet() {
		CacheRegistryUtil.register(this);
	}

	@Override
	public void clearCache() {
		clearLocalCache();

		for (PortalCache<?, ?> portalCache : _portalCaches.values()) {
			portalCache.removeAll();
		}
	}

	@Override
	public void clearCache(String className) {
		clearLocalCache();

		PortalCache<?, ?> portalCache = _getPortalCache(className, true);

		if (portalCache != null) {
			portalCache.removeAll();
		}
	}

	@Override
	public void clearLocalCache() {
		if (_localCacheAvailable) {
			_localCache.remove();
		}
	}

	@Override
	public String getRegistryName() {
		return CACHE_NAME;
	}

	@Override
	public Object getResult(
		FinderPath finderPath, Object[] args, SessionFactory sessionFactory) {

		if (!PropsValues.VALUE_OBJECT_FINDER_CACHE_ENABLED ||
			!finderPath.isFinderCacheEnabled() ||
			!CacheRegistryUtil.isActive()) {

			return null;
		}

		Serializable primaryKey = null;

		Map<Serializable, Serializable> localCache = null;

		Serializable localCacheKey = null;

		if (_localCacheAvailable) {
			localCache = _localCache.get();

			localCacheKey = finderPath.encodeLocalCacheKey(_shardEnabled, args);

			primaryKey = localCache.get(localCacheKey);
		}

		if (primaryKey == null) {
			PortalCache<Serializable, Serializable> portalCache =
				_getPortalCache(finderPath.getCacheName(), true);

			Serializable cacheKey = finderPath.encodeCacheKey(
				_shardEnabled, args);

			primaryKey = portalCache.get(cacheKey);

			if (primaryKey != null) {
				if (_localCacheAvailable) {
					localCache.put(localCacheKey, primaryKey);
				}
			}
		}

		if (primaryKey != null) {
			return _primaryKeyToResult(finderPath, sessionFactory, primaryKey);
		}

		return null;
	}

	@Override
	public void invalidate() {
		clearCache();
	}

	@Override
	public void putResult(FinderPath finderPath, Object[] args, Object result) {
		if (!PropsValues.VALUE_OBJECT_FINDER_CACHE_ENABLED ||
			!finderPath.isFinderCacheEnabled() ||
			!CacheRegistryUtil.isActive() ||
			(result == null)) {

			return;
		}

		Serializable primaryKey = _resultToPrimaryKey((Serializable)result);

		if (_localCacheAvailable) {
			Map<Serializable, Serializable> localCache = _localCache.get();

			Serializable localCacheKey = finderPath.encodeLocalCacheKey(
				_shardEnabled, args);

			localCache.put(localCacheKey, primaryKey);
		}

		PortalCache<Serializable, Serializable> portalCache = _getPortalCache(
			finderPath.getCacheName(), true);

		Serializable cacheKey = finderPath.encodeCacheKey(_shardEnabled, args);

		portalCache.put(cacheKey, primaryKey);
	}

	@Override
	public void removeCache(String className) {
		_portalCaches.remove(className);

		String groupKey = _GROUP_KEY_PREFIX.concat(className);

		_multiVMPool.removeCache(groupKey);
	}

	@Override
	public void removeResult(FinderPath finderPath, Object[] args) {
		if (!PropsValues.VALUE_OBJECT_FINDER_CACHE_ENABLED ||
			!finderPath.isFinderCacheEnabled() ||
			!CacheRegistryUtil.isActive()) {

			return;
		}

		if (_localCacheAvailable) {
			Map<Serializable, Serializable> localCache = _localCache.get();

			Serializable localCacheKey = finderPath.encodeLocalCacheKey(
				_shardEnabled, args);

			localCache.remove(localCacheKey);
		}

		PortalCache<Serializable, Serializable> portalCache = _getPortalCache(
			finderPath.getCacheName(), true);

		Serializable cacheKey = finderPath.encodeCacheKey(_shardEnabled, args);

		portalCache.remove(cacheKey);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		if (beanFactory.containsBean(ShardAdvice.class.getName())) {
			_shardEnabled = true;
		}
	}

	public void setMultiVMPool(MultiVMPool multiVMPool) {
		_multiVMPool = multiVMPool;
	}

	private PortalCache<Serializable, Serializable> _getPortalCache(
		String className, boolean createIfAbsent) {

		PortalCache<Serializable, Serializable> portalCache = _portalCaches.get(
			className);

		if ((portalCache == null) && createIfAbsent) {
			String groupKey = _GROUP_KEY_PREFIX.concat(className);

			portalCache =
				(PortalCache<Serializable, Serializable>)_multiVMPool.getCache(
					groupKey, PropsValues.VALUE_OBJECT_FINDER_BLOCKING_CACHE);

			PortalCache<Serializable, Serializable> previousPortalCache =
				_portalCaches.putIfAbsent(className, portalCache);

			if (previousPortalCache != null) {
				portalCache = previousPortalCache;
			}
		}

		return portalCache;
	}

	private Serializable _primaryKeyToResult(
		FinderPath finderPath, SessionFactory sessionFactory,
		Serializable primaryKey) {

		if (primaryKey instanceof List<?>) {
			List<Serializable> cachedList = (List<Serializable>)primaryKey;

			if (cachedList.isEmpty()) {
				return (Serializable)Collections.emptyList();
			}

			List<Serializable> list = new ArrayList<Serializable>(
				cachedList.size());

			for (Serializable curPrimaryKey : cachedList) {
				Serializable result = _primaryKeyToResult(
					finderPath, sessionFactory, curPrimaryKey);

				if (result == null) {
					return null;
				}

				list.add(result);
			}

			list = new UnmodifiableList<Serializable>(list);

			return (Serializable)list;
		}
		else if (BaseModel.class.isAssignableFrom(
					finderPath.getResultClass())) {

			return EntityCacheUtil.loadResult(
				finderPath.isEntityCacheEnabled(), finderPath.getResultClass(),
				primaryKey, sessionFactory);
		}

		return primaryKey;
	}

	private Serializable _resultToPrimaryKey(Serializable result) {
		if (result instanceof BaseModel<?>) {
			BaseModel<?> model = (BaseModel<?>)result;

			return model.getPrimaryKeyObj();
		}
		else if (result instanceof List<?>) {
			List<Serializable> list = (List<Serializable>)result;

			if (list.isEmpty()) {
				return (Serializable)Collections.emptyList();
			}

			ArrayList<Serializable> cachedList = new ArrayList<Serializable>(
				list.size());

			for (Serializable curResult : list) {
				Serializable primaryKey = _resultToPrimaryKey(curResult);

				cachedList.add(primaryKey);
			}

			return cachedList;
		}

		return result;
	}

	private static final String _GROUP_KEY_PREFIX = CACHE_NAME.concat(
		StringPool.PERIOD);

	private static ThreadLocal<LRUMap> _localCache;
	private static boolean _localCacheAvailable;

	static {
		if (PropsValues.VALUE_OBJECT_FINDER_THREAD_LOCAL_CACHE_MAX_SIZE > 0) {
			_localCache = new AutoResetThreadLocal<LRUMap>(
				FinderCacheImpl.class + "._localCache",
				new LRUMap(
					PropsValues.
						VALUE_OBJECT_FINDER_THREAD_LOCAL_CACHE_MAX_SIZE));
			_localCacheAvailable = true;
		}
	}

	private MultiVMPool _multiVMPool;
	private ConcurrentMap<String, PortalCache<Serializable, Serializable>>
		_portalCaches =
			new ConcurrentHashMap
				<String, PortalCache<Serializable, Serializable>>();
	private boolean _shardEnabled;

}