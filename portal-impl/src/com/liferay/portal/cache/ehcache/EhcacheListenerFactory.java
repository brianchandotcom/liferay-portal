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

package com.liferay.portal.cache.ehcache;

import com.liferay.portal.kernel.cache.BootstrapLoader;
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheManagerListener;
import com.liferay.portal.kernel.cache.ListenerFactory;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.ClassLoaderUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.bootstrap.BootstrapCacheLoaderFactory;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;
import net.sf.ehcache.event.CacheManagerEventListener;
import net.sf.ehcache.event.CacheManagerEventListenerFactory;

/**
 * @author Tina Tian
 */
public class EhcacheListenerFactory implements ListenerFactory {

	@Override
	public BootstrapLoader createBootstrapLoader(Properties properties) {
		String factoryClassName = properties.getProperty(
			EhcacheConstants.BOOTSTRAP_CACHE_LOADER_FACTORY_CLASS_NAME);

		if (Validator.isNull(factoryClassName)) {
			return null;
		}

		try {
			BootstrapCacheLoaderFactory<?> bootstrapCacheLoaderFactory =
				_bootstrapCacheLoaderFactories.get(factoryClassName);

			if (bootstrapCacheLoaderFactory == null) {
				bootstrapCacheLoaderFactory =
					(BootstrapCacheLoaderFactory)InstanceFactory.newInstance(
						ClassLoaderUtil.getPortalClassLoader(),
						factoryClassName);

				_bootstrapCacheLoaderFactories.put(
					factoryClassName, bootstrapCacheLoaderFactory);
			}

			BootstrapCacheLoader bootstrapCacheLoader =
				bootstrapCacheLoaderFactory.createBootstrapCacheLoader(
					properties);

			return new EhcacheBootstrapLoaderAdapter(bootstrapCacheLoader);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public CacheListener<? extends Serializable, ?> createCacheListener(
		Properties properties) {

		String factoryClassName = properties.getProperty(
			EhcacheConstants.CACHE_EVENT_LISTENER_FACTORY_CLASS_NAME);

		if (Validator.isNull(factoryClassName)) {
			return null;
		}

		try {
			CacheEventListenerFactory cacheEventListenerFactory =
				_cacheEventListenerFactories.get(factoryClassName);

			if (cacheEventListenerFactory == null) {
				cacheEventListenerFactory =
					(CacheEventListenerFactory)InstanceFactory.newInstance(
						ClassLoaderUtil.getPortalClassLoader(),
						factoryClassName);

				_cacheEventListenerFactories.put(
					factoryClassName, cacheEventListenerFactory);
			}

			CacheEventListener cacheEventListener =
				cacheEventListenerFactory.createCacheEventListener(properties);

			if (cacheEventListener instanceof
					net.sf.ehcache.distribution.CacheReplicator) {

				return new EhcacheCacheReplicatorAdapter(cacheEventListener);
			}
			else {
				return new EhcacheCacheListenerAdapter(cacheEventListener);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public CacheManagerListener createCacheManagerListener(
		Properties properties) {

		String factoryClassName = properties.getProperty(
			EhcacheConstants.CACHE_MANAGER_LISTENER_FACTORY_CLASS_NAME);

		if (Validator.isNull(factoryClassName)) {
			return null;
		}

		String portalCacheManagerName = properties.getProperty(
			EhcacheConstants.PORTAL_CACHE_MANAGER_NAME);

		if (Validator.isNull(portalCacheManagerName)) {
			return null;
		}

		PortalCacheManager<?, ?> portalCacheManager =
			PortalCacheProvider.getPortalCacheManager(portalCacheManagerName);

		if (!(portalCacheManager instanceof EhcachePortalCacheManager)) {
			throw new IllegalArgumentException(
				"PortalCacheManager is not a " +
					EhcachePortalCacheManager.class.getName());
		}

		EhcachePortalCacheManager<?, ?> ehcachePortalCacheManager =
			(EhcachePortalCacheManager<?, ?>)portalCacheManager;

		try {
			CacheManagerEventListenerFactory cacheManagerEventListenerFactory =
				_cacheManagerEventListenerFactories.get(factoryClassName);

			if (cacheManagerEventListenerFactory == null) {
				cacheManagerEventListenerFactory =
					(CacheManagerEventListenerFactory)
						InstanceFactory.newInstance(
							ClassLoaderUtil.getPortalClassLoader(),
							factoryClassName);

				_cacheManagerEventListenerFactories.put(
					factoryClassName, cacheManagerEventListenerFactory);
			}

			CacheManagerEventListener cacheManagerEventListener =
				cacheManagerEventListenerFactory.
					createCacheManagerEventListener(
						ehcachePortalCacheManager.getEhcacheManager(),
						properties);

			return new EhcacheCacheManagerListenerAdapter(
				cacheManagerEventListener);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	private static Log _log = LogFactoryUtil.getLog(
		EhcacheListenerFactory.class);

	private static final Map<String, BootstrapCacheLoaderFactory<?>>
		_bootstrapCacheLoaderFactories =
			new ConcurrentHashMap<String, BootstrapCacheLoaderFactory<?>>();
	private static final Map<String, CacheEventListenerFactory>
		_cacheEventListenerFactories =
			new ConcurrentHashMap<String, CacheEventListenerFactory>();
	private static final Map<String, CacheManagerEventListenerFactory>
		_cacheManagerEventListenerFactories =
			new ConcurrentHashMap<String, CacheManagerEventListenerFactory>();

}