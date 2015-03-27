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

import com.liferay.portal.cache.AbstractPortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheWrapper;
import com.liferay.portal.kernel.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.net.URL;

import java.util.Map;

import javax.management.MBeanServer;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.event.CacheManagerEventListenerRegistry;
import net.sf.ehcache.management.ManagementService;
import net.sf.ehcache.util.FailSafeTimer;

/**
 * @author Joseph Shum
 * @author Raymond Augé
 * @author Michael C. Han
 * @author Shuyang Zhou
 * @author Edward Han
 */
public class EhcachePortalCacheManager<K extends Serializable, V>
	extends AbstractPortalCacheManager<K, V> {

	public CacheManager getEhcacheManager() {
		return _cacheManager;
	}

	@Override
	public void reconfigureCaches(URL configurationURL) {
		_configurationPair = EhcacheConfigurationHelperUtil.getConfiguration(
			configurationURL, clusterAware, _usingDefault);

		reconfigEhcache(_configurationPair.getKey());

		reconfigPortalCache(_configurationPair.getValue());
	}

	public void setConfigPropertyKey(String configPropertyKey) {
		_configPropertyKey = configPropertyKey;
	}

	public void setRegisterCacheConfigurations(
		boolean registerCacheConfigurations) {

		_registerCacheConfigurations = registerCacheConfigurations;
	}

	public void setRegisterCacheManager(boolean registerCacheManager) {
		_registerCacheManager = registerCacheManager;
	}

	public void setRegisterCaches(boolean registerCaches) {
		_registerCaches = registerCaches;
	}

	public void setRegisterCacheStatistics(boolean registerCacheStatistics) {
		_registerCacheStatistics = registerCacheStatistics;
	}

	@Override
	protected PortalCache<K, V> createPortalCache(String cacheName) {
		synchronized (_cacheManager) {
			if (!_cacheManager.cacheExists(cacheName)) {
				_cacheManager.addCache(cacheName);
			}
		}

		Cache cache = _cacheManager.getCache(cacheName);

		return new EhcachePortalCache<>(this, cache);
	}

	@Override
	protected void doClearAll() {
		_cacheManager.clearAll();
	}

	@Override
	protected void doDestroy() {
		try {
			_cacheManager.shutdown();
		}
		finally {
			if (_managementService != null) {
				_managementService.dispose();
			}
		}

		_serviceTracker.close();
	}

	@Override
	protected void doRemoveCache(String cacheName) {
		_cacheManager.removeCache(cacheName);
	}

	@Override
	protected PortalCacheManagerConfiguration
		getPortalCacheManagerConfiguration() {

		return _configurationPair.getValue();
	}

	@Override
	protected void initPortalCacheManager() {
		String configurationPath = PropsUtil.get(_configPropertyKey);

		if (Validator.isNull(configurationPath)) {
			configurationPath = _DEFAULT_CLUSTERED_EHCACHE_CONFIG_FILE;
		}

		_usingDefault = configurationPath.equals(
			_DEFAULT_CLUSTERED_EHCACHE_CONFIG_FILE);

		_configurationPair = EhcacheConfigurationHelperUtil.getConfiguration(
			configurationPath, clusterAware, _usingDefault);

		_cacheManager = new CacheManager(_configurationPair.getKey());

		_cacheManager.setName(name);

		FailSafeTimer failSafeTimer = _cacheManager.getTimer();

		failSafeTimer.cancel();

		try {
			Field cacheManagerTimerField = ReflectionUtil.getDeclaredField(
				CacheManager.class, "cacheManagerTimer");

			cacheManagerTimerField.set(_cacheManager, null);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		CacheManagerEventListenerRegistry cacheManagerEventListenerRegistry =
			_cacheManager.getCacheManagerEventListenerRegistry();

		cacheManagerEventListenerRegistry.registerListener(
			new PortalCacheManagerEventListener(
				aggregatedCacheManagerListener));

		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			MBeanServer.class, new MBeanServerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	protected void reconfigEhcache(Configuration configuration) {
		Map<String, CacheConfiguration> cacheConfigurations =
			configuration.getCacheConfigurations();

		for (CacheConfiguration cacheConfiguration :
				cacheConfigurations.values()) {

			String portalCacheName = cacheConfiguration.getName();

			synchronized (_cacheManager) {
				if (_cacheManager.cacheExists(portalCacheName)) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Overriding existing cache " + portalCacheName);
					}

					_cacheManager.removeCache(portalCacheName);
				}

				Cache cache = new Cache(cacheConfiguration);

				_cacheManager.addCache(cache);

				PortalCache<K, V> portalCache = portalCaches.get(
					portalCacheName);

				if (portalCache != null) {
					EhcachePortalCache<K, V> ehcachePortalCache =
						_getEhcachePortalCache(portalCache);

					if (ehcachePortalCache != null) {
						ehcachePortalCache.reconfigEhcache(cache);
					}
					else {
						_log.error(
							"Unable to reconfigure cache with name " +
								portalCacheName);
					}
				}
			}
		}
	}

	private EhcachePortalCache<K, V> _getEhcachePortalCache(
		PortalCache<K, V> portalCache) {

		while (portalCache instanceof PortalCacheWrapper) {
			PortalCacheWrapper<K, V> portalCacheWrapper =
				(PortalCacheWrapper<K, V>)portalCache;

			portalCache = portalCacheWrapper.getWrappedPortalCache();
		}

		if (portalCache instanceof EhcachePortalCache) {
			return (EhcachePortalCache<K, V>)portalCache;
		}

		return null;
	}

	private static final String _DEFAULT_CLUSTERED_EHCACHE_CONFIG_FILE =
		"/ehcache/liferay-multi-vm-clustered.xml";

	private static final Log _log = LogFactoryUtil.getLog(
		EhcachePortalCacheManager.class);

	private CacheManager _cacheManager;
	private String _configPropertyKey;
	private ObjectValuePair<Configuration, PortalCacheManagerConfiguration>
		_configurationPair;
	private ManagementService _managementService;
	private boolean _registerCacheConfigurations = true;
	private boolean _registerCacheManager = true;
	private boolean _registerCaches = true;
	private boolean _registerCacheStatistics = true;
	private ServiceTracker <MBeanServer, MBeanServer> _serviceTracker;
	private boolean _usingDefault;

	private class MBeanServerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<MBeanServer, MBeanServer> {

		@Override
		public MBeanServer addingService(
			ServiceReference<MBeanServer> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			MBeanServer mBeanServer = registry.getService(serviceReference);

			if (PropsValues.EHCACHE_PORTAL_CACHE_MANAGER_JMX_ENABLED) {
				_managementService = new ManagementService(
					_cacheManager, mBeanServer, _registerCacheManager,
					_registerCaches, _registerCacheConfigurations,
					_registerCacheStatistics);

				_managementService.init();
			}

			return mBeanServer;
		}

		@Override
		public void modifiedService(
			ServiceReference<MBeanServer> serviceReference,
			MBeanServer mBeanServer) {
		}

		@Override
		public void removedService(
			ServiceReference<MBeanServer> serviceReference,
			MBeanServer mBeanServer) {
		}

	}

}