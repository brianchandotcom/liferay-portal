/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.test.integration.internal.util;

import java.lang.reflect.Method;

/**
 * @author Andrea Di Giorgi
 */
public class ReflectionUtil {

	public static Method getMethod(
		Object object, String name, Class<?>... parameterTypes) {

		Class<?> clazz = object.getClass();

		try {
			return clazz.getMethod(name, parameterTypes);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			return null;
		}
	}

}