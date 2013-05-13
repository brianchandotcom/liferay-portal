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

import com.liferay.portal.cache.ehcache.ModifiableEhcacheWrapper;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;

import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.hibernate.regions.EhcacheDataRegion;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.Region;

/**
 * @author Edward Han
 */
public abstract class BaseRegionWrapper implements CacheRegistryItem, Region {

	public BaseRegionWrapper(EhcacheDataRegion ehcacheDataRegion) {
		_ehcacheDataRegion = ehcacheDataRegion;

		Ehcache ehcache = _ehcacheDataRegion.getEhcache();

		if (ehcache instanceof ModifiableEhcacheWrapper) {
			ModifiableEhcacheWrapper modifiableEhcacheWrapper =
				(ModifiableEhcacheWrapper)ehcache;

			modifiableEhcacheWrapper.addReference();
		}

		CacheRegistryUtil.register(this);
	}

	@Override
	public boolean contains(Object object) {
		return _ehcacheDataRegion.contains(object);
	}

	@Override
	public void destroy() throws CacheException {
		EhcacheDataRegion ehcacheDataRegion = getEhcacheDataRegion();

		Ehcache ehcache = ehcacheDataRegion.getEhcache();

		if (ehcache instanceof ModifiableEhcacheWrapper) {
			ModifiableEhcacheWrapper modifiableEhcacheWrapper =
				(ModifiableEhcacheWrapper)ehcache;

			modifiableEhcacheWrapper.removeReference();

			if (modifiableEhcacheWrapper.getActiveReferenceCount() == 0) {
				doDestroy();
			}
		}
		else {
			doDestroy();
		}
	}

	@Override
	public long getElementCountInMemory() {
		return _ehcacheDataRegion.getElementCountInMemory();
	}

	@Override
	public long getElementCountOnDisk() {
		return _ehcacheDataRegion.getElementCountOnDisk();
	}

	@Override
	public String getName() {
		return _ehcacheDataRegion.getName();
	}

	@Override
	public String getRegistryName() {
		return getName();
	}

	@Override
	public long getSizeInMemory() {
		return _ehcacheDataRegion.getSizeInMemory();
	}

	@Override
	public int getTimeout() {
		return _ehcacheDataRegion.getTimeout();
	}

	@Override
	public long nextTimestamp() {
		return _ehcacheDataRegion.nextTimestamp();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Map toMap() {
		return _ehcacheDataRegion.toMap();
	}

	@Override
	public String toString() {
		return _ehcacheDataRegion.toString();
	}

	protected void doDestroy() {
		_ehcacheDataRegion.destroy();

		CacheRegistryUtil.unregister(getRegistryName());
	}

	protected EhcacheDataRegion getEhcacheDataRegion() {
		return _ehcacheDataRegion;
	}

	private EhcacheDataRegion _ehcacheDataRegion;

}