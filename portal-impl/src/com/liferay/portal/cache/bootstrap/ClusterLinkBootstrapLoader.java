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

import com.liferay.portal.kernel.cache.BootstrapLoader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Properties;

/**
 * @author Tina Tian
 */
public class ClusterLinkBootstrapLoader implements BootstrapLoader {

	public ClusterLinkBootstrapLoader(Properties properties) {
		if (properties != null) {
			_bootstrapAsynchronously = GetterUtil.getBoolean(
				properties.getProperty("bootstrapAsynchronously"));
		}
		else {
			_bootstrapAsynchronously = true;
		}
	}

	@Override
	public boolean isAsynchronous() {
		return _bootstrapAsynchronously;
	}

	@Override
	public void load(String portalCacheManagerName, String portalCacheName) {
		if (ClusterLinkBootstrapLoaderHelperUtil.isSkipped()) {
			return;
		}

		if (_bootstrapAsynchronously) {
			BootstrapLoaderClientThread bootstrapLoaderClientThread =
				new BootstrapLoaderClientThread(
					portalCacheManagerName, portalCacheName);

			bootstrapLoaderClientThread.start();
		}
		else {
			doLoad(portalCacheManagerName, portalCacheName);
		}
	}

	protected void doLoad(
		String portalCacheManagerName, String portalCacheName) {

		if (_log.isDebugEnabled()) {
			_log.debug("Bootstraping " + portalCacheName);
		}

		try {
			ClusterLinkBootstrapLoaderHelperUtil.loadCachesFromCluster(
				portalCacheManagerName, portalCacheName);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to load cache data from the cluster", e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClusterLinkBootstrapLoader.class);

	private final boolean _bootstrapAsynchronously;

	private class BootstrapLoaderClientThread extends Thread {

		public BootstrapLoaderClientThread(
			String portalCacheManagerName, String portalCacheName) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Bootstrap loader client thread for cache " +
						portalCacheName + " from cache manager " +
							portalCacheManagerName);
			}

			_portalCacheManagerName = portalCacheManagerName;
			_portalCacheName = portalCacheName;

			setDaemon(true);
			setName(
				BootstrapLoaderClientThread.class.getName() + " - " +
					portalCacheManagerName + " - " + portalCacheName);
			setPriority(Thread.NORM_PRIORITY);
		}

		@Override
		public void run() {
			try {
				doLoad(_portalCacheManagerName, _portalCacheName);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to asynchronously stream bootstrap", e);
				}
			}
		}

		private final String _portalCacheManagerName;
		private final String _portalCacheName;

	}

}