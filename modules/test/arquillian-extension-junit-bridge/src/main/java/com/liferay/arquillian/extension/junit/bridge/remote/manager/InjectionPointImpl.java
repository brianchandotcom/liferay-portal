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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.impl.Reflections;
import org.jboss.arquillian.core.spi.InjectionPoint;
import org.jboss.arquillian.core.spi.InvocationException;

/**
 * @author Matthew Tambara
 */
public class InjectionPointImpl implements InjectionPoint {

	public static InjectionPointImpl of(Object target, Field field) {
		return new InjectionPointImpl(
			target, field, Reflections.getScope(field));
	}

	@Override
	public Class<? extends Annotation> getScope() {
		return _scope;
	}

	@Override
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

	@Override
	public void set(Instance<?> instance) throws InvocationException {
		try {
			if (!_field.isAccessible()) {
				_field.setAccessible(true);
			} _field.set(_target, instance);
		}
		catch (Exception e) {
			throw new InvocationException(e.getCause());
		}
	}

	private InjectionPointImpl(
		Object target, Field field, Class<? extends Annotation> scope) {

		_target = target;
		_field = field;
		_scope = scope;
	}

	private final Field _field;
	private final Class<? extends Annotation> _scope;
	private final Object _target;

}