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

package com.liferay.portal.kernel.security.permission;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringPool;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PermissionThreadLocal {

	public static PermissionChecker getPermissionChecker() {
		return _permissionChecker.get();
	}

	public static boolean isAddResource() {
		return _addResource.get();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static boolean isFlushResourceBlockEnabled(
		long companyId, long groupId, String name) {

		return false;
	}

	public static boolean isFlushResourcePermissionEnabled(
		String resourceName, String primKey) {

		Set<String> set = _flushResourcePermissionEnabled.get();

		return !set.contains(resourceName + StringPool.UNDERLINE + primKey);
	}

	public static void setAddResource(boolean addResource) {
		_addResource.set(addResource);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public static void setFlushResourceBlockEnabled(
		long companyId, long groupId, String name, boolean enabled) {
	}

	public static void setFlushResourcePermissionEnabled(
		String resourceName, String primKey, boolean enabled) {

		Set<String> set = _flushResourcePermissionEnabled.get();

		if (enabled) {
			set.remove(resourceName + StringPool.UNDERLINE + primKey);
		}
		else {
			set.add(resourceName + StringPool.UNDERLINE + primKey);
		}
	}

	public static void setPermissionChecker(
		PermissionChecker permissionChecker) {

		_permissionChecker.set(permissionChecker);
	}

	private static final ThreadLocal<Boolean> _addResource =
		new CentralizedThreadLocal<>(
			PermissionThreadLocal.class + "._addResource", () -> Boolean.TRUE);
	private static final ThreadLocal<Set<String>>
		_flushResourcePermissionEnabled = new CentralizedThreadLocal<>(
			PermissionThreadLocal.class + "._flushResourcePermissionEnabled",
			HashSet::new);
	private static final ThreadLocal<PermissionChecker> _permissionChecker =
		new CentralizedThreadLocal<>(
			PermissionThreadLocal.class + "._permissionChecker", null,
			Function.identity(), true);

}