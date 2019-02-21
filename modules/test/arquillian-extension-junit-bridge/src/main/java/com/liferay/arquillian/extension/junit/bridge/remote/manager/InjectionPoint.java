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

package com.liferay.arquillian.extension.junit.bridge.remote.manager;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.core.api.annotation.Inject;

/**
 * @author Matthew Tambara
 */
public class InjectionPoint {

	public static List<InjectionPoint> getInjections(Object target) {
		List<InjectionPoint> injectionPoints = new ArrayList<>();

		Class<?> clazz = target.getClass();

		while (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				if (_isInjectionPoint(field)) {
					field.setAccessible(true);

					injectionPoints.add(new InjectionPoint(target, field));
				}
			}

			clazz = clazz.getSuperclass();
		}

		return injectionPoints;
	}

	public Type getType() {
		ParameterizedType parameterizedType =
			(ParameterizedType)_field.getGenericType();

		if (parameterizedType.getActualTypeArguments()[0] instanceof
				ParameterizedType) {

			ParameterizedType first =
				(ParameterizedType)
					parameterizedType.getActualTypeArguments()[0];

			return (Class<?>)first.getRawType();
		}

		return (Class<?>)parameterizedType.getActualTypeArguments()[0];
	}

	public void set(Instance<?> instance) throws ReflectiveOperationException {
		_field.set(_target, instance);
	}

	private static boolean _isInjectionPoint(Field field) {
		if (field.isAnnotationPresent(Inject.class)) {
			Class<?> type = field.getType();

			if (type.equals(Instance.class)) {
				return true;
			}
		}

		return false;
	}

	private InjectionPoint(Object target, Field field) {
		_target = target;
		_field = field;
	}

	private final Field _field;
	private final Object _target;

}