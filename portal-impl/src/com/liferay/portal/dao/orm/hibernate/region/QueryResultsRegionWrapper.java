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

import net.sf.ehcache.hibernate.regions.EhcacheQueryResultsRegion;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.QueryResultsRegion;

/**
 * @author Edward Han
 */
public class QueryResultsRegionWrapper
	extends BaseRegionWrapper implements QueryResultsRegion {

	public QueryResultsRegionWrapper(
		EhcacheQueryResultsRegion ehcacheQueryResultsRegion) {

		super(ehcacheQueryResultsRegion);
	}

	@Override
	public void evict(Object key) throws CacheException {
		EhcacheQueryResultsRegion ehcacheQueryResultsRegion =
			getEhcacheQueryResultsRegion();

		ehcacheQueryResultsRegion.evict(key);
	}

	@Override
	public void evictAll() throws CacheException {
		EhcacheQueryResultsRegion ehcacheQueryResultsRegion =
			getEhcacheQueryResultsRegion();

		ehcacheQueryResultsRegion.evictAll();
	}

	@Override
	public Object get(Object key) throws CacheException {
		EhcacheQueryResultsRegion ehcacheQueryResultsRegion =
			getEhcacheQueryResultsRegion();

		return ehcacheQueryResultsRegion.get(key);
	}

	@Override
	public void invalidate() {
		EhcacheQueryResultsRegion ehcacheQueryResultsRegion =
			getEhcacheQueryResultsRegion();

		ehcacheQueryResultsRegion.evictAll();
	}

	@Override
	public void put(Object key, Object value) throws CacheException {
		EhcacheQueryResultsRegion ehcacheQueryResultsRegion =
			getEhcacheQueryResultsRegion();

		ehcacheQueryResultsRegion.put(key, value);
	}

	protected EhcacheQueryResultsRegion getEhcacheQueryResultsRegion() {
		return (EhcacheQueryResultsRegion)getEhcacheDataRegion();
	}

}