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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.QueryResultsRegion;
import org.hibernate.cache.RegionFactory;
import org.hibernate.cache.TimestampsRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cfg.Settings;

/**
 * @author Edward Han
 * @author Shuyang Zhou
 */
public class SingletonLiferayEhcacheRegionFactory implements RegionFactory {

	public static LiferayEhcacheRegionFactory getInstance() {
		return _liferayEhcacheRegionFactoryReference.get();
	}

	public SingletonLiferayEhcacheRegionFactory(Properties properties) {
		boolean useQueryCache = GetterUtil.getBoolean(
			properties.get(PropsKeys.HIBERNATE_CACHE_USE_QUERY_CACHE));
		boolean useSecondLevelCache = GetterUtil.getBoolean(
			properties.get(PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));

		if (useQueryCache || useSecondLevelCache) {
			_liferayEhcacheRegionFactoryReference.compareAndSet(
				null, new LiferayEhcacheRegionFactory(properties));
		}
	}

	@Override
	public CollectionRegion buildCollectionRegion(
			String regionName, Properties properties,
			CacheDataDescription cacheDataDescription)
		throws CacheException {

		return getInstance().buildCollectionRegion(
			regionName, properties, cacheDataDescription);
	}

	@Override
	public EntityRegion buildEntityRegion(
			String regionName, Properties properties,
			CacheDataDescription cacheDataDescription)
		throws CacheException {

		return getInstance().buildEntityRegion(
			regionName, properties, cacheDataDescription);
	}

	@Override
	public QueryResultsRegion buildQueryResultsRegion(
			String regionName, Properties properties)
		throws CacheException {

		return getInstance().buildQueryResultsRegion(regionName, properties);
	}

	@Override
	public TimestampsRegion buildTimestampsRegion(
			String regionName, Properties properties)
		throws CacheException {

		return getInstance().buildTimestampsRegion(regionName, properties);
	}

	@Override
	public AccessType getDefaultAccessType() {
		return getInstance().getDefaultAccessType();
	}

	@Override
	public boolean isMinimalPutsEnabledByDefault() {
		return getInstance().isMinimalPutsEnabledByDefault();
	}

	@Override
	public long nextTimestamp() {
		return getInstance().nextTimestamp();
	}

	@Override
	public synchronized void start(Settings settings, Properties properties)
		throws CacheException {

		LiferayEhcacheRegionFactory liferayEhcacheRegionFactory = getInstance();

		if ((liferayEhcacheRegionFactory != null) &&
			(_instanceCounter++ == 0)) {

			liferayEhcacheRegionFactory.start(settings, properties);
		}
	}

	@Override
	public synchronized void stop() {
		LiferayEhcacheRegionFactory liferayEhcacheRegionFactory = getInstance();

		if ((liferayEhcacheRegionFactory != null) &&
			(--_instanceCounter == 0)) {

			liferayEhcacheRegionFactory.stop();
		}
	}

	private static int _instanceCounter;
	private static final AtomicReference<LiferayEhcacheRegionFactory>
		_liferayEhcacheRegionFactoryReference =
			new AtomicReference<LiferayEhcacheRegionFactory>();

}