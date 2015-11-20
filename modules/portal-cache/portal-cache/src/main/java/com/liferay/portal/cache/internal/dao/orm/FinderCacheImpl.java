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

import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerListener;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.collections.map.LRUMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@Component(
	immediate = true,
	service = {CacheRegistryItem.class, FinderCache.class}
)
public class FinderCacheImpl
	implements PortalCacheManagerListener, CacheRegistryItem, FinderCache {

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

		PortalCache<?, ?> portalCache = _getPortalCache(className);

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
	public void dispose() {
		_portalCaches.clear();
	}

	@Override
	public String getRegistryName() {
		return FinderCache.class.getName();
	}

	@Override
	public Object getResult(
		FinderPath finderPath, Object[] args,
		BasePersistenceImpl<? extends BaseModel<?>> basePersistenceImpl) {

		if (!_valueObjectFinderCacheEnabled ||
			!finderPath.isFinderCacheEnabled() ||
			!CacheRegistryUtil.isActive()) {

			return null;
		}

		Serializable primaryKey = null;

		Map<Serializable, Serializable> localCache = null;

		Serializable localCacheKey = null;

		if (_localCacheAvailable) {
			localCache = _localCache.get();

			localCacheKey = finderPath.encodeLocalCacheKey(args);

			primaryKey = localCache.get(localCacheKey);
		}

		if (primaryKey == null) {
			PortalCache<Serializable, Serializable> portalCache =
				_getPortalCache(finderPath.getCacheName());

			Serializable cacheKey = finderPath.encodeCacheKey(args);

			primaryKey = portalCache.get(cacheKey);

			if (primaryKey != null) {
				if (_localCacheAvailable) {
					localCache.put(localCacheKey, primaryKey);
				}
			}
		}

		if (primaryKey != null) {
			return _primaryKeyToResult(
				finderPath, basePersistenceImpl, primaryKey);
		}

		return null;
	}

	@Override
	public void init() {
	}

	@Override
	public void invalidate() {
		clearCache();
	}

	@Override
	public void notifyPortalCacheAdded(String portalCacheName) {
	}

	@Override
	public void notifyPortalCacheRemoved(String portalCacheName) {
		_portalCaches.remove(portalCacheName);
	}

	@Override
	public void putResult(FinderPath finderPath, Object[] args, Object result) {
		putResult(finderPath, args, result, true);
	}

	@Override
	public void putResult(
		FinderPath finderPath, Object[] args, Object result, boolean quiet) {

		if (!_valueObjectFinderCacheEnabled ||
			!finderPath.isFinderCacheEnabled() ||
			!CacheRegistryUtil.isActive() || (result == null)) {

			return;
		}

		Serializable primaryKey = _resultToPrimaryKey((Serializable)result);

		if (_localCacheAvailable) {
			Map<Serializable, Serializable> localCache = _localCache.get();

			Serializable localCacheKey = finderPath.encodeLocalCacheKey(args);

			localCache.put(localCacheKey, primaryKey);
		}

		PortalCache<Serializable, Serializable> portalCache = _getPortalCache(
			finderPath.getCacheName());

		Serializable cacheKey = finderPath.encodeCacheKey(args);

		if (quiet) {
			PortalCacheHelperUtil.putWithoutReplicator(
				portalCache, cacheKey, primaryKey);
		}
		else {
			portalCache.put(cacheKey, primaryKey);
		}
	}

	@Override
	public void removeCache(String className) {
		_portalCaches.remove(className);

		String groupKey = _GROUP_KEY_PREFIX.concat(className);

		_multiVMPool.removePortalCache(groupKey);
	}

	@Override
	public void removeResult(FinderPath finderPath, Object[] args) {
		if (!_valueObjectFinderCacheEnabled ||
			!finderPath.isFinderCacheEnabled() ||
			!CacheRegistryUtil.isActive()) {

			return;
		}

		if (_localCacheAvailable) {
			Map<Serializable, Serializable> localCache = _localCache.get();

			Serializable localCacheKey = finderPath.encodeLocalCacheKey(args);

			localCache.remove(localCacheKey);
		}

		PortalCache<Serializable, Serializable> portalCache = _getPortalCache(
			finderPath.getCacheName());

		Serializable cacheKey = finderPath.encodeCacheKey(args);

		portalCache.remove(cacheKey);
	}

	@Activate
	@Modified
	protected void activate() {
		_valueObjectEntityBlockingCacheEnabled = GetterUtil.getBoolean(
			_props.get(PropsKeys.VALUE_OBJECT_ENTITY_BLOCKING_CACHE));
		_valueObjectFinderCacheEnabled = GetterUtil.getBoolean(
			_props.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_ENABLED));

		int localCacheMaxSize = GetterUtil.getInteger(
			_props.get(
				PropsKeys.VALUE_OBJECT_FINDER_THREAD_LOCAL_CACHE_MAX_SIZE));

		if (localCacheMaxSize > 0) {
			_localCacheAvailable = true;

			_localCache = new AutoResetThreadLocal<>(
				FinderCacheImpl.class + "._localCache",
				new LRUMap(localCacheMaxSize));
		}
		else {
			_localCacheAvailable = false;

			_localCache = null;
		}

		PortalCacheManager
			<? extends Serializable, ? extends Serializable>
				portalCacheManager = _multiVMPool.getPortalCacheManager();

		portalCacheManager.registerPortalCacheManagerListener(
			FinderCacheImpl.this);
	}

	@Reference(unbind = "-")
	protected void setEntityCache(EntityCache entityCache) {
		_entityCache = entityCache;
	}

	@Reference(unbind = "-")
	protected void setMultiVMPool(MultiVMPool multiVMPool) {
		_multiVMPool = multiVMPool;
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_props = props;
	}

	private PortalCache<Serializable, Serializable> _getPortalCache(
		String className) {

		PortalCache<Serializable, Serializable> portalCache = _portalCaches.get(
			className);

		if (portalCache != null) {
			return portalCache;
		}

		String groupKey = _GROUP_KEY_PREFIX.concat(className);

		portalCache =
			(PortalCache<Serializable, Serializable>)
				_multiVMPool.getPortalCache(
					groupKey, _valueObjectEntityBlockingCacheEnabled);

		PortalCache<Serializable, Serializable> previousPortalCache =
			_portalCaches.putIfAbsent(className, portalCache);

		if (previousPortalCache != null) {
			return previousPortalCache;
		}

		return portalCache;
	}

	private Serializable _primaryKeyToResult(
		FinderPath finderPath,
		BasePersistenceImpl<? extends BaseModel<?>> basePersistenceImpl,
		Serializable primaryKey) {

		if (primaryKey instanceof List<?>) {
			List<Serializable> primaryKeys = (List<Serializable>)primaryKey;

			if (primaryKeys.isEmpty()) {
				return (Serializable)Collections.emptyList();
			}

			Set<Serializable> primaryKeysSet = new HashSet<>(primaryKeys);

			Map<Serializable, ? extends BaseModel<?>> map =
				basePersistenceImpl.fetchByPrimaryKeys(primaryKeysSet);

			if (map.size() < primaryKeysSet.size()) {
				return null;
			}

			List<Serializable> list = new ArrayList<>(primaryKeys.size());

			for (Serializable curPrimaryKey : primaryKeys) {
				list.add(map.get(curPrimaryKey));
			}

			return (Serializable)Collections.unmodifiableList(list);
		}
		else if (BaseModel.class.isAssignableFrom(
					finderPath.getResultClass())) {

			return _entityCache.loadResult(
				finderPath.isEntityCacheEnabled(), finderPath.getResultClass(),
				primaryKey, basePersistenceImpl);
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

			ArrayList<Serializable> cachedList = new ArrayList<>(list.size());

			for (Serializable curResult : list) {
				Serializable primaryKey = _resultToPrimaryKey(curResult);

				cachedList.add(primaryKey);
			}

			return cachedList;
		}

		return result;
	}

	private static final String _GROUP_KEY_PREFIX =
		FinderCache.class.getName() + StringPool.PERIOD;

	private volatile EntityCache _entityCache;
	private ThreadLocal<LRUMap> _localCache;
	private boolean _localCacheAvailable;
	private volatile MultiVMPool _multiVMPool;
	private final ConcurrentMap<String, PortalCache<Serializable, Serializable>>
		_portalCaches = new ConcurrentHashMap<>();
	private volatile Props _props;
	private boolean _valueObjectEntityBlockingCacheEnabled;
	private boolean _valueObjectFinderCacheEnabled;

}