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

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
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

		_cacheManager = ehcachePortalCacheManager.getEhcacheManager();
	}

	@Override
	public Ehcache getEhcache() {
		if (_ehcache == null) {
			synchronized (this) {
				if (_ehcache == null) {
					synchronized (_cacheManager) {
						if (!_cacheManager.cacheExists(_portalCacheName)) {
							_cacheManager.addCache(_portalCacheName);
						}
					}

					_ehcache = _cacheManager.getCache(_portalCacheName);

					RegisteredEventListeners registeredEventListeners =
						_ehcache.getCacheEventNotificationService();

					registeredEventListeners.registerListener(
						new PortalCacheCacheEventListener<>(
							aggregatedPortalCacheListener, this),
						NotificationScope.ALL);
				}
			}
		}

		return _ehcache;
	}

	@Override
	public List<K> getKeys() {
		Ehcache ehcache = getEhcache();

		List<?> rawKeys = ehcache.getKeys();

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
		Ehcache ehcache = getEhcache();

		ehcache.removeAll();
	}

	protected void dispose() {
		_cacheManager.removeCache(_portalCacheName);
	}

	@Override
	protected V doGet(K key) {
		Ehcache ehcache = getEhcache();

		if (_serializable) {
			return _getValue(ehcache.get(new SerializableObjectWrapper(key)));
		}

		return _getValue(ehcache.get(key));
	}

	@Override
	protected void doPut(K key, V value, int timeToLive) {
		Ehcache ehcache = getEhcache();

		ehcache.put(_createElement(key, value, timeToLive));
	}

	@Override
	protected V doPutIfAbsent(K key, V value, int timeToLive) {
		Ehcache ehcache = getEhcache();

		return _getValue(
			ehcache.putIfAbsent(_createElement(key, value, timeToLive)));
	}

	@Override
	protected void doRemove(K key) {
		Ehcache ehcache = getEhcache();

		if (_serializable) {
			ehcache.remove(new SerializableObjectWrapper(key));
		}
		else {
			ehcache.remove(key);
		}
	}

	@Override
	protected boolean doRemove(K key, V value) {
		Ehcache ehcache = getEhcache();

		return ehcache.removeElement(
			_createElement(key, value, DEFAULT_TIME_TO_LIVE));
	}

	@Override
	protected V doReplace(K key, V value, int timeToLive) {
		Ehcache ehcache = getEhcache();

		return _getValue(
			ehcache.replace(_createElement(key, value, timeToLive)));
	}

	@Override
	protected boolean doReplace(K key, V oldValue, V newValue, int timeToLive) {
		Ehcache ehcache = getEhcache();

		return ehcache.replace(
			_createElement(key, oldValue, DEFAULT_TIME_TO_LIVE),
			_createElement(key, newValue, timeToLive));
	}

	protected Map<PortalCacheListener<K, V>, PortalCacheListenerScope>
		getPortalCacheListeners() {

		return aggregatedPortalCacheListener.getPortalCacheListeners();
	}

	protected void resetEhcache() {
		_ehcache = null;
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

	private final CacheManager _cacheManager;
	private volatile Ehcache _ehcache;
	private final String _portalCacheName;
	private final boolean _serializable;

}