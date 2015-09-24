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

package com.liferay.portal.dao.orm.common;

import com.liferay.portal.cache.mvcc.MVCCPortalCacheFactory;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerListener;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.dependency.ServiceDependencyListener;
import com.liferay.registry.dependency.ServiceDependencyManager;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.collections.map.LRUMap;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@DoPrivileged
public class EntityCacheImpl
	implements PortalCacheManagerListener, CacheRegistryItem, EntityCache {

	public static final String CACHE_NAME = EntityCache.class.getName();

	public void afterPropertiesSet() {
		final Registry registry = RegistryUtil.getRegistry();

		_serviceRegistration = registry.registerService(
			CacheRegistryItem.class, this);

		ServiceDependencyManager serviceDependencyManager =
			new ServiceDependencyManager();

		serviceDependencyManager.addServiceDependencyListener(
			new ServiceDependencyListener() {

				@Override
				public void dependenciesFulfilled() {
					_multiVMPool = registry.getService(MultiVMPool.class);

					PortalCacheManager
						<? extends Serializable, ? extends Serializable>
							portalCacheManager =
								_multiVMPool.getPortalCacheManager();

					portalCacheManager.registerPortalCacheManagerListener(
						EntityCacheImpl.this);
				}

				@Override
				public void destroy() {
				}

			}
		);

		serviceDependencyManager.registerDependencies(MultiVMPool.class);
	}

	@Override
	public void clearCache() {
		clearLocalCache();

		for (PortalCache<?, ?> portalCache : _portalCaches.values()) {
			portalCache.removeAll();
		}
	}

	@Override
	public void clearCache(Class<?> clazz) {
		clearLocalCache();

		PortalCache<?, ?> portalCache = _getPortalCache(clazz, true);

		if (portalCache != null) {
			portalCache.removeAll();
		}
	}

	@Override
	public void clearLocalCache() {
		if (_LOCAL_CACHE_AVAILABLE) {
			_localCache.remove();
		}
	}

	public void destroy() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Override
	public void dispose() {
		_portalCaches.clear();
	}

	@Override
	public PortalCache<Serializable, Serializable> getPortalCache(
		Class<?> clazz) {

		return _getPortalCache(clazz, true);
	}

	@Override
	public String getRegistryName() {
		return CACHE_NAME;
	}

	@Override
	public Serializable getResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistryUtil.isActive()) {

			return null;
		}

		Serializable result = null;

		Map<Serializable, Serializable> localCache = null;

		Serializable localCacheKey = null;

		if (_LOCAL_CACHE_AVAILABLE) {
			localCache = _localCache.get();

			localCacheKey = _encodeLocalCacheKey(clazz, primaryKey);

			result = localCache.get(localCacheKey);
		}

		if (result == null) {
			PortalCache<Serializable, Serializable> portalCache =
				_getPortalCache(clazz, true);

			Serializable cacheKey = _encodeCacheKey(primaryKey);

			result = portalCache.get(cacheKey);

			if (result == null) {
				result = StringPool.BLANK;
			}

			if (_LOCAL_CACHE_AVAILABLE) {
				localCache.put(localCacheKey, result);
			}
		}

		return _toEntityModel(result);
	}

	@Override
	public void init() {
	}

	@Override
	public void invalidate() {
		clearCache();
	}

	@Override
	public Serializable loadResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey,
		SessionFactory sessionFactory) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistryUtil.isActive()) {

			Session session = null;

			try {
				session = sessionFactory.openSession();

				return (Serializable)session.load(clazz, primaryKey);
			}
			finally {
				sessionFactory.closeSession(session);
			}
		}

		Serializable result = null;

		Map<Serializable, Serializable> localCache = null;

		Serializable localCacheKey = null;

		if (_LOCAL_CACHE_AVAILABLE) {
			localCache = _localCache.get();

			localCacheKey = _encodeLocalCacheKey(clazz, primaryKey);

			result = localCache.get(localCacheKey);
		}

		Serializable loadResult = null;

		if (result == null) {
			PortalCache<Serializable, Serializable> portalCache =
				_getPortalCache(clazz, true);

			Serializable cacheKey = _encodeCacheKey(primaryKey);

			result = portalCache.get(cacheKey);

			if (result == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Load " + clazz + " " + primaryKey + " from session");
				}

				Session session = null;

				try {
					session = sessionFactory.openSession();

					loadResult = (Serializable)session.load(clazz, primaryKey);
				}
				finally {
					if (loadResult == null) {
						result = StringPool.BLANK;
					}
					else {
						result = ((BaseModel<?>)loadResult).toCacheModel();
					}

					PortalCacheHelperUtil.putWithoutReplicator(
						portalCache, cacheKey, result);

					sessionFactory.closeSession(session);
				}
			}

			if (_LOCAL_CACHE_AVAILABLE) {
				localCache.put(localCacheKey, result);
			}
		}

		if (loadResult != null) {
			return loadResult;
		}

		return _toEntityModel(result);
	}

	@Override
	public void notifyPortalCacheAdded(String portalCacheName) {
	}

	@Override
	public void notifyPortalCacheRemoved(String portalCacheName) {
		_portalCaches.remove(portalCacheName);
	}

	@Override
	public void putResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey,
		Serializable result) {

		putResult(entityCacheEnabled, clazz, primaryKey, result, true);
	}

	@Override
	public void putResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey,
		Serializable result, boolean quiet) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistryUtil.isActive() ||
			(result == null)) {

			return;
		}

		result = ((BaseModel<?>)result).toCacheModel();

		if (_LOCAL_CACHE_AVAILABLE) {
			Map<Serializable, Serializable> localCache = _localCache.get();

			Serializable localCacheKey = _encodeLocalCacheKey(
				clazz, primaryKey);

			localCache.put(localCacheKey, result);
		}

		PortalCache<Serializable, Serializable> portalCache = _getPortalCache(
			clazz, true);

		Serializable cacheKey = _encodeCacheKey(primaryKey);

		if (quiet) {
			PortalCacheHelperUtil.putWithoutReplicator(
				portalCache, cacheKey, result);
		}
		else {
			portalCache.put(cacheKey, result);
		}
	}

	@Override
	public void removeCache(String className) {
		_portalCaches.remove(className);

		String groupKey = _GROUP_KEY_PREFIX.concat(className);

		_multiVMPool.removePortalCache(groupKey);
	}

	@Override
	public void removeResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey) {

		if (!PropsValues.VALUE_OBJECT_ENTITY_CACHE_ENABLED ||
			!entityCacheEnabled || !CacheRegistryUtil.isActive()) {

			return;
		}

		if (_LOCAL_CACHE_AVAILABLE) {
			Map<Serializable, Serializable> localCache = _localCache.get();

			Serializable localCacheKey = _encodeLocalCacheKey(
				clazz, primaryKey);

			localCache.remove(localCacheKey);
		}

		PortalCache<Serializable, Serializable> portalCache = _getPortalCache(
			clazz, true);

		Serializable cacheKey = _encodeCacheKey(primaryKey);

		portalCache.remove(cacheKey);
	}

	private Serializable _encodeCacheKey(Serializable primaryKey) {
		return primaryKey;
	}

	private Serializable _encodeLocalCacheKey(
		Class<?> clazz, Serializable primaryKey) {

		return new LocalCacheKey(clazz.getName(), primaryKey);
	}

	private PortalCache<Serializable, Serializable> _getPortalCache(
		Class<?> clazz, boolean createIfAbsent) {

		String className = clazz.getName();

		PortalCache<Serializable, Serializable> portalCache = _portalCaches.get(
			className);

		if ((portalCache == null) && createIfAbsent) {
			String groupKey = _GROUP_KEY_PREFIX.concat(className);

			portalCache =
				(PortalCache<Serializable, Serializable>)
					_multiVMPool.getPortalCache(
						groupKey,
						PropsValues.VALUE_OBJECT_ENTITY_BLOCKING_CACHE);

			if (PropsValues.VALUE_OBJECT_MVCC_ENTITY_CACHE_ENABLED &&
				MVCCModel.class.isAssignableFrom(clazz)) {

				portalCache =
					(PortalCache<Serializable, Serializable>)
						MVCCPortalCacheFactory.createMVCCEhcachePortalCache(
							portalCache);
			}

			PortalCache<Serializable, Serializable> previousPortalCache =
				_portalCaches.putIfAbsent(className, portalCache);

			if (previousPortalCache != null) {
				portalCache = previousPortalCache;
			}
		}

		return portalCache;
	}

	private Serializable _toEntityModel(Serializable result) {
		if (result == StringPool.BLANK) {
			return null;
		}

		CacheModel<?> cacheModel = (CacheModel<?>)result;

		BaseModel<?> entityModel = (BaseModel<?>)cacheModel.toEntityModel();

		entityModel.setCachedModel(true);

		return entityModel;
	}

	private static final String _GROUP_KEY_PREFIX = CACHE_NAME.concat(
		StringPool.PERIOD);

	private static final boolean _LOCAL_CACHE_AVAILABLE;

	private static final Log _log = LogFactoryUtil.getLog(
		EntityCacheImpl.class);

	private static final ThreadLocal<LRUMap> _localCache;

	static {
		if (PropsValues.VALUE_OBJECT_ENTITY_THREAD_LOCAL_CACHE_MAX_SIZE > 0) {
			_LOCAL_CACHE_AVAILABLE = true;

			_localCache = new AutoResetThreadLocal<LRUMap>(
				EntityCacheImpl.class + "._localCache",
				new LRUMap(
					PropsValues.
						VALUE_OBJECT_ENTITY_THREAD_LOCAL_CACHE_MAX_SIZE));
		}
		else {
			_LOCAL_CACHE_AVAILABLE = false;

			_localCache = null;
		}
	}

	private MultiVMPool _multiVMPool;
	private final ConcurrentMap<String, PortalCache<Serializable, Serializable>>
		_portalCaches = new ConcurrentHashMap<>();
	private ServiceRegistration<CacheRegistryItem> _serviceRegistration;

	private static class LocalCacheKey implements Serializable {

		public LocalCacheKey(String className, Serializable primaryKey) {
			_className = className;
			_primaryKey = primaryKey;
		}

		@Override
		public boolean equals(Object obj) {
			LocalCacheKey localCacheKey = (LocalCacheKey)obj;

			if (localCacheKey._className.equals(_className) &&
				localCacheKey._primaryKey.equals(_primaryKey)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return _className.hashCode() * 11 + _primaryKey.hashCode();
		}

		private static final long serialVersionUID = 1L;

		private final String _className;
		private final Serializable _primaryKey;

	}

}