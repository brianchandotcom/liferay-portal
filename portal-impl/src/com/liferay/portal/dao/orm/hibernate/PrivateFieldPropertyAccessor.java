/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.concurrent.ConcurrentReferenceValueHashMap;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import java.util.Map;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.property.access.spi.Getter;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;

/**
 * @author Shuyang Zhou
 */
public class PrivateFieldPropertyAccessor implements PropertyAccessStrategy {

	public PrivateFieldPropertyAccessor() {
		this(StringPool.UNDERLINE);
	}

	public PrivateFieldPropertyAccessor(String prefix) {
		_prefix = prefix;
	}

	@Override
	public PropertyAccess buildPropertyAccess(
		Class clazz, String propertyName) {

		String fieldName;

		if (_prefix.isEmpty()) {
			fieldName = propertyName;
		}
		else {
			fieldName = _prefix.concat(propertyName);
		}

		return _propertyAccesses.computeIfAbsent(
			StringBundler.concat(
				clazz.hashCode(), StringPool.POUND, clazz.getName(),
				StringPool.POUND, propertyName),
			key -> new FieldPropertyAccess(new FieldHolder(clazz, fieldName)));
	}

	private static final Map<String, PropertyAccess> _propertyAccesses =
		new ConcurrentReferenceValueHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

	private final String _prefix;

	private static class FieldGetter implements Getter {

		@Override
		public Object get(Object target) {
			Field field = _fieldHolder.getField();

			try {
				return field.get(target);
			}
			catch (IllegalAccessException illegalAccessException) {
				return ReflectionUtil.throwException(illegalAccessException);
			}
		}

		@Override
		public Object getForInsert(
			Object target, Map mergeMap,
			SharedSessionContractImplementor sharedSessionContractImplementor) {

			return get(target);
		}

		@Override
		public Member getMember() {
			return null;
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
			Field field = _fieldHolder.getField();

			return field.getType();
		}

		private FieldGetter(FieldHolder fieldHolder) {
			_fieldHolder = fieldHolder;
		}

		private final FieldHolder _fieldHolder;

	}

	private static class FieldHolder {

		public Field getField() {
			if (_field == null) {
				Class<?> modelClass = _containerJavaType;

				if (BaseModelImpl.class.isAssignableFrom(modelClass)) {
					Class<?> superClass = modelClass.getSuperclass();

					while (BaseModelImpl.class != superClass) {
						modelClass = superClass;

						superClass = modelClass.getSuperclass();
					}
				}

				try {
					Field field = modelClass.getDeclaredField(_propertyName);

					field.setAccessible(true);

					_field = field;
				}
				catch (NoSuchFieldException noSuchFieldException) {
					return ReflectionUtil.throwException(noSuchFieldException);
				}
			}

			return _field;
		}

		private FieldHolder(Class<?> containerJavaType, String propertyName) {
			_containerJavaType = containerJavaType;
			_propertyName = propertyName;
		}

		private final Class<?> _containerJavaType;
		private Field _field;
		private final String _propertyName;

	}

	private static class FieldSetter implements Setter {

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
			SessionFactoryImplementor sessionFactoryImplementor) {

			Field field = _fieldHolder.getField();

			try {
				field.set(target, value);
			}
			catch (IllegalAccessException illegalAccessException) {
				ReflectionUtil.throwException(illegalAccessException);
			}
		}

		private FieldSetter(FieldHolder fieldHolder) {
			_fieldHolder = fieldHolder;
		}

		private final FieldHolder _fieldHolder;

	}

	private class FieldPropertyAccess implements PropertyAccess {

		@Override
		public Getter getGetter() {
			return _getter;
		}

		@Override
		public PropertyAccessStrategy getPropertyAccessStrategy() {
			return PrivateFieldPropertyAccessor.this;
		}

		@Override
		public Setter getSetter() {
			return _setter;
		}

		private FieldPropertyAccess(FieldHolder fieldHolder) {
			_getter = new FieldGetter(fieldHolder);
			_setter = new FieldSetter(fieldHolder);
		}

		private final Getter _getter;
		private final Setter _setter;

	}

}