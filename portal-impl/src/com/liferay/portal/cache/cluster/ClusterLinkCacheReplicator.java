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

import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheReplicator;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEvent;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterEventType;
import com.liferay.portal.kernel.cache.cluster.PortalCacheClusterLinkUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.util.Properties;

/**
 * @author Tina Tian
 */
public class ClusterLinkCacheReplicator
	<K extends Serializable, V extends Serializable>
		implements CacheListener<K, V>, CacheReplicator {

	public ClusterLinkCacheReplicator(Properties properties) {
		if (properties != null) {
			_replicatePuts = GetterUtil.getBoolean(
				properties.getProperty(_REPLICATE_PUTS), true);
			_replicatePutsViaCopy = GetterUtil.getBoolean(
				properties.getProperty(_REPLICATE_PUTS_VIA_COPY));
			_replicateRemovals = GetterUtil.getBoolean(
				properties.getProperty(_REPLICATE_REMOVALS), true);
			_replicateUpdates = GetterUtil.getBoolean(
				properties.getProperty(_REPLICATE_UPDATES), true);
			_replicateUpdatesViaCopy = GetterUtil.getBoolean(
				properties.getProperty(_REPLICATE_UPDATES_VIA_COPY));
		}
		else {
			_replicatePuts = true;
			_replicatePutsViaCopy = false;
			_replicateRemovals = true;
			_replicateUpdates = true;
			_replicateUpdatesViaCopy = false;
		}
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
				portalCacheManager.getName(), portalCache.getName(), key,
				PortalCacheClusterEventType.PUT);

		if (_replicatePutsViaCopy) {
			portalCacheClusterEvent.setElementValue(value);
			portalCacheClusterEvent.setTimeToLive(timeToLive);
		}

		PortalCacheClusterLinkUtil.sendEvent(portalCacheClusterEvent);
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
				portalCacheManager.getName(), portalCache.getName(), key,
				PortalCacheClusterEventType.REMOVE);

		PortalCacheClusterLinkUtil.sendEvent(portalCacheClusterEvent);
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
				portalCacheManager.getName(), portalCache.getName(), key,
				PortalCacheClusterEventType.UPDATE);

		if (_replicateUpdatesViaCopy) {
			portalCacheClusterEvent.setElementValue(value);
			portalCacheClusterEvent.setTimeToLive(timeToLive);
		}

		PortalCacheClusterLinkUtil.sendEvent(portalCacheClusterEvent);
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
				portalCacheManager.getName(), portalCache.getName(), null,
				PortalCacheClusterEventType.REMOVE_ALL);

		PortalCacheClusterLinkUtil.sendEvent(portalCacheClusterEvent);
	}

	private static final String _REPLICATE_PUTS = "replicatePuts";

	private static final String _REPLICATE_PUTS_VIA_COPY =
		"replicatePutsViaCopy";

	private static final String _REPLICATE_REMOVALS = "replicateRemovals";

	private static final String _REPLICATE_UPDATES = "replicateUpdates";

	private static final String _REPLICATE_UPDATES_VIA_COPY =
		"replicateUpdatesViaCopy";

	private final boolean _replicatePuts;
	private final boolean _replicatePutsViaCopy;
	private final boolean _replicateRemovals;
	private final boolean _replicateUpdates;
	private final boolean _replicateUpdatesViaCopy;

}