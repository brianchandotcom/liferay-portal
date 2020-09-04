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

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerListener;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.servlet.filters.threadlocal.ThreadLocalFilterThreadLocal;

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

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@Component(
	immediate = true,
	service = {
		CacheRegistryItem.class, FinderCache.class, FinderCacheImpl.class
	}
)
public class FinderCacheImpl
	implements CacheRegistryItem, FinderCache, PortalCacheManagerListener {

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

		portalCache.removeAll();
	}

	public void clearCacheByEntityCache(Class<?> clazz) {
		String cacheName = clazz.getName();

		clearCache(cacheName);
		clearCache(_getCacheNameWithPagination(cacheName));
		clearCache(_getCacheNameWithoutPagination(cacheName));
	}

	@Override
	public void clearLocalCache() {
		if (_isLocalCacheEnabled()) {
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

		if (!_valueObjectFinderCacheEnabled || !CacheRegistryUtil.isActive()) {
			return null;
		}

		Serializable cacheKey = finderPath.encodeCacheKey(args);
		Serializable cacheValue = null;
		Map<LocalCacheKey, Serializable> localCache = null;
		LocalCacheKey localCacheKey = null;

		if (_isLocalCacheEnabled()) {
			localCache = _localCache.get();

			localCacheKey = new LocalCacheKey(
				finderPath.getCacheName(), cacheKey);

			cacheValue = localCache.get(localCacheKey);
		}

		if (cacheValue == null) {
			PortalCache<Serializable, Serializable> portalCache =
				_getPortalCache(finderPath.getCacheName());

			cacheValue = portalCache.get(cacheKey);

			if ((cacheValue != null) && (localCache != null)) {
				localCache.put(localCacheKey, cacheValue);
			}
		}

		if (cacheValue == null) {
			return null;
		}

		if (cacheValue instanceof EmptyResult) {
			EmptyResult emptyResult = (EmptyResult)cacheValue;

			if (emptyResult.matches(args)) {
				return Collections.emptyList();
			}

			return null;
		}

		if (cacheValue instanceof List<?>) {
			List<Serializable> primaryKeys = (List<Serializable>)cacheValue;

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

			return Collections.unmodifiableList(list);
		}

		if (finderPath.isBaseModelResult()) {
			return basePersistenceImpl.fetchByPrimaryKey(cacheValue);
		}

		return cacheValue;
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
		if (portalCacheName.startsWith(_GROUP_KEY_PREFIX)) {
			_portalCaches.remove(
				portalCacheName.substring(_GROUP_KEY_PREFIX.length()));
		}
	}

	@Override
	public void putResult(FinderPath finderPath, Object[] args, Object result) {
		putResult(finderPath, args, result, true);
	}

	@Override
	public void putResult(
		FinderPath finderPath, Object[] args, Object result, boolean quiet) {

		if (!_valueObjectFinderCacheEnabled || !CacheRegistryUtil.isActive() ||
			(result == null)) {

			return;
		}

		Serializable cacheValue = (Serializable)result;

		if (result instanceof BaseModel<?>) {
			BaseModel<?> model = (BaseModel<?>)result;

			cacheValue = model.getPrimaryKeyObj();
		}
		else if (result instanceof List<?>) {
			List<BaseModel<?>> baseModels = (List<BaseModel<?>>)result;

			if (baseModels.isEmpty()) {
				cacheValue = new EmptyResult(args);
			}
			else if ((baseModels.size() >
						_valueObjectFinderCacheListThreshold) &&
					 (_valueObjectFinderCacheListThreshold > 0)) {

				_removeResult(finderPath, args);

				return;
			}
			else {
				ArrayList<Serializable> primaryKeys = new ArrayList<>(
					baseModels.size());

				for (BaseModel<?> baseModel : baseModels) {
					primaryKeys.add(baseModel.getPrimaryKeyObj());
				}

				cacheValue = primaryKeys;
			}
		}

		Serializable cacheKey = finderPath.encodeCacheKey(args);

		if (_isLocalCacheEnabled()) {
			Map<LocalCacheKey, Serializable> localCache = _localCache.get();

			localCache.put(
				new LocalCacheKey(finderPath.getCacheName(), cacheKey),
				cacheValue);
		}

		PortalCache<Serializable, Serializable> portalCache = _getPortalCache(
			finderPath.getCacheName());

		if (quiet) {
			PortalCacheHelperUtil.putWithoutReplicator(
				portalCache, cacheKey, cacheValue);
		}
		else {
			portalCache.put(cacheKey, cacheValue);
		}
	}

	public void removeByEntityCache(Class<?> clazz, BaseModel<?> baseModel) {
		String cacheName = clazz.getName();

		clearCache(_getCacheNameWithPagination(cacheName));
		clearCache(_getCacheNameWithoutPagination(cacheName));

		for (FinderPath finderPath : _getFinderPaths(cacheName)) {
			removeResult(
				finderPath, _getArguments(finderPath, baseModel, false, false));
			removeResult(
				finderPath, _getArguments(finderPath, baseModel, true, true));
		}
	}

	@Override
	public void removeCache(String className) {
		_portalCaches.remove(className);

		String groupKey = _GROUP_KEY_PREFIX.concat(className);

		_multiVMPool.removePortalCache(groupKey);
	}

	public void removeCacheByEntityCache(String cacheName) {
		removeCache(cacheName);
		removeCache(_getCacheNameWithPagination(cacheName));
		removeCache(_getCacheNameWithoutPagination(cacheName));
	}

	@Override
	public void removeResult(FinderPath finderPath, Object[] args) {
		if (!_valueObjectFinderCacheEnabled || !CacheRegistryUtil.isActive()) {
			return;
		}

		_removeResult(finderPath, args);
	}

	public void updateByEntityCache(Class<?> clazz, BaseModel<?> baseModel) {
		if (!_valueObjectFinderCacheEnabled) {
			return;
		}

		String cacheName = clazz.getName();

		clearCache(_getCacheNameWithPagination(cacheName));

		for (FinderPath finderPath :
				_getFinderPaths(_getCacheNameWithoutPagination(cacheName))) {

			if (baseModel.isNew()) {
				_removeResult(
					finderPath,
					_getArguments(finderPath, baseModel, false, false));
			}
			else {
				_removeResult(
					finderPath,
					_getArguments(finderPath, baseModel, true, false));
				_removeResult(
					finderPath,
					_getArguments(finderPath, baseModel, true, true));
			}
		}

		for (FinderPath finderPath : _getFinderPaths(cacheName)) {
			_removeResult(
				finderPath, _getArguments(finderPath, baseModel, true, true));
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_valueObjectFinderCacheEnabled = GetterUtil.getBoolean(
			_props.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_ENABLED));
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			_props.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		if (_valueObjectFinderCacheListThreshold == 0) {
			_valueObjectFinderCacheEnabled = false;
		}

		int localCacheMaxSize = GetterUtil.getInteger(
			_props.get(
				PropsKeys.VALUE_OBJECT_FINDER_THREAD_LOCAL_CACHE_MAX_SIZE));

		if (localCacheMaxSize > 0) {
			_localCache = new CentralizedThreadLocal<>(
				FinderCacheImpl.class + "._localCache",
				() -> new LRUMap(localCacheMaxSize));
		}
		else {
			_localCache = null;
		}

		PortalCacheManager<? extends Serializable, ? extends Serializable>
			portalCacheManager = _multiVMPool.getPortalCacheManager();

		portalCacheManager.registerPortalCacheManagerListener(this);

		_finderPathServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, FinderPath.class, "cache.name");
		_argumentsResolverServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ArgumentsResolver.class, "model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_finderPathServiceTrackerMap.close();

		_argumentsResolverServiceTrackerMap.close();
	}

	private Object[] _getArguments(
		FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
		boolean original) {

		ArgumentsResolver argumentsResolver =
			_argumentsResolverServiceTrackerMap.getService(
				baseModel.getModelClassName());

		return argumentsResolver.getArguments(
			finderPath, baseModel, checkColumn, original);
	}

	private String _getCacheNameWithoutPagination(String cacheName) {
		return cacheName.concat(".List2");
	}

	private String _getCacheNameWithPagination(String cacheName) {
		return cacheName.concat(".List1");
	}

	private List<FinderPath> _getFinderPaths(String cacheName) {
		List<FinderPath> finderPaths = _finderPathServiceTrackerMap.getService(
			cacheName);

		if (finderPaths == null) {
			return Collections.emptyList();
		}

		return finderPaths;
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
				_multiVMPool.getPortalCache(groupKey);

		PortalCache<Serializable, Serializable> previousPortalCache =
			_portalCaches.putIfAbsent(className, portalCache);

		if (previousPortalCache != null) {
			return previousPortalCache;
		}

		return portalCache;
	}

	private boolean _isLocalCacheEnabled() {
		if (_localCache == null) {
			return false;
		}

		return ThreadLocalFilterThreadLocal.isFilterInvoked();
	}

	private void _removeResult(FinderPath finderPath, Object[] args) {
		if (args == null) {
			return;
		}

		Serializable cacheKey = finderPath.encodeCacheKey(args);

		if (_isLocalCacheEnabled()) {
			Map<LocalCacheKey, Serializable> localCache = _localCache.get();

			localCache.remove(
				new LocalCacheKey(finderPath.getCacheName(), cacheKey));
		}

		PortalCache<Serializable, Serializable> portalCache = _getPortalCache(
			finderPath.getCacheName());

		portalCache.remove(cacheKey);
	}

	private static final String _GROUP_KEY_PREFIX =
		FinderCache.class.getName() + StringPool.PERIOD;

	private ServiceTrackerMap<String, ArgumentsResolver>
		_argumentsResolverServiceTrackerMap;
	private ServiceTrackerMap<String, List<FinderPath>>
		_finderPathServiceTrackerMap;
	private ThreadLocal<LRUMap> _localCache;

	@Reference
	private MultiVMPool _multiVMPool;

	private final ConcurrentMap<String, PortalCache<Serializable, Serializable>>
		_portalCaches = new ConcurrentHashMap<>();

	@Reference
	private Props _props;

	private boolean _valueObjectFinderCacheEnabled;
	private int _valueObjectFinderCacheListThreshold;

	private static class LocalCacheKey {

		@Override
		public boolean equals(Object object) {
			LocalCacheKey localCacheKey = (LocalCacheKey)object;

			if (_className.equals(localCacheKey._className) &&
				_cacheKey.equals(localCacheKey._cacheKey)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return HashUtil.hash(_className.hashCode(), _cacheKey.hashCode());
		}

		private LocalCacheKey(String className, Serializable cacheKey) {
			_className = className;
			_cacheKey = cacheKey;
		}

		private final Serializable _cacheKey;
		private final String _className;

	}

}