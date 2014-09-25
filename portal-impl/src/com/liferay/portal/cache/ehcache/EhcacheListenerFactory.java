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

import net.sf.ehcache.bootstrap.BootstrapCacheLoaderFactory;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;
import net.sf.ehcache.event.CacheManagerEventListenerFactory;

/**
 * @author Tina Tian
 */
public class EhcacheListenerFactory implements ListenerFactory {

	@Override
	public BootstrapLoader createBootstrapLoader(Properties properties) {
		String className = properties.getProperty(
			EhcacheConstants.BOOTSTRAP_CACHE_LOADER_FACTORY_CLASS_NAME);

		if (Validator.isNull(className)) {
			return null;
		}

		BootstrapCacheLoaderFactory<?> bootstrapCacheLoaderFactory =
			_bootstrapCacheLoaderFactories.get(className);

		if (bootstrapCacheLoaderFactory == null) {
			try {
				bootstrapCacheLoaderFactory =
					(BootstrapCacheLoaderFactory<?>)InstanceFactory.newInstance(
						ClassLoaderUtil.getPortalClassLoader(), className);
			}
			catch (Exception e) {
				_log.error(
					"Unable to instantiate bootstrap cache loader factory " +
						className,
					e);

				return null;
			}

			_bootstrapCacheLoaderFactories.put(
				className, bootstrapCacheLoaderFactory);
		}

		return new EhcacheBootstrapLoaderAdapter(
			bootstrapCacheLoaderFactory.createBootstrapCacheLoader(properties));
	}

	@Override
	public CacheListener<? extends Serializable, ?> createCacheListener(
		Properties properties) {

		String className = properties.getProperty(
			EhcacheConstants.CACHE_EVENT_LISTENER_FACTORY_CLASS_NAME);

		if (Validator.isNull(className)) {
			return null;
		}

		CacheEventListenerFactory cacheEventListenerFactory =
			_cacheEventListenerFactories.get(className);

		if (cacheEventListenerFactory == null) {
			try {
				cacheEventListenerFactory =
					(CacheEventListenerFactory)InstanceFactory.newInstance(
						ClassLoaderUtil.getPortalClassLoader(), className);
			}
			catch (Exception e) {
				_log.error(
					"Unable to instantiate cache event listener factory " +
						className,
					e);

				return null;
			}

			_cacheEventListenerFactories.put(
				className, cacheEventListenerFactory);
		}

		CacheEventListener cacheEventListener =
			cacheEventListenerFactory.createCacheEventListener(properties);

		if (cacheEventListener instanceof
				net.sf.ehcache.distribution.CacheReplicator) {

			return
				new EhcacheCacheReplicatorAdapter<Serializable, Serializable>(
					cacheEventListener);
		}

		return new EhcacheCacheListenerAdapter<Serializable, Object>(
			cacheEventListener);
	}

	@Override
	public CacheManagerListener createCacheManagerListener(
		Properties properties) {

		String className = properties.getProperty(
			EhcacheConstants.CACHE_MANAGER_LISTENER_FACTORY_CLASS_NAME);

		if (Validator.isNull(className)) {
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
				"PortalCacheManager with name " + portalCacheManagerName +
					" is not a " + EhcachePortalCacheManager.class.getName());
		}

		EhcachePortalCacheManager<?, ?> ehcachePortalCacheManager =
			(EhcachePortalCacheManager<?, ?>)portalCacheManager;

		CacheManagerEventListenerFactory cacheManagerEventListenerFactory =
			_cacheManagerEventListenerFactories.get(className);

		if (cacheManagerEventListenerFactory == null) {
			try {
				cacheManagerEventListenerFactory =
					(CacheManagerEventListenerFactory)
						InstanceFactory.newInstance(
							ClassLoaderUtil.getPortalClassLoader(), className);
			}
			catch (Exception e) {
				_log.error(
					"Unable to instantiate cache manager event listener " +
						"factory " + className,
					e);

				return null;
			}

			_cacheManagerEventListenerFactories.put(
				className, cacheManagerEventListenerFactory);
		}

		return new EhcacheCacheManagerListenerAdapter(
			cacheManagerEventListenerFactory.createCacheManagerEventListener(
				ehcachePortalCacheManager.getEhcacheManager(), properties));
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