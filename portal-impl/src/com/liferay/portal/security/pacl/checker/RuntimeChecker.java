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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.util.StringPool;

import java.security.Permission;

/**
 * @author Raymond Augé
 */
public class RuntimeChecker extends BaseChecker {

	public void afterPropertiesSet() {
	}

	public void checkPermission(Permission permission) {
		String name = permission.getName();

		if (name.startsWith(RUNTIME_PERMISSION_ACCESS_CLASS_IN_PACKAGE)) {
			int pos = name.indexOf(StringPool.PERIOD);

			String pkg = name.substring(pos + 1);

			if (!hasPackageAccess(pkg)) {
				throw new SecurityException(
					"Attempted to access package " + pkg);
			}
		}
		else if (name.equals(RUNTIME_PERMISSION_GET_CLASSLOADER)) {
		}
		else if (name.equals(RUNTIME_PERMISSION_SET_SECURITY_MANAGER)) {
			throw new SecurityException(
				"Attempted to set another security manager");
		}
		else if (name.equals(RUNTIME_PERMISSION_EXIT_VM)) {
			Thread.dumpStack();

			throw new SecurityException("Attempted to shutdown the VM");
		}
	}

	public boolean hasPackageAccess(String pkg) {

		// TODO

		if (pkg.startsWith("sun.reflect")) {
		}

		return true;
	}

}