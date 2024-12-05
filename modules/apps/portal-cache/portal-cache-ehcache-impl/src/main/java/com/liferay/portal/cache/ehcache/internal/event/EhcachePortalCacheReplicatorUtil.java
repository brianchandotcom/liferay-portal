/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.event;

import com.liferay.portal.cache.PortalCacheReplicator;
import com.liferay.portal.cache.PortalCacheReplicatorFactory;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.util.Properties;

/**
 * @author Tina Tian
 */
public class EhcachePortalCacheReplicatorUtil {

	public static <K extends Serializable, V> PortalCacheListener<K, V> create(
		PortalCacheReplicatorFactory portalCacheReplicatorFactory,
		Properties properties) {

		boolean replicator = GetterUtil.getBoolean(
			properties.get(PortalCacheReplicator.REPLICATOR));

		if (replicator) {
			PortalCacheListener<K, V> portalCacheListener =
				(PortalCacheListener<K, V>)portalCacheReplicatorFactory.create(
					properties);

			if (portalCacheListener == null) {
				return null;
			}

			return (PortalCacheListener<K, V>)
				new EhcachePortalCacheReplicator<>(
					(PortalCacheReplicator<K, Serializable>)
						portalCacheListener);
		}

		return null;
	}

	private static class EhcachePortalCacheReplicator
		<K extends Serializable, V extends Serializable>
			implements ConfigurableEhcachePortalCacheListener,
					   PortalCacheReplicator<K, V> {

		@Override
		public void dispose() {
			_portalCacheReplicator.dispose();
		}

		@Override
		public void notifyEntryEvicted(
				PortalCache<K, V> portalCache, K key, V value, int timeToLive)
			throws PortalCacheException {

			_portalCacheReplicator.notifyEntryEvicted(
				portalCache, key, value, timeToLive);
		}

		@Override
		public void notifyEntryExpired(
				PortalCache<K, V> portalCache, K key, V value, int timeToLive)
			throws PortalCacheException {

			_portalCacheReplicator.notifyEntryExpired(
				portalCache, key, value, timeToLive);
		}

		@Override
		public void notifyEntryPut(
				PortalCache<K, V> portalCache, K key, V value, int timeToLive)
			throws PortalCacheException {

			_portalCacheReplicator.notifyEntryPut(
				portalCache, key, value, timeToLive);
		}

		@Override
		public void notifyEntryRemoved(
				PortalCache<K, V> portalCache, K key, V value, int timeToLive)
			throws PortalCacheException {

			_portalCacheReplicator.notifyEntryRemoved(
				portalCache, key, value, timeToLive);
		}

		@Override
		public void notifyEntryUpdated(
				PortalCache<K, V> portalCache, K key, V value, int timeToLive)
			throws PortalCacheException {

			_portalCacheReplicator.notifyEntryUpdated(
				portalCache, key, value, timeToLive);
		}

		@Override
		public void notifyRemoveAll(PortalCache<K, V> portalCache)
			throws PortalCacheException {

			_portalCacheReplicator.notifyRemoveAll(portalCache);
		}

		private EhcachePortalCacheReplicator(
			PortalCacheReplicator<K, V> portalCacheReplicator) {

			_portalCacheReplicator = portalCacheReplicator;
		}

		private final PortalCacheReplicator<K, V> _portalCacheReplicator;

	}

}