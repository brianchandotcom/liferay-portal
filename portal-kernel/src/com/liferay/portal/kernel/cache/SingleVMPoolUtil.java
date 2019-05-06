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

import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.Serializable;

/**
 * @author     Brian Wing Shun Chan
 * @author     Michael Young
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
@OSGiBeanProperties(service = SingleVMPoolUtil.class)
public class SingleVMPoolUtil {

	public static void clear() {
		_singleVMPool.clear();
	}

	public static <K extends Serializable, V> PortalCache<K, V> getPortalCache(
		String portalCacheName) {

		return (PortalCache<K, V>)_singleVMPool.getPortalCache(portalCacheName);
	}

	public static <K extends Serializable, V> PortalCache<K, V> getPortalCache(
		String portalCacheName, boolean blocking) {

		return (PortalCache<K, V>)_singleVMPool.getPortalCache(
			portalCacheName, blocking);
	}

	public static <K extends Serializable, V> PortalCacheManager<K, V>
		getPortalCacheManager() {

		return (PortalCacheManager<K, V>)_singleVMPool.getPortalCacheManager();
	}

	public static void removePortalCache(String portalCacheName) {
		_singleVMPool.removePortalCache(portalCacheName);
	}

	private static volatile SingleVMPool _singleVMPool =
		ServiceProxyFactory.newServiceTrackedInstance(
			SingleVMPool.class, SingleVMPoolUtil.class, "_singleVMPool", true);

}