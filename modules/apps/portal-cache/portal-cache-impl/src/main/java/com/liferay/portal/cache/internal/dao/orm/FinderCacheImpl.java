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
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.servlet.filters.threadlocal.ThreadLocalFilterThreadLocal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
	immediate = true, service = {CacheRegistryItem.class, FinderCache.class}
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

		if (!_valueObjectFinderCacheEnabled ||
			!finderPath.isFinderCacheEnabled() ||
			!CacheRegistryUtil.isActive()) {

			return null;
		}

		String encodedArguments = finderPath.encodeArguments(args);
		Map<Serializable, Serializable> localCache = null;
		Serializable localCacheKey = null;
		Serializable primaryKey = null;

		if (_isLocalCacheEnabled()) {
			localCache = _localCache.get();

			localCacheKey = finderPath.encodeLocalCacheKey(encodedArguments);

			primaryKey = localCache.get(localCacheKey);
		}

		if (primaryKey == null) {
			PortalCache<Serializable, Serializable> portalCache =
				_getPortalCache(finderPath.getCacheName());

			primaryKey = portalCache.get(
				finderPath.encodeCacheKey(encodedArguments));

			if ((primaryKey != null) && (localCache != null)) {
				localCache.put(localCacheKey, primaryKey);
			}
		}

		if (primaryKey != null) {
			return _primaryKeyToResult(
				finderPath, args, basePersistenceImpl, primaryKey);
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

		if (!_valueObjectFinderCacheEnabled ||
			!finderPath.isFinderCacheEnabled() ||
			!CacheRegistryUtil.isActive() || (result == null)) {

			return;
		}

		Serializable primaryKey = _resultToPrimaryKey(
			args, (Serializable)result);

		PortalCache<Serializable, Serializable> portalCache = _getPortalCache(
			finderPath.getCacheName());

		String encodedArguments = finderPath.encodeArguments(args);

		Serializable cacheKey = finderPath.encodeCacheKey(encodedArguments);

		if (primaryKey == null) {
			if (_isLocalCacheEnabled()) {
				Map<Serializable, Serializable> localCache = _localCache.get();

				localCache.remove(
					finderPath.encodeLocalCacheKey(encodedArguments));
			}

			if (quiet) {
				PortalCacheHelperUtil.removeWithoutReplicator(
					portalCache, cacheKey);
			}
			else {
				portalCache.remove(cacheKey);
			}
		}
		else {
			if (_isLocalCacheEnabled()) {
				Map<Serializable, Serializable> localCache = _localCache.get();

				localCache.put(
					finderPath.encodeLocalCacheKey(encodedArguments),
					primaryKey);
			}

			if (quiet) {
				PortalCacheHelperUtil.putWithoutReplicator(
					portalCache, cacheKey, primaryKey);
			}
			else {
				portalCache.put(cacheKey, primaryKey);
			}
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

		String encodedArguments = finderPath.encodeArguments(args);

		if (_isLocalCacheEnabled()) {
			Map<Serializable, Serializable> localCache = _localCache.get();

			localCache.remove(finderPath.encodeLocalCacheKey(encodedArguments));
		}

		PortalCache<Serializable, Serializable> portalCache = _getPortalCache(
			finderPath.getCacheName());

		portalCache.remove(finderPath.encodeCacheKey(encodedArguments));
	}

	@Activate
	@Modified
	protected void activate() {
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

	private Serializable _primaryKeyToResult(
		FinderPath finderPath, Object[] args,
		BasePersistenceImpl<? extends BaseModel<?>> basePersistenceImpl,
		Serializable primaryKey) {

		if (primaryKey instanceof EmptyResult) {
			EmptyResult emptyResult = (EmptyResult)primaryKey;

			if (emptyResult.matches(args)) {
				return (Serializable)Collections.emptyList();
			}

			return null;
		}

		if (primaryKey instanceof List<?>) {
			List<Serializable> primaryKeys = (List<Serializable>)primaryKey;

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

		if (BaseModel.class.isAssignableFrom(finderPath.getResultClass())) {
			Serializable result = _entityCache.loadResult(
				finderPath.isEntityCacheEnabled(), finderPath.getResultClass(),
				primaryKey, basePersistenceImpl);

			if (result == _NULL_MODEL) {
				return null;
			}

			return result;
		}

		return primaryKey;
	}

	private Serializable _resultToPrimaryKey(
		Object[] args, Serializable result) {

		if (result instanceof BaseModel<?>) {
			BaseModel<?> model = (BaseModel<?>)result;

			return model.getPrimaryKeyObj();
		}

		if (result instanceof List<?>) {
			List<Serializable> list = (List<Serializable>)result;

			if (list.isEmpty()) {
				return new EmptyResult(args);
			}

			if ((list.size() > _valueObjectFinderCacheListThreshold) &&
				(_valueObjectFinderCacheListThreshold > 0)) {

				return null;
			}

			ArrayList<Serializable> cachedList = new ArrayList<>(list.size());

			for (Serializable curResult : list) {
				Serializable primaryKey = _resultToPrimaryKey(args, curResult);

				cachedList.add(primaryKey);
			}

			return cachedList;
		}

		return result;
	}

	private static final String _GROUP_KEY_PREFIX =
		FinderCache.class.getName() + StringPool.PERIOD;

	private static final Object _NULL_MODEL;

	static {
		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
				"nullModel");

			field.setAccessible(true);

			_NULL_MODEL = field.get(null);
		}
		catch (ReflectiveOperationException roe) {
			throw new ExceptionInInitializerError(roe);
		}
	}

	private EntityCache _entityCache;
	private ThreadLocal<LRUMap> _localCache;
	private MultiVMPool _multiVMPool;
	private final ConcurrentMap<String, PortalCache<Serializable, Serializable>>
		_portalCaches = new ConcurrentHashMap<>();
	private Props _props;
	private boolean _valueObjectFinderCacheEnabled;
	private int _valueObjectFinderCacheListThreshold;

	private static class EmptyResult implements Externalizable {

		public EmptyResult() {
		}

		public boolean matches(Object[] args) {
			if (args.length != _args.length) {
				return false;
			}

			for (int i = 0; i < _args.length; i++) {
				if (!Objects.equals(args[i], _args[i])) {
					return false;
				}
			}

			return true;
		}

		@Override
		public void readExternal(ObjectInput objectInput)
			throws ClassNotFoundException, IOException {

			_args = (Object[])objectInput.readObject();
		}

		@Override
		public void writeExternal(ObjectOutput objectOutput)
			throws IOException {

			objectOutput.writeObject(_args);
		}

		private EmptyResult(Object[] args) {
			_args = args;
		}

		private Object[] _args;

	}

}