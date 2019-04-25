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

package com.liferay.info.internal.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Jorge Ferrer
 */
public class GenericsUtil {

	public static Class<?> getItemClass(Class clazz) {
		Type[] genericInterfaceTypes = clazz.getGenericInterfaces();

		for (Type genericInterfaceType : genericInterfaceTypes) {
			ParameterizedType parameterizedType =
				(ParameterizedType)genericInterfaceType;

			return (Class<?>)parameterizedType.getActualTypeArguments()[0];
		}

		Class<?> superclass = clazz.getSuperclass();

		if (superclass != null) {
			return getItemClass(superclass);
		}

		return Object.class;
	}

	public static Class<?> getItemClass(Object object) {
		Class<?> infoListProviderClass = object.getClass();

		return getItemClass(infoListProviderClass);
	}

	public static String getItemClassName(Object object) {
		Class<?> clazz = getItemClass(object);

		return clazz.getName();
	}

}