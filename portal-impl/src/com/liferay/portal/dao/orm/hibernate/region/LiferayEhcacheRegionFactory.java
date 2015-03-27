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

package com.liferay.portal.dao.orm.hibernate.region;

import com.liferay.portal.cache.ehcache.ModifiableEhcacheWrapper;
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;
import com.liferay.portal.kernel.cache.CacheManagerListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.cache.PortalCacheProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.net.URL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.management.MBeanServer;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.hibernate.EhCacheRegionFactory;
import net.sf.ehcache.hibernate.regions.EhcacheCollectionRegion;
import net.sf.ehcache.hibernate.regions.EhcacheEntityRegion;
import net.sf.ehcache.hibernate.regions.EhcacheQueryResultsRegion;
import net.sf.ehcache.hibernate.regions.EhcacheTimestampsRegion;
import net.sf.ehcache.management.ManagementService;
import net.sf.ehcache.util.FailSafeTimer;

import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.QueryResultsRegion;
import org.hibernate.cache.TimestampsRegion;
import org.hibernate.cfg.Settings;

/**
 * @author Edward Han
 */
public class LiferayEhcacheRegionFactory extends EhCacheRegionFactory {

	public LiferayEhcacheRegionFactory(Properties properties) {
		super(properties);
	}

	@Override
	public CollectionRegion buildCollectionRegion(
			String regionName, Properties properties,
			CacheDataDescription cacheDataDescription)
		throws CacheException {

		configureCache(regionName);

		EhcacheCollectionRegion ehcacheCollectionRegion =
			(EhcacheCollectionRegion)super.buildCollectionRegion(
				regionName, properties, cacheDataDescription);

		return new CollectionRegionWrapper(ehcacheCollectionRegion);
	}

	@Override
	public EntityRegion buildEntityRegion(
			String regionName, Properties properties,
			CacheDataDescription cacheDataDescription)
		throws CacheException {

		configureCache(regionName);

		EhcacheEntityRegion ehcacheEntityRegion =
			(EhcacheEntityRegion)super.buildEntityRegion(
				regionName, properties, cacheDataDescription);

		return new EntityRegionWrapper(ehcacheEntityRegion);
	}

	@Override
	public QueryResultsRegion buildQueryResultsRegion(
			String regionName, Properties properties)
		throws CacheException {

		configureCache(regionName);

		EhcacheQueryResultsRegion ehcacheQueryResultsRegion =
			(EhcacheQueryResultsRegion)super.buildQueryResultsRegion(
				regionName, properties);

		return new QueryResultsRegionWrapper(ehcacheQueryResultsRegion);
	}

	@Override
	public TimestampsRegion buildTimestampsRegion(
			String regionName, Properties properties)
		throws CacheException {

		configureCache(regionName);

		EhcacheTimestampsRegion ehcacheTimestampsRegion =
			(EhcacheTimestampsRegion)super.buildTimestampsRegion(
				regionName, properties);

		TimestampsRegion timestampsRegion = new TimestampsRegionWrapper(
			ehcacheTimestampsRegion);

		return timestampsRegion;
	}

