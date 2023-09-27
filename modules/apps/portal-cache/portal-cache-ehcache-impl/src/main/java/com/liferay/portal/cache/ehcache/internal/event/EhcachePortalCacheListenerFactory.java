/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.event;

import com.liferay.portal.cache.PortalCacheListenerFactory;
import com.liferay.portal.cache.PortalCacheReplicator;
import com.liferay.portal.cache.PortalCacheReplicatorFactory;
import com.liferay.portal.cache.ehcache.internal.constants.EhcacheConstants;
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
@Component(service = PortalCacheListenerFactory.class)
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
		catch (Exception exception) {
			throw new SystemException(
				"Unable to instantiate cache event listener factory " +
					className,
				exception);
		}
	}

	@Reference
	private PortalCacheReplicatorFactory _portalCacheReplicatorFactory;

	private class EhcachePortalCacheReplicator
		<K extends Serializable, V extends Serializable>
			implements ConfigurableEhcachePortalCacheListener,
					   PortalCacheReplicator<K, V> {

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