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

import com.liferay.portal.cache.BasePortalCacheManager;
import com.liferay.portal.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.cache.ehcache.internal.configurator.BaseEhcachePortalCacheManagerConfigurator;
import com.liferay.portal.cache.ehcache.internal.event.ConfigurableEhcachePortalCacheListener;
import com.liferay.portal.cache.ehcache.internal.event.PortalCacheManagerEventListener;
import com.liferay.portal.cache.ehcache.internal.management.ManagementService;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.net.URL;

import java.util.Map;

import javax.management.MBeanServer;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.event.CacheManagerEventListenerRegistry;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Joseph Shum
 * @author Raymond Augé
 * @author Michael C. Han
 * @author Shuyang Zhou
 * @author Edward Han
 */
public class EhcachePortalCacheManager<K extends Serializable, V>
	extends BasePortalCacheManager<K, V> {

	public CacheManager getEhcacheManager() {
		return _cacheManager;
	}

	@Override
	public void reconfigurePortalCaches(
		URL configurationURL, ClassLoader classLoader) {

		ObjectValuePair<Configuration, PortalCacheManagerConfiguration>
			configurationObjectValuePair =
				baseEhcachePortalCacheManagerConfigurator.
					getConfigurationObjectValuePair(
						getPortalCacheManagerName(), configurationURL,
						classLoader, _usingDefault);

		reconfigEhcache(configurationObjectValuePair.getKey());

		reconfigPortalCache(configurationObjectValuePair.getValue());
	}

	public void setConfigFile(String configFile) {
		_configFile = configFile;
	}

	public void setDefaultConfigFile(String defaultConfigFile) {
		_defaultConfigFile = defaultConfigFile;
	}

	@Override
	protected PortalCache<K, V> createPortalCache(
		PortalCacheConfiguration portalCacheConfiguration) {

		String portalCacheName = portalCacheConfiguration.getPortalCacheName();

		synchronized (_cacheManager) {
			if (!_cacheManager.cacheExists(portalCacheName)) {
				_cacheManager.addCache(portalCacheName);
			}
		}

		Cache cache = _cacheManager.getCache(portalCacheName);

		EhcachePortalCacheConfiguration ehcachePortalCacheConfiguration =
			(EhcachePortalCacheConfiguration)portalCacheConfiguration;

		if (ehcachePortalCacheConfiguration.isRequireSerialization()) {
			return new SerializableEhcachePortalCache<>(this, cache);
		}

		return new EhcachePortalCache<>(this, cache);
	}

	@Override
	protected void doClearAll() {
		for (String cacheName : _cacheManager.getCacheNames()) {
			Cache cache = _cacheManager.getCache(cacheName);

			if (cache != null) {
				cache.removeAll();
			}
		}
	}

	@Override
	protected void doDestroy() {
		_cacheManager.shutdown();

		if (_configuratorSettingsServiceTracker != null) {
			_configuratorSettingsServiceTracker.close();

			_configuratorSettingsServiceTracker = null;
		}

		if (_mBeanServerServiceTracker != null) {
			_mBeanServerServiceTracker.close();
		}
	}

	@Override
	protected void doRemovePortalCache(String portalCacheName) {
		_cacheManager.removeCache(portalCacheName);
	}

	@Override
	protected PortalCacheManagerConfiguration
		getPortalCacheManagerConfiguration() {

		return _portalCacheManagerConfiguration;
	}

	@Override
	protected void initPortalCacheManager() {
		setTransactionalPortalCacheEnabled(
			GetterUtil.getBoolean(
				props.get(PropsKeys.TRANSACTIONAL_CACHE_ENABLED)));
		setTransactionalPortalCacheNames(
			GetterUtil.getStringValues(
				props.getArray(PropsKeys.TRANSACTIONAL_CACHE_NAMES)));

		if (Validator.isNull(_configFile)) {
			_configFile = _defaultConfigFile;
		}

		ClassLoader classLoader =
			BaseEhcachePortalCacheManagerConfigurator.class.getClassLoader();

		URL configFileURL = classLoader.getResource(_configFile);

		if (configFileURL == null) {
			classLoader = PortalClassLoaderUtil.getClassLoader();

			configFileURL = classLoader.getResource(_configFile);
		}

		_usingDefault = _configFile.equals(_defaultConfigFile);

		ObjectValuePair<Configuration, PortalCacheManagerConfiguration>
			configurationObjectValuePair =
				baseEhcachePortalCacheManagerConfigurator.
					getConfigurationObjectValuePair(
						getPortalCacheManagerName(), configFileURL, classLoader,
						_usingDefault);

		_cacheManager = new CacheManager(configurationObjectValuePair.getKey());

		_portalCacheManagerConfiguration =
			configurationObjectValuePair.getValue();

		CacheManagerEventListenerRegistry cacheManagerEventListenerRegistry =
			_cacheManager.getCacheManagerEventListenerRegistry();

		cacheManagerEventListenerRegistry.registerListener(
			new PortalCacheManagerEventListener(
				aggregatedPortalCacheManagerListener));

		if (!GetterUtil.getBoolean(
				props.get(
					PropsKeys.EHCACHE_PORTAL_CACHE_MANAGER_JMX_ENABLED))) {

			return;
		}

		_mBeanServerServiceTracker =
			new ServiceTracker<MBeanServer, ManagementService>(
				bundleContext, MBeanServer.class, null) {

				@Override
				public ManagementService addingService(
					ServiceReference<MBeanServer> serviceReference) {

					MBeanServer mBeanServer = bundleContext.getService(
						serviceReference);

					ManagementService managementService = new ManagementService(
						_cacheManager, mBeanServer);

					managementService.init();

					return managementService;
				}

				@Override
				public void removedService(
					ServiceReference<MBeanServer> serviceReference,
					ManagementService managementService) {

					managementService.dispose();

					bundleContext.ungetService(serviceReference);
				}

			};

		_mBeanServerServiceTracker.open();
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

				Ehcache ehcache = new Cache(cacheConfiguration);

				_cacheManager.addCache(ehcache);

				PortalCache<K, V> portalCache = portalCaches.get(
					portalCacheName);

				if (portalCache != null) {
					EhcachePortalCache<K, V> ehcachePortalCache =
						(EhcachePortalCache<K, V>)
							EhcacheUnwrapUtil.getWrappedPortalCache(
								portalCache);

					if (ehcachePortalCache != null) {
						ehcachePortalCache.reconfigEhcache(ehcache);
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

	@Override
	protected void removeConfigurableEhcachePortalCacheListeners(
		PortalCache<K, V> portalCache) {

		EhcachePortalCache<K, V> ehcachePortalCache =
			(EhcachePortalCache<K, V>)EhcacheUnwrapUtil.getWrappedPortalCache(
				portalCache);

		Map<PortalCacheListener<K, V>, PortalCacheListenerScope>
			portalCacheListeners = ehcachePortalCache.getPortalCacheListeners();

		for (PortalCacheListener<K, V> portalCacheListener :
				portalCacheListeners.keySet()) {

			if (portalCacheListener instanceof
					ConfigurableEhcachePortalCacheListener) {

				portalCache.unregisterPortalCacheListener(portalCacheListener);
			}
		}
	}

	protected BaseEhcachePortalCacheManagerConfigurator
		baseEhcachePortalCacheManagerConfigurator;
	protected BundleContext bundleContext;
	protected volatile Props props;

	private static final Log _log = LogFactoryUtil.getLog(
		EhcachePortalCacheManager.class);

	private CacheManager _cacheManager;
	private String _configFile;
	private ServiceTracker<?, ?> _configuratorSettingsServiceTracker;
	private String _defaultConfigFile;
	private ServiceTracker<MBeanServer, ManagementService>
		_mBeanServerServiceTracker;
	private PortalCacheManagerConfiguration _portalCacheManagerConfiguration;
	private boolean _usingDefault;

}