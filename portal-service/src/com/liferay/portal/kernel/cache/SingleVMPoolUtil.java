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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;

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
		String name) {

		return (PortalCache<K, V>)getSingleVMPool().getCache(name);
	}

	public static <K extends Serializable, V> PortalCache<K, V> getCache(
		String name, boolean blocking) {

		return (PortalCache<K, V>)getSingleVMPool().getCache(name, blocking);
	}

	public static <K extends Serializable, V> PortalCacheManager<K, V>
		getCacheManager() {

		return (PortalCacheManager<K, V>)getSingleVMPool().getCacheManager();
	}

	public static SingleVMPool getSingleVMPool() {
		PortalRuntimePermission.checkGetBeanProperty(SingleVMPoolUtil.class);

		return _singleVMPool;
	}

	public static void removeCache(String name) {
		getSingleVMPool().removeCache(name);
	}

	public void setSingleVMPool(SingleVMPool singleVMPool) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_singleVMPool = singleVMPool;
	}

	private static SingleVMPool _singleVMPool;

}