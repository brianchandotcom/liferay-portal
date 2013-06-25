/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.util.PropsValues;

import java.util.Properties;

import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.bootstrap.BootstrapCacheLoaderFactory;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayBootstrapCacheLoaderFactory
	extends BootstrapCacheLoaderFactory {

	public LiferayBootstrapCacheLoaderFactory() {
		String className = PropsValues.EHCACHE_BOOTSTRAP_CACHE_LOADER_FACTORY;

		if (PropsValues.CLUSTER_LINK_ENABLED &&
			PropsValues.EHCACHE_CLUSTER_LINK_REPLICATION_ENABLED) {

			className =
				EhcacheStreamBootstrapCacheLoaderFactory.class.getName();
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Instantiating " + className + " " + hashCode());
		}

		try {
			_bootstrapCacheLoaderFactory =
				(BootstrapCacheLoaderFactory)InstanceFactory.newInstance(
					className);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public BootstrapCacheLoader createBootstrapCacheLoader(
		Properties properties) {

		return _bootstrapCacheLoaderFactory.createBootstrapCacheLoader(
			properties);
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiferayBootstrapCacheLoaderFactory.class);

	private BootstrapCacheLoaderFactory _bootstrapCacheLoaderFactory;

}