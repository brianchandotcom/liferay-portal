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

package com.liferay.vulcan.wiring.osgi;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.vulcan.error.VulcanDeveloperError;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public class GenericUtil {

	public static <T, S> Class<S> getGenericClass(T t, Class<T> clazz) {
		Type genericType = ReflectionUtil.getGenericInterface(t, clazz);

		if ((genericType != null) &&
			(genericType instanceof ParameterizedType)) {

			ParameterizedType parameterizedType =
				(ParameterizedType)genericType;

			Type[] typeArguments = parameterizedType.getActualTypeArguments();

			if (!ArrayUtil.isEmpty(typeArguments) &&
				(typeArguments.length == 1)) {

				try {
					return (Class<S>)typeArguments[0];
				}
				catch (ClassCastException cce) {
				}
			}
		}

		throw new VulcanDeveloperError.MustHaveValidGenericType(t.getClass());
	}

}