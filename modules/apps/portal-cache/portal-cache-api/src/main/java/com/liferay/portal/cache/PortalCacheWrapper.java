/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheManager;

import java.io.Serializable;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheWrapper<K extends Serializable, V>
	implements PortalCache<K, V> {

	public PortalCacheWrapper(PortalCache<K, V> portalCache) {
		this.portalCache = portalCache;
	}

	@Override
	public V get(K key) {
		return portalCache.get(key);
	}

	@Override
	public List<K> getKeys() {
		return portalCache.getKeys();
	}

	@Override
	public PortalCacheManager<K, V> getPortalCacheManager() {
		return portalCache.getPortalCacheManager();
	}

	@Override
	public String getPortalCacheName() {
		return portalCache.getPortalCacheName();
	}

	public PortalCache<K, V> getWrappedPortalCache() {
		return portalCache;
	}

	@Override
	public boolean isMVCC() {
		return portalCache.isMVCC();
	}

	@Override
	public boolean isSharded() {
		return portalCache.isSharded();
	}

	@Override
	public void put(K key, V value) {
		portalCache.put(key, value);
	}

	@Override
	public void put(K key, V value, int timeToLive) {
		portalCache.put(key, value, timeToLive);
	}

	@Override
	public void registerPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener) {

		portalCache.registerPortalCacheListener(portalCacheListener);
	}

	@Override
	public void registerPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener,
		PortalCacheListenerScope portalCacheListenerScope) {

		portalCache.registerPortalCacheListener(
			portalCacheListener, portalCacheListenerScope);
	}

	@Override
	public void remove(K key) {
		portalCache.remove(key);
	}

	@Override
	public void removeAll() {
		portalCache.removeAll();
	}

	public void setPortalCache(PortalCache<K, V> portalCache) {
		this.portalCache = portalCache;
	}

	@Override
	public void unregisterPortalCacheListener(
		PortalCacheListener<K, V> portalCacheListener) {

		portalCache.unregisterPortalCacheListener(portalCacheListener);
	}

	@Override
	public void unregisterPortalCacheListeners() {
		portalCache.unregisterPortalCacheListeners();
	}

	protected PortalCache<K, V> portalCache;

}