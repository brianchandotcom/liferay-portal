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
import com.liferay.portal.cache.ehcache.EhcacheUnwrapUtil;
import com.liferay.portal.cache.ehcache.internal.configurator.BaseEhcachePortalCacheManagerConfigurator;
import com.liferay.portal.cache.ehcache.internal.event.PortalCacheManagerEventListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.configurator.PortalCacheConfiguratorSettings;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.net.URL;

import java.util.Map;

import javax.management.MBeanServer;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
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
	extends BasePortalCacheManager<K, V> {

	public CacheManager getEhcacheManager() {
		return _cacheManager;
	}

	@Override
	public void reconfigurePortalCaches(URL configurationURL) {
		ObjectValuePair<Configuration, PortalCacheManagerConfiguration>
			configurationObjectValuePair =
				baseEhcachePortalCacheManagerConfigurator.
					getConfigurationObjectValuePair(
						getPortalCacheManagerName(), configurationURL,
						_usingDefault);

		reconfigEhcache(configurationObjectValuePair.getKey());

		reconfigPortalCache(configurationObjectValuePair.getValue());
	}

	public void setConfigFile(String configFile) {
		_configFile = configFile;
	}

	public void setDefaultConfigFile(String defaultConfigFile) {
		_defaultConfigFile = defaultConfigFile;
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

	public void setStopCacheManagerTimer(boolean stopCacheManagerTimer) {
		_stopCacheManagerTimer = stopCacheManagerTimer;
	}

	protected Ehcache createEhcache(
		String portalCacheName, CacheConfiguration cacheConfiguration) {

		if (_cacheManager.cacheExists(portalCacheName)) {
			if (_log.isInfoEnabled()) {
				_log.info("Overriding existing cache " + portalCacheName);
			}

			_cacheManager.removeCache(portalCacheName);
		}

		Cache cache = new Cache(cacheConfiguration);

		_cacheManager.addCache(cache);

		return cache;
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
		_cacheManager.clearAll();
	}

	@Override
	protected void doDestroy() {
		_cacheManager.shutdown();

		if (_managementService != null) {
			_managementService.dispose();
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
		setBlockingPortalCacheAllowed(
			GetterUtil.getBoolean(
				props.get(PropsKeys.EHCACHE_BLOCKING_CACHE_ALLOWED)));
		setPortalCacheBootstrapLoaderEnabled(
			GetterUtil.getBoolean(
				props.get(PropsKeys.EHCACHE_BOOTSTRAP_CACHE_LOADER_ENABLED)));
		setTransactionalPortalCacheEnabled(
			GetterUtil.getBoolean(
				props.get(PropsKeys.TRANSACTIONAL_CACHE_ENABLED)));
		setTransactionalPortalCacheNames(
			GetterUtil.getStringValues(
				props.getArray(PropsKeys.TRANSACTIONAL_CACHE_NAMES)));

		if (Validator.isNull(_configFile)) {
			_configFile = _defaultConfigFile;
		}

		URL configFileURL =
			BaseEhcachePortalCacheManagerConfigurator.class.getResource(
				_configFile);

		if (configFileURL == null) {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			configFileURL = classLoader.getResource(_configFile);
		}

		_usingDefault = _configFile.equals(_defaultConfigFile);

		ObjectValuePair<Configuration, PortalCacheManagerConfiguration>
			configurationObjectValuePair =
				baseEhcachePortalCacheManagerConfigurator.
					getConfigurationObjectValuePair(
						getPortalCacheManagerName(), configFileURL,
						_usingDefault);

		_cacheManager = new CacheManager(configurationObjectValuePair.getKey());

		_portalCacheManagerConfiguration =
			configurationObjectValuePair.getValue();

		if (_stopCacheManagerTimer) {
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
		}

		CacheManagerEventListenerRegistry cacheManagerEventListenerRegistry =
			_cacheManager.getCacheManagerEventListenerRegistry();

		cacheManagerEventListenerRegistry.registerListener(
			new PortalCacheManagerEventListener(
				aggregatedPortalCacheManagerListener));

		if (GetterUtil.getBoolean(
				props.get(
					PropsKeys.EHCACHE_PORTAL_CACHE_MANAGER_JMX_ENABLED))) {

			_managementService = new ManagementService(
				_cacheManager, mBeanServer, _registerCacheManager,
				_registerCaches, _registerCacheConfigurations,
				_registerCacheStatistics);

			_managementService.init();
		}
	}

	protected void reconfigEhcache(Configuration configuration) {
		Map<String, CacheConfiguration> cacheConfigurations =
			configuration.getCacheConfigurations();

		for (CacheConfiguration cacheConfiguration :
				cacheConfigurations.values()) {

			String portalCacheName = cacheConfiguration.getName();

			synchronized (_cacheManager) {
				Ehcache ehcache = createEhcache(
					portalCacheName, cacheConfiguration);

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

	protected boolean reconfigure(
		PortalCacheConfiguratorSettings portalCacheConfiguratorSettings) {

		String portalCacheConfigurationLocation =
			portalCacheConfiguratorSettings.
				getPortalCacheConfigrationLocation();

		if (Validator.isNull(portalCacheConfigurationLocation)) {
			return false;
		}

		ClassLoader classLoader =
			portalCacheConfiguratorSettings.getClassLoader();

		URL url = classLoader.getResource(portalCacheConfigurationLocation);

		if (url == null) {
			return false;
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(
			AggregateClassLoader.getAggregateClassLoader(
				PortalClassLoaderUtil.getClassLoader(),
				portalCacheConfiguratorSettings.getClassLoader()));

		try {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Reconfiguring caches in cache manager " +
						getPortalCacheManagerName() + " using " + url);
			}

			reconfigurePortalCaches(url);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		return true;
	}

	protected BaseEhcachePortalCacheManagerConfigurator
		baseEhcachePortalCacheManagerConfigurator;
	protected MBeanServer mBeanServer;
	protected volatile Props props;

	private static final Log _log = LogFactoryUtil.getLog(
		EhcachePortalCacheManager.class);

	private CacheManager _cacheManager;
	private String _configFile;
	private String _defaultConfigFile;
	private ManagementService _managementService;
	private PortalCacheManagerConfiguration _portalCacheManagerConfiguration;
	private boolean _registerCacheConfigurations = true;
	private boolean _registerCacheManager = true;
	private boolean _registerCaches = true;
	private boolean _registerCacheStatistics = true;
	private boolean _stopCacheManagerTimer = true;
	private boolean _usingDefault;

}