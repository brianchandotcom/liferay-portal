/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.portal.cache.ehcache.internal.event.PortalCacheCacheEventListener;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.io.Serializable;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.CacheRuntimeConfiguration;
import org.ehcache.config.FluentCacheConfigurationBuilder;
import org.ehcache.event.EventFiring;
import org.ehcache.event.EventOrdering;
import org.ehcache.event.EventType;

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
	public Cache<?, ?> getEhcache() {
		long companyId = CompanyThreadLocal.getNonsystemCompanyId();

		return _caches.computeIfAbsent(
			companyId,
			key -> {
				String shardedPortalCacheName = _getCacheName(key);

				synchronized (_cacheManager) {
					Cache<?, ?> cache = _cacheManager.getCache(
						shardedPortalCacheName, keyType, valueType);

					if (cache == null) {
						Cache<?, ?> mainCache = _cacheManager.getCache(
							getPortalCacheName(), keyType, valueType);

						if (mainCache != null) {
							CacheRuntimeConfiguration<?, ?>
								cacheRuntimeConfiguration =
									mainCache.getRuntimeConfiguration();

							FluentCacheConfigurationBuilder<?, ?, ?>
								fluentCacheConfigurationBuilder =
									cacheRuntimeConfiguration.derive();

							CacheConfiguration<?, ?> clonedCacheConfiguration =
								fluentCacheConfigurationBuilder.build();

							_cacheManager.createCache(
								shardedPortalCacheName,
								clonedCacheConfiguration);
						}
						else {
							BaseEhcachePortalCacheManager<?, ?>
								baseEhcachePortalCacheManager =
									(BaseEhcachePortalCacheManager<?, ?>)
										getPortalCacheManager();

							_cacheManager.createCache(
								shardedPortalCacheName,
								baseEhcachePortalCacheManager.
									getDefaultCacheConfiguration());
						}
					}
				}

				Cache<?, ?> cache = _cacheManager.getCache(
					shardedPortalCacheName, keyType, valueType);

				CacheRuntimeConfiguration<?, ?> cacheRuntimeConfiguration =
					cache.getRuntimeConfiguration();

				cacheRuntimeConfiguration.registerCacheEventListener(
					new PortalCacheCacheEventListener<>(
						aggregatedPortalCacheListener, this),
					EventOrdering.ORDERED, EventFiring.SYNCHRONOUS,
					EnumSet.allOf(EventType.class));

				return cache;
			});
	}

	@Override
	public boolean isSharded() {
		return true;
	}

	@Override
	protected void dispose() {
		_cacheManager.removeCache(getPortalCacheName());

		for (Long key : _caches.keySet()) {
			_cacheManager.removeCache(_getCacheName(key));
		}
	}

	protected void removeEhcache(long companyId) {
		Cache<?, ?> cache = _caches.remove(companyId);

		if (cache == null) {
			return;
		}

		_cacheManager.removeCache(_getCacheName(companyId));
	}

	@Override
	protected void resetEhcache() {
		_caches.clear();
	}

	private String _getCacheName(long companyId) {
		return getPortalCacheName() + _SHARDED_SEPARATOR + companyId;
	}

	private static final String _SHARDED_SEPARATOR = "_SHARDED_SEPARATOR_";

	private final CacheManager _cacheManager;
	private final Map<Long, Cache<?, ?>> _caches = new ConcurrentHashMap<>();

}