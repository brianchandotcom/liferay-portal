/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.management;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.time.Duration;

import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.ehcache.Cache;
import org.ehcache.config.CacheRuntimeConfiguration;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.ResourceType;
import org.ehcache.config.SizedResourcePool;
import org.ehcache.expiry.ExpiryPolicy;

/**
 * @author Dante Wang
 */
public class CacheMBeanImpl extends StandardMBean implements CacheMBean {

	public CacheMBeanImpl(String cacheName, Cache<?, ?> cache)
		throws NotCompliantMBeanException {

		super(CacheMBean.class);

		_cacheName = cacheName;
		_cache = cache;

		_cacheRuntimeConfiguration = cache.getRuntimeConfiguration();
	}

	@Override
	public void clear() {
		_cache.clear();
	}

	@Override
	public String getHeapSize() {
		ResourcePools resourcePools =
			_cacheRuntimeConfiguration.getResourcePools();

		SizedResourcePool sizedResourcePool = resourcePools.getPoolForResource(
			ResourceType.Core.HEAP);

		if (sizedResourcePool == null) {
			return "No heap store for this cache";
		}

		return StringBundler.concat(
			sizedResourcePool.getSize(), StringPool.SPACE,
			sizedResourcePool.getUnit());
	}

	@Override
	public String getKeyType() {
		Class<?> clazz = _cacheRuntimeConfiguration.getKeyType();

		return clazz.getName();
	}

	@Override
	public String getName() {
		return _cacheName;
	}

	@Override
	public long getTimeToIdle() {
		ExpiryPolicy<?, ?> expiryPolicy =
			_cacheRuntimeConfiguration.getExpiryPolicy();

		Duration duration = expiryPolicy.getExpiryForAccess(null, null);

		return duration.getSeconds();
	}

	@Override
	public String getValueType() {
		Class<?> clazz = _cacheRuntimeConfiguration.getValueType();

		return clazz.getName();
	}

	private final Cache<?, ?> _cache;
	private final String _cacheName;
	private final CacheRuntimeConfiguration<?, ?> _cacheRuntimeConfiguration;

}