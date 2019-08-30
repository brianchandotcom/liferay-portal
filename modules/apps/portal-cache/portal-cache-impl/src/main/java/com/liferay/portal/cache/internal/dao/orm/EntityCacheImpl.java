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

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.servlet.filters.threadlocal.ThreadLocalFilterThreadLocal;

import java.io.Serializable;

import java.util.Map;
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
	immediate = true, service = {CacheRegistryItem.class, EntityCache.class}
)
public class EntityCacheImpl
	implements CacheRegistryItem, EntityCache, PortalCacheManagerListener {

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

		PortalCache<?, ?> portalCache = getPortalCache(clazz);

		portalCache.removeAll();
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
	public PortalCache<Serializable, Serializable> getPortalCache(
		Class<?> clazz) {

		String className = clazz.getName();

		PortalCache<Serializable, Serializable> portalCache = _portalCaches.get(
			className);

		if (portalCache != null) {
			return portalCache;
		}

		String groupKey = _GROUP_KEY_PREFIX.concat(className);

		boolean mvcc = false;

		if (_valueObjectMVCCEntityCacheEnabled &&
			MVCCModel.class.isAssignableFrom(clazz)) {

			mvcc = true;
		}

		portalCache =
			(PortalCache<Serializable, Serializable>)
				_multiVMPool.getPortalCache(
					groupKey, _valueObjectEntityBlockingCacheEnabled, mvcc);

		PortalCache<Serializable, Serializable> previousPortalCache =
			_portalCaches.putIfAbsent(className, portalCache);

		if (previousPortalCache != null) {
			return previousPortalCache;
		}

		return portalCache;
	}

	@Override
	public String getRegistryName() {
		return EntityCache.class.getName();
	}

	@Override
	public Serializable getResult(
		boolean entityCacheEnabled, Class<?> clazz, Serializable primaryKey) {

		if (!_valueObjectEntityCacheEnabled || !entityCacheEnabled ||
			!CacheRegistryUtil.isActive()) {

			return null;
		}

		Serializable result = null;

		Map<Serializable, Serializable> localCache = null;

		Serializable localCacheKey = null;

		if (_isLocalCacheEnabled()) {
			localCache = _localCache.get();

			localCacheKey = new LocalCacheKey(clazz.getName(), primaryKey);

			result = localCache.get(localCacheKey);
		}

		if (result == null) {
			PortalCache<Serializable, Serializable> portalCache =
				getPortalCache(clazz);

			result = portalCache.get(primaryKey);

			if (result == null) {
				result = StringPool.BLANK;
			}

			if (localCache != null) {
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

		if (!_valueObjectEntityCacheEnabled || !entityCacheEnabled ||
			!CacheRegistryUtil.isActive()) {

			Session session = null;

			try {
				session = sessionFactory.openSession();

				return (Serializable)session.get(clazz, primaryKey);
			}
			finally {
				sessionFactory.closeSession(session);
			}
		}

		Serializable result = null;

		Map<Serializable, Serializable> localCache = null;

		Serializable localCacheKey = null;

		if (_isLocalCacheEnabled()) {
			localCache = _localCache.get();

			localCacheKey = new LocalCacheKey(clazz.getName(), primaryKey);

			result = localCache.get(localCacheKey);
		}

		Serializable loadResult = null;

		if (result == null) {
			PortalCache<Serializable, Serializable> portalCache =
				getPortalCache(clazz);

			result = portalCache.get(primaryKey);

			if (result == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Load ", clazz, " ", primaryKey, " from session"));
				}

				Session session = null;

				try {
					session = sessionFactory.openSession();

					loadResult = (Serializable)session.get(clazz, primaryKey);
				}
				finally {
					sessionFactory.closeSession(session);
				}

				if (loadResult == null) {
					result = StringPool.BLANK;
				}
				else {
					BaseModel<?> baseModel = (BaseModel<?>)loadResult;

					result = baseModel.toCacheModel();

					PortalCacheHelperUtil.putWithoutReplicator(
						portalCache, primaryKey, result);
				}
			}

			if (localCache != null) {
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
		if (portalCacheName.startsWith(_GROUP_KEY_PREFIX)) {
			_portalCaches.remove(
				portalCacheName.substring(_GROUP_KEY_PREFIX.length()));
		}
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

		if (!_valueObjectEntityCacheEnabled || !entityCacheEnabled ||
			!CacheRegistryUtil.isActive() || (result == null)) {

			return;
		}

		BaseModel<?> baseModel = (BaseModel<?>)result;

		result = baseModel.toCacheModel();

		if (_isLocalCacheEnabled()) {
			Map<Serializable, Serializable> localCache = _localCache.get();

			Serializable localCacheKey = new LocalCacheKey(
				clazz.getName(), primaryKey);

			localCache.put(localCacheKey, result);
		}

		PortalCache<Serializable, Serializable> portalCache = getPortalCache(
			clazz);

		if (quiet) {
			PortalCacheHelperUtil.putWithoutReplicator(
				portalCache, primaryKey, result);
		}
		else {
			portalCache.put(primaryKey, result);
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

		if (!_valueObjectEntityCacheEnabled || !entityCacheEnabled ||
			!CacheRegistryUtil.isActive()) {

			return;
		}

		if (_isLocalCacheEnabled()) {
			Map<Serializable, Serializable> localCache = _localCache.get();

			Serializable localCacheKey = new LocalCacheKey(
				clazz.getName(), primaryKey);

			localCache.remove(localCacheKey);
		}

		PortalCache<Serializable, Serializable> portalCache = getPortalCache(
			clazz);

		portalCache.remove(primaryKey);
	}

	@Activate
	@Modified
	protected void activate() {
		_valueObjectEntityBlockingCacheEnabled = GetterUtil.getBoolean(
			_props.get(PropsKeys.VALUE_OBJECT_ENTITY_BLOCKING_CACHE));
		_valueObjectEntityCacheEnabled = GetterUtil.getBoolean(
			_props.get(PropsKeys.VALUE_OBJECT_ENTITY_CACHE_ENABLED));
		_valueObjectMVCCEntityCacheEnabled = GetterUtil.getBoolean(
			_props.get(PropsKeys.VALUE_OBJECT_MVCC_ENTITY_CACHE_ENABLED));

		int localCacheMaxSize = GetterUtil.getInteger(
			_props.get(
				PropsKeys.VALUE_OBJECT_ENTITY_THREAD_LOCAL_CACHE_MAX_SIZE));

		if (localCacheMaxSize > 0) {
			_localCache = new CentralizedThreadLocal<>(
				EntityCacheImpl.class + "._localCache",
				() -> new LRUMap(localCacheMaxSize));
		}
		else {
			_localCache = null;
		}

		PortalCacheManager<? extends Serializable, ? extends Serializable>
			portalCacheManager = _multiVMPool.getPortalCacheManager();

		portalCacheManager.registerPortalCacheManagerListener(this);
	}

	@Reference(unbind = "-")
	protected void setMultiVMPool(MultiVMPool multiVMPool) {
		_multiVMPool = multiVMPool;
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		_props = props;
	}

	private boolean _isLocalCacheEnabled() {
		if (_localCache == null) {
			return false;
		}

		return ThreadLocalFilterThreadLocal.isFilterInvoked();
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

	private static final String _GROUP_KEY_PREFIX =
		EntityCache.class.getName() + StringPool.PERIOD;

	private static final Log _log = LogFactoryUtil.getLog(
		EntityCacheImpl.class);

	private ThreadLocal<LRUMap> _localCache;
	private MultiVMPool _multiVMPool;
	private final ConcurrentMap<String, PortalCache<Serializable, Serializable>>
		_portalCaches = new ConcurrentHashMap<>();
	private Props _props;
	private boolean _valueObjectEntityBlockingCacheEnabled;
	private boolean _valueObjectEntityCacheEnabled;
	private boolean _valueObjectMVCCEntityCacheEnabled;

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