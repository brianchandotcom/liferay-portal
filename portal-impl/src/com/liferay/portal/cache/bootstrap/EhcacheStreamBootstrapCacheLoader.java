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

package com.liferay.portal.cache.bootstrap;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Properties;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.bootstrap.BootstrapCacheLoader;

/**
 * @author Shuyang Zhou
 * @author Sherry Yang
 */
public class EhcacheStreamBootstrapCacheLoader implements BootstrapCacheLoader {

	public EhcacheStreamBootstrapCacheLoader(Properties properties) {
		if (properties != null) {
			_bootstrapAsynchronously = GetterUtil.getBoolean(
				properties.getProperty("bootstrapAsynchronously"));
		}
		else {
			_bootstrapAsynchronously = true;
		}
	}

	@Override
	public Object clone() {
		return this;
	}

	public void doLoad(Ehcache ehcache) {
		if (_log.isDebugEnabled()) {
			_log.debug("Bootstraping " + ehcache.getName());
		}

		CacheManager cacheManager = ehcache.getCacheManager();

		try {
			ClusterLinkBootstrapLoaderHelperUtil.loadCachesFromCluster(
				cacheManager.getName(), ehcache.getName());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to load cache data from the cluster", e);
			}
		}
	}

	@Override
	public boolean isAsynchronous() {
		return _bootstrapAsynchronously;
	}

	@Override
	public void load(Ehcache ehcache) {
		if (ClusterLinkBootstrapLoaderHelperUtil.isSkipped()) {
			return;
		}

		if (_bootstrapAsynchronously) {
			EhcacheStreamClientThread streamBootstrapThread =
				new EhcacheStreamClientThread(ehcache);

			streamBootstrapThread.start();
		}
		else {
			doLoad(ehcache);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EhcacheStreamBootstrapCacheLoader.class);

	private final boolean _bootstrapAsynchronously;

	private class EhcacheStreamClientThread extends Thread {

		public EhcacheStreamClientThread(Ehcache ehcache) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Ehcache stream client thread for cache " +
						ehcache.getName());
			}

			_ehcache = ehcache;

			setDaemon(true);
			setName(
				EhcacheStreamClientThread.class.getName() + " - " +
					ehcache.getName());
			setPriority(Thread.NORM_PRIORITY);
		}

		@Override
		public void run() {
			try {
				doLoad(_ehcache);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to asynchronously stream bootstrap", e);
				}
			}
		}

		private final Ehcache _ehcache;

	}

}