	@Override
	public void start(Settings settings, Properties properties)
		throws CacheException {

		try {
			String configurationPath = null;

			if (properties != null) {
				configurationPath = (String)properties.get(
					NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME);
			}

			if (Validator.isNull(configurationPath)) {
				configurationPath = _DEFAULT_CLUSTERED_EHCACHE_CONFIG_FILE;
			}

			Configuration configuration = null;

			if (Validator.isNull(configurationPath)) {
				configuration = ConfigurationFactory.parseConfiguration();
			}
			else {
				_usingDefault = configurationPath.equals(
					_DEFAULT_CLUSTERED_EHCACHE_CONFIG_FILE);

				configuration = EhcacheConfigurationUtil.getConfiguration(
					configurationPath, _usingDefault);
			}

			/*Object transactionManager =
				getOnePhaseCommitSyncTransactionManager(settings, properties);

			configuration.setDefaultTransactionManager(transactionManager);*/

			manager = new CacheManager(configuration);

			boolean skipUpdateCheck = GetterUtil.getBoolean(
				SystemProperties.get("net.sf.ehcache.skipUpdateCheck"));
			boolean tcActive = GetterUtil.getBoolean(
				SystemProperties.get("tc.active"));

			if (skipUpdateCheck && !tcActive) {
				FailSafeTimer failSafeTimer = manager.getTimer();

				failSafeTimer.cancel();

				try {
					Field cacheManagerTimerField =
						ReflectionUtil.getDeclaredField(
							CacheManager.class, "cacheManagerTimer");

					cacheManagerTimerField.set(manager, null);
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

			mbeanRegistrationHelper.registerMBean(manager, properties);

			PortalCacheProvider.registerPortalCacheManager(
				new HibernatePortalCacheManager(manager));

			Registry registry = RegistryUtil.getRegistry();

			_serviceTracker = registry.trackServices(
				MBeanServer.class, new MBeanServerServiceTrackerCustomizer());

			_serviceTracker.open();
		}
		catch (net.sf.ehcache.CacheException ce) {
			throw new CacheException(ce);
		}
	}

	@Override
	public void stop() {
		PortalCacheProvider.unregisterPortalCacheManager(manager.getName());

		_managementService.dispose();

		_serviceTracker.close();

		super.stop();
	}

	protected void configureCache(String regionName) {
		synchronized (manager) {
			Ehcache ehcache = manager.getEhcache(regionName);

			if (ehcache == null) {
				manager.addCache(regionName);

				ehcache = manager.getEhcache(regionName);
			}

			if (!(ehcache instanceof ModifiableEhcacheWrapper)) {
				Ehcache modifiableEhcacheWrapper = new ModifiableEhcacheWrapper(
					ehcache);

				manager.replaceCacheWithDecoratedCache(
					ehcache, modifiableEhcacheWrapper);
			}
		}
	}

	protected void reconfigureCache(Ehcache replacementCache) {
		String cacheName = replacementCache.getName();

		Ehcache ehcache = manager.getEhcache(cacheName);

		if ((ehcache != null) &&
			(ehcache instanceof ModifiableEhcacheWrapper)) {

			if (_log.isInfoEnabled()) {
				_log.info("Reconfiguring Hibernate cache " + cacheName);
			}

			ModifiableEhcacheWrapper modifiableEhcacheWrapper =
				(ModifiableEhcacheWrapper)ehcache;

			manager.replaceCacheWithDecoratedCache(
				ehcache, modifiableEhcacheWrapper.getWrappedCache());

			manager.removeCache(cacheName);

			manager.addCache(replacementCache);

			modifiableEhcacheWrapper.setWrappedCache(replacementCache);

			manager.replaceCacheWithDecoratedCache(
				replacementCache, modifiableEhcacheWrapper);
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("Configuring Hibernate cache " + cacheName);
			}

			if (ehcache != null) {
				manager.removeCache(cacheName);
			}

			ehcache = new ModifiableEhcacheWrapper(replacementCache);

			manager.addCache(replacementCache);

			manager.replaceCacheWithDecoratedCache(replacementCache, ehcache);
		}
	}

	private static final String _DEFAULT_CLUSTERED_EHCACHE_CONFIG_FILE =
		"/ehcache/hibernate-clustered.xml";

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayEhcacheRegionFactory.class);

	private ManagementService _managementService;
	private ServiceTracker<MBeanServer, MBeanServer> _serviceTracker;
	private boolean _usingDefault;

