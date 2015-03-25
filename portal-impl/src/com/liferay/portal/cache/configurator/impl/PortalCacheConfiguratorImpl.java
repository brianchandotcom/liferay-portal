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

package com.liferay.portal.cache.configurator.impl;

import com.liferay.portal.cache.configurator.PortalCacheConfigurator;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.util.ClassLoaderUtil;

import java.io.Serializable;

import java.net.URL;

/**
 * @author Miguel Pastor
 * @author Shuyang Zhou
 */
public class PortalCacheConfiguratorImpl implements PortalCacheConfigurator {

	@Override
	public void reconfigureCaches(
		String portalCacheManagerName, ClassLoader classLoader, URL url) {

		if (url == null) {
			return;
		}

		PortalCacheManager<? extends Serializable, ?> portalCacheManager =
			PortalCacheProvider.getPortalCacheManager(portalCacheManagerName);

		if (portalCacheManager == null) {
			return;
		}

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		ClassLoaderUtil.setContextClassLoader(
			AggregateClassLoader.getAggregateClassLoader(
				ClassLoaderUtil.getPortalClassLoader(), classLoader));

		try {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Reconfiguring caches in cache manager " +
						portalCacheManager.getName() + " using " + url);
			}

			portalCacheManager.reconfigureCaches(url);
		}
		finally {
			ClassLoaderUtil.setContextClassLoader(contextClassLoader);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalCacheConfiguratorImpl.class);

}