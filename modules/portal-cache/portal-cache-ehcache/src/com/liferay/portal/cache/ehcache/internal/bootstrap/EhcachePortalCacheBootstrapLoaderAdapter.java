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

package com.liferay.portal.cache.ehcache.internal.bootstrap;

import com.liferay.portal.cache.ehcache.EhcacheUnwrapUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheBootstrapLoader;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import net.sf.ehcache.bootstrap.BootstrapCacheLoader;

/**
 * @author Tina Tian
 */
public class EhcachePortalCacheBootstrapLoaderAdapter
	implements PortalCacheBootstrapLoader {

	public EhcachePortalCacheBootstrapLoaderAdapter(
		BootstrapCacheLoader bootstrapCacheLoader) {

		_bootstrapCacheLoader = bootstrapCacheLoader;
	}

	@Override
	public boolean isAsynchronous() {
		return _bootstrapCacheLoader.isAsynchronous();
	}

	@Override
	public void loadPortalCache(
		String portalCacheManagerName, String portalCacheName) {

		PortalCacheManager<?, ?> portalCacheManager =
			PortalCacheManagerProvider.getPortalCacheManager(
				portalCacheManagerName);

		if (!portalCacheManager.isClusterAware()) {
			_log.error(
				"Unable to load cache within cache manager " +
					portalCacheManagerName);

			return;
		}

		PortalCache<?, ?> portalCache = portalCacheManager.getPortalCache(
			portalCacheName);

		_bootstrapCacheLoader.load(EhcacheUnwrapUtil.getEhcache(portalCache));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EhcachePortalCacheBootstrapLoaderAdapter.class);

	private final BootstrapCacheLoader _bootstrapCacheLoader;

}