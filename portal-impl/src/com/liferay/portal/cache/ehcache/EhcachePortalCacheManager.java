/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
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
import java.util.Map;

import javax.management.MBeanServer;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
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
	}

	@Override
	public void clearAll() {
		_cacheManager.clearAll();
	}

	@Override
	public void destroy() {
		try {
			_ehcachePortalCaches.clear();
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
					portalCache = addCache(name, null);

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

	public CacheManager getEhcacheManager() {
		return _cacheManager;
	}

	@Override
	public void reconfigureCaches(URL configurationURL) {
		Configuration configuration = EhcacheConfigurationUtil.getConfiguration(
			configurationURL, _clusterAware, _usingDefault);

		Map<String, CacheConfiguration> cacheConfigurations =
			configuration.getCacheConfigurations();

		for (CacheConfiguration cacheConfiguration :
				cacheConfigurations.values()) {

			Cache cache = new Cache(cacheConfiguration);

			PortalCache<K, V> portalCache = addCache(cache.getName(), cache);

			if (portalCache == null) {
				_log.error(
					"Failed to override cache " + cacheConfiguration.getName());
			}
		}
	}

	@Override
	public void removeCache(String name) {
		_cacheManager.removeCache(name);
		_ehcachePortalCaches.remove(name);
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

	protected PortalCache<K, V> addCache(String name, Cache cache) {
		EhcachePortalCache<K, V> ehcachePortalCache = null;

		synchronized (_cacheManager) {
			if ((cache != null) && _cacheManager.cacheExists(name)) {
				if (_log.isInfoEnabled()) {
					_log.info("Overriding existing cache " + name);
				}

				_cacheManager.removeCache(name);
			}

			if (cache == null) {
				if (!_cacheManager.cacheExists(name)) {
					_cacheManager.addCache(name);
				}
			}
			else {
				_cacheManager.addCache(cache);
			}

			Ehcache ehcache = _cacheManager.getEhcache(name);

			if (ehcache == null) {
				return null;
			}

			ehcachePortalCache = _ehcachePortalCaches.get(name);

			if (ehcachePortalCache == null) {
				ehcachePortalCache = new EhcachePortalCache<K, V>(ehcache);

				_ehcachePortalCaches.put(name, ehcachePortalCache);
			}
			else {
				ehcachePortalCache.setEhcache(ehcache);
			}
		}

		return ehcachePortalCache;
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

	private static final String _DEFAULT_CLUSTERED_EHCACHE_CONFIG_FILE =
		"/ehcache/liferay-multi-vm-clustered.xml";

	private static Log _log = LogFactoryUtil.getLog(
		EhcachePortalCacheManager.class);

	private CacheManager _cacheManager;
	private boolean _clusterAware;
	private String _configPropertyKey;
	private Map<String, EhcachePortalCache<K, V>> _ehcachePortalCaches =
		new HashMap<String, EhcachePortalCache<K, V>>();
	private ManagementService _managementService;
	private MBeanServer _mBeanServer;
	private boolean _mpiOnly;
	private Map<String, PortalCache<K, V>> _portalCaches =
		new HashMap<String, PortalCache<K, V>>();
	private boolean _registerCacheConfigurations = true;
	private boolean _registerCacheManager = true;
	private boolean _registerCaches = true;
	private boolean _registerCacheStatistics = true;
	private boolean _usingDefault;

}