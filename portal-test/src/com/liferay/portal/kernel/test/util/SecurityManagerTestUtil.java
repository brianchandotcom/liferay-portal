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

package com.liferay.portal.kernel.test.util;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.test.SwappableSecurityManager;

import java.security.Permission;
import java.util.Objects;

/**
 * @author Janis Zhang
 */
public class SecurityManagerTestUtil {

	public static SwappableSecurityManager installForSuppressAccessChecks(
			Class<?> callerClass, SecurityException securityException)
		throws ClassNotFoundException {

		Class.forName(ReflectionUtil.class.getName());

		SwappableSecurityManager swappableSecurityManager =
			new SwappableSecurityManager() {

				@Override
				public void checkPermission(Permission permission) {
					if (!Objects.equals(
							permission.getName(), _SUPPRESS_ACCESS_CHECKS)) {
						return;
					}

					for (Class<?> clazz : getClassContext()) {
						if (clazz == callerClass) {
							throw securityException;
						}
					}
				}

			};

		swappableSecurityManager.install();

		return swappableSecurityManager;
	}

	private static final String _SUPPRESS_ACCESS_CHECKS =
		"suppressAccessChecks";

}