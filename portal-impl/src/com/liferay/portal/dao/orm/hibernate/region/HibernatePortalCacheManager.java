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

package com.liferay.portal.dao.orm.hibernate.region;

import com.liferay.portal.cache.ehcache.EhcachePortalCacheManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;

/**
 * @author Tina Tian
 */
public class HibernatePortalCacheManager
	extends EhcachePortalCacheManager<Serializable, Serializable> {

	@Override
	protected Ehcache createEhcache(
		String portalCacheName, CacheConfiguration cacheConfiguration) {

		reconfigureCache(new Cache(cacheConfiguration));

		CacheManager cacheManager = getEhcacheManager();

		return cacheManager.getCache(portalCacheName);
	}

	protected void reconfigureCache(Ehcache replacementCache) {
		CacheManager cacheManager = getEhcacheManager();

		String cacheName = replacementCache.getName();

		Ehcache ehcache = cacheManager.getEhcache(cacheName);

		if ((ehcache != null) &&
			(ehcache instanceof ModifiableEhcacheWrapper)) {

			if (_log.isInfoEnabled()) {
				_log.info("Reconfiguring Hibernate cache " + cacheName);
			}

			ModifiableEhcacheWrapper modifiableEhcacheWrapper =
				(ModifiableEhcacheWrapper)ehcache;

			cacheManager.replaceCacheWithDecoratedCache(
				ehcache, modifiableEhcacheWrapper.getWrappedCache());

			cacheManager.removeCache(cacheName);

			cacheManager.addCache(replacementCache);

			modifiableEhcacheWrapper.setWrappedCache(replacementCache);

			cacheManager.replaceCacheWithDecoratedCache(
				replacementCache, modifiableEhcacheWrapper);
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("Configuring Hibernate cache " + cacheName);
			}

			if (ehcache != null) {
				cacheManager.removeCache(cacheName);
			}

			ehcache = new ModifiableEhcacheWrapper(replacementCache);

			cacheManager.addCache(replacementCache);

			cacheManager.replaceCacheWithDecoratedCache(
				replacementCache, ehcache);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HibernatePortalCacheManager.class);

}