	private class HibernatePortalCache<K extends Serializable, V>
		implements PortalCache<K, V> {

		@Override
		public V get(K key) {
			Element element = _ehcache.get(key);

			if (element == null) {
				return null;
			}

			return (V)element.getObjectValue();
		}

		@Override
		public List<K> getKeys() {
			return _ehcache.getKeys();
		}

		@Override
		public String getName() {
			return _ehcache.getName();
		}

		@Override
		public PortalCacheManager<K, V> getPortalCacheManager() {
			return _portalCacheManager;
		}

		@Override
		public void put(K key, V value) {
			_ehcache.put(new Element(key, value));
		}

		@Override
		public void put(K key, V value, int timeToLive) {
			Element element = new Element(key, value);

			if (timeToLive != DEFAULT_TIME_TO_LIVE) {
				element.setTimeToLive(timeToLive);
			}

			_ehcache.put(element);
		}

		@Override
		public void registerCacheListener(CacheListener<K, V> cacheListener) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void registerCacheListener(
			CacheListener<K, V> cacheListener,
			CacheListenerScope cacheListenerScope) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void remove(K key) {
			_ehcache.remove(key);
		}

		@Override
		public void removeAll() {
			_ehcache.removeAll();
		}

		@Override
		public void unregisterCacheListener(CacheListener<K, V> cacheListener) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void unregisterCacheListeners() {
			throw new UnsupportedOperationException();
		}

		private HibernatePortalCache(
			Ehcache ehcache, PortalCacheManager<K, V> portalCacheManager) {

			_ehcache = ehcache;
			_portalCacheManager = portalCacheManager;
		}

		private final Ehcache _ehcache;
		private final PortalCacheManager<K, V> _portalCacheManager;

	}

	private class HibernatePortalCacheManager
		implements PortalCacheManager<Serializable, Serializable> {

		@Override
		public void clearAll() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void destroy() {
			throw new UnsupportedOperationException();
		}

		@Override
		public PortalCache<Serializable, Serializable> getCache(String name) {
			PortalCache<Serializable, Serializable> portalCache =
				_portalCaches.get(name);

			if (portalCache != null) {
				return portalCache;
			}

			synchronized (_cacheManager) {
				portalCache = _portalCaches.get(name);

				if (portalCache == null) {
					if (!_cacheManager.cacheExists(name)) {
						return null;
					}

					Cache cache = _cacheManager.getCache(name);

					portalCache = new HibernatePortalCache<>(cache, this);

					_portalCaches.put(name, portalCache);
				}
			}

			return portalCache;
		}

		@Override
		public PortalCache<Serializable, Serializable> getCache(
			String name, boolean blocking) {

			throw new UnsupportedOperationException();
		}

		@Override
		public Set<CacheManagerListener> getCacheManagerListeners() {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getName() {
			return _name;
		}

		@Override
		public boolean isClusterAware() {
			return true;
		}

		@Override
		public void reconfigureCaches(URL configurationURL) {
			if (configurationURL == null) {
				return;
			}

			if (manager == null) {
				return;
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Reconfiguring Hibernate caches using " + configurationURL);
			}

			Configuration configuration =
				EhcacheConfigurationUtil.getConfiguration(
					configurationURL, _usingDefault);

			synchronized (manager) {
				Map<String, CacheConfiguration> cacheConfigurations =
					configuration.getCacheConfigurations();

				for (CacheConfiguration cacheConfiguration :
						cacheConfigurations.values()) {

					reconfigureCache(new Cache(cacheConfiguration));
				}
			}
		}

		@Override
		public boolean registerCacheManagerListener(
			CacheManagerListener cacheManagerListener) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void removeCache(String name) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean unregisterCacheManagerListener(
			CacheManagerListener cacheManagerListener) {

			throw new UnsupportedOperationException();
		}

		@Override
		public void unregisterCacheManagerListeners() {
			throw new UnsupportedOperationException();
		}

		private HibernatePortalCacheManager(CacheManager cacheManager) {
			_cacheManager = cacheManager;

			_cacheManager.setName(PortalCacheManagerNames.HIBERNATE);

			_name = _cacheManager.getName();
		}

		private final CacheManager _cacheManager;
		private final String _name;
		private final Map<String, PortalCache<Serializable, Serializable>>
			_portalCaches = new HashMap<>();

	}

	private class MBeanServerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<MBeanServer, MBeanServer> {

		@Override
		public MBeanServer addingService(
			ServiceReference<MBeanServer> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			MBeanServer mBeanServer = registry.getService(serviceReference);

			if (PropsValues.EHCACHE_PORTAL_CACHE_MANAGER_JMX_ENABLED) {
				_managementService = new ManagementService(
					manager, mBeanServer, true, true, true, true);

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