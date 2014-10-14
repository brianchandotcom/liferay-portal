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
import com.liferay.portal.dao.orm.hibernate.region.LiferayEhcacheRegionFactory;
import com.liferay.portal.dao.orm.hibernate.region.SingletonLiferayEhcacheRegionFactory;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.ClassLoaderUtil;

import java.io.Serializable;

import java.net.URL;

import java.util.Collection;

/**
 * @author Miguel Pastor
 */
public class PortalCacheConfiguratorImpl implements PortalCacheConfigurator {

	@Override
	public void reconfigureCaches(ClassLoader classLoader, URL url)
		throws Exception {

		if (Validator.isNull(url)) {
			return;
		}

		ClassLoader aggregateClassLoader =
			AggregateClassLoader.getAggregateClassLoader(
				new ClassLoader[] {
					ClassLoaderUtil.getPortalClassLoader(), classLoader
				});

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		try {
			ClassLoaderUtil.setContextClassLoader(aggregateClassLoader);

			Collection<PortalCacheManager<? extends Serializable, ?>>
				portalCacheManagers =
					PortalCacheProvider.getPortalCacheManagers();

			for (PortalCacheManager<? extends Serializable, ?>
					portalCacheManager : portalCacheManagers) {

				if (_log.isInfoEnabled()) {
					_log.info(
						"Reconfiguring caches in cache manager " +
							portalCacheManager.getName() + " using " + url);
				}

				portalCacheManager.reconfigureCaches(url);
			}
		}
		finally {
			ClassLoaderUtil.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public void reconfigureHibernateCache(URL url) {
		if (Validator.isNull(url)) {
			return;
		}

		LiferayEhcacheRegionFactory liferayEhcacheRegionFactory =
			SingletonLiferayEhcacheRegionFactory.getInstance();

		if (_log.isInfoEnabled()) {
			_log.info("Reconfiguring Hibernate caches using " + url);
		}

		liferayEhcacheRegionFactory.reconfigureCaches(url);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalCacheConfiguratorImpl.class);

}