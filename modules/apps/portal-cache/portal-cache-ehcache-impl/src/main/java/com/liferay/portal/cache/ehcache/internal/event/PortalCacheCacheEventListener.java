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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.cache.AggregatedPortalCacheListener;
import com.liferay.portal.cache.ehcache.internal.SerializableEhcachePortalCache;
import com.liferay.portal.cache.io.SerializableObjectWrapper;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheListener;
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

		_log = LogFactoryUtil.getLog(
			PortalCacheCacheEventListener.class.getName() + StringPool.PERIOD +
				portalCache.getPortalCacheName());
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
		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Evicted ", getKey(element), " from ", ehcache.getName()));
		}

		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		_aggregatedPortalCacheListener.notifyEntryEvicted(
			_portalCache, getKey(element), getValue(element),
			element.getTimeToLive());
	}

	@Override
	public void notifyElementExpired(Ehcache ehcache, Element element) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Expired ", getKey(element), " from ", ehcache.getName()));
		}

		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		_aggregatedPortalCacheListener.notifyEntryExpired(
			_portalCache, getKey(element), getValue(element),
			element.getTimeToLive());
	}

	@Override
	public void notifyElementPut(Ehcache ehcache, Element element)
		throws CacheException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Put ", getKey(element), " into ", ehcache.getName()));
		}

		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		_aggregatedPortalCacheListener.notifyEntryPut(
			_portalCache, getKey(element), getValue(element),
			element.getTimeToLive());
	}

	@Override
	public void notifyElementRemoved(Ehcache ehcache, Element element)
		throws CacheException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Removed ", getKey(element), " from ", ehcache.getName()));
		}

		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		_aggregatedPortalCacheListener.notifyEntryRemoved(
			_portalCache, getKey(element), getValue(element),
			element.getTimeToLive());
	}

	@Override
	public void notifyElementUpdated(Ehcache ehcache, Element element)
		throws CacheException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Updated ", getKey(element), " in ", ehcache.getName()));
		}

		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		_aggregatedPortalCacheListener.notifyEntryUpdated(
			_portalCache, getKey(element), getValue(element),
			element.getTimeToLive());
	}

	@Override
	public void notifyRemoveAll(Ehcache ehcache) {
		if (_log.isDebugEnabled()) {
			_log.debug("Cleared " + ehcache.getName());
		}

		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		_aggregatedPortalCacheListener.notifyRemoveAll(_portalCache);
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

	private final AggregatedPortalCacheListener<K, V>
		_aggregatedPortalCacheListener;
	private final Log _log;
	private final PortalCache<K, V> _portalCache;
	private final boolean _requireSerialization;

}