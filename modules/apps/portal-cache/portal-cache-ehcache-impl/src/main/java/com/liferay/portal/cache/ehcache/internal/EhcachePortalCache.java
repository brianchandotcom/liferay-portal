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

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.portal.cache.BasePortalCache;
import com.liferay.portal.cache.ehcache.internal.event.PortalCacheCacheEventListener;
import com.liferay.portal.cache.io.SerializableObjectWrapper;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.NotificationScope;
import net.sf.ehcache.event.RegisteredEventListeners;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 * @author Shuyang Zhou
 */
public class EhcachePortalCache<K extends Serializable, V>
	extends BasePortalCache<K, V> implements EhcacheWrapper {

	public EhcachePortalCache(
		EhcachePortalCacheManager<K, V> ehcachePortalCacheManager,
		EhcachePortalCacheConfiguration ehcachePortalCacheConfiguration) {

		super(ehcachePortalCacheManager);

		_portalCacheName = ehcachePortalCacheConfiguration.getPortalCacheName();
		_serializable =
			ehcachePortalCacheConfiguration.isRequireSerialization();

		CacheManager cacheManager =
			ehcachePortalCacheManager.getEhcacheManager();

		synchronized (cacheManager) {
			if (!cacheManager.cacheExists(_portalCacheName)) {
				cacheManager.addCache(_portalCacheName);
			}
		}

		_ehcache = cacheManager.getCache(_portalCacheName);

		RegisteredEventListeners registeredEventListeners =
			_ehcache.getCacheEventNotificationService();

		registeredEventListeners.registerListener(
			new PortalCacheCacheEventListener<>(
				aggregatedPortalCacheListener, this),
			NotificationScope.ALL);
	}

	@Override
	public Ehcache getEhcache() {
		return _ehcache;
	}

	@Override
	public List<K> getKeys() {
		List<?> rawKeys = _ehcache.getKeys();

		if (!_serializable) {
			return (List<K>)rawKeys;
		}

		if (rawKeys.isEmpty()) {
			return Collections.emptyList();
		}

		List<K> keys = new ArrayList<>(rawKeys.size());

		for (Object object : rawKeys) {
			keys.add(SerializableObjectWrapper.<K>unwrap(object));
		}

		return keys;
	}

	@Override
	public String getPortalCacheName() {
		return _portalCacheName;
	}

	public boolean isSerializable() {
		return _serializable;
	}

	@Override
	public void removeAll() {
		_ehcache.removeAll();
	}

	@Override
	protected V doGet(K key) {
		if (_serializable) {
			return _getValue(_ehcache.get(new SerializableObjectWrapper(key)));
		}

		return _getValue(_ehcache.get(key));
	}

	@Override
	protected void doPut(K key, V value, int timeToLive) {
		_ehcache.put(_createElement(key, value, timeToLive));
	}

	@Override
	protected V doPutIfAbsent(K key, V value, int timeToLive) {
		return _getValue(
			_ehcache.putIfAbsent(_createElement(key, value, timeToLive)));
	}

	@Override
	protected void doRemove(K key) {
		if (_serializable) {
			_ehcache.remove(new SerializableObjectWrapper(key));
		}
		else {
			_ehcache.remove(key);
		}
	}

	@Override
	protected boolean doRemove(K key, V value) {
		return _ehcache.removeElement(
			_createElement(key, value, DEFAULT_TIME_TO_LIVE));
	}

	@Override
	protected V doReplace(K key, V value, int timeToLive) {
		return _getValue(
			_ehcache.replace(_createElement(key, value, timeToLive)));
	}

	@Override
	protected boolean doReplace(K key, V oldValue, V newValue, int timeToLive) {
		return _ehcache.replace(
			_createElement(key, oldValue, DEFAULT_TIME_TO_LIVE),
			_createElement(key, newValue, timeToLive));
	}

	protected Map<PortalCacheListener<K, V>, PortalCacheListenerScope>
		getPortalCacheListeners() {

		return Collections.unmodifiableMap(
			aggregatedPortalCacheListener.getPortalCacheListeners());
	}

	protected void reconfigEhcache(Ehcache ehcache) {
		RegisteredEventListeners registeredEventListeners =
			ehcache.getCacheEventNotificationService();

		registeredEventListeners.registerListener(
			new PortalCacheCacheEventListener<>(
				aggregatedPortalCacheListener, this),
			NotificationScope.ALL);

		Ehcache oldEhcache = _ehcache;

		_ehcache = ehcache;

		registeredEventListeners =
			oldEhcache.getCacheEventNotificationService();

		Set<CacheEventListener> cacheEventListeners =
			registeredEventListeners.getCacheEventListeners();

		for (CacheEventListener cacheEventListener : cacheEventListeners) {
			registeredEventListeners.unregisterListener(cacheEventListener);
		}
	}

	private Element _createElement(K key, V value, int timeToLive) {
		Element element = null;

		if (_serializable) {
			Object objectValue = value;

			if (value instanceof Serializable) {
				objectValue = new SerializableObjectWrapper(
					(Serializable)value);
			}

			element = new Element(
				new SerializableObjectWrapper(key), objectValue);
		}
		else {
			element = new Element(key, value);
		}

		if (timeToLive != DEFAULT_TIME_TO_LIVE) {
			element.setTimeToLive(timeToLive);
		}

		return element;
	}

	private V _getValue(Element element) {
		if (element == null) {
			return null;
		}

		if (_serializable) {
			return SerializableObjectWrapper.unwrap(element.getObjectValue());
		}

		return (V)element.getObjectValue();
	}

	private volatile Ehcache _ehcache;
	private final String _portalCacheName;
	private final boolean _serializable;

}