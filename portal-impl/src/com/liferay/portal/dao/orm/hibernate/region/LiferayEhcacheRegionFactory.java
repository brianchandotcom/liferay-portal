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

import com.liferay.portal.cache.ehcache.EhcachePortalCacheManager;
import com.liferay.portal.cache.ehcache.EhcacheUnwrapUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SystemProperties;

import java.io.Serializable;

import java.util.Properties;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.hibernate.EhCacheRegionFactory;
import net.sf.ehcache.hibernate.regions.EhcacheCollectionRegion;
import net.sf.ehcache.hibernate.regions.EhcacheEntityRegion;
import net.sf.ehcache.hibernate.regions.EhcacheQueryResultsRegion;
import net.sf.ehcache.hibernate.regions.EhcacheTimestampsRegion;

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
	public void start(Settings settings, Properties properties) {
		String configFile = null;

		if (properties != null) {
			configFile = (String)properties.get(
				NET_SF_EHCACHE_CONFIGURATION_RESOURCE_NAME);
		}

		HibernatePortalCacheManager hibernatePortalCacheManager =
			new HibernatePortalCacheManager();

		hibernatePortalCacheManager.setClusterAware(true);
		hibernatePortalCacheManager.setConfigFile(configFile);
		hibernatePortalCacheManager.setDefaultConfigFile(
			_DEFAULT_CLUSTERED_EHCACHE_CONFIG_FILE);
		hibernatePortalCacheManager.setMpiOnly(true);
		hibernatePortalCacheManager.setName(PortalCacheManagerNames.HIBERNATE);

		boolean skipUpdateCheck = GetterUtil.getBoolean(
			SystemProperties.get("net.sf.ehcache.skipUpdateCheck"));
		boolean tcActive = GetterUtil.getBoolean(
			SystemProperties.get("tc.active"));

		hibernatePortalCacheManager.setStopCacheManagerTimer(
			skipUpdateCheck && !tcActive);

		hibernatePortalCacheManager.afterPropertiesSet();

		manager = hibernatePortalCacheManager.getEhcacheManager();
		_hibernatePortalCacheManager = hibernatePortalCacheManager;

		mbeanRegistrationHelper.registerMBean(manager, properties);
	}

	@Override
	public void stop() {
		super.stop();

		if (_hibernatePortalCacheManager != null) {
			_hibernatePortalCacheManager.destroy();
		}
	}

	protected void configureCache(String regionName) {
		PortalCache<Serializable, Serializable> portalCache =
			_hibernatePortalCacheManager.getCache(regionName);

		Ehcache ehcache = EhcacheUnwrapUtil.getEhcache(portalCache);

		if (!(ehcache instanceof ModifiableEhcacheWrapper)) {
			Ehcache modifiableEhcacheWrapper = new ModifiableEhcacheWrapper(
				ehcache);

			manager.replaceCacheWithDecoratedCache(
				ehcache, modifiableEhcacheWrapper);
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

	private volatile PortalCacheManager<Serializable, Serializable>
		_hibernatePortalCacheManager;

	private class HibernatePortalCacheManager
		extends EhcachePortalCacheManager<Serializable, Serializable> {

		@Override
		protected Ehcache createEhcache(
			String portalCacheName, CacheConfiguration cacheConfiguration) {

			reconfigureCache(new Cache(cacheConfiguration));

			return manager.getCache(portalCacheName);
		}

	}

}