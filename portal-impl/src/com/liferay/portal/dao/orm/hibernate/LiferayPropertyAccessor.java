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
import com.liferay.portal.kernel.model.impl.BaseModelImpl;

import java.lang.reflect.Field;
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

		try {
			Field field = _findField(clazz, propertyName);

			return new LiferayPropertyGetter(field, propertyName);
		}
		catch (NoSuchFieldException nsfe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Field not found for ", clazz.getName(),
						StringPool.POUND, propertyName),
					nsfe);
			}

			return super.getGetter(clazz, propertyName);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Setter getSetter(Class clazz, String propertyName)
		throws PropertyNotFoundException {

		try {
			Field field = _findField(clazz, propertyName);

			return new LiferayPropertySetter(field, propertyName);
		}
		catch (NoSuchFieldException nsfe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Setter not found for ", clazz.getName(),
						StringPool.POUND, propertyName),
					nsfe);
			}

			return super.getSetter(clazz, propertyName);
		}
	}

	private Field _findField(Class<?> clazz, String propertyName)
		throws NoSuchFieldException {

		boolean baseModelImpl = false;

		while (true) {
			Class<?> superClass = clazz.getSuperclass();

			if (superClass == BaseModelImpl.class) {
				baseModelImpl = true;

				break;
			}

			if (superClass == Object.class) {
				break;
			}

			clazz = superClass;
		}

		if (baseModelImpl) {
			Field field = clazz.getDeclaredField("_".concat(propertyName));

			field.setAccessible(true);

			return field;
		}

		return clazz.getField(propertyName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayPropertyAccessor.class);

	private static class LiferayPropertyGetter implements Getter {

		@Override
		public Object get(Object target) throws PropertyAccessException {
			try {
				return _field.get(target);
			}
			catch (IllegalAccessException | IllegalArgumentException e) {
				throw new PropertyAccessException(
					e, e.getMessage(), false, _field.getDeclaringClass(),
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
			return _field;
		}

		@Override
		public Method getMethod() {
			return null;
		}

		@Override
		public String getMethodName() {
			return null;
		}

		@Override
		public Class getReturnType() {
			return _field.getType();
		}

		private LiferayPropertyGetter(Field field, String propertyName) {
			_field = field;
			_propertyName = propertyName;
		}

		private final Field _field;
		private final String _propertyName;

	}

	private static class LiferayPropertySetter implements Setter {

		@Override
		public Method getMethod() {
			return null;
		}

		@Override
		public String getMethodName() {
			return null;
		}

		@Override
		public void set(
				Object target, Object value,
				SessionFactoryImplementor sessionFactoryImplementor)
			throws PropertyAccessException {

			try {
				_field.set(target, value);
			}
			catch (IllegalAccessException | IllegalArgumentException e) {
				throw new PropertyAccessException(
					e, e.getMessage(), true, _field.getDeclaringClass(),
					_propertyName);
			}
		}

		private LiferayPropertySetter(Field field, String propertyName) {
			_field = field;
			_propertyName = propertyName;
		}

		private final Field _field;
		private final String _propertyName;

	}

}