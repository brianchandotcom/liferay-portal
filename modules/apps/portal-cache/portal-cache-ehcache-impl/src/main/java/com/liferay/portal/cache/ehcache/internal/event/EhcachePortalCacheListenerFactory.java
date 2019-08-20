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

import com.liferay.portal.cache.PortalCacheListenerFactory;
import com.liferay.portal.cache.PortalCacheReplicator;
import com.liferay.portal.cache.PortalCacheReplicatorFactory;
import com.liferay.portal.cache.ehcache.internal.EhcacheConstants;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.exception.SystemException;
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
			PortalCacheListener<K, V> portalCacheListener =
				(PortalCacheListener<K, V>)_portalCacheReplicatorFactory.create(
					properties);

			if (portalCacheListener == null) {
				return null;
			}

			return (PortalCacheListener<K, V>)
				new EhcachePortalCacheReplicator<>(
					(PortalCacheReplicator<K, Serializable>)
						portalCacheListener);
		}

		String className = (String)properties.remove(
			EhcacheConstants.CACHE_LISTENER_PROPERTIES_KEY_FACTORY_CLASS_NAME);

		if (Validator.isNull(className)) {
			return null;
		}

		ClassLoader classLoader = (ClassLoader)properties.remove(
			EhcacheConstants.
				CACHE_LISTENER_PROPERTIES_KEY_FACTORY_CLASS_LOADER);

		if (classLoader == null) {
			return null;
		}

		try {
			CacheEventListenerFactory cacheEventListenerFactory =
				(CacheEventListenerFactory)InstanceFactory.newInstance(
					classLoader, className);

			CacheEventListener cacheEventListener =
				cacheEventListenerFactory.createCacheEventListener(properties);

			return new EhcachePortalCacheListenerAdapter<>(cacheEventListener);
		}
		catch (Exception e) {
			throw new SystemException(
				"Unable to instantiate cache event listener factory " +
					className,
				e);
		}
	}

	@Reference(unbind = "-")
	protected void setPortalCacheReplicatorFactory(
		PortalCacheReplicatorFactory portalCacheReplicatorFactory) {

		_portalCacheReplicatorFactory = portalCacheReplicatorFactory;
	}

	private PortalCacheReplicatorFactory _portalCacheReplicatorFactory;

	private class EhcachePortalCacheReplicator
		<K extends Serializable, V extends Serializable>
			implements PortalCacheReplicator<K, V>,
					   ConfigurableEhcachePortalCacheListener {

		@Override
		public void dispose() {
			_portalCacheReplicator.dispose();
		}

		@Override
		public void notifyEntryEvicted(
				PortalCache<K, V> portalCache, K key, V value, int timeToLive)
			throws PortalCacheException {

			_portalCacheReplicator.notifyEntryEvicted(
				portalCache, key, value, timeToLive);
		}

		@Override
		public void notifyEntryExpired(
				PortalCache<K, V> portalCache, K key, V value, int timeToLive)
			throws PortalCacheException {

			_portalCacheReplicator.notifyEntryExpired(
				portalCache, key, value, timeToLive);
		}

		@Override
		public void notifyEntryPut(
				PortalCache<K, V> portalCache, K key, V value, int timeToLive)
			throws PortalCacheException {

			_portalCacheReplicator.notifyEntryPut(
				portalCache, key, value, timeToLive);
		}

		@Override
		public void notifyEntryRemoved(
				PortalCache<K, V> portalCache, K key, V value, int timeToLive)
			throws PortalCacheException {

			_portalCacheReplicator.notifyEntryRemoved(
				portalCache, key, value, timeToLive);
		}

		@Override
		public void notifyEntryUpdated(
				PortalCache<K, V> portalCache, K key, V value, int timeToLive)
			throws PortalCacheException {

			_portalCacheReplicator.notifyEntryUpdated(
				portalCache, key, value, timeToLive);
		}

		@Override
		public void notifyRemoveAll(PortalCache<K, V> portalCache)
			throws PortalCacheException {

			_portalCacheReplicator.notifyRemoveAll(portalCache);
		}

		private EhcachePortalCacheReplicator(
			PortalCacheReplicator<K, V> portalCacheReplicator) {

			_portalCacheReplicator = portalCacheReplicator;
		}

		private final PortalCacheReplicator<K, V> _portalCacheReplicator;

	}

}