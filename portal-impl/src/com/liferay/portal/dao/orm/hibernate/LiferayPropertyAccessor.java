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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.TextFormatter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import java.util.Map;

import org.hibernate.PropertyAccessException;
import org.hibernate.PropertyNotFoundException;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.property.BasicPropertyAccessor;
import org.hibernate.property.Getter;
import org.hibernate.property.Setter;

/**
 * @author Preston Crary
 */
public class LiferayPropertyAccessor extends BasicPropertyAccessor {

	@Override
	@SuppressWarnings("unchecked")
	public Getter getGetter(Class clazz, String propertyName)
		throws PropertyNotFoundException {

		String methodNameSuffix = TextFormatter.format(
			propertyName, TextFormatter.G);

		String getterMethodName = "get".concat(methodNameSuffix);

		try {
			Method getterMethod = clazz.getMethod(getterMethodName);

			return new LiferayPropertyGetter(getterMethod, propertyName);
		}
		catch (NoSuchMethodException nsme) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Getter not found for ", clazz.getName(),
						StringPool.POUND, propertyName),
					nsme);
			}

			return super.getGetter(clazz, propertyName);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Setter getSetter(Class clazz, String propertyName)
		throws PropertyNotFoundException {

		String methodNameSuffix = TextFormatter.format(
			propertyName, TextFormatter.G);

		String getterMethodName = "get".concat(methodNameSuffix);
		String setterMethodName = "set".concat(methodNameSuffix);

		try {
			Method getterMethod = clazz.getMethod(getterMethodName);

			Method setterMethod = clazz.getMethod(
				setterMethodName, getterMethod.getReturnType());

			return new LiferayPropertySetter(setterMethod, propertyName);
		}
		catch (NoSuchMethodException nsme) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Setter not found for ", clazz.getName(),
						StringPool.POUND, propertyName),
					nsme);
			}

			return super.getSetter(clazz, propertyName);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayPropertyAccessor.class);

	private static class LiferayPropertyGetter implements Getter {

		@Override
		public Object get(Object target) throws PropertyAccessException {
			try {
				return _method.invoke(target);
			}
			catch (IllegalAccessException | IllegalArgumentException |
				   InvocationTargetException e) {

				throw new PropertyAccessException(
					e, e.getMessage(), false, _method.getDeclaringClass(),
					_propertyName);
			}
		}

		@Override
		public Object getForInsert(
				Object target, Map mergeMap,
				SessionImplementor sessionImplementor)
			throws PropertyAccessException {

			return get(target);
		}

		@Override
		public Member getMember() {
			return _method;
		}

		@Override
		public Method getMethod() {
			return _method;
		}

		@Override
		public String getMethodName() {
			return _method.getName();
		}

		@Override
		public Class getReturnType() {
			return _method.getReturnType();
		}

		private LiferayPropertyGetter(Method method, String propertyName) {
			_method = method;
			_propertyName = propertyName;
		}

		private final Method _method;
		private final String _propertyName;

	}

	private static class LiferayPropertySetter implements Setter {

		@Override
		public Method getMethod() {
			return _method;
		}

		@Override
		public String getMethodName() {
			return _method.getName();
		}

		@Override
		public void set(
				Object target, Object value,
				SessionFactoryImplementor sessionFactoryImplementor)
			throws PropertyAccessException {

			try {
				_method.invoke(target, value);
			}
			catch (IllegalAccessException | IllegalArgumentException |
				   InvocationTargetException | NullPointerException e) {

				throw new PropertyAccessException(
					e, e.getMessage(), true, _method.getDeclaringClass(),
					_propertyName);
			}
		}

		private LiferayPropertySetter(Method method, String propertyName) {
			_method = method;
			_propertyName = propertyName;
		}

		private final Method _method;
		private final String _propertyName;

	}

}