/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cache;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class PortalCacheMapSynchronizeUtil {

	public static <K extends Serializable, V> void synchronize(
		PortalCache<K, V> portalCache, Map<? extends K, ? extends V> map,
		Synchronizer<K, V> synchronizer) {

		portalCache.registerPortalCacheListener(
			new SynchronizePortalCacheListener<K, V>(map, synchronizer));
	}

	public interface Synchronizer<K extends Serializable, V> {

		public void onSynchronize(
			Map<? extends K, ? extends V> map, K key, V value, int timeToLive);

	}

	private static class SynchronizePortalCacheListener
		<K extends Serializable, V>
			implements PortalCacheListener<K, V> {

		public SynchronizePortalCacheListener(
			Map<? extends K, ? extends V> map,
			Synchronizer<K, V> synchronizer) {

			_map = map;
			_synchronizer = synchronizer;
		}

		@Override
		public void dispose() {
		}

		@Override
		public void notifyEntryEvicted(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive) {

			_synchronizer.onSynchronize(_map, key, value, timeToLive);
		}

		@Override
		public void notifyEntryExpired(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive) {

			_synchronizer.onSynchronize(_map, key, value, timeToLive);
		}

		@Override
		public void notifyEntryPut(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive) {

			_synchronizer.onSynchronize(_map, key, value, timeToLive);
		}

		@Override
		public void notifyEntryRemoved(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive) {

			_synchronizer.onSynchronize(_map, key, value, timeToLive);
		}

		@Override
		public void notifyEntryUpdated(
			PortalCache<K, V> portalCache, K key, V value, int timeToLive) {

			_synchronizer.onSynchronize(_map, key, value, timeToLive);
		}

		@Override
		public void notifyRemoveAll(PortalCache<K, V> portalCache) {
			_map.clear();
		}

		private final Map<? extends K, ? extends V> _map;
		private final Synchronizer<K, V> _synchronizer;

	}

}