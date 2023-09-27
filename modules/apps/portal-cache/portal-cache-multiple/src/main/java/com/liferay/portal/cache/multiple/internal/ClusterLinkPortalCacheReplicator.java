/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.multiple.internal;

import com.liferay.portal.cache.PortalCacheReplicator;
import com.liferay.portal.cache.multiple.internal.cluster.link.PortalCacheClusterLink;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
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

		if (portalCache.isSharded()) {
			portalCacheClusterEvent.setCompanyId(
				CompanyThreadLocal.getCompanyId());
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

		if (portalCache.isSharded()) {
			portalCacheClusterEvent.setCompanyId(
				CompanyThreadLocal.getCompanyId());
		}

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

		if (portalCache.isSharded()) {
			portalCacheClusterEvent.setCompanyId(
				CompanyThreadLocal.getCompanyId());
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

		if (portalCache.isSharded()) {
			portalCacheClusterEvent.setCompanyId(
				CompanyThreadLocal.getCompanyId());
		}

		_portalCacheClusterLink.sendEvent(portalCacheClusterEvent);
	}

	private final PortalCacheClusterLink _portalCacheClusterLink;
	private final boolean _replicatePuts;
	private final boolean _replicatePutsViaCopy;
	private final boolean _replicateRemovals;
	private final boolean _replicateUpdates;
	private final boolean _replicateUpdatesViaCopy;

}