/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.cache.ehcache.internal.event.PortalCacheCacheEventListener;

import java.io.Serializable;

import java.util.EnumSet;
import java.util.function.Supplier;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheRuntimeConfiguration;
import org.ehcache.event.EventFiring;
import org.ehcache.event.EventOrdering;
import org.ehcache.event.EventType;

/**
 * @author Brian Wing Shun Chan
 * @author Edward Han
 * @author Shuyang Zhou
 */
public class EhcachePortalCache<K extends Serializable, V>
	extends BaseEhcachePortalCache<K, V> {

	public EhcachePortalCache(
		BaseEhcachePortalCacheManager<K, V> baseEhcachePortalCacheManager,
		EhcachePortalCacheConfiguration ehcachePortalCacheConfiguration) {

		super(baseEhcachePortalCacheManager, ehcachePortalCacheConfiguration);

		_cacheManager = baseEhcachePortalCacheManager.getEhcacheManager();

		_ehcacheSupplier = this::_createEhcache;
	}

	@Override
	public Cache<?, ?> getEhcache() {
		return _ehcacheDCLSingleton.getSingleton(_ehcacheSupplier);
	}

	@Override
	protected void dispose() {
		_cacheManager.removeCache(getPortalCacheName());
	}

	@Override
	protected void resetEhcache() {
		_ehcacheDCLSingleton.destroy(null);
	}

	private Cache<?, ?> _createEhcache() {
		synchronized (_cacheManager) {
			Cache<?, ?> cache = _cacheManager.getCache(
				getPortalCacheName(), keyType, valueType);

			if (cache == null) {
				BaseEhcachePortalCacheManager<?, ?>
					baseEhcachePortalCacheManager =
						(BaseEhcachePortalCacheManager<?, ?>)
							getPortalCacheManager();

				_cacheManager.createCache(
					getPortalCacheName(),
					baseEhcachePortalCacheManager.newBuilder());
			}
		}

		Cache<?, ?> cache = _cacheManager.getCache(
			getPortalCacheName(), keyType, valueType);

		CacheRuntimeConfiguration<?, ?> cacheRuntimeConfiguration =
			cache.getRuntimeConfiguration();

		cacheRuntimeConfiguration.registerCacheEventListener(
			new PortalCacheCacheEventListener<>(
				aggregatedPortalCacheListener, this),
			EventOrdering.ORDERED, EventFiring.SYNCHRONOUS,
			EnumSet.allOf(EventType.class));

		return cache;
	}

	private final CacheManager _cacheManager;
	private final DCLSingleton<Cache<?, ?>> _ehcacheDCLSingleton =
		new DCLSingleton<>();
	private final Supplier<Cache<?, ?>> _ehcacheSupplier;

}