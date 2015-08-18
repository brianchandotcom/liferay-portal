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

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.portal.cache.ehcache.EhcacheConstants;
import com.liferay.portal.cache.ehcache.event.EhcachePortalCacheListenerAdapter;
import com.liferay.portal.cache.ehcache.event.EhcachePortalCacheManagerListenerAdapter;
import com.liferay.portal.cache.ehcache.internal.bootstrap.EhcachePortalCacheBootstrapLoaderAdapter;
import com.liferay.portal.cache.ehcache.internal.distribution.EhcachePortalCacheReplicatorAdapter;
import com.liferay.portal.kernel.cache.CallbackFactory;
import com.liferay.portal.kernel.cache.PortalCacheBootstrapLoader;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheManagerListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Properties;

import net.sf.ehcache.bootstrap.BootstrapCacheLoaderFactory;
import net.sf.ehcache.distribution.CacheReplicator;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;
import net.sf.ehcache.event.CacheManagerEventListenerFactory;

/**
 * @author Tina Tian
 */
public class EhcacheCallbackFactory
	implements CallbackFactory<EhcachePortalCacheManager<?, ?>> {

	public static final CallbackFactory<EhcachePortalCacheManager<?, ?>>
		INSTANCE = new EhcacheCallbackFactory();

	@Override
	public PortalCacheBootstrapLoader createPortalCacheBootstrapLoader(
		Properties properties) {

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

	@Override
	public <K extends Serializable, V> PortalCacheListener<K, V>
		createPortalCacheListener(Properties properties) {

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

			if (cacheEventListener instanceof CacheReplicator) {
				return (PortalCacheListener<K, V>)
					new EhcachePortalCacheReplicatorAdapter<K, Serializable>(
						cacheEventListener);
			}

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

	@Override
	public PortalCacheManagerListener createPortalCacheManagerListener(
		EhcachePortalCacheManager<?, ?> ehcachePortalCacheManager,
		Properties properties) {

		String className = properties.getProperty(
			EhcacheConstants.CACHE_MANAGER_LISTENER_FACTORY_CLASS_NAME);

		if (Validator.isNull(className)) {
			return null;
		}

		try {
			CacheManagerEventListenerFactory cacheManagerEventListenerFactory =
				(CacheManagerEventListenerFactory)
					InstanceFactory.newInstance(getClassLoader(), className);

			return new EhcachePortalCacheManagerListenerAdapter(
				cacheManagerEventListenerFactory.
					createCacheManagerEventListener(
						ehcachePortalCacheManager.getEhcacheManager(),
						properties));
		}
		catch (Exception e) {
			_log.error(
				"Unable to instantiate cache manager event listener " +
					"factory " + className,
				e);

			return null;
		}
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	private EhcacheCallbackFactory() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EhcacheCallbackFactory.class);

}