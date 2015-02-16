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

package com.liferay.portal.cache.cluster;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheProvider;

import java.io.Serializable;

import java.util.Properties;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

/**
 * @author Shuyang Zhou
 */
public class EhcachePortalCacheClusterReplicator
		<K extends Serializable, V extends Serializable>
	implements CacheEventListener {

	public EhcachePortalCacheClusterReplicator(Properties properties) {
		_clusterLinkCacheReplicator = new ClusterLinkCacheReplicator<>(
			properties);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public void dispose() {
	}

	@Override
	public void notifyElementEvicted(Ehcache ehcache, Element element) {
	}

	@Override
	public void notifyElementExpired(Ehcache ehcache, Element element) {
	}

	@Override
	public void notifyElementPut(Ehcache ehcache, Element element)
		throws CacheException {

		if (!ClusterReplicationThreadLocal.isReplicate()) {
			return;
		}

		_clusterLinkCacheReplicator.notifyEntryPut(
			_getPortalCache(ehcache), (K)element.getObjectKey(),
			(V)element.getObjectValue(), element.getTimeToLive());
	}

	@Override
	public void notifyElementRemoved(Ehcache ehcache, Element element)
		throws CacheException {

		if (!ClusterReplicationThreadLocal.isReplicate()) {
			return;
		}

		_clusterLinkCacheReplicator.notifyEntryRemoved(
			_getPortalCache(ehcache), (K)element.getObjectKey(),
			(V)element.getObjectValue(), element.getTimeToLive());
	}

	@Override
	public void notifyElementUpdated(Ehcache ehcache, Element element)
		throws CacheException {

		if (!ClusterReplicationThreadLocal.isReplicate()) {
			return;
		}

		_clusterLinkCacheReplicator.notifyEntryUpdated(
			_getPortalCache(ehcache), (K)element.getObjectKey(),
			(V)element.getObjectValue(), element.getTimeToLive());
	}

	@Override
	public void notifyRemoveAll(Ehcache ehcache) {
		if (!ClusterReplicationThreadLocal.isReplicate()) {
			return;
		}

		_clusterLinkCacheReplicator.notifyRemoveAll(_getPortalCache(ehcache));
	}

	private PortalCache<K, V> _getPortalCache(Ehcache ehcache) {
		CacheManager cacheManager = ehcache.getCacheManager();

		PortalCacheManager<K, V> portalCacheManager =
			(PortalCacheManager<K, V>)PortalCacheProvider.getPortalCacheManager(
				cacheManager.getName());

		return portalCacheManager.getCache(ehcache.getName());
	}

	private final ClusterLinkCacheReplicator<K, V> _clusterLinkCacheReplicator;

}