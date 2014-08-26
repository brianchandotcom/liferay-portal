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

import com.liferay.portal.cache.transactional.TransactionalPortalCache;
import com.liferay.portal.kernel.cache.BlockingPortalCache;
import com.liferay.portal.kernel.cache.CacheManagerListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheProvider;
import com.liferay.portal.kernel.cache.PortalCacheWrapper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.net.URL;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServer;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.event.CacheManagerEventListener;
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
	implements PortalCacheManager<K, V> {

	public void afterPropertiesSet() {
		if ((_cacheManager != null) || (_mpiOnly && SPIUtil.isSPI())) {
			return;
		}

		String configurationPath = PropsUtil.get(_configPropertyKey);

		if (Validator.isNull(configurationPath)) {
			configurationPath = _DEFAULT_CLUSTERED_EHCACHE_CONFIG_FILE;
		}

		_usingDefault = configurationPath.equals(
			_DEFAULT_CLUSTERED_EHCACHE_CONFIG_FILE);

		Configuration configuration = EhcacheConfigurationUtil.getConfiguration(
			configurationPath, _clusterAware, _usingDefault);

		_cacheManager = CacheManagerUtil.createCacheManager(configuration);

		_name = _cacheManager.getName();

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

		if (PropsValues.EHCACHE_PORTAL_CACHE_MANAGER_JMX_ENABLED) {
			_managementService = new ManagementService(
				_cacheManager, _mBeanServer, _registerCacheManager,
				_registerCaches, _registerCacheConfigurations,
				_registerCacheStatistics);

			_managementService.init();
		}

		PortalCacheProvider.registerPortalCacheManager(this);
	}

	@Override
	public void clearAll() {
		_cacheManager.clearAll();
	}

	@Override
	public void destroy() {
		try {
			PortalCacheProvider.unregisterPortalCacheManager(
				_cacheManager.getName());

			_portalCaches.clear();

			_cacheManager.shutdown();
		}
		finally {
			if (_managementService != null) {
				_managementService.dispose();
			}
		}
	}

	@Override
	public PortalCache<K, V> getCache(String name) {
		return getCache(name, false);
	}

	@Override
	public PortalCache<K, V> getCache(String name, boolean blocking) {
		PortalCache<K, V> portalCache = _portalCaches.get(name);

		if (portalCache == null) {
			synchronized (_cacheManager) {
				portalCache = _portalCaches.get(name);

				if (portalCache == null) {
					if (!_cacheManager.cacheExists(name)) {
						_cacheManager.addCache(name);
					}

					Cache cache = _cacheManager.getCache(name);

					portalCache = new EhcachePortalCache<K, V>(this, cache);

					if (PropsValues.TRANSACTIONAL_CACHE_ENABLED &&
						isTransactionalPortalCache(name)) {

						portalCache = new TransactionalPortalCache<K, V>(
							portalCache);
					}

					if (PropsValues.EHCACHE_BLOCKING_CACHE_ALLOWED &&
						blocking) {

						portalCache = new BlockingPortalCache<K, V>(
							portalCache);
					}

					_portalCaches.put(name, portalCache);
				}
			}
		}

		return portalCache;
	}

	@Override
	public Set<CacheManagerListener> getCacheManagerListeners() {
		Set<CacheManagerListener> cacheManagerListeners =
			new HashSet<CacheManagerListener>();

		CacheManagerEventListenerRegistry cacheManagerEventListenerRegistry =
			_cacheManager.getCacheManagerEventListenerRegistry();

		Set<CacheManagerEventListener> cacheManagerEventListeners =
			cacheManagerEventListenerRegistry.getRegisteredListeners();

		for (CacheManagerEventListener cacheManagerEventListener :
				cacheManagerEventListeners) {

			if (!(cacheManagerEventListener instanceof
					PortalCacheManagerEventListener)) {

				continue;
			}

			PortalCacheManagerEventListener portalCacheManagerEventListener =
				(PortalCacheManagerEventListener)cacheManagerEventListener;

			cacheManagerListeners.add(
				portalCacheManagerEventListener.getCacheManagerListener());
		}

		return cacheManagerListeners;
	}

	public CacheManager getEhcacheManager() {
		return _cacheManager;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public boolean isClusterAware() {
		return _clusterAware;
	}

	@Override
	public void reconfigureCaches(URL configurationURL) {
		Configuration configuration = EhcacheConfigurationUtil.getConfiguration(
			configurationURL, _clusterAware, _usingDefault);

		if (!_name.equals(configuration.getName())) {
			return;
		}

		Map<String, CacheConfiguration> cacheConfigurations =
			configuration.getCacheConfigurations();

		for (CacheConfiguration cacheConfiguration :
				cacheConfigurations.values()) {

			replaceCache(new Cache(cacheConfiguration));
		}
	}

	@Override
	public boolean registerCacheManagerListener(
		CacheManagerListener cacheManagerListener) {

		CacheManagerEventListenerRegistry cacheManagerEventListenerRegistry =
			_cacheManager.getCacheManagerEventListenerRegistry();

		return cacheManagerEventListenerRegistry.registerListener(
			new PortalCacheManagerEventListener(cacheManagerListener));
	}

	@Override
	public void removeCache(String name) {
		_cacheManager.removeCache(name);
		_portalCaches.remove(name);
	}

	public void setClusterAware(boolean clusterAware) {
		_clusterAware = clusterAware;
	}

	public void setConfigPropertyKey(String configPropertyKey) {
		_configPropertyKey = configPropertyKey;
	}

	public void setMBeanServer(MBeanServer mBeanServer) {
		_mBeanServer = mBeanServer;
	}

	public void setMpiOnly(boolean mpiOnly) {
		_mpiOnly = mpiOnly;
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
	public boolean unregisterCacheManagerListener(
		CacheManagerListener cacheManagerListener) {

		CacheManagerEventListenerRegistry cacheManagerEventListenerRegistry =
			_cacheManager.getCacheManagerEventListenerRegistry();

		return cacheManagerEventListenerRegistry.unregisterListener(
			new PortalCacheManagerEventListener(cacheManagerListener));
	}

	@Override
	public void unregisterCacheManagerListeners() {
		CacheManagerEventListenerRegistry cacheManagerEventListenerRegistry =
			_cacheManager.getCacheManagerEventListenerRegistry();

		Set<CacheManagerEventListener> cacheManagerEventListeners =
			cacheManagerEventListenerRegistry.getRegisteredListeners();

		for (CacheManagerEventListener cacheManagerEventListener :
				cacheManagerEventListeners) {

			if (!(cacheManagerEventListener instanceof
					PortalCacheManagerEventListener)) {

				continue;
			}

			cacheManagerEventListenerRegistry.unregisterListener(
				cacheManagerEventListener);
		}
	}

	protected boolean isTransactionalPortalCache(String name) {
		for (String namePattern : PropsValues.TRANSACTIONAL_CACHE_NAMES) {
			if (StringUtil.wildcardMatches(
					name, namePattern, CharPool.QUESTION, CharPool.STAR,
					CharPool.PERCENT, true)) {

				return true;
			}
		}

		return false;
	}

	protected void replaceCache(Cache cache) {
		String name = cache.getName();

		synchronized (_cacheManager) {
			if (_cacheManager.cacheExists(name)) {
				if (_log.isInfoEnabled()) {
					_log.info("Overriding existing cache " + name);
				}

				_cacheManager.removeCache(name);
			}

			_cacheManager.addCache(cache);

			EhcachePortalCache<K, V> ehcachePortalCache = getEhcachePortalCache(
				_portalCaches.get(name));

			if (ehcachePortalCache != null) {
				ehcachePortalCache.setEhcache(cache);
			}
		}
	}

	private EhcachePortalCache<K, V> getEhcachePortalCache(
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

	private static Log _log = LogFactoryUtil.getLog(
		EhcachePortalCacheManager.class);

	private CacheManager _cacheManager;
	private boolean _clusterAware;
	private String _configPropertyKey;
	private ManagementService _managementService;
	private MBeanServer _mBeanServer;
	private boolean _mpiOnly;
	private String _name;
	private Map<String, PortalCache<K, V>> _portalCaches =
		new HashMap<String, PortalCache<K, V>>();
	private boolean _registerCacheConfigurations = true;
	private boolean _registerCacheManager = true;
	private boolean _registerCaches = true;
	private boolean _registerCacheStatistics = true;
	private boolean _usingDefault;

}