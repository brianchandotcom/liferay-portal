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

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.HashSet;
import java.util.Set;

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

	public static boolean isFlushResourceBlockEnabled(
		long companyId, long groupId, String name) {

		Set<String> set = _flushResourceBlockEnabled.get();

		StringBundler sb = new StringBundler(5);

		sb.append(companyId);
		sb.append(StringPool.UNDERLINE);
		sb.append(groupId);
		sb.append(StringPool.UNDERLINE);
		sb.append(name);

		return !set.contains(sb.toString());
	}

	public static boolean isFlushResourcePermissionEnabled(
		String resourceName, String primKey) {

		Set<String> set = _flushResourcePermissionEnabled.get();

		return !set.contains(resourceName + StringPool.UNDERLINE + primKey);
	}

	public static void setAddResource(boolean addResource) {
		_addResource.set(addResource);
	}

	public static void setFlushResourceBlockEnabled(
		long companyId, long groupId, String name, boolean enabled) {

		Set<String> set = _flushResourceBlockEnabled.get();

		StringBundler sb = new StringBundler(5);

		sb.append(companyId);
		sb.append(StringPool.UNDERLINE);
		sb.append(groupId);
		sb.append(StringPool.UNDERLINE);
		sb.append(name);

		if (enabled) {
			set.remove(sb.toString());
		}
		else {
			set.add(sb.toString());
		}
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
		new AutoResetThreadLocal<>(
			PermissionThreadLocal.class + "._addResource", true);
	private static final ThreadLocal<Set<String>> _flushResourceBlockEnabled =
		new AutoResetThreadLocal<Set<String>>(
			PermissionThreadLocal.class + "._flushResourceBlockEnabled",
			new HashSet<String>());
	private static final ThreadLocal<Set<String>>
		_flushResourcePermissionEnabled = new AutoResetThreadLocal<Set<String>>(
			PermissionThreadLocal.class +
				"._flushResourcePermissionEnabled",
			new HashSet<String>());

	private static final ThreadLocal<PermissionChecker> _permissionChecker =
		new AutoResetThreadLocal<PermissionChecker>(
			PermissionThreadLocal.class + "._permissionChecker") {

			@Override
			protected PermissionChecker copy(
				PermissionChecker permissionChecker) {

				return permissionChecker;
			}

		};

}