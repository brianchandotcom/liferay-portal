/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 */
public class SingleVMPoolUtil {

	public static void clear() {
		getSingleVMPool().clear();
	}

	public static void clear(String name) {
		getSingleVMPool().clear(name);
	}

	/**
	 * @deprecated
	 */
	public static Object get(PortalCache portalCache, String key) {
		return getSingleVMPool().get(portalCache, key);
	}

	public static Object get(String name, String key) {
		return getSingleVMPool().get(name, key);
	}

	public static PortalCache getCache(String name) {
		return getSingleVMPool().getCache(name);
	}

	public static PortalCache getCache(String name, boolean blocking) {
		return getSingleVMPool().getCache(name, blocking);
	}

	public static SingleVMPool getSingleVMPool() {
		PortalRuntimePermission.checkGetBeanProperty(SingleVMPoolUtil.class);

		return _singleVMPool;
	}

	/**
	 * @deprecated
	 */
	public static void put(PortalCache portalCache, String key, Object value) {
		getSingleVMPool().put(portalCache, key, value);
	}

	/**
	 * @deprecated
	 */
	public static void put(
		PortalCache portalCache, String key, Object value, int timeToLive) {

		getSingleVMPool().put(portalCache, key, value, timeToLive);
	}

	/**
	 * @deprecated
	 */
	public static void put(
		PortalCache portalCache, String key, Serializable value) {

		getSingleVMPool().put(portalCache, key, value);
	}

	/**
	 * @deprecated
	 */
	public static void put(
		PortalCache portalCache, String key, Serializable value,
		int timeToLive) {

		getSingleVMPool().put(portalCache, key, value, timeToLive);
	}

	public static void put(String name, String key, Object value) {
		getSingleVMPool().put(name, key, value);
	}

	public static void put(String name, String key, Serializable value) {
		getSingleVMPool().put(name, key, value);
	}

	/**
	 * @deprecated
	 */
	public static void remove(PortalCache portalCache, String key) {
		getSingleVMPool().remove(portalCache, key);
	}

	public static void remove(String name, String key) {
		getSingleVMPool().remove(name, key);
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