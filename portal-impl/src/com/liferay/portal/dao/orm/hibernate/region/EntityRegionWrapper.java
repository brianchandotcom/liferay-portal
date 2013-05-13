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

package com.liferay.portal.dao.orm.hibernate.region;

import net.sf.ehcache.hibernate.regions.EhcacheEntityRegion;

import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.EntityRegionAccessStrategy;

/**
 * @author Edward Han
 */
public class EntityRegionWrapper
	extends BaseRegionWrapper implements EntityRegion {

	public EntityRegionWrapper(EhcacheEntityRegion ehcacheEntityRegion) {
		super(ehcacheEntityRegion);
	}

	@Override
	public EntityRegionAccessStrategy buildAccessStrategy(AccessType accessType)
		throws CacheException {

		EhcacheEntityRegion ehcacheEntityRegion = getEhcacheEntityRegion();

		return ehcacheEntityRegion.buildAccessStrategy(accessType);
	}

	@Override
	public CacheDataDescription getCacheDataDescription() {
		EhcacheEntityRegion ehcacheEntityRegion = getEhcacheEntityRegion();

		return ehcacheEntityRegion.getCacheDataDescription();
	}

	@Override
	public void invalidate() {
		EhcacheEntityRegion ehcacheEntityRegion = getEhcacheEntityRegion();

		ehcacheEntityRegion.clear();
	}

	@Override
	public boolean isTransactionAware() {
		EhcacheEntityRegion ehcacheEntityRegion = getEhcacheEntityRegion();

		return ehcacheEntityRegion.isTransactionAware();
	}

	protected EhcacheEntityRegion getEhcacheEntityRegion() {
		return (EhcacheEntityRegion)getEhcacheDataRegion();
	}

}