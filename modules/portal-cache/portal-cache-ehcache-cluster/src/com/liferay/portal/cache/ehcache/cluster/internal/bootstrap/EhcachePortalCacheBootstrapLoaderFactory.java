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

package com.liferay.portal.cache.ehcache.cluster.internal.bootstrap;

import com.liferay.portal.cache.ehcache.EhcacheConstants;
import com.liferay.portal.kernel.cache.PortalCacheBootstrapLoader;
import com.liferay.portal.kernel.cache.PortalCacheBootstrapLoaderFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;

import net.sf.ehcache.bootstrap.BootstrapCacheLoaderFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = PortalCacheBootstrapLoaderFactory.class)
public class EhcachePortalCacheBootstrapLoaderFactory
	implements PortalCacheBootstrapLoaderFactory {

	@Override
	public PortalCacheBootstrapLoader create(Properties properties) {
		String className = properties.getProperty(
			EhcacheConstants.BOOTSTRAP_CACHE_LOADER_FACTORY_CLASS_NAME);

		if (Validator.isNull(className)) {
			return null;
		}

		try {
			BootstrapCacheLoaderFactory<?> bootstrapCacheLoaderFactory =
				(BootstrapCacheLoaderFactory<?>)InstanceFactory.newInstance(
					getClassLoader(), className);

			return new EhcachePortalCacheBootstrapLoaderAdapter(
				bootstrapCacheLoaderFactory.createBootstrapCacheLoader(
					properties));
		}
		catch (Exception e) {
			_log.error(
				"Unable to instantiate bootstrap cache loader factory " +
					className,
				e);

			return null;
		}
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EhcachePortalCacheBootstrapLoaderFactory.class);

}