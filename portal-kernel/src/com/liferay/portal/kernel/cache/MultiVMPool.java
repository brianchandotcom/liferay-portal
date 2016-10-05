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

package com.liferay.portal.kernel.cache;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
@ProviderType
public interface MultiVMPool {

	public void clear();

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getPortalCache(String)}
	 */
	@Deprecated
	public PortalCache<? extends Serializable, ? extends Serializable>
		getCache(String portalCacheName);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getPortalCache(String,
	 *             boolean)}
	 */
	@Deprecated
	public PortalCache<? extends Serializable, ? extends Serializable>
		getCache(String portalCacheName, boolean blocking);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getPortalCacheManager()}
	 */
	@Deprecated
	public PortalCacheManager<? extends Serializable, ? extends Serializable>
		getCacheManager();

	public PortalCache<? extends Serializable, ? extends Serializable>
		getPortalCache(String portalCacheName);

	public PortalCache<? extends Serializable, ? extends Serializable>
		getPortalCache(String portalCacheName, boolean blocking);

	public PortalCache<? extends Serializable, ? extends Serializable>
		getPortalCache(String portalCacheName, boolean blocking, boolean mvcc);

	public PortalCacheManager<? extends Serializable, ? extends Serializable>
		getPortalCacheManager();

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #removePortalCache(String)}
	 */
	@Deprecated
	public void removeCache(String portalCacheName);

	public void removePortalCache(String portalCacheName);

}