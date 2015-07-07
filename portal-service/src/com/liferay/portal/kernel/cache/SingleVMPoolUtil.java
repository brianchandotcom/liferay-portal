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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
@OSGiBeanProperties(service = SingleVMPoolUtil.class)
public class SingleVMPoolUtil {

	public static void clear() {
		getSingleVMPool().clear();
	}

	public static <K extends Serializable, V> PortalCache<K, V> getCache(
		String portalCacheName) {

		return (PortalCache<K, V>)getSingleVMPool().getCache(portalCacheName);
	}

	public static <K extends Serializable, V> PortalCache<K, V> getCache(
		String portalCacheName, boolean blocking) {

		return (PortalCache<K, V>)getSingleVMPool().getCache(
			portalCacheName, blocking);
	}

	public static <K extends Serializable, V> PortalCacheManager<K, V>
		getCacheManager() {

		return (PortalCacheManager<K, V>)getSingleVMPool().getCacheManager();
	}

	public static SingleVMPool getSingleVMPool() {
		PortalRuntimePermission.checkGetBeanProperty(SingleVMPoolUtil.class);

		SingleVMPool singleVMPool = _instance._serviceTracker.getService();

		if (singleVMPool == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("SingleVMPoolUtil has not been initialized");
			}

			return null;
		}

		return singleVMPool;
	}

	public static void removeCache(String portalCacheName) {
		getSingleVMPool().removeCache(portalCacheName);
	}

	private SingleVMPoolUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(SingleVMPool.class);

		_serviceTracker.open();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SingleVMPoolUtil.class);

	private static final SingleVMPoolUtil _instance = new SingleVMPoolUtil();

	private final ServiceTracker<SingleVMPool, SingleVMPool> _serviceTracker;

}