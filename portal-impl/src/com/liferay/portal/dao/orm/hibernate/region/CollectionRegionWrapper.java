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

import net.sf.ehcache.hibernate.regions.EhcacheCollectionRegion;

import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;

/**
 * @author Edward Han
 */
public class CollectionRegionWrapper
	extends BaseRegionWrapper implements CollectionRegion {

	public CollectionRegionWrapper(
		EhcacheCollectionRegion ehcacheCollectionRegion) {

		super(ehcacheCollectionRegion);
	}

	@Override
	public CollectionRegionAccessStrategy buildAccessStrategy(
			AccessType accessType)
		throws CacheException {

		EhcacheCollectionRegion ehcacheCollectionRegion =
			getEhcacheCollectionRegion();

		return ehcacheCollectionRegion.buildAccessStrategy(accessType);
	}

	@Override
	public CacheDataDescription getCacheDataDescription() {
		EhcacheCollectionRegion ehcacheCollectionRegion =
			getEhcacheCollectionRegion();

		return ehcacheCollectionRegion.getCacheDataDescription();
	}

	@Override
	public void invalidate() {
		EhcacheCollectionRegion ehcacheCollectionRegion =
			getEhcacheCollectionRegion();

		ehcacheCollectionRegion.clear();
	}

	@Override
	public boolean isTransactionAware() {
		EhcacheCollectionRegion ehcacheCollectionRegion =
			getEhcacheCollectionRegion();

		return ehcacheCollectionRegion.isTransactionAware();
	}

	protected EhcacheCollectionRegion getEhcacheCollectionRegion() {
		return (EhcacheCollectionRegion)getEhcacheDataRegion();
	}

}