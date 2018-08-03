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

package com.liferay.portal.cache.ehcache.multiple.internal.distribution;

import com.liferay.portal.cache.PortalCacheReplicator;
import com.liferay.portal.cache.PortalCacheReplicatorFactory;
import com.liferay.portal.cache.ehcache.multiple.configuration.EhcacheMultipleConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.InstanceFactory;

import java.io.Serializable;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.cache.ehcache.multiple.configuration.EhcacheMultipleConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = PortalCacheReplicatorFactory.class
)
public class EhcachePortalCacheReplicatorFactory
	implements PortalCacheReplicatorFactory {

	@Override
	public <K extends Serializable, V extends Serializable>
		PortalCacheReplicator<K, V> create(Properties properties) {

		String factoryClassName =
			_ehcacheMultipleConfiguration.cacheReplicatorFactoryClass();

		try {
			CacheEventListenerFactory cacheEventListenerFactory =
				(CacheEventListenerFactory)InstanceFactory.newInstance(
					getClassLoader(), factoryClassName);

			CacheEventListener cacheEventListener =
				cacheEventListenerFactory.createCacheEventListener(properties);

			return new EhcachePortalCacheReplicatorAdapter<>(
				cacheEventListener);
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to instantiate cache event listener factory " +
					factoryClassName,
				e);
		}
	}

	@Activate
	@Modified
	protected void activate(ComponentContext componentContext) {
		_ehcacheMultipleConfiguration = ConfigurableUtil.createConfigurable(
			EhcacheMultipleConfiguration.class,
			componentContext.getProperties());
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	private volatile EhcacheMultipleConfiguration _ehcacheMultipleConfiguration;

}