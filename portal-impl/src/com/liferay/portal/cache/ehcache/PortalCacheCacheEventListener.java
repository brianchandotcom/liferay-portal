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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.PortalCache;
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
		CacheListener<K, V> cacheListener, PortalCache<K, V> portalCache) {

		_cacheListener = cacheListener;
		_portalCache = portalCache;
	}

	@Override
	public Object clone() {
		return new PortalCacheCacheEventListener<K, V>(
			_cacheListener, _portalCache);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void notifyElementEvicted(Ehcache ehcache, Element element) {
		K key = (K)element.getKey();
		V value = (V)element.getObjectValue();

		_cacheListener.notifyEntryEvicted(_portalCache, key, value);

		if (_log.isDebugEnabled()) {
			_log.debug("Evicted " + key + " from " + ehcache.getName());
		}
	}

	@Override
	public void notifyElementExpired(Ehcache ehcache, Element element) {
		K key = (K)element.getKey();
		V value = (V)element.getObjectValue();

		_cacheListener.notifyEntryExpired(_portalCache, key, value);

		if (_log.isDebugEnabled()) {
			_log.debug("Expired " + key + " from " + ehcache.getName());
		}
	}

	@Override
	public void notifyElementPut(Ehcache ehcache, Element element)
		throws CacheException {

		K key = (K)element.getKey();
		V value = (V)element.getObjectValue();

		_cacheListener.notifyEntryPut(_portalCache, key, value);

		if (_log.isDebugEnabled()) {
			_log.debug("Inserted " + key + " into " + ehcache.getName());
		}
	}

	@Override
	public void notifyElementRemoved(Ehcache ehcache, Element element)
		throws CacheException {

		K key = (K)element.getKey();
		V value = (V)element.getObjectValue();

		_cacheListener.notifyEntryRemoved(_portalCache, key, value);

		if (_log.isDebugEnabled()) {
			_log.debug("Removed " + key + " from " + ehcache.getName());
		}
	}

	@Override
	public void notifyElementUpdated(Ehcache ehcache, Element element)
		throws CacheException {

		K key = (K)element.getKey();
		V value = (V)element.getObjectValue();

		_cacheListener.notifyEntryUpdated(_portalCache, key, value);

		if (_log.isDebugEnabled()) {
			_log.debug("Updated " + key + " in " + ehcache.getName());
		}
	}

	@Override
	public void notifyRemoveAll(Ehcache ehcache) {
		_cacheListener.notifyRemoveAll(_portalCache);

		if (_log.isDebugEnabled()) {
			_log.debug("Cleared " + ehcache.getName());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalCacheCacheEventListener.class);

	private CacheListener<K, V> _cacheListener;
	private PortalCache<K, V> _portalCache;

}