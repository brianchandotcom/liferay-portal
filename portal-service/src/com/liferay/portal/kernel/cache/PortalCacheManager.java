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

package com.liferay.portal.kernel.cache;

import java.io.Serializable;

import java.net.URL;

/**
 * @author Joseph Shum
 */
public interface PortalCacheManager<K extends Serializable, V> {

	public void clearAll() throws PortalCacheException;

	public void destroy();

	public PortalCache<K, V> getCache(String name) throws PortalCacheException;

	public PortalCache<K, V> getCache(String name, boolean blocking)
		throws PortalCacheException;

	public void reconfigureCaches(URL configurationURL);

	public void removeCache(String name);

}