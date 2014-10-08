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

import com.liferay.portal.cache.ehcache.CacheManagerUtil;
import com.liferay.portal.cache.ehcache.EhcacheConfigurationUtil;
import com.liferay.portal.cache.ehcache.EhcachePortalCache;
import com.liferay.portal.cache.ehcache.ModifiableEhcacheWrapper;
import com.liferay.portal.kernel.cache.CacheManagerListener;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.hibernate.EhCacheRegionFactory;
import net.sf.ehcache.hibernate.regions.EhcacheCollectionRegion;
import net.sf.ehcache.hibernate.regions.EhcacheEntityRegion;
import net.sf.ehcache.hibernate.regions.EhcacheQueryResultsRegion;
import net.sf.ehcache.hibernate.regions.EhcacheTimestampsRegion;
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

	public void reconfigureCaches(URL cacheConfigFile) {
		if (manager == null) {
			return;
		}

		synchronized (manager) {
			Configuration configuration =
				EhcacheConfigurationUtil.getConfiguration(
					cacheConfigFile, true, _usingDefault);

			Map<String, CacheConfiguration> cacheConfigurations =
				configuration.getCacheConfigurations();

			for (CacheConfiguration cacheConfiguration :
					cacheConfigurations.values()) {

				Ehcache ehcache = new Cache(cacheConfiguration);

				reconfigureCache(ehcache);
			}
		}
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
					configurationPath, true, _usingDefault);
			}

			/*Object transactionManager =
				getOnePhaseCommitSyncTransactionManager(settings, properties);

			configuration.setDefaultTransactionManager(transactionManager);*/

			manager = CacheManagerUtil.createCacheManager(configuration);

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

			_mBeanRegisteringPortalLifecycle =
				new MBeanRegisteringPortalLifecycle(manager);

			_mBeanRegisteringPortalLifecycle.registerPortalLifecycle(
				PortalLifecycle.METHOD_INIT);

			PortalCacheProvider.registerPortalCacheManager(
				new HibernatePortalCacheManager(manager));
		}
		catch (net.sf.ehcache.CacheException ce) {
			throw new CacheException(ce);
		}
	}

	@Override
	public void stop() {
		PortalCacheProvider.unregisterPortalCacheManager(manager.getName());

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

	private MBeanRegisteringPortalLifecycle _mBeanRegisteringPortalLifecycle;
	private boolean _usingDefault;

	private class HibernatePortalCacheManager
		implements PortalCacheManager<Serializable, Serializable> {

		public HibernatePortalCacheManager(CacheManager cacheManager) {
			_cacheManager = cacheManager;
		}

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

					portalCache =
						new EhcachePortalCache<Serializable, Serializable>(
							this, cache);

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
			return _cacheManager.getName();
		}

		@Override
		public boolean isClusterAware() {
			return true;
		}

		@Override
		public void reconfigureCaches(URL configurationURL) {
			throw new UnsupportedOperationException();
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

		private final CacheManager _cacheManager;
		private final Map<String, PortalCache<Serializable, Serializable>>
			_portalCaches =
				new HashMap<String, PortalCache<Serializable, Serializable>>();

	}

}