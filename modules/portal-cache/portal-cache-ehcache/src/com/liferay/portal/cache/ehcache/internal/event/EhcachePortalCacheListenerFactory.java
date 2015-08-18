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

import com.liferay.portal.cache.ehcache.EhcacheConstants;
import com.liferay.portal.cache.ehcache.event.EhcachePortalCacheListenerAdapter;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerFactory;
import com.liferay.portal.kernel.cache.PortalCacheReplicator;
import com.liferay.portal.kernel.cache.PortalCacheReplicatorFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = PortalCacheListenerFactory.class)
public class EhcachePortalCacheListenerFactory
	implements PortalCacheListenerFactory {

	@Override
	public <K extends Serializable, V> PortalCacheListener<K, V> create(
		Properties properties) {

		boolean replicator = GetterUtil.getBoolean(
			properties.get(PortalCacheReplicator.REPLICATOR));

		if (replicator) {
			return
				(PortalCacheListener<K, V>)_portalCacheReplicatorFactory.create(
					properties);
		}

		String className = properties.getProperty(
			EhcacheConstants.CACHE_EVENT_LISTENER_FACTORY_CLASS_NAME);

		if (Validator.isNull(className)) {
			return null;
		}

		try {
			CacheEventListenerFactory cacheEventListenerFactory =
				(CacheEventListenerFactory)InstanceFactory.newInstance(
					getClassLoader(), className);

			CacheEventListener cacheEventListener =
				cacheEventListenerFactory.createCacheEventListener(properties);

			return new EhcachePortalCacheListenerAdapter<>(cacheEventListener);
		}
		catch (Exception e) {
			_log.error(
				"Unable to instantiate cache event listener factory " +
					className,
				e);

			return null;
		}
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	@Reference(unbind = "-")
	protected void setPortalCacheReplicatorFactory(
		PortalCacheReplicatorFactory portalCacheReplicatorFactory) {

		_portalCacheReplicatorFactory = portalCacheReplicatorFactory;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EhcachePortalCacheListenerFactory.class);

	private volatile PortalCacheReplicatorFactory _portalCacheReplicatorFactory;

}