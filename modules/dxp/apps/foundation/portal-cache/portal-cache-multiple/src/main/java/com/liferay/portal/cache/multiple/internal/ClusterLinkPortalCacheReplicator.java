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

package com.liferay.portal.cache.multiple.internal;

import com.liferay.portal.cache.PortalCacheReplicator;
import com.liferay.portal.cache.multiple.internal.cluster.link.PortalCacheClusterLink;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.util.Properties;

/**
 * @author Tina Tian
 */
public class ClusterLinkPortalCacheReplicator
	<K extends Serializable, V extends Serializable>
		implements PortalCacheReplicator<K, V> {

	public ClusterLinkPortalCacheReplicator(
		Properties properties, PortalCacheClusterLink portalCacheClusterLink) {

		_portalCacheClusterLink = portalCacheClusterLink;

		_replicatePuts = GetterUtil.getBoolean(
			properties.getProperty(PortalCacheReplicator.REPLICATE_PUTS),
			PortalCacheReplicator.DEFAULT_REPLICATE_PUTS);
		_replicatePutsViaCopy = GetterUtil.getBoolean(
			properties.getProperty(
				PortalCacheReplicator.REPLICATE_PUTS_VIA_COPY),
			PortalCacheReplicator.DEFAULT_REPLICATE_PUTS_VIA_COPY);
		_replicateRemovals = GetterUtil.getBoolean(
			properties.getProperty(PortalCacheReplicator.REPLICATE_REMOVALS),
			PortalCacheReplicator.DEFAULT_REPLICATE_REMOVALS);
		_replicateUpdates = GetterUtil.getBoolean(
			properties.getProperty(PortalCacheReplicator.REPLICATE_UPDATES),
			PortalCacheReplicator.DEFAULT_REPLICATE_UPDATES);
		_replicateUpdatesViaCopy = GetterUtil.getBoolean(
			properties.getProperty(
				PortalCacheReplicator.REPLICATE_UPDATES_VIA_COPY),
			PortalCacheReplicator.DEFAULT_REPLICATE_UPDATES_VIA_COPY);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void notifyEntryEvicted(
		PortalCache<K, V> portalCache, K key, V value, int timeToLive) {
	}

	@Override
	public void notifyEntryExpired(
		PortalCache<K, V> portalCache, K key, V value, int timeToLive) {
	}

	@Override
	public void notifyEntryPut(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		if (!_replicatePuts) {
			return;
		}

		PortalCacheManager<K, V> portalCacheManager =
			portalCache.getPortalCacheManager();

		PortalCacheClusterEvent portalCacheClusterEvent =
			new PortalCacheClusterEvent(
				portalCacheManager.getPortalCacheManagerName(),
				portalCache.getPortalCacheName(), key,
				PortalCacheClusterEventType.PUT);

		if (_replicatePutsViaCopy) {
			portalCacheClusterEvent.setElementValue(value);
			portalCacheClusterEvent.setTimeToLive(timeToLive);
		}

		_portalCacheClusterLink.sendEvent(portalCacheClusterEvent);
	}

	@Override
	public void notifyEntryRemoved(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		if (!_replicateRemovals) {
			return;
		}

		PortalCacheManager<K, V> portalCacheManager =
			portalCache.getPortalCacheManager();

		PortalCacheClusterEvent portalCacheClusterEvent =
			new PortalCacheClusterEvent(
				portalCacheManager.getPortalCacheManagerName(),
				portalCache.getPortalCacheName(), key,
				PortalCacheClusterEventType.REMOVE);

		_portalCacheClusterLink.sendEvent(portalCacheClusterEvent);
	}

	@Override
	public void notifyEntryUpdated(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive)
		throws PortalCacheException {

		if (!_replicateUpdates) {
			return;
		}

		PortalCacheManager<K, V> portalCacheManager =
			portalCache.getPortalCacheManager();

		PortalCacheClusterEvent portalCacheClusterEvent =
			new PortalCacheClusterEvent(
				portalCacheManager.getPortalCacheManagerName(),
				portalCache.getPortalCacheName(), key,
				PortalCacheClusterEventType.UPDATE);

		if (_replicateUpdatesViaCopy) {
			portalCacheClusterEvent.setElementValue(value);
			portalCacheClusterEvent.setTimeToLive(timeToLive);
		}

		_portalCacheClusterLink.sendEvent(portalCacheClusterEvent);
	}

	@Override
	public void notifyRemoveAll(PortalCache<K, V> portalCache)
		throws PortalCacheException {

		if (!_replicateRemovals) {
			return;
		}

		PortalCacheManager<K, V> portalCacheManager =
			portalCache.getPortalCacheManager();

		PortalCacheClusterEvent portalCacheClusterEvent =
			new PortalCacheClusterEvent(
				portalCacheManager.getPortalCacheManagerName(),
				portalCache.getPortalCacheName(), null,
				PortalCacheClusterEventType.REMOVE_ALL);

		_portalCacheClusterLink.sendEvent(portalCacheClusterEvent);
	}

	private final PortalCacheClusterLink _portalCacheClusterLink;
	private final boolean _replicatePuts;
	private final boolean _replicatePutsViaCopy;
	private final boolean _replicateRemovals;
	private final boolean _replicateUpdates;
	private final boolean _replicateUpdatesViaCopy;

}