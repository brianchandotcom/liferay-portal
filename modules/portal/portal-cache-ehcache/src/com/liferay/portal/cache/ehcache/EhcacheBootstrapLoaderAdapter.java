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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.kernel.cache.BootstrapLoader;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import net.sf.ehcache.bootstrap.BootstrapCacheLoader;

/**
 * @author Tina Tian
 */
public class EhcacheBootstrapLoaderAdapter implements BootstrapLoader {

	public EhcacheBootstrapLoaderAdapter(
		BootstrapCacheLoader bootstrapCacheLoader) {

		_bootstrapCacheLoader = bootstrapCacheLoader;
	}

	@Override
	public boolean isAsynchronous() {
		return _bootstrapCacheLoader.isAsynchronous();
	}

	@Override
	public void load(String portalCacheManagerName, String portalCacheName) {
		PortalCacheManager<?, ?> portalCacheManager =
			PortalCacheProvider.getPortalCacheManager(portalCacheManagerName);

		if (!portalCacheManager.isClusterAware()) {
			_log.error(
				"Unable to load cache within cache manager " +
					portalCacheManagerName);

			return;
		}

		PortalCache<?, ?> portalCache = portalCacheManager.getCache(
			portalCacheName);

		_bootstrapCacheLoader.load(EhcacheUnwrapUtil.getEhcache(portalCache));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EhcacheBootstrapLoaderAdapter.class);

	private final BootstrapCacheLoader _bootstrapCacheLoader;

}