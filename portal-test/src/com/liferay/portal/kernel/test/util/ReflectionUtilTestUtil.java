/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.util;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.test.SwappableSecurityManager;

import java.security.Permission;

import java.util.Objects;

/**
 * @author Janis Zhang
 */
public class ReflectionUtilTestUtil {

	public static SwappableSecurityManager throwForSuppressAccessChecks(
			SecurityException securityException)
		throws ClassNotFoundException {

		Class.forName(ReflectionUtil.class.getName());

		SwappableSecurityManager swappableSecurityManager =
			new SwappableSecurityManager() {

				@Override
				public void checkPermission(Permission permission) {
					if (Objects.equals(
							permission.getName(), "suppressAccessChecks")) {

						throw securityException;
					}
				}

			};

		swappableSecurityManager.install();

		return swappableSecurityManager;
	}

}