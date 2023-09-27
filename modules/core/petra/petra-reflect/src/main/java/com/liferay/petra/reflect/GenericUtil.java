/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Jorge Ferrer
 */
public class GenericUtil {

	public static Class<?> getGenericClass(Class<?> clazz) {
		return getGenericClass(clazz, 0);
	}

	public static Class<?> getGenericClass(Class<?> clazz, int index) {
		Type[] genericInterfaceTypes = clazz.getGenericInterfaces();

		for (Type genericInterfaceType : genericInterfaceTypes) {
			if (genericInterfaceType instanceof ParameterizedType) {
				ParameterizedType parameterizedType =
					(ParameterizedType)genericInterfaceType;

				Type[] actualArgumentTypes =
					parameterizedType.getActualTypeArguments();

				if (index >= actualArgumentTypes.length) {
					return Object.class;
				}

				return (Class<?>)actualArgumentTypes[index];
			}
		}

		Class<?> superClass = clazz.getSuperclass();

		if (superClass != null) {
			return getGenericClass(superClass);
		}

		return Object.class;
	}

	public static Class<?> getGenericClass(Object object) {
		return getGenericClass(object, 0);
	}

	public static Class<?> getGenericClass(Object object, int index) {
		Class<?> clazz = object.getClass();

		return getGenericClass(clazz, index);
	}

	public static String getGenericClassName(Object object) {
		Class<?> clazz = getGenericClass(object);

		return clazz.getName();
	}

}