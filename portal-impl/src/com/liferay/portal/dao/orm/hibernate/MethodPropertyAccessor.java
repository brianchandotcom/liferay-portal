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

import java.lang.reflect.Member;
import java.lang.reflect.Method;

import java.util.Map;

import org.hibernate.PropertyAccessException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.property.access.spi.Getter;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.PropertyAccessStrategy;
import org.hibernate.property.access.spi.Setter;

/**
 * @author Shuyang Zhou
 */
public class MethodPropertyAccessor implements PropertyAccessStrategy {

	@Override
	public PropertyAccess buildPropertyAccess(
		Class clazz, String propertyName) {

		String key = StringBundler.concat(
			clazz.hashCode(), StringPool.POUND, clazz.getName(),
			StringPool.POUND, propertyName);

		PropertyAccess propertyAccess = _propertyAccesses.get(key);

		if (propertyAccess == null) {
			propertyAccess = new MethodPropertyAccess(
				this, clazz, propertyName);

			_propertyAccesses.put(key, propertyAccess);
		}

		return propertyAccess;
	}

	private static final Map<String, PropertyAccess> _propertyAccesses =
		new ConcurrentReferenceValueHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

	private static class MethodHolder {

		public Method getGetterMethod() {
			if (_getterMethod == null) {
				_initialize();
			}

			return _getterMethod;
		}

		public Method getSetterMethod() {
			if (_setterMethod == null) {
				_initialize();
			}

			return _setterMethod;
		}

		private MethodHolder(Class<?> clazz, String propertyName) {
			_clazz = clazz;
			_propertyName = propertyName;
		}

		private String _getMethodName1() {
			StringBuilder sb = new StringBuilder(_propertyName);

			char c = sb.charAt(0);

			if ((c >= 'a') && (c <= 'z')) {
				sb.setCharAt(0, (char)(c - 32));
			}

			return sb.toString();
		}

		private String _getMethodName2() {
			StringBuilder sb = new StringBuilder(_propertyName);

			for (int i = 0; i < sb.length(); i++) {
				char c = sb.charAt(i);

				if ((c >= 'a') && (c <= 'z')) {
					sb.setCharAt(i, (char)(c - 32));
				}
				else {
					break;
				}
			}

			return sb.toString();
		}

		private void _initialize() {
			try {
				String methodName1 = _getMethodName1();

				_getterMethod = _clazz.getMethod("get".concat(methodName1));

				_getterMethod.setAccessible(true);

				_setterMethod = _clazz.getMethod(
					"set".concat(methodName1), _getterMethod.getReturnType());

				_setterMethod.setAccessible(true);
			}
			catch (NoSuchMethodException noSuchMethodException1) {
				try {
					String methodName2 = _getMethodName2();

					_getterMethod = _clazz.getMethod("get".concat(methodName2));

					_getterMethod.setAccessible(true);

					_setterMethod = _clazz.getMethod(
						"set".concat(methodName2),
						_getterMethod.getReturnType());

					_setterMethod.setAccessible(true);
				}
				catch (NoSuchMethodException noSuchMethodException2) {
					noSuchMethodException2.addSuppressed(
						noSuchMethodException1);

					ReflectionUtil.throwException(noSuchMethodException2);
				}
			}
		}

		private final Class<?> _clazz;
		private Method _getterMethod;
		private final String _propertyName;
		private Method _setterMethod;

	}

	private static class MethodPropertyAccess implements PropertyAccess {

		@Override
		public Getter getGetter() {
			return _getter;
		}

		@Override
		public PropertyAccessStrategy getPropertyAccessStrategy() {
			return _propertyAccessStrategy;
		}

		@Override
		public Setter getSetter() {
			return _setter;
		}

		private MethodPropertyAccess(
			PropertyAccessStrategy propertyAccessStrategy, Class<?> clazz,
			String propertyName) {

			_propertyAccessStrategy = propertyAccessStrategy;

			MethodHolder methodHolder = new MethodHolder(clazz, propertyName);

			_getter = new MethodPropertyGetter(methodHolder);
			_setter = new MethodPropertySetter(methodHolder);
		}

		private final Getter _getter;
		private final PropertyAccessStrategy _propertyAccessStrategy;
		private final Setter _setter;

	}

	private static class MethodPropertyGetter implements Getter {

		@Override
		public Object get(Object target) {
			try {
				Method getterMethod = _methodHolder.getGetterMethod();

				return getterMethod.invoke(target);
			}
			catch (ReflectiveOperationException reflectiveOperationException) {
				return ReflectionUtil.throwException(
					reflectiveOperationException);
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
			Method getterMethod = _methodHolder.getGetterMethod();

			return getterMethod.getReturnType();
		}

		private MethodPropertyGetter(MethodHolder methodHolder) {
			_methodHolder = methodHolder;
		}

		private final MethodHolder _methodHolder;

	}

	private static class MethodPropertySetter implements Setter {

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
				Method setterMethod = _methodHolder.getSetterMethod();

				setterMethod.invoke(target, value);
			}
			catch (ReflectiveOperationException reflectiveOperationException) {
				ReflectionUtil.throwException(reflectiveOperationException);
			}
		}

		private MethodPropertySetter(MethodHolder methodHolder) {
			_methodHolder = methodHolder;
		}

		private final MethodHolder _methodHolder;

	}

}