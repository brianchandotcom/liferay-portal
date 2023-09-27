/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.event;

import com.liferay.portal.cache.ehcache.internal.EhcacheUnwrapUtil;
import com.liferay.portal.cache.io.SerializableObjectWrapper;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheListener;

import java.io.Serializable;

import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

/**
 * @author Tina Tian
 */
public class EhcachePortalCacheListenerAdapter<K extends Serializable, V>
	implements ConfigurableEhcachePortalCacheListener,
			   PortalCacheListener<K, V> {

	public EhcachePortalCacheListenerAdapter(
		CacheEventListener cacheEventListener) {

		this.cacheEventListener = cacheEventListener;
	}

	@Override
	public void dispose() {
		cacheEventListener.dispose();
	}

	@Override
	public void notifyEntryEvicted(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		Element element = _createElement(key, value);

		if (timeToLive != PortalCache.DEFAULT_TIME_TO_LIVE) {
			element.setTimeToLive(timeToLive);
		}

		cacheEventListener.notifyElementEvicted(
			EhcacheUnwrapUtil.getEhcache(portalCache), element);
	}

	@Override
	public void notifyEntryExpired(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		Element element = _createElement(key, value);

		if (timeToLive != PortalCache.DEFAULT_TIME_TO_LIVE) {
			element.setTimeToLive(timeToLive);
		}

		cacheEventListener.notifyElementExpired(
			EhcacheUnwrapUtil.getEhcache(portalCache), element);
	}

	@Override
	public void notifyEntryPut(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		Element element = _createElement(key, value);

		if (timeToLive != PortalCache.DEFAULT_TIME_TO_LIVE) {
			element.setTimeToLive(timeToLive);
		}

		cacheEventListener.notifyElementPut(
			EhcacheUnwrapUtil.getEhcache(portalCache), element);
	}

	@Override
	public void notifyEntryRemoved(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		Element element = _createElement(key, value);

		if (timeToLive != PortalCache.DEFAULT_TIME_TO_LIVE) {
			element.setTimeToLive(timeToLive);
		}

		cacheEventListener.notifyElementRemoved(
			EhcacheUnwrapUtil.getEhcache(portalCache), element);
	}

	@Override
	public void notifyEntryUpdated(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		Element element = _createElement(key, value);

		if (timeToLive != PortalCache.DEFAULT_TIME_TO_LIVE) {
			element.setTimeToLive(timeToLive);
		}

		cacheEventListener.notifyElementUpdated(
			EhcacheUnwrapUtil.getEhcache(portalCache), element);
	}

	@Override
	public void notifyRemoveAll(PortalCache<K, V> portalCache)
		throws PortalCacheException {

		cacheEventListener.notifyRemoveAll(
			EhcacheUnwrapUtil.getEhcache(portalCache));
	}

	protected final CacheEventListener cacheEventListener;

	private Element _createElement(K key, V value) {
		Object objectValue = value;

		if (value instanceof Serializable) {
			objectValue = new SerializableObjectWrapper((Serializable)value);
		}

		return new Element(new SerializableObjectWrapper(key), objectValue);
	}

}