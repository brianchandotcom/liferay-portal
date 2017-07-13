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

package com.liferay.portal.cache.ehcache.internal.event;

import com.liferay.portal.cache.AggregatedPortalCacheListener;
import com.liferay.portal.cache.ehcache.internal.SerializableEhcachePortalCache;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.io.SerializableObjectWrapper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

/**
 * @author Edward C. Han
 * @author Shuyang Zhou
 */
public class PortalCacheCacheEventListener<K extends Serializable, V>
	implements CacheEventListener {

	public PortalCacheCacheEventListener(
		AggregatedPortalCacheListener<K, V> aggregatedPortalCacheListener,
		PortalCache<K, V> portalCache) {

		_aggregatedPortalCacheListener = aggregatedPortalCacheListener;
		_portalCache = portalCache;

		boolean requireSerialization = false;

		if (_portalCache instanceof SerializableEhcachePortalCache) {
			requireSerialization = true;
		}

		_requireSerialization = requireSerialization;
	}

	@Override
	public Object clone() {
		return new PortalCacheCacheEventListener<>(
			_aggregatedPortalCacheListener, _portalCache);
	}

	@Override
	public void dispose() {
		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		_aggregatedPortalCacheListener.dispose();
	}

	public PortalCacheListener<K, V> getCacheListener() {
		return _aggregatedPortalCacheListener;
	}

	public PortalCache<K, V> getPortalCache() {
		return _portalCache;
	}

	@Override
	public void notifyElementEvicted(Ehcache ehcache, Element element) {
		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		K key = getKey(element);
		V value = getValue(element);
		int timeToLive = element.getTimeToLive();

		_aggregatedPortalCacheListener.notifyEntryEvicted(
			_portalCache, key, value, timeToLive);

		if (_log.isDebugEnabled()) {
			_log.debug("Evicted " + key + " from " + ehcache.getName());
		}
	}

	@Override
	public void notifyElementExpired(Ehcache ehcache, Element element) {
		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		K key = getKey(element);
		V value = getValue(element);
		int timeToLive = element.getTimeToLive();

		_aggregatedPortalCacheListener.notifyEntryExpired(
			_portalCache, key, value, timeToLive);

		if (_log.isDebugEnabled()) {
			_log.debug("Expired " + key + " from " + ehcache.getName());
		}
	}

	@Override
	public void notifyElementPut(Ehcache ehcache, Element element)
		throws CacheException {

		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		K key = getKey(element);
		V value = getValue(element);
		int timeToLive = element.getTimeToLive();

		_aggregatedPortalCacheListener.notifyEntryPut(
			_portalCache, key, value, timeToLive);

		if (_log.isDebugEnabled()) {
			_log.debug("Inserted " + key + " into " + ehcache.getName());
		}
	}

	@Override
	public void notifyElementRemoved(Ehcache ehcache, Element element)
		throws CacheException {

		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		K key = getKey(element);
		V value = getValue(element);
		int timeToLive = element.getTimeToLive();

		_aggregatedPortalCacheListener.notifyEntryRemoved(
			_portalCache, key, value, timeToLive);

		if (_log.isDebugEnabled()) {
			_log.debug("Removed " + key + " from " + ehcache.getName());
		}
	}

	@Override
	public void notifyElementUpdated(Ehcache ehcache, Element element)
		throws CacheException {

		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		K key = getKey(element);
		V value = getValue(element);
		int timeToLive = element.getTimeToLive();

		_aggregatedPortalCacheListener.notifyEntryUpdated(
			_portalCache, key, value, timeToLive);

		if (_log.isDebugEnabled()) {
			_log.debug("Updated " + key + " in " + ehcache.getName());
		}
	}

	@Override
	public void notifyRemoveAll(Ehcache ehcache) {
		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		_aggregatedPortalCacheListener.notifyRemoveAll(_portalCache);

		if (_log.isDebugEnabled()) {
			_log.debug("Cleared " + ehcache.getName());
		}
	}

	protected K getKey(Element element) {
		if (_requireSerialization) {
			return SerializableObjectWrapper.unwrap(element.getObjectKey());
		}

		return (K)element.getObjectKey();
	}

	protected V getValue(Element element) {
		if (_requireSerialization) {
			return SerializableObjectWrapper.unwrap(element.getObjectValue());
		}

		return (V)element.getObjectValue();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalCacheCacheEventListener.class);

	private final AggregatedPortalCacheListener<K, V>
		_aggregatedPortalCacheListener;
	private final PortalCache<K, V> _portalCache;
	private final boolean _requireSerialization;

}