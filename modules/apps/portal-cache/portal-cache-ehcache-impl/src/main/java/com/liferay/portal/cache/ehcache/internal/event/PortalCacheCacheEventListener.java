/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.event;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.cache.AggregatedPortalCacheListener;
import com.liferay.portal.cache.ehcache.internal.BaseEhcachePortalCache;
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

		if (_portalCache instanceof BaseEhcachePortalCache) {
			BaseEhcachePortalCache baseEhcachePortalCache =
				(BaseEhcachePortalCache)_portalCache;

			requireSerialization = baseEhcachePortalCache.isSerializable();
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
					"Evicted ", _getKey(element), " from ", ehcache.getName()));
		}

		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		_aggregatedPortalCacheListener.notifyEntryEvicted(
			_portalCache, _getKey(element), _getValue(element),
			element.getTimeToLive());
	}

	@Override
	public void notifyElementExpired(Ehcache ehcache, Element element) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Expired ", _getKey(element), " from ", ehcache.getName()));
		}

		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		_aggregatedPortalCacheListener.notifyEntryExpired(
			_portalCache, _getKey(element), _getValue(element),
			element.getTimeToLive());
	}

	@Override
	public void notifyElementPut(Ehcache ehcache, Element element)
		throws CacheException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Put ", _getKey(element), " into ", ehcache.getName()));
		}

		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		_aggregatedPortalCacheListener.notifyEntryPut(
			_portalCache, _getKey(element), _getValue(element),
			element.getTimeToLive());
	}

	@Override
	public void notifyElementRemoved(Ehcache ehcache, Element element)
		throws CacheException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Removed ", _getKey(element), " from ", ehcache.getName()));
		}

		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		_aggregatedPortalCacheListener.notifyEntryRemoved(
			_portalCache, _getKey(element), _getValue(element),
			element.getTimeToLive());
	}

	@Override
	public void notifyElementUpdated(Ehcache ehcache, Element element)
		throws CacheException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Updated ", _getKey(element), " in ", ehcache.getName()));
		}

		if (_aggregatedPortalCacheListener.isEmpty()) {
			return;
		}

		_aggregatedPortalCacheListener.notifyEntryUpdated(
			_portalCache, _getKey(element), _getValue(element),
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

	private K _getKey(Element element) {
		if (_requireSerialization) {
			return SerializableObjectWrapper.unwrap(element.getObjectKey());
		}

		return (K)element.getObjectKey();
	}

	private V _getValue(Element element) {
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