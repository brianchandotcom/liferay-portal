/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.portal.cache.ehcache.internal.event.PortalCacheCacheEventListener;
import com.liferay.portal.db.partition.DBPartitionUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.event.NotificationScope;
import net.sf.ehcache.event.RegisteredEventListeners;

/**
 * @author Tina Tian
 */
public class ShardedEhcachePortalCache<K extends Serializable, V>
	extends BaseEhcachePortalCache<K, V> {

	public ShardedEhcachePortalCache(
		BaseEhcachePortalCacheManager<K, V> baseEhcachePortalCacheManager,
		EhcachePortalCacheConfiguration ehcachePortalCacheConfiguration) {

		super(baseEhcachePortalCacheManager, ehcachePortalCacheConfiguration);

		_cacheManager = baseEhcachePortalCacheManager.getEhcacheManager();
	}

	@Override
	public Ehcache getEhcache() {
		long companyId = DBPartitionUtil.getCurrentCompanyId();

		return _ehcaches.computeIfAbsent(
			companyId,
			key -> {
				String shardedPortalCacheName =
					getPortalCacheName() + _SHARDED_SEPARATOR + key;

				synchronized (_cacheManager) {
					if (!_cacheManager.cacheExists(shardedPortalCacheName)) {
						if (_cacheManager.cacheExists(getPortalCacheName())) {
							Cache cache = _cacheManager.getCache(
								getPortalCacheName());

							CacheConfiguration cacheConfiguration =
								cache.getCacheConfiguration();

							CacheConfiguration clonedCacheConfiguration =
								cacheConfiguration.clone();

							clonedCacheConfiguration.setName(
								shardedPortalCacheName);

							_cacheManager.addCache(
								new Cache(clonedCacheConfiguration));
						}
						else {
							_cacheManager.addCache(shardedPortalCacheName);
						}
					}
				}

				Ehcache ehcache = _cacheManager.getCache(
					shardedPortalCacheName);

				RegisteredEventListeners registeredEventListeners =
					ehcache.getCacheEventNotificationService();

				registeredEventListeners.registerListener(
					new PortalCacheCacheEventListener<>(
						aggregatedPortalCacheListener, this),
					NotificationScope.ALL);

				return ehcache;
			});
	}

	@Override
	public boolean isSharded() {
		return true;
	}

	@Override
	protected void dispose() {
		_cacheManager.removeCache(getPortalCacheName());

		for (Ehcache ehcache : _ehcaches.values()) {
			_cacheManager.removeCache(ehcache.getName());
		}
	}

	protected void removeEhcache(long companyId) {
		Ehcache ehcache = _ehcaches.remove(companyId);

		if (ehcache == null) {
			return;
		}

		_cacheManager.removeCache(ehcache.getName());
	}

	@Override
	protected void resetEhcache() {
		_ehcaches.clear();
	}

	private static final String _SHARDED_SEPARATOR = "_SHARDED_SEPARATOR_";

	private final CacheManager _cacheManager;
	private final Map<Long, Ehcache> _ehcaches = new ConcurrentHashMap<>();

}