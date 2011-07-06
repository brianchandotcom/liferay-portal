/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * @author Edward Han
 */
public interface CacheListener {

	public void notifyEntryEvicted(
			PortalCache portalCache, Serializable key, Object value)
		throws PortalCacheException;

	public void notifyEntryExpired(
			PortalCache portalCache, Serializable key, Object value)
		throws PortalCacheException;

	public void notifyEntryPut(
			PortalCache portalCache, Serializable key, Object value)
		throws PortalCacheException;

	public void notifyEntryRemoved(
			PortalCache portalCache, Serializable key, Object value)
		throws PortalCacheException;

	public void notifyEntryUpdated(
			PortalCache portalCache, Serializable key, Object value)
		throws PortalCacheException;

	public void notifyRemoveAll(PortalCache portalCache)
		throws PortalCacheException;

}