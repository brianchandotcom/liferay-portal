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

package com.liferay.portal.cache.ehcache.multiple.internal.bootstrap;

import com.liferay.portal.cache.PortalCacheBootstrapLoader;
import com.liferay.portal.cache.ehcache.EhcacheUnwrapUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerProvider;
import com.liferay.portal.kernel.cache.SkipReplicationThreadLocal;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import net.sf.ehcache.bootstrap.BootstrapCacheLoader;

/**
 * @author Tina Tian
 */
public class EhcachePortalCacheBootstrapLoaderAdapter
	implements PortalCacheBootstrapLoader {

	public EhcachePortalCacheBootstrapLoaderAdapter(
		BootstrapCacheLoader bootstrapCacheLoader,
		boolean bootstrapAsynchronously,
		ThreadPoolExecutor threadPoolExecutor) {

		_bootstrapCacheLoader = bootstrapCacheLoader;
		_bootstrapAsynchronously = bootstrapAsynchronously;
		_threadPoolExecutor = threadPoolExecutor;
	}

	@Override
	public boolean isAsynchronous() {
		return _bootstrapAsynchronously;
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

		final PortalCache<?, ?> portalCache = portalCacheManager.getPortalCache(
			portalCacheName);

		if (!_bootstrapAsynchronously) {
			_loadPortalCache(portalCache);

			return;
		}

		_threadPoolExecutor.submit(
			new Runnable() {

				@Override
				public void run() {
					_loadPortalCache(portalCache);
				}

			});
	}

	private void _loadPortalCache(PortalCache<?, ?> portalCache) {
		boolean enabled = SkipReplicationThreadLocal.isEnabled();

		SkipReplicationThreadLocal.setEnabled(true);

		try {
			_bootstrapCacheLoader.load(
				EhcacheUnwrapUtil.getEhcache(portalCache));
		}
		finally {
			SkipReplicationThreadLocal.setEnabled(enabled);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EhcachePortalCacheBootstrapLoaderAdapter.class);

	private final boolean _bootstrapAsynchronously;
	private final BootstrapCacheLoader _bootstrapCacheLoader;
	private final ThreadPoolExecutor _threadPoolExecutor;

}