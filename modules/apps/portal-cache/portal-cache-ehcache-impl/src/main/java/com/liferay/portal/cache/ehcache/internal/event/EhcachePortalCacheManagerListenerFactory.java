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

package com.liferay.portal.cache.ehcache.internal.event;

import com.liferay.portal.cache.PortalCacheManagerListenerFactory;
import com.liferay.portal.cache.ehcache.internal.EhcacheConstants;
import com.liferay.portal.cache.ehcache.internal.EhcachePortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerListener;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;

import net.sf.ehcache.event.CacheManagerEventListenerFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = PortalCacheManagerListenerFactory.class)
public class EhcachePortalCacheManagerListenerFactory
	implements PortalCacheManagerListenerFactory
		<EhcachePortalCacheManager<?, ?>> {

	@Override
	public PortalCacheManagerListener create(
		EhcachePortalCacheManager<?, ?> ehcachePortalCacheManager,
		Properties properties) {

		String className = (String)properties.remove(
			EhcacheConstants.
				CACHE_MANAGER_LISTENER_PROPERTIES_KEY_FACTORY_CLASS_NAME);

		if (Validator.isNull(className)) {
			return null;
		}

		ClassLoader classLoader = (ClassLoader)properties.remove(
			EhcacheConstants.
				CACHE_MANAGER_LISTENER_PROPERTIES_KEY_FACTORY_CLASS_LOADER);

		if (classLoader == null) {
			return null;
		}

		try {
			CacheManagerEventListenerFactory cacheManagerEventListenerFactory =
				(CacheManagerEventListenerFactory)InstanceFactory.newInstance(
					classLoader, className);

			return new EhcachePortalCacheManagerListenerAdapter(
				cacheManagerEventListenerFactory.
					createCacheManagerEventListener(
						ehcachePortalCacheManager.getEhcacheManager(),
						properties));
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to instantiate cache manager event listener " +
					className,
				e);
		}
	}

}