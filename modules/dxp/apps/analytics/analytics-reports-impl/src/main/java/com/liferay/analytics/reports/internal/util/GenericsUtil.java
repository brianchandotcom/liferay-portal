/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.internal.util;

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

		Class<?> superClass = clazz.getSuperclass();

		if (superClass != null) {
			return getItemClass(superClass);
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