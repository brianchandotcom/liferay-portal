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
 * @see com.liferay.analytics.reports.internal.util.GenericsUtil
 * @see com.liferay.layout.internal.list.retriever.util.GenericsUtil
 * @see com.liferay.layout.page.template.admin.web.internal.util.GenericsUtil
 */
public class GenericsUtil {

	public static Class<?> getItemClass(Class<?> clazz) {
		Type[] genericInterfaceTypes = clazz.getGenericInterfaces();

		for (Type genericInterfaceType : genericInterfaceTypes) {
			if (genericInterfaceType instanceof ParameterizedType) {
				ParameterizedType parameterizedType =
					(ParameterizedType)genericInterfaceType;

				return (Class<?>)parameterizedType.getActualTypeArguments()[0];
			}
		}

		Class<?> superClass = clazz.getSuperclass();

		if (superClass != null) {
			return getItemClass(superClass);
		}

		return Object.class;
	}

	public static Class<?> getItemClass(Object object) {
		Class<?> clazz = object.getClass();

		return getItemClass(clazz);
	}

	public static String getItemClassName(Object object) {
		Class<?> clazz = getItemClass(object);

		return clazz.getName();
	}